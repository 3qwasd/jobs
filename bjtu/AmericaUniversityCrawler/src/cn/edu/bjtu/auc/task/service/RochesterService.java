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
public class RochesterService extends BaseService {

	/**
	 * 
	 */
	public RochesterService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementById("ctl00_MainContentArea_GVStudentResults");
		if(table == null){
			addNoResultMsg(lastName);
			return null;
		}
		Elements trs = table.getElementsByTag("tr");
		if(trs!=null&&trs.size()>1){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					String name = tr.select("a[href^=javascript:__doPostBack]").get(0).text();
					String email ="https://info.rochester.edu/Directory/"+ tr.select("img[src^=JpegImage.ashx]").get(0).attr("src");
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
	 * @param lastName
	 * @return
	 */
	public Map<String, String> getParams(String lastName) {
		// TODO Auto-generated method stub
		Map<String,String> params = new HashMap<String, String>();
		String __EVENTVALIDATION = this.getElementById("__EVENTVALIDATION").attr("value");
		String __VIEWSTATE =  this.getElementById("__VIEWSTATE").attr("value");
		params.put("__EVENTARGUMENT", "");
		params.put("__EVENTTARGET", "ctl00$MainContentArea$btnSearch");
		params.put("__EVENTVALIDATION", __EVENTVALIDATION);
		params.put("__VIEWSTATE", __VIEWSTATE);
		params.put("__VIEWSTATEENCRYPTED", "");
		params.put("__LASTFOCUS", "");
		params.put("ctl00$MainContentArea$ddlCollege", "");
		params.put("ctl00$MainContentArea$txtEmail", "");
		params.put("ctl00$MainContentArea$txtName", lastName);
		params.put("ctl00$MainContentArea$txtNETID", "");
		params.put("ctl00$MainContentArea$txtPassword", "");
		params.put("ctl00$MainContentArea$txtProgram", "");
		return params;
	}

}
