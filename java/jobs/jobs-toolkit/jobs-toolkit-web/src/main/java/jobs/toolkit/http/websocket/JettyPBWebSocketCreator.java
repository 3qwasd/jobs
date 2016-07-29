package jobs.toolkit.http.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class JettyPBWebSocketCreator implements WebSocketCreator{

	@Override
	public Object createWebSocket(ServletUpgradeRequest request, ServletUpgradeResponse response) {
		System.out.println(request);
		// TODO Auto-generated method stub
		for (String subprotocol : request.getSubProtocols()) {
			System.out.println(subprotocol);
			if ("binary".equals(subprotocol)) {
				response.setAcceptedSubProtocol(subprotocol);
				return new JettyPBWebSocketAdapter();
			}
		}
		return null;
	}

}
