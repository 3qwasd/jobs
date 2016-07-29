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
public class FiveZeroSevenService extends BaseService {

	/**
	 * 
	 */
	public FiveZeroSevenService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	public List<String> collecteLinks() {
		// TODO Auto-generated method stub
		List<String> links = new ArrayList<String>();
		Elements as = this.getElementsBySelector("a[href^=help_des_]");
		for(int i=0;i<as.size();i++){
			String link = "https://172.16.1.2/site/help/ips"+as.get(i).attr("href");
			links.add(link);
		}
		return links;
	}

	/**
	 * @return
	 */
	public StudentInfo collectContent() {
		// TODO Auto-generated method stub
	
		Elements trs = this.getElementsByTag("tr");
		StudentInfo info = new StudentInfo();
		for(int i=1;i<trs.size();i++){
			Element tr = trs.get(i);
			Elements td = tr.getElementsByTag("td");
			info.putInfo(td.get(0).text(), td.get(1).text());
		}
	
		return info;
	}

}
