package org.demy.gateway;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;

public class HttpGatewayInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline p = ch.pipeline();

		// 或者使用HttpRequestDecoder & HttpResponseEncoder

		p.addLast(new HttpServerCodec());

		//在处理POST消息体时需要加上

		p.addLast(new HttpObjectAggregator(1024*1024));
		p.addLast(new HttpServerExpectContinueHandler());
		p.addLast(new HttpGatewayHandler());
	}

}
