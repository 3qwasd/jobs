/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.task.service.PurdueService;

/**
 * @author QiaoJian
 *
 */
public class PurdueTask extends CollectTask {
	
	PurdueService service;
	/**
	 * 
	 */
	public PurdueTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public PurdueTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#collect(java.lang.String)
	 */
	@Override
	public void collect(String lastName) {
		// TODO Auto-generated method stub
		restConnectioner();
		infos = new ArrayList<StudentInfo>();
		String url = "http://www.itap.purdue.edu/directory/";
		params = new HashMap<String, String>();
		params.put("searchType", "student");
		params.put("search", lastName);
		this.doPostVistor(url);
		if(service.isToManyResult()){
			collectInfos(lastName+" ");
		}else{
			List<StudentInfo> list = service.collecte(lastName);
			if(list!=null&&list.size()>0)
				infos.addAll(list);
		}
		writeToExcel(lastName);
	}
	public void collectInfos(String queryStr){
		restConnectioner();
		for(String let:letter){
			String url = "http://www.itap.purdue.edu/directory/";
			params = new HashMap<String, String>();
			params.put("searchType", "student");
			params.put("search", queryStr+let);
			doPostVistor(url);
			if(service.isToManyResult()){
				collectInfos(queryStr+let);
			}else{
				List<StudentInfo> list = service.collecte(queryStr+let);
				if(list!=null&&list.size()>0)
					infos.addAll(list);
			}
		}
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		initConnectioner();
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("Host", "www.itap.purdue.edu");
		service = new PurdueService();
		baseService = service;
	}

}
