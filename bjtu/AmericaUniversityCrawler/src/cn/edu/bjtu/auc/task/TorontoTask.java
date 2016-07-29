/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.UniversityTask;

/**
 * @author QiaoJian
 *
 */
public class TorontoTask extends UniversityTask {
	
	
	public TorontoTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public TorontoTask(String taskName) {
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
		
	}

}
