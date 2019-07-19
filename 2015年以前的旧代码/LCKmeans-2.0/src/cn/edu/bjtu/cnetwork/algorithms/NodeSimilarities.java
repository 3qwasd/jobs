/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.algorithms;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.cnetwork.handler.SimilarityHandler;

/**
 * @author QiaoJian
 *
 */
public class NodeSimilarities extends NetworkAlgorithms {
	
	SimilarityHandler similarityHandler;
	
	/**
	 * @param network
	 */
	public NodeSimilarities(Network network,SimilarityHandler similarityHandler) {
		super(network);
		// TODO Auto-generated constructor stub
		this.similarityHandler = similarityHandler;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		int num = (network.nodeNum-1)*network.nodeNum/2;
		
		for(int i=0;i<network.nodeNum;i++){
			for(int j=i+1;j<network.nodeNum;j++){
				double similarity = similarityHandler.calculate(network,(NetworkNode)network.getNode(i), (NetworkNode)network.getNode(j));
				int k = i*(network.nodeNum-i-1)+i*(i+1)/2+(j-i-1);
				network.similarities[k] = similarity;
				if(valueHandler!=null)
					valueHandler.handlerValue(similarity);
			}
		}
		
	}
	
	
}
