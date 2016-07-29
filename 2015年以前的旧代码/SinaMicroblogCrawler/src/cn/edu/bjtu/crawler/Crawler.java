/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler;
import java.util.ArrayList;
import java.util.List;


import cn.edu.bjtu.crawler.bean.sina.LoginUserAccount;
import cn.edu.bjtu.crawler.exception.LoginException;
import cn.edu.bjtu.crawler.network.HttpConnectionerManager;
import cn.edu.bjtu.crawler.network.sina.SinaHttpClient;
import cn.edu.bjtu.crawler.network.sina.SinaLoginer;
import cn.edu.bjtu.crawler.sql.myatis.SinaSqlDao;
import cn.edu.bjtu.crawler.sql.myatis.domain.CrawlerConfig;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogger;
import cn.edu.bjtu.crawler.sql.myatis.mapper.SinaMicrobloggerMapper;
import cn.edu.bjtu.crawler.thread.CollectBloggerDataTask;
import cn.edu.bjtu.crawler.utils.CrawlerGlobals;
import cn.edu.bjtu.joe.system.thread.JoeThreadService;
import cn.edu.bjtu.joe.system.utils.SystemUtils;

/**
 * @author QiaoJian
 *
 */
public class Crawler {
	
	HttpConnectionerManager clientManager;
		
	public Crawler() {
		super();
		// TODO Auto-generated constructor stub
		clientManager = HttpConnectionerManager.getInstance();
	}
	
	/**
	 * 初始化连接池
	 */
	public void initClientPool(){
		int processNum = SystemUtils.getSystemProcessorNumber();
		if(processNum>4){
			processNum = 4;
		}
		for(int i=0;i<processNum;i++){
			LoginUserAccount account = CrawlerGlobals.getLoginUser();
			createClientAndLogin(account);
		}

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
	public void start() {
		this.initClientPool();
		JoeThreadService joeThreadService = JoeThreadService.getThreadService();
		SinaSqlDao sinaSqlDao = new SinaSqlDao();
		SinaMicrobloggerMapper microbloggerMapper = 
				(SinaMicrobloggerMapper) sinaSqlDao.getMyBatisProxyMapper(SinaMicrobloggerMapper.class);
		CrawlerConfig config = microbloggerMapper.getConfigInfo().get(0);
		int deep = config.getDeep();
		long page = config.getPage();
		System.out.println("info--start deep:"+deep);
		System.out.println("info--start page:"+page);
		List<SinaMicroblogger> cache = new ArrayList<SinaMicroblogger>();
		cache.addAll(microbloggerMapper.selectBloggerForCollect(deep, page*10,10));
		long startTime = System.currentTimeMillis();
		while(true){
			int activeCount = joeThreadService.getActiveCount();
			System.out.println("info-- activie thread number:"+activeCount);
			/*深度大于4停止抓取*/
			try {

				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(deep > 4){
				joeThreadService.shutdownService();
				return;
			}
			/*缓存空，继续载入*/
			if(cache.size()<1){
				page++;
				System.out.println("info--start deep:"+deep);
				System.out.println("info--start page:"+page);
				microbloggerMapper.updatePage(page);
				sinaSqlDao.commit();
				cache.addAll(microbloggerMapper.selectBloggerForCollect(deep, page*10,10));
				if(cache.size()<1){
					deep++;
					page=0;
					System.out.println("info--start deep:"+deep);
					System.out.println("info--start page:"+page);
					microbloggerMapper.updatePage(page);
					microbloggerMapper.updateDeep(deep);
					sinaSqlDao.commit();
					continue;
				}
			}
			long nowTime = System.currentTimeMillis(); 
			if(activeCount<4&&(nowTime - startTime)<1000*60*5&&clientManager.hasVisibleHttpClient()){
				SinaMicroblogger taskTarget = cache.get(0);
				if(taskTarget.getCompleteFlag().equals("n")){
					System.out.println("info-- submit url:"+taskTarget.getMblogUrl());
					CollectBloggerDataTask dataTask = 
							new CollectBloggerDataTask(taskTarget.getMblogUrl(), taskTarget.getMblogUrl(), taskTarget);
					joeThreadService.submitTask(dataTask);
				}
				cache.remove(taskTarget);
			}
			System.out.println("info -- catche size:"+cache.size());
			if(activeCount == 0&&(nowTime - startTime)>1000*60*5){
				clientManager.clearPool();
				CrawlerGlobals.initLoginUsers();
				this.initClientPool();
				startTime = System.currentTimeMillis();
			}
			if((nowTime - startTime)>1000*60*15){
				joeThreadService.reset();
				joeThreadService = JoeThreadService.getThreadService();
				startTime = System.currentTimeMillis();
			}
		}

	}
	
	public static void main(String[] args) {
		Crawler crawler = new Crawler();
		crawler.start();
	}
}
