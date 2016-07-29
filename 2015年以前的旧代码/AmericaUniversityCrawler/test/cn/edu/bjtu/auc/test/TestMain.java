/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.bjtu.auc.HttpManager;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.AlabamaTask;
import cn.edu.bjtu.auc.task.IllinoisTask;
import cn.edu.bjtu.auc.task.OhioTask;
import cn.edu.bjtu.auc.task.SaintMichaelTask;
import cn.edu.bjtu.auc.task.UCIrvineTask;
import cn.edu.bjtu.auc.task.UCLATask;
import cn.edu.bjtu.auc.task.UoregonTask;
import cn.edu.bjtu.auc.task.UtahTask;
import cn.edu.bjtu.auc.task.WashingtonTask;
import cn.edu.bjtu.joe.system.thread.JoeThreadService;

/**
 * @author QiaoJian
 *
 */
public class TestMain {
	
	public static void main(String[] args) throws Exception {
		JoeThreadService joeThreadService = JoeThreadService.getThreadService();
		HttpManager httpManager = HttpManager.getInstance();
		httpManager.createHttpConnection();
		
		List<UniversityTask> taskes = new ArrayList<UniversityTask>();
		OhioTask ohioTask = new OhioTask("Ohio");
		taskes.add(ohioTask);
		UCLATask uclaTask = new UCLATask("UCLA");
		taskes.add(uclaTask);
		IllinoisTask illinoisTask = new IllinoisTask("Illinois");
		taskes.add(illinoisTask);
		
		while(true){
			System.out.println(new Date()+":nomal");
			if(joeThreadService.getActiveCount()<3){
				if(taskes.size()<1){
					break;
				}else{
					UniversityTask task = taskes.remove(0);
					joeThreadService.submitTask(task);
				}
			}
			Thread.sleep(1000*10);
		}
	}
}
