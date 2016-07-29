/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;

/**
 * @author QiaoJian
 * 抽象基类本工程的数据包装类需要继承该类
 */
public abstract class HttpDataBean {
	
	String result = "";
	
	public static List<Cookie> cookies = new ArrayList<Cookie>();
	
	String url = "";
	public HttpDataBean(String result) {
		super();
		this.result = result;
	}

	/**
	 * 
	 */
	public HttpDataBean() {
		// TODO Auto-generated constructor stub
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
