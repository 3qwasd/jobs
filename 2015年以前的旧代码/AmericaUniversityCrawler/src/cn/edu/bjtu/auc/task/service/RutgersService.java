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
public class RutgersService extends BaseService {
	
	
	public String getUrl(String lastName,String firstName){
		String url = "https://www.acs.rutgers.edu/pls/pdb_p/Pdb_Display.search_results?"
				+ "p_name_first={firstName}%25&p_name_last={lastName}";
		return url.replace("{firstName}", firstName).replace("{lastName}", lastName);
	}
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
	public List<String> collecteLinks(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementBySelector("table.data");
		if(table==null){
			resourceManager.addMessage(lastName+"has no result for serarch!");
			return null;
		}
		Elements trs = table.getElementsByTag("tr");
		if(trs!=null&&trs.size()>1){
			List<String> links = new ArrayList<String>();
			for(int i=1;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					Element a = tr.select("a[href^=http://www.acs.rutgers.edu/pls/pdb_p/Pdb_Display.display?SESSION_INFO=]").get(0);
					links.add(a.attr("href"));
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}
			return links;
		}else{
			resourceManager.addMessage(lastName+"has no result for serarch!");
			return null;
		}
	}
	/**
	 * @return
	 */
	public StudentInfo collectInfo() {
		// TODO Auto-generated method stub
		try{
			StudentInfo info = new StudentInfo();
			String name = this.getElementBySelector("h3.c").text();
			String email = this.getElementBySelector("a[href^=mailto:]").text();
			resourceManager.addMessage("name="+name+",email="+email);
			info.putInfo("name", name);
			info.putInfo("email", email);
			return info;
		}catch(Exception e){
			
			return null;
		}
		
	}

}
