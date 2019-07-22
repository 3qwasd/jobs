package gateway.component.server;

import gateway.component.HttpEventDispatcher;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jobs.toolkit.config.MapConfiguration;
import jobs.toolkit.service.BaseService;

public class NettyHttpServer extends BaseService{
	
	
	private volatile ChannelFuture channelFuture;
	
	private final NioEventLoopGroup bossLoopGroup;
	
	private final NioEventLoopGroup workLoopGroup;
	
	private final ServerBootstrap server;
	
	private int port;
	
	public NettyHttpServer() {
		super("NettyHttpServer");
		this.bossLoopGroup = new NioEventLoopGroup();
		this.workLoopGroup = new NioEventLoopGroup();
		this.server = new ServerBootstrap();
	}

	@Override
	protected void initialize() throws Exception {
		
		this.port = this.getConfiguration().getInt("port", 8080);
		
		this.server.option(ChannelOption.SO_BACKLOG, 1024);
		this.server.childOption(ChannelOption.TCP_NODELAY,true);
		this.server.childOption(ChannelOption.SO_KEEPALIVE,true);
		
		
		this.server.
			group(this.bossLoopGroup, this.workLoopGroup).
			channel(NioServerSocketChannel.class).
			handler(new LoggingHandler(LogLevel.INFO)).
			childHandler(new NettyHttpInitializer());
	}

	@Override
	protected void startup() throws Exception {
		this.channelFuture = this.server.bind(this.port).sync();
	}

	@Override
	protected void shutdown() throws Exception {
		try {
			this.channelFuture.channel().close().sync();
		}finally {
			this.bossLoopGroup.shutdownGracefully().sync();
			this.workLoopGroup.shutdownGracefully().sync();
		}
	}
	
	
	public static void main(String[] args) {
		NettyHttpServer server = new NettyHttpServer();
		HttpEventDispatcher.getInstance().init(new MapConfiguration());
		
		server.init(new MapConfiguration());
		server.start();
		
		try {
			server.syncWait();
		} catch (InterruptedException e) {
			server.close();
		}
	}
}
