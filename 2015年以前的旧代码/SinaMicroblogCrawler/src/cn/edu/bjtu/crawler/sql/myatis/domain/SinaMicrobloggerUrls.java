/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis.domain;


/**
 * @author QiaoJian
 *	保存用户首页上的一些超链接
 */
public class SinaMicrobloggerUrls{
	
	int id;
	/*用户在新浪网上的id*/
	String sinaId;
	/*粉丝页面地址*/
	private String fansUrl;
	/*关注页面地址*/
	private String followUrl;
	/*微博页面地址*/
	private String weiboUrl;
	/*读书页面地址*/
	private String bookUrl;
	/*音乐页面地址*/
	private String musicUrl;
	/*电影页面地址*/
	private String movieUrl;
	/*话题页面地址*/
	private String talkUrl;
	/*个人资料页面地址*/
	private String infoUrl;
	/*足迹页面地址*/
	private String placeUrl;
	/*赞的页面地址*/
	private String priceUrl;
	/*照片页面地址*/
	private String photoUrl;
	/*图书作品页面地址*/
	private String hisBookUrl;
	
	
	/*判断url的关键字分派注入*/
	public void setUrl(String url){
		if(url.indexOf("author")>0){
			this.setHisBookUrl(url);
		}
		if(url.indexOf("album")>0){
			this.setPhotoUrl(url);
		}
		if(url.indexOf("like")>0){
			this.setPriceUrl(url);
		}
		if(url.indexOf("footprint")>0){
			this.setPlaceUrl(url);
		}
		if(url.indexOf("music")>0){
			this.setMusicUrl(url);
		}
		if(url.indexOf("book")>0){
			this.setBookUrl(url);
		}
		if(url.indexOf("movie")>0){
			this.setMovieUrl(url);
		}
		if(url.indexOf("huati")>0){
			this.setTalkUrl(url);
		}
		if(url.indexOf("info")>0){
			this.setInfoUrl(url);
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSinaId() {
		return sinaId;
	}
	public void setSinaId(String sinaId) {
		this.sinaId = sinaId;
	}
	public String getFansUrl() {
		return fansUrl;
	}
	public void setFansUrl(String fansUrl) {
		this.fansUrl = fansUrl;
	}
	public String getFollowUrl() {
		return followUrl;
	}
	public void setFollowUrl(String followUrl) {
		this.followUrl = followUrl;
	}
	public String getWeiboUrl() {
		return weiboUrl;
	}
	public void setWeiboUrl(String weiboUrl) {
		this.weiboUrl = weiboUrl;
	}
	public String getBookUrl() {
		return bookUrl;
	}
	public void setBookUrl(String bookUrl) {
		this.bookUrl = bookUrl;
	}
	public String getMusicUrl() {
		return musicUrl;
	}
	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}
	public String getMovieUrl() {
		return movieUrl;
	}
	public void setMovieUrl(String movieUrl) {
		this.movieUrl = movieUrl;
	}
	public String getTalkUrl() {
		return talkUrl;
	}
	public void setTalkUrl(String talkUrl) {
		this.talkUrl = talkUrl;
	}
	public String getInfoUrl() {
		return infoUrl;
	}
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}
	public String getPlaceUrl() {
		return placeUrl;
	}
	public void setPlaceUrl(String placeUrl) {
		this.placeUrl = placeUrl;
	}
	public String getPriceUrl() {
		return priceUrl;
	}
	public void setPriceUrl(String priceUrl) {
		this.priceUrl = priceUrl;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getHisBookUrl() {
		return hisBookUrl;
	}
	public void setHisBookUrl(String hisBookUrl) {
		this.hisBookUrl = hisBookUrl;
	}
	
	
}
