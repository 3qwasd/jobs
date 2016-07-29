/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.service.IllinoisService;
import cn.edu.bjtu.auc.ui.utils.ResourceManager;

/**
 * @author QiaoJian
 *
 */
public class IllinoisTask extends UniversityTask {
	
	IllinoisService service = null;
	Map<String,String> headers = null;
	HttpResponse httpResponse = null;
	String html="";
	public List<String> firsNames = null;
	
	public IllinoisTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public IllinoisTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		firsNames = resourceManager.getFirstNames();
		resourceManager.addMessage("start collect illinois student info!");
		initConnectioner();
		service = new IllinoisService();
		headers = connectioner.createCommonHeader();
		headers.put("Host", "illinois.edu");
		for(int i=11;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			doVistor(lastName);
			if(service.toManyResult()){
				for(String firstName:firsNames){
					doVistor(firstName+"*+"+lastName);
					if(service.toManyResult()){
						
					}else{
						collectInfo(lastName+", "+firstName);
					}
				}
			}
			System.out.println("finished-->"+lastName);
		}
		resourceManager.addMessage("finishws collect illinois student info!");
		httpManager.giveBackHttpClient(connectioner);
	}
	
	public void doVistor(String lastName){
		try {
			Thread.sleep(12*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resourceManager.addMessage("start collect " +lastName+" ");
		httpResponse = connectioner.doGet(service.getSearchUrl(lastName), headers);
		html = service.getStringFromResponse(httpResponse);
		service.parserHtml(html);
	}
	public void collectInfo(String lastName){
		List<StudentInfo> infos = service.collecte(lastName);
		if(infos!=null&&infos.size()>0){
			try {
				service.writeToExcel(infos, service.getProjectPath()+this.getTaskName()+".xls","sheet");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
