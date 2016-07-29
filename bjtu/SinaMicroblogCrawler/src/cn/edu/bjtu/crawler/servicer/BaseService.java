/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.servicer;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import cn.edu.bjtu.crawler.utils.StringUtils;

/**
 * @author QiaoJian
 *
 */
public abstract class BaseService {
	/**
	 * 从响应中获取输入流
	 * @param httpResponse
	 * @return
	 */
	public InputStream getInputStreamFromResponse(HttpResponse httpResponse){
		HttpEntity entity = httpResponse.getEntity();
		try {
			InputStream inputStream = entity.getContent();
			return inputStream;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从响应中获取字符串
	 * @param httpResponse
	 * @return
	 */
	public String getStringFromResponse(HttpResponse httpResponse){
		InputStream inputStream = this.getInputStreamFromResponse(httpResponse);
		String result = StringUtils.stream2String(inputStream);
		
		return result;
	}
}
