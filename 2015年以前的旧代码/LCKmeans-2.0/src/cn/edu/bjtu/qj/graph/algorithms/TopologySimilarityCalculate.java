/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import cn.edu.bjtu.qj.graph.component.Graph;
import cn.edu.bjtu.qj.graph.component.Node;

/**
 * @author QiaoJian
 *
 */
public class TopologySimilarityCalculate implements ISimilarityCalculate {

	/**
	 * 
	 */
	public TopologySimilarityCalculate() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.algorithms.ISimilarityCalculate#calculate(cn.edu.bjtu.qj.graph.component.Node, cn.edu.bjtu.qj.graph.component.Node)
	 */
	@Override
	public double calculate(Graph graph,Node node_x,Node node_y) {
		// TODO Auto-generated method stub
		
		double euclideanDistance = 0;
		double[] xSimVector = (double[]) node_x.getAttribute("similarity_vector");
		double[] ySimVector = (double[]) node_x.getAttribute("similarity_vector");
		if(xSimVector == null){
			xSimVector = new double[graph.getNodeNum()];
			node_x.putAttribute("similarity_vector", xSimVector);
		}
		euclideanDistance = xSimVector[node_y.getId()-1];
		if( euclideanDistance == 0){
			double[] xSinalVector = (double[]) node_x.getAttribute("sinal_vector");
			double[] ySinalVector = (double[]) node_y.getAttribute("sinal_vector");
			for(int i=0;i<graph.getNodeNum();i++){
				euclideanDistance += (xSinalVector[i]-ySinalVector[i])*(xSinalVector[i]-ySinalVector[i]);
			}
			euclideanDistance = Math.sqrt(euclideanDistance);
			xSimVector[node_y.getId()-1] = euclideanDistance;
			if(ySimVector == null){
				ySimVector = new double[graph.getNodeNum()];
				node_y.putAttribute("similarity_vector", ySimVector);
			}
			ySimVector[node_x.getId()-1] = euclideanDistance;
		}
		//double[] ySimVector = (double[]) node_y.getAttribute("similarity_vector");
		return euclideanDistance;
	}

}
