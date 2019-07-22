package gateway.component.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;

public class NettyHttpInitializer extends ChannelInitializer<Channel>{

	@Override
	protected void initChannel(Channel ch) throws Exception {
		var pipeline = ch.pipeline();
		
		pipeline.addLast("serverCodec", new HttpServerCodec());
		pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024));
		pipeline.addLast("respondExpectContinue",new HttpServerExpectContinueHandler());
		pipeline.addLast("requestHandler", new NettyHttpRequestHandler());
	}
}
