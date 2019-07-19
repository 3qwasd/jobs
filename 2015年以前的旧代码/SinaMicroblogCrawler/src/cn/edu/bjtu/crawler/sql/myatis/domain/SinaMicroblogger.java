/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sql.myatis.domain;

import java.util.Date;

/**
 * @author QiaoJian
 *
 */
public class SinaMicroblogger {
	
	private long localId;
	private String sinaId;
	private String mblogUrl;
	private String mblogNickname;
	private String address;
	private String gender;
	private Date birthDay;
	private String remark;
	private String corporation;
	private String startSign;
	private String mBlogTags;
	private long fansNum = 0;
	private long attentions = 0;
	private long mblogNum = 0;
	private String sinaProve;
	private String college;
	private String completeFlag;
	private int deep;
	private String clissfiy;
	private String industry;
	private Date regTime;
	private String domain;
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
	public String getMblogUrl() {
		return mblogUrl;
	}
	public void setMblogUrl(String mblogUrl) {
		this.mblogUrl = mblogUrl;
	}
	public String getMblogNickname() {
		return mblogNickname;
	}
	public void setMblogNickname(String mblogNickname) {
		this.mblogNickname = mblogNickname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCorporation() {
		return corporation;
	}
	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}
	public String getStartSign() {
		return startSign;
	}
	public void setStartSign(String startSign) {
		this.startSign = startSign;
	}
	public String getmBlogTags() {
		return mBlogTags;
	}
	public void setmBlogTags(String mBlogTags) {
		this.mBlogTags = mBlogTags;
	}
	public long getFansNum() {
		return fansNum;
	}
	public void setFansNum(long fansNum) {
		this.fansNum = fansNum;
	}
	public long getAttentions() {
		return attentions;
	}
	public void setAttentions(long attentions) {
		this.attentions = attentions;
	}
	public long getMblogNum() {
		return mblogNum;
	}
	public void setMblogNum(long mblogNum) {
		this.mblogNum = mblogNum;
	}
	public String getSinaProve() {
		return sinaProve;
	}
	public void setSinaProve(String sinaProve) {
		this.sinaProve = sinaProve;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getCompleteFlag() {
		return completeFlag;
	}
	public void setCompleteFlag(String completeFlag) {
		this.completeFlag = completeFlag;
	}
	public int getDeep() {
		return deep;
	}
	public void setDeep(int deep) {
		this.deep = deep;
	}
	public String getClissfiy() {
		return clissfiy;
	}
	public void setClissfiy(String clissfiy) {
		this.clissfiy = clissfiy;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
}
