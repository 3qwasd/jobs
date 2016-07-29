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
public class WashingtonService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element element = this.getElementBySelector("a[href=#s1]");
		if(element == null)
			return null;
		String [] strs = element.text().split(" ");
		int num = Integer.valueOf(strs[0]);
		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		List<String> tableHeaders = null;
		Element table = this.getElementBySelector("table[summary=results]");
		Elements trs = table.getElementsByTag("tr");
		for(int i=0;i<num+2;i++){
			Element tr = trs.get(i);
			if(i==0){
				tableHeaders = collecteTh(tr);
			}else if(i>1){
				List<String> tdInfos = collecteTd(tr);
				if(tableHeaders!=null&&tableHeaders.size()>0
						&&tdInfos!=null&&tdInfos.size()>0){
					StudentInfo info = new StudentInfo();
					String msg = "";
					for(int j=0;j<tableHeaders.size();j++){
						info.putInfo(tableHeaders.get(j), tdInfos.get(j));
						msg+=tableHeaders.get(j)+"="+tdInfos.get(j)+" ";
					}
					resourceManager.addMessage(msg);
					infos.add(info);
				}
			}
		}
		return infos;
	}
	public List<String> collecteTd(Element tr){
		List<String> tdInfos = new ArrayList<String>();
		Elements tds = tr.getElementsByTag("td");
		for(int i=0;i<tds.size();i++){
			Element td = tds.get(i);
			Elements as = td.getElementsByTag("a");
			String text = "";
			text = td.text();
			tdInfos.add(text);
			
		}
		return tdInfos;
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
}
