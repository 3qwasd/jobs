package gateway;

import org.junit.Test;

import gateway.component.server.NettyHttpServer;
import jobs.toolkit.config.MapConfiguration;

public class NettyHttpServerTest {

	@Test
	public void test() {
		
		NettyHttpServer server = new NettyHttpServer();
		
		server.init(new MapConfiguration());
		
		server.start();
		
		try {
			server.syncWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
