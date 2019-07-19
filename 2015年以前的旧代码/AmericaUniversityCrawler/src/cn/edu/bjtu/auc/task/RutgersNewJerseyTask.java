/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.RutgersNewJerseyService;

/**
 * @author QiaoJian
 *
 */
public class RutgersNewJerseyTask extends StudentInfoCollectTask {
	
	RutgersNewJerseyService service;
	public RutgersNewJerseyTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RutgersNewJerseyTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = new RutgersNewJerseyService();
		baseService = service;
		restConnectioner();
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("host", "search.rutgers.edu");
		addMsg("start collect "+this.getTaskName()+" infos!");
		for(int i=0;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			addMsg("start collect lastName="+lastName);
			for(String let:letter){
				String url = service.getUrl(let, lastName);
				this.doGetVistor(url);
				System.out.println(html);
			}
		}
		addMsg("finished collect "+this.getTaskName()+" infos!");
	}

}
