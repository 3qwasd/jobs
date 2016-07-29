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
public class PennStateService extends BaseService {

	/**
	 * 
	 */
	public PennStateService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Elements lis = this.getElementsBySelector("li.views-row ");
		if(lis!=null&&lis.size()>0){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<lis.size();i++){
				Element li = lis.get(i);
				try{
					String name = li.getElementsContainingText("Name:").get(0).text();
					name = name.substring(name.indexOf("Name:"),name.indexOf("E-mail:"));
					String email = li.select("a[href^=mailto:]").get(0).text();
					StudentInfo info = createStudentInfo(name, email);
					infos.add(info);
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
	public String getNextPageUrl() {
		// TODO Auto-generated method stub
		try{
			Element a = this.getElementBySelector("a[title=Go to next page]");
			if(a!=null){
				return "http://www.psu.edu"+a.attr("href");
			}
		}catch(Exception e){
			return null;
		}
		return null;
	}

}
