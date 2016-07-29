/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.service.UoregonService;
import cn.edu.bjtu.auc.ui.utils.ResourceManager;

/**
 * @author QiaoJian
 *
 */
public class UoregonTask extends UniversityTask {
	
	
	public UoregonTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public UoregonTask(String taskName) {
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
		UoregonService uoregonService = new UoregonService();
		Map<String,String> headers = connectioner.createCommonHeader();
		headers.put("Host", "uoregon.edu");
		headers.put("Referer", "http://uoregon.edu/findpeople");
		String url = "http://uoregon.edu/findpeople";
		for(int i=10;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			HttpResponse httpResponse = connectioner.doGet(url, headers);
			String html = uoregonService.getStringFromResponse(httpResponse);
			uoregonService.parserHtml(html);
			Map<String,String> params = uoregonService.getPreParams(html);
			params.put("name",lastName);
			httpResponse = connectioner.doPost(url, headers, params);
			int code = -1;
			code = httpResponse.getStatusLine().getStatusCode();
			if(code == 302){
				Header[] hs = httpResponse.getHeaders("Location");
				Header locHeader = hs[0];
				String redUrl = locHeader.getValue();
				httpResponse = connectioner.doGet(redUrl, headers);
			}
			html = uoregonService.getStringFromResponse(httpResponse);
			uoregonService.parserHtml(html);
			List<String> userLinks = uoregonService.collecteUserLink(lastName);
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			if(userLinks!=null&&userLinks.size()>0)
				for(String link:userLinks){
					restConnectioner();
					httpResponse = connectioner.doGet(link, headers);
					html = uoregonService.getStringFromResponse(httpResponse);
					uoregonService.parserHtml(html);
					StudentInfo info = uoregonService.collecteInfo();
					if(info!=null){
						infos.add(info);
					}
					
				}
			System.out.println("start write to xls");
			if(infos!=null&&infos.size()>0){
				try {
					uoregonService.writeToExcel(infos, "I:\\UA_DATA\\"+this.getTaskName()+".xls","sheet");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			httpManager.createHttpConnection();
			connectioner = httpManager.getFirstHttpConnection();
		}

		httpManager.giveBackHttpClient(connectioner);
	}

}
