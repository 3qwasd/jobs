/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author QiaoJian
 * 用于输入输出的工具类
 */
public class IOUtils {
	
	/**
	 * 获取当前项目的类路径
	 * @return
	 */
	public static String getClassPath(){
		
		String classPath = IOUtils.class.getClassLoader().getResource("").getPath();
		return classPath;
	}
	/**
	 * 获取文件的输入流
	 * @return
	 * @throws Exception 
	 */
	public static InputStream getFileInputStream(String path) throws Exception{
		File file = new File(path);
		return new FileInputStream(file);
	}
}
