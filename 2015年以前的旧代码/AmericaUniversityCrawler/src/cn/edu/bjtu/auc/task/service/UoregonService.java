/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import cn.edu.bjtu.auc.BaseService;
import cn.edu.bjtu.auc.StudentInfo;

/**
 * @author QiaoJian
 *
 */
public class UoregonService extends BaseService{

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param html
	 * @return
	 */
	public Map<String, String> getPreParams(String html) {
		// TODO Auto-generated method stub
		Map<String,String> params = new HashMap<String, String>();
		params.put("form_build_id",this.getElementBySelector("input[name=form_build_id]").attr("value"));
		params.put("form_id",this.getElementBySelector("input[name=form_id]").attr("value"));
		params.put("op",this.getElementBySelector("input[name=op]").attr("value"));
		return params;
	}

	/**
	 * @param lastName
	 * @return
	 */
	public List<String> collecteUserLink(String lastName) {
		// TODO Auto-generated method stub
		Element ulList = this.getElementById("resultlist");
		if(ulList == null)
			return null;
		Elements elements = ulList.getElementsByTag("li");
		if(elements!=null&&elements.size()>0){
			List<String> links = new ArrayList<String>();
			for(int i=0;i<elements.size();i++){
				try{
					Element li = elements.get(i);
					String link = "http://uoregon.edu"+li.getElementsByTag("a").get(0).attr("href");
					links.add(link);
					System.out.println(link);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return links;
		}else{
			return null;
		}
	}

	/**
	 * @return
	 */
	public StudentInfo collecteInfo() {
		// TODO Auto-generated method stub
		StudentInfo info = null;
		try{
			Element table = getElementBySelector("table.directory_results");
			if(table == null){
				return null;
			}
			Element td = table.select("td.name_result").get(0);
			String name = td.text();
			String email = table.select("a[href^=mailto:]").get(0).text();
			Elements trs = table.getElementsByTag("tr");
			info = this.createStudentInfo(name, email);
			System.out.println("name="+name+":email="+email);
		}catch(Exception e){
			e.printStackTrace();
		}
		return info;
	}

}
