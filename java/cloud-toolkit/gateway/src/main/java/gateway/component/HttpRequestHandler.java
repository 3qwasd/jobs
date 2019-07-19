package gateway.component;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpRequestHandler extends SimpleChannelInboundHandler<HttpObject>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		
		//FullHttpRequest httpRequest = (FullHttpRequest) msg;
		
		var response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer("Hello World!".getBytes(CharsetUtil.UTF_8)));
		
		response.headers().set("Content-Type", "text/plain");
		response.headers().setInt("Content-Length", response.content().readableBytes());
		response.headers().set("Connection", "keep-alive");
		
		ctx.writeAndFlush(response);
	}

}
