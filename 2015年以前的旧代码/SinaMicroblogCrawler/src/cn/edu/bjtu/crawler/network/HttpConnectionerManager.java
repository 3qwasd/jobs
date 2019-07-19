/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.network;

import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import cn.edu.bjtu.crawler.bean.sina.ProxyHost;
import cn.edu.bjtu.crawler.network.sina.SinaHttpClient;

/**
 * @author QiaoJian
 *	http管理类
 */
public class HttpConnectionerManager {

	private static HttpConnectionerManager httpConnectionerManager;

	private static HttpVersion defaultHttpVersion = HttpVersion.HTTP_1_1;

	private static String defaultChaSet = "UTF-8";

	//private HttpParams httpParams;


	public static int MAX_CONNECTION_NUM = 4;
	/*http连接池*/
	private List<SinaHttpClient> httpConnectionPool = new ArrayList<>();

	/**
	 * 构造器
	 */
	private HttpConnectionerManager(){

	}
	/**
	 * 获取http管理类实例
	 * @return
	 */
	public static HttpConnectionerManager getInstance(){
		if(httpConnectionerManager == null){
			httpConnectionerManager = new HttpConnectionerManager();
		}
		return httpConnectionerManager;
	}
	/**
	 * 归还httpClient
	 * @param httpClient
	 */
	public synchronized void giveBackHttpClient(SinaHttpClient httpClient){
		if(httpConnectionPool.contains(httpClient)){
			return;
		}
		httpConnectionPool.add(httpClient);
	}
	/**
	 * 从连接池中移除httpClient
	 * @param httpClient
	 */
	public synchronized void removeHttpClient(SinaHttpClient httpClient){
		if(httpConnectionPool.contains(httpClient)){
			httpConnectionPool.remove(httpClient);
		}
	}
	/**
	 * 创建httpClient
	 */
	public synchronized SinaHttpClient getFirstHttpClient(){
		if(httpConnectionPool.size()<1){
			return null;
		}
		return httpConnectionPool.remove(0);
	}
	/**
	 * 创建httpClient
	 */
	public synchronized SinaHttpClient getLastHttpClient(){
		if(httpConnectionPool.size()<1){
			return null;
		}
		return httpConnectionPool.remove(httpConnectionPool.size()-1);
	}
	/**
	 * 创建httpClient对象放入连接池使用Jre的代理
	 */
	public SinaHttpClient createSinaHttpClient(){
		return createSinaHttpClient(null);
	}
	/**
	 * 清空连接池
	 */
	public void clearPool(){
		for(int i = 0;i<httpConnectionPool.size();i++){
			SinaHttpClient client = httpConnectionPool.remove(i);
			client.getHttpClient().getConnectionManager().shutdown();
		}
		httpConnectionPool.clear();
		if(httpConnectionPool.size()>0){
			clearPool();
		}
	}
	/**
	 * 判断连接池是否还有连接可用
	 * @return
	 */
	public synchronized boolean hasVisibleHttpClient(){
		if(httpConnectionPool.size()>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 创建httpClient对象放入连接池
	 */
	public SinaHttpClient createSinaHttpClient(ProxyHost proxyHost){

		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter(ClientPNames.HANDLE_REDIRECTS, false);  
		HttpProtocolParams.setVersion(httpParams, defaultHttpVersion);
		HttpProtocolParams.setContentCharset(httpParams, defaultChaSet);

		PoolingClientConnectionManager poolingClientConnectionManager = 
				new PoolingClientConnectionManager();
		poolingClientConnectionManager.setMaxTotal(20);

		DefaultHttpClient defaultHttpClient = new DefaultHttpClient(poolingClientConnectionManager,httpParams);

		if(proxyHost!=null){
			HttpHost proxy = new HttpHost(proxyHost.getIp(),proxyHost.getPort());
			defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}else{
			ProxySelectorRoutePlanner routePlanner = new ProxySelectorRoutePlanner(
					defaultHttpClient.getConnectionManager().getSchemeRegistry(),
					ProxySelector.getDefault());  
			defaultHttpClient.setRoutePlanner(routePlanner);
			//System.out.println();
		}
		SinaHttpClient sinaHttpClient = new SinaHttpClient();
		sinaHttpClient.setHttpClient(defaultHttpClient);
		this.httpConnectionPool.add(sinaHttpClient);
		return sinaHttpClient;
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
}
