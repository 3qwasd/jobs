/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc;

import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;

/**
 * @author QiaoJian
 *
 */
public class StudentInfo {
	
	Map<String,String> infos = new HashedMap();

	public void putInfo(String key,String value){
		infos.put(key, value);
	}
	public String getInfo(String key){
		return infos.get(key);
	}
	public int size(){
		return infos.size();
	}
	public Set<String> keySet(){
		return infos.keySet();
	}
	public Map<String, String> getInfos() {
		return infos;
	}
	public void setInfos(Map<String, String> infos) {
		this.infos = infos;
	}
	
}
