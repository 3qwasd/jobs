/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.parser.sinadata;


import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.JSONObject;
import cn.edu.bjtu.crawler.bean.HttpDataBean;
import cn.edu.bjtu.crawler.bean.sina.JsonDataBean;
import cn.edu.bjtu.crawler.network.HttpManager;
import cn.edu.bjtu.crawler.parser.BaseParser;
import cn.edu.bjtu.crawler.parser.html.HtmlContentParser;
import cn.edu.bjtu.crawler.parser.json.JsonParser;
import cn.edu.bjtu.crawler.utils.StringUtils;

/**
 * @author QiaoJian
 * 新浪数据的解析类
 */
public class SinaDataParser extends BaseParser{


	HtmlContentParser htmlParser = null;
	JsonParser jsonParser = null;



	public SinaDataParser() {
		super();
		htmlParser = new HtmlContentParser();
		jsonParser = new JsonParser();
	}
	
	/**
	 * 获取新浪用户主页上的config信息
	 * @param html
	 * @return
	 */
	public Map<String,String> extractSinaConfigInfo(String html){
		Map<String,String> result = new HashedMap();
		Elements elements = htmlParser.getElementsByTag(html, "script");
		if(elements == null){
			return null;
		}
		if(elements.size()<1){
			return null;
		}
		for(int i=0;i<elements.size();i++){
			Element script = elements.get(i);
			String data = script.data();
			if(data.trim().startsWith("var $CONFIG = {};")){
				data = data.replaceAll("\n", "").
				replaceAll("\\$CONFIG", "").
				replaceAll("\\[", "").
				replaceAll("\\]", "").
				replaceAll("'", "").
				replaceAll("'", "");
				
				String[] dataStrs = data.split(";");
				for(String str:dataStrs){
					String[] strs = str.split("=",2);
					if(strs.length==2){
						result.put(strs[0].trim(), strs[1].trim());
					}
				}
				break;
			}
		}

		return result;
	}
	/**
	 * 从html中抽取标签属性信息
	 * @param html
	 * @param attrName 属性名称
	 * @param selector
	 */
	public void extractTagAttrFromHtml(String html,String attrName,String selector,List<String> collector){
		Elements elements = this.htmlParser.getElementsBySelector(html, selector);
		if(elements!=null&&elements.size()>0){
			for(int i=0;i<elements.size();i++){
				Element element = elements.get(i);
				if(element.hasAttr(attrName))
					collector.add(element.attr(attrName));
			}
		}
	}
	/**
	 * 从html中抽取标签下的信息
	 * @param html
	 * @param selector
	 */
	public void extractTagTextFromHtml(String html,String selector,List<String> collector){
		Elements elements = this.htmlParser.getElementsBySelector(html, selector);
		if(elements!=null&&elements.size()>0){
			for(int i=0;i<elements.size();i++){
				Element element = elements.get(i);
				if(element.hasText())
					collector.add(element.text());
			}
		}
	}
	/**
	 * 从返回的json格式数据中抽取html片段
	 * @param inputStream
	 * @param key
	 * @return
	 */
	public String extractHtmlFromAjaxJson(String html,String key){
		
		JSONObject jsonObject = JSONObject.fromObject(html);
		if(jsonObject.containsKey(key)){
			return jsonObject.get(key)+"";
		}
		
		return null;
	}
	/**
	 * 从返回的json格式数据中抽取html片段
	 * @param inputStream
	 * @param key
	 * @return
	 */
	public String extractHtmlFromAjaxJson(String html,String key,String key2){
		
		JSONObject jsonObject = JSONObject.fromObject(html);
		if(jsonObject.containsKey(key)){
			if(jsonObject.get(key) instanceof JSONObject){
				JSONObject subObj = jsonObject.getJSONObject(key);
				if(subObj.containsKey(key2)){
					return subObj.get(key2)+"";
				}
			}
		}
		
		return null;
	}
	/**
	 * 从weibo页面的脚本语言中抽取html片段，html片段在脚本语言的json数据中，根据json数据的key与value抽取需要
	 * 的html片段所在的json数据
	 * @param inputStream 服务器返回的数据流
	 * @param key 
	 */
	public Map<String,String> extractHtmlFromScript(String html,String key){
		
		//System.out.println(html);
		Map<String,String> result = new HashedMap();
		Elements elements = htmlParser.getElementsByTag(html, "script");
		if(elements == null){
			return null;
		}
		if(elements.size()<1){
			return null;
		}
		for(int i=0;i<elements.size();i++){
			Element script = elements.get(i);
			String data = script.data();
			if(data.trim().startsWith("var $CONFIG = {};")){
				result.put("$CONFIG", data.trim().replaceAll("\n", ""));
			}
			if(!data.startsWith("FM.view"))
				continue;
			int start = data.indexOf("FM.view({")+8;
			int end = data.lastIndexOf("})")+1;
			data = data.substring(start, end);
			JSONObject object = this.jsonParser.parserJsonStr(data);
			if(!object.containsKey(key))
				continue;
			String domain = object.get(key)+"";
			if(object.containsKey("domid")){
				String domid = object.get("domid")+"";
				if(domid.startsWith("Pl_Official_LeftProfileFeed__")){
					result.put("weibo_feed_domid", domid);
				}
			}
			if(domain!=null&&domain.length()>0&&object.containsKey("html")){
				String htmlSegment = object.get("html")+"";
				result.put(domain, htmlSegment);
			}
		}

		return result;
	}
	/**
	 * 从weibo页面的脚本语言中抽取html片段，html片段在脚本语言的json数据中，根据json数据的key与value抽取需要
	 * 的html片段所在的json数据
	 * @param inputStream 服务器返回的数据流
	 * @param key 
	 */
	public Map<String,String> extractHtmlFromScript(InputStream inputStream,String key){
		String html = StringUtils.stream2String(inputStream);
		
		//System.out.println(html);
		Map<String,String> result = new HashedMap();
		Elements elements = htmlParser.getElementsByTag(html, "script");
		if(elements == null){
			return null;
		}
		for(int i=0;i<elements.size();i++){
			Element script = elements.get(i);
			String data = script.data();
			if(data.trim().startsWith("var $CONFIG = {};")){
				result.put("$CONFIG", data.trim().replaceAll("\n", ""));
			}
			if(!data.startsWith("FM.view"))
				continue;
			int start = data.indexOf("FM.view({")+8;
			int end = data.lastIndexOf("})")+1;
			data = data.substring(start, end);
			JSONObject object = this.jsonParser.parserJsonStr(data);
			if(!object.containsKey(key))
				continue;
			String domain = object.get(key)+"";
			if(object.containsKey("domid")){
				String domid = object.get("domid")+"";
				if(domid.startsWith("Pl_Official_LeftProfileFeed__")){
					result.put("weibo_feed_domid", domid);
				}
			}
			if(domain!=null&&domain.length()>0&&object.containsKey("html")){
				String htmlSegment = object.get("html")+"";
				result.put(domain, htmlSegment);
			}
		}

		return result;
	}
	/**
	 * 解析新浪微博的预登陆参数
	 * @param preLoginData
	 */
	public void parsePreLoginData(JsonDataBean preLoginData){

		parseJsonBackData(preLoginData);
	}
	/**
	 * 获取登陆返回内容中的重定向url参数
	 * @param httpDataBean
	 */
	public void parseLoginRedirectUrl(HttpDataBean httpDataBean){
		Element element = htmlParser.getFirstElementBySelector(httpDataBean.getResult(), "script");
		String context = element.data();
		int start = context.indexOf("(\"")+2;
		int end = context.indexOf("\")");

		httpDataBean.setUrl(context.substring(start, end));
	}
	/**
	 * 解析登陆后的参数
	 * @param afterLoginData
	 */
	public void parseAfterLoginData(JsonDataBean afterLoginData){

		parseJsonBackData(afterLoginData);
	}
	/**
	 * 解析返回的json参数
	 * @param jsonDataBean
	 */
	public void parseJsonBackData(JsonDataBean jsonDataBean){
		if(jsonDataBean.getResult()!=null&&jsonDataBean.getResult().length()>0){
			String result = jsonDataBean.getResult();
			int startIndex = result.indexOf("({")+1;
			int endIndex = result.indexOf("})")+1;
			String jsonStr = result.substring(startIndex,endIndex);

			JSONObject jsonObject = jsonParser.parserJsonStr(jsonStr);
			jsonParser.jsonObject2Bean(jsonDataBean, jsonObject);
		}
	}

