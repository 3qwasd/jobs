/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sina.test;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cn.edu.bjtu.crawler.bean.sina.LoginUserAccount;
import cn.edu.bjtu.crawler.exception.LoginException;
import cn.edu.bjtu.crawler.network.HttpConnectionerManager;
import cn.edu.bjtu.crawler.network.sina.HotWeiboDowloader;
import cn.edu.bjtu.crawler.network.sina.SinaHttpClient;
import cn.edu.bjtu.crawler.network.sina.SinaLoginer;
import cn.edu.bjtu.crawler.servicer.sina.HotWeiboService;
import cn.edu.bjtu.crawler.utils.CrawlerGlobals;
/**
 * @author QiaoJian
 *
 */
public class WeiboTest {

	HttpConnectionerManager clientManager;

	HotWeiboDowloader dowloader;
	
	
	public void init(){
		clientManager = HttpConnectionerManager.getInstance();


		LoginUserAccount account = CrawlerGlobals.getLoginUser();
		createClientAndLogin(account);
		dowloader = new HotWeiboDowloader();
		dowloader.setHttpClient(clientManager.getFirstHttpClient());
	}
	public void writeToText(InputStream inputStream,String fileName){
		try{
			byte[] buffer = new byte[1024];
			int i;
			FileOutputStream outputStream = new FileOutputStream(fileName);
			while ((i=inputStream.read(buffer))>0) {
				outputStream.write(buffer);
			}
			outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testExtrackJson(){
		HotWeiboService service = new HotWeiboService();
		String res = service.readFromFile("I:\\weibopage_2.html");
		String html = service.extractHtmlFromJson(res);
		System.out.println(html);
		String lastId = service.extractHotWeibo(html);
		System.out.println(lastId);
	}
	@Test
	public void testSearchWeibo(){
		init();
		dowloader.searchWeibo();
	}
	@Test
	public void testExtract(){
		HotWeiboService service = new HotWeiboService();
		String html = service.readFromFile("I:\\files\\month.htm");
		service.extractHotWeibo(html);
	}
	@Test
	public void testExtractComment(){
		HotWeiboService service = new HotWeiboService();
		String html = service.readFromFile("I:\\josnHtml.html");
		service.extractComment(html);
	}
	@Test
	public void testDowloadWeibo(){
		init();
		List<String> ids = dowloader.readWeiboIds("I:\\weibo\\relation\\hot.txt");
		for(String id:ids){
			dowloader.dowloadComment(id);
		}
	}
	@Test
	public void dowloader(){
		init();
		dowloader.dowload();

		dowloader.giveBackHttpClient();
	}

	
	/**
	 * 创建新的httpClient并且登陆
	 * @param account
	 */
	public void createClientAndLogin(LoginUserAccount account){
		SinaHttpClient httpClient = clientManager.createSinaHttpClient();

		SinaLoginer sinaLoginer = new SinaLoginer(httpClient, account);
		boolean isLogin = false;
		try {
			isLogin = sinaLoginer.login();
			if(isLogin){
				System.out.println(account.getUserName()+" login success! HttpClient.hashCode="+httpClient.hashCode());
			}else{
				System.out.println(account.getUserName()+" login fail!");
				CrawlerGlobals.giveBackLoginUser(account);
				clientManager.removeHttpClient(httpClient);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof LoginException){
				((LoginException) e).showMessage();
			}
			System.out.println(account.getUserName()+" login fail!");
			CrawlerGlobals.giveBackLoginUser(account);
			clientManager.removeHttpClient(httpClient);
		}
	}
	
}
