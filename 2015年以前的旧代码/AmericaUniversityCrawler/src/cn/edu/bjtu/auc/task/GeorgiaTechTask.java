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
import cn.edu.bjtu.auc.task.service.GeorgiaTechService;

/**
 * @author QiaoJian
 *
 */
public class GeorgiaTechTask extends UniversityTask{
	
	GeorgiaTechService service;
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.resourceManager.addMessage("start collect GeorgiaTech student info");
		
		this.initConnectioner();
		resetConnection();
		service = new GeorgiaTechService();
		commoneHeader.put("Host", "www.directory.gatech.edu");
		for(int i=0;i<lastNames.size();i++){
			HttpResponse httpResponse = connectioner.doGet("https://www.directory.gatech.edu/directory", commoneHeader);
			String html = service.getStringFromResponse(httpResponse);
			service.parserHtml(html);
			Map<String,String> params = service.getPreParams();
			String lastName = lastNames.get(i);
			resourceManager.addMessage("start collect lastName:"+lastName);
			params.put("lastname", lastName);
			resetConnection();
			httpResponse = connectioner.doPost("https://www.directory.gatech.edu/directory", commoneHeader, params);
			int code = -1;
			code = httpResponse.getStatusLine().getStatusCode();
			if(code == 302){
				Header[] hs = httpResponse.getHeaders("Location");
				Header locHeader = hs[0];
				String redUrl = locHeader.getValue();
				resetConnection();
				httpResponse = connectioner.doGet(redUrl, commoneHeader);
			}else{
				continue;
			}
			html = service.getStringFromResponse(httpResponse);
			service.parserHtml(html);
			List<String> studentLink = service.collecteStudentLinks(lastName+", ");
			List<StudentInfo> infos = null;
			if(studentLink!=null&&studentLink.size()>0){
				infos = new ArrayList<StudentInfo>();
				resetConnection();
				for(String link : studentLink){
					while((httpResponse = connectioner.doGet("https://www.directory.gatech.edu"+link, commoneHeader))==null){
						resetConnection();
					}
					html = service.getStringFromResponse(httpResponse);
					service.parserHtml(html);
					StudentInfo info = service.collecteInfo();
					if(info!=null)
						infos.add(info);
				}
			}
			if(infos!=null&&infos.size()>0){
				try {
					service.writeToExcel(infos, service.getProjectPath()+this.getTaskName()+".xls",lastName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			resourceManager.addMessage("finished collect lastName:"+lastName);
		}
		
		this.resourceManager.addMessage("finished collect GeorgiaTech student info");
	}
	public void resetConnection(){
		httpManager.giveBackHttpClient(connectioner);
		httpManager.clearPool();
		httpManager.createHttpConnection("I:\\cers\\GeorgiaTech.keystore", "123456");
		connectioner = httpManager.getFirstHttpConnection();
	}
}