	/**
	 * 抽取html中的超链接
	 * @param html
	 * @param selector
	 * @param urls
	 */
	public void extractLinkFromHtml(String html ,String selector,List<String> urls){
		Elements elements = htmlParser.getElementsBySelector(html, selector);
		if(elements !=null&&elements.size() > 0)
			for(Element element:elements){
				String url = element.attr("href");
				if(urls.contains(url))
					continue;
				urls.add(element.attr("href"));
			}
	}

	/**
	 * 抽取html中标签的属性信息
	 * @param html
	 * @param selector
	 * @param urls
	 */
	public void extractTagAttrFromHtml(String html ,String selector,List<Map<String,String>> infoCollector,String...attrNames){
		Elements elements = htmlParser.getElementsBySelector(html, selector);
		if(elements !=null&&elements.size() > 0)
			for(Element element:elements){
				Map<String,String> attrInfo = new HashMap<String,String>();
				for(String attrName:attrNames){
					attrInfo.put(attrName,element.attr(attrName));
				}
				infoCollector.add(attrInfo);
			}
	}
	/**
	 * 从html片段中抽取标签数据
	 * @param html
	 * @param selector
	 * @param infoCollector
	 */
	public void extractTagDataFromHtml(String html,String selector,List<String> infoCollector){
		Elements elements = htmlParser.getElementsBySelector(html, selector);
		if(elements !=null&&elements.size() > 0)
			for(Element element:elements){
				infoCollector.add(element.outerHtml());
			}
	}
	
