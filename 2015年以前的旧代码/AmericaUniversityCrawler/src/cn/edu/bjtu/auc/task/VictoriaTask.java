/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.VictoriaService;

/**
 * @author QiaoJian
 *
 */
public class VictoriaTask extends StudentInfoCollectTask {
	
	VictoriaService service;
	public VictoriaTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VictoriaTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = new VictoriaService();
		baseService = service;
		addMsg("start collect "+this.getTaskName()+" infos !");
		httpManager.clearPool();
		httpManager.createHttpConnection("218.18.208.162", 9000);
		connectioner = httpManager.getFirstHttpConnection();
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("Host", "www.uvic.ca");
		for(int i=12;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect "+lastName+" infos !");
			String url = service.getUrl(lastName);
			this.doGetVistor(url);
			infos = service.collecte(lastName);
			writeToExcel(lastName);
			addMsg("finished collect "+lastName+" infos !");
			httpManager.clearPool();
			httpManager.createHttpConnection("222.20.74.24", 18186);
			connectioner = httpManager.getFirstHttpConnection();
		}
		addMsg("finished collect "+this.getTaskName()+" infos !");
		httpManager.giveBackHttpClient(connectioner);
	}

}
