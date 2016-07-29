/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.service.UCLAService;

/**
 * @author QiaoJian
 *
 */
public class UCLATask extends UniversityTask {
	UCLAService uclaService;
	long startTime;
	int count = 0;
	
	
	public UCLATask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public UCLATask(String taskName) {
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
		uclaService = new UCLAService();
		Map<String,String> headers = connectioner.createCommonHeader();
		headers.put("Host", "www.directory.ucla.edu");
		headers.put("Referer", "http://www.directory.ucla.edu/search.php");
		startTime = System.currentTimeMillis();
		for(int i=27;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			resourceManager.addMessage("start lastName:"+lastName);
			Map<String,String> params = uclaService.getParams(lastName);
			stopAndWait();
			HttpResponse httpResponse = connectioner.doPost("http://www.directory.ucla.edu/search.php", headers, params);
			count++;
			String html = uclaService.getStringFromResponse(httpResponse);
			uclaService.parserHtml(html);
			
			if(uclaService.resultsExceeded(count)){
				for(int j=0;j<letter.length;j++){
					String let = letter[j];
					resourceManager.addMessage("start lastName&firstName:"+lastName+", "+let);
					params = uclaService.getParams(lastName+", "+let);
					stopAndWait();
					httpResponse = connectioner.doPost("http://www.directory.ucla.edu/search.php", headers, params);
					count++;
					html = uclaService.getStringFromResponse(httpResponse);
					uclaService.parserHtml(html);
					if(uclaService.resultsExceeded(count)){
						for(String let2:letter){
							params = uclaService.getParams(lastName+", "+let+let2);
							resourceManager.addMessage("start lastName&firstName:"+lastName+", "+let+let2);
							stopAndWait();
							httpResponse = connectioner.doPost("http://www.directory.ucla.edu/search.php", headers, params);
							count++;
							html = uclaService.getStringFromResponse(httpResponse);
							uclaService.parserHtml(html);
							uclaService.resultsExceeded(count);
							List<StudentInfo> infos = uclaService.collecte(lastName+", "+let+let2);
							if(infos!=null&&infos.size()>0){
								try {
									uclaService.writeToExcel(infos, uclaService.getProjectPath()+this.getTaskName()+".xls",lastName);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							restConnectioner();
						}
					}else{
						List<StudentInfo> infos = uclaService.collecte(lastName+", "+let);
						if(infos!=null&&infos.size()>0){
							try {
								uclaService.writeToExcel(infos, uclaService.getProjectPath()+this.getTaskName()+".xls",lastName);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					restConnectioner();
				}
			}else{
				List<StudentInfo> infos = uclaService.collecte(lastName+", ");
				if(infos!=null&&infos.size()>0){
					try {
						uclaService.writeToExcel(infos, uclaService.getProjectPath()+this.getTaskName()+".xls",lastName);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			System.out.println("finished->"+lastName);
			restConnectioner();
		}
		resourceManager.addMessage("finished collect "+this.getTaskName()+" student info!");
		httpManager.giveBackHttpClient(connectioner);
	}

	/**
	 * 
	 */
	private void stopAndWait() {
		// TODO Auto-generated method stub
		/*System.out.println("vistor count="+count);
		if(count<10){
			return;
		}else{
			long endTime = System.currentTimeMillis();
			long waitTime = 1000*61-(endTime - startTime);
			try {
				System.out.println("sleep time = "+waitTime);
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startTime = System.currentTimeMillis();
			count = 0;
		}*/
		try {
			Thread.sleep(12*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
