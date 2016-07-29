/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc;

import java.util.List;
import java.util.Map;

import cn.edu.bjtu.auc.ui.utils.ResourceManager;
import cn.edu.bjtu.joe.system.thread.component.JoeBaseTask;

/**
 * @author QiaoJian
 *
 */
public abstract class UniversityTask extends JoeBaseTask{
	public ResourceManager resourceManager;
	public HttpManager httpManager;
	public List<String> lastNames = null;
	public HttpConnectioner connectioner = null;
	public Map<String,String> commoneHeader = null;
	public String[] letter = {
			"A","B","C","D","E","F","G","H","I",
			"J","K","L","M","N","O","P","Q","R",
			"S","T","U","V","W","X","Y","Z"
	};
	public UniversityTask() {
		super();
		// TODO Auto-generated constructor stub
		httpManager = HttpManager.getInstance();
		resourceManager = ResourceManager.getInstance();
		lastNames = resourceManager.getLastNames();
	}
	/**
	 * @param taskName
	 */
	public UniversityTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
		httpManager = HttpManager.getInstance();
		resourceManager = ResourceManager.getInstance();
		lastNames = resourceManager.getLastNames();
	}
	public void initConnectioner(){
		/*任务开始创建http连接*/
		/*从连接池中获取连接，如果没有则等待，直到获取到连接为止*/
		while(connectioner == null){
			connectioner = httpManager.getFirstHttpConnection();
			if(connectioner == null){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		commoneHeader = connectioner.createCommonHeader();
	}
	public void restConnectioner(){
		httpManager.createHttpConnection();
		connectioner = httpManager.getFirstHttpConnection();
	}
	public void restConnectionerSSL(){
		httpManager.clearPool();
		httpManager.createHttpConnection(true);
		connectioner = httpManager.getFirstHttpConnection();
	}
}
