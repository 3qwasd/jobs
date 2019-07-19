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
public class SaintMichaelService extends BaseService{

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		Element table = this.getElementById("resultsTable");
		Elements trs = table.getElementsByTag("tr");
		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		List<String> tableHeaders = null;
		for(int i=0;i<trs.size();i++){
			Element tr = trs.get(i);
			if(i == 0){
				tableHeaders = collecteTh(tr);
			}else{
				List<String> tdInfos = collecteTd(tr);

				if(tableHeaders!=null&&tableHeaders.size()>0
						&&tdInfos!=null&&tdInfos.size()>0){
					String lastNameInfo = tdInfos.get(tableHeaders.indexOf("Last"));
					if(lastNameInfo.equals(lastName)){
						StudentInfo info = new StudentInfo();
						for(int j=0;j<tableHeaders.size();j++){
							info.putInfo(tableHeaders.get(j), tdInfos.get(j));
						}
						infos.add(info);
					}
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

			String text = td.text();
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
	 * @return
	 */
	public Map<String,String> getPreParams() {
		// TODO Auto-generated method stub
		Map<String,String> params = new HashMap<String, String>();
		params.put("__EVENTTARGET",this.getElementById("__EVENTTARGET").attr("value"));
		params.put("__EVENTARGUMENT",this.getElementById("__EVENTARGUMENT").attr("value"));
		params.put("__VIEWSTATE",this.getElementById("__VIEWSTATE").attr("value"));
		params.put("__EVENTVALIDATION",this.getElementById("__EVENTVALIDATION").attr("value"));
		params.put("ctl10$txtSearch", "Search this site");
		params.put("ctl11$txtComments", "");
		params.put("ctl11$txtEmail", "");
		params.put("ctl11$txtFriendEmail", "");
		params.put("ctl11$txtName", "");
		params.put("maincontent_1$btnSubmit", "Submit");
		params.put("maincontent_1$firstname", "");
		return params;
	}

}
