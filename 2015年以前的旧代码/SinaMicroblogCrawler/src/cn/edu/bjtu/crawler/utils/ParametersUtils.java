/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author QiaoJian
 *用于对请求参数进行处理的接口
 */
public abstract class ParametersUtils {
	
	
	/**
	 * 对参数进行字符集编码
	 * @param parameter
	 * @param charSet
	 * @return
	 */
	public String encodingParameter(String parameter, String charSet) {
		// TODO Auto-generated method stub
		try {
			return URLEncoder.encode(parameter, charSet);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 生成一定长度的随机字符创
	 * @param length
	 * @return
	 */
	public  String getRandomString(int length){
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String randomStr = "";
		for(int i=0;i<length;i++){
			randomStr += letters.charAt((int)Math.ceil(Math.random() * 1000000) % letters.length());
		}
		return randomStr;
	}
}
