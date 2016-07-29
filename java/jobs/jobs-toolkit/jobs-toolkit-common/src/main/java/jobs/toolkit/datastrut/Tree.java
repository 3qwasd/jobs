package jobs.toolkit.datastrut;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * 树形结构工具
 * @author jobs
 *
 */
public class Tree {
	
	/**
	 * 广度优先遍历, 层次遍历
	 * @param root
	 * @param visitor
	 */
	public static <T> void breadthFirstTraversal(T root, TreeNodeVisitor<T> visitor){
		if(root == null) return;
		
		Queue<T> queue = new LinkedList<T>();
		
		queue.offer(root);
		
		while(!queue.isEmpty()){
			T node = queue.poll();
			visitor.visit(node);
			if(visitor.isLeaf(node)) continue;
			List<T> children = visitor.getChildrean(node);
			for(int i = 0; i < children.size(); i++){
				queue.offer(children.get(i));
			}
		}
	} 
	
	/**
	 * 树形结构先根遍历, 深度优先遍历
	 * @param root
	 * @param visitor
	 */
	public  static <T> void preorderTraversal(T root, TreeNodeVisitor<T> visitor){
		
		if(root == null) return;
		
		Stack<T> stack = new Stack<T>();
		
		stack.push(root);
		
		while(!stack.isEmpty()){
			T node = stack.pop();
			visitor.visit(node);
			if(visitor.isLeaf(node)) continue;
			List<T> children = visitor.getChildrean(node);
			for(int i = children.size() - 1; i >= 0; i--){
				stack.push(children.get(i));
			}
		}
	}
	/**
	 * 树形结构后根遍历
	 * @param root
	 * @param visitor
	 */
	public static <T> void postorderTraversal(T root, TreeNodeVisitor<T> visitor){
		if(root == null) return;
		
		Stack<T> stack = new Stack<T>();
		
		T p = null;
		T q = null;
		
		p = root;
		
		do{
			while (p != null) {
				stack.push(p);
				if(visitor.isLeaf(p)) {
					p = null;
				}else{
					p = visitor.getChildrean(p).get(0);
				}
			}
			q = null;
			while(!stack.isEmpty()){
				p = stack.pop();
				if(visitor.isLeaf(p)){
					visitor.visit(p);
					q = p;
				}else{
					List<T> children = visitor.getChildrean(p);
					int index = children.indexOf(q);
					if(index == (children.size() - 1)){
						visitor.visit(p);
						q = p;
					}else{
						stack.push(p);
						p = children.get(index + 1);
						break;
					}
				}
			}
		}while(!stack.isEmpty());	
	}
}
