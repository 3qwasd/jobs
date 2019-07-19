/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.servicer.sina;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;

import cn.edu.bjtu.crawler.network.HttpManager;

/**
 * @author QiaoJian
 *
 */
public class FamousUrlService extends BaseSinaService{
	
	/**
	 * 收集名人url
	 * @param httpResponse
	 */
	public List<String> collectFamousUrl(HttpResponse httpResponse) {
		// TODO Auto-generated method stub
		List<String> urls = new ArrayList<String>();
		String html = HttpManager.getHttpManager().getStringFromResponse(httpResponse);
		this.sinaDataParser.extractLinkFromHtml(html, "a[action-type=usercard]", urls);
		return urls;
	}

	/**
	 * 收集名人板块的分类url
	 * @param httpResponse
	 * @return
	 */
	public List<String> collectFamousClassfiyUrl(HttpResponse httpResponse) {
		List<String> famousClassfiyParams = new ArrayList<String>(); 
		String html = HttpManager.getHttpManager().getStringFromResponse(httpResponse);
		this.sinaDataParser.extractLinkFromHtml(html, "a[href~=^(\\?class=([0-9]{1,6}))(&type=(day)$)]", famousClassfiyParams);
		return famousClassfiyParams;
	}

}
