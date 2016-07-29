/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.network.sina;

import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.crawler.bean.HttpDataBean;
import cn.edu.bjtu.crawler.bean.sina.JsonDataBean;
import cn.edu.bjtu.crawler.bean.sina.LoginUserAccount;
import cn.edu.bjtu.crawler.bean.sina.SinaResponseBean;
import cn.edu.bjtu.crawler.network.HttpConnectioner;
import cn.edu.bjtu.crawler.network.HttpLogin;
import cn.edu.bjtu.crawler.servicer.sina.LoginSinaService;
import cn.edu.bjtu.crawler.utils.StringUtils;


/**
 * @author QiaoJian
 *
 */
@Deprecated
public class SinaHttpLoginManager extends HttpConnectioner implements HttpLogin{

	private LoginSinaService loginSinaService;

	

	private LoginUserAccount account;
	/**
	 * 
	 */
	public SinaHttpLoginManager(LoginUserAccount account) {
		super();
		this.account = account;
		loginSinaService = new LoginSinaService(account);
		// TODO Auto-generated constructor stub
	}
	public SinaHttpLoginManager() {
		super();
		loginSinaService = new LoginSinaService();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 登陆微博
	 */
	public HttpDataBean loginSinaMicroblog(){
		/*模拟微博登陆过程*/
		HttpDataBean preLoginBean = this.preLogin();
		HttpDataBean loginResponse = this.doLogin(preLoginBean);
		this.doRedirect(loginResponse.getUrl());
		String url = "http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack&sudaref=weibo.com";
		loginResponse.setUrl(url);
		JsonDataBean afterLoginBean = (JsonDataBean) this.afterLogin(loginResponse);
		JsonDataBean userInfo = (JsonDataBean) afterLoginBean.get("userinfo");
		url = "http://weibo.com/"+userInfo.get("userdomain");
		this.doRedirect(url);
		url = "http://weibo.com/u/"+userInfo.get("uniqueid")+"/home"+userInfo.get("userdomain");
		afterLoginBean.setUrl(url);
		return afterLoginBean;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.crawler.network.HttpLogin#preLogin()
	 */
	@Override
	public HttpDataBean preLogin() {
		String url = loginSinaService.compositePreLoginUrl();
		Map<String,String> headers= loginSinaService.createPreLoginHeader();
		HttpResponse httpResponse = this.doGet(url, headers);
		this.getResponesCookie(httpResponse);
		return loginSinaService.processPreloginResponse(httpResponse);
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.crawler.network.HttpLogin#doLogin()
	 */
	@Override
	public HttpDataBean doLogin(HttpDataBean preLoginData) {

		String url = "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.11)";

		Map<String,String> headers = loginSinaService.createLoginHeader();
		Map<String,String> params = loginSinaService.createLoginParams((JsonDataBean) preLoginData);

		HttpResponse httpResponse = this.doPost(url, headers, params);
		this.getResponesCookie(httpResponse);
		SinaResponseBean responseBean = (SinaResponseBean) loginSinaService.processLoginResponse(httpResponse);
		return responseBean;
	}
	public void doRedirect(String url){

		Map<String,String> headers = loginSinaService.createCommonHeader();
//		if(this.cookies!=null&&cookies.size()>0){
//			String cookieStr = StringUtils.cookies2String(HttpDataBean.cookies);
//			headers.put("Cookie", cookieStr);
//		}
		headers.put("Host", "weibo.com");
		headers.put("Referer", "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.11)");
		HttpResponse httpResponse = this.doGet(url, headers);
		this.getResponesCookie(httpResponse);
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.crawler.network.HttpLogin#afterLogin()
	 */
	@Override
	public HttpDataBean afterLogin(HttpDataBean responseBean) {
		String url = responseBean.getUrl();

		Map<String,String> headers = loginSinaService.createCommonHeader();
		headers.put("Host", "weibo.com");
		headers.put("Referer", "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.11)");
//		if(this.cookies!=null&&cookies.size()>0){
//			String cookieStr = StringUtils.cookies2String(HttpDataBean.cookies);
//			headers.put("Cookie", cookieStr);
//		}
		HttpResponse httpResponse = this.doGet(url, headers);
		this.getResponesCookie(httpResponse);
		return loginSinaService.processAfterLoginResponse(httpResponse);
	}	
}
