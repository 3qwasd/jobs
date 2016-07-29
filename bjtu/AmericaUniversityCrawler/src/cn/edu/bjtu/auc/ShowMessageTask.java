/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc;

import cn.edu.bjtu.auc.ui.SwingComponent;
import cn.edu.bjtu.auc.ui.utils.ResourceManager;
import cn.edu.bjtu.joe.system.thread.component.JoeBaseTask;

/**
 * @author QiaoJian
 *
 */
public class ShowMessageTask extends JoeBaseTask {
	
	ResourceManager manager;
	
	SwingComponent component;
	/**
	 * @param taskName
	 */
	public ShowMessageTask(String taskName,SwingComponent component) {
		super(taskName);
		// TODO Auto-generated constructor stub
		manager = ResourceManager.getInstance();
		this.component = component;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			String msg = manager.getMessage();
			if(msg!=null){
				component.createMessageBox(msg);
			}
		}
	}

}
