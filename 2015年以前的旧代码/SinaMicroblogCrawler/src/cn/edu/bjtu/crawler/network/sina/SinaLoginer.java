/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.network.sina;

import java.util.Date;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import cn.edu.bjtu.crawler.bean.HttpDataBean;
import cn.edu.bjtu.crawler.bean.sina.JsonDataBean;
import cn.edu.bjtu.crawler.bean.sina.LoginUserAccount;
import cn.edu.bjtu.crawler.bean.sina.SinaResponseBean;
import cn.edu.bjtu.crawler.exception.LoginException;
import cn.edu.bjtu.crawler.network.HttpConnectioner;
import cn.edu.bjtu.crawler.servicer.sina.LoginSinaService;
import cn.edu.bjtu.crawler.utils.PropertiesUtils;
import cn.edu.bjtu.crawler.utils.StringUtils;

/**
 * @author QiaoJian
 *
 */
public class SinaLoginer extends HttpConnectioner{

	private LoginSinaService loginSinaService;

	private LoginUserAccount account;


	/**
	 * 构造函数
	 * @param httpClient
	 * @param account
	 */
	public SinaLoginer(SinaHttpClient httpClient,LoginUserAccount account) {
		super();
		// TODO Auto-generated constructor stub
		this.sinaHttpClient = httpClient;
		loginSinaService = new LoginSinaService();
		this.account = account;
		this.sinaHttpClient.setAccount(account);
	}
	/**
	 * 登陆
	 * @return
	 * @throws Exception
	 */
	public boolean login() throws Exception{
		visitIndex();
		JsonDataBean preLoginData = null;
		SinaResponseBean loginResponseData = null;
		JsonDataBean doRedResData = null;
		
		preLoginData = this.doPreLogin();
		loginResponseData = this.doLogin(preLoginData);
		doRedResData = this.doRedirect(loginResponseData);
		return this.doAfterLogin(doRedResData);
	}
	/**
	 * 登陆结束后重定向并验证是否登陆成功
	 * @param doRedData
	 * @return
	 * @throws LoginException 
	 */
	public boolean doAfterLogin(JsonDataBean doRedData) throws LoginException{
		String indexUrl = this.account.getIndexUrl();
		Map<String,String> headers = loginSinaService.createCommonHeader();
		headers.put("Host", "weibo.com");
		this.putCookies(headers, ".weibo.com");		
		HttpResponse httpResponse = this.doGet(indexUrl, headers);
		int code = -1;
		code = httpResponse.getStatusLine().getStatusCode();
		if(code!=200&&code!=302){
			throw new LoginException("F", "Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------doAfterLogin exception! The response code is not 200!code="+code);
		}
		if(code == 302){
			Header[] hs = httpResponse.getHeaders("Location");
			Header locHeader = hs[0];
			String redUrl = locHeader.getValue();
			redUrl = StringUtils.formatSinaUrl(redUrl);
			httpResponse = this.doGet(redUrl, headers);
			this.getResponesCookie(httpResponse);
		}
		String result = null;
		try{
			result = this.loginSinaService.getStringFromResponse(httpResponse);
		}catch(Exception e){
			throw new LoginException(e,"F", "Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------doAfterLogin exception! The response data exception!");
		}
		if(result == null){
			throw new LoginException("F", "Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------doAfterLogin exception! The response data is null!");
		}
		Map<String,String> resMap = null;
		try{
			resMap = loginSinaService.processAfterLoginData(result);
		}catch(Exception e){
			throw new LoginException(e,"F", "Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------doAfterLogin exception! The response data exception result=!"+result);
		}
		
		if(resMap.containsKey("islogin")){
			int i = Integer.valueOf(resMap.get("islogin"));
			if(i == 1){
				return true;
			}else{
				return false;
			}
		}else{
			throw new LoginException("F", "Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------doAfterLogin exception! The response data exception result=!"+result);
		}
	}
	/**
	 * 
	 * @param loginResponse
	 * @return
	 * @throws LoginException 
	 */
	public JsonDataBean doRedirect(SinaResponseBean loginResponse) throws LoginException{
		String url = loginResponse.getUrl();
		Map<String,String> headers = loginSinaService.createCommonHeader();
		headers.put("Host", "weibo.com");
		headers.put("Referer", "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.11)");
		this.putCookies(headers,".weibo.com");
		HttpResponse httpResponse = this.doGet(url, headers);
		this.getResponesCookie(httpResponse);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode == 302){
			Header[] hs = httpResponse.getHeaders("Location");
			Header locHeader = hs[0];
			String redUrl = locHeader.getValue();
			httpResponse = this.doGet(redUrl, headers);
			this.getResponesCookie(httpResponse);
		}else{
			throw new LoginException("R", "Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------doRedirect exception! The response code is not 302!");
			
		}
		String responseData = null;
		try{
			responseData = loginSinaService.getStringFromResponse(httpResponse);
		}catch(Exception e){
			throw new LoginException(e,"R",  "Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------doRedirect exception! The response data is null!");
		}
		if(responseData == null){
			throw new LoginException("R",  "Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------doRedirect exception! The response data is null!");
		}
		JsonDataBean redBackData = null;
		try{
			redBackData = loginSinaService.processDoRedirectResponseData(responseData);
		}catch(Exception e){
			throw new LoginException(e,"R",  "Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------doRedirect exception! The response data is null!");
		}
		if(redBackData == null){
			throw new LoginException("R",  "Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------doRedirect exception! The response data is null!");
		}
		return redBackData;
	}
	/**
	 * 登陆过程
	 * @return 
	 * @throws LoginException
	 */
	public SinaResponseBean doLogin(JsonDataBean preLoginData) throws LoginException{

		String url = "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.11)";

		Map<String,String> headers = loginSinaService.createCommonHeader();
		headers.put("Host", "login.sina.com.cn");
		headers.put("Referer", "http://www.weibo.com/");
		headers.put("Cache-Control", "no-cache");
		this.putCookies(headers,new String[]{"login.sina.com.cn",".sina.com.cn"});
		Map<String,String> params = loginSinaService.createLoginParams((JsonDataBean) preLoginData);
		String responseData = null;
		try{
			HttpResponse httpResponse = this.doPost(url, headers, params);
			this.getResponesCookie(httpResponse);
			Header[] cookiesHeaders = httpResponse.getHeaders("Set-Cookie");
			for(int i=0;i<cookiesHeaders.length;i++){
				Header cookieHeader = cookiesHeaders[i];
				System.out.println(cookieHeader.getName()+":"+cookieHeader.getValue());
				String value = cookieHeader.getValue();
				String key = value.split("=", 2)[0];
				value = value.split(";")[0];
				sinaHttpClient.putImportantCookies(key, value);
			}
			responseData = loginSinaService.getStringFromResponse(httpResponse);
		}catch(Exception e){
			throw new LoginException("C","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------DoLogin exception! Please check the network!");
		}
		if(responseData == null){
			throw new LoginException("C","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------DoLogin exception! The response data is null!");
		}
		try{
			return loginSinaService.processLoginData(responseData);
		}catch(Exception e){
			throw new LoginException("C","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------DoLogin exception! The response data is error!");
		}
	}
	
	/**
	 * 访问新浪微博首页，目的是测试网络以及获取cookie
	 * @throws LoginException 
	 */
	public void visitIndex() throws LoginException{
		/*访问2次weibo.com,第一次主要是获取cookies,第二次携带cookies再访问一次*/
		String hostUrl = PropertiesUtils.getProperties("hostUrl");
		Map<String,String> headers = this.loginSinaService.createCommonHeader();
		headers.put("Host", "weibo.com");
		headers.put("Referer", "http://weibo.com/");
		HttpResponse httpResponse = this.doGet(hostUrl, headers);
		Header[] cookiesHeaders = httpResponse.getHeaders("Set-Cookie");
		for(int i=0;i<cookiesHeaders.length;i++){
			Header cookieHeader = cookiesHeaders[i];
			System.out.println(cookieHeader.getName()+":"+cookieHeader.getValue());
			String value = cookieHeader.getValue();
			if(value.startsWith("login_sid_t")){
				this.sinaHttpClient.putImportantCookies("login_sid_t", value.split(";")[0]);
			}
			if(value.startsWith("UUG")){
				this.sinaHttpClient.putImportantCookies("UUG", value.split(";")[0]);
			}
		}
		int statusCode = -1;
		statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode!=200){
			throw new LoginException("P","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------PreLogin exception! Please check the network!statusCode="+statusCode);
		}
		this.getResponesCookie(httpResponse);
		/*放入cookies*/
		/*this.putImportantCookies(headers);
		
		httpResponse = this.doGet(hostUrl, headers);
		statusCode = -1;
		statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode!=200){
			throw new LoginException("P","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------PreLogin exception! Please check the network!statusCode="+statusCode);
		}
		this.getResponesCookie(httpResponse);*/
	}
	/**
	 * 预登陆
	 * @throws LoginException 
	 */
	public JsonDataBean doPreLogin() throws LoginException{
		
		/*需要访问2次preloginUrl*/
		
		/*第一次*/
		String preLoginUrl = PropertiesUtils.getProperties("preloginUrl");
		Date currDate = new Date();
		preLoginUrl = preLoginUrl.replaceAll("\\{newDate\\}", currDate.getTime()+"");
		Map<String,String> headers = this.loginSinaService.createCommonHeader();
		headers.put("Host", "login.sina.com.cn");
		headers.put("Referer", "http://www.weibo.com/");
		String content = null;
		HttpResponse httpResponse = null;
		JsonDataBean firstReslutBean = null;
		int code = -1;
		try{
			httpResponse = this.doGet(preLoginUrl, headers);
			this.getResponesCookie(httpResponse);
			content = loginSinaService.getStringFromResponse(httpResponse);
		}catch(Exception e){
			throw new LoginException("P","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------PreLogin exception! Please check the network!");
		}
		if(content == null){
			throw new LoginException("P","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------PreLogin exception! The response data is null!");
		}
		try {
			firstReslutBean = (JsonDataBean) loginSinaService.processPreloginData(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new LoginException("P","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------PreLogin exception! The response data is error!");
		}
		/*第二次*/
		//http://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&su=MTg4MTE0NDU4MTM%3D&rsakt=mod&checkpin=1&client=ssologin.js(v1.4.11)&_=1384150951276
		preLoginUrl = "http://login.sina.com.cn/sso/prelogin.php?"
				+ "entry=weibo&callback=sinaSSOController.preloginCallBack&"
				+ "su={userCode}&rsakt=mod&checkpin=1&"
				+ "client=ssologin.js(v1.4.11)&_={newDate}";
		currDate = new Date();
		preLoginUrl = preLoginUrl.replaceAll("\\{newDate\\}", currDate.getTime()+"")
				.replaceAll("\\{userCode\\}", account.getAccountCode());
		headers = this.loginSinaService.createCommonHeader();
		headers.put("Host", "login.sina.com.cn");
		headers.put("Referer", "http://weibo.com/");
		this.putCookies(headers,new String[]{"login.sina.com.cn",".sina.com.cn"});
		JsonDataBean secondResultBean = null;
		try{
			httpResponse = this.doGet(preLoginUrl, headers);
			this.getResponesCookie(httpResponse);
			content = loginSinaService.getStringFromResponse(httpResponse);
		}catch(Exception e){
			throw new LoginException("P","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------PreLogin exception! Please check the network!");
		}
		if(content == null){
			throw new LoginException("P","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------PreLogin exception! The response data is null!");
		}
		try {
			return secondResultBean = (JsonDataBean) loginSinaService.processPreloginData(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new LoginException("P","Exception info at time:"+StringUtils.getFormatCurrDate()+
					"------PreLogin exception! The response data is error!");
		}
	}
}
