package cn.edu.bjtu.auc.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.bjtu.auc.HttpConnectioner;
import cn.edu.bjtu.auc.HttpManager;
import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.task.service.IllinoisService;
import cn.edu.bjtu.auc.task.service.UoregonService;
import cn.edu.bjtu.auc.ui.utils.ResourceManager;

public class IllinoisMain {

	public static void main(String[] args) throws Exception{
		List<String> firstNames = ResourceManager.getInstance().getFirstNames();
		HttpManager httpManager = HttpManager.getInstance();
		HttpConnectioner connectioner = httpManager.getFirstHttpConnection();
		if(connectioner == null){
			httpManager.createHttpConnection("111.11.27.90", 9999);
			connectioner = httpManager.getFirstHttpConnection();
		}
		IllinoisService service = new IllinoisService();
		Map<String,String> headers = connectioner.createCommonHeader();
		headers.put("Host", "illinois.edu");

		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		String url = "http://illinois.edu/ds/search?skinId=0&sub=&search={name}&search_type=student";
		for(String firstName:firstNames){
			try{
				HttpResponse response = null;
				String lastName="";
				//firstName+"*+"+lastName
				response = connectioner.doGet(service.getSearchUrl(firstName+"*+"+lastName), headers);
				String html = service.getStringFromResponse(response);
				service.parserHtml(html);
				infos = service.collecte(lastName);
				service.writeToExcel(infos, "I:\\UoregonNew.xls", "sheet");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
