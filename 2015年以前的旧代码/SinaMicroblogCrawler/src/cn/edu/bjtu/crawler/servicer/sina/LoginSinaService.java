/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.servicer.sina;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.crawler.bean.HttpDataBean;
import cn.edu.bjtu.crawler.bean.sina.JsonDataBean;
import cn.edu.bjtu.crawler.bean.sina.LoginUserAccount;
import cn.edu.bjtu.crawler.bean.sina.SinaResponseBean;
import cn.edu.bjtu.crawler.parser.sinadata.SinaDataParser;
import cn.edu.bjtu.crawler.utils.PropertiesUtils;
import cn.edu.bjtu.crawler.utils.sina.SinaParamtersUtils;
import cn.edu.bjtu.crawler.utils.sina.SinaPasswordEncoder;

/**
 * @author QiaoJian
 * 处理登陆新浪微博的业务类
 */
public class LoginSinaService extends BaseSinaService {
	
	LoginUserAccount account;
	
	private String password;
	
	private String username;
	
	public LoginSinaService() {
		super();
		this.sinaDataParser = new SinaDataParser();
		if(account == null){
			password = PropertiesUtils.getProperties("password");
			username = PropertiesUtils.getProperties("username");
		}
	}
	public LoginSinaService(LoginUserAccount account) {
		super();
		this.sinaDataParser = new SinaDataParser();
		this.account = account;
		password = account.getPassword();
		username = account.getUserName();
	}
	/**
	 * 组合预登陆的url
	 * @return
	 */
	@Deprecated
	public String compositePreLoginUrl(){
		StringBuilder builder=new StringBuilder();
		builder.append("http://login.sina.com.cn/sso/prelogin.php?")
		   .append("entry=weibo&callback=sinaSSOController.preloginCallBack")
		   .append("&su=&rsakt=mod&client=ssologin.js(v1.4.11)&")
		   .append("_="+System.currentTimeMillis());
		return builder.toString();
	}
	/**
	 * 创建预登陆的请求头参数
	 * @return
	 */
	@Deprecated
	public Map<String,String> createPreLoginHeader(){
		Map<String,String> headers = this.createCommonHeader();
		headers.put("Referer", "http://www.weibo.com/");
		headers.put("Host", "login.sina.com.cn");
		return headers;
	}
	/**
	 * 创建登陆的请求头参数
	 * @return
	 */
	@Deprecated
	public Map<String,String> createLoginHeader(){
		Map<String,String> headers = this.createCommonHeader();
		headers.put("Referer", "http://weibo.com/");
		headers.put("Host", "login.sina.com.cn");
		return headers;
	}
	/**
	 * 创建登陆的post参数
	 * @return
	 */
	public Map<String,String> createLoginParams(JsonDataBean preLoginData){
		Map<String,String> params = new HashMap<String, String>();
		params.put("encoding", "UTF-8");
		params.put("entry", "weibo");
		params.put("from", "");
		params.put("prelt", "112");
		params.put("gateway", "1");
		params.put("nonce", preLoginData.get("nonce")+"");
		params.put("pwencode", "rsa2");//wsse
		params.put("returntype", "META");
		params.put("pagerefer", "");
		params.put("savestate", "7");
		Date currDate = new Date();
		long serverTime = currDate.getTime()-1383888758000L;
		params.put("servertime",serverTime+"");
		params.put("rsakv", preLoginData.get("rsakv")+"");
		params.put("service", "miniblog");
		params.put("sp", secretPassordEncoding(password,preLoginData));
		params.put("su", uesrNameEncoding(username));
		params.put("url", "http://www.weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack");
		params.put("useticket", "1");
		params.put("vsnf", "1");
		return params;
	}
	/**
	 * 处理预登陆的响应数据，获取登陆所需参数
	 * @param responseData
	 * @return
	 * @throws Exception 
	 */
	public HttpDataBean processPreloginData(String responseData) throws Exception{
		JsonDataBean preLoginData = new JsonDataBean(responseData);
		try{
			sinaDataParser.parsePreLoginData(preLoginData);
		}catch(Exception e){
			throw new Exception();
		}
		return preLoginData;
	}
	/**
	 * 处理预登陆的响应，获取登陆所需参数
	 * @param httpResponse
	 * @return
	 */
	@Deprecated
	public HttpDataBean processPreloginResponse(HttpResponse httpResponse){
		String result = this.getStringFromResponse(httpResponse);
		System.out.println(result);
		JsonDataBean preLoginData = new JsonDataBean(result);
		sinaDataParser = new SinaDataParser();
		sinaDataParser.parsePreLoginData(preLoginData);
		return preLoginData;
	}
	/**
	 * 根据预登陆获取的参数对密码进行编码
	 * @return
	 */
	public String secretPassordEncoding(String password,JsonDataBean preLoginData){
		String message = preLoginData.get("servertime")+"\t"+preLoginData.get("nonce")+"\n"+PropertiesUtils.getProperties("password");
		String pwd = SinaPasswordEncoder.rsaCrypt(preLoginData.get("pubkey")+"", "10001", message);
		return pwd;
	}
	/**
	 * 对用户名进行编码
	 * @param username
	 * @return
	 */
	public String uesrNameEncoding(String username){
		return SinaParamtersUtils.getInstance().secretEncodingUserName(username);
	}
	/**
	 * 登陆成功后抽取重定向的url
	 * @param loginData
	 * @return
	 */
	public SinaResponseBean processLoginData(String responseData){
		SinaResponseBean responseBean = new SinaResponseBean(responseData);
		sinaDataParser.parseLoginRedirectUrl(responseBean);
		return responseBean;
	}
	/**
	 * 对登陆后的响应进行处理
	 * @param httpResponse
	 * @return
	 */
	@Deprecated
	public HttpDataBean processLoginResponse(HttpResponse httpResponse){
		
		String result = this.getStringFromResponse(httpResponse);
		System.out.println(result);
		SinaResponseBean responseBean = new SinaResponseBean(result);
		sinaDataParser.parseLoginRedirectUrl(responseBean);
		
		return responseBean;
	}
	/**
	 * 解析登陆后返回的数据
	 * @param httpResponse
	 * @return
	 */
	public HttpDataBean processAfterLoginResponse(HttpResponse httpResponse){
		
		String result = this.getStringFromResponse(httpResponse);
		System.out.println(result);
		JsonDataBean afterLoginData = new JsonDataBean(result);

		sinaDataParser = new SinaDataParser();
		sinaDataParser.parseAfterLoginData(afterLoginData);
		return afterLoginData;
	}
	/**
	 * 处理转发的返回数据
	 * @param responseData
	 */
	public JsonDataBean processDoRedirectResponseData(String responseData) {
		// TODO Auto-generated method stub
		List<String> infoCollector = new ArrayList<String>();
		sinaDataParser.extractTagDataFromHtml(responseData, "script", infoCollector);
		if(infoCollector.size()>0){
			String script = infoCollector.get(0);
			JsonDataBean jsonDataBean = new JsonDataBean();
			jsonDataBean.setResult(script);
			sinaDataParser.parseJsonBackData(jsonDataBean);
			return jsonDataBean;
		}
		
		return null;
	}
	/**
	 * 处理登陆后返回的的主页数据
	 * @param result
	 */
	public Map<String,String> processAfterLoginData(String result) {
		// TODO Auto-generated method stub
		return this.getSinaConfigInfo(result);
	}
}
