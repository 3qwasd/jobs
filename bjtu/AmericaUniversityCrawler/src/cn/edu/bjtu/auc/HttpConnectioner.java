/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ProxySelector;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






















import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
/**
 * @author QiaoJian
 *
 */
public class HttpConnectioner {

	DefaultHttpClient httpClient;


	public HttpConnectioner() {
		super();
		createHttpClient();

	}
	public HttpConnectioner(String cerPath,String cerPassword){
		try {
			createSSLHttpClient(cerPath,cerPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public HttpConnectioner(boolean ssl){
		if(ssl){
			try {
				createHttpClientSSL();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			createHttpClient();
		}
	}
	public HttpConnectioner(String proxIp,int port){
		super();

		createHttpClientProxy(proxIp, port);
	}
	public void createHttpClientSSL(){
		try {  
			HttpParams httpParams = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParams, HttpManager.defaultHttpVersion);
			HttpProtocolParams.setContentCharset(httpParams, HttpManager.defaultChaSet);

			PoolingClientConnectionManager poolingClientConnectionManager = new PoolingClientConnectionManager();
			poolingClientConnectionManager.setMaxTotal(20);
			httpClient = new DefaultHttpClient(poolingClientConnectionManager,httpParams);

			SSLContext sslcontext = SSLContext.getInstance("SSL");  
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);  
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);  
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
			Scheme https = new Scheme("https", sf, 443);  
			httpClient.getConnectionManager().getSchemeRegistry().register(https);  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
	}
	private static TrustManager truseAllManager = new X509TrustManager(){  

		public void checkClientTrusted(  
				java.security.cert.X509Certificate[] arg0, String arg1)  
						throws CertificateException {  
			// TODO Auto-generated method stub  

		}  

		public void checkServerTrusted(  
				java.security.cert.X509Certificate[] arg0, String arg1)  
						throws CertificateException {  
			// TODO Auto-generated method stub  

		}  

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
			// TODO Auto-generated method stub  
			return null;  
		}  

	}; 
	public void createHttpClientProxy(String proxIp,int port){
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter(ClientPNames.HANDLE_REDIRECTS, false);  
		HttpProtocolParams.setVersion(httpParams, HttpManager.defaultHttpVersion);
		HttpProtocolParams.setContentCharset(httpParams, HttpManager.defaultChaSet);

		PoolingClientConnectionManager poolingClientConnectionManager = 
				new PoolingClientConnectionManager();
		poolingClientConnectionManager.setMaxTotal(20);

		httpClient = new DefaultHttpClient(poolingClientConnectionManager,httpParams);


		HttpHost proxy = new HttpHost(proxIp,port);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}
	public void createHttpClient(){
		HttpParams httpParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(httpParams, HttpManager.defaultHttpVersion);
		HttpProtocolParams.setContentCharset(httpParams, HttpManager.defaultChaSet);

		PoolingClientConnectionManager poolingClientConnectionManager = new PoolingClientConnectionManager();
		poolingClientConnectionManager.setMaxTotal(20);
		httpClient = new DefaultHttpClient(poolingClientConnectionManager,httpParams);

		ProxySelectorRoutePlanner routePlanner = new ProxySelectorRoutePlanner(
				httpClient.getConnectionManager().getSchemeRegistry(),
				ProxySelector.getDefault());  
		httpClient.setRoutePlanner(routePlanner);
	}
	public void createSSLHttpClient(String cerPath,String password) throws Exception{
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter(ClientPNames.HANDLE_REDIRECTS, false);  
		HttpProtocolParams.setVersion(httpParams, HttpManager.defaultHttpVersion);
		HttpProtocolParams.setContentCharset(httpParams, HttpManager.defaultChaSet);

		PoolingClientConnectionManager poolingClientConnectionManager = new PoolingClientConnectionManager();
		poolingClientConnectionManager.setMaxTotal(20);
		httpClient = new DefaultHttpClient(poolingClientConnectionManager,httpParams);

		ProxySelectorRoutePlanner routePlanner = new ProxySelectorRoutePlanner(
				httpClient.getConnectionManager().getSchemeRegistry(),
				ProxySelector.getDefault());  
		httpClient.setRoutePlanner(routePlanner);

		//获得密匙库
		KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream instream = new FileInputStream(new File(cerPath));
		//密匙库的密码
		trustStore.load(instream, password.toCharArray());
		//注册密匙库
		SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
		//不校验域名
		socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme sch = new Scheme("https", 443, socketFactory);
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);
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
			httpResponse = httpClient.execute(httpGet);
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
				httpPost.setEntity(new UrlEncodedFormEntity(nvPairs));
			}
			httpResponse = httpClient.execute(httpPost);
			return httpResponse;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 创建请求头的map集合，该集合内的请求头数据，是无论什么请求都需要的
	 * @return
	 */
	public Map<String,String> createCommonHeader(){
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Accept","application/x-ms-application, image/jpeg, application/xaml+xml, image/gif, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		headers.put("Accept-Language","zh-CN");
		//headers.put("Accept-Encoding", HttpPropertiesUtils.getAcceptEncoding());
		headers.put("Connection","Keep-Alive");
		headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)");
		//headers.put("Content-Type", );
		//headers.put("Cache-Control", );
		return headers;
	}
	public DefaultHttpClient getHttpClient() {
		return httpClient;
	}
	public void setHttpClient(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}

}
