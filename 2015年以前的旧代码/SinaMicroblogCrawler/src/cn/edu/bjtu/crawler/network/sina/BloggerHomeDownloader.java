/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.network.sina;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.crawler.bean.HttpDataBean;
import cn.edu.bjtu.crawler.bean.sina.BloggerHomePageBean;
import cn.edu.bjtu.crawler.bean.sina.WeiboAjaxUrl;
import cn.edu.bjtu.crawler.servicer.sina.BloggerHomeService;
import cn.edu.bjtu.crawler.sql.myatis.SinaSqlDao;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogTopic;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogger;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicrobloggerRelation;
import cn.edu.bjtu.crawler.sql.myatis.mapper.SinaMicroblogTopicMapper;
import cn.edu.bjtu.crawler.sql.myatis.mapper.SinaMicroblogerRelationMapper;
import cn.edu.bjtu.crawler.sql.myatis.mapper.SinaMicrobloggerMapper;
import cn.edu.bjtu.crawler.sql.myatis.mapper.SinaMicrobloggerUrlsMapper;
import cn.edu.bjtu.crawler.utils.StringUtils;

/**
 * @author QiaoJian
 * 微博首页用户数据下载
 */
public class BloggerHomeDownloader extends AbstractSinaDataDowloader{

	BloggerHomeService bloggerHomeService;
	
	SinaSqlDao sinaSqlDao;
	
