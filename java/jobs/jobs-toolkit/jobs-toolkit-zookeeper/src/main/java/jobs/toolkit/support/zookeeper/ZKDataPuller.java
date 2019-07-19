package jobs.toolkit.support.zookeeper;

import org.apache.zookeeper.KeeperException;
import jobs.toolkit.support.zookeeper.fun.DataFun;

/**
 * 这个接口用来从zookeeper节点拉取数据
 * @author jobs
 *
 */
public interface ZKDataPuller extends ZKNodeWatcher<ZKDataPuller, DataFun>{
	
	/**
	 * 同步从path表示的zookeeper节点上拉取数据
	 * @param path
	 * @return
	 */
	public byte[] pullSync(String path) throws KeeperException, Exception;
	/**
	 * 异步从path表示的zookeeper节点上拉取数据
	 * @param path
	 */
	public void pullAsync(String path);
	
	
}
