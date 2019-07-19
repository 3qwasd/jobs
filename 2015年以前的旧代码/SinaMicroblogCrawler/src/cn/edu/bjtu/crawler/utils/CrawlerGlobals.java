/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cn.edu.bjtu.crawler.bean.sina.LoginUserAccount;
import cn.edu.bjtu.crawler.bean.sina.ProxyHost;

/**
 * @author QiaoJian
 *	全局的配置参数
 */
public class CrawlerGlobals {
	
	private static List<LoginUserAccount> loginUsers = new ArrayList<>();
	
	private static  List<ProxyHost> proxyHosts = new ArrayList<>(); 
	
	/**
	 * 从properites文件中载入登陆账户
	 */
	public synchronized static void initLoginUsers(){
		loginUsers.clear();
		Properties properties = new Properties();
		try {
			InputStream inputStream = IOUtils.getFileInputStream(IOUtils.getClassPath()+"loginUsers.properties");
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Object key:properties.keySet()){
			String name = key+"";
			if(!name.startsWith("url_")&&!name.startsWith("code_")){
				LoginUserAccount account = new LoginUserAccount();
				account.setUserName(key+"");
				account.setPassword(properties.getProperty((String) key));
				String indexUrl = properties.getProperty("url_"+name);
				if(indexUrl!=null&&indexUrl.length()>0){
					account.setIndexUrl(indexUrl);
				}
				String accountCode = properties.getProperty("code_"+name);
				if(accountCode!=null&&accountCode.length()>0){
					account.setAccountCode(accountCode);
				}
				loginUsers.add(account);
			}
		}
	}
	/**
	 * 从properites文件中载入代理IP
	 */
	public synchronized static void initProxyHost(){
		Properties properties = new Properties();
		try {
			InputStream inputStream = IOUtils.getFileInputStream(IOUtils.getClassPath()+"proxyIp.properties");
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Object key:properties.keySet()){
			ProxyHost proxyHost = new ProxyHost();
			proxyHost.setIp(key+"");
			proxyHost.setPort(Integer.valueOf(properties.getProperty(key+"")));
			proxyHosts.add(proxyHost);
		}
	}
	/**
	 * 获取一个登录账号
	 * @return
	 */
	public synchronized static LoginUserAccount getLoginUser(){
		if(loginUsers.size()<1){
			initLoginUsers();
		}
		return loginUsers.remove(0);
	}
	/**
	 * 返回一个sina登陆用户
	 * @param account
	 */
	public synchronized static void giveBackLoginUser(LoginUserAccount account){
		if(!loginUsers.contains(account)){
			loginUsers.add(account);
		}
	}
	/**
	 * 获取一个代理ip地址
	 * @return
	 */
	public synchronized static ProxyHost getProxyHost(){
		if(proxyHosts.size()<1){
			initProxyHost();
		}
		return proxyHosts.remove(0);
	}
}
