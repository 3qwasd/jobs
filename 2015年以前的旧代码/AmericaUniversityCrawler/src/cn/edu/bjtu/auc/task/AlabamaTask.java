/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.auc.HttpConnectioner;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.service.AlabamaService;

/**
 * @author QiaoJian
 *
 */
public class AlabamaTask extends UniversityTask{
	
	public AlabamaTask(){
		super();
	}
	/**
	 * @param taskName
	 */
	public AlabamaTask(String taskName) {
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
		AlabamaService alabamaService = new AlabamaService();
		/*开始下载数据*/

		//1.首次收集预处理参数
		Map<String,String> headers = connectioner.createCommonHeader();
		headers.put("Host", "directory.ua.edu");
		HttpResponse httpResponse = connectioner.doGet("http://directory.ua.edu/", headers);
		String html = alabamaService.getStringFromResponse(httpResponse);
		alabamaService.parserHtml(html);
		Map<String,String> params = alabamaService.getPreParams();
		headers.put("Referer", "http://directory.ua.edu/");

		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			resourceManager.addMessage("start lastName:"+lastName);
			params.put("f", "");
			params.put("l",lastName);
			params.put("search", "Search");
			params.put("spam", "");
			params.put("t", "2");
			httpResponse = connectioner.doPost("http://directory.ua.edu/search.php", headers, params);
			html = alabamaService.getStringFromResponse(httpResponse);
			alabamaService.parserHtml(html);
			List<StudentInfo> infos = alabamaService.collecte(lastName);
			if(infos!=null&&infos.size()>0){
				try {
					alabamaService.writeToExcel(infos, alabamaService.getProjectPath()+this.getTaskName()+".xls",lastName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String nextPageUrl = null;
			while((nextPageUrl =  alabamaService.collecteNextPageUrl())!=null){
				headers.put("Referer", "http://directory.ua.edu/search.php");
				httpResponse = connectioner.doGet("http://directory.ua.edu/"+nextPageUrl, headers);
				html = alabamaService.getStringFromResponse(httpResponse);
				alabamaService.parserHtml(html);
				infos = alabamaService.collecte(lastName);
				if(infos!=null&&infos.size()>0){
					try {
						alabamaService.writeToExcel(infos, alabamaService.getProjectPath()+this.getTaskName()+".xls", lastName);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			params = alabamaService.getPreParams();
		}
		/*数据下载结束*/

		resourceManager.addMessage("finished collect "+this.getTaskName()+" student info!");
		/*任务结束返还http连接*/
		httpManager.giveBackHttpClient(connectioner);
	}

}
