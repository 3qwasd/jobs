/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc;

/**
 * @author QiaoJian
 *
 */
public abstract class CollectTask extends StudentInfoCollectTask {
	public int startIndex = 0;
	/**
	 * 
	 */
	public CollectTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public CollectTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		addMsg("start collect "+this.getTaskName()+" student infos!");
		init();
		for(int i=startIndex;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect "+lastName+" student infos!");
			collect(lastName);
			addMsg("finished collect "+lastName+" student infos!");
		}
		addMsg("finished collect "+this.getTaskName()+" student infos!");
	}
	public abstract void collect(String lastName);
	public abstract void init();
	public void resetConnectionWithSSL(String keyStroe){
		httpManager.clearPool();
		httpManager.createHttpConnection(keyStroe, "123456");
		connectioner = httpManager.getFirstHttpConnection();
	}
}
