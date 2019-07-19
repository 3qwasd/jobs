package jobs.toolkit.support.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import jobs.toolkit.support.zookeeper.fun.StatFun;

/**
 * 该接口负责向zookeeper节点更新数据
 * @author jobs
 *
 */
public interface ZKDataPusher extends ZKCallback<ZKDataPusher, StatFun>{
	/**
	 * 同步推送数据到path表示的zookeeper节点
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws Exception
	 */
	public Stat pushSync(String path) throws  KeeperException, Exception;
	/**
	 * 异步推送数据到path表示的zookeeper节点
	 * @param path
	 */
	public void pushAsync(String path);
	/**
	 * 设置要推送的数据
	 * @param data
	 * @return
	 */
	public ZKDataPusher data(byte[] data);
	/**
	 * 设置推送数据时, 需要匹配的版本号
	 * @param version
	 * @return
	 */
	public ZKDataPusher version(int version);
}
