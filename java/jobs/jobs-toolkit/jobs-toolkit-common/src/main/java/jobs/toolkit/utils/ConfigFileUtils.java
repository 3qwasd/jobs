package jobs.toolkit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 
 * @author jobs
 *
 */
public class ConfigFileUtils {

	/**
	 * 注意directory是相对目录，该方法会先在应用的jar文件所在的目录下的directory目录下寻找配置文件, 如果没有则会在类路径下寻找
	 * @param directory 当前应用所在jar的相对目录
	 * @param configFileName
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static InputStream loadConfigFile(String directory, String configFileName) throws Exception{
		InputStream inputStream = null;
		File file = new File(directory + File.separator + configFileName);
		if(!file.exists()){
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName);
		}else{
			inputStream = new FileInputStream(file);

		}
		return inputStream;
	}
	/**
	 * 该方法会先在应用的jar文件所在的目录下寻找配置文件, 如果没有则会在类路径下寻找
	 * 使用该方法可以将配置文件与可执行jar同一目录，或者放置为类路径的根目录
	 * @param configFileName
	 * @return
	 * @throws Throwable 
	 */
	public static InputStream loadConfigFile(String configFileName) throws Exception{
		return loadConfigFile(".", configFileName);
	}
}
