/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.CaseWesternService;
import cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger.TaskLogType;

/**
 * @author QiaoJian
 *
 */
public class CaseWesternTask extends StudentInfoCollectTask{
	
	CaseWesternService service;
	/**
	 * @param taskName
	 */
	public CaseWesternTask() {
		super();
		// TODO Auto-generated constructor stub
		service = new CaseWesternService();
		this.baseService = service;
	}
	/**
	 * @param taskName
	 */
	public CaseWesternTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
		service = new CaseWesternService();
		this.baseService = service;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		initConnectioner();
		this.addMsg("start collect"+this.getTaskName()+" info");
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			this.addMsg("start collect lastName="+lastName+" info");
			String url = service.getQueryUrl(lastName);
			this.doGetVistor(url);
			infos = service.collecte(lastName);
			writeToExcel(lastName);
			this.addMsg("finished collect lastName="+lastName+" info");
		}
		
		
		this.addMsg("finished collect"+this.getTaskName()+" info");
	}

}
