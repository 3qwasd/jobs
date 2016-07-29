/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.bjtu.auc.CollectTask;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.task.service.MichiganService;

/**
 * @author QiaoJian
 *
 */
public class MichiganAnnArborTask extends CollectTask {
	MichiganService service;
	/**
	 * 
	 */
	public MichiganAnnArborTask() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param taskName
	 */
	public MichiganAnnArborTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#collect(java.lang.String)
	 */
	@Override
	public void collect(String lastName) {
		// TODO Auto-generated method stub
		/*String url = "https://www.umich.edu/search/keywords/"+lastName+"/";
		this.doGetVistor(url);
		List<String> links = service.collectLinks(lastName);
		if(links!=null&&links.size()>0){
			infos = new ArrayList<StudentInfo>();
			for(String link:links){
				this.doGetVistor("https://mcommunity.umich.edu/mcDirectoryMessages/help/authurl/");
				this.doWithRed("https://mcommunity.umich.edu/mcPeopleService/private/people/getAuthorization/"+date.getTime());
				String stuName = link.substring(link.indexOf("profile:")+8);
				String infoUrl = "https://mcommunity.umich.edu/mcPeopleService/people/"+stuName;
				this.doGetVistor(infoUrl);
				System.out.println(html);
			}
		}*/
		Date date = new Date();
		String url = "https://mcommunity.umich.edu/mcPeopleService/private/people/getAuthorization/"+date.getTime();
		this.doWithRed(url);
		System.out.println(html);
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.CollectTask#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		resetConnectionWithSSL("I:\\cers\\MichiganAnnArbor.keystore");
		service = new MichiganService();
		baseService = service;
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("Host","www.umich.edu");
	}

}