	/**
	 * 从weibo页面的脚本语言中抽取html片段，html片段在脚本语言的json数据中，根据json数据的key与value抽取需要
	 * 的html片段所在的json数据
	 * @param inputStream 服务器返回的数据流
	 * @param key 
	 */
	public Map<String,String> extractHtmlFromScript(String html,String keyWord,String key){
		
		//System.out.println(html);
		Map<String,String> result = new HashedMap();
		Elements elements = htmlParser.getElementsByTag(html, "script");
		if(elements == null){
			return null;
		}
		if(elements.size()<1){
			return null;
		}
		for(int i=0;i<elements.size();i++){
			Element script = elements.get(i);
			String data = script.data();
			if(data.trim().startsWith("var $CONFIG = {};")){
				result.put("$CONFIG", data.trim().replaceAll("\n", ""));
			}
			if(!data.startsWith(keyWord))
				continue;
			int start = data.indexOf(keyWord+"({")+keyWord.length()+1;
			int end = data.lastIndexOf("})")+1;
			data = data.substring(start, end);
			JSONObject object = this.jsonParser.parserJsonStr(data);
			if(!object.containsKey(key))
				continue;
			String domain = object.get(key)+"";
			if(object.containsKey("domid")){
				String domid = object.get("domid")+"";
				if(domid.startsWith("Pl_Official_LeftProfileFeed__")){
					result.put("weibo_feed_domid", domid);
				}
			}
			if(domain!=null&&domain.length()>0&&object.containsKey("html")){
				String htmlSegment = object.get("html")+"";
				result.put(domain, htmlSegment);
			}
		}

		return result;
	}
}
