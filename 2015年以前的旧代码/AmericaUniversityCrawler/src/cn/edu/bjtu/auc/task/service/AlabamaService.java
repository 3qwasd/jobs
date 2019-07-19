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
import cn.edu.bjtu.auc.ui.utils.ResourceManager;

/**
 * @author QiaoJian
 *
 */
public class AlabamaService extends BaseService {
	
	
	public List<StudentInfo> collecte(String lastName){
		Element table = this.getElementByTag("table");
		Elements trs = table.getElementsByTag("tr");
		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		List<String> tableHeaders = null;
		for(int i=0;i<trs.size();i++){
			Element tr = trs.get(i);
			if(i == 0){
				tableHeaders = collecteTh(tr);
			}else{
				String nameTd = tr.select("td.name").get(0).text();
				if(!nameTd.toLowerCase().matches(lastName.toLowerCase()+",[\\x00-\\xff]*")){
					continue;
				}
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
	public String collecteNextPageUrl(){
		Elements elements = this.getElementsBySelector("span.next");
		if(elements!=null&&elements.size()>0){
			Element page = elements.get(0);
			Elements a = page.getElementsByTag("a");
			if(a!=null&&a.size()>0)
				return a.get(0).attr("href");
		}
		return null;
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
	
	/**
	 * 获取参数
	 * @return 
	 */
	public Map<String, String> getPreParams(){
		
		Map<String,String> preParams = new HashMap<String, String>();
		
		
		preParams.put("CSRFName", getPreParam("input[name=CSRFName]"));
		preParams.put("CSRFToken", getPreParam("input[name=CSRFToken]"));
		return preParams;
	}
	/**
	 * 获取单参数
	 * @param select
	 * @return
	 */
	public String getPreParam(String select){
		Elements eles = this.getElementsBySelector(select);
		Element element = eles.get(0);
		String value = element.attr("value");
		return value;
	}
}
