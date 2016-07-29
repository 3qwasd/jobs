/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.FloridaService;

/**
 * @author QiaoJian
 *
 */
public class FloridaTask extends StudentInfoCollectTask {
	
	FloridaService service;
	
	
	public FloridaTask() {
		super();
		// TODO Auto-generated constructor stub
	}


	public FloridaTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = new FloridaService();
		baseService = service;
		addMsg("start collect "+this.getTaskName()+" student infos!");
		
		resetConnectionWithSSL();
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("Host", "directory.ufl.edu");
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect "+lastName+" student infos!");
			String url = "https://directory.ufl.edu/?query="+lastName;
			doGetVistor(url);
			url = service.getUrl(lastName);
			doGetVistor(url);
			infos = service.collecteFromJson(lastName,html);
			writeToExcel(lastName);
			addMsg("finished collect "+lastName +" student infos!");
		}
		addMsg("finished collect "+this.getTaskName()+" student infos!");
	}
	public void resetConnectionWithSSL(){
		httpManager.clearPool();
		httpManager.createHttpConnection("I:\\cers\\Florida.keystore", "123456");
		connectioner = httpManager.getFirstHttpConnection();
	}
}
