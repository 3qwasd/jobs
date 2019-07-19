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
public class MichiganService extends BaseService {

	/**
	 * 
	 */
	public MichiganService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Elements divs = this.getElementsBySelector("div.person");
		if(divs!=null&&divs.size()>0){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<divs.size();i++){
				Element div = divs.get(i);
				try{
					String name = div.select("a[href^=index.php?fst=&lst=]").get(0).text();
					if(name.toLowerCase().startsWith(lastName.toLowerCase()+",")){
						String email = div.select("a[href^=mailto:]").get(0).text();
						String link = "https://search.msu.edu/people/"+div.select("a[href^=index.php]").get(0).attr("href");
						StudentInfo info = this.createStudentInfo(name, email);
						info.putInfo("link", link);
						infos.add(info);
					}
				}catch(Exception e){
					continue;
				}
			}
			return infos;
		}else{
			addNoResultMsg(lastName);
			return null;
		}
	}

	/**
	 * @param lastName
	 * @return
	 */
	public String getUrl(String lastName) {
		String url = "https://search.msu.edu/people/index.php?fst=&lst={lastName}&nid=&search=Search&type=stu";
		return url.replace("{lastName}", lastName);
	}

	/**
	 * @param lastName
	 * @return
	 */
	public List<String> collectLinks(String lastName) {
		// TODO Auto-generated method stub
		Elements as = this.getDocument().select("a[href^=https://mcommunity.umich.edu/#profile:]");
		if(as!=null&&as.size()>0){
			List<String> links = new ArrayList<String>();
			for(int i=0;i<as.size();i++){
				Element a = as.get(i);
				String link = a.attr("href");
				links.add(link);
			}
			return links;
		}else{
			this.addNoResultMsg(lastName);
			return null;
		}
	}

	/**
	 * @param info
	 */
	public void collectInfo(StudentInfo info) {
		// TODO Auto-generated method stub
		Element div = this.getElementById("search_results");
		if(div == null){
			return;
		}
		try{
			Element a = div.select("a[href^=mailto:]").get(0);
			String type = a.parent().nextElementSibling().text().replace("Title:", "");
			info.putInfo("type", type);
			String year = a.parent().nextElementSibling().nextElementSibling().text();
			info.putInfo("year", year);
			String unit = a.parent().nextElementSibling().nextElementSibling().nextElementSibling().text();
			info.putInfo("unit", unit);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	

}
