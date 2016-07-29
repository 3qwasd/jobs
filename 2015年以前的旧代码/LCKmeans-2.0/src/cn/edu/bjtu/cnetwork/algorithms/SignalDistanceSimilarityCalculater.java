/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.algorithms;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.utils.MathUtils;

/**
 * @author QiaoJian
 *
 */
public class SignalDistanceSimilarityCalculater extends SignalSimilarityCalculater{

	/**
	 * 
	 */
	public SignalDistanceSimilarityCalculater() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.SignalSimilarityCalculater#calculate(cn.edu.bjtu.cnetwork.Network, cn.edu.bjtu.cnetwork.NetworkNode, cn.edu.bjtu.cnetwork.NetworkNode)
	 */
	@Override
	public double calculate(Network network, NetworkNode x, NetworkNode y) {
		// TODO Auto-generated method stub
		double[] a = singalStandardize(network,x.getId());
		double[] b = singalStandardize(network,y.getId());
		
		double similarity = MathUtils.vectorDistance(a, b);
		
 		return MathUtils.distanceNormalization(similarity);
	}

}
