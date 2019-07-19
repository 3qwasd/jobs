/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis.domain;

import java.util.Date;

/**
 * @author QiaoJian
 *
 */
public class SinaMicroblogTopic {
	
	private long localId;
	private String sinaId;
	/*帖子内容*/
	private String topic;
	//帖子类型：forward为转发，original为原创
	private String type;
	//转发的帖子的ID
	private String targetId;
	//赞的人数
	private long priceCount;
	//转发的人数
	private long quoteCount;
	//评论的人数
	private long commentCount;
	//发表人ID
	private String mBloggerId;
	//帖子主题需要后续处理后获得
	private String theme;
	//关键字，需要后续处理后获得
	private String keyword;
	//发表日期
	private Date postDate;
	//发表时使用的应用
	private String postApp;
	//帖子分类，需要后续处理后获得
	private String classfiy;
	//假如是转发的帖子，转发的内容
	private String targetTopic;
	//转发的帖子的作者首页地址
	private String targetPosterUrl;
	public long getLocalId() {
		return localId;
	}
	public void setLocalId(long localId) {
		this.localId = localId;
	}
	public String getSinaId() {
		return sinaId;
	}
	public void setSinaId(String sinaId) {
		this.sinaId = sinaId;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public long getPriceCount() {
		return priceCount;
	}
	public void setPriceCount(long priceCount) {
		this.priceCount = priceCount;
	}
	public long getQuoteCount() {
		return quoteCount;
	}
	public void setQuoteCount(long quoteCount) {
		this.quoteCount = quoteCount;
	}
	public long getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}
	public String getmBloggerId() {
		return mBloggerId;
	}
	public void setmBloggerId(String mBloggerId) {
		this.mBloggerId = mBloggerId;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getPostApp() {
		return postApp;
	}
	public void setPostApp(String postApp) {
		this.postApp = postApp;
	}
	public String getClassfiy() {
		return classfiy;
	}
	public void setClassfiy(String classfiy) {
		this.classfiy = classfiy;
	}
	public String getTargetTopic() {
		return targetTopic;
	}
	public void setTargetTopic(String targetTopic) {
		this.targetTopic = targetTopic;
	}
	public String getTargetPosterUrl() {
		return targetPosterUrl;
	}
	public void setTargetPosterUrl(String targetPosterUrl) {
		this.targetPosterUrl = targetPosterUrl;
	}
}
