package gateway.message;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import jobs.toolkit.event.EventType;

public class HttpRequestEvent extends HttpMessageEvent{

	
	private final FullHttpRequest request;

	private final Pair<String, String> contextPathAndVersion;
	
	private static final char PATH_SPLIT = '/';
	
	public HttpRequestEvent(FullHttpRequest request) {
		super(EventType.of(HttpRequestEvent.class.getName()));
		this.request = request;
		this.contextPathAndVersion = this.extractContextPathAndVersion(request.uri());
	}
	
	private Pair<String, String> extractContextPathAndVersion(String uri){
		String[] pathSegs = StringUtils.split(uri, PATH_SPLIT);
		if(pathSegs.length < 2) {
			throw new IllegalStateException("Invalid path of " + uri);
		}
		return Pair.of(pathSegs[0], pathSegs[1]);
	}
	
	private void parseHeaders() {
		HttpHeaders headers = this.request.headers();
		for(var header : headers) {
			header.getKey();
			header.getValue();
		}
		Map<String, String> map = new HashMap<>();
		map.
	}
	
	public String getSessionId() {
		
	}
	
	public FullHttpRequest getRequest() {
		return request;
	}
	
	public String getContextPath() {
		return contextPathAndVersion.getKey();
	}

	public String getServiceVersion() {
		return contextPathAndVersion.getValue();
	}
}
