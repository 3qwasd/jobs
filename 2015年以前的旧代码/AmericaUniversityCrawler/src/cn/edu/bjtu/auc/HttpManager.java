/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpVersion;

/**
 * @author QiaoJian
 *
 */
public class HttpManager {
	
	private static HttpManager httpConnectionerManager;

	public static HttpVersion defaultHttpVersion = HttpVersion.HTTP_1_1;

	public static String defaultChaSet = "UTF-8";

	//private HttpParams httpParams;


	public static int MAX_CONNECTION_NUM = 4;
	/*http连接池*/
	private List<HttpConnectioner> httpConnectionPool = new ArrayList<HttpConnectioner>();

	/**
	 * 构造器
	 */
	private HttpManager(){

	}
	/**
	 * 获取http管理类实例
	 * @return
	 */
	public synchronized static HttpManager getInstance(){
		if(httpConnectionerManager == null){
			httpConnectionerManager = new HttpManager();
		}
		return httpConnectionerManager;
	}
	/**
	 * 创建httpConnection实例放入连接池
	 * @return
	 */
	public synchronized void createHttpConnection(String proxyIp,int port){
		HttpConnectioner httpConnectioner = new HttpConnectioner(proxyIp,port);
		httpConnectionPool.add(httpConnectioner);
	}
	/**
	 * 创建httpConnection实例放入连接池
	 * @return
	 */
	public synchronized void createHttpConnection(){
		HttpConnectioner httpConnectioner = new HttpConnectioner();
		httpConnectionPool.add(httpConnectioner);
	}
	/**
	 * 创建httpConnection实例放入连接池
	 * @return
	 */
	public synchronized void createHttpConnection(String cersPath,String cersPassword){
		HttpConnectioner httpConnectioner = new HttpConnectioner(cersPath,cersPassword);
		httpConnectionPool.add(httpConnectioner);
	}
	public synchronized void createHttpConnectionSSL(){
		HttpConnectioner httpConnectioner = new HttpConnectioner(true);
		httpConnectionPool.add(httpConnectioner);
	}
	/**
	 * 创建httpConnection实例放入连接池
	 * @return
	 */
	public synchronized void createHttpConnection(boolean isSSL){
		HttpConnectioner httpConnectioner = new HttpConnectioner(isSSL);
		httpConnectionPool.add(httpConnectioner);
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
	 * 清空连接池
	 */
	public synchronized void clearPool(){
		for(int i = 0;i<httpConnectionPool.size();i++){
			HttpConnectioner client = httpConnectionPool.remove(i);
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
	 * 获取httpClient
	 */
	public synchronized HttpConnectioner getFirstHttpConnection(){
		if(httpConnectionPool.size()<1){
			return null;
		}
		return httpConnectionPool.remove(0);
	}
	/**
	 * 获取httpClient
	 */
	public synchronized HttpConnectioner getLastHttpConnection(){
		if(httpConnectionPool.size()<1){
			return null;
		}
		return httpConnectionPool.remove(httpConnectionPool.size()-1);
	}
	/**
	 * 从连接池中移除httpClient
	 * @param httpClient
	 */
	public synchronized void removeHttpClient(HttpConnectioner httpClient){
		if(httpConnectionPool.contains(httpClient)){
			httpConnectionPool.remove(httpClient);
		}
	}
	/**
	 * 归还httpClient
	 * @param httpClient
	 */
	public synchronized void giveBackHttpClient(HttpConnectioner httpClient){
		if(httpConnectionPool.contains(httpClient)){
			return;
		}
		httpConnectionPool.add(httpClient);
	}
}
