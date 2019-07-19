/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.network.sina;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.crawler.bean.HttpDataBean;
import cn.edu.bjtu.crawler.servicer.sina.FamousUrlService;
import cn.edu.bjtu.crawler.utils.StringUtils;

/**
 * @author QiaoJian
 *
 */
public class FamousUrlDowloader extends AbstractSinaDataDowloader {
	
	private FamousUrlService famousUrlService;
	
	public FamousUrlDowloader() {
		super();
		famousUrlService = new FamousUrlService();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 收集名人板块的分类url
	 * @param url
	 * @return
	 */
	public List<String> collectFamousClassfiyUrl(String url){
		Map<String,String> headers = famousUrlService.createCommonHeader();
		headers.put("Host", "data.weibo.com");
		//http://verified.weibo.com/?leftnav=1&wvr=5&type=official&set=5
		headers.put("Referer", "http://verified.weibo.com/?leftnav=1&wvr=5&type=official&set=5");
		String cookieStr = StringUtils.cookies2String(HttpDataBean.cookies);
		headers.put("Cookie", cookieStr);
		
		HttpResponse httpResponse = this.doGet(url, headers);
		return famousUrlService.collectFamousClassfiyUrl(httpResponse);		
	}
	/**
	 * 收集名人的weibo首页url
	 * @param url
	 * @return
	 */
	public List<String> collectFamousUrl(String url){
		Map<String,String> headers = famousUrlService.createCommonHeader();
		headers.put("Host", "data.weibo.com");
		//http://verified.weibo.com/?leftnav=1&wvr=5&type=official&set=5
		headers.put("Referer", "http://verified.weibo.com/?leftnav=1&wvr=5&type=official&set=5");
		String cookieStr = StringUtils.cookies2String(HttpDataBean.cookies);
		headers.put("Cookie", cookieStr);
		
		HttpResponse httpResponse = this.doGet(url, headers);
		return famousUrlService.collectFamousUrl(httpResponse);		
	}
	
}
