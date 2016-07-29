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
public interface ISimilarityCalculate {

	/**
	 * 
	 */
	public double calculate(Graph graph,Node node_x,Node node_y);
	
}
