/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.DrexelService;

/**
 * @author QiaoJian
 *
 */
public class DrexelTask extends StudentInfoCollectTask {
	
	DrexelService service;
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public DrexelTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrexelTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = new DrexelService();
		baseService = this.service;
		initConnectioner();
		commoneHeader.put("Host", "www.drexel.edu");
		addMsg("start collect "+this.getTaskName()+" infos !");
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect "+lastName+" infos !");
			
			String url = service.getUrl(lastName);
			
			this.doGetVistor(url);
			infos = service.collecte(lastName);
			writeToExcel(lastName);
			addMsg("finished collect "+lastName+" infos !");
		}
		addMsg("finshed collect "+this.getTaskName()+" infos !");
	}

	

}
