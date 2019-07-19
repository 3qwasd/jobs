/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.task.service.IowaService;

/**
 * @author QiaoJian
 *
 */
public class IowaTask extends CollectTask {
	IowaService service;
	/**
	 * 
	 */
	public IowaTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public IowaTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#collect(java.lang.String)
	 */
	@Override
	public void collect(String lastName) {
		// TODO Auto-generated method stub
		String url = "http://dnaapps.uiowa.edu/PublicDirectory/";
		this.doGetVistor(url);
		this.params = service.getParams(lastName);
		this.doPostVistor(url);
		infos = service.collecte(lastName);
		if(infos!=null&&infos.size()>0){
			this.addMsg("start collect detail");
			for(StudentInfo info:infos){
				String link = info.getInfo("link");
				this.doGetVistor(link);
				service.collecteInfo(info);
			}
			this.addMsg("finish collect detail");
		}
		writeToExcel(lastName);
		this.initConnectioner();
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		initConnectioner();
		service = new IowaService();
		baseService = service;
		this.startIndex = 5;
		this.commoneHeader.put("Host", "dnaapps.uiowa.edu");
		
	}

}
