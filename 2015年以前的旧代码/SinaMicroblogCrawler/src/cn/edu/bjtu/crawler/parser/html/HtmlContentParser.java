/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.parser.html;

import java.io.IOException;
import java.io.InputStream;





import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.bjtu.crawler.parser.BaseParser;
import cn.edu.bjtu.crawler.parser.HtmlParserFilter;

/**
 * @author QiaoJian
 *
 */
public class HtmlContentParser extends BaseParser {
	
	/**
	 * 解析html内容
	 * @param htmlContent
	 * @return
	 */
	public Document parserHtml(String htmlContent){
		Document document = Jsoup.parse(htmlContent);
		
		
		return document;
	}
	/**
	 * 解析html内容
	 * @param htmlContent
	 * @return
	 */
	public Document parserHtml(InputStream htmlContent){
		Document document = null;
		try {
			document = Jsoup.parse(htmlContent,"UTF-8","");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return document;
	}
	/**
	 * 解析html内容
	 * @param htmlContent
	 * @param charSetName
	 * @return
	 */
	public Document parserHtml(InputStream htmlContent,String charSetName){
		Document document = null;
		try {
			document = Jsoup.parse(htmlContent,charSetName,"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return document;
	}
	/**
	 * 解析html内容
	 * @param htmlContent
	 * @param charSetName
	 * @param baseUri
	 * @return
	 */
	public Document parserHtml(InputStream htmlContent,String charSetName,String baseUri){
		Document document = null;
		try {
			document = Jsoup.parse(htmlContent,charSetName,baseUri);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return document;
	}
	
	/**
	 * 获取某个名称的tag元素
	 * @param document
	 * @param tagName
	 * @param htmlParserFilter
	 */
	public Elements getElementsByTag(Document document,String tagName){
		Elements elements = document.getElementsByTag(tagName);
	
		return elements;
	}
	/**
	 * 获取某个名称的tag元素
	 * @param document
	 * @param tagName
	 * @param htmlParserFilter
	 */
	public Elements getElementsByTag(String html,String tagName){
		Document document = this.parserHtml(html);
		Elements elements = document.getElementsByTag(tagName);
	
		return elements;
	}
	/**
	 * 获取html内容中脚本语言
	 * @param htmlContent
	 * @return
	 */
	public Element getFirstScriptStr(String htmlContent){
		Document document = this.parserHtml(htmlContent);
		return document.select("script[language=javascript]").first();
	}
	/**
	 * 获取html内容中特定元素的第一个元素
	 * @param htmlContent
	 * @return
	 */
	public Element getFirstElementBySelector(String htmlContent,String selector){
		Document document = this.parserHtml(htmlContent);
		return document.select(selector).first();
	}
	/**
	 * 获取html中的特定元素集合
	 * @param htmlContent
	 * @param selector
	 * @return
	 */
	public Elements getElementsBySelector(String htmlContent,String selector){
		Document document = this.parserHtml(htmlContent);
		return document.select(selector);
	}
}
