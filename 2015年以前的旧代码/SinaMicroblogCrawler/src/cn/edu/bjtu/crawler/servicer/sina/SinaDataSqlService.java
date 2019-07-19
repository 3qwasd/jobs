/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.servicer.sina;

import java.util.List;

import cn.edu.bjtu.crawler.sql.myatis.SinaSqlDao;
import cn.edu.bjtu.crawler.sql.myatis.mapper.SinaMicrobloggerMapper;

/**
 * @author QiaoJian
 * 数据库服务类
 */
public class SinaDataSqlService {
	
	SinaSqlDao sinaSqlDao;

	public SinaDataSqlService() {
		this.sinaSqlDao = new SinaSqlDao();
	}
	
	/**
	 * 保存最开始抓取的用户weibo首页url到数据库
	 * @param urls
	 * @param deep 抓取的数据深度
	 */
	public void saveSinaUseUrls(List<String> urls,int deep){
		SinaMicrobloggerMapper mapper = (SinaMicrobloggerMapper) sinaSqlDao.getMyBatisProxyMapper(SinaMicrobloggerMapper.class);
		for(String url:urls){
			mapper.insertBloggerUrl(url, deep, "n");
		}
		this.sinaSqlDao.commit();
	}
}
