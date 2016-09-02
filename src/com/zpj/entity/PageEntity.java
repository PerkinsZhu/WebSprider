package com.zpj.entity;
/**
 * @author PerKins Zhu
 * @date:2016年9月2日 下午8:43:26
 * @version :1.1
 * 
 */

	public class PageEntity implements Comparable<PageEntity> {

		private String title;
		private String url;
		private float weight;
		private int PRIME = 1000000000;

		public PageEntity(String title, String url, float weight) {
			super();
			this.title = title;
			this.url = url;
			this.weight = weight;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public float getWeight() {
			return weight;
		}

		public void setWeight(float weight) {
			this.weight = weight;
		}

		/**
		 * 比较优先级： weight > title > url
		 */
		@Override
		public int compareTo(PageEntity node) {
			if (this.weight == node.weight) {
				if (this.title.equals(node.title)) {
					if (this.url.equals(node.url)) {
						return 0;
					} else {
						return this.url.compareTo(node.url);
					}
				} else {
					return this.title.compareTo(node.title);
				}
			} else {
				return this.weight > node.weight ? 1 : -1;
			}
		}

		//覆写hashCode
		@Override
		public int hashCode() {
			//如果调用父类的hashCode，则每个对象的hashcode都不相同，这里通过url计算hashcode，相同url的hashcode是相同的
			int hash, i;
			for (hash = this.url.length(), i = 0; i < this.url.length(); ++i)
				hash = (hash << 4) ^ (hash >> 28) ^ this.url.charAt(i);
			if (hash < 0) {
				hash = -hash;
			}
			return (hash % PRIME);
		}
		
		
		

}
