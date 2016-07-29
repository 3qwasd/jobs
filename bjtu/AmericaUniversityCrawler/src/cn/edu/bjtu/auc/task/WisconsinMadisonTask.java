/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.task.service.WisconsinMadisonService;

/**
 * @author QiaoJian
 *
 */
public class WisconsinMadisonTask extends CollectTask {

	WisconsinMadisonService service;
	/**
	 * 
	 */
	public WisconsinMadisonTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public WisconsinMadisonTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#collect(java.lang.String)
	 */
	@Override
	public void collect(String lastName) {
		// TODO Auto-generated method stub
		infos = new ArrayList<StudentInfo>();
		String url = "http://www.wisc.edu/search/live_directory.php?q="+lastName;
		doGetVistor(url);
		
		if(service.toManyResult()){
			initConnectioner();
			for(String firstName:firsNames){
				url = "http://www.wisc.edu/search/live_directory.php?q="+lastName+"%20"+firstName;
				doGetVistor(url);
				if(service.toManyResult()){
					continue;
				}else{
					List<StudentInfo> tmp = service.collecte(lastName);
					if(tmp!=null&&tmp.size()>0){
						infos.addAll(tmp);
					}
				}
			}
		}else{
			List<StudentInfo> tmp = service.collecte(lastName);
			if(tmp!=null&&tmp.size()>0){
				infos.addAll(tmp);
			}
		}
		addMsg("start collect detail");
		initConnectioner();
		if(infos!=null&&infos.size()>0){
			for(StudentInfo info:infos){
				if(info.getInfos().containsKey("link")){
					this.doGetVistor(url);
					service.collecteDetail(info);
				}
			}
		}
		addMsg("finish collect detail");
		this.writeToExcel(lastName);
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		service = new WisconsinMadisonService();
		baseService = service;
		initConnectioner();
		commoneHeader.put("Host", "www.wisc.edu");
		startIndex=211;
	}

}
