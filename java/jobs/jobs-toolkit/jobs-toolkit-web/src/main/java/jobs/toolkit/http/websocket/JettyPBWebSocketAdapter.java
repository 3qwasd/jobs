package jobs.toolkit.http.websocket;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;
import jobs.toolkit.protocol.MessageEventDispatcher;




public class JettyPBWebSocketAdapter extends WebSocketAdapter {
	
	protected final Log LOG = LogFactory.getLog(this.getClass());
	
	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		// TODO Auto-generated method stub
		JettyPBWebSocketMessageEvent event = new JettyPBWebSocketMessageEvent(this.getSession());
		event.unMarshal(payload, offset, len);
		MessageEventDispatcher.dispatchEvent(event);
	}	
}
