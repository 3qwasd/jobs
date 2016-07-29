/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;

/**
 * @author QiaoJian
 *
 */
public class Modu extends NetworkAlgorithms {
	
	double modu = 0;
	/**
	 * @param network
	 */
	public Modu(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		modu = 0;
		for(int i=0;i<network.nodeNum;i++){
			for(int j=0;j<network.nodeNum;j++){
				NetworkNode node_i = (NetworkNode) network.getNode(i);
				NetworkNode node_j = (NetworkNode) network.getNode(j);
				if(node_i.getAttribute("community_id") == node_j.getAttribute("community_id")){
					modu+=network.adjMatrix[i][j]-(node_i.getOutEdges().size()*node_j.getInEdges().size())/network.edgeNum;
				}
			}
		}
		modu = modu/network.edgeNum;
	}

	/**
	 * @return the modu
	 */
	public double getModu() {
		return modu;
	}
}
