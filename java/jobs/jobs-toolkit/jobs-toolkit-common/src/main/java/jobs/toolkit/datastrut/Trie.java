package jobs.toolkit.datastrut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 单词查找树
 * @author jobs
 *
 */
public class Trie {

	private final Node head;

	public Trie(char root) {
		this.head = this.newNode(root);
	}
	public Trie(char root, boolean wordEnd){
		this.head = this.newNode(root);
		this.head.wordEnd = wordEnd;
	}
	public Node getHead(){
		return this.head;
	}
	public Trie addWord(char[] word){
		if(word == null || word.length < 1) return this;
		if(word[0] != this.head.key) throw new IllegalArgumentException("word not start with this root char" + head.key);
		Node p = this.head;

		for(int i = 1; i < word.length; i++){
			Node q = p.subNode(word[i]);
			if(q == null){
				q = this.newNode(word[i]);
				p.born(word[i], q);
			}
			p = q;
		}
		p.wordEnd = true;
		return this;
	}
	public Trie addWordSuffix(char[] word){
		if(word == null || word.length < 1) return this;
		Node p = this.head;

		for(int i = 0; i < word.length; i++){
			Node q = p.subNode(word[i]);
			if(q == null){
				q = this.newNode(word[i]);
				p.born(word[i], q);
			}
			p = q;
		}
		p.wordEnd = true;
		return this;
	}
	/**
	 * 返回keys中offset开始片段中在树中的匹配的序列最大长度, 没有找到返回0
	 * @param keys
	 * @param offset
	 * @return
	 */
	public int maxMatch(char[] sequence, int offset){
		if(sequence == null || sequence.length < 1 || offset >= sequence.length || offset < 0) return 0;
		int idx = offset - 1;
		if(sequence[offset] != this.head.key) return 0;
		Node p = this.head;
		for(int i = offset + 1; i < sequence.length; i++){
			p = p.subNode(sequence[i]);
			if(p == null) break;
			if(p.isWordEnd()) idx = i;
		}
		return idx - offset + 1;
	}
	public int maxMatch(char[] sequence){
		return this.maxMatch(sequence, 0);
	}
	/**
	 * 返回keys中offset开始后面的片段中在树中的匹配的所有的序列的长度, 没有则返回空的List
	 * @param tailLens
	 * @param keys
	 * @param offset
	 * @return
	 */
	public List<Integer> allMatch(List<Integer> tailLens, char[] sequence, int offset){
		if(sequence == null || sequence.length < 1 || offset >= sequence.length || offset < 0) return tailLens;
		if(tailLens == null){
			tailLens = new ArrayList<Integer>();
		}
		if(sequence[offset] != this.head.key) return tailLens;
		Node p = this.head;
		if(this.head.isWordEnd()) tailLens.add(1);
		for(int i = offset + 1; i < sequence.length; i++){
			p = p.subNode(sequence[i]);
			if(p == null) break;
			if(p.isWordEnd()) tailLens.add(i - offset + 1);
		}
		return tailLens;
	}
	public List<Integer> allMatch(List<Integer> tailLens, char[] sequence){
		return this.allMatch(tailLens, sequence, 0);
	}
	public List<Integer> allMatch(char[] sequence, int offset){
		return this.allMatch(null, sequence, offset);
	}
	public List<Integer> allMatch(char[] sequence){
		return allMatch(sequence, 0);
	}
	public boolean match(char[] sequence, int offset, int len){
		if(sequence == null || sequence.length < 1 || offset < 0 || len < 1 || (offset + len) > sequence.length) return false;
		if(sequence[offset] != this.head.key) return false;
		Node p = this.head;
		for(int i = 1; i < len; i++){
			p = p.subNode(sequence[offset + i]);
			if(p == null) return false;
		}
		return p.isWordEnd();
	}
	public boolean match(char[] sequence){
		if(sequence == null || sequence.length < 1) return false;
		return this.match(sequence, 0, sequence.length);
	}
	private Node newNode(char c){
		return new Node(c);
	}
	public class Node{
		private final char key;
		private final Map<Character, Node> subNodes;
		private volatile boolean wordEnd;
		private Node(char key) {
			this.key = key;
			subNodes = new HashMap<Character, Node>();
		}
		public void born(char k, Node sub) {
			subNodes.put(k, sub);
		}
		public Node subNode(char k) {
			return subNodes.get(k);
		}
		public boolean isWordEnd() {
			return this.wordEnd;
		}
	}
}
