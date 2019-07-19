/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.WilliamsService;

/**
 * @author QiaoJian
 *
 */
public class WilliamsTask extends StudentInfoCollectTask {
	
	WilliamsService service;
	public WilliamsTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WilliamsTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		addMsg("start collect "+this.getTaskName()+" infos !");
		initConnectioner();
		service = new WilliamsService();
		baseService = service;
		commoneHeader.put("Host", "www.williams.edu");
		commoneHeader.put("Referer", "http://www.williams.edu/people/");
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect "+lastName+" infos !");
			String url = service.getUrl(lastName);
			doGetVistor(url);
			infos = service.collecte(lastName);
			this.writeToExcel(lastName);
			addMsg("finished collect "+lastName+" infos !");
		}
		addMsg("finished collect "+this.getTaskName()+" infos !");
		httpManager.giveBackHttpClient(connectioner);
	}

}
