/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.HashMap;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.task.service.OhioStateService;

/**
 * @author QiaoJian
 *
 */
public class OhioStateTask extends CollectTask {
	OhioStateService service;
	/**
	 * 
	 */
	public OhioStateTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public OhioStateTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#collect(java.lang.String)
	 */
	@Override
	public void collect(String lastName) {
		// TODO Auto-generated method stub
		params = new HashMap<String, String>();
		params.put("ChooseMatch", "student");
		params.put("firstname", "");
		params.put("name_n", "");
		params.put("lastname", lastName);
		String url = "https://directory.osu.edu/findpeople.php";
		this.doPostVistor(url);
		infos = service.collecte(lastName);
		writeToExcel(lastName);
		// TODO Auto-generated method stub
		this.resetConnectionWithSSL("I:\\cers\\OhioState.keystore");
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		this.resetConnectionWithSSL("I:\\cers\\OhioState.keystore");
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("Host", "directory.osu.edu");
		service = new OhioStateService();
		baseService = service;
	}

}
