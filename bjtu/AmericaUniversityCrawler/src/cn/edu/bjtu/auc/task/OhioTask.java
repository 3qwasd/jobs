/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.service.OhioService;

/**
 * @author QiaoJian
 *
 */
public class OhioTask extends UniversityTask{
	
	public OhioTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public OhioTask(String taskName) {
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
		httpManager.createHttpConnection();
		connectioner = httpManager.getFirstHttpConnection();
		OhioService ohioService = new OhioService();
		Map<String,String> headers = connectioner.createCommonHeader();
		headers.put("Host","google.ohio.edu");
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			resourceManager.addMessage("start lastName:"+lastName);
			String url = "http://google.ohio.edu/search?"+ohioService.buildQueryStr(lastName);
			HttpResponse httpResponse = null;
			try {
				Thread.sleep(1000*12);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			while((httpResponse = connectioner.doGet(url, headers))==null){
				httpManager.createHttpConnection();
				connectioner = httpManager.getFirstHttpConnection();
			}
			try {
				Thread.sleep(12*1000);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(httpResponse.getStatusLine().getStatusCode()!=200){
				httpManager.createHttpConnection();
				connectioner = httpManager.getFirstHttpConnection();
				i--;
				continue;
			}
			List<StudentInfo> infos = null;
			String html = ohioService.getStringFromResponse(httpResponse);
			ohioService.parserHtml(html);
			infos = ohioService.collecte(lastName);
			if(infos!=null&&infos.size()>0){
				try {
					ohioService.writeToExcel(infos, ohioService.getProjectPath()+this.getTaskName()+".xls", lastName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(1000*5);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		resourceManager.addMessage("finished collect "+this.getTaskName()+" student info!");
		httpManager.giveBackHttpClient(connectioner);
	}

}
