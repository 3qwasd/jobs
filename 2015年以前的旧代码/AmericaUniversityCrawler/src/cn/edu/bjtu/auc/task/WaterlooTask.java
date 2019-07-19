/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.WaterlooService;

/**
 * @author QiaoJian
 *
 */
public class WaterlooTask extends StudentInfoCollectTask {
	WaterlooService service;
	/**
	 * 
	 */
	public WaterlooTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public WaterlooTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
