/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author QiaoJian
 *	properties工具
 */
public class PropertiesUtils extends ParametersUtils{
	
	static Properties properties;
	
	/**
	 * 载入properties文件
	 */
	static{
		properties = new Properties();
		try {
			InputStream inputStream = IOUtils.getFileInputStream(IOUtils.getClassPath()+"properties.properties");
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取properity的值
	 * @param key
	 * @return
	 */
	public static String getProperties(String key){
		return properties.getProperty(key);
	}
	/**
	 * 写入properties值
	 * @param key
	 * @return
	 */
	public static void setProperties(String key,String value){
		properties.setProperty(key, value);
		 OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(IOUtils.getClassPath()+"properties.properties");
			properties.setProperty(key, value);
			properties.store(outputStream, "update");
			outputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
