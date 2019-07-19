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
public class DrexelService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementByTag("table");
		if(table==null){
			resourceManager.addMessage(lastName+" has no result !");
			return null;
		}
		Elements trs = table.select("tr.result-row");
		if(trs!=null&&trs.size()>0){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<trs.size();i++){
				try{
					Element tr = trs.get(i);
					String name = tr.select("span.fullname").get(0).text();
					if(name.toLowerCase().startsWith(lastName.toLowerCase()+", ")){
						String email = tr.select("a[href^=mailto:]").get(0).text();
						resourceManager.addMessage("name="+name+",email="+email);
						StudentInfo info = new StudentInfo();
						info.putInfo("name", name);
						info.putInfo("email", email);
						infos.add(info);
					}
				}catch(Exception e){
					continue;
				}
			}
			return infos;
		}
		return null;
	}
	
	public String getUrl(String lastName){
		String url = "http://www.drexel.edu/search/?q={lastName}&t=student&num=10&";
		return url.replace("{lastName}", lastName);
	}
}
