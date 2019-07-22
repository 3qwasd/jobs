package gateway.component;

import gateway.message.HttpMessageEvent;
import gateway.message.HttpRequestEvent;
import gateway.message.HttpResponseEvent;
import jobs.toolkit.event.BaseDispatcher;
import jobs.toolkit.event.EventType;


public final class HttpEventDispatcher extends BaseDispatcher<HttpMessageEvent>{

	private static final HttpEventDispatcher instance = new HttpEventDispatcher();
	
	private HttpEventDispatcher() {
		super("HttpEventDispatcher");
	}
	
	public static final HttpEventDispatcher getInstance(){
		return instance;
	}

	@Override
	protected void initialize() throws Exception {
		super.initialize();
		this.register(EventType.of(HttpRequestEvent.class.getName()), new HttpRequestHandler());
		this.register(EventType.of(HttpResponseEvent.class.getName()), new HttpResponseHandler());
	}
}
