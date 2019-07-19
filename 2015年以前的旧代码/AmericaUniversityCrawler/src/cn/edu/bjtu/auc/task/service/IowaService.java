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
public class IowaService extends BaseService{

	/**
	 * 
	 */
	public IowaService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		Elements trsOdd = getElementsBySelector("tr.table_row_odd");
		if(trsOdd!=null&&trsOdd.size()>0){
			for(int i=0;i<trsOdd.size();i++){
				Element tr = trsOdd.get(i);
				try{
					String name = tr.select("a[href^=advanced.aspx?mail=]").get(0).text();
					if(!name.toLowerCase().startsWith(lastName.toLowerCase()+",")){
						continue;
					}
					String link = "http://dnaapps.uiowa.edu/PublicDirectory/"+tr.select("a[href^=advanced.aspx?mail=]").get(0).attr("href");
					String email = tr.select("a[href^=mailto:]").get(0).text();
					StudentInfo info = createStudentInfo(name, email);
					info.putInfo("link", link);
					infos.add(info);
				}catch(Exception e){
					continue;
				}
			}
		}
		Elements trsEven = getElementsBySelector("tr.table_row_even");
		if(trsEven!=null&&trsEven.size()>0){
			for(int i=0;i<trsEven.size();i++){
				Element tr = trsEven.get(i);
				try{
					String name = tr.select("a[href^=advanced.aspx?mail=]").get(0).text();
					if(!name.toLowerCase().startsWith(lastName.toLowerCase()+",")){
						continue;
					}
					String link = "http://dnaapps.uiowa.edu/PublicDirectory/"+tr.select("a[href^=advanced.aspx?mail=]").get(0).attr("href");
					String email = tr.select("a[href^=mailto:]").get(0).text();
					StudentInfo info = createStudentInfo(name, email);
					info.putInfo("link", link);
					infos.add(info);
				}catch(Exception e){
					continue;
				}
			}
		}
		if(infos.size()<1){
			addNoResultMsg(lastName);
			return null;
		}else{
			return infos;
		}
	}
	public Map<String,String> getParams(String lastName){
		Map<String,String> params = new HashMap<String, String>();
		params.put("__EVENTARGUMENT", "");
		params.put("__EVENTTARGET", "");
		params.put("__LASTFOCUS", "");
		params.put("ctl00$ContentPlaceHolder1$SearchBtn", "Search for Faculty, Staff, Students");
		params.put("ctl00$ContentPlaceHolder1$SimpleSearchNameTextBox", lastName);
		String __EVENTVALIDATION = this.getElementById("__EVENTVALIDATION").val();
		String __VIEWSTATE = this.getElementById("__VIEWSTATE").val();
		params.put("__EVENTVALIDATION", __EVENTVALIDATION);
		params.put("__VIEWSTATE", __VIEWSTATE);
		return params;
	}

	/**
	 * @param info
	 */
	public void collecteInfo(StudentInfo info) {
		// TODO Auto-generated method stub
		Element table = this.getElementByTag("table");
		List<String> keys = new ArrayList<String>();
		keys.add("E-mail routing address");
		keys.add("HawkID");
		keys.add("University Classification");
		keys.add("Phone");
		keys.add("Person Type");
		keys.add("College");
		keys.add("Home City");
		keys.add("Residing Address");
		keys.add("Residing Phone");
		for(String str:keys){
			info.putInfo(str, "null");
		}
		if(table == null)
			return;
		Elements trs = table.getElementsByTag("tr");
		if(trs!=null&&trs.size()>0){
			for(int i=0;i<trs.size();i++){
				try{
					Element tr = trs.get(i);
					Elements tds = tr.getElementsByTag("td");
					
					String key = tds.get(0).text();
					String value = tds.get(1).text();
					if(keys.contains(key)){
						info.putInfo(key, value);
					}
					
				}catch(Exception e){

				}
			}
		}
		info.getInfos().remove("link");
	}
}
