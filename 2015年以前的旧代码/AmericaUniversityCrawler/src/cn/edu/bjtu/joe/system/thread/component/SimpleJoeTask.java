/**
 * @QiaoJian
 */
package cn.edu.bjtu.joe.system.thread.component;

/**
 * @author QiaoJian
 *
 */
public class SimpleJoeTask extends JoeBaseTask {

	/**
	 * @param taskName
	 */
	public SimpleJoeTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(this.getTaskName()+"run");
	}

}
