package jobs.toolkit.support.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.ACL;
import jobs.toolkit.support.zookeeper.fun.StringFun;

/**
 * 该接口用来创建Zookeeper节点
 * @author jobs
 *
 */
public interface ZKNodeCreater extends ZKCallback<ZKNodeCreater, StringFun>{
	/**
	 * 同步创建path表示的zookeeper节点
	 * @param path
	 * @return String
	 * @throws KeeperException
	 * @throws Exception
	 */
	public String createSync(String path) throws  KeeperException, Exception;
	/**
	 * 异步创建path表示的节点
	 * @param path
	 */
	public void createAsync(String path);
	/**
	 * 设置该节点的ACL列表
	 * @param acl
	 * @return
	 */
	public ZKNodeCreater acl(List<ACL> acl);
	/**
	 * 设置创建节点需要上传的数据
	 * @param data
	 * @return
	 */
	public ZKNodeCreater data(byte[] data);
	/**
	 * 设置创建模式
	 * @param mode
	 * @return
	 */
	public ZKNodeCreater createMode(CreateMode mode);
	/**
	 * 设置目标节点为临时节点
	 * @return
	 */
	public ZKNodeCreater ephemeral();
	/**
	 * 设置目标节点为临时序列节点
	 * @return
	 */
	public ZKNodeCreater ephemeralSequential();
	/**
	 * 设置目标节点为永久节点
	 * @return
	 */
	public ZKNodeCreater persistent();
	/**
	 * 设置目标节点为永久序列节点
	 * @return
	 */
	public ZKNodeCreater persistentSequential();
}
