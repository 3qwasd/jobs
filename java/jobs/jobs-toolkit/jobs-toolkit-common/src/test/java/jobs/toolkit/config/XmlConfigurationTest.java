package jobs.toolkit.config;



import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import jobs.toolkit.config.Configuration;
import jobs.toolkit.config.XmlConfiguration;


public class XmlConfigurationTest {
	
	XmlConfiguration xioConfig;
	@Before
	public void setUp() throws Exception {
		String configFile = XmlConfigurationTest.class.getResource("..").getPath().concat("resource/gs.xio.xml");
		xioConfig = new XmlConfiguration();
		xioConfig.parse(configFile);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testParse() {
		XmlConfiguration config = new XmlConfiguration();
		try{
			config.parse("D:\\config.file");
			Assert.fail();
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		try{
			String errorFile = XmlConfigurationTest.class.getResource("..").getPath().concat("resource/error_gs.xio.xml");
			config.parse(errorFile);
			Assert.fail();
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		try{
			String configFile = XmlConfigurationTest.class.getResource("..").getPath().concat("resource/gs.xio.xml");
			config.parse(configFile);
		}catch(RuntimeException e){
			Assert.fail();
		}
	}
	@Test
	public void testGetAttribute(){
		Assert.assertEquals("gs", xioConfig.getAttribute("name"));
		Assert.assertEquals(null, xioConfig.getAttribute("noattribute"));
		//integer
		try{
			xioConfig.getInt("name");
			Assert.fail();
		}catch(Exception e){
			e.printStackTrace();
		}
		Assert.assertEquals(Integer.MIN_VALUE, xioConfig.getInt("noint"));
		Assert.assertEquals(131072, xioConfig.getInt("inputBufferSize"));
		//long
		try{
			xioConfig.getLong("name");
			Assert.fail();
		}catch(Exception e){
			e.printStackTrace();
		}
		Assert.assertEquals(Long.MIN_VALUE, xioConfig.getLong("nolong"));
		Assert.assertEquals(10485760L, xioConfig.getLong("outputBufferSize"));
		//boolean
		Assert.assertFalse(xioConfig.getBoolean("name"));
		Assert.assertTrue(xioConfig.getBoolean("tcpNoDelay"));
		Assert.assertEquals(null, xioConfig.getBoolean("nonevalue"));
		boolean flag = Boolean.valueOf(xioConfig.getString("nonevalue"));
		Assert.assertFalse(flag);
	}
	@Test
	public void testGetSubConfigs(){
		Assert.assertEquals(4, xioConfig.getSubConfigs().size());
		List<Configuration> subConfigs = xioConfig.getSubConfigs();
		for(int i=0; i<subConfigs.size();i++){
			XmlConfiguration configuration = (XmlConfiguration) subConfigs.get(i);
			Assert.assertEquals(Node.ELEMENT_NODE, configuration.config.getNodeType());
			Assert.assertEquals("Manager", configuration.config.getNodeName());
		}
		List<Configuration> subConfigs2 = subConfigs.get(0).getSubConfigs();
		Assert.assertEquals(2, subConfigs2.size());
		XmlConfiguration conn = (XmlConfiguration) subConfigs2.get(0);
		XmlConfiguration code = (XmlConfiguration) subConfigs2.get(1);
		Assert.assertEquals(conn.config.getNodeType(), Node.ELEMENT_NODE);
		Assert.assertEquals(code.config.getNodeType(), Node.ELEMENT_NODE);
		Assert.assertEquals(conn.config.getNodeName(), "Connector");
		Assert.assertEquals(code.config.getNodeName(), "Coder");
	}
}
