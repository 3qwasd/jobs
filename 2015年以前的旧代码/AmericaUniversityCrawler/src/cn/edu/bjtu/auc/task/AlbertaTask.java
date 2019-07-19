/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.AlbertaService;

/**
 * @author QiaoJian
 *
 */
public class AlbertaTask extends StudentInfoCollectTask {
	
	AlbertaService service;
	public AlbertaTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AlbertaTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		httpManager.clearPool();
		httpManager.createHttpConnection("222.20.74.24", 18186);
		connectioner = httpManager.getFirstHttpConnection();
		commoneHeader = connectioner.createCommonHeader();
		service = new AlbertaService();
		baseService = service;
		// TODO Auto-generated method stub
		params = service.getParams();
		commoneHeader.put("Host", "webapps.srv.ualberta.ca");
		addMsg("start collect "+this.getTaskName()+" infos !");
		
		for(int i=66;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			params.remove("sn");
			params.put("sn", lastName);
			String url = "http://webapps.srv.ualberta.ca/search/?advanced";
			addMsg("start collect lastName = "+lastName);
			
			this.doPostVistor(url);
			List<String> links = service.getUserLinks(lastName);
			if(links!=null&&links.size()>0){
				infos = new ArrayList<StudentInfo>();
				for(String link:links){
					this.doGetVistor(link);
					StudentInfo info = service.getStudentInfo();
					if(info!=null)
						infos.add(info);
				}
				writeToExcel(lastName);
			}
			addMsg("finished collect lastName = "+lastName);
			httpManager.clearPool();
			httpManager.createHttpConnection("222.20.74.24", 18186);
			connectioner = httpManager.getFirstHttpConnection();
		}
		
		addMsg("finished collect "+this.getTaskName()+" infos !");
		httpManager.giveBackHttpClient(connectioner);
	}

}
