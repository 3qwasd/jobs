package jobs.toolkit.support.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import jobs.toolkit.support.zookeeper.fun.StatFun;

/**
 * 该接口用来检查zookeeper节点是否存在
 * @author jobs
 *
 */
public interface ZKNodeChecker extends ZKNodeWatcher<ZKNodeChecker, StatFun>{
	/**
	 * 同步检查path表示的节点是否存在, 如果节点不存在则返回null, 如果节点存在则返回节点的Stat对象
	 * @param path
	 * @return Stat
	 * @throws KeeperException
	 * @throws Exception
	 */
	public abstract Stat checkSync(String path) throws  KeeperException, Exception;
	/**
	 * 异步检查path表示的节点是否存在
	 * @param path
	 */
	public abstract void checkAsync(String path);

}
