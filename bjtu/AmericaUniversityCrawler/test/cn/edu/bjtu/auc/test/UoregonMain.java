package cn.edu.bjtu.auc.test;

import java.io.File;
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
import cn.edu.bjtu.auc.task.service.UoregonService;

public class UoregonMain {

	public static void main(String[] args) throws Exception{
		String fileUrl = "I:\\html\\html5.html";
		Document doc = Jsoup.parse(new File(fileUrl),"UTF-8");
		HttpManager httpManager = HttpManager.getInstance();
		HttpConnectioner connectioner = httpManager.getFirstHttpConnection();
		if(connectioner == null){
			httpManager.createHttpConnection("202.108.50.75", 80);
			connectioner = httpManager.getFirstHttpConnection();
		}
		UoregonService service = new UoregonService();
		Map<String,String> headers = connectioner.createCommonHeader();
		headers.put("Host", "uoregon.edu");

		Elements as = doc.select("a[href^=/findpeople/person/]");
		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		if(as!=null&&as.size()>0){
			for(int i=0;i<as.size();i++){
				try{
					Element a = as.get(i);
					String link = "http://uoregon.edu"+a.attr("href");
					String name = a.text();
					HttpResponse response = connectioner.doGet(link,headers);
					String html = service.getStringFromResponse(response);
					Document temp = Jsoup.parse(html);
					String mail = temp.select("a[HREF^=mailto:]").get(0).text();
					if(mail.equals("NTS@ithelp.uoregon.edu")){
						break;
					}
					System.out.println(name+":"+mail);
					StudentInfo info = new StudentInfo();
					info.putInfo("name", name);
					info.putInfo("email", mail);
					infos.add(info);
					service.writeToExcel(infos, "I:\\UoregonNew.xls", "sheet");
					infos.clear();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

		//service.writeToExcel(infos, "I:\\UoregonNew.xls", "sheet");
	}
}
