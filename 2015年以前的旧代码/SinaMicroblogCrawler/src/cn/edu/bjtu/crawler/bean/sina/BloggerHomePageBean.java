/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.bean.sina;

import cn.edu.bjtu.crawler.bean.HttpDataBean;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicroblogger;
import cn.edu.bjtu.crawler.sql.myatis.domain.SinaMicrobloggerUrls;

/**
 * @author QiaoJian
 *
 */
public class BloggerHomePageBean extends HttpDataBean{
	
	SinaMicroblogger microblogger;
	SinaMicrobloggerUrls microbloggerUrls;
	public SinaMicroblogger getMicroblogger() {
		return microblogger;
	}
	public void setMicroblogger(SinaMicroblogger microblogger) {
		this.microblogger = microblogger;
	}
	public SinaMicrobloggerUrls getMicrobloggerUrls() {
		return microbloggerUrls;
	}
	public void setMicrobloggerUrls(SinaMicrobloggerUrls microbloggerUrls) {
		this.microbloggerUrls = microbloggerUrls;
	}
	
	
}
