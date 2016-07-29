/**
 * @QiaoJian
 */
package cn.edu.bjtu.joe.system.thread.component;

import java.util.concurrent.ThreadFactory;

/**
 * @author QiaoJian
 *
 */
public  class JoeTaskThreadFactory implements ThreadFactory {

	/* (non-Javadoc)
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 */
	@Override
	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		
		JoeTaskThread thread = new JoeTaskThread(r);
		
		return thread;
	}

}
