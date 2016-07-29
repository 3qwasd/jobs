/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.task.service.MinnesotaTwinCitiesService;

/**
 * @author QiaoJian
 *
 */
public class MinnesotaTwinCitiesTask extends CollectTask {
	MinnesotaTwinCitiesService service;
	/**
	 * 
	 */
	public MinnesotaTwinCitiesTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public MinnesotaTwinCitiesTask(String taskName) {
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
		infos = new ArrayList<StudentInfo>();
		if(service.isTooMany(html)){
			for(String let:letter){
				url = service.getUrl(lastName,let);
				doGetVistor(url);
				List<StudentInfo> tmp = service.collecte(lastName);
				if(tmp!=null&&tmp.size()>0){
					infos.addAll(tmp);
				}
				writeToExcel(lastName);
			}
		}else{
			infos = service.collecte(lastName);
		}
		initConnectioner();
		if(infos!=null&&infos.size()>0){
			for(StudentInfo info:infos){
				String link = info.getInfos().remove("link");
				this.doGetVistor(link);
				service.collecteInfoDetail(info);
			}
		}
		writeToExcel(lastName);
		initConnectioner();
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		initConnectioner();
		service = new MinnesotaTwinCitiesService();
		baseService = service;
		commoneHeader.put("Host", "www.umn.edu");
	}

}
