/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.utils.MathUtils;

/**
 * @author QiaoJian
 *
 */
public class JTopologySimilarityCalculater extends TopologySimilarityCalculater {
	
	@Override
	public double calculate(Network network, NetworkNode x, NetworkNode y) {
		// TODO Auto-generated method stub
		int k = network.getK(x.getId()-1, y.getId()-1);
		double similarity = network.topologySimilarities[k];
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
		if(union == 0) similarity = 0;
		else similarity = intersection/union;
		network.topologySimilarities[k] = similarity;
		return similarity;
	}
}
