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
 *
 */
public class ConsoleJoeThreadLogger implements JoeThreadLogger {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger#recordTaskLog(cn.edu.bjtu.joe.system.thread.component.JoeBaseTask, cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger.TaskLogType)
	 */
	@Override
	public void recordTaskLog(JoeBaseTask task, TaskLogType type) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger#recordThreadLog(cn.edu.bjtu.joe.system.thread.component.JoeTaskThread, cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger.ThreadLogType)
	 */
	@Override
	public void recordThreadLog(JoeTaskThread thread, ThreadLogType type) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger#recordThreadFac(cn.edu.bjtu.joe.system.thread.component.JoeTaskThreadFactory, cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger.ThreadFacLogType)
	 */
	@Override
	public void recordThreadFac(JoeTaskThreadFactory joeTaskThreadFactory,
			ThreadFacLogType type) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger#recordThreadPoolLog(cn.edu.bjtu.joe.system.thread.component.JoeThreadPoolExecutor, cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger.ThreadPoolLogType)
	 */
	@Override
	public void recordThreadPoolLog(JoeThreadPoolExecutor executor,
			ThreadPoolLogType type) {
		// TODO Auto-generated method stub
		
	}

}
