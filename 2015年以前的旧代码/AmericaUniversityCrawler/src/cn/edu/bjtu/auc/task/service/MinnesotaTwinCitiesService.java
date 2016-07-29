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
public class MinnesotaTwinCitiesService extends BaseService {

	/**
	 * 
	 */
	public MinnesotaTwinCitiesService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementBySelector("table[cellpadding=3]");
		if(table == null){
			this.addNoResultMsg(lastName);
			return null;
		}
		Elements trs = table.select("tr[VALIGN=TOP]");
		if(trs!=null&&trs.size()>0){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					String name = tr.select("a[href^=/lookup?SET_INSTITUTION=UMNTC&UID]").get(0).text();
					if(!name.toLowerCase().startsWith(lastName.toLowerCase()+",")){
						continue;
					}
					String email = tr.select("a[href^=mailto:]").get(0).text();
					String link = "http://www.umn.edu"+tr.select("a[href^=/lookup?SET_INSTITUTION=UMNTC&UID]").get(0).attr("href");
					StudentInfo info = this.createStudentInfo(name, email);
					info.putInfo("link", link);
					infos.add(info);
				}catch(Exception e){
					continue;
				}
			}
			return infos;
		}else{
			this.addNoResultMsg(lastName);
			return null;
		}
		
	}
	public boolean isTooMany(String html){
		if(html.contains("Too many entries matched your search criteria. Please try again with more specific criteria.")){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * @param lastName
	 * @param let
	 * @return
	 */
	public String getUrl(String lastName, String firstName) {
		// TODO Auto-generated method stub
		String url = "http://www.umn.edu/lookup?SET_INSTITUTION=UMNTC&type=name&CN={lastName}+{firstName}&campus=a&role=stu";
		return url.replace("{lastName}", lastName).replace("{firstName}", firstName);
	}
	public String getUrl(String lastName) {
		// TODO Auto-generated method stub
		String url = "http://www.umn.edu/lookup?SET_INSTITUTION=UMNTC&type=name&CN={lastName}&campus=a&role=stu";
		return url.replace("{lastName}", lastName);
	}

	/**
	 * @param info
	 */
	public void collecteInfoDetail(StudentInfo info) {
		// TODO Auto-generated method stub
		List<String> keys = new ArrayList<String>();
		keys.add("Appointment");
		keys.add("Enrollment");
		keys.add("Internet ID");
		keys.add("Address");
		keys.add("Phone");
		info.putInfo("Appointment", " ");
		info.putInfo("Enrollment", " ");
		info.putInfo("Internet ID", " ");
		info.putInfo("Address", " ");
		info.putInfo("Phone", " ");
		Element table = this.getElementByTag("table");
		if(table == null)
			return;
		Elements trs = table.getElementsByTag("tr");
		if(trs != null&&trs.size()>0){
			for(int i=0;i<trs.size();i++){
				try{
					Element tr = trs.get(i);
					String key = tr.getElementsByTag("th").get(0).text().replace(":", "");
					String value = tr.getElementsByTag("td").get(0).text();
					if(keys.contains(key)){
						info.putInfo(key, value);
					}
				}catch(Exception e){
					continue;
				}
			}
		}
	}
}
