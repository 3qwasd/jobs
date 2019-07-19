/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.task.service.MichiganService;
import cn.edu.bjtu.auc.task.service.RochesterService;

/**
 * @author QiaoJian
 *
 */
public class RochesterTask extends CollectTask {
	
	RochesterService service;
	/**
	 * 
	 */
	public RochesterTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public RochesterTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#collect(java.lang.String)
	 */
	@Override
	public void collect(String lastName) {
		// TODO Auto-generated method stub
		this.doGetVistor("https://info.rochester.edu/Directory/");
		params = service.getParams(lastName);
		this.doPostVistor("https://info.rochester.edu/Directory/Default.aspx");
		
		infos = service.collecte(lastName);
		if(infos!=null&&infos.size()>0){
			for(StudentInfo info:infos){
				String imgUrl = info.getInfo("email");
				this.doGetVistorGetIS(imgUrl);
				String suffix = imgUrl.substring(imgUrl.lastIndexOf("."));
				baseService.saveImage(httpResponse,"I:\\emailImag\\"+this.getTaskName(),info.getInfo("name")+".png");
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		resetConnectionWithSSL("I:\\cers\\Rochester.keystore");
		service = new RochesterService();
		baseService = service;
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("Host","info.rochester.edu");
	}

}
