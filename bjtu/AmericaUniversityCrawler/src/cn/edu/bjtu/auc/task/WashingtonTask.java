/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.service.AlabamaService;
import cn.edu.bjtu.auc.task.service.WashingtonService;

/**
 * @author QiaoJian
 *
 */
public class WashingtonTask extends UniversityTask {
	
	
	public WashingtonTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public WashingtonTask(String taskName) {
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
		WashingtonService washingtonService = new WashingtonService();
		/*开始下载数据*/

		Map<String,String> headers = connectioner.createCommonHeader();
		headers.put("Host", "www.washington.edu");
		headers.put("Referer", "http://www.washington.edu/home/peopledir/");
		Map<String,String> params = new HashMap<String, String>();
		params.put("length", "sum");
		params.put("method", "name");
		
		params.put("whichdir", "student");
		String url = "http://www.washington.edu/home/peopledir/";
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			resourceManager.addMessage("start lastName:"+lastName);
			params.put("term", lastName);
			HttpResponse httpResponse = connectioner.doPost(url, headers, params);
			String html = washingtonService.getStringFromResponse(httpResponse);
			washingtonService.parserHtml(html);
			List<StudentInfo> infos = washingtonService.collecte(lastName);
			if(infos!=null&&infos.size()>0){
				try {
					washingtonService.writeToExcel(infos, washingtonService.getProjectPath()+this.getTaskName()+".xls",lastName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		resourceManager.addMessage("finished collect "+this.getTaskName()+" student info!");
		httpManager.giveBackHttpClient(connectioner);
	}

}
