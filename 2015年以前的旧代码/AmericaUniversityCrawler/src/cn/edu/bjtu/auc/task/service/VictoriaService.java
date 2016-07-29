/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.internal.runners.model.EachTestNotifier;

import cn.edu.bjtu.auc.BaseService;
import cn.edu.bjtu.auc.StudentInfo;

/**
 * @author QiaoJian
 *
 */
public class VictoriaService extends BaseService{

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element div = this.getElementBySelector("div[id^=dir-tabs-]");
		if(div == null){
			resourceManager.addMessage(lastName+" no result fo search!");
			return null;
		}
		Element tabA = div.getElementById("PersSearchHeader_last_name");
		if(tabA == null){
			resourceManager.addMessage(lastName+" no result fo search!");
			return null;
		}
		String emailDivId = tabA.attr("href").replace("#", "");
		Element emailDiv = this.getElementById(emailDivId);
		if(emailDiv == null){
			resourceManager.addMessage(lastName+" no result fo search!");
			return null;
		}
		Elements trs = emailDiv.select("tr.DirListing");
		if(trs!=null&&trs.size()>0){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					String name = tr.select("a.DirCompleteListing").get(0).text();
					if(name.toLowerCase().endsWith(lastName.toLowerCase())){
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

	/**
	 * @param lastName
	 * @return
	 */
	public String getUrl(String lastName) {
		// TODO Auto-generated method stub
		String url = "http://www.uvic.ca/search/q/directory.php?qtype=pers&persq={lastName}";
		return url.replace("{lastName}", lastName);
	}

}
