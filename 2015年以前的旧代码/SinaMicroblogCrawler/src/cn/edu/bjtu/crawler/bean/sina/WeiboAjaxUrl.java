/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.bean.sina;

/**
 * 新浪微博的异步加载微博数据的url生成类
 * @author QiaoJian
 *
 */
public class WeiboAjaxUrl {
	
	/**
	 * 基本url需要替换其中的参数
	 */
	private String baseUrl = "http://weibo.com/p/aj/mblog/mbloglist?"
			+ "domain=[domain]&"
			+ "pre_page=[pre_page]"
			+ "&page=[page]&"
			+ "max_id=[max_id]&"
			+ "end_id=[end_id]&"
			+ "count=15&pagebar=[pagebar]&"
			+ "max_msign=&"
			+ "filtered_min_id=&"
			+ "pl_name=[pl_name]&"
			+ "id=[domain][id]&"
			+ "script_uri=/p/[domain][id]/weibo&"
			+ "feed_type=0&"
			+ "from=page_[domain]&"
			+ "wvr=5&"
			+ "mod=headweibo"
			+ "&__rnd=[__rnd]";
	/*需要替换的参数*/
	private String url = baseUrl;
	private String domain;
	private String id;
	private String max_id;
	private String end_id;
	private String pre_page;
	private String page;
	private String pagebar;
	private String __rnd;
	private String pl_name = "Pl_Official_LeftProfileFeed__13";
	
	public String getUrl(){
		url = baseUrl;
		url = url.replaceAll("\\[domain\\]", domain).
				replaceAll("\\[id\\]", id).
				replaceAll("\\[max_id\\]", max_id).
				replaceAll("\\[end_id\\]", end_id).
				replaceAll("\\[pre_page\\]", pre_page).
				replaceAll("\\[page\\]", page).
				replaceAll("\\[pagebar\\]", pagebar).
				replaceAll("\\[__rnd\\]", __rnd).
				replaceAll("\\[pl_name\\]", pl_name);
		return url;
	}


	public void setPl_name(String pl_name) {
		this.pl_name = pl_name;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMax_id() {
		return max_id;
	}


	public void setMax_id(String max_id) {
		this.max_id = max_id;
	}


	public String getEnd_id() {
		return end_id;
	}


	public void setEnd_id(String end_id) {
		this.end_id = end_id;
	}


	public String getPre_page() {
		return pre_page;
	}


	public void setPre_page(String pre_page) {
		this.pre_page = pre_page;
	}


	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public String getPagebar() {
		return pagebar;
	}


	public void setPagebar(String pagebar) {
		this.pagebar = pagebar;
	}


	public String get__rnd() {
		return __rnd;
	}


	public void set__rnd(String __rnd) {
		this.__rnd = __rnd;
	}
	
}
