package com.zpj.test;

import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.zpj.parser.HtmlParser;
import com.zpj.weightStrategy.WeightStrategy;

/**
 * @author PerKins Zhu
 * @date:2016年9月2日 下午8:46:39
 * @version :1.1
 * 
 */
public class HtmlParser01 extends HtmlParser {

	public HtmlParser01(WeightStrategy weightStrategy) {
		super(weightStrategy);
	}

	@Override
	public NodeList parserUrl(Parser parser) {
		NodeFilter hrefNodeFilter = new NodeFilter() {
			@Override
			public boolean accept(Node node) {
				if (node.getText().startsWith("a href=")) {
					return true;
				} else {
					return false;
				}
			}
		};
		try {
			return parser.extractAllNodesThatMatch(hrefNodeFilter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean matchesUrl(String url) {
		Pattern p = Pattern
				.compile("(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*");
		return p.matcher(url).matches();
	}

}
