/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.List;

import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;

/**
 * @author QiaoJian
 *
 */
public class Entropy extends NetworkAlgorithms {
	
	private double entropyValue;
	/**
	 * @param network
	 */
	public Entropy(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		double[] ent_k = new double[network.topicNum];
		double ent = 0;
		List<Community> communites = network.getCommunities();
		for(int i=0;i<communites.size();i++){
			Community community = communites.get(i);
			double attr_sum = 0;
			double[] attr_p = new double[network.attrSize];
			for(int j=0;j<community.getNodes().size();j++){
				NetworkNode node = (NetworkNode) community.getNode(j);
				for(int n=0;n<network.attrSize;n++){
					if(node.nodeContentDouble[n] == 1){
						attr_sum++;
						attr_p[n]++;
					}
				}
			}
			for(int n=0;n<network.attrSize;n++){
				double p = attr_p[n]/attr_sum;
				if(p != 0)
					ent_k[i]+=-((p)*(Math.log(p)));
			}
		}
		for(int i=0;i<network.topicNum;i++){
			ent+=(communites.get(i).getNodes().size()/(double)network.nodeNum)*ent_k[i];
		}
		entropyValue = ent;
	}

	/**
	 * @return the entropyValue
	 */
	public double getEntropy() {
		return entropyValue;
	}
	
}
