/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.utils;

/**
 * @author QiaoJian
 * {@link HttpPropertiesUtils}
 */
public class HttpPropertiesUtils extends PropertiesUtils{
	
	public static String getAccept(){
		return properties.getProperty("Accept");
	}
	public static String getAcceptLanguage(){
		return properties.getProperty("Accept-Language");
	}
	public static String getUserAgent(){
		return properties.getProperty("User-Agent");
	}
	public static String getContentType(){
		return properties.getProperty("Content-Type");
	}
	public static String getConnection(){
		return properties.getProperty("Connection");
	}
	public static String getCacheControl(){
		return properties.getProperty("Cache-Control");
	}
	public static String getAcceptEncoding() {
		return properties.getProperty("Accept-Encoding");
	}
}
