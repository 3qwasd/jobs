package jobs.toolkit.http;

public class CFG {
	public static final String NAME_HTTP_PORT = "http_server_port";
	public static final String NAME_USE_SPRING = "use_spring";
	public static final String NAME_SPRING_CONTEXT_CFG_LOCAL = "contextConfigLocation";
	public static final String NAME_JERSEY_PACKAGES = "jersey.config.server.provider.packages";
	public static final String NAME_RESTFULL_PACKAGES = "restfull_packages";
	public static final String VALUE_SPRING_CONTEXT_CFG_LOCAL = "classpath:applicationContext.xml";
	public static final String NAME_USE_JMX = "use_jmx";
	public static final String NAME_RESTFULL_PATH = "rest_path";
	public static final String NAME_USE_WEBSOCKET = "use_websocket";
	public static final String NAME_WEBSOCKET_PATH = "ws_path";
	public static final String NAME_STATIC_RESOURCE_PATH = "static_res_path";
	public static final String NAME_WEBSOCKET_SESSION_IDLETIME = "ws_session_idletime";
	public static final String NAME_MESSAGE_PACKAGE = "message_package";
	public static final String NAME_MESSAGES = "messages";
	public static final String NAME_CODE = "code";
	public static final String NAME_CLASS = "class";
	public static final String NAME_HANDLER = "handler";
	public static final int DEFAULT_WEBSOCKET_SESSION_IDLETIME = 30*1000;
}
