/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.service.UCIrvineService;

/**
 * @author QiaoJian
 *
 */
public class UCIrvineTask extends UniversityTask {
	
	
	public UCIrvineTask() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param taskName
	 */
	public UCIrvineTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		resourceManager.addMessage("start collect "+this.getTaskName()+" student info!");
		initConnectioner();
		UCIrvineService irvineService = new UCIrvineService();
		Map<String,String> headers = connectioner.createCommonHeader();
		headers.put("Host", "directory.uci.edu");
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			resourceManager.addMessage("start lastName:"+lastName);
			for(String firstName:letter){
				resourceManager.addMessage("start lastName&firstName:"+lastName+", "+firstName);
//				try {
//					Thread.sleep(1000*12);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				String url = irvineService.getSearcheUrl(lastName,firstName);
				HttpResponse httpResponse = connectioner.doGet(url, headers);
				String html = irvineService.getStringFromResponse(httpResponse);
				irvineService.parserHtml(html);
				List<StudentInfo> infos = irvineService.collecte(lastName);
				if(infos!=null&&infos.size()>0){
					try {
						irvineService.writeToExcel(infos, irvineService.getProjectPath()+this.getTaskName()+".xls",lastName);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			System.out.println("finished->"+lastName);
		}
		resourceManager.addMessage("finished collect "+this.getTaskName()+" student info!");
		httpManager.giveBackHttpClient(connectioner);
	}

}
