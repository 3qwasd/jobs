/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.List;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.task.service.IndianaBloomingtonService;

/**
 * @author QiaoJian
 *
 */
public class IndianaBloomingtonTask extends CollectTask {

	IndianaBloomingtonService service;
	/**
	 * 
	 */
	public IndianaBloomingtonTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public IndianaBloomingtonTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#collect(java.lang.String)
	 */
	@Override
	public void collect(String lastName) {
		// TODO Auto-generated method stub
		//for(int i=0;i<letter.length;i++){
			//String firstName = letter[i];
			params = service.getParams(lastName,"");
			String url = "http://people.iu.edu/index.cgi";
			this.doPostVistor(url);
			List<String> links = service.collectLinks(lastName);
			if(links!=null&&links.size()>0){
				for(String link:links){
					this.doGetVistor(link);
					StudentInfo info = service.collecteInfo();
					if(info!=null){
						String imgUrl = info.getInfo("email");
						this.doGetVistorGetIS(imgUrl);
						String suffix = imgUrl.substring(imgUrl.lastIndexOf("."));
						baseService.saveImage(httpResponse,"I:\\emailImag\\"+this.getTaskName(),info.getInfo("name")+suffix);
					}
				}
			}
		//}
		restConnectioner();
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		initConnectioner();
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("Host", "www.iub.edu");
		service = new IndianaBloomingtonService();
		baseService = service;
		startIndex = 50;
	}

}
