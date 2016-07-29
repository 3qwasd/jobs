package jobs.toolkit.lifecycle;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 生命周期监听器管理
 * @author jobs
 *
 */
public class LifeCycleListenerManager {
	
	
	/*监听器列表, 使用CopyOnWriteArrayList保证线程安全*/
	private List<LifeCycleStateListener> listeners = new CopyOnWriteArrayList<LifeCycleStateListener>();
	/**
	 * 添加新的监听器
	 * @param listener
	 */
	public void addListener(LifeCycleStateListener listener){
		this.listeners.add(listener);
	}
	/**
	 * 移除监听器
	 * @param listener
	 */
	public void removeListener(LifeCycleStateListener listener){
		this.listeners.remove(listener);
	}
	/**
	 * 重置监听器
	 */
	public void reset(){
		this.listeners.clear();
	}
	/**
	 * 通知监听器
	 * @param component
	 */
	public void notifyListeners(LifeCycle component){
		for(Iterator<LifeCycleStateListener> iter = listeners.iterator(); iter.hasNext();){
			LifeCycleStateListener l = iter.next();
			l.stateChanged(component);
		}
	}
}
