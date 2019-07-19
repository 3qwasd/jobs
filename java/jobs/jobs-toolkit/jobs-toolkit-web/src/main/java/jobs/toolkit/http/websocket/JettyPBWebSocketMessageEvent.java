package jobs.toolkit.http.websocket;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WriteCallback;

import com.google.protobuf.Message;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;
import jobs.toolkit.protocol.PBMessageEvent;

public class JettyPBWebSocketMessageEvent extends PBMessageEvent implements WriteCallback{
	
	protected final Log LOG = LogFactory.getLog(this.getClass());
	
	private final Session session;
	
	public JettyPBWebSocketMessageEvent(Session session) {
		super();
		this.session = session;
	}

	public JettyPBWebSocketMessageEvent(Session session, short code, Message message) {
		super(code, message);
		this.session = session;
	}
	
	public void transfer(){
		this.session.getRemote().sendBytes(this.marshalToByteBuffer(), this);
	}

	@Override
	public void writeFailed(Throwable _t) {
		// TODO Auto-generated method stub
		this.session.close();
		LOG.error("", _t);
	}

	@Override
	public void writeSuccess() {
		// TODO Auto-generated method stub
		
	}
	
}
