/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.parser.json;

import java.util.Set;

import net.sf.json.JSONObject;
import cn.edu.bjtu.crawler.bean.sina.JsonDataBean;
import cn.edu.bjtu.crawler.parser.BaseParser;

/**
 * @author QiaoJian
 *
 */
public class JsonParser extends BaseParser {
	
	/**
	 * 解析json格式的字符串
	 * @param jsonStr
	 * @return
	 */
	public JSONObject parserJsonStr(String jsonStr){
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		return jsonObject;
	}
	
	@SuppressWarnings("unchecked")
	public void jsonObject2Bean(JsonDataBean jsonDataBean,JSONObject jsonObject){
		
		Set<String> keySet = jsonObject.keySet();
		for(String key:keySet){
			if(jsonObject.get(key) instanceof JSONObject){
				JSONObject jsObject = jsonObject.getJSONObject(key);
				JsonDataBean newJsonBean = new JsonDataBean();
				jsonDataBean.put(key, newJsonBean);
				jsonObject2Bean(newJsonBean, jsObject);
			}else{
				jsonDataBean.put(key, jsonObject.get(key));
			}
		}
	}
}
