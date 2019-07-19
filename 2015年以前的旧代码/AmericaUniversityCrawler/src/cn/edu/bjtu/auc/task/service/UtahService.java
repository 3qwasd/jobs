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
public class UtahService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementById("tablesorter");
		if(table==null)
			return null;
		
		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		List<String> tableHeaders = null;
		Elements trs = table.getElementsByTag("tr");
		for(int i=0;i<trs.size();i++){
			Element tr = trs.get(i);
			
			if(i==0){
				tableHeaders = collecteTh(tr);
			}else{
				List<String> tdInfos = collecteTd(tr,lastName);
				if(tableHeaders!=null&&tableHeaders.size()>0
						&&tdInfos!=null&&tdInfos.size()>0){
					StudentInfo info = new StudentInfo();
					for(int j=0;j<tableHeaders.size();j++){
						info.putInfo(tableHeaders.get(j), tdInfos.get(j));
					}
					infos.add(info);
				}
			}
		}
		return infos;
	}
	public List<String> collecteTh(Element tr){
		List<String> tableHeaders = new ArrayList<String>();
		Elements ths = tr.getElementsByTag("th");
		for(int i=0;i<ths.size();i++){
			Element th = ths.get(i);
			String text = th.text();
			tableHeaders.add(text);
		}
		return tableHeaders;
	}
	public List<String> collecteTd(Element tr,String lastName){
		List<String> tdInfos = new ArrayList<String>();
		Elements tds = tr.getElementsByTag("td");
		for(int i=0;i<tds.size();i++){
			Element td = tds.get(i);
			String text = "";
			if(td.className().equals("toCenter")){
				text = findMail(td.html());
			}else if(td.html().replace(" ", "").matches("<\\!\\-\\-[\\x00-\\xff]*\\-\\-><a[\\x00-\\xff]*</a>")){
				text = td.text();
				if(!text.toLowerCase().matches(lastName.toLowerCase()+",[\\x00-\\xff]*")){
					return null;
				}
			}else{
				text = td.text();
			}
			tdInfos.add(text);
			
		}
		return tdInfos;
	}
	public String findMail(String html){
		Pattern pattern = Pattern.compile("(?<s>)[a-zA-z.]+@[a-zA-z.]+");
		Matcher matcher = pattern.matcher(html.replace("\"", "").replace("+", "").replace(" ", ""));
		String mail = "";
		if(matcher.find()){
			mail = matcher.group(0);
			return mail;
		}else{
			return null;
		}
	}
}
