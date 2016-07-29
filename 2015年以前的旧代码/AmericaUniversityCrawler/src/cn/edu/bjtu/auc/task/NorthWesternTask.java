/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.HashMap;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.task.service.IndianaBloomingtonService;
import cn.edu.bjtu.auc.task.service.NorthWesternService;

/**
 * @author QiaoJian
 *
 */
public class NorthWesternTask extends CollectTask {
	
	NorthWesternService service;
	/**
	 * 
	 */
	public NorthWesternTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public NorthWesternTask(String taskName) {
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
		params.put("affiliations", "student");
		params.put("doit", "Query");
		params.put("form_type", "simple");
		params.put("query", lastName);
		this.doPostVistor("http://directory.northwestern.edu/");
		infos = service.collecte(lastName);
		if(infos!=null&&infos.size()>0){
			for(int i=0;i<infos.size();i++){
				StudentInfo info = infos.get(i);
				
				String imgUrl = info.getInfo("email");
				this.doGetVistorGetIS(imgUrl);
				String suffix = imgUrl.substring(imgUrl.lastIndexOf("."));
				baseService.saveImage(httpResponse,"I:\\emailImag\\"+this.getTaskName(),info.getInfo("name")+suffix);
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
		commoneHeader.put("Host", "directory.northwestern.edu");
		service = new NorthWesternService();
		baseService = service;
		startIndex = 0;
	}

}