	public BloggerHomeDownloader() {
		super();
		// TODO Auto-generated constructor stub
		bloggerHomeService = new BloggerHomeService();
		sinaSqlDao = new SinaSqlDao();
	}
	/**
	 * 收集用户的微博数据，包括个人信息，fans信息，关注信息，微博信息等
	 * @param url
	 */
	public void collectUserWeiboData(SinaMicroblogger weiUser){

		System.out.println("info-start collect url:"+weiUser.getMblogUrl());
		/*收集用户信息，以及用户微博，fans等信息的页面url地址*/
		SinaMicrobloggerMapper sinaMicrobloggerMapper = 
				(SinaMicrobloggerMapper) sinaSqlDao.getMyBatisProxyMapper(SinaMicrobloggerMapper.class);
		String url = weiUser.getMblogUrl();
		sinaMicrobloggerMapper.updateBloggerToComplete(url, "s");
		sinaSqlDao.commit();
		try{
			SinaMicroblogger microblogger = null;
			BloggerHomePageBean homePageBean = 
					(BloggerHomePageBean) this.collectBloggerHomeData(url);

			if(homePageBean == null){
				/*数据采集完成更新数据库标志*/
				sinaMicrobloggerMapper.updateBloggerToComplete(url, "e");
				sinaSqlDao.closeSqlSession();
				return ;
			}
			/*保存用户信息到数据库中*/
			if(homePageBean.getMicroblogger() == null){
				/*数据采集完成更新数据库标志*/
				sinaMicrobloggerMapper.updateBloggerToComplete(url, "e");
				sinaSqlDao.closeSqlSession();
				return ;
			}
			microblogger = homePageBean.getMicroblogger();
			String sinaId = homePageBean.getMicroblogger().getSinaId();
			if(!(sinaId!=null&&sinaId.length()>0)){
				if(weiUser.getSinaId()!=null&&weiUser.getSinaId().length()>0){
					homePageBean.getMicroblogger().setSinaId(weiUser.getSinaId());
				}else{
					sinaMicrobloggerMapper.updateBloggerToComplete(url, "e");
					sinaSqlDao.closeSqlSession();
					return ;
				}
			}
			try{
				sinaMicrobloggerMapper.updateBloggerInfo(homePageBean.getMicroblogger());
			}catch(Exception e){
				System.out.println("warn--update User id:"+homePageBean.getMicroblogger().getSinaId()+
						" url:"+microblogger.getMblogUrl()+" nickName:"+microblogger.getMblogNickname()+" fail!");
			}
			sinaSqlDao.commit();
			/*保存用户的板块url地址到数据库中*/
			if(homePageBean.getMicrobloggerUrls() == null){
				/*数据采集完成更新数据库标志*/
				sinaMicrobloggerMapper.updateBloggerToComplete(url, "e");
				sinaSqlDao.closeSqlSession();
				return;
			}


			SinaMicrobloggerUrlsMapper sinaMicrobloggerUrlsMapper =
					(SinaMicrobloggerUrlsMapper) sinaSqlDao.getMyBatisProxyMapper(SinaMicrobloggerUrlsMapper.class);
			homePageBean.getMicrobloggerUrls().setSinaId(homePageBean.getMicroblogger().getSinaId());
			try{
				sinaMicrobloggerUrlsMapper.saveSinaMicroblogerUrls(homePageBean.getMicrobloggerUrls());
			}catch(Exception e){
				System.out.println("warn--save urls id:"+homePageBean.getMicroblogger().getSinaId()+" fail!");
			}
			sinaSqlDao.commit();

			/*收集用户的fans信息*/
			String fansUrl = homePageBean.getMicrobloggerUrls().getFansUrl();
			SinaMicroblogerRelationMapper sinaMicroblogerRelationMapper = 
					(SinaMicroblogerRelationMapper) sinaSqlDao.getMyBatisProxyMapper(SinaMicroblogerRelationMapper.class);
			if(fansUrl!=null&&fansUrl.length()>0){
				fansUrl = StringUtils.formatSinaUrl(fansUrl);
				long fansNum = homePageBean.getMicroblogger().getFansNum();
				if(fansNum>0){
					long page = fansNum%20==0?fansNum/20:fansNum/20+1;
					for(int i=1;i<=page;i++){
						List<SinaMicroblogger> fans = null;
						if(this.getFansPageUrl() == null){
							fans = this.collectFansUserData(fansUrl,url);
						}else{
							fansUrl = this.getFansPageUrl();
							fansUrl = fansUrl.replaceAll("page=(\\d{1,2})", "page="+i);
							fans = this.collectFansUserData(fansUrl);

						}
						if(fans!=null&&fans.size()>0){
							for(SinaMicroblogger fanBlogger:fans){
								try{
									sinaMicrobloggerMapper.insertBloggerUrlAndSinaId(fanBlogger.getMblogUrl(), weiUser.getDeep()+1, "n",fanBlogger.getSinaId());
								}catch(Exception e){
									System.out.println("warn--save fans user id:"+fanBlogger.getSinaId()+" fail! exist");
								}
								sinaSqlDao.commit();
								SinaMicrobloggerRelation relation = new SinaMicrobloggerRelation();
								relation.setBloggerSinaId(homePageBean.getMicroblogger().getSinaId());
								relation.setFansSinaId(fanBlogger.getSinaId());
								try{
									sinaMicroblogerRelationMapper.saveBloggerFansRelation(relation);
								}catch(Exception e){
									System.out.println("warn--save fansRelation user id:"+fanBlogger.getSinaId()+" fail! exist");
								}
								sinaSqlDao.commit();
							}
						}else{
							break;
						}
					}
				}
			}
			/*收集用户的关注信息*/
			String followUrl = homePageBean.getMicrobloggerUrls().getFollowUrl();
			if(followUrl!=null&&followUrl.length()>0){
				followUrl = StringUtils.formatSinaUrl(followUrl);
				long followNum = homePageBean.getMicroblogger().getAttentions();
				if(followNum>0){
					long page = followNum%20==0?followNum/20:followNum/20+1;
					for(int i=1;i<=page;i++){
						List<SinaMicroblogger> follows = null;
						if(this.getFollowsPageUrl() == null){
							follows = this.collectFollowUserData(followUrl,url);
						}else{
							followUrl = this.getFollowsPageUrl();
							followUrl = followUrl.replaceAll("page=(\\d{1,2})", "page="+i);
							follows = this.collectFollowUserData(followUrl);

						}
						if(follows!=null&&follows.size()>0){
							for(SinaMicroblogger followBlogger:follows){
								try{
									sinaMicrobloggerMapper.insertBloggerUrlAndSinaId(followBlogger.getMblogUrl(), weiUser.getDeep()+1, "n",followBlogger.getSinaId());
								}catch(Exception e){
									System.out.println("warn--save followUser user id:"+followBlogger.getSinaId()+" fail! exist");
								}
								sinaSqlDao.commit();
								SinaMicrobloggerRelation relation = new SinaMicrobloggerRelation();
								relation.setBloggerSinaId(followBlogger.getSinaId());
								relation.setFansSinaId(homePageBean.getMicroblogger().getSinaId());
								try{
									sinaMicroblogerRelationMapper.saveBloggerFansRelation(relation);
								}catch(Exception e){
									System.out.println("warn--save followRelation user id:"+followBlogger.getSinaId()+" fail! exist");
								}
								sinaSqlDao.commit();
							}
						}else{
							break;
						}
					}
				}
			}
			/*收集用户的微博客信息*/
			SinaMicroblogTopicMapper sinaMicroblogTopicMapper = 
					(SinaMicroblogTopicMapper) sinaSqlDao.getMyBatisProxyMapper(SinaMicroblogTopicMapper.class);
			String blogUrl = homePageBean.getMicrobloggerUrls().getWeiboUrl();
			if(blogUrl!=null&&blogUrl.length()>0){
				blogUrl = StringUtils.formatSinaUrl(blogUrl);
				long weiboNum = homePageBean.getMicroblogger().getMblogNum();
				if(weiboNum>0){
					long page = weiboNum%45==0?weiboNum/45:weiboNum/45+1;
					List<SinaMicroblogTopic> topics = null;
					WeiboAjaxUrl weiboAjaxUrl = new WeiboAjaxUrl();
					weiboAjaxUrl.setDomain(homePageBean.getMicroblogger().getDomain());
					weiboAjaxUrl.setId(homePageBean.getMicroblogger().getSinaId());
					for(int i = 1;i<=page;i++){
						String pageUrl = this.getPageUrl();
						if(pageUrl!=null&&pageUrl.length()>0){
							pageUrl = StringUtils.formatSinaUrl(pageUrl);
							topics = this.collectWeiboData(pageUrl,blogUrl);
						}else{
							topics = this.collectWeiboData(blogUrl);
						}
						if(topics!=null&&topics.size()>0){
							/*存入数据库*/
							for(SinaMicroblogTopic topic:topics){
								topic.setmBloggerId(homePageBean.getMicroblogger().getSinaId());
								try{
									sinaMicroblogTopicMapper.saveTopic(topic);
								}catch(Exception e){
									System.out.println("warn--save topic  id:"+topic.getSinaId()+" fail! exist");
								}
								sinaSqlDao.commit();
							}
						}else{
							/*数据采集完成更新数据库标志*/
							sinaMicrobloggerMapper.updateBloggerToComplete(url, "y");
							sinaSqlDao.closeSqlSession();
							this.giveBackHttpClient();
							return;
						}
						weiboAjaxUrl.setPage(i+"");
						weiboAjaxUrl.setPre_page(i+"");
						weiboAjaxUrl.setEnd_id(topics.get(0).getSinaId());
						weiboAjaxUrl.setMax_id(topics.get(topics.size()-1).getSinaId());
						weiboAjaxUrl.setPagebar(0+"");
						weiboAjaxUrl.set__rnd(new Date().getTime()+"");
						weiboAjaxUrl.setPl_name(this.getWeiboDomid());
						topics = this.collectWeiboData(weiboAjaxUrl.getUrl(),blogUrl);
						if(topics!=null&&topics.size()>0){
							/*存入数据库*/
							for(SinaMicroblogTopic topic:topics){
								topic.setmBloggerId(homePageBean.getMicroblogger().getSinaId());
								try{
									sinaMicroblogTopicMapper.saveTopic(topic);
								}catch(Exception e){
									System.out.println("warn--save topic  id:"+topic.getSinaId()+" fail! exist");
								}
								sinaSqlDao.commit();
							}
						}else{
							/*数据采集完成更新数据库标志*/
							sinaMicrobloggerMapper.updateBloggerToComplete(url, "y");
							sinaSqlDao.closeSqlSession();
							this.giveBackHttpClient();
							return;
						}
						weiboAjaxUrl.setMax_id(topics.get(topics.size()-1).getSinaId());
						weiboAjaxUrl.setPagebar(1+"");
						topics = this.collectWeiboData(weiboAjaxUrl.getUrl(),blogUrl);
						if(topics!=null&&topics.size()>0){
							/*存入数据库*/
							for(SinaMicroblogTopic topic:topics){
								topic.setmBloggerId(homePageBean.getMicroblogger().getSinaId());
								try{
									sinaMicroblogTopicMapper.saveTopic(topic);
								}catch(Exception e){
									System.out.println("warn--save topic  id:"+topic.getSinaId()+" fail! exist");
								}
								sinaSqlDao.commit();
							}
						}else{
							/*数据采集完成更新数据库标志*/
							sinaMicrobloggerMapper.updateBloggerToComplete(url, "y");
							sinaSqlDao.closeSqlSession();
							this.giveBackHttpClient();
							return;
						}
					}

				}
			}
			/*数据采集完成更新数据库标志*/
			sinaMicrobloggerMapper.updateBloggerToComplete(url, "y");
			sinaSqlDao.closeSqlSession();
			this.giveBackHttpClient();
		}catch(Exception e){
			e.printStackTrace();
			sinaMicrobloggerMapper.updateBloggerToComplete(url, "e");
			sinaSqlDao.closeSqlSession();
			this.giveBackHttpClient();
		}
	}
	/**
	 * 收集微博数据
	 * @param url
	 * @return
	 */
	public List<SinaMicroblogTopic> collectWeiboData(String ...url){
		Map<String,String> headers = bloggerHomeService.createCommonHeader();
		headers.put("Host", "weibo.com");
		this.putCookies(headers, ".weibo.com");
		if(url.length>1)
			headers.put("refer", url[1]);
		HttpResponse httpResponse = this.doGet(url[0], headers);
		return bloggerHomeService.collectWeiboData(httpResponse);
	}
	/**
	 * 收集weibo用户首页的数据
	 * @param url 第一个为url后续的为refer头里的url
	 * @return
	 */
	public HttpDataBean collectBloggerHomeData(String ...url){

		Map<String,String> headers = bloggerHomeService.createCommonHeader();
		headers.put("Host", "weibo.com");
		this.putCookies(headers, ".weibo.com");
		if(url.length>1)
			headers.put("refer", url[1]);
		HttpResponse httpResponse = this.doGet(url[0], headers);
		BloggerHomePageBean bloggerHomePageBean = (BloggerHomePageBean) bloggerHomeService.collectHomePageInfos(httpResponse);
		bloggerHomePageBean.getMicroblogger().setMblogUrl(url[0]);
		return bloggerHomePageBean;
	}
	/**
	 * 收集粉丝的用户基本信息
	 * @param url
	 * @return
	 */
	public List<SinaMicroblogger> collectFansUserData(String...url){

		Map<String,String> headers = bloggerHomeService.createCommonHeader();
		headers.put("Host", "weibo.com");
		if(url.length>1)
			headers.put("refer", url[1]);
		this.putCookies(headers, ".weibo.com");

		HttpResponse httpResponse = this.doGet(url[0], headers);
		List<SinaMicroblogger> result = bloggerHomeService.collectFansBaseInfo(httpResponse);

		return result;
	}
	/**
	 * 收集用户关注的用户基本信息
	 * @param url
	 * @return
	 */
	public List<SinaMicroblogger> collectFollowUserData(String...url){

		Map<String,String> headers = bloggerHomeService.createCommonHeader();
		headers.put("Host", "weibo.com");
		if(url.length>1)
			headers.put("refer", url[1]);
		this.putCookies(headers, ".weibo.com");

		HttpResponse httpResponse = this.doGet(url[0], headers);
		List<SinaMicroblogger> result = bloggerHomeService.collectFollowsBaseInfo(httpResponse);

		return result;
	}
	/**
	 * 获取关注用户的分页地址
	 * @return
	 */
	public String getFollowsPageUrl(){
		return bloggerHomeService.getFollowPageUrl();
	}
	/**
	 * 获取粉丝用户的分页地址
	 * @return
	 */
	public String getFansPageUrl(){
		return bloggerHomeService.getFansPageUrl();
	}
	/**
	 * 获取微博页面的实体ID
	 * @return
	 */
	public String getWeiboDomid(){
		return bloggerHomeService.getWeiboDomid();
	}
	/**
	 * 获取微博的分页url
	 * @return
	 */
	public String getPageUrl(){
		return bloggerHomeService.getPageUrl();
	}
}
