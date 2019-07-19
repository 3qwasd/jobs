/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.network.sina;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;

import cn.edu.bjtu.crawler.bean.sina.Cookies;
import cn.edu.bjtu.crawler.bean.sina.LoginUserAccount;

/**
 * @author QiaoJian
 *
 */
public class SinaHttpClient {
	
	HttpClient httpClient;
	LoginUserAccount account;
	Cookies cookies = new Cookies();
	private Map<String,String> importantCookies = new HashMap<String,String>();
	
	public void putImportantCookies(String key,String value){
		importantCookies.put(key, value);
	}
	public String getImportantCookies(String key){
		if(importantCookies.containsKey(key)){
			return importantCookies.get(key);
		}
		return null;
	}
	public boolean containImportantCookie(String key){
		return importantCookies.containsKey(key);
	}
	
	public Map<String, String> getImportantCookies() {
		return importantCookies;
	}
	public void setImportantCookies(Map<String, String> importantCookies) {
		this.importantCookies = importantCookies;
	}
	public HttpClient getHttpClient() {
		return httpClient;
	}
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	public LoginUserAccount getAccount() {
		return account;
	}
	public void setAccount(LoginUserAccount account) {
		this.account = account;
	}
	public Cookies getCookies() {
		return cookies;
	}
	public void setCookies(Cookies cookies) {
		this.cookies = cookies;
	}
	
	public void clearCookies(){
		cookies = new Cookies();
	}
}
