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
public class WilliamsService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Elements h4s = this.getElementsBySelector("h4.title-students");
		Elements divs = this.getElementsBySelector("div.wms-directory-wrapper");
		if(h4s==null||divs==null){
			resourceManager.addMessage(lastName+" no result search!");
			return null;
		}
		Element studentDiv = null;
		for(int i=0;i<h4s.size();i++){
			Element h4 = h4s.get(i);
			if(h4.text().equals("Students")){
				studentDiv = divs.get(i);
			}
		}
		if(studentDiv == null){
			resourceManager.addMessage(lastName+" no result search!");
			return null;
		}

		Elements infoDivs = studentDiv.select("div.index-row");
		if(infoDivs!=null&&infoDivs.size()>0){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<infoDivs.size();i++){
				Element div = infoDivs.get(i);
				try{
					String name = div.select("div.name").get(0).text();
					String email = div.select("a[href^=mailto:]").get(0).text();
					StudentInfo info = new StudentInfo();
					info.putInfo("name", name);
					info.putInfo("email", email);
					infos.add(info);
					resourceManager.addMessage("name="+name+",email="+email);
				}catch(Exception e){
					continue;
				}
			}
			return infos;
		}
		return null;
	}

	public String getUrl(String lastName){
		String url = "http://www.williams.edu/wp-admin/admin-ajax.php?"
				+ "action=load_directory&data=s_directory%3D{lastName}%26department%3D";
		return url.replace("{lastName}", lastName);
	}
}
