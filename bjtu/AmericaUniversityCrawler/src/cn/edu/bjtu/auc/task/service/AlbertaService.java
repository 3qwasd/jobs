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
public class AlbertaService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}
	public Map<String,String> getParams(){
		Map<String,String> params = new HashMap<String, String>();
		params.put("bldg_desc","All Buildings");
		params.put("constraint", "Students");
		params.put("givenName", "");
		params.put("givenName_modifier", "Starts With");
		params.put("mail", "");
		params.put("ou", "All Departments");
		params.put("phonearea", "780");
		params.put("phoneexch", "492");
		params.put("phoneext", "");
		params.put("phonesub", "");
		params.put("sn_modifier", "Starts With");
		params.put("Submit", "SEARCH");
		params.put("title", "");
		params.put("title_modifier", "Starts With");
		return params;
	}
	/**
	 * @param lastName
	 * @return
	 */
	public List<String> getUserLinks(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementById("sortable1");
		if(table == null){
			resourceManager.addMessage(lastName + " has no search result!");
			return null;
		}
		
		Elements as = table.getElementsByTag("a");
		if(as!=null&&as.size()>1){
			List<String> links = new ArrayList<String>();
			for(int i=0;i<as.size();i++){
				Element a = as.get(i);
				String name = a.text();
				if(name.toLowerCase().endsWith(lastName.toLowerCase())){
					links.add(a.attr("href"));
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
		try{
			Element font = this.getElementBySelector("font[size=5]");
			String name = font.text();
			Element a = this.getElementBySelector("a[href^=mailto:]");
			String email = a.text();
			resourceManager.addMessage("name="+name+",email="+email);
			StudentInfo info = new StudentInfo();
			info.putInfo("name", name);
			info.putInfo("email", email);
			return info;
		}catch(Exception e){
			return null;
		}
	}
}
