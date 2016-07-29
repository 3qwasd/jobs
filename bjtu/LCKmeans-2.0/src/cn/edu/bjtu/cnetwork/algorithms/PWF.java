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
public class PWF extends NetworkAlgorithms {
	
	
	private double pwfValue;
	/**
	 * @param network
	 */
	public PWF(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#beforeExecute()
	 */
	@Override
	void beforeExecute() {
		// TODO Auto-generated method stub
		super.beforeExecute();
		
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		//集合T的基 
		double card_T = 0;
		//集合S的基
		double card_S = 0;
		//集合S与T的交集的基
		double card_S_T = 0;
		/*计算各变量*/
		for(int i=0;i<network.nodeNum;i++){
			for(int j=i+1;j<network.nodeNum;j++){
				NetworkNode node_i = (NetworkNode) network.getNode(i);
				NetworkNode node_j = (NetworkNode) network.getNode(j);
				boolean flag_T;
				boolean flag_S;
				if((flag_T=node_i.getAttribute("class_flag").equals(node_j.getAttribute("class_flag"))))
					card_T++;
				if((flag_S=node_i.getAttribute("community_id").equals(node_j.getAttribute("community_id"))))
					card_S++;
				if(flag_T&&flag_S)
					card_S_T++;
			}
		}
		//System.out.println("S="+card_S+";T="+card_T+";S^T="+card_S_T);
		/*计算pwf*/
		pwfValue = (2*(card_S_T/card_S)*(card_S_T/card_T))/(card_S_T/card_S+card_S_T/card_T);
	}

	/**
	 * @return the pwfValue
	 */
	public double getPwf() {
		return pwfValue;
	}
	
	
}
