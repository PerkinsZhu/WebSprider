package com.zpj.test;

import com.zpj.parser.HtmlParser;
import com.zpj.sprider.WebSprider;
import com.zpj.weightStrategy.WeightStrategy;

/**
 * @author PerKins Zhu
 * @date:2016年9月2日 下午8:34:26
 * @version :1.1
 * 
 */
public class RunThread {

	public static void main(String[] args) {

		WeightStrategy weightStrategy = new WeightStrategy01("中国");

		HtmlParser htmlParser = new HtmlParser01(weightStrategy);

		WebSprider sprider01 = new MyWebSprider01(htmlParser, "http://news.baidu.com/");

		Thread thread01 = new Thread(sprider01);

		thread01.start();

	}
}
