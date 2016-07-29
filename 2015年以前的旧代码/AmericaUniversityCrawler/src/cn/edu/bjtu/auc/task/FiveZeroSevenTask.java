/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.bjtu.auc.StudentInfo;
import cn.edu.bjtu.auc.StudentInfoCollectTask;
import cn.edu.bjtu.auc.task.service.FiveZeroSevenService;

/**
 * @author QiaoJian
 *
 */
public class FiveZeroSevenTask extends StudentInfoCollectTask{
	FiveZeroSevenService service;
	/**
	 * 
	 */
	public FiveZeroSevenTask() {
		// TODO Auto-generated constructor stub
	}
	
	public FiveZeroSevenTask(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = new FiveZeroSevenService();
		baseService = service;
		resetConnectionWithSSL();
		commoneHeader = connectioner.createCommonHeader();
		commoneHeader.put("host", "172.16.1.2");
		
		String url = "https://172.16.1.2/cgi/maincgi.cgi?Url=Index";
		this.params = new HashMap<String, String>();
		this.params.put("passwd", "talent");
		this.params.put("username", "superman");
		InputStream is = this.doPostVistorGetInputstream(url);
 
		try {
			OutputStream outputStream = new FileOutputStream(new File("I:\\tip.html"));
			byte[] buffer = new byte[1024];
			int i=0;
			while((i=is.read(buffer))>-1){
				outputStream.write(buffer);
			}
			outputStream.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		url ="https://172.16.1.2/site/help/ips/index.html";
		try{
			httpResponse = connectioner.doGet(url, commoneHeader);
			html = baseService.getStringFromResponse(httpResponse);
			baseService.parserHtml(html);
		}catch(Exception e){
			e.printStackTrace();
		}
		List<String> links = service.collecteLinks();
		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		for(String link:links){
	
			try{
				httpResponse = connectioner.doGet(link, commoneHeader);
				html = baseService.getStringFromResponse(httpResponse);
				baseService.parserHtml(html);
			}catch(Exception e){
				e.printStackTrace();
			}
			StudentInfo info = service.collectContent();
			infos.add(info);
		}
		
		try {
			service.writeToExcel(infos, "I:\\help.xls", "规则");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void resetConnectionWithSSL(){
		httpManager.clearPool();
		httpManager.createHttpConnectionSSL();
		connectioner = httpManager.getFirstHttpConnection();
	}
}
