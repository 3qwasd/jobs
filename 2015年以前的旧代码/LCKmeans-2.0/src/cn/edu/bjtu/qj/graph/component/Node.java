/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;

/**
 * @author QiaoJian
 *
 */
public abstract class Node extends GraphElement{
	
	int id = -1;
	String name;
	
	
	public Node(){};
	public Node(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		if(id<0)
			return super.toString();
		String str = "This node's information:id="+id+",name="+name;
		for(String key:attributes.keySet()){
			str+=","+key+"="+attributes.get(key);
		}
		return str;
	}
	
}
