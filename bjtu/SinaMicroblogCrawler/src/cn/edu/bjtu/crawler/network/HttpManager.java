/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import cn.edu.bjtu.crawler.bean.HttpDataBean;
import cn.edu.bjtu.crawler.system.http.BaseManager;
import cn.edu.bjtu.crawler.utils.StringUtils;

/**
 * @author QiaoJian
 *
 */
public class HttpManager extends BaseManager{
	
	private static HttpManager httpManager;
	
	private static HttpVersion defaultHttpVersion = HttpVersion.HTTP_1_1;
	
	private static String defaultChaSet = "UTF-8";
	/*http连接池*/
	private static Map<String,HttpClient> connPool = new HashMap<String,HttpClient>();
	
	private ThreadLocal<HttpClient> threadLocal = new ThreadLocal<HttpClient>();
	
	private HttpManager() {
		super();
	}


	public static HttpManager getHttpManager(){
		if(httpManager == null){
			httpManager = new HttpManager();
		}
		return httpManager;
	}
	
	/**
	 * 设置默认的httpVersion及字符编码
	 * @param httpVersion
	 * @param charSet
	 */
	public static void defaultHttpVersionAndChaset(HttpVersion httpVersion,String charSet){
		defaultHttpVersion = httpVersion;
		defaultChaSet = charSet;
	}
	/**
	 * 从响应数据获取cookie
	 * @param response
	 * @return
	 */
	public void getResponesCookie(HttpResponse response){
		List<Cookie> cookies = HttpDataBean.cookies;
		Header[] hds=response.getAllHeaders();
		if(hds!=null && hds.length>0){
			for(int i=0;i<hds.length;i++){
				if(hds[i].getName().equalsIgnoreCase("Set-Cookie")){
					if(cookies==null){
						cookies=new ArrayList<Cookie>();
					}					 
					String cookiestring[]=hds[i].getValue().split(";");
					String ss[]=cookiestring[0].split("=",2);
					String cookiename=ss[0];
					String cookievalue=ss[1];
					Cookie cookie=new BasicClientCookie(cookiename,cookievalue);
					cookies.add(cookie);
				}
			}
		}		
	}
	/**
	 * 根据特定的httpVersion及字符编码集创建httpClient
	 * @return
	 */
	public HttpClient createHttpClient(HttpVersion httpVersion,String charSet){
		
		HttpClient defaultHttpClient = threadLocal.get();
		if(defaultHttpClient != null){
			return defaultHttpClient;
		}
		HttpParams httpParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(httpParams, httpVersion);
		HttpProtocolParams.setContentCharset(httpParams, charSet);
		
		PoolingClientConnectionManager poolingClientConnectionManager = new PoolingClientConnectionManager();
		poolingClientConnectionManager.setMaxTotal(20);
		defaultHttpClient = new DefaultHttpClient(poolingClientConnectionManager,httpParams);
		HttpHost proxy = new HttpHost("122.96.59.104",80);
		defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		this.threadLocal.set(defaultHttpClient);
		return defaultHttpClient;
	}
	/**
	 * 创建httpClient
	 * @return
	 */
	public HttpClient createHttpClient(){
		
		HttpClient defaultHttpClient = threadLocal.get();
		if(defaultHttpClient != null){
			return defaultHttpClient;
		}
		
		HttpParams httpParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(httpParams, defaultHttpVersion);
		HttpProtocolParams.setContentCharset(httpParams, defaultChaSet);
		
		PoolingClientConnectionManager poolingClientConnectionManager = new PoolingClientConnectionManager();
		poolingClientConnectionManager.setMaxTotal(20);
		defaultHttpClient = new DefaultHttpClient(poolingClientConnectionManager,httpParams);
		this.threadLocal.set(defaultHttpClient);
		return defaultHttpClient;
	}
	/**
	 * 从响应中获取输入流
	 * @param httpResponse
	 * @return
	 */
	public InputStream getInputStreamFromResponse(HttpResponse httpResponse){
		HttpEntity entity = httpResponse.getEntity();
		try {
			InputStream inputStream = entity.getContent();
			return inputStream;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从响应中获取字符串
	 * @param httpResponse
	 * @return
	 */
	public String getStringFromResponse(HttpResponse httpResponse){
		InputStream inputStream = this.getInputStreamFromResponse(httpResponse);
		String result = StringUtils.stream2String(inputStream);
		
		return result;
	}
}
