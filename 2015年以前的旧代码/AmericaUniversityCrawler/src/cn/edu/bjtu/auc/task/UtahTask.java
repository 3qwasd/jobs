/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.service.UtahService;

/**
 * @author QiaoJian
 *
 */
public class UtahTask extends UniversityTask {
	
	
	public UtahTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public UtahTask(String taskName) {
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
		UtahService utahService = new UtahService();
		/*开始下载数据*/

		//1.首次收集预处理参数
		Map<String,String> headers = connectioner.createCommonHeader();
		headers.put("Host", "people.utah.edu");
		headers.put("Referer", "http://people.utah.edu/uWho/basic.hml");
		Map<String,String> params = new HashMap<String, String>();
		params.put("goBtn.x", 19+"");
		params.put("goBtn.y",7+"");
		params.put("isAdvancedLinkSelected", "");
		params.put("searchRole", 1+"");
		String url ="http://people.utah.edu/uWho/basic.hml";
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			params.put("searchTerm", lastName);
			HttpResponse httpResponse = connectioner.doPost(url, headers, params);
			int code = -1;
			code = httpResponse.getStatusLine().getStatusCode();
			if(code == 302){
				Header[] hs = httpResponse.getHeaders("Location");
				Header locHeader = hs[0];
				String redUrl = locHeader.getValue();
				httpResponse = connectioner.doGet(redUrl, headers);
			}
			String html = utahService.getStringFromResponse(httpResponse);
			utahService.parserHtml(html);
			List<StudentInfo> infos = utahService.collecte(lastName);
			if(infos!=null&&infos.size()>0){
				try {
					utahService.writeToExcel(infos, utahService.getProjectPath()+this.getTaskName()+".xls",lastName);
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
