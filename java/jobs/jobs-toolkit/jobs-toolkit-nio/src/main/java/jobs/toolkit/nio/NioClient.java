package jobs.toolkit.nio;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import jobs.toolkit.nio.NioConfig.NioConnectorConfig;
import jobs.toolkit.nio.event.NioEventDispatcher;
import jobs.toolkit.nio.event.RunnableNioEvent;

/**
 * Nio客户端, 负责连接远程的TCPIP服务器
 * @author jobs
 *
 */
public class NioClient extends NioConnector {
		
	/*是否重连*/
	private volatile boolean reconnect;
	/*连接延迟, 单位是秒, 连接延迟主要是用于连接失败后的再次连接, 
	 *如果连接失败, NioClient会等待connectDelay秒的时间然后再次连接*/
	private volatile int connectDelay = 0;
	
	public NioClient(NioManager manager, String name) {
		super(manager, name);
	}

	

	@Override
	protected void initialize() throws Exception {
		// TODO Auto-generated method stub
		super.initialize();
		NioConnectorConfig ncc = (NioConnectorConfig) this.getConfiguration();
		this.reconnect = ncc.isReconnect();
		String remoteIp = ncc.getRemoteIp();
		int remotePort = ncc.getRemotePort();
		this.initSocketAddress(remoteIp, remotePort);
	}

	@Override
	protected void startup() throws Exception {
		super.startup();
		//连接前首先验证NioEngine是否已经启动
		NioEngine.verify();
		this._connect();
	}
	
	void _connect(){
		NioEventDispatcher.getInstance().dispatch(new ConnectEvent(this.connectDelay * 1000));
	}
	
	@Override
	public void onConnectAbort(SocketChannel channel, Throwable cause) {
		// TODO Auto-generated method stub
		if(channel != null){
			try{
				channel.close();
			}catch(Throwable __){}
		}
		synchronized (this) {
			// reduce delay after a fail connect
			if (connectDelay == 0) {
				connectDelay = 1;
			} else {
				connectDelay *= 2;
				if (connectDelay > 60)
					connectDelay = 60;
			}
		}

		String message = String.format("Client %1$s  connect to %2$s error, will be reconnect after %3$d seconds.", this.getName(), this.getAddress(), connectDelay);
		LOG.error(message, cause);
		this._connect();
	}
	
	@Override
	void onClose(Nio nio, Throwable e) throws Throwable {
		super.onClose(nio, e);
		if(reconnect && this.isStarted()){
			this._connect();
		}
	}



	@Override
	protected void shutdown() throws Exception {
		super.shutdown();
	}
	
	private class ConnectEvent extends RunnableNioEvent{
		
		public ConnectEvent(long delay) {
			super(delay);
		}

		@Override
		public void run() {
			
			SocketChannel sc = null;
			try {
				sc = SocketChannel.open();

				sc.socket().setTcpNoDelay(NioClient.this.isTcpNoDelay());
				sc.socket().setReceiveBufferSize(NioClient.this.getReceiveBufferSize());
				sc.socket().setSendBufferSize(NioClient.this.getSendBufferSize());
				
				sc.configureBlocking(false);
				if(sc.connect(getAddress())){
					NioClient.this.onConnectSucess(sc);
				}else{
					NioEngine.selector().submitRegsiterTask(sc, SelectionKey.OP_CONNECT, NioClient.this);
				}
			} catch (Throwable e) {
				if(sc != null){
					try { sc.close(); } catch (Throwable __) {}
				}
				NioClient.this.onConnectAbort(sc, e);
			}
		}
		
	}
	
	/*用于单元测试*/
	protected synchronized int getDelay(){
		return connectDelay;
	}
}
