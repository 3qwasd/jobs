/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import cn.edu.bjtu.crawler.network.sina.SinaHttpClient;
import cn.edu.bjtu.crawler.utils.StringUtils;

/**
 * @author QiaoJian
 * 与服务器进行通信的类
 */
public abstract class HttpConnectioner {

	private HttpConnectionerManager connectionerManager;

	
	public SinaHttpClient sinaHttpClient;
	//public List<Cookie> cookies = new ArrayList<>();
	public HttpConnectioner() {
		super();
		connectionerManager = HttpConnectionerManager.getInstance();
	}
	/**
	 * 放入重要的cookies
	 * @param header
	 * @param keys
	 */
	public void putImportantCookies(Map<String,String> header){
		String cookieStr = StringUtils.importantCookies2String(sinaHttpClient.getImportantCookies());
		header.put("Cookie", cookieStr);
	}
	/**
	 * 放入cookies
	 * @param header
	 * @param domain
	 */
	public void putCookies(Map<String,String> header,String domain){
		if(sinaHttpClient.getCookies().getCookiesByDomain(domain)!=null){
			String cookieStr = StringUtils.cookies2String(sinaHttpClient.getCookies().getCookiesByDomain(domain));
			String temp = StringUtils.cookies2String(sinaHttpClient.getCookies().getCookiesByDomain("all"));
			if(temp!=null&&temp.length()>0){
				cookieStr = cookieStr+";"+temp;
			}
			header.put("Cookie", cookieStr);
		}
	}
	/**
	 * 
	 * @param header
	 * @param domains
	 */
	public void putCookies(Map<String,String> header,String[] domains){


		String cookieStr = "";
		for(int i=0;i<domains.length;i++){
			String domain = domains[i];
			if(sinaHttpClient.getCookies().getCookiesByDomain(domain)!=null){
				String temp = StringUtils.cookies2String(sinaHttpClient.getCookies().getCookiesByDomain(domain));
				if(temp!=null&&temp.length()>0){
					cookieStr = cookieStr+temp+";";
				}
			}
		}
		if(cookieStr!=null&&cookieStr.length()>0){
			cookieStr = cookieStr.substring(0, cookieStr.length()-1);
			header.put("Cookie", cookieStr);
		}
	}
	public void clearCookies(){
		sinaHttpClient.clearCookies();
	}
	/**
	 * 从响应数据获取cookie
	 * @param response
	 * @return
	 */
	public void getResponesCookie(HttpResponse response){

		Header[] hds = response.getHeaders("Set-Cookie");
		if(hds!=null && hds.length>0){
			for(int i=0;i<hds.length;i++){			 
				String cookiestring[]=hds[i].getValue().split(";");
				//domain
				String cookieName = null;
				String cookieValue = null;
				String cookieDomain = null;
				for(int j=0;j<cookiestring.length;j++){
					if(j==0){
						String ss[]=cookiestring[j].split("=",2);
						cookieName=ss[0];
						cookieValue=ss[1];
					}else{
						if(cookiestring[j].trim().startsWith("domain")){
							String ss[]=cookiestring[j].split("=",2);
							cookieDomain=ss[1].trim();
						}
					}

				}
				if(cookieDomain!=null&&cookieDomain.trim().length()>0)
					sinaHttpClient.getCookies().putCookie(cookieDomain, cookieName, cookieValue);
				else
					sinaHttpClient.getCookies().putCookie("all", cookieName, cookieValue);

			}
		}	
	}
	/**
	 * 付给httpClient
	 * @param httpClient
	 */
	public void setHttpClient(SinaHttpClient httpClient) {
		this.sinaHttpClient = httpClient;
	}
	/**
	 * 将httpclient对象放入连接池
	 */
	public void giveBackHttpClient(){
		if(this.sinaHttpClient!=null)
			this.connectionerManager.giveBackHttpClient(sinaHttpClient);
	}

	/**
	 * 处理get请求
	 * @param url
	 * @param headers
	 * @return
	 */
	public HttpResponse doGet(String url,Map<String,String> headers){


		HttpGet httpGet = new HttpGet(url);
		if(headers!=null&&headers.size()>0){
			for(String key:headers.keySet()){
				httpGet.addHeader(key, headers.get(key));
			}
		}

		HttpResponse httpResponse = null;

		try {
			httpResponse = sinaHttpClient.getHttpClient().execute(httpGet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return httpResponse;
	}
	/**
	 * 
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public HttpResponse doPost(String url,Map<String,String> headers,Map<String,String> params){
		HttpPost httpPost = new HttpPost(url);
		HttpResponse httpResponse = null;

		if(headers!=null&&headers.size()>0){
			for(String key:headers.keySet()){
				httpPost.setHeader(key, headers.get(key));
			}
		}
		List<NameValuePair> nvPairs = null;
		if(params!=null&&params.size()>0){
			nvPairs = new ArrayList<NameValuePair>();
			for(String key:params.keySet()){
				BasicNameValuePair nameValuePair = new BasicNameValuePair(key, params.get(key));
				nvPairs.add(nameValuePair);
			}
		}
		try{
			if(nvPairs!=null){
				httpPost.setEntity(new UrlEncodedFormEntity(nvPairs,HTTP.UTF_8));
			}
			httpResponse = sinaHttpClient.getHttpClient().execute(httpPost);
			return httpResponse;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
