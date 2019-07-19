/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.edu.bjtu.auc.ui.SwingComponent;
import cn.edu.bjtu.auc.ui.utils.ResourceManager;
import cn.edu.bjtu.joe.system.thread.JoeThreadService;
import cn.edu.bjtu.joe.system.thread.component.JoeBaseTask;

/**
 * @author QiaoJian
 *
 */
public class Crawler {
	
	final JoeThreadService joeThreadService = JoeThreadService.getThreadService();
	HttpManager httpManager = HttpManager.getInstance();
	final ResourceManager resourceManager = ResourceManager.getInstance();
	final SwingComponent component = new SwingComponent();
	public void submitTask(String taskClass,String taskName){
		try {
			JoeBaseTask task =  (JoeBaseTask) Class.forName(taskClass).newInstance();
			task.setTaskName(taskName);
			joeThreadService.submitTask(task);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	public void crawler(){
		httpManager.createHttpConnection();
		ShowMessageTask showMessageTask = new ShowMessageTask("show message", component);
		joeThreadService.submitTask(showMessageTask);
		component.init(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i = component.getUniversityList().getSelectedIndex();
				String schoolName = resourceManager.getSchoolList().get(i);
				String taskClassName = resourceManager.getUniversitiyTaskName(schoolName);
				submitTask(taskClassName,schoolName);
			}
		});
		
		component.show();
	}
	
	public static void main(String[] args) {
		Crawler crawler = new Crawler();
		crawler.crawler();
	}
}
