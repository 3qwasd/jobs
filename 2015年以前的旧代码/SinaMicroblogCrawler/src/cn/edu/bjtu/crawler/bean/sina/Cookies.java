/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.bean.sina;

import java.util.HashMap;
import java.util.Map;

/**
 * @author QiaoJian
 * cookie类
 */
public class Cookies {
	
	Map<String,Map<String,String>> cookies = null;

	public Cookies() {
		super();
		this.cookies = new HashMap<String, Map<String,String>>();
	}
	/**
	 * 
	 * @param domain
	 * @param name
	 * @param value
	 */
	public void putCookie(String domain,String name,String value){
		if(!cookies.containsKey(domain)){
			Map<String,String> map = new HashMap<>();
			cookies.put(domain, map);
		}
		cookies.get(domain).put(name, value);
	}
	/**
	 * 
	 * @param domain
	 * @return
	 */
	public Map<String,String> getCookiesByDomain(String domain){
		if(cookies.containsKey(domain))
			return this.cookies.get(domain);
		else
			return null;
	}
}
