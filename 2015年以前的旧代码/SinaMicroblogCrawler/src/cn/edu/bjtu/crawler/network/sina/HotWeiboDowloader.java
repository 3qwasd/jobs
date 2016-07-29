/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.network.sina;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.edu.bjtu.crawler.servicer.sina.HotWeiboService;
import cn.edu.bjtu.crawler.sql.myatis.SinaSqlDao;

/**
 * @author QiaoJian
 *
 */
public class HotWeiboDowloader extends AbstractSinaDataDowloader{
	
	HotWeiboService service;
	
	SinaSqlDao dao;
	
	List<String> weiboIds;
	/**
	 * 
	 */
	public HotWeiboDowloader() {
		super();
		service = new HotWeiboService();
		dao = new SinaSqlDao();
	}
	public List<String> readWeiboIds(String filePath){
		weiboIds = service.readRandomFile(filePath);
		return weiboIds;
	}
	public void searchWeibo(){
		String url = "http://s.weibo.com/wb/";
		String searchStr = "【请用微博一起祈祷！】我们知道，航班失联";
		try {
			searchStr = URLEncoder.encode(searchStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchStr = searchStr.replace("%", "%25");
		url = url+searchStr+"&xsort=hot&vip=1&page=1";
		Map<String,String> headers = service.createCommonHeader();
		headers.put("Host", "s.weibo.com");
		HttpResponse response = this.doGet(url, headers);
		String html = service.getStringFromResponse(response);
		service.extractSearchWeibo(html);
	
	}
	public void dowloadComment(String weiboIdInfo){
		
		String[] info = weiboIdInfo.split(";",3);
		String mid = info[0];
		String midExt = info[1];
		String uid = info[2];
		String hotCommentUrl = "http://weibo.com/aj/comment/big?_wv=5&id={mid}&filter=hot&__rnd=1395281572590";
		//String weiboUrl = "http://weibo.com/"+uid+"/"+midExt;
		//System.out.println(weiboUrl);
		String nextPageUrl = null;
		nextPageUrl = hotCommentUrl.replace("{mid}", mid);
		while(nextPageUrl!=null){
			Map<String,String> headers = service.createCommonHeader();
			headers.put("Host", "weibo.com");
			
			HttpResponse response = this.doGet(nextPageUrl, headers);
			
			String html = service.getStringFromResponse(response);
			String res = service.extractHtmlFromJson(html);
			nextPageUrl = service.extractComment(res);
		}
	}
	public void dowload(){
		String hotUrl = "http://hot.weibo.com/month?v=9999";
		Map<String,String> headers = service.createCommonHeader();
		headers.put("Host", "hot.weibo.com");
		HttpResponse response = this.doGet(hotUrl, headers);
		String html = service.getStringFromResponse(response);
		String lastId = service.extractHotWeibo(html);
		String ajaxUrl = "http://hot.weibo.com/ajax/feed?since_id={lastId}&type=m&v=9999&date=&page={page}&topic_id=&cur_order=0&_t=0&__rnd={date}";
		for(int i=2;i<=10;i++){
			String pageUrl = ajaxUrl.replace("{lastId}", lastId).replace("{page}", i+"").replace("{date}", new Date().getTime()+"");
			headers.put("Referer", "http://hot.weibo.com/month?v=9999");
			response = this.doGet(pageUrl, headers);
			String res = service.getStringFromResponse(response);
			html = service.extractHtmlFromJson(res);
			lastId = service.extractHotWeibo(html);
		}
		//System.out.println(html);
	}
}
