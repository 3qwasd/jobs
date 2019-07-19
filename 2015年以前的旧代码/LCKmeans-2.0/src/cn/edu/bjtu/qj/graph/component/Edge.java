/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;

/**
 * @author QiaoJian
 *
 */
public abstract class Edge extends GraphElement{
	
	Node sourceNode;
	Node targetNode;
	boolean isDirected;
	
	public Edge() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Edge(Node sourceNode, Node targetNode, boolean isDirected) {
		super();
		this.sourceNode = sourceNode;
		this.targetNode = targetNode;
		this.isDirected = isDirected;
		init();
	}
	public abstract void init();
	public Node getSourceNode() {
		return sourceNode;
	}
	public abstract void setSourceNode(Node sourceNode);
	public Node getTargetNode() {
		return targetNode;
	}
	public abstract void setTargetNode(Node targetNode);
	public boolean isDirected() {
		return isDirected;
	}
	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}
	@Override
	public String toString() {
		if(sourceNode!=null&&targetNode!=null)
			return "sourceNode.id="+sourceNode.getId()+",targetNode.id="+targetNode.getId();
		else
			return super.toString();
	}
	
}
