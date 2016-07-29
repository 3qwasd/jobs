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
public class UCLAService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element resultDiv = this.getElementById("results-wrapper");
		if(resultDiv == null){
			return null;
		}
		Elements trs = resultDiv.getElementsByTag("tr");
		if(trs!=null&&trs.size()>0){
			List<StudentInfo> infos = new ArrayList<StudentInfo>();
			for(int i=0;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					
					String name = tr.select("a[rel=lightbox]").get(0).text();
					if(!name.toLowerCase().startsWith(lastName.toLowerCase()))
						continue;
					String email = tr.getElementsByTag("img").get(0).attr("alt");
					StudentInfo info = new StudentInfo();
					info.putInfo("name",name);
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
	public boolean resultsExceeded(int count){
		Element msgDiv = this.getElementBySelector("div.pexit");
		if(msgDiv == null){
			return false;
		}
		String text = msgDiv.text();
		resourceManager.addMessage(text);
		if(text.contains("Results exceeded")){
			return true;
		}
		return false;
	}
	public Map<String,String> getParams(String lastName){
		Map<String,String> params = new HashMap<String,String>();
		params.put("admcode", "");
		params.put("cn", lastName);
		params.put("department", "");
		params.put("group", "student");
		
		params.put("mail", "");
		params.put("postaladdress", "");
		params.put("postalcode", "");
		params.put("querytype", "person");
		params.put("searchtype", "advanced");
		params.put("telephonenumber", "");
		params.put("url", "");
		params.put("title", "");
		return params;
	}
}
