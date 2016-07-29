/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.task.service.MichiganService;

/**
 * @author QiaoJian
 *
 */
public class MichiganTask extends CollectTask {
	MichiganService service;
	/**
	 * 
	 */
	public MichiganTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public MichiganTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#collect(java.lang.String)
	 */
	@Override
	public void collect(String lastName) {
		// TODO Auto-generated method stub
		String url = service.getUrl(lastName);
		doGetVistor(url);
		infos = service.collecte(lastName);
		this.addMsg("start collect detail");
		if(infos!=null&&infos.size()>0){
			for(StudentInfo info:infos){
				String link = info.getInfo("link");
				this.doGetVistor(link);
				service.collectInfo(info);
			}
			this.addMsg("finished collect detail");
			writeToExcel(lastName);
		}
		resetConnectionWithSSL("I:\\cers\\Michigan,keystore");
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		service = new MichiganService();
		baseService = service;
		resetConnectionWithSSL("I:\\cers\\Michigan,keystore");
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("Host", "search.msu.edu");
		startIndex = 294;
	}

}
