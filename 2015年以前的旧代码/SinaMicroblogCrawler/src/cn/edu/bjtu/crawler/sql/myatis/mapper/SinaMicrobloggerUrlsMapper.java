/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis.mapper;

import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicrobloggerUrls;

/**
 * @author QiaoJian
 *
 */
public interface SinaMicrobloggerUrlsMapper {
	
	/**
	 * 保存用户的板块地址到数据库中
	 * @param sinaMicrobloggerUrls
	 */
	public void saveSinaMicroblogerUrls(SinaMicrobloggerUrls sinaMicrobloggerUrls);
}
