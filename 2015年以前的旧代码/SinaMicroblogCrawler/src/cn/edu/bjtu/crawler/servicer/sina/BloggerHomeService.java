/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.servicer.sina;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.crawler.bean.HttpDataBean;
import cn.edu.bjtu.crawler.bean.sina.BloggerHomePageBean;
import cn.edu.bjtu.crawler.network.HttpManager;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogTopic;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogger;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicrobloggerUrls;
import cn.edu.bjtu.crawler.utils.StringUtils;

/**
 * @author QiaoJian
 *
 */
public class BloggerHomeService extends BaseSinaService {

	String followPageUrl;
	String fansPageUrl;
	String weiboPageUrl;
	String weiboDomid;
	String pageUrl;
	/**
	 * 收集主页上的信息
	 * @param httpResponse
	 * @return
	 */
	public HttpDataBean collectHomePageInfos(HttpResponse httpResponse){

		InputStream inputStream = HttpManager.getHttpManager().getInputStreamFromResponse(httpResponse);


		Map<String,String> htmlMap = this.sinaDataParser.extractHtmlFromScript(inputStream, "ns");
		SinaMicroblogger sinaMicroblogger = new SinaMicroblogger();
		collectBloggerInfo("pl.header.head.index", htmlMap,sinaMicroblogger);
		collectConfigInfo("$CONFIG",htmlMap,sinaMicroblogger);
		
		SinaMicrobloggerUrls microbloggerUrls = new SinaMicrobloggerUrls();
		collectFollowMainPageUrls("pl.header.head.index", htmlMap, microbloggerUrls);
		collectOtherMainPageUrls("pl.nav.index", htmlMap, microbloggerUrls);
		microbloggerUrls.setSinaId(sinaMicroblogger.getSinaId());
		BloggerHomePageBean bean = new BloggerHomePageBean();
		bean.setMicroblogger(sinaMicroblogger);
		bean.setMicrobloggerUrls(microbloggerUrls);
		
		return bean;
	}
	/**
	 * 获取主页上的配置信息
	 * @param string
	 * @param htmlMap
	 * @param sinaMicroblogger
	 */
	private void collectConfigInfo(String key, Map<String, String> htmlMap,
			SinaMicroblogger sinaMicroblogger) {
		// TODO Auto-generated method stub
		if(!htmlMap.containsKey(key))
			return ;
		String htmlSegment = htmlMap.get(key);
		String[] configInfos = htmlSegment.split(";");
		for(String configInfo:configInfos){
			if(configInfo.trim().startsWith("$CONFIG['domain']")){
				String domain = configInfo.split("=")[1];
				domain = domain.replaceAll("'", "");
				sinaMicroblogger.setDomain(domain);
			}
			if(configInfo.trim().startsWith("$CONFIG['oid']")){
				String sinaId = configInfo.split("=")[1];
				sinaId = sinaId.replaceAll("'", "");
				sinaMicroblogger.setSinaId(sinaId);
			}
		}
	}
	/**
	 * 收集其他版块的页面地址
	 * @param key
	 * @param htmlMap
	 * @param microbloggerUrls
	 */
	public void collectOtherMainPageUrls(String key,Map<String,String> htmlMap,SinaMicrobloggerUrls microbloggerUrls){
		if(!htmlMap.containsKey(key))
			return ;
		String htmlSegment = htmlMap.get(key);
		List<String> infoCollect = new ArrayList<String>();
		sinaDataParser.extractLinkFromHtml(htmlSegment, "a", infoCollect);
		if(infoCollect.size()<1)
			return;
		for(String url:infoCollect){
			microbloggerUrls.setUrl(url);
		}
	}
	/**
	 * 收集粉丝，关注，微博的页面地址
	 * @param key
	 * @param htmlMap
	 * @param microbloggerUrls
	 */
	public void collectFollowMainPageUrls(String key,Map<String,String> htmlMap,SinaMicrobloggerUrls microbloggerUrls){
		if(!htmlMap.containsKey(key))
			return ;
		String htmlSegment = htmlMap.get(key);
		List<String> infoCollect = new ArrayList<String>();
		sinaDataParser.extractLinkFromHtml(htmlSegment, "ul.user_atten a.S_func1", infoCollect);
		if(infoCollect.size()>=3){
			microbloggerUrls.setFollowUrl(infoCollect.get(0));
			microbloggerUrls.setFansUrl(infoCollect.get(1));
			microbloggerUrls.setWeiboUrl(infoCollect.get(2));
		}else{
			sinaDataParser.extractLinkFromHtml(htmlSegment, "table.W_tc a.S_func1", infoCollect);
			if(infoCollect.size()>=3){
				microbloggerUrls.setFollowUrl(infoCollect.get(0));
				microbloggerUrls.setFansUrl(infoCollect.get(1));
				microbloggerUrls.setWeiboUrl(infoCollect.get(2));
			}
		}
	}
	/**
	 * 从html片段中收集用户信息
	 * @param key
	 * @param htmlMap
	 * @return
	 */
	public void collectBloggerInfo(String key,Map<String,String> htmlMap,SinaMicroblogger blogger){
		if(!htmlMap.containsKey(key))
			return ;
		String htmlSegment = htmlMap.get(key);
		List<String> infoCollect = new ArrayList<String>();
		/*获取用户昵称*/
		sinaDataParser.extractTagTextFromHtml(htmlSegment, "span.name",infoCollect);
		if(infoCollect.size()>0)
			blogger.setMblogNickname(infoCollect.get(0));
		infoCollect.clear();
		/*获取用户简介*/
		sinaDataParser.extractTagTextFromHtml(htmlSegment, "div.pf_intro > span.S_txt2", infoCollect);
		if(infoCollect.size()>0)
			blogger.setRemark(infoCollect.get(0));
		infoCollect.clear();
		/*获取用户的新浪认证*/
		sinaDataParser.extractTagTextFromHtml(htmlSegment, "div.pf_verified_info", infoCollect);
		if(infoCollect.size()>0)
			blogger.setSinaProve(infoCollect.get(0).replace("举报身份", "").replace("申请认证", ""));
		infoCollect.clear();
		/*获取性别*/
		sinaDataParser.extractTagAttrFromHtml(htmlSegment,"title","em.W_ico12", infoCollect);
		if(infoCollect.size()>0)
			blogger.setGender(infoCollect.get(0));
		infoCollect.clear();
		/*获取用户所在地*/
		sinaDataParser.extractTagTextFromHtml(htmlSegment, "em.S_txt2 > a", infoCollect);
		if(infoCollect.size()>0)
			blogger.setAddress(infoCollect.get(0));
		infoCollect.clear();
		/*获取用户Tag*/
		sinaDataParser.extractTagTextFromHtml(htmlSegment, "div.layer_menulist_tags span.S_func1", infoCollect);
		String tags = "";
		if(infoCollect.size()>0){
			for(String info:infoCollect){
				tags += info+",";
			}
			blogger.setmBlogTags(tags);
		}
		infoCollect.clear();
		/*获取用户关注的人数*/
		sinaDataParser.extractTagTextFromHtml(htmlSegment, "ul.user_atten strong[node-type=follow]", infoCollect);
		if(infoCollect.size()>0){
			String countStr = infoCollect.get(0);
			if(countStr.matches("[0-9]*"));
				blogger.setAttentions(Long.valueOf(infoCollect.get(0)));
		}
		infoCollect.clear();
		/*获取用户粉丝数*/
		sinaDataParser.extractTagTextFromHtml(htmlSegment, "ul.user_atten strong[node-type=fans]", infoCollect);
		if(infoCollect.size()>0){
			String countStr = infoCollect.get(0);
			if(countStr.matches("[0-9]*"));
				blogger.setFansNum(Long.valueOf(infoCollect.get(0)));
		}
		infoCollect.clear();
		/*获取用户所发的微博数*/
		sinaDataParser.extractTagTextFromHtml(htmlSegment, "ul.user_atten strong[node-type=weibo]", infoCollect);
		if(infoCollect.size()>0){
			String countStr = infoCollect.get(0);
			if(countStr.matches("[0-9]*"));
				blogger.setMblogNum(Long.valueOf(infoCollect.get(0)));
		}
		infoCollect.clear();
		/*获取用户在新浪微博的ID*/
		sinaDataParser.extractTagAttrFromHtml(htmlSegment,"levelcard","span.W_level_ico span.W_level_num", infoCollect);
		if(infoCollect.size()>0){
			String sinaId = infoCollect.get(0).split("=")[1];
			blogger.setSinaId(sinaId);
		}
		infoCollect.clear();
		/*获取用户的星座*/
		/*获取用户的毕业院校*/
		/*获取用户的感情状况*/
		/*获取用户的生日*/
		/*获取用户的*/
	}
	/**
	 * 收集用户粉丝的用户基本信息
	 * @param httpResponse
	 */
	public List<SinaMicroblogger> collectFansBaseInfo(HttpResponse httpResponse) {
		// TODO Auto-generated method stub
		InputStream inputStream = HttpManager.getHttpManager().getInputStreamFromResponse(httpResponse);
		Map<String,String> htmlMap = this.sinaDataParser.extractHtmlFromScript(inputStream, "ns");
		List<Map<String,String>> collector = new ArrayList<>();
		List<SinaMicroblogger> datas = new ArrayList<SinaMicroblogger>();

		String htmlSegment = htmlMap.get("pl.content.followTab.index");
		sinaDataParser.extractTagAttrFromHtml(htmlSegment, "div.name a.W_f14", collector, "usercard","href");
		if(collector.size()<1){
			return null;
		}
		for(Map<String,String> map:collector){
			SinaMicroblogger microblogger = new SinaMicroblogger();
			microblogger.setSinaId(map.get("usercard").split("=")[1]);
			microblogger.setMblogUrl("http://weibo.com"+map.get("href"));
			datas.add(microblogger);
		}
		/*获取粉丝用户的分页的超链接*/
		if(!(fansPageUrl != null&&fansPageUrl.length()>0)){
			List<String> pageUrlCollector = new ArrayList<String>();
			sinaDataParser.extractTagAttrFromHtml(htmlSegment, "href", "div[node-type=pageList] a.page", pageUrlCollector);
			if(pageUrlCollector.size()>0)
				this.fansPageUrl ="http://weibo.com" + pageUrlCollector.get(0);
		}
		return datas;
	}
	/**
	 * 收集用户关注的用户基本信息
	 * @param httpResponse
	 */
	public List<SinaMicroblogger> collectFollowsBaseInfo(HttpResponse httpResponse) {
		// TODO Auto-generated method stub
		InputStream inputStream = HttpManager.getHttpManager().getInputStreamFromResponse(httpResponse);
		Map<String,String> htmlMap = this.sinaDataParser.extractHtmlFromScript(inputStream, "ns");
		List<Map<String,String>> collector = new ArrayList<>();
		List<SinaMicroblogger> datas = new ArrayList<SinaMicroblogger>();

		String htmlSegment = htmlMap.get("pl.content.followTab.index");
		sinaDataParser.extractTagAttrFromHtml(htmlSegment, "div.name a.W_f14", collector, "usercard","href");
		if(collector.size()<1){
			return null;
		}
		for(Map<String,String> map:collector){
			SinaMicroblogger microblogger = new SinaMicroblogger();
			microblogger.setSinaId(map.get("usercard").split("=")[1]);
			microblogger.setMblogUrl("http://weibo.com"+map.get("href"));
			datas.add(microblogger);
		}
		/*获取关注用户的分页的超链接*/
		if(!(followPageUrl != null&&followPageUrl.length()>0)){
			List<String> pageUrlCollector = new ArrayList<String>();
			sinaDataParser.extractTagAttrFromHtml(htmlSegment, "href", "div[node-type=pageList] a.page", pageUrlCollector);
			if(pageUrlCollector.size()>0)
				this.followPageUrl ="http://weibo.com" + pageUrlCollector.get(0);
		}
		return datas;
	}

