/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author QiaoJian
 *
 */
public abstract class GraphElement {
	
	Map<String,Object> attributes = new HashMap<String,Object>();
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Object getAttribute(String key){
		return attributes.get(key);
	}
	public void putAttribute(String key,Object value){
		if(attributes == null){
			attributes = new HashMap<String,Object>();
		}
		attributes.put(key, value);
	}
}
