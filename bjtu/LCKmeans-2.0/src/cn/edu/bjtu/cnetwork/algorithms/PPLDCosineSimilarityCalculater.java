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
public class PPLDCosineSimilarityCalculater implements SimilarityHandler{

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.SimilarityHandler#calculate(cn.edu.bjtu.cnetwork.Network, cn.edu.bjtu.cnetwork.NetworkNode, cn.edu.bjtu.cnetwork.NetworkNode)
	 */
	@Override
	public double calculate(Network network, NetworkNode x, NetworkNode y) {
		// TODO Auto-generated method stub
		double[] a = (double[]) x.getAttribute("ppld");
		double[] b = (double[]) y.getAttribute("ppld");
		
		double similarity = MathUtils.vectorCos(a, b);
		
 		//return MathUtils.cosNormalization(similarity);
		return similarity;
	}

}
