package jobs.toolkit.http;

import jobs.toolkit.support.yaml.YamlConfiguration;

public class JettyHttpServerTest {
	
	public static void main(String[] args) throws Exception{
		JettyRestFullHttpServer httpServer = new JettyRestFullHttpServer();
		YamlConfiguration configuration = new YamlConfiguration();
		String classPath = JettyHttpServerTest.class.getClassLoader().getResource(".").getPath();
		System.out.println(classPath);
		configuration.parse(classPath + "jetty_rest_http_server.yaml");
		httpServer.init(configuration);
		httpServer.start();
		httpServer.syncWait();
	}
}
