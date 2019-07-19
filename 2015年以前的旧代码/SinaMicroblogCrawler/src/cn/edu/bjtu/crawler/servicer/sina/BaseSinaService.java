/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.servicer.sina;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.bjtu.crawler.parser.sinadata.SinaDataParser;
import cn.edu.bjtu.crawler.servicer.BaseService;
import cn.edu.bjtu.crawler.utils.HttpPropertiesUtils;
import cn.edu.bjtu.crawler.utils.StringUtils;

/**
 * @author QiaoJian
 *
 */
public abstract class BaseSinaService extends BaseService{

	SinaDataParser sinaDataParser;

	FileOutputStream outputStream = null;
	public BaseSinaService() {
		super();
		// TODO Auto-generated constructor stub
		this.sinaDataParser = new SinaDataParser();
	}
	public List<String> readRandomFile(String filePath){
		/*读取文件*/
		try{
			List<String> res = new ArrayList<String>();
			File file = new File(filePath);
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
			String text = null;
			while ((text = randomAccessFile.readLine())!=null) {
				/*RandomAccessFile以ISO-8859-1编码读取文件，需转成UTF-8*/
				res.add(text);
			}

			randomAccessFile.close();
			return res;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 创建请求头的map集合，该集合内的请求头数据，是无论什么请求都需要的
	 * @return
	 */
	public Map<String,String> createCommonHeader(){
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Accept",HttpPropertiesUtils.getAccept());
		headers.put("Accept-Language", HttpPropertiesUtils.getAcceptLanguage());
		//headers.put("Accept-Encoding", HttpPropertiesUtils.getAcceptEncoding());
		headers.put("Connection", HttpPropertiesUtils.getConnection());
		headers.put("User-Agent", HttpPropertiesUtils.getUserAgent());
		//headers.put("Content-Type", HttpPropertiesUtils.getContentType());
		//headers.put("Cache-Control", HttpPropertiesUtils.getCacheControl());
		return headers;

	}
	/**
	 * 抽取用户首页的配置信息
	 * @param html
	 * @return
	 */
	public Map<String,String> getSinaConfigInfo(String html){
		return sinaDataParser.extractSinaConfigInfo(html);
	}

	public String readFromFile(String fileName){
		File file = new File(fileName);
		try {
			FileInputStream inputStream = new FileInputStream(file);
			return StringUtils.stream2String(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @param path
	 */
	public File createTfOutFile(String path){
		File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
				return file;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return file;
	}
	public void write2File(String path,String content){

		File file = createTfOutFile(path);

		if(outputStream == null){
			try{
				outputStream = new FileOutputStream(file,true);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		try {
			outputStream.write(content.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeText(String path,String text){
		File file = createTfOutFile(path);
		try {
			FileOutputStream stream = new FileOutputStream(file);
			stream.write(text.getBytes());
			stream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void closeAll(){
		try {
			if(outputStream!=null){
				outputStream.close();
				outputStream = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
