package gateway.message;

import java.net.URI;
import java.net.URL;

import io.netty.handler.codec.http.FullHttpRequest;
import jobs.toolkit.event.EventType;

public class HttpRequestEvent extends HttpMessageEvent{

	
	private final FullHttpRequest request;

	private final String contextPath;
	
	private final String serviceVersion;
	
	public HttpRequestEvent(FullHttpRequest request) {
		super(EventType.of(HttpRequestEvent.class.getName()));
		this.request = request;
		URI uri = new URI(request.uri());
		
		uri.get
	}

	public FullHttpRequest getRequest() {
		return request;
	}
	
	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServiceVersion() {
		// TODO Auto-generated method stub
		return null;
	}
}
