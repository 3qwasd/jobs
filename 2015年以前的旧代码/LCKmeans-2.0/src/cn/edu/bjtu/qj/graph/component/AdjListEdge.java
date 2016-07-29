/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;

/**
 * @author QiaoJian
 *
 */
public class AdjListEdge extends Edge {

	public AdjListEdge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdjListEdge(AdjListNode sourceNode, AdjListNode targetNode, boolean isDirected) {
		super(sourceNode, targetNode, isDirected);
	}
	@Override
	public void init(){
		if(isDirected){
			((AdjListNode)sourceNode).addOutEdge(this);
			((AdjListNode)targetNode).addInEdge(this);
		}else{
			((AdjListNode) sourceNode).addUndirectEdge(this);
			((AdjListNode) targetNode).addUndirectEdge(this);
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.Edge#setSourceNode(cn.edu.bjtu.qj.graph.component.Node)
	 */
	@Override
	public void setSourceNode(Node sourceNode) {
		// TODO Auto-generated method stub
		this.sourceNode = sourceNode;
		if(isDirected){
			((AdjListNode)this.sourceNode).addOutEdge(this);
			((AdjListNode)this.sourceNode).addUndirectEdge(this);
		}else{
			((AdjListNode)this.sourceNode).addUndirectEdge(this);
			
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.Edge#setTargetNode(cn.edu.bjtu.qj.graph.component.Node)
	 */
	@Override
	public void setTargetNode(Node targetNode) {
		this.targetNode = targetNode;
		if(isDirected){
			((AdjListNode)this.targetNode).addInEdge(this);
			((AdjListNode)this.targetNode).addUndirectEdge(this);
		}else{
			((AdjListNode)this.targetNode).addUndirectEdge(this);
		}
	}
}
