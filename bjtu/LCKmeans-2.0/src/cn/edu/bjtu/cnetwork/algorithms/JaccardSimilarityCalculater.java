/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.cnetwork.handler.SimilarityHandler;

/**
 * @author QiaoJian
 *
 */
public class JaccardSimilarityCalculater implements SimilarityHandler{

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.SimilarityHandler#calculate(cn.edu.bjtu.cnetwork.Network, cn.edu.bjtu.cnetwork.NetworkNode, cn.edu.bjtu.cnetwork.NetworkNode)
	 */
	@Override
	public double calculate(Network network, NetworkNode x, NetworkNode y) {
		// TODO Auto-generated method stub
		int k = network.getK(x.getId()-1, y.getId()-1);
		double similarity;
		similarity = network.jaccardSimilarities[k];
		if(similarity>=0)
			return similarity;
		
		List<NetworkNode> xNgbr = new ArrayList<>(x.getNeighbours());
		List<NetworkNode> yNgbr = new ArrayList<>(y.getNeighbours());
		//求交集
		xNgbr.retainAll(yNgbr);
		double intersection = xNgbr.size();
		//求并集
		xNgbr = new ArrayList<>(x.getNeighbours());
		yNgbr.removeAll(xNgbr);
		xNgbr.addAll(yNgbr);
		double union = xNgbr.size();
		similarity = intersection/union;
		network.jaccardSimilarities[k] = similarity;
		return similarity;
		
	}

}
