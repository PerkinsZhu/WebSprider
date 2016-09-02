package com.zpj.test;

import com.zpj.weightStrategy.WeightStrategy;

/**
 * @author PerKins Zhu
 * @date:2016年9月2日 下午8:34:19
 * @version :1.1
 * 
 */
public class WeightStrategy01 extends WeightStrategy {

	public WeightStrategy01(String keyWord) {
		super(keyWord);
	}

	public float countWeight(String title, String url) {
		int temp = 0;
		while (-1 != title.indexOf(keyWord)) {
			temp++;
			title = title.substring(title.indexOf(keyWord) + keyWord.length());
		}
		return temp * 2;
	}

	@Override
	public void setDefaultWeightByContent(String content) {
		// 解析文本内容计算defaultWeight
		super.defaultWeight = 1;
	}

}
