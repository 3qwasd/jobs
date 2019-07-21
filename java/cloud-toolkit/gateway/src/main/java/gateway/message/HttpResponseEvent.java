package gateway.message;

import io.netty.handler.codec.http.FullHttpResponse;
import jobs.toolkit.event.EventType;

public class HttpResponseEvent extends HttpMessageEvent{

	private final FullHttpResponse response;
	
	public HttpResponseEvent(FullHttpResponse response) {
		super(EventType.of(HttpResponseEvent.class.getName()));
		this.response = response;
	}

	public FullHttpResponse getResponse() {
		return response;
	}
}
