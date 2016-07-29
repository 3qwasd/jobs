/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis.domain;

/**
 * @author QiaoJian
 *
 */
public class SinaMicrobloggerRelation {
	private long localId;
	private long bloggerLocalId;
	private long fansLocalId;
	private String bloggerSinaId;
	private String fansSinaId;
	public long getLocalId() {
		return localId;
	}
	public void setLocalId(long localId) {
		this.localId = localId;
	}
	public long getBloggerLocalId() {
		return bloggerLocalId;
	}
	public void setBloggerLocalId(long bloggerLocalId) {
		this.bloggerLocalId = bloggerLocalId;
	}
	public long getFansLocalId() {
		return fansLocalId;
	}
	public void setFansLocalId(long fansLocalId) {
		this.fansLocalId = fansLocalId;
	}
	public String getBloggerSinaId() {
		return bloggerSinaId;
	}
	public void setBloggerSinaId(String bloggerSinaId) {
		this.bloggerSinaId = bloggerSinaId;
	}
	public String getFansSinaId() {
		return fansSinaId;
	}
	public void setFansSinaId(String fansSinaId) {
		this.fansSinaId = fansSinaId;
	}
}
