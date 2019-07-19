/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.bjtu.auc.BaseService;
import cn.edu.bjtu.auc.StudentInfo;

/**
 * @author QiaoJian
 *
 */
public class IllinoisService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementBySelector("table[summary=Table of students]");
		if(table == null){
			resourceManager.addMessage(lastName+" search results is null!");
			return null;
		}
		Elements trs = table.getElementsByTag("tr");
		if(trs!=null&&trs.size()>0){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					String name = tr.getElementsByTag("a").get(0).text();
					if(!name.toLowerCase().startsWith(lastName.toLowerCase())){
						continue;
					}
					String email = getEmail(tr.getElementsByTag("script").get(0).html());
					StudentInfo info = new StudentInfo();
					info.putInfo("name", name);
					info.putInfo("email", email);
					infos.add(info);
					resourceManager.addMessage("name="+name+",email="+email);
				}catch(Exception e){
					continue;
				}
			}
			return infos;
		}
		return null;
	}
	/**
	 * @param html
	 * @return
	 */
	private String getEmail(String html) {
		Pattern pattern = Pattern.compile("(?<s>)displayIllinois\\(\"[0-9a-zA-z]+\"\\)");
		Matcher matcher = pattern.matcher(html);
		String mail = "";
		if(matcher.find()){
			mail = matcher.group(0);
		}
		mail = mail.replace("\"", "").replace("displayIllinois(", "").replace(")","")+"@illinois.edu";
		return mail;
	}
	public String getSearchUrl(String name){
		String url = "http://illinois.edu/ds/search?skinId=0&sub=&search={name}&search_type=student";
		return url.replace("{name}", name);
	}
	public boolean toManyResult(){
		Element msgDiv = this.getElementBySelector("div.ws-ds-text");
		if(msgDiv == null){
			return false;
		}
		String msg = msgDiv.text();
		resourceManager.addMessage(msg);
		if(msg.contains("Too many results")){
			return true;
		}
		return false;
	}
}
