/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.bean.sina;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import cn.edu.bjtu.crawler.bean.HttpDataBean;

/**
 * @author QiaoJian
 *
 */
public class JsonDataBean extends HttpDataBean{
	
	
	Map<String,Object> jsonParams= null;
	
	public JsonDataBean() {
		super();
	}
	
	public JsonDataBean(String result) {
		super(result);
	}

	
	public void put(String key,Object value){
		if(jsonParams == null){
			jsonParams = new HashMap<String, Object>();
		}
		jsonParams.put(key, value);
	}
	public Object get(String key){
		
		return jsonParams.get(key);
	}
	public Set<String> keySet(){
		return jsonParams.keySet();
	}
}
