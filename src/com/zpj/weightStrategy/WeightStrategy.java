package com.zpj.weightStrategy;
/**
 * @author PerKins Zhu
 * @date:2016年9月2日 下午9:04:37
 * @version :1.1
 * 
 */

public abstract class WeightStrategy  {
	protected String keyWord;
	protected float defaultWeight = 1;

	public WeightStrategy(String keyWord) {
		this.keyWord = keyWord;
	}

	public  float getWeight(String title, String url){
		return defaultWeight+countWeight(title,url);
	};
	
	/**
	 * 计算连接权重，计算结果为：defaultWeight+该方法返回值
	 * @param title
	 * @param url
	 * @return
	 */
	public abstract float countWeight(String title, String url) ;

	/**
	 * 根据网页内容计算该页面中所有连接的默认权重
	 * @param content
	 */
	public abstract void setDefaultWeightByContent(String content);

	
}
