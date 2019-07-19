/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis.mapper;

import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicrobloggerRelation;

/**
 * @author QiaoJian
 *
 */
public interface SinaMicroblogerRelationMapper {

	/**
	 * 保存用户粉丝关系
	 * @param relation
	 */
	public void saveBloggerFansRelation(SinaMicrobloggerRelation relation);

}
