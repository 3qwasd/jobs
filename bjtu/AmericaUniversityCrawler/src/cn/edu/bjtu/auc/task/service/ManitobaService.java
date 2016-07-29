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
public class ManitobaService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param lastName
	 * @return
	 */
	public String getUrl(String lastName) {
		// TODO Auto-generated method stub
		String url = "http://umanitoba.ca/search/search2003_update_2009.php?"
				+ "txtSearch={lastName}&s=t&newSearch=t";
		return url.replace("{lastName}", lastName);
	}

	/**
	 * @param lastName
	 * @return
	 */
	public List<String> collecteLinks(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementByTag("table");
		if(table == null){
			resourceManager.addMessage(lastName+" no result fo search!");
			return null;
		}
		Elements as = table.getElementsByTag("a");
		if(as!=null&&as.size()>0){
			List<String> links = new ArrayList<String>();
			String url = "http://umanitoba.ca/search/search2003_update_2009.php";
			for(int i=0;i<as.size();i++){
				Element a = as.get(i);
				String name = a.text();
				if(name.toLowerCase().startsWith(lastName.toLowerCase()+", ")){
					links.add(url+a.attr("href"));
				}
			}
			return links;
		}
		return null;
	}

	/**
	 * @return
	 */
	public StudentInfo getStudentInfo() {
		// TODO Auto-generated method stub
		try {
			Element table = this.getElementByTag("table");
			String name = table.getElementsByTag("h2").get(0).text();
			String email = table.select("a[href^=mailto:]").get(0).text();
			StudentInfo info = new StudentInfo();
			info.putInfo("name", name);
			info.putInfo("email", email);
			resourceManager.addMessage("name="+name+",email="+email);
			return info;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
