package jobs.toolkit.support.zookeeper;

import java.io.Closeable;

/**
 * zookeeper助手
 * @author jobs
 *
 */
public interface ZKSupporter extends Closeable{
	/**
	 * zookeeper助手是否可用
	 * @return
	 */
	public boolean isValid();
	/**
	 * 准备zookeeper连接器连接到zookeeper服务器
	 * @return
	 */
	public ZKConnector prepareConnect();
	/**
	 * 准备创建zookeeper节点
	 * @return
	 */
	public ZKNodeCreater prepareCreate();
	/**
	 * 准备检查zookeeper节点
	 * @return
	 */
	public ZKNodeChecker prepareCheck();
	/**
	 * 准备从zookeeper节点上拉取数据
	 * @return
	 */
	public ZKDataPuller preparePull();
	/**
	 * 准备向zookeeper节点推送数据
	 * @return
	 */
	public ZKDataPusher preparePush();
	
}
