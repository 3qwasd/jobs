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
public class OhioStateService extends BaseService {

	/**
	 * 
	 */
	public OhioStateService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element div = this.getElementById("results_box");
		if(div == null){
			addNoResultMsg(lastName);
			return null;
		}
		Element table = null;
		try{
			table = div.select("table.results_table").get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(table == null){
			addNoResultMsg(lastName);
			return null;
		}
		
		Elements trs = table.getElementsByTag("tr");
		if(trs!=null&&trs.size()>1){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=1;i<trs.size();i++){
				Element tr = trs.get(i);
				String name = tr.select("a[href^=javascript:toggle]").get(0).text();
				String email = tr.select("a[href^=javascript:sendMailTo]").get(0).parent().text();
				StudentInfo info = createStudentInfo(name, email);
				infos.add(info);
			}
			return infos;
		}else{
			addNoResultMsg(lastName);
			return null;
		}
	}

}
