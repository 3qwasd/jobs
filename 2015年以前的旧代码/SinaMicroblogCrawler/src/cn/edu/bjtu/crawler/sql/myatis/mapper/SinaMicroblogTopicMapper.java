/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis.mapper;

import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogTopic;

/**
 * @author QiaoJian
 *
 */
public interface SinaMicroblogTopicMapper {

	/**
	 * @param topic
	 */
	public void saveTopic(SinaMicroblogTopic topic);

}
