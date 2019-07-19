/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.parser;

/**
 * @author QiaoJian
 * 在解析html文档时用于过滤的接口
 */
public interface HtmlParserFilter {
	
	public boolean keep(String source);
}
