package jobs.toolkit.handler;

import java.util.List;

/**
 * 树节点处理器
 * @author jobs
 *
 * @param <T> 表示分支节点类型
 * @param <V> 表示叶子节点类型
 */
public interface TreeNodeHandler<T, V extends Object> {
	
	/**
	 * 处理根节点
	 * @param root
	 */
	public void handleRoot(T root);
	/**
	 * 处理叶子节点
	 * @param v
	 */
	public void handleLeaf(V v);
	/**
	 * 处理分支节点
	 * @param t
	 */
	public void handleBranch(T t);
	/**
	 * 判断节点是否是叶子节点
	 * @param node
	 * @return
	 */
	public boolean isLeaf(Object node);
	/**
	 * 获取分支节点的孩子节点
	 * @param root
	 * @return
	 */
	public List<Object> getChildren(T branch);	
}
