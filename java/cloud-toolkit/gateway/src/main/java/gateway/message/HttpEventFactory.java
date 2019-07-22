package gateway.message;

import io.netty.handler.codec.http.FullHttpRequest;

public class HttpEventFactory {

	public static final HttpRequestEvent newRequest(FullHttpRequest nettyHttpRequest) {
		
		return new HttpRequestEvent(nettyHttpRequest);
	}
}
