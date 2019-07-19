/**
 * @QiaoJian
 */
package cn.edu.bjtu.joe.system.thread.log;

import cn.edu.bjtu.joe.system.thread.component.JoeBaseTask;
import cn.edu.bjtu.joe.system.thread.component.JoeTaskThread;
import cn.edu.bjtu.joe.system.thread.component.JoeTaskThreadFactory;
import cn.edu.bjtu.joe.system.thread.component.JoeThreadPoolExecutor;

/**
 * @author QiaoJian
 *	本系统的日志接口
 */
public interface JoeThreadLogger {
	
	/*枚举任务日志类型*/
	public enum TaskLogType{
		CREATE,SUBMIT,START,COMPLETE,ERROR,SUCCESS
	}
	/*线程日志类型*/
	public enum ThreadLogType{
		CREATE,DESROTY,EXECUTE_TASK,ERROR
	}
	/*线程工厂日志类型*/
	public enum ThreadFacLogType{
		CREATE_THREAD
	}
	/*线程池日志*/
	public enum ThreadPoolLogType{
		CREATE,TERMINATE

	}
	/*记录任务日志*/
	public void recordTaskLog(JoeBaseTask task,TaskLogType type);
	/*记录线程日志*/
	public void recordThreadLog(JoeTaskThread thread,ThreadLogType type);
	/*记录线程工厂日志*/
	public void recordThreadFac(JoeTaskThreadFactory joeTaskThreadFactory,ThreadFacLogType type);
	/*记录线程池日志*/
	public void recordThreadPoolLog(JoeThreadPoolExecutor executor,ThreadPoolLogType type);
}
