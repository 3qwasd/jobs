/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.algorithms;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.utils.MathUtils;

/**
 * @author QiaoJian
 * 计算余弦相似度
 */
public class SignalCosineSimilarityCalculater extends SignalSimilarityCalculater{

	/**
	 * 
	 */
	public SignalCosineSimilarityCalculater() {
		// TODO Auto-generated constructor stub
		super();
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.SimilarityHandler#calculate(cn.edu.bjtu.cnetwork.Network, cn.edu.bjtu.cnetwork.NetworkNode, cn.edu.bjtu.cnetwork.NetworkNode)
	 */
	@Override
	public double calculate(Network network, NetworkNode x, NetworkNode y) {
		// TODO Auto-generated method stub
		double[] a = singalStandardize(network,x.getId());
		double[] b = singalStandardize(network,y.getId());
		
		double similarity = MathUtils.vectorCos(a, b);
		
 		//return MathUtils.cosNormalization(similarity);
		return similarity;
	}
	
	
}
