/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sina.test;


import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cn.edu.bjtu.crawler.bean.sina.BloggerHomePageBean;
import cn.edu.bjtu.crawler.bean.sina.JsonDataBean;
import cn.edu.bjtu.crawler.bean.sina.WeiboAjaxUrl;
import cn.edu.bjtu.crawler.network.sina.BloggerHomeDownloader;
import cn.edu.bjtu.crawler.network.sina.FamousUrlDowloader;
import cn.edu.bjtu.crawler.network.sina.SinaHttpLoginManager;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogTopic;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogger;
import cn.edu.bjtu.crawler.thread.CollectFamousUrlTask;
import cn.edu.bjtu.joe.system.thread.JoeThreadService;

/**
 * @author QiaoJian
 *
 */
public class SinaDowloaderTest {
	
	FamousUrlDowloader famousUrlDowloader;
	SinaHttpLoginManager httpLoginManager;
	JsonDataBean afterLoginBean;
	@Test
	public void initSinaDowload(){
		httpLoginManager = new SinaHttpLoginManager();
		afterLoginBean = (JsonDataBean) httpLoginManager.loginSinaMicroblog();
	}
	
	@Test
	public void testDowloadUserWeiboData(){
		httpLoginManager = new SinaHttpLoginManager();
		afterLoginBean = (JsonDataBean) httpLoginManager.loginSinaMicroblog();
		String url = "http://weibo.com/u/3740785832";
		BloggerHomeDownloader bloggerHomeDownloader = new BloggerHomeDownloader();
		SinaMicroblogger microblogger = new SinaMicroblogger();
		microblogger.setDeep(1);
		microblogger.setMblogUrl(url);
		bloggerHomeDownloader.collectUserWeiboData(microblogger);
	}
	@Test
	public void testDowloadWeiboData(){
		
		httpLoginManager = new SinaHttpLoginManager();
		afterLoginBean = (JsonDataBean) httpLoginManager.loginSinaMicroblog();
		String url = "http://weibo.com/dreamerjimmy";
		BloggerHomeDownloader bloggerHomeDownloader = new BloggerHomeDownloader();
		BloggerHomePageBean homePageBean = (BloggerHomePageBean) bloggerHomeDownloader.collectBloggerHomeData(url);
		if(homePageBean.getMicroblogger().getMblogNum()<1)
			return;
		String weiboUrl = homePageBean.getMicrobloggerUrls().getWeiboUrl();
		if(!weiboUrl.startsWith("http://")){
			weiboUrl = "http://weibo.com"+weiboUrl;
		}
		List<SinaMicroblogTopic> topics = bloggerHomeDownloader.collectWeiboData(weiboUrl);
		if(topics.size()<15)
			return;
		WeiboAjaxUrl weiboAjaxUrl = new WeiboAjaxUrl();
		weiboAjaxUrl.setDomain(homePageBean.getMicroblogger().getDomain());
		weiboAjaxUrl.setId(homePageBean.getMicroblogger().getSinaId());
		weiboAjaxUrl.setPage(1+"");
		weiboAjaxUrl.setPre_page(1+"");
		weiboAjaxUrl.setEnd_id(topics.get(0).getSinaId());
		weiboAjaxUrl.setMax_id(topics.get(14).getSinaId());
		weiboAjaxUrl.setPagebar(0+"");
		weiboAjaxUrl.set__rnd(new Date().getTime()+"");
		weiboAjaxUrl.setPl_name(bloggerHomeDownloader.getWeiboDomid());
		topics.addAll(bloggerHomeDownloader.collectWeiboData(weiboAjaxUrl.getUrl(),weiboUrl));
		if(topics.size()<30)
			return;
		weiboAjaxUrl.setMax_id(topics.get(topics.size()-1).getSinaId());
		weiboAjaxUrl.setPagebar(1+"");
		topics.addAll(bloggerHomeDownloader.collectWeiboData(weiboAjaxUrl.getUrl(),weiboUrl));
		System.out.println("第1页微博");
		for(SinaMicroblogTopic topic:topics){
			System.out.println(topic.getTopic());
		}
		System.out.println(topics.size());
		long weiboCount = homePageBean.getMicroblogger().getMblogNum();
		long page = weiboCount%45 == 0?(weiboCount/45):(weiboCount/45+1);
		System.out.println(page);
		for(int i=2;i<=page;i++){
			System.out.println("第"+i+"页微博");
			String pageUrl = bloggerHomeDownloader.getPageUrl();
			System.out.println(pageUrl);
			
			if(!pageUrl.startsWith("http://weibo.com"))
				pageUrl = "http://weibo.com"+pageUrl;
			topics = bloggerHomeDownloader.collectWeiboData(pageUrl,weiboUrl);
		
			weiboAjaxUrl.setMax_id(topics.get(topics.size()-1).getSinaId());
			weiboAjaxUrl.setEnd_id(topics.get(0).getSinaId());
			weiboAjaxUrl.setPage(i+"");
			weiboAjaxUrl.setPre_page(i+"");
			weiboAjaxUrl.setPagebar(0+"");
			topics.addAll(bloggerHomeDownloader.collectWeiboData(weiboAjaxUrl.getUrl(),weiboUrl));
			
			weiboAjaxUrl.setPagebar(1+"");
			weiboAjaxUrl.setMax_id(topics.get(topics.size()-1).getSinaId());
			topics.addAll(bloggerHomeDownloader.collectWeiboData(weiboAjaxUrl.getUrl(),weiboUrl));
			for(SinaMicroblogTopic topic:topics){
				System.out.println(topic.getTopic());
			}
			System.out.println(topics.size());
		}
	}
	@Test
	public void testDowloadFansData(){
		httpLoginManager = new SinaHttpLoginManager();
		afterLoginBean = (JsonDataBean) httpLoginManager.loginSinaMicroblog();
		String url = "http://weibo.com/xiena";
		BloggerHomeDownloader bloggerHomeDownloader = new BloggerHomeDownloader();
		BloggerHomePageBean homePageBean = (BloggerHomePageBean) bloggerHomeDownloader.collectBloggerHomeData(url);
		String fansUrl = homePageBean.getMicrobloggerUrls().getFansUrl();
		for(int i=1;i<=28;i++){
			List<SinaMicroblogger> result = null;
			if(bloggerHomeDownloader.getFansPageUrl() == null){
				System.out.println(fansUrl);
				result = bloggerHomeDownloader.collectFansUserData("http://weibo.com"+fansUrl,url);
			}else{
				String fansUrlPage = bloggerHomeDownloader.getFansPageUrl();
				fansUrlPage = fansUrlPage.replaceAll("page=(\\d{1,2})", "page="+i);
				System.out.println(fansUrlPage);
				result = bloggerHomeDownloader.collectFansUserData(fansUrlPage);
				
			}
			if(result!=null)
				for(SinaMicroblogger microblogger:result){
					System.out.println("id="+microblogger.getSinaId()+";url="+microblogger.getMblogUrl());
				}
		}
	}
	
