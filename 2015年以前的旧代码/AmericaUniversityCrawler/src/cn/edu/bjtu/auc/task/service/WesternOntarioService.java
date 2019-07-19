/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task.service;

import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.bjtu.auc.BaseService;
import cn.edu.bjtu.auc.StudentInfo;

/**
 * @author QiaoJian
 *
 */
public class WesternOntarioService extends BaseService {

	/**
	 * 
	 */
	public WesternOntarioService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementById("student_search_results");
		if(table == null){
			resourceManager.addMessage(lastName+"has no result for search!");
			return null;
		}
		Elements trs = table.getElementsByTag("tr");
		if(trs!=null&&trs.size()>1){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=1;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					String name = tr.getElementsByTag("td").get(0).text();
					String email = tr.getElementsByTag("td").get(1).text();
					StudentInfo info = new StudentInfo();
					info.putInfo("name", name);
					info.putInfo("email", email);
					resourceManager.addMessage("name="+name+",email="+email);
					infos.add(info);
				}catch(Exception e){
					continue;
				}
			}
			return infos;
		}else{
			resourceManager.addMessage(lastName+"has no result for search!");
		}
		return null;
	}

}
