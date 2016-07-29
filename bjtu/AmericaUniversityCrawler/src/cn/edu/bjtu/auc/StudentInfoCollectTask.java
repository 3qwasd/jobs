/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import cn.edu.bjtu.auc.ui.utils.ResourceManager;
import cn.edu.bjtu.joe.system.thread.component.JoeBaseTask;

/**
 * @author QiaoJian
 *
 */
public abstract class StudentInfoCollectTask extends JoeBaseTask{
	public ResourceManager resourceManager;
	public HttpManager httpManager;
	public List<String> lastNames = null;
	public HttpConnectioner connectioner = null;
	public Map<String,String> commoneHeader = null;
	public Map<String,String> params = null;
	public HttpResponse httpResponse = null;
	public String html = null;
	public List<StudentInfo> infos = null;
	public BaseService baseService = null;
	public List<String> firsNames = null;
	public String[] letter = {
			"B","C","D","F","G","H",
			"J","K","L","M","N","P","Q",
			"S","T","W","X","Y","Z"
	};
	/**
	 * @param taskName
	 */
	public StudentInfoCollectTask() {
		super();
		// TODO Auto-generated constructor stub
		httpManager = HttpManager.getInstance();
		resourceManager = ResourceManager.getInstance();
		lastNames = resourceManager.getLastNames();
		firsNames = resourceManager.getFirstNames();
	}
	/**
	 * @param taskName
	 */
	public StudentInfoCollectTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
		httpManager = HttpManager.getInstance();
		resourceManager = ResourceManager.getInstance();
		lastNames = resourceManager.getLastNames();
		firsNames = resourceManager.getFirstNames();
	}
	public void initConnectioner(){
		/*任务开始创建http连接*/
		/*从连接池中获取连接，如果没有则等待，直到获取到连接为止*/

			connectioner = httpManager.getFirstHttpConnection();
			if(connectioner == null){
				httpManager.createHttpConnection();
				connectioner = httpManager.getFirstHttpConnection();
			}
			
		commoneHeader = connectioner.createCommonHeader();
	}
	public void restConnectioner(){
		httpManager.createHttpConnection();
		connectioner = httpManager.getFirstHttpConnection();
	}
	public void writeToExcel(String lastName){
		if(infos!=null&&infos.size()>0){
			try {
				baseService.writeToExcel(infos, "I:\\UA_DATA\\"+this.getTaskName()+".xls", lastName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void doPostVistor(String url,String charSet){
		try{
			httpResponse = connectioner.doPost(url, commoneHeader, params);
			html = baseService.getStringFromResponse(httpResponse,charSet);
			System.out.println(html);
			baseService.parserHtml(html);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public InputStream doPostVistorGetInputstream(String url){
		try{
			httpResponse = connectioner.doPost(url, commoneHeader, params);

			return baseService.getInputStreamFromResponse(httpResponse);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	public void doPostVistor(String url){
		try{
			httpResponse = connectioner.doPost(url, commoneHeader, params);
			html = baseService.getStringFromResponse(httpResponse);
			baseService.parserHtml(html);
			System.out.println(html);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void doGetVistor(String url){
		try{
			httpResponse = connectioner.doGet(url, commoneHeader);
			html = baseService.getStringFromResponse(httpResponse);
			baseService.parserHtml(html);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void doGetVistorGetIS(String url){
		try{
			httpResponse = connectioner.doGet(url, commoneHeader);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	public void doWithRed(String url){
		httpResponse = connectioner.doGet(url, commoneHeader);
		int code = -1;
		code = httpResponse.getStatusLine().getStatusCode();
		if(code == 302){
			Header[] hs = httpResponse.getHeaders("Location");
			Header locHeader = hs[0];
			String redUrl = locHeader.getValue();
			doGetVistor(redUrl);
		}

	}
	public void addMsg(String msg){
		resourceManager.addMessage(msg);
	}
	public void restConnectionerSSL(){
		httpManager.clearPool();
		httpManager.createHttpConnection(true);
		connectioner = httpManager.getFirstHttpConnection();
		commoneHeader = connectioner.createCommonHeader();
	}
}
