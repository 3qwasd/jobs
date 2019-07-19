/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.UcDavisService;

/**
 * @author QiaoJian
 *
 */
public class UcDavisTask extends StudentInfoCollectTask {
	
	UcDavisService service;
	
	public UcDavisTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UcDavisTask(String taskName) {
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
		commoneHeader.put("Host", "www.ucdavis.edu");
		service = new UcDavisService();
		baseService = service;
		addMsg("start collect "+this.getTaskName()+" infos !");
		for(int i=237;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect lastName="+lastName+" infos !");
			String url = service.getUrl(lastName);
			this.doGetVistor(url);
			infos = service.collecte(lastName);
			this.writeToExcel(lastName);
			addMsg("finished collect lastName="+lastName+" infos !");
		}
		addMsg("finished collect "+this.getTaskName()+" infos !");
		
		httpManager.giveBackHttpClient(connectioner);
	}

}
