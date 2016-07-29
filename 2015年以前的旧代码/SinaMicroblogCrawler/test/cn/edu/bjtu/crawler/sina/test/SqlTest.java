/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sina.test;

import java.util.List;

import org.junit.Test;

import cn.edu.bjtu.crawler.sql.myatis.SinaSqlDao;
import cn.edu.bjtu.crawler.sql.myatis.SqlManager;
import cn.edu.bjtu.crawler.sql.myatis.domain.CrawlerConfig;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogger;
import cn.edu.bjtu.crawler.sql.myatis.mapper.SinaMicrobloggerMapper;
import cn.edu.bjtu.crawler.utils.PropertiesUtils;

/**
 * @author QiaoJian
 *
 */
public class SqlTest {
	@Test
	public void testSqlFunction(){
		SinaSqlDao sinaSqlDao = new SinaSqlDao();
		SinaMicrobloggerMapper mapper = (SinaMicrobloggerMapper) sinaSqlDao.getMyBatisProxyMapper(SinaMicrobloggerMapper.class);
		List<SinaMicroblogger> list = mapper.selectBloggerNoComplete(1, "n");
		for(SinaMicroblogger microblogger:list){
			System.out.println(microblogger.getMblogUrl());
		}
		
	}
	@Test
	public void propertiesWrite(){
		SinaSqlDao sinaSqlDao = new SinaSqlDao();
		SinaMicrobloggerMapper microbloggerMapper = 
				(SinaMicrobloggerMapper) sinaSqlDao.getMyBatisProxyMapper(SinaMicrobloggerMapper.class);
		CrawlerConfig config = microbloggerMapper.getConfigInfo().get(0);
		int deep = config.getDeep();
		long page = config.getPage();
		System.out.println(deep);
		System.out.println(page);
		microbloggerMapper.updateDeep(1);
		microbloggerMapper.updatePage(0);
		sinaSqlDao.commit();
	}
}
