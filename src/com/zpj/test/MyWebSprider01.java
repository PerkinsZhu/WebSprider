package com.zpj.test;

import com.zpj.entity.PageEntity;
import com.zpj.parser.HtmlParser;
import com.zpj.sprider.WebSprider;

/**
 * @author PerKins Zhu
 * @date:2016年9月2日 下午8:54:39
 * @version :1.1
 * 
 */
public class MyWebSprider01 extends WebSprider {

	public MyWebSprider01(HtmlParser parser, String seed) {
		super(parser, seed);
	}

	@Override
	public Object dealPageEntity(PageEntity entity) {
		System.out.println(entity.getTitle() + "---" + entity.getWeight() + "--" + entity.getUrl());
		return null;
	}

}
