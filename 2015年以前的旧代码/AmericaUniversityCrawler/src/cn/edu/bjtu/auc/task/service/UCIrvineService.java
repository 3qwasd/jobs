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
public class UCIrvineService extends BaseService {

	public String getSearcheUrl(String lastName,String firstName){
		String baseUrl = "http://directory.uci.edu/index.php?"
				+ "search_group=students&form_fname={firstName}&form_fname_filter="
				+ "starts+with&form_lname={lastName}&form_lname_filter=exact&"
				+ "form_email=&form_email_filter=starts+with&form_ucinetid=&"
				+ "form_ucinetid_filter=starts+with&form_department=&"
				+ "form_department_filter=starts+with&form_phone=&"
				+ "advanced_submit=Search&form_type=advanced_search";
		String newUrl = baseUrl.replace("{lastName}", lastName).replace("{firstName}", firstName);
		return newUrl;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementBySelector("table[summary=Search results]");
		if(table == null)
			return null;
		String typycne= "";
		try{
			Element element = this.getElementsByTag("script").get(0);
			typycne = this.getTypycne(element.html());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		Elements trs = table.getElementsByTag("tr");
		if(trs!=null&&trs.size()>0){
			for(int i=0;i<trs.size();i++){
				Element tr = trs.get(i);
				try{
					StudentInfo info = new StudentInfo();
					String name = tr.getElementsByTag("a").get(0).text();
					Elements spans = tr.select("span.departmentmajor");
					String department = "";
					if(spans!=null&&spans.size()>0){
						for(int j=0;j<spans.size();j++){
							department+=spans.get(j).text()+", ";
							
						}
					}
					info.putInfo("name", name);
					info.putInfo("department", department);
					String email = getEmail(tr.getElementsByTag("script").html(),typycne);
					info.putInfo("email", email);
					resourceManager.addMessage("name="+name+",email="+email+",department="+department);
					infos.add(info);
				}catch(Exception e){
					continue;
				}
			}
		}else{
			return null;
		}
		return infos;
	}

	/**
	 * @param html
	 * @return
	 */
	private String getTypycne(String html) {

		Pattern pattern = Pattern.compile("(?<s>)vartpyrcne='[\\x00-\\xff]*';\n\t\t\t\tvarfixed");
		html = html.trim().replace(" ", "");
		Matcher matcher = pattern.matcher(html);
		String typycne = "";
		if(matcher.find()){
			typycne = matcher.group(0);
		}
		typycne = typycne.replace("vartpyrcne='", "").replace("';\n\t\t\t\tvarfixed", "");
		return typycne;
	}

	/**
	 * @param html
	 * @return
	 */
	public String getEmail(String html,String typycne) {
		Pattern pattern = Pattern.compile("(?<s>)'[&#;0987654321]+'");
		Matcher matcher = pattern.matcher(html);
		String decoded = "";
		if(matcher.find()){
			decoded = matcher.group(0);
		}
		if(decoded.length()>0){
			return decode(decoded.replace("'", ""),typycne);
		}
		return null;
	}
	private String decode(String str,String typycne){
		String tpyrcne = typycne;
		String fixed = "&#;0987654321";	
		String decoded = ""; 
		for (int i=0; i<str.length(); i++) {
			decoded += tpyrcne.charAt(fixed.indexOf(str.charAt(i)));
		}
		Pattern pattern = Pattern.compile("(?<s>)[0-9]+");
		Matcher matcher = pattern.matcher(decoded);
		String email = "";
		while (matcher.find()) {
			String num = matcher.group(0);
			int acc = Integer.valueOf(num);
			char c = (char) acc;
			email+=c;
		}
		return email;
	}
}
