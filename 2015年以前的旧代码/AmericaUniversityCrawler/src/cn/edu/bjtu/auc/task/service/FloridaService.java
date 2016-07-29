/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.edu.bjtu.auc.BaseService;
import cn.edu.bjtu.auc.StudentInfo;

/**
 * @author QiaoJian
 *
 */
public class FloridaService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub

		return null;
	}
	public List<StudentInfo> collecteFromJson(String lastName,String html) {
		// TODO Auto-generated method stub
		JSONArray jsonArray = null;
		try{
			jsonArray = JSONArray.fromObject(html);
		}catch (Exception e) {
			e.printStackTrace();
			resourceManager.addMessage(lastName+"has no result search!");
			return null;
		}
		if(jsonArray!=null){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<jsonArray.size();i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				try{
					JSONObject nameObj = (JSONObject) obj.get("cn");
					JSONObject emailObj = (JSONObject) obj.get("mail");
					String name = nameObj.get("0")+"";
					String email = emailObj.get("0")+"";
					StudentInfo info = new StudentInfo();
					info.putInfo("name", name);
					info.putInfo("email", email);
					infos.add(info);
					resourceManager.addMessage("name="+name+",email"+email);
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}
			return infos;
		}
		return null;
	} 
	/**
	 * @param lastName
	 * @return
	 */
	public String getUrl(String lastName) {
		// TODO Auto-generated method stub
		String url = "https://directory.ufl.edu/people/search/search.php?query={lastName}&_={time}";

		return url.replace("{lastName}", lastName).replace("{time}", new Date().getTime()+"");
	}

}
