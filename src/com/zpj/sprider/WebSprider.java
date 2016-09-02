package com.zpj.sprider;

import java.util.List;

import com.zpj.collection.PageCollection;
import com.zpj.entity.PageEntity;
import com.zpj.parser.HtmlParser;

/**
 * @author PerKins Zhu
 * @date:2016年9月2日 下午9:02:39
 * @version :1.1
 * 
 */

public abstract class WebSprider implements Runnable {
	private HtmlParser parser;
	// 存储待访问的节点
	private PageCollection<PageEntity> collection = new PageCollection<PageEntity>();
	// 存储已经访问过的节点
	private PageCollection<Integer> visitedCollection = new PageCollection<Integer>();

	public WebSprider(HtmlParser parser, String seed) {
		super();
		this.parser = parser;
		collection.insert(new PageEntity("", seed, 1));
	}

	@Override
	public void run() {
		PageEntity entity;
		while (!collection.isEmpty()) {
			entity = collection.getMaxNode();
			dealPageEntity(entity);
			addToCollection(parser.parser(entity));
		}
	}

	private void addToCollection(List<PageEntity> list) {
		for (int i = 0; i < list.size(); i++) {
			PageEntity pe = list.get(i);
			int hashCode = pe.hashCode();
			if (visitedCollection.get(hashCode) == null) {
				collection.insert(pe);
				visitedCollection.insert(hashCode);
			}
		}
	}

	/**
	 * 子类对爬取的数据进行处理，可以对entity进行存储或者输出等操作
	 * @param entity
	 * @return
	 */
	public abstract Object dealPageEntity(PageEntity entity);
}
