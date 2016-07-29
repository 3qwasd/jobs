/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.utils;

/**
 * @author QiaoJian
 * 需要对访问url时的请求参数进行编码时需要实现该接口
 */
public interface ParametersSecretEncoding {
	
	/**
	 * 对用户名进行编码
	 * @param userName
	 * @return
	 */
	public String secretEncodingUserName(String userName);
	/**
	 * 对参数进行编码
	 * @param paramStr
	 * @return
	 */
	public String secretEncodingParameters(String paramStr);
	/**
	 * 对密码进行编码
	 * @param password
	 * @return
	 */
	public String secretEncodingPassword(String password);
	/**
	 * 对url进行编码
	 * @param password
	 * @return
	 */
	public String secretEncodingUrl(String url);
}
