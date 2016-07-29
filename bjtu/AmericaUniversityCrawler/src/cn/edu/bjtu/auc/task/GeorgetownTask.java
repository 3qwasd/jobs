/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.GeorgetownService;

/**
 * @author QiaoJian
 *
 */
public class GeorgetownTask extends StudentInfoCollectTask {
	
	GeorgetownService service;
	public GeorgetownTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GeorgetownTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		initConnectioner();
		service = new GeorgetownService();
		baseService = service;
	}

}
