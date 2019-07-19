/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author QiaoJian
 *
 */
public class AdjListNode extends Node {
	
	List<AdjListEdge> inEdges;
	List<AdjListEdge> outEdges;
	List<AdjListEdge> undirectEdges;
	
	
	public AdjListNode() {
		super();
		init();
		// TODO Auto-generated constructor stub
	}
	public AdjListNode(int id, String name) {
		super(id, name);
		init();
		// TODO Auto-generated constructor stub
	}
	private void init(){
		inEdges = new ArrayList<>();
		outEdges = new ArrayList<>();
		undirectEdges = new ArrayList<>();
	}
	public void addInEdge(AdjListEdge edge){
		if(inEdges == null){
			inEdges = new ArrayList<AdjListEdge>();
		}
		inEdges.add(edge);
	}
	public void addOutEdge(AdjListEdge edge){
		if(outEdges == null){
			outEdges = new ArrayList<AdjListEdge>();
		}
		outEdges.add(edge);
	}
	public void addUndirectEdge(AdjListEdge edge){
		if(undirectEdges == null){
			undirectEdges = new ArrayList<AdjListEdge>();
		}
		undirectEdges.add(edge);
	}
	public List<AdjListEdge> getInEdges() {
		return inEdges;
	}
	
	public List<AdjListEdge> getOutEdges() {
		return outEdges;
	}
	
	public List<AdjListEdge> getUndirectEdges() {
		return undirectEdges;
	}
}
