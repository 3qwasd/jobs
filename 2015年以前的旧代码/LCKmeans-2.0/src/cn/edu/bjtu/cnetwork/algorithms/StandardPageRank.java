/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;
import java.util.Random;

import cn.edu.bjtu.cnetwork.ComparableValue;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.utils.MathUtils;

/**
 * @author QiaoJian
 *
 */
public class StandardPageRank extends NetworkAlgorithms{
	
	/* r=G*q,G=αS+(1-α)1/nU */
	public double C = 0.85;
	private double[][] G;
	private double[] q;
	private double[] r;
	public double mu = 0.001;
	/**
	 * @param network
	 */
	public StandardPageRank(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 计算矩阵G
	 */
	private void init(){
		double U = (1-C)*1.0D/network.nodeNum;
		q = new double[network.nodeNum];
		r = new double[network.nodeNum];
		G = new double[network.nodeNum][network.nodeNum];
		Random random = new Random();
		for(int i=0;i<network.nodeNum;i++){
			NetworkNode node = (NetworkNode) network.getNode(i); 
			double num = node.getOutEdges().size();
			for(int j=0;j<network.nodeNum;j++){
				if(num == 0)
					G[j][i] = U;
				else{
					G[j][i] = C*network.adjMatrix[i][j]/num+U;
				}
			}
			q[i] = network.getNodeNum()*random.nextDouble();
		}
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		init();
		
		double distance = 1;
		while(distance>mu){
			distance = 0;
			for(int i=0;i<network.nodeNum;i++){
				double value = MathUtils.vectorMulti(G[i], q);
				r[i] = value;
				distance+=(r[i]-q[i])*(r[i]-q[i]);
			}
			distance = Math.sqrt(distance);
			q = r;
			r = new double[network.nodeNum];
			//System.out.println(distance);
		}
		G = null;
		r = null;
		network.initPageRankValues();
		for(int i=0;i<q.length;i++){
			ComparableValue prVal = new ComparableValue(i+1,q[i]);
			network.pageRankValues.add(prVal);
			network.getNode(i).putAttribute("pagerank", prVal);
		}
	}

}
