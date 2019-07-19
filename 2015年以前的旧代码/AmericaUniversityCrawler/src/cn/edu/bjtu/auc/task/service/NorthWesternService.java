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
public class NorthWesternService extends BaseService {

	/**
	 * 
	 */
	public NorthWesternService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementBySelector("table[width=600]");
		if(table == null){
			this.addNoResultMsg(lastName);
			return null;
		}
		Elements trs = table.getElementsByTag("tr");
		if(trs!=null&&trs.size()>1){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=1;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					String name = tr.getElementsByTag("b").get(0).text();
					String email = tr.select("img[src^=http://directory.northwestern.edu/image-temp-dir/]").get(0).attr("src");
					StudentInfo info = this.createStudentInfo(name, email);
					infos.add(info);
				}catch(Exception e){
					continue;
				}
			}
			return infos;
		}else{
			this.addNoResultMsg(lastName);
			return null;
		}
	}

}
