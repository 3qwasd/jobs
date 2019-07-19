/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.servicer.sina;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;


/**
 * @author QiaoJian
 *
 */
public class HotWeiboService extends BaseSinaService{

	public static String HOTWEIBOREURL = "I:\\weibo\\relation\\";
	public static String HOTWEIBOTEXTURL = "I:\\weibo\\text\\";
	/**
	 * 
	 */
	public HotWeiboService() {
		// TODO Auto-generated constructor stub
		File file = new File(HOTWEIBOREURL);
		if(!file.exists()){
			file.mkdirs();
		}
		file = new File(HOTWEIBOTEXTURL);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	public String extractHtmlFromJson(String json){
		return this.sinaDataParser.extractHtmlFromAjaxJson(json,"data","html");
	}
	public String extractComment(String html){
		Document document = null;
		Elements commentDls = null;
		String mid = null;
		String nextPageUrl = null;
		this.closeAll();
		try{
			document = Jsoup.parse(html);
			commentDls = document.select("dl.comment_list");
			mid = commentDls.get(0).attr("mid");
			File file = new File(HOTWEIBOTEXTURL+mid+"\\");
			if(!file.exists()){
				file.mkdirs();
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		if(commentDls!=null&&commentDls.size()>0){
			for(int i=0;i<commentDls.size();i++){
				try{
					Element commentDl = commentDls.get(i);
					Element commentDD = commentDl.getElementsByTag("dd").get(0);
					List<TextNode> textNodes = commentDD.textNodes();
					String text = null;
					for(TextNode node : textNodes){
						if(node.text().trim().length()>=40){
							text = node.text().trim();

						}
					}
					if(text!=null&&text.length()>0){


						Element aPrice = commentDD.select("a[action-type=fl_like]").get(0);
						String actionDate = aPrice.attr("action-data");
						//System.out.println(actionDate);
						String cid = (actionDate.split("&")[0]).split("=",2)[1];
						this.write2File(HOTWEIBOREURL+mid+".txt", cid+"\n");
						this.writeText(HOTWEIBOTEXTURL+mid+"\\"+cid+".txt", text);
					}
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}
			
		}
		
		try{
			Element nextPageSpan = document.select("a.btn_page_next").first().child(0);
			String actionData = nextPageSpan.attr("action-data");
			if(actionData!=null&&actionData.length()>0)
				nextPageUrl = "http://weibo.com/aj/comment/big?_wv=5&"+actionData+"&__rnd="+new Date().getTime();
			return nextPageUrl;
		}catch(Exception e){
			System.out.println("This is the last page! So has no next page button!");
		}
		return null;
	}
	/**
	 * @param html
	 */
	public String extractHotWeibo(String html) {
		// TODO Auto-generated method stub
		Document document = Jsoup.parse(html);
		Elements topDivs = document.select("div[action-type=feed_list_item].WB_feed_type");
		if(topDivs!=null&&topDivs.size()>0){
			for(int i=0;i<topDivs.size();i++){
				try{
					Element topDiv = topDivs.get(i);
					String mid = topDiv.attr("mid");
					String midExt = topDiv.attr("midext");
					String uid = topDiv.select("a.WB_name").attr("usercard").split("=",2)[1];
					String text = topDiv.select("div[node-type=feed_list_content].WB_text").text();
					String content = mid+";"+midExt+";"+uid+"\n";
					this.write2File(HOTWEIBOREURL+"hot.txt", content);
					this.writeText(HOTWEIBOTEXTURL+mid+".txt", text);
					if(i == topDivs.size()-1){
						return mid;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return null;
		//System.out.println(topListDiv.outerHtml());
	}
	/**
	 * @param html
	 */
	public void extractSearchWeibo(String html) {
		// TODO Auto-generated method stub
		System.out.println(html);
		Map<String,String> res = 
				sinaDataParser.extractHtmlFromScript(html, "STK && STK.pageletM && STK.pageletM.view","pid");
		
		String jsonHtml = res.get("pl_wb_feedlist");
		this.writeText("I:\\search_weibo.html", jsonHtml);;
	}
}
