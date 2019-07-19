/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.HashMap;
import java.util.Map;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.WesternOntarioService;

/**
 * @author QiaoJian
 *
 */
public class WesternOntarioTask extends StudentInfoCollectTask {
	
	WesternOntarioService service;
	/**
	 * 
	 */
	public WesternOntarioTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public WesternOntarioTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = new WesternOntarioService();
		
		baseService = service;
		initConnectioner();
		commoneHeader.put("Host", "www.uwo.ca");
		addMsg("start collect "+this.getTaskName()+" student infos!");
		for(int i=77;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect "+lastName+" student infos!");
			
			this.params = new HashMap<String, String>();
			params.put("query", lastName);
			params.put("server", "localhost");
			this.doPostVistor("http://www.uwo.ca/cgi-bin/dsgw/whois2html2");
			infos = service.collecte(lastName);
			writeToExcel(lastName);
			addMsg("finished collect "+lastName+" student infos!");
		}
		addMsg("finished collect "+this.getTaskName()+" student infos!");
	}

}
