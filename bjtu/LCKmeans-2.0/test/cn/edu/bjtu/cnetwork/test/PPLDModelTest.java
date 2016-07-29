/**
*
**/
package cn.edu.bjtu.cnetwork.test;

import java.io.IOException;

import org.junit.Test;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.algorithms.PPLDModel;
import cn.edu.bjtu.cnetwork.io.NetworkReader;

/**
 * @author QiaoJian
 *
 */
public class PPLDModelTest {
	
	@Test
	public void testPPLD(){
		String networkName = "cora";
		Network network = null;
		NetworkReader networkReader = new NetworkReader();
		try {
			network = networkReader.readNodeWithTopics(networkName, true);
		} catch (Throwable e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PPLDModel ppldModel = new PPLDModel(network);
		
		ppldModel.init();
		ppldModel.iterNum = 0;
		ppldModel.resetParams();
		ppldModel.computeKParams();
		ppldModel.computeLikelihood();
		System.out.println(ppldModel.currLikelihood);
		ppldModel.lastLikelihood = ppldModel.currLikelihood-1;
		while(!ppldModel.isFinish()){
			System.out.println("iter num:"+ppldModel.iterNum+"\tlikelihood:"+ppldModel.currLikelihood);
			ppldModel.computeQijk();
			ppldModel.computeZetaParams();
			ppldModel.computeMainParams();
			ppldModel.computeKParams();
			ppldModel.computeLikelihood();
			ppldModel.resetParams();
			ppldModel.iterNum++;
		}
		
		try {
			ppldModel.savePPLD();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
