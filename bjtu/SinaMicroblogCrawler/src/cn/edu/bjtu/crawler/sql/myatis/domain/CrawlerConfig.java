/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis.domain;

/**
 * @author QiaoJian
 *
 */
public class CrawlerConfig {
	
	int id;
	
	int deep;
	
	long page;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeep() {
		return deep;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}
	
	
}
