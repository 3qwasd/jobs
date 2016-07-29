/**
 *
 **/
package cn.edu.bjtu.cnetwork.algorithms;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.cnetwork.handler.SimilarityHandler.MathType;
import cn.edu.bjtu.utils.MathUtils;

/**
 * @author QiaoJian
 *
 */
public class TopicDisSimilarityCalculater extends ContentSimilarityCalculater {

	public static MathType mathType = MathType.cosin; 
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.SimilarityHandler#calculate(cn.edu.bjtu.cnetwork.Network, cn.edu.bjtu.cnetwork.NetworkNode, cn.edu.bjtu.cnetwork.NetworkNode)
	 */
	@Override
	public double calculate(Network network, NetworkNode x, NetworkNode y) {
		// TODO Auto-generated method stub
		int k = network.getK(x.getId()-1, y.getId()-1);
		double similarity = network.contentSimilarities[k];
		if(similarity>=0)
			return similarity;

		double[] a = x.nodeTopicDis;
		double[] b = y.nodeTopicDis;
		switch (mathType) {
		case euclidean:
			similarity = MathUtils.normalizationEucliDistance(a, b);
			break;

		case cosin:
			similarity = MathUtils.vectorCos(a, b);
			break;
		}
		network.contentSimilarities[k] = similarity;
		//return MathUtils.cosNormalization(similarity);
		return similarity;
	}
}
