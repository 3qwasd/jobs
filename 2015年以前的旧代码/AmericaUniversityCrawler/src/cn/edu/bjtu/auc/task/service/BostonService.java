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
public class BostonService extends BaseService{

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
	 * @param let
	 * @return
	 */
	public String getUrl(String lastName, String let) {
		// TODO Auto-generated method stub
		String url = "http://www.bu.edu/phpbin/directory/?q={first}+{last}";
		return url.replace("{first}", let+"*").replace("{last}", lastName);
	}

	/**
	 * @param lastName
	 * @return
	 */
	public List<String> collecteLinks(String lastName) {
		// TODO Auto-generated method stub
		Element ul = this.getElementBySelector("ul.listing");
		if(ul == null){
			return null;
		}

		Elements as = ul.getElementsByTag("a");
		if(as!=null&&as.size()>0){
			List<String> links = new ArrayList<String>();
			for(int i=0;i<as.size();i++){
				try{
					Element a = as.get(i);
					String name = a.text();
					String link = "http://www.bu.edu"+a.attr("href");
					if(name.toLowerCase().startsWith(lastName.toLowerCase()+",")){
						resourceManager.addMessage(link);
						links.add(name);
					}
				}catch(Exception e){
					continue;
				}
			}
			return links;
		}
		return null;
	}

	/**
	 * @return
	 */
	public StudentInfo collectInfo() {
		// TODO Auto-generated method stub
		try{
			String email = this.getElementBySelector("a[href^=mailto:]").text();
			String name = this.getElementByTag("h2").text();
			StudentInfo info = new StudentInfo();
			info.putInfo("name", name);
			info.putInfo("email", email);
			resourceManager.addMessage("name="+name+",email="+email);
			return info;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
