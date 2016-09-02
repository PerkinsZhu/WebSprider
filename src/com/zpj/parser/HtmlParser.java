package com.zpj.parser;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

import com.zpj.entity.PageEntity;
import com.zpj.weightStrategy.WeightStrategy;

/**
 * @author PerKins Zhu
 * @date:2016年9月2日 下午8:44:39
 * @version :1.1
 * 
 */
	public abstract class HtmlParser {
		private WeightStrategy weightStrategy;
		private List<PageEntity> pageList = new ArrayList<PageEntity>();

		public HtmlParser(WeightStrategy weightStrategy) {
			super();
			this.weightStrategy = weightStrategy;
		}

		NodeList list;

		public List<PageEntity> parser(PageEntity pageEntity) {
			try {
				String entityUrl = pageEntity.getUrl();
				URL getUrl = new URL(entityUrl);
				HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
				connection.connect();
				Parser parser;
				parser = new Parser(entityUrl);
				// TODO 自动设置编码方式
				parser.setEncoding(getCharSet(connection));

				list = parserUrl(parser);
				setDefaultWeight();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (list == null)
				return pageList;
			for (int i = 0; i < list.size(); i++) {
				String url = list.elementAt(i).getText().substring(8);
				int lastIndex = url.indexOf("\"");
				url = url.substring(0, lastIndex == -1 ? url.length() : lastIndex);
				if (matchesUrl(url)) {
					String title = list.elementAt(i).toPlainTextString();
					float weight = weightStrategy.getWeight(title, url);
					if (weight <= 1) {
						continue;
					}
					pageList.add(new PageEntity(title, url, weight));
				}
			}
			return pageList;
		}

		private void setDefaultWeight() {
			// 解析出body网页文本内容
			String text = "";
			// 调用权重策略计算本页面中的所有连接的默认权重，每个页面的默认权重都要重新计算
			weightStrategy.setDefaultWeightByContent(text);
		}

		/**
		 * 子类实现方法，通过过滤器过滤出节点集合
		 * @param parser
		 * @return
		 */
		public abstract NodeList parserUrl(Parser parser);

		/**
		 * 子类实现方法，过滤进行存储的url
		 * @param url
		 * @return
		 */
		public abstract boolean matchesUrl(String url);

		//获取页面编码方式，默认gb2312
		private String getCharSet(HttpURLConnection connection) {
			Map<String, List<String>> map = connection.getHeaderFields();
			Set<String> keys = map.keySet();
			Iterator<String> iterator = keys.iterator();
			// 遍历,查找字符编码
			String key = null;
			String tmp = null;
			while (iterator.hasNext()) {
				key = iterator.next();
				tmp = map.get(key).toString().toLowerCase();
				// 获取content-type charset
				if (key != null && key.equals("Content-Type")) {
					int m = tmp.indexOf("charset=");
					if (m != -1) {
						String charSet =  tmp.substring(m + 8).replace("]", "");
						return charSet;
					}
				}
			}
			return "gb2312";
		}
		
		
	}
