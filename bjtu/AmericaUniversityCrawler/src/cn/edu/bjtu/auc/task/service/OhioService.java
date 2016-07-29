/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import cn.edu.bjtu.auc.BaseService;
import cn.edu.bjtu.auc.StudentInfo;

/**
 * @author QiaoJian
 *
 */
public class OhioService extends BaseService{

	public String buildQueryStr(String lastName){
		String queryStr = "q=STD%3A{lastName}&btnG=People+Search&client=peoplesearch_frontend&"
				+ "proxystylesheet=peoplesearch_frontend&ulang=zh-CN&"
				+ "sort=date%3AD%3AL%3Ad1&entqr=3&entqrm=0&oe=UTF-8&ie=UTF-8&ud=1&site=peoplesearch";
		return queryStr.replace("{lastName}", lastName);
	}

	/**
	 * @return
	 */
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element mainDiv = this.getElementById("ps_main_div");
		if(mainDiv==null){
			return null;
		}
		Elements infoDivs = mainDiv.getElementsByTag("div");

		if(!(infoDivs!=null&&infoDivs.size()>0)){
			return null;
		}
		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		for(int i=0;i<infoDivs.size();i++){
			try{
				StudentInfo info = new StudentInfo();
				Element infoDiv = infoDivs.get(i);
				String name = infoDiv.getElementsByTag("b").get(0).text();
				String email = infoDiv.getElementsByTag("a").get(0).text();
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


}
