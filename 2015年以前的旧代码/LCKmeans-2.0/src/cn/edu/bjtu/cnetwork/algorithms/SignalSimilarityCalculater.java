/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.algorithms;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.cnetwork.handler.SimilarityHandler;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.TriplesTable;

/**
 * @author QiaoJian
 *
 */
public abstract class SignalSimilarityCalculater implements SimilarityHandler {

	/**
	 * 
	 */
	public SignalSimilarityCalculater() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.SimilarityHandler#calculate(cn.edu.bjtu.cnetwork.Network, cn.edu.bjtu.cnetwork.NetworkNode, cn.edu.bjtu.cnetwork.NetworkNode)
	 */
	@Override
	public abstract double calculate(Network network, NetworkNode x, NetworkNode y);
	
	/*
	 * 信号向量标准化
	 */
	double[] singalStandardize(Network network,int nodeId){
		double[] standardizeSingal = new double[network.nodeNum];
		TriplesTable<Double> signalTriples = network.signalTriplesTable;
		int rowPos = signalTriples.postions[nodeId-1];
		int nextRowPos = nodeId==signalTriples.rn?signalTriples.length:signalTriples.postions[nodeId];
		double sum = 0;
		for(int pos = rowPos;pos<nextRowPos;pos++){
			sum+=signalTriples.datas[pos].getWeight();
		}
		for(int pos = rowPos;pos<nextRowPos;pos++){
			standardizeSingal[signalTriples.datas[pos].col] = signalTriples.datas[pos].getWeight()/sum;
		}
		return standardizeSingal;
	}

}
