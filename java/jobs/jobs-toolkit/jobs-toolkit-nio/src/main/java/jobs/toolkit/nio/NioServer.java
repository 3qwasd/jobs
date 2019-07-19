package jobs.toolkit.nio;

import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import jobs.toolkit.nio.NioConfig.NioConnectorConfig;

/**
 * Nio服务器, 用于服务端, 被动接受连接
 * @author jobs
 *
 */
public class NioServer extends NioConnector{
	
	private int backlog;
	
	/*服务端channel*/
	private volatile ServerSocketChannel server;
	/*注册的selectionKey*/
	private volatile SelectionKey serverKey;
	
	public NioServer(NioManager manager, String name) {
		super(manager, name);
	}
	

	@Override
	protected void initialize() throws Exception {
		super.initialize();
		NioConnectorConfig ncc = (NioConnectorConfig) this.getConfiguration();
		this.backlog = ncc.getBackLog();
		String serverIp = ncc.getServerIp();
		int serverPort = ncc.getServerPort();
		this.initSocketAddress(serverIp, serverPort);
	}

	@Override
	protected void startup() throws Exception {
		super.startup();
		NioEngine.verify();
		this.server = ServerSocketChannel.open();
		ServerSocket ss = this.server.socket();
		ss.setReuseAddress(true);
		ss.setReceiveBufferSize(super.getReceiveBufferSize());
		ss.bind(super.getAddress(), backlog);
		/*提交注册任务*/
		FutureTask<SelectionKey> registerTask = NioEngine.selector().submitRegsiterTask(this.server, SelectionKey.OP_ACCEPT, this);
		/*等待注册任务完成*/
		this.serverKey = registerTask.get(1000, TimeUnit.SECONDS);
	}

	@Override
	protected void shutdown() throws Exception {
		super.shutdown();
		if(this.server != null){
			this.server.close();
			this.server = null;
			this.serverKey = null;
		}
	}
	
	@Override
	public void onConnectAbort(SocketChannel channel, Throwable cause) {
		String remoteAddress = "unknow";
		if(channel != null){
			try{
				remoteAddress = channel.getRemoteAddress().toString();
				channel.close();
			}catch(Throwable __){}
		}
		String message = String.format("Server %1$s accept socket connect from %2$s error!", this.getName(), remoteAddress);
		LOG.error(message, cause);
	}

	public final int getBacklog() {
		return backlog;
	}

	/**
	 * 用于单元测试
	 * @return
	 */
	protected final ServerSocketChannel getServer() {
		return server;
	}

	/**
	 * 用于单元测试
	 * @return
	 */
	protected final SelectionKey getServerKey() {
		return serverKey;
	}

}
