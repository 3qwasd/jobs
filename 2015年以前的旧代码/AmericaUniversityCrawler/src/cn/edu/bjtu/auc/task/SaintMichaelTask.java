/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.service.SaintMichaelService;

/**
 * @author QiaoJian
 *
 */
public class SaintMichaelTask extends UniversityTask {
	
	public SaintMichaelTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public SaintMichaelTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		initConnectioner();
		SaintMichaelService michaelService = new SaintMichaelService();
		Map<String,String> headers = connectioner.createCommonHeader();
		headers.put("Host", "www.smcvt.edu");
		String url = "http://www.smcvt.edu/About-SMC/Contact-Us/Directories/Student-Directory.aspx";
		HttpResponse httpResponse = connectioner.doGet(url, headers);
		String html = michaelService.getStringFromResponse(httpResponse);
		michaelService.parserHtml(html);
		Map<String,String> params = michaelService.getPreParams();
		
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			System.out.println(lastName);
			params.put("maincontent_1$lastname", lastName);
			httpResponse = connectioner.doPost(url, headers, params);
			html = michaelService.getStringFromResponse(httpResponse);
			michaelService.parserHtml(html);
			List<StudentInfo> infos = michaelService.collecte(lastName);
			
			if(infos!=null&&infos.size()>0){
				try {
					michaelService.writeToExcel(infos,michaelService.getProjectPath()+this.getTaskName()+".xls",lastName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			params = michaelService.getPreParams();
		}
		httpManager.giveBackHttpClient(connectioner);
	}

}
