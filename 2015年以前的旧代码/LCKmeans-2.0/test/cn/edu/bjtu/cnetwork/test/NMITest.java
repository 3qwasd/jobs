/**
*
**/
package cn.edu.bjtu.cnetwork.test;

import java.util.HashMap;

import org.junit.Test;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.algorithms.NMI;

/**
 * @author QiaoJian
 *
 */
public class NMITest {
	
	@Test
	public void testNMI(){
		Network network = new Network();
		int i=1;
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",1+"");put("class_flag_new",1+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",1+"");put("class_flag_new",2+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",1+"");put("class_flag_new",1+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",1+"");put("class_flag_new",1+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",1+"");put("class_flag_new",1+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",1+"");put("class_flag_new",1+"");}});
		
		
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",2+"");put("class_flag_new",1+"");}});
		network.createNode(1, "1", 
				new HashMap<String, Object>(){{put("class_flag",2+"");put("class_flag_new",2+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",2+"");put("class_flag_new",2+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",2+"");put("class_flag_new",2+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",2+"");put("class_flag_new",2+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",2+"");put("class_flag_new",3+"");}});
		
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",3+"");put("class_flag_new",1+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",3+"");put("class_flag_new",1+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",3+"");put("class_flag_new",3+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",3+"");put("class_flag_new",3+"");}});
		network.createNode(i++, "1", 
				new HashMap<String, Object>(){{put("class_flag",3+"");put("class_flag_new",3+"");}});
		
		network.nodeNum = network.getNodes().size();
		network.topicNum = 3;
		
		NMI nmi = new NMI(network);
		
		nmi.run();
		System.out.println(nmi.getNmi());
	}
}
