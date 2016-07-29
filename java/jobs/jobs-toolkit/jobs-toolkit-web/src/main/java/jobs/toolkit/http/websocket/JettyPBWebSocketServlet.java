package jobs.toolkit.http.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * 
 * @author jobs
 *
 */
public class JettyPBWebSocketServlet extends WebSocketServlet{

	private static final long serialVersionUID = -600494456396112226L;
	
	public final int idleTime;
	
	public JettyPBWebSocketServlet(int idleTime) {
		super();
		this.idleTime = idleTime;
	}

	@Override
	public void configure(WebSocketServletFactory factory) {
		// TODO Auto-generated method stub
		 factory.getPolicy().setIdleTimeout(idleTime);
		 factory.register(JettyPBWebSocketAdapter.class);
	}
}
