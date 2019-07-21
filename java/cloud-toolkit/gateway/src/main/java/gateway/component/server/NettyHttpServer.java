package gateway.component.server;

import gateway.component.NettyHttpInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jobs.toolkit.service.BaseService;

public class NettyHttpServer extends BaseService{
	
	
	private volatile ChannelFuture channelFuture;
	
	private final NioEventLoopGroup eventLoopGroup;
	
	private final ServerBootstrap server;
	
	public NettyHttpServer() {
		super("NettyHttpServer");
		this.eventLoopGroup = new NioEventLoopGroup();
		this.server = new ServerBootstrap();
	}

	@Override
	protected void initialize() throws Exception {
		
		int port = this.getConfiguration().getInt("port", 8080);
		
		this.server.
			group(this.eventLoopGroup).
			channel(NioServerSocketChannel.class).
			localAddress(port).
			childHandler(new NettyHttpInitializer());
	}

	@Override
	protected void startup() throws Exception {
		this.channelFuture = this.server.bind().sync();
	}

	@Override
	protected void shutdown() throws Exception {
		try {
			this.channelFuture.channel().close().sync();
		}finally {
			this.eventLoopGroup.shutdownGracefully().sync();
		}
	}
	
	
	
}
