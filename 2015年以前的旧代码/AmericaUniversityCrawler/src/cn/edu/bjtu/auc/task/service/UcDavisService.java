/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.bjtu.auc.BaseService;
import cn.edu.bjtu.auc.StudentInfo;

/**
 * @author QiaoJian
 *
 */
public class UcDavisService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementByTag("table");
		if(table == null){
			resourceManager.addMessage(lastName +" has no result for search!");
			return null;
		}
		Elements trs = table.getElementsByTag("tr");
		if(trs!=null&&trs.size()>2){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=1;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					String email = tr.select("a[href^=mailto:]").get(0).text();
					String name = tr.select("a[href^=/search/directory_results.shtml]").get(0).text();
					resourceManager.addMessage("name="+name+",email="+email);
					StudentInfo info = new StudentInfo();
					info.putInfo("name", name);
					info.putInfo("email", email);
					infos.add(info);
				}catch(Exception e){
					continue;
				}
			}
			return infos;
		}else{
			resourceManager.addMessage(lastName +" has no result for search!");
		}
		return null;
	}

	public String getUrl(String lastName){
		String url = "http://www.ucdavis.edu/search/directory_results.shtml?filter={lastName}";
		return url.replace("{lastName}", lastName);
	}
}
