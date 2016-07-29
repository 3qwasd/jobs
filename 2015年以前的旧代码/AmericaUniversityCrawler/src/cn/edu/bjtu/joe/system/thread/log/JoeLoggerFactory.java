/**
 * @QiaoJian
 */
package cn.edu.bjtu.joe.system.thread.log;

/**
 * @author QiaoJian
 *日志工厂
 */
public class JoeLoggerFactory {
	
	
	
	public static JoeThreadLogger getJoeThreadLogger(){
		
		JoeThreadLogger joeLogger = new ConsoleJoeThreadLogger();
		
		return joeLogger;
	}
}
