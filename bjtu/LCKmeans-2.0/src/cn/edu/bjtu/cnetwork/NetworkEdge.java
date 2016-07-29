/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork;

import cn.edu.bjtu.qj.graph.component.AdjListEdge;
import cn.edu.bjtu.qj.graph.component.AdjListNode;

/**
 * @author QiaoJian
 *
 */
public class NetworkEdge extends AdjListEdge{

	/**
	 * 
	 */
	public NetworkEdge() {
		// TODO Auto-generated constructor stub
	}

	public NetworkEdge(AdjListNode sourceNode, AdjListNode targetNode,
			boolean isDirected) {
		super(sourceNode, targetNode, isDirected);
		// TODO Auto-generated constructor stub
	}
	
}
