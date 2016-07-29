/**
 * @QiaoJian
 */
package cn.edu.bjtu.joe.system.utils;

/**
 * @author QiaoJian
 *
 */
public class SystemUtils {
	
	/**
	 * 获取当前系统可用的处理器数量
	 * @return
	 */
	public static int getSystemProcessorNumber(){
		return Runtime.getRuntime().availableProcessors();
	}
}
