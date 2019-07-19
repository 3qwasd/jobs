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
public class SouthCarolinaService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = getElementById("directorystudent");
		if(table == null){
			resourceManager.addMessage(lastName+" has no result for search");
			return null;
		}
		Elements trs = table.getElementsByTag("tr");
		if(trs!=null&&trs.size()>1){
			List<StudentInfo> infos= new ArrayList<StudentInfo>();
			for(int i=1;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					String name = tr.getElementsByTag("td").get(0).text();
					String email = tr.getElementsByTag("td").get(1).text();
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
			resourceManager.addMessage(lastName+" has no result for search");
			return null;
		}
	}
	
	public String getUrl(String lastName,String firstName){
		String url = "https://www.sc.edu/about/directory/?name={firstName}*+{lastName}&type%5B%5D=student";
		return url.replace("{firstName}", firstName).replace("{lastName}", lastName);
	}
}
