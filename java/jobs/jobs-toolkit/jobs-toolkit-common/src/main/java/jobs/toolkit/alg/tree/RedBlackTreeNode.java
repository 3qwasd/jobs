package jobs.toolkit.alg.tree;

public class RedBlackTreeNode extends BaseTreeNode<RedBlackTreeNode> {
	
	protected static final RedBlackTreeNode NIL = new RedBlackTreeNode(); 
	
	enum Color{
		Red, Black
	}
	
	private volatile Color color = Color.Black;
	
	public RedBlackTreeNode() {
		this.setParent(NIL).setLeftChild(NIL).setRightChild(NIL);
	}
	
	public RedBlackTreeNode switchToRed(){
		this.color = Color.Red;
		return this;
	}
	
	public RedBlackTreeNode switchToBlack(){
		this.color = Color.Black;
		return this;
	}
	
	public boolean isRed(){
		return this.color == Color.Red;
	}
	
	public boolean isBlack(){
		return this.color == Color.Black;
	}
}
