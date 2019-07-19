/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.thread;

import java.util.List;

import cn.edu.bjtu.crawler.network.sina.FamousUrlDowloader;
import cn.edu.bjtu.crawler.servicer.sina.SinaDataSqlService;
import cn.edu.bjtu.crawler.utils.CrawlerGlobals;

/**
 * @author QiaoJian
 *
 */
public class CollectFamousUrlTask extends DowloadSinaDateTask {
	
	FamousUrlDowloader famousUrlDowloader;
	SinaDataSqlService sinaDataSqlService;
	/**
	 * @param taskName
	 */
	public CollectFamousUrlTask(String taskName,String url) {
		super(taskName,url);
		// TODO Auto-generated constructor stub
		famousUrlDowloader = new FamousUrlDowloader();
		sinaDataSqlService = new SinaDataSqlService();
		this.setTaskStartMessage("开始抓取路径"+url+"上的名人微博首页url！");
		this.setTaskCompleteMessage("抓取路径"+url+"上的名人微博首页url结束！");
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<String> famousUrls = famousUrlDowloader.collectFamousUrl(this.url);
		sinaDataSqlService.saveSinaUseUrls(famousUrls,1);
		
	}

}
