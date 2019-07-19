/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis.mapper;

import java.util.List;

import cn.edu.bjtu.crawler.sql.myatis.domain.CrawlerConfig;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogger;

/**
 * @author QiaoJian
 *
 */
public interface SinaMicrobloggerMapper {
	
	/*将微博用户的地址存入数据库*/
	public void insertBloggerUrl(String url,int deep,String competeFlag);
	/*将微博用户的地址存入数据库*/
	public void insertBloggerUrlAndSinaId(String url,int deep,String competeFlag,String sinaId);
	/*更新微博用户的信息*/
	public void updateBloggerInfo(SinaMicroblogger sinaMicroblogger);
	/*获取微博用户的url*/
	public List<String> selectBloggerUrl(String url);
	/*获取未抓取完成的微博用户*/
	public List<SinaMicroblogger> selectBloggerNoComplete(int deep,String flag);
	/*更新微博用户数据下载完成标志*/
	public void updateBloggerToComplete(String url,String competeFlag);
	/*获取未抓取完成的微博用户*/
	public List<SinaMicroblogger> selectBloggerForCollect(int deep,long nextPageStartIndex,int size);
	/*获取配置信息*/
	public List<CrawlerConfig> getConfigInfo();
	/*更新页数*/
	public void updatePage(long page);
	/*更新深度*/
	public void updateDeep(int deep);
}
