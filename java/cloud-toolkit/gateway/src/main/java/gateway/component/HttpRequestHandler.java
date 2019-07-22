package gateway.component;

import gateway.message.HttpRequestEvent;
import jobs.toolkit.event.EventHandler;

public class HttpRequestHandler implements EventHandler<HttpRequestEvent>{

	@Override
	public void doHandle(HttpRequestEvent event) throws Throwable {
		
		System.out.println(event.getContextPath());
		System.out.println(event.getServiceVersion());
	}

	@Override
	public void doExcepion(HttpRequestEvent event, Throwable e) {
		e.printStackTrace();
	}

}
