/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.ManitobaService;

/**
 * @author QiaoJian
 *
 */
public class ManitobaTask extends StudentInfoCollectTask {
	
	ManitobaService service;
	public ManitobaTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ManitobaTask(String taskName) {
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
		service = new ManitobaService();
		baseService = service;
		commoneHeader.put("Host", "umanitoba.ca");
		addMsg("start collect "+this.getTaskName()+" infos!");
		for(int i=95;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect "+lastName+" infos!");
			String url = service.getUrl(lastName);
			this.doGetVistor(url);
			List<String> links = service.collecteLinks(lastName);
			if(links!=null&&links.size()>0){
				infos = new ArrayList<StudentInfo>();
				for(String link:links){
					this.doGetVistor(link);
					StudentInfo info = service.getStudentInfo();
					if(info!=null)
						infos.add(info);
				}
				this.writeToExcel(lastName);
			}
			
			addMsg("finished collect "+lastName+" infos!");
		}
		addMsg("finished collect "+this.getTaskName()+" infos!");
		httpManager.giveBackHttpClient(connectioner);
	}

}
