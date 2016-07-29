/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.cnetwork.handler.SimilarityHandler;
import cn.edu.bjtu.utils.MathUtils;

/**
 * @author QiaoJian
 *
 */
public class TopologySimilarityCalculater implements SimilarityHandler{
	
	
	
	public static MathType mathType = MathType.cosin; 
	
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.SimilarityHandler#calculate(cn.edu.bjtu.cnetwork.Network, cn.edu.bjtu.cnetwork.NetworkNode, cn.edu.bjtu.cnetwork.NetworkNode)
	 */
	@Override
	public double calculate(Network network, NetworkNode x, NetworkNode y) {
		// TODO Auto-generated method stub
		int k = network.getK(x.getId()-1, y.getId()-1);
		double similarity = network.topologySimilarities[k];
		if(similarity>=0)
			return similarity;
		
		double[] a = network.singalStandardize(x.getId());
		double[] b = network.singalStandardize(y.getId());
		switch (mathType) {
		case euclidean:
			similarity = MathUtils.normalizationEucliDistance(a, b);
			break;

		case cosin:
			similarity = MathUtils.vectorCos(a, b);
			break;
		}
		
		network.topologySimilarities[k] = similarity;
 		//return MathUtils.cosNormalization(similarity);
		return similarity;
	}

}
