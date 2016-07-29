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
public class IndianaBloomingtonService extends BaseService {

	/**
	 * 
	 */
	public IndianaBloomingtonService() {
		// TODO Auto-generated constructor stub
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
	 * @param firstName 
	 * @param let
	 * @return
	 */
	public Map<String, String> getParams(String lastName, String firstName) {
		// TODO Auto-generated method stub
		Map<String,String> params = new HashMap<String, String>();
		params.put("campus", "bloomington");
		params.put("exactness", "exact");
		params.put("firstname",firstName);
		params.put("lastname", lastName);
		params.put("netid", "");
		params.put("Search", "Search");
		params.put("status", "Student");
		return params;
	}

	/**
	 * @param lastName
	 * @return
	 */
	public List<String> collectLinks(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementByTag("table");
		if(table == null){
			this.addNoResultMsg(lastName);
			return null;
		}
		Elements as = table.select("a[href^=?user_string]");
		if(as!=null&&as.size()>0){
			List<String> links = new ArrayList<String>();
			for(int i=0;i<as.size();i++){
				Element a = as.get(i);
				links.add("http://people.iu.edu/index.cgi"+a.attr("href"));
			}
			return links;
		}else{
			this.addNoResultMsg(lastName);
			return null;
		}
	}

	/**
	 * @return
	 */
	public StudentInfo collecteInfo() {
		// TODO Auto-generated method stub
		try{
			
			String name = getElementBySelector("th.details").text();
			name = name.substring("Address Book entry for".length());
			String imgSrc = getElementBySelector("img[src^=./tmp/people]").attr("src");
			String email = "http://people.iu.edu"+imgSrc.replaceFirst(".", "");
			StudentInfo info = createStudentInfo(name, email);
			return info;
		}catch(Exception e){
			return null;
		}
	}

}
