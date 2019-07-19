/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.bjtu.auc.BaseService;
import cn.edu.bjtu.auc.StudentInfo;

/**
 * @author QiaoJian
 *
 */
public class GeorgiaTechService extends BaseService{

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	public Map<String, String> getPreParams() {
		// TODO Auto-generated method stub
		Element inputFormBuildId = this.getElementBySelector("input[name=form_build_id]");
		Element inputFormId = this.getElementBySelector("input[name=form_id]");
		Map<String ,String> preParams = new HashMap<String, String>();
		preParams.put("form_build_id", inputFormBuildId.attr("value"));
		preParams.put("form_id", inputFormId.attr("value"));
		preParams.put("op", "Search");
		preParams.put("firstname", "");
		return preParams;
	}

	/**
	 * @param lastName
	 * @return
	 */
	public List<String> collecteStudentLinks(String lastName) {
		// TODO Auto-generated method stub
		Element div = this.getElementBySelector("div.content");
		if(div==null)
			return null;
		Elements links = div.getElementsByTag("a");
		if(links!=null&&links.size()>0){
			List<String> linkStrs = new ArrayList<String>();
			for(Element link:links){
				String href = link.attr("href");
				String name = link.text();
				if(href.startsWith("/directory/detail/")&&name.toLowerCase().startsWith(lastName.toLowerCase())){
					linkStrs.add(href);
				}
			}
			return linkStrs;
		}
		return null;
	}

	/**
	 * @return
	 */
	public StudentInfo collecteInfo() {
		// TODO Auto-generated method stub
		try{
			Element div = this.getElementBySelector("div.content");
			if(div == null)
				return null;
			Element nameH2 = div.getElementsByTag("h2").get(0);
			Element emailA = div.select("a[href^=mailto:]").get(0);
			String name = nameH2.text();
			String email = emailA.text();
			resourceManager.addMessage("name="+name+",email="+email);
			StudentInfo info = new StudentInfo();
			info.putInfo("name", name);
			info.putInfo("email", email);
			return info;
		}catch(Exception e){
			return null;
		}
	}

}
