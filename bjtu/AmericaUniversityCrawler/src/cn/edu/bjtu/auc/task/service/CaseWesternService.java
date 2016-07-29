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
public class CaseWesternService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementBySelector("table.dirresults");
		if(table == null){
			resourceManager.addMessage("no result for "+lastName);
			return null;
		}
		Elements trs = table.getElementsByTag("tr");
		if(trs.size()>3){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=1;i<trs.size();i=i+2){
				Element nameTr = trs.get(i);
				Element emailTr = trs.get(i+1);
				try{
					StudentInfo info = new StudentInfo();
					String name = nameTr.getElementsByTag("b").get(0).text();
					String email = emailTr.select("a[href^=mailto:]").get(0).text();
					info.putInfo("name", name);
					info.putInfo("email", email);
					resourceManager.addMessage("name="+name+",email="+email);
					infos.add(info);
				}catch(Exception e){
					continue;
				}
			}
			return infos;
		}
		return null;
	}

	public String getQueryUrl(String lastName){
		String url = "https://webapps.case.edu/directory/lookup?"
				+ "search_text=&surname={lastName}&givenname=&department=&location=&"
				+ "category=student&search_method=regular";
		return url.replace("{lastName}", lastName);
	}
}
