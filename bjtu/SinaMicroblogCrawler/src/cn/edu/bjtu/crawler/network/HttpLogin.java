/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.network;

import cn.edu.bjtu.crawler.bean.HttpDataBean;

/**
 * @author QiaoJian
 * 对于需要登陆的http访问需要实现该接口
 */
public interface HttpLogin {
	
	/**
	 * 登陆前
	 * @return
	 */
	public HttpDataBean preLogin(); 
	
	/**
	 * 登陆
	 * @return
	 */
	public HttpDataBean doLogin(HttpDataBean httpDataBean);
	
	/**
	 * 登陆成功
	 * @return
	 */
	public HttpDataBean afterLogin(HttpDataBean httpDataBean);
}
