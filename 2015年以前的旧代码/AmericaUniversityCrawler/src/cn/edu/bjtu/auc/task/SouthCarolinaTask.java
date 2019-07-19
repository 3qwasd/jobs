/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.SouthCarolinaService;

/**
 * @author QiaoJian
 *
 */
public class SouthCarolinaTask extends StudentInfoCollectTask {
	SouthCarolinaService service;
	/**
	 * 
	 */
	public SouthCarolinaTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public SouthCarolinaTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = new SouthCarolinaService();
		baseService = service;
		resetConnectionWithSSL();
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("Host", "www.sc.edu");
		addMsg("start collect "+this.getTaskName()+" student infos!");
		for(int i=181;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect "+lastName+" student infos!");
			
			for(String firstName:letter){
				String url = service.getUrl(lastName, firstName);
				doGetVistor(url);
				infos = service.collecte(lastName);
				writeToExcel(lastName);
			}
			
			addMsg("finished collect "+lastName +" student infos!");
		}
		addMsg("finished collect "+this.getTaskName()+" student infos!");
	}
	public void resetConnectionWithSSL(){
		httpManager.clearPool();
		httpManager.createHttpConnection("I:\\cers\\SouthCarolina.keystore", "123456");
		connectioner = httpManager.getFirstHttpConnection();
	}
	public void resetConnection(){
		httpManager.clearPool();
		httpManager.createHttpConnection();
		connectioner = httpManager.getFirstHttpConnection();
		
	}
}
