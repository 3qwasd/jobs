/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.HashMap;
import java.util.Map;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;

/**
 * @author QiaoJian
 * normalized mutual information标准互信息，用来评测聚类效果
 */
public class NMI extends NetworkAlgorithms {
	
	double[] p_x;
	double[] p_y; 
	double[][] p_unit;
	private double NMIValue = 0;
	Map<String,Integer> label2Id = new HashMap<>();
	/**
	 * @param network
	 */
	public NMI(Network network) {
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
		p_x = new double[network.topicNum];
		p_y = new double[network.topicNum];
		p_unit = new double[network.topicNum][network.topicNum];
		for(int i=0;i<network.nodeNum;i++){
			NetworkNode node = (NetworkNode) network.getNode(i);
//			System.out.println(node.getName());
//			System.out.println(node.getAttribute("class_flag"));
//			System.out.println(node.getAttribute("class_flag_new"));
			int x = getLabelId(((String) node.getAttribute("class_flag")).trim());
			int y = getLabelId(((String) node.getAttribute("class_flag_new")).trim());	
//			System.out.println(x);
//			System.out.println(y);
//			System.out.println(label2Id);
			p_unit[x][y]++;
			p_x[x]++;
			p_y[y]++;
		}
		for(int i=0;i<network.topicNum;i++){
			p_x[i] = p_x[i]/network.nodeNum;
			p_y[i] = p_y[i]/network.nodeNum;
			for(int j=0;j<network.topicNum;j++){
				p_unit[i][j] = p_unit[i][j]/network.nodeNum;
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		double mi = 0;
		double h_x = 0;
		double h_y = 0;
		for(int x=0;x<network.topicNum;x++){
			for(int y=0;y<network.topicNum;y++){
				if(p_unit[x][y] == 0||p_x[x]==0||p_y[y]==0) continue;
				mi += p_unit[x][y]*Math.log(p_unit[x][y]/(p_x[x]*p_y[y]));
			}
			if(p_x[x] == 0) continue;
			h_x += p_x[x]*Math.log(1/p_x[x]);
			if(p_y[x] == 0) continue;
			h_y += p_y[x]*Math.log(1/p_y[x]);
		}
		//NMIValue = mi/Math.max(h_x, h_y);
		NMIValue = 2.0*mi/(h_x+h_y);
	}

	private int getLabelId(String lable){
		if(label2Id.containsKey(lable)){
			return label2Id.get(lable);
		}
		
		int id = label2Id.size();
		label2Id.put(lable, id);
		return id;
	}


	/**
	 * @return the nmi
	 */
	public double getNmi() {
		return NMIValue;
	}
}
