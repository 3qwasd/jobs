/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sina.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

import cn.edu.bjtu.crawler.bean.HttpDataBean;
import cn.edu.bjtu.crawler.bean.sina.JsonDataBean;
import cn.edu.bjtu.crawler.bean.sina.SinaResponseBean;
import cn.edu.bjtu.crawler.network.sina.SinaHttpLoginManager;

/**
 * @author QiaoJian
 *
 */
public class SinaHttpConnectionerTest {

	/**
	 * 
	 */
	private SinaHttpLoginManager sinaHttpConnectioner = null;

	@Before
	public void createSinaHttpConnectioner(){
		sinaHttpConnectioner = new SinaHttpLoginManager();
	}
	@Test
	public void loginMincrsBlogTest(){
		sinaHttpConnectioner.loginSinaMicroblog();
		
	}
	@Test
	public void preLoginTest(){
		JsonDataBean preLoginData = (JsonDataBean) sinaHttpConnectioner.preLogin();

		System.out.println(preLoginData.getResult());
	}
	@Test
	public void doLoginTest(){
		JsonDataBean preLoginData = (JsonDataBean) sinaHttpConnectioner.preLogin();

		System.out.println(preLoginData.getResult());
		
		HttpDataBean httpDataBean = sinaHttpConnectioner.doLogin(preLoginData);
		System.out.println(httpDataBean.getResult());
		System.out.println(httpDataBean.getUrl());
	}
	/*@Test
	public void afterLoginTest(){
		JsonDataBean afterLoginBean ;
		SinaResponseBean sinaResponseBean;
		sinaResponseBean = (SinaResponseBean) sinaHttpConnectioner.doLogin();
		afterLoginBean = (JsonDataBean) sinaHttpConnectioner.afterLogin(sinaResponseBean);
		System.out.println(afterLoginBean.getResult());
	}*/
	@Test
	public void doGetTest(){

		SinaHttpLoginManager sinaHttpConnectioner = new SinaHttpLoginManager();
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Accept","application/x-ms-application, "
				+ "image/jpeg, application/xaml+xml, "
				+ "image/gif, image/pjpeg, application/x-ms-xbap, "
				+ "application/vnd.ms-excel, application/vnd.ms-powerpoint,"
				+ " application/msword, */*");
		headers.put("Accept-Encoding","gzip, deflate");
		headers.put("Accept-Language","zh-CN");
		headers.put("Connection","Keep-Alive");
		headers.put("Host","www.sina.com.cn");
		headers.put("User-Agent","Mozilla/4.0 "
				+ "(compatible; MSIE 8.0;"
				+ " Windows NT 6.1; WOW64; "
				+ "Trident/4.0; SLCC2; "
				+ ".NET CLR 2.0.50727; "
				+ ".NET CLR 3.5.30729; "
				+ ".NET CLR 3.0.30729;"
				+ " Media Center PC 6.0; "
				+ ".NET4.0C; .NET4.0E)");
		HttpResponse httpResponse = sinaHttpConnectioner.doGet("http://www.sina.com.cn/", headers);

		System.out.println(httpResponse);
	}
}
