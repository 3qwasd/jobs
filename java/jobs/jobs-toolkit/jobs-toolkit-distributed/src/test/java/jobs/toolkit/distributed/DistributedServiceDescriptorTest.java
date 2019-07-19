package jobs.toolkit.distributed;

import java.net.InetSocketAddress;
import java.net.URI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DistributedServiceDescriptorTest {
	
	DistributedServiceDescriptor des = new DistributedServiceDescriptor();
	
	@Before
	public void setUp() throws Exception {
		InetSocketAddress newAddress = new InetSocketAddress("192.168.128.130", 8080);
		URI uri = new URI("http://localhost:8080/res/proto");
		this.des.setServiceName("testServicename");
		this.des.setServiceBaseURI(uri);
		this.des.setServiceAddress(newAddress);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToJson() {
		System.out.println(this.des.toJson());
	}

	@Test
	public void testFromJson() {
		
	}

	@Test
	public void testToBinary() {
		System.out.println("".getBytes().length);
		System.out.println(this.des.toBinary());
	}

	@Test
	public void testFromBinary() {
		
	}

}
