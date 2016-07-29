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
public class WisconsinMadisonService extends BaseService {
	
	List<String> emails = new ArrayList<String>();
	/**
	 * 
	 */
	public WisconsinMadisonService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		Elements divs = this.getElementsBySelector("div.person");
		if(divs!=null&&divs.size()>0){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<divs.size();i++){
				try{
					Element div = divs.get(i);
					String name = div.select("div.person_name").get(0).text();
					if(!name.toLowerCase().endsWith(" "+lastName.toLowerCase())){
						continue;
					}
					String email = div.select("a[href^=mailto:]").get(0).text();
					if(emails.contains(email)){
						continue;
					}
					emails.add(email);
					StudentInfo info = createStudentInfo(name, email);
					info.putInfo("Title", "null");
					info.putInfo("SCHOOL OF PHARMACY", "null");
					info.putInfo("Unit", "null");
					info.putInfo("Phone", "null");
					infos.add(info);
					String link = "http://www.wisc.edu"+div.select("a[href^=/directories/person.php]").get(0).attr("href");
					info.putInfo("link", link);
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
	 * @return
	 */
	public boolean toManyResult() {
		// TODO Auto-generated method stub
		String msg = this.getElementBySelector("div.person").text();
		if(msg.equals("Your search returned more than the maximum 25 matches. Please try to narrow your search.")){
			addMsg(msg);
			return true;
		}
		return false;
	}

	/**
	 * @param info
	 */
	public void collecteDetail(StudentInfo info) {
		// TODO Auto-generated method stub
		info.getInfos().remove("link");
		Element div = this.getElementById("people");
		if(div==null)
			return;
		try{
			Elements title = div.select("div.person_title_title");
			String titles = "";
			for(int i=0;i<title.size();i++){
				titles +=title.get(i).text();
			}
			info.putInfo("Title",titles);
			String pharmacy = div.select("div.person_title_division").get(0).text();
			info.putInfo("SCHOOL OF PHARMACY", pharmacy);
			String unit = div.select("div.person_title_subdepartment").get(0).text();
			info.putInfo("Unit", unit);
			String phone = div.select("div.person_phone").get(0).text();
			info.putInfo("Phone", phone);
		}catch(Exception e){
			
		}
	}

}
