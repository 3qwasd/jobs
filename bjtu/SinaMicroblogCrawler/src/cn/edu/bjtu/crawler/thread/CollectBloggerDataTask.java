/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.thread;

import cn.edu.bjtu.crawler.network.HttpConnectionerManager;
import cn.edu.bjtu.crawler.network.sina.BloggerHomeDownloader;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogger;

/**
 * @author QiaoJian
 *
 */
public class CollectBloggerDataTask extends DowloadSinaDateTask {
	
	private SinaMicroblogger microblogger;
	
	private BloggerHomeDownloader bloggerHomeDownloader;
	/**
	 * @param taskName
	 * @param url
	 */
	public CollectBloggerDataTask(String taskName, String url,SinaMicroblogger microblogger) {
		super(taskName, url);
		// TODO Auto-generated constructor stub
		this.microblogger = microblogger;
		bloggerHomeDownloader = new BloggerHomeDownloader();
		bloggerHomeDownloader.setHttpClient(HttpConnectionerManager.getInstance().getFirstHttpClient());
		this.setTaskStartMessage("开始抓取"+url+"的信息！");
		this.setTaskCompleteMessage("抓取"+url+"上的信息结束！");
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		bloggerHomeDownloader.collectUserWeiboData(microblogger);
	}

}