	@Test
	public void testBloggerHomeDowloader(){
		
		httpLoginManager = new SinaHttpLoginManager();
		afterLoginBean = (JsonDataBean) httpLoginManager.loginSinaMicroblog();
		String url = "http://weibo.com/xiena";
		BloggerHomeDownloader bloggerHomeDownloader = new BloggerHomeDownloader();
		BloggerHomePageBean homePageBean = (BloggerHomePageBean) bloggerHomeDownloader.collectBloggerHomeData(url);
		String followUrl = homePageBean.getMicrobloggerUrls().getFollowUrl();
		for(int i=1;i<=28;i++){
			List<SinaMicroblogger> result = null;
			if(bloggerHomeDownloader.getFollowsPageUrl() == null){
				result = bloggerHomeDownloader.collectFollowUserData("http://weibo.com"+followUrl,url);
			}else{
				String followUrlPage = bloggerHomeDownloader.getFollowsPageUrl();
				followUrlPage = followUrlPage.replaceAll("page=(\\d{1,2})", "page="+i);
		
				result = bloggerHomeDownloader.collectFollowUserData(followUrlPage);
			}
			if(result!=null)
				for(SinaMicroblogger microblogger:result){
					System.out.println("id="+microblogger.getSinaId()+";url="+microblogger.getMblogUrl());
				}
		}
	}
	@Test
	public void testCollectFamousUrl(){
		
		famousUrlDowloader = new FamousUrlDowloader();
		String famousMainUrl = "http://data.weibo.com/top/influence/famous";
		List<String> urls = famousUrlDowloader.collectFamousClassfiyUrl(famousMainUrl);
		JoeThreadService joeThreadService = JoeThreadService.getThreadService();
		
		for(String url:urls){
			CollectFamousUrlTask collectFamousUrlTask = 
					new CollectFamousUrlTask("抓取名人的微博主页地址任务："+url, famousMainUrl+url);
			joeThreadService.submitTask(collectFamousUrlTask);
		}
	}
}
