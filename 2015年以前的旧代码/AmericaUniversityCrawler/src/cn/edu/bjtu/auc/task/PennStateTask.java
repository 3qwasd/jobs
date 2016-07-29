/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.task.service.PennStateService;

/**
 * @author QiaoJian
 *
 */
public class PennStateTask extends CollectTask {
	PennStateService service;
	/**
	 * 
	 */
	public PennStateTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public PennStateTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#collect(java.lang.String)
	 */
	@Override
	public void collect(String lastName) {
		// TODO Auto-generated method stub
		String url = "http://www.psu.edu/search/people/"+lastName;
		doGetVistor(url);
		infos = service.collecte(lastName);
		writeToExcel(lastName);
		String nextPageUrl = null;
		while((nextPageUrl = service.getNextPageUrl())!=null){
			doGetVistor(nextPageUrl);
			infos = service.collecte(lastName);
			writeToExcel(lastName);
			nextPageUrl = null;
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		service = new PennStateService();
		baseService = service;
		initConnectioner();
		commoneHeader.put("Host", "www.psu.edu");
	}

}
