/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.UniversityTask;
import cn.edu.bjtu.auc.task.service.BostonService;

/**
 * @author QiaoJian
 *
 */
public class BostonTask extends StudentInfoCollectTask{
	BostonService service;
	/**
	 * @param taskName
	 */
	public BostonTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}
	
	public BostonTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		initConnectioner();
		service = new BostonService();
		baseService = service;
		commoneHeader.put("Host", "www.bu.edu");
		this.addMsg("start collect "+this.getTaskName()+" infos !");
		for(int i=13;i<lastNames.size();i++){
			String lastName = lastNames.get(i);
			for(String let:letter){
				this.addMsg("start collect "+lastName+", "+let+" infos !");
				String url = service.getUrl(lastName,let);
				httpManager.clearPool();
				httpManager.createHttpConnection();
				connectioner = httpManager.getFirstHttpConnection();
				doGetVistor(url);
				List<String> links = service.collecteLinks(lastName);
				if(links!=null&&links.size()>0){
					try {
						baseService.writeLinksToText(links, baseService.getProjectPath()+this.getTaskName()+".txt");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					infos = new ArrayList<StudentInfo>();
					for(String link:links){
						httpManager.clearPool();
						httpManager.createHttpConnection();
						connectioner = httpManager.getFirstHttpConnection();
						String[] linkStr = link.split(",",2);
						url = "http://www.bu.edu/phpbin/directory/?q={1}%2C+{2}";
						url = url.replace("{1}", linkStr[1].trim()).replace("{2}", linkStr[0].trim());
						doGetVistor(url);
						StudentInfo info = service.collectInfo();
						if(info!=null)
							infos.add(info);
					}
					writeToExcel("sheet");
				}
			}
			this.addMsg("finished collect "+lastName+" infos !");
			
		}
		this.addMsg("finished collect "+this.getTaskName()+" infos !");
		httpManager.giveBackHttpClient(connectioner);
	}

}
