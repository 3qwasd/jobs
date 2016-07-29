package jobs.toolkit.http;

import java.lang.management.ManagementFactory;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.web.context.ContextLoaderListener;
import jobs.toolkit.http.websocket.JettyPBWebSocketServlet;

public class JettyRestFullHttpServer extends HttpServer implements Runnable{
	
	private Server httpServer;
	
	public JettyRestFullHttpServer(String name) {
		super(name);
	}
	public JettyRestFullHttpServer() {
		super(JettyRestFullHttpServer.class.getSimpleName());
	}
	
	@Override
	protected void initialize() throws Exception {
		// TODO Auto-generated method stub
		super.initialize();
		this.httpServer = new Server(this.getConfiguration().getInt(CFG.NAME_HTTP_PORT));
		
		//配置JMX
		if(this.getConfiguration().getBoolean(CFG.NAME_USE_JMX)){
	        MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
	        this.httpServer.addBean(mbContainer);
		}
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		//配置SPRING
		if(this.getConfiguration().getBoolean(CFG.NAME_USE_SPRING)){
			context.setInitParameter(CFG.NAME_SPRING_CONTEXT_CFG_LOCAL, CFG.VALUE_SPRING_CONTEXT_CFG_LOCAL);
			context.addEventListener(new ContextLoaderListener());
		}
		
		//配置RestFull服务
		ServletHolder restServlet = new ServletHolder(ServletContainer.class);
		restServlet.setInitParameter(CFG.NAME_JERSEY_PACKAGES, this.getConfiguration().getString(CFG.NAME_RESTFULL_PACKAGES));
		String restPath = this.getConfiguration().getString(CFG.NAME_RESTFULL_PATH);
		if(restPath == null || restPath.isEmpty()) restPath = "/*";
		context.addServlet(restServlet, restPath);
		
		//配置WebSocket服务
		if(this.getConfiguration().getBoolean(CFG.NAME_USE_WEBSOCKET)){
			ServletHolder wsServlet = new ServletHolder();
			int idleTime = this.getConfiguration().getInt(CFG.NAME_WEBSOCKET_SESSION_IDLETIME);
			wsServlet.setServlet(new JettyPBWebSocketServlet(idleTime > 0 ? idleTime * 1000 : CFG.DEFAULT_WEBSOCKET_SESSION_IDLETIME));
			String wsPath = this.getConfiguration().getString(CFG.NAME_WEBSOCKET_PATH);
			if(wsPath == null || wsPath.isEmpty()) wsPath = "/ws";
			context.addServlet(wsServlet, wsPath);
		}
		
		//配置静态资源
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setResourceBase(this.getConfiguration().getString(CFG.NAME_STATIC_RESOURCE_PATH));
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] {resourceHandler, context});
		
		httpServer.setHandler(handlers);
	}

	@Override
	protected void startup() throws Exception {
		super.startup();
		this.httpServer.start();
	}

	@Override
	protected void shutdown() throws Exception {
		super.shutdown();
		this.httpServer.setStopAtShutdown(true);
		this.httpServer.setStopTimeout(10000L);
		new Thread(this).start();
	}

	@Override
	public void syncWait() {
		try {
			if(this.httpServer.isRunning())
				this.httpServer.join();
		} catch (InterruptedException e) {
			LOG.error(String.format("Http service [%1$s] has interrupted!", this.getName()), e);
			this.stop();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.httpServer.stop();
			this.httpServer.destroy();
		} catch (Throwable _e) {
			LOG.error("JettyRestFullHttpServer[name=" + this.getName() + "] occur some error when shutting down!", _e);
		} finally {
			LOG.info("JettyRestFullHttpServer[name=" + this.getName() + "] has been stopped and the JVM will be exit!");
			System.exit(0);
		}
	}
	
}
