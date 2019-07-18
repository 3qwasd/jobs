package org.demy.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class HttpGateway {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpGateway.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.childOption(ChannelOption.TCP_NODELAY,true);
			b.childOption(ChannelOption.SO_KEEPALIVE,true);
			
			b.group(bossGroup, workerGroup).
			channel(NioServerSocketChannel.class).
			handler(new LoggingHandler(LogLevel.INFO)).
			childHandler(new HttpGatewayInitializer());

			Channel ch = b.bind(8080).sync().channel();
			
			LOGGER.info("Netty http server listening on port "+ 8080);
			
			ch.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
