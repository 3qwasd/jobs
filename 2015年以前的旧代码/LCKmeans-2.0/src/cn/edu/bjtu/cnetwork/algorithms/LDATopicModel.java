/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import jgibblda.Estimator;
import jgibblda.LDACmdOption;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;

/**
 * @author QiaoJian
 *
 */
public class LDATopicModel extends NetworkAlgorithms{
	
	public int K = 0;
	/**
	 * @param network
	 */
	public LDATopicModel(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		LDACmdOption option = new LDACmdOption();
		if(K == 0)
			option.K = network.topicNum;
		else
			option.K = K;
		option.est = true;
		Estimator estimator = new Estimator();
		estimator.init(option,network.ldaDataset);
		estimator.estimate();
		double[][] topicDis = estimator.getTrnModel().theta;
		for(int i=0;i<network.nodeNum;i++){
			NetworkNode node = (NetworkNode) network.getNode(i);
			node.nodeTopicDis = topicDis[i];
		}
	}

}
