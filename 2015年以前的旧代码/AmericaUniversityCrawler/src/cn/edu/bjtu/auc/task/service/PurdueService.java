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
public class PurdueService extends BaseService {

	/**
	 * 
	 */
	public PurdueService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element div = this.getElementById("directoryResults");
		if(div == null){
			this.addNoResultMsg(lastName);
			return null;
		}
		Elements lis = this.getElementsBySelector("li.vcard");
		if(lis!=null&&lis.size()>0){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<lis.size();i++){
				Element li = lis.get(i);
				try{
					String name = li.select("span.fn").get(0).text();
					String email = li.select("a[href^=mailto:]").get(0).text();
					StudentInfo info = createStudentInfo(name, email);
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

	/**
	 * @return
	 */
	public boolean isToManyResult() {
		// TODO Auto-generated method stub
		Element h1 = this.getElementBySelector("h1.error");
		if(h1 == null)
			return false;
		String msg = h1.text();
		if(msg.contains("Your search has returned too many entries."))
			return true;
		
		return false;
	}

}
