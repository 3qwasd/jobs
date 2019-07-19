/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.RutgersService;

/**
 * @author QiaoJian
 *
 */
public class RutgersTask extends StudentInfoCollectTask{

	RutgersService service;

	public RutgersTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RutgersTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = new RutgersService();
		baseService = service;
		initConnectioner();
		//initConnectioner();
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("Host", "search.rutgers.edu");
		addMsg("start collect "+this.getTaskName()+" student infos!");
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect "+lastName+" student infos!");


			String url = "http://search.rutgers.edu/people.html?q="+lastName;
			this.doGetVistor(url);
			List<String> links = service.collecteLinks(lastName);
			if(links!=null&&links.size()>0){
				infos = new ArrayList<StudentInfo>();
				//resetConnection();
				for(String link:links){

					this.doGetVistor(link);
					StudentInfo info = service.collectInfo();
					if(info!=null){
						infos.add(info);
					}
				}
				writeToExcel(lastName);
			}


			addMsg("finished collect "+lastName +" student infos!");
		}
		addMsg("finished collect "+this.getTaskName()+" student infos!");
	}

	public void resetConnectionWithSSL(){
		httpManager.clearPool();
		httpManager.createHttpConnection("I:\\cers\\Rutgersnew.keystore", "123456");
		connectioner = httpManager.getFirstHttpConnection();
	}
	public void resetConnection(){
		httpManager.clearPool();
		httpManager.createHttpConnection();
		connectioner = httpManager.getFirstHttpConnection();
	}
}
