/**
*
**/
package cn.edu.bjtu.cnetwork.test;

import org.junit.Test;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.algorithms.Simplify;
import cn.edu.bjtu.cnetwork.algorithms.SparseNetwork;
import cn.edu.bjtu.cnetwork.io.NetworkReader;

/**
 * @author QiaoJian
 *
 */
public class SparseNetworkTest {
	
	@Test
	public void testMain(){
		String networkName = "cora";
		Network network = null;
		NetworkReader networkReader = new NetworkReader();
		try {
			network = networkReader.readNodeWithTopics(networkName, true);
		} catch (Throwable e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(network.edgeNum);
		Simplify simplify = new Simplify(network);
		simplify.run();
		network = simplify.getSimplifyNetwork();
		System.out.println(network.edgeNum);
	}
}