	/**
	 * 提取用户的微博数据
	 * @param httpResponse
	 * @return
	 */
	public List<SinaMicroblogTopic> collectWeiboData(HttpResponse httpResponse) {
		// TODO Auto-generated method stub
		InputStream inputStream = HttpManager.getHttpManager().getInputStreamFromResponse(httpResponse);
		String responseStr = StringUtils.stream2String(inputStream);
		Map<String,String> htmlMap = this.sinaDataParser.extractHtmlFromScript(responseStr, "ns");
		String html;
		if(htmlMap != null&&htmlMap.size()>0){
			html = htmlMap.get("pl.content.homeFeed.index");
			if(htmlMap.containsKey("weibo_feed_domid")){
				weiboDomid = htmlMap.get("weibo_feed_domid");
			}
		}else{
			html = this.sinaDataParser.extractHtmlFromAjaxJson(responseStr, "data");
		}
		List<String> pageUrl = new ArrayList<>();
		sinaDataParser.extractLinkFromHtml(html, "a[bpfilter=page].W_btn_c", pageUrl);
		if(pageUrl.size()==1){
			this.pageUrl = pageUrl.get(0);
		}else if(pageUrl.size() == 2){
			this.pageUrl = pageUrl.get(1);
		}
		List<SinaMicroblogTopic> blogTopics = new ArrayList<SinaMicroblogTopic>();
		List<String> collector = new ArrayList<>();
		sinaDataParser.extractTagDataFromHtml(html, "div.WB_feed_type", collector);
		if(collector.size()>0){
			for(String weiboInfo:collector){
				SinaMicroblogTopic microblogTopic = new SinaMicroblogTopic();
				blogTopics.add(microblogTopic);
				List<String> webInfos = new ArrayList<String>();
				/*获取帖子是否为转发的帖子*/
				sinaDataParser.extractTagAttrFromHtml(weiboInfo, "isforward", "div.WB_feed_type", webInfos);
				if(webInfos.size()>0){
					if(webInfos.get(0).equals("1")){
						/*帖子类型为转发*/
						microblogTopic.setType("forward");
						webInfos.clear();
						/*获取转发的帖子的新浪ID*/
						sinaDataParser.extractTagAttrFromHtml(weiboInfo, "omid", "div[action-type=feed_list_item].WB_feed_type", webInfos);
						if(webInfos.size()>0){
							microblogTopic.setTargetId(webInfos.get(0));
							webInfos.clear();
						}
						/*获取转发的帖子的内容*/
						sinaDataParser.extractTagTextFromHtml(weiboInfo, "div[node-type=feed_list_reason].WB_text", webInfos);
						if(webInfos.size()>0){
							microblogTopic.setTargetTopic(webInfos.get(0));
							webInfos.clear();
						}
						/*获取转发的帖子的作者的首页地址*/
						sinaDataParser.extractLinkFromHtml(weiboInfo, "a[node-type=feed_list_originNick]", webInfos);
						if(webInfos.size()>0){
							microblogTopic.setTargetPosterUrl("http://weibo.com"+webInfos.get(0));
							webInfos.clear();
						}
					}
					webInfos.clear();
				}else{
					/*帖子类型为原创*/
					microblogTopic.setType("original");
				}

				/*抽取贴子在新浪微博的ID*/
				sinaDataParser.extractTagAttrFromHtml(weiboInfo, "mid", "div.WB_feed_type", webInfos);
				if(webInfos.size()>0){
					microblogTopic.setSinaId(webInfos.get(0));
					webInfos.clear();
				}
				/*抽取帖子的内容*/
				sinaDataParser.extractTagTextFromHtml(weiboInfo, "div.WB_detail div[node-type=feed_list_content]", webInfos);
				if(webInfos.size()>0){
					microblogTopic.setTopic(webInfos.get(0));
					webInfos.clear();
				}
				/*获取赞的人数*/
				sinaDataParser.extractTagTextFromHtml(weiboInfo, "a[action-type=fl_like]", webInfos);
				if(webInfos.size()>0){
					long count = 0;
					String countStr = "0";
					if(microblogTopic.getType().equals("forward")){
						if(webInfos.size()>1)
							countStr = webInfos.get(1);
					}else{
						if(webInfos.size()>0)
							countStr = webInfos.get(0);
					}
					try{
						count = Long.valueOf(countStr.substring(countStr.indexOf("(")+1, countStr.indexOf(")")));
					}catch(Exception e){
						count = 0;
					}
					microblogTopic.setPriceCount(count);
					webInfos.clear();
				}
				/*获取转发的人数*/
				sinaDataParser.extractTagTextFromHtml(weiboInfo, "a[action-type=fl_forward]", webInfos);
				if(webInfos.size()>0){
					long count = 0;
					String countStr = "0";
					if(webInfos.size()>0)
						 countStr = webInfos.get(0);
					try{
						count = Long.valueOf(countStr.substring(countStr.indexOf("(")+1, countStr.indexOf(")")));
					}catch(Exception e){
						count = 0;
					}
					microblogTopic.setQuoteCount(count);
					webInfos.clear();
				}
				/*获取评论的人数*/
				sinaDataParser.extractTagTextFromHtml(weiboInfo, "a[action-type=fl_comment]", webInfos);
				if(webInfos.size()>0){
					long count = 0;
					String countStr = "0";
					if(webInfos.size()>0)
						 countStr = webInfos.get(0);
					try{
						count = Long.valueOf(countStr.substring(countStr.indexOf("(")+1, countStr.indexOf(")")));
					}catch(Exception e){
						count = 0;
					}
					microblogTopic.setCommentCount(count);
					webInfos.clear();
				}
				/*获取发表时间*/
				sinaDataParser.extractTagAttrFromHtml(weiboInfo,"date","a[node-type=feed_list_item_date]", webInfos);
				if(webInfos.size()>0){
					long dateL = Long.valueOf(webInfos.get(0));
					Date date = new Date(dateL);
					microblogTopic.setPostDate(date);
					webInfos.clear();
				}
				/*获取该条微博发送的应用*/
				sinaDataParser.extractTagTextFromHtml(weiboInfo,"a[action-type=app_source]", webInfos);
				if(webInfos.size()>0){
					microblogTopic.setPostApp(webInfos.get(0));
					webInfos.clear();
				}
			}

		}
		return blogTopics;
	}
	/**
	 * 
	 * @return
	 */
	public String getFollowPageUrl() {
		return followPageUrl;
	}
	/**
	 * 
	 * @return
	 */
	public String getFansPageUrl() {
		return fansPageUrl;
	}
	/**
	 * 
	 * @return
	 */
	public String getWeiboPageUrl() {
		return weiboPageUrl;
	}
	/**
	 * 
	 * @return
	 */
	public String getWeiboDomid() {
		return weiboDomid;
	}
	/**
	 * 
	 * @return
	 */
	public String getPageUrl() {
		return pageUrl;
	}
	
}
