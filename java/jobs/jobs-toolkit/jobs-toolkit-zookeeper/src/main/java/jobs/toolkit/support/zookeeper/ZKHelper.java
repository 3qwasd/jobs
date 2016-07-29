package jobs.toolkit.support.zookeeper;

import org.apache.zookeeper.data.Stat;

/**
 * 
 * @author jobs
 *
 * @param <T>
 */
public interface ZKHelper<T> extends ZKRetry<T>{
	
	public T reset();
	
	/**
	 * 设置上下文对象
	 * @param ctx
	 * @return
	 */
	public abstract T ctx(Object ctx);
	/**
	 * 设置获取节点状态的Stat对象
	 * @param stat
	 * @return
	 */
	public abstract T stat(Stat stat);
}
