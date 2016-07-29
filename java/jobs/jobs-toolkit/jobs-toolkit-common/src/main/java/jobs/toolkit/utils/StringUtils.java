package jobs.toolkit.utils;

/**
 * 字符串工具集
 * @author jobs
 *
 */
public class StringUtils {
	
	/**
	 * 字符串转为整数
	 * @param value
	 * @param defaultVal
	 * @return
	 */
	public static int convertToInt(String value, int defaultVal){
		String trim = value.trim();
		if (trim.length() == 0) {
			return defaultVal;
		}
		return Integer.parseInt(trim);
	}

}
