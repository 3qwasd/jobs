/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.thread;

import cn.edu.bjtu.joe.system.thread.component.JoeBaseTask;

/**
 * @author QiaoJian
 * 从新浪微博下载数据的任务
 */
public abstract class DowloadSinaDateTask extends JoeBaseTask {
	
	String url;

	
	/**
	 * @param taskName
	 */
	public DowloadSinaDateTask(String taskName,String url) {
		
		super(taskName);
		this.url = url;
		
		// TODO Auto-generated constructor stub
	}
}
