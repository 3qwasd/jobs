package jobs.toolkit.alg.tree;

public abstract class BaseTree<Node extends BaseTreeNode<Node>, Tree extends BaseTree<Node, Tree>> {
	
	protected volatile Node root;
	
	/**
	 * 插入节点
	 * @param node
	 * @return
	 */
	abstract Tree insert(Node node);
	/**
	 * 删除节点
	 * @param node
	 * @return
	 */
	abstract Tree delete(Node node);
}
