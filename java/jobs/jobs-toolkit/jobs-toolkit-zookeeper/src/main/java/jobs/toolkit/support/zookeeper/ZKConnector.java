package jobs.toolkit.support.zookeeper;

/**
 * Zookeeper连接器接口
 * @author jobs
 *
 */
public interface ZKConnector extends ZKSessionWatcher<ZKConnector>{
	/**
	 * 连接到hostRi表示的zookeeper服务器上
	 * @param hostRi
	 * @throws Exception
	 */
	public void connectSync(String hostRi) throws Exception;
	/**
	 * 设置zookeeper会话超时时间
	 * @param seesionTimeout
	 * @return
	 */
	public ZKConnector sessionTimeout(int seesionTimeout);

}
