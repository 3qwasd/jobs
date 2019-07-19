/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;

/**
 * @author QiaoJian
 * 字符串工具类
 */
public class StringUtils {
	
	/**
	 * 返回格式化的当前时间
	 * @return
	 */
	public static String getFormatCurrDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
	/**
	 * 返回格式化的时间字符创
	 * @return
	 */
	public static String getFormatDate(Date date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	/**
	 * 格式化新浪微博网的url
	 * @param url
	 * @return
	 */
	public static String formatSinaUrl(String url){
		if(url.startsWith("http://weibo.com")||url.startsWith("http://www.weibo.com")){
			return url;
		}else{
			String formatUrl = "http://weibo.com"+url;
			return formatUrl;
		}
	}
	/**
	 * cookiesMap组装成字符串
	 * @param cookies
	 * @return
	 */
	public static String importantCookies2String(Map<String,String> cookies){
		StringBuilder builder=null; 
		if(cookies!=null && cookies.size()>0){
			builder=new StringBuilder();
			int i=0;
			for(String key:cookies.keySet()){
				builder.append(cookies.get(key));
				if(i!=cookies.size()-1){
					builder.append("; ");
				}
				i++;
			}
			
			return builder.toString();
		}		
		return null;
	}
	/**
	 * cookiesMap组装成字符串
	 * @param cookies
	 * @return
	 */
	public static String cookies2String(Map<String,String> cookies){
		StringBuilder builder=null; 
		if(cookies!=null && cookies.size()>0){
			builder=new StringBuilder();
			int i=0;
			for(String key:cookies.keySet()){
				builder.append(key+"="+cookies.get(key));
				if(i!=cookies.size()-1){
					builder.append("; ");
				}
				i++;
			}
			
			return builder.toString();
		}		
		return null;
	}
	/**
	 * cookies列表组装成字符串
	 * @param cookies
	 * @return
	 */
	public static String cookies2String(List<Cookie> cookies){
		
		StringBuilder builder=null; 
		if(cookies!=null && cookies.size()>0){
			builder=new StringBuilder();
			for(int j=0;j<cookies.size();j++){
				Cookie c=cookies.get(j);
				builder.append(c.getName()+"="+c.getValue());
				if(j!=cookies.size()-1)
					builder.append("; ");
			 }
			return builder.toString();
		}		
		return null;
	}
	/**
	 * 字节流转成字符串
	 * @param in
	 * @return
	 */
	public static String stream2String(InputStream in) {
    	BufferedReader reader=null;
		reader = new BufferedReader(new InputStreamReader(in));
		StringBuffer buffer=new StringBuffer();
		String str=null;
		try{
			while((str=reader.readLine())!=null){
				buffer.append(str+"\n");
			}	
			reader.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		try {
			return new String(buffer.toString().getBytes(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "error:"+e.getMessage();
		}
    }
	
}
