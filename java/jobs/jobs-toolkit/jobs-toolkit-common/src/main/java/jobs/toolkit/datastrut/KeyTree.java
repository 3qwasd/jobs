package jobs.toolkit.datastrut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KeyTree 提供类似于单词查找树的功能
 * @author jobs
 *
 * @param <T>
 */
public class KeyTree<T> {
	
	private volatile KeyTreeNode head;
	
	public KeyTree(T rootKey) {
		this.head = this.newNode(rootKey);
	}
	public KeyTree(T rootKey, boolean isSeqTail){
		this.head = this.newNode(rootKey);
		this.head.isSeqTail = isSeqTail;
	}
	public KeyTreeNode getHead(){
		return this.head;
	}
	public KeyTree<T> add(T[] keys){
		if(keys == null || keys.length < 1) return this;
		if(!keys[0].equals(this.head.key)) throw new IllegalArgumentException("Start key is not head" + head.key);
		KeyTreeNode p = this.head;
		
		for(int i = 1; i < keys.length; i++){
			T key = keys[i];
			KeyTreeNode q = p.subNode(key);
			if(q == null){
				q = this.newNode(key);
				p.born(key, q);
			}
			p = q;
		}
		p.isSeqTail = true;
		return this;
	}
	/**
	 * 将Keys作为后缀添加在head后面
	 * @param keys
	 * @return
	 */
	public KeyTree<T> addSuffix(T[] keys){
		if(keys == null || keys.length < 1) return this;
		KeyTreeNode p = this.head;
		for(int i = 0; i < keys.length; i++){
			T key = keys[i];
			KeyTreeNode q = p.subNode(key);
			if(q == null){
				q = this.newNode(key);
				p.born(key, q);
			}
			p = q;
		}
		p.isSeqTail = true;
		return this;
	}
	/**
	 * 返回keys中offset开始片段中在树中的匹配的序列最大长度, 没有找到返回0
	 * @param keys
	 * @param offset
	 * @return
	 */
	public int maxMatch(T[] keys, int offset){
		if(keys == null || keys.length < 1 || offset >= keys.length || offset < 0) return 0;
		if(!keys[offset].equals(this.head.key)) return 0;
		int idx = offset - 1;
		KeyTreeNode p = this.head;
		for(int i = offset + 1; i < keys.length; i++){
			p = p.subNode(keys[i]);
			if(p == null) break;
			if(p.isSeqTail()) idx = i;
		}
		return idx - offset + 1;
	}
	public int maxMatch(T[] keys){
		return this.maxMatch(keys, 0);
	}
	/**
	 * 返回keys中offset开始后面的片段中在树中的匹配的所有的序列的长度, 没有则返回空的List
	 * @param tailLens
	 * @param keys
	 * @param offset
	 * @return
	 */
	public List<Integer> allMatch(List<Integer> tailLens, T[] keys, int offset){
		if(keys == null || keys.length < 1 || offset >= keys.length || offset < 0) return tailLens;
		if(tailLens == null){
			tailLens = new ArrayList<Integer>();
		}
		if(!keys[offset].equals(this.head.key)) return tailLens;
		if(this.head.isSeqTail()) tailLens.add(1);
		KeyTreeNode p = this.head;
		for(int i = offset + 1; i < keys.length; i++){
			p = p.subNode(keys[i]);
			if(p == null) break;
			if(p.isSeqTail()) tailLens.add(i - offset + 1);
		}
		return tailLens;
	}
	public List<Integer> allMatch(List<Integer> tailLens, T[] keys){
		return this.allMatch(tailLens, keys, 0);
	}
	public List<Integer> allMatch(T[] keys, int offset){
		return this.allMatch(null, keys, offset);
	}
	public List<Integer> allMatch(T[] keys){
		return allMatch(keys, 0);
	}
	public boolean match(T[] keys, int offset, int len){
		if(keys == null || keys.length < 1 || offset < 0 || len < 1 || (offset + len) > keys.length) return false;
		if(!keys[offset].equals(this.head.key)) return false;
		KeyTreeNode p = this.head;
		for(int i = 1; i < len; i++){
			p = p.subNode(keys[offset + i]);
			if(p == null) return false;
		}
		return p.isSeqTail();
	}
	public boolean match(T[] keys){
		if(keys == null || keys.length < 1) return false;
		return this.match(keys, 0, keys.length);
	}
	public KeyTreeNode newNode(T key){
		return new KeyTreeNode(key);
	}
	public class KeyTreeNode{
		private volatile T key;
		private volatile Map<T, KeyTreeNode> subNodes;
		private volatile boolean isSeqTail;
		private KeyTreeNode(T key) {
			this.key = key;
			subNodes = new HashMap<T, KeyTreeNode>();
		}
		public void born(T k, KeyTreeNode sub) {
			subNodes.put(k, sub);
		}
		public KeyTreeNode subNode(T k) {
			return subNodes.get(k);
		}
		public boolean isSeqTail() {
			return this.isSeqTail;
		}
	}
}

