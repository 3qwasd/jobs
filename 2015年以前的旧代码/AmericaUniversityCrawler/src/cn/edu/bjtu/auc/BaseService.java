/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.bjtu.auc.ui.utils.ResourceManager;
/**
 * @author QiaoJian
 *
 */
public abstract class BaseService {

	Document document;
	
	public ResourceManager resourceManager = ResourceManager.getInstance();
	
	public StudentInfo createStudentInfo(String name,String email){
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.putInfo("name", name);
		studentInfo.putInfo("email", email);
		addMsg("name="+name+",email="+email);
		return studentInfo;
	}
	public void addNoResultMsg(String lastName){
		String msg = lastName+" has no result for search!";
		addMsg(msg);
	}
	public void addMsg(String msg){
		resourceManager.addMessage(msg);
	}
	/**
	 * 从响应中获取输入流
	 * @param httpResponse
	 * @return
	 */
	public InputStream getInputStreamFromResponse(HttpResponse httpResponse){
		if(httpResponse == null)
			return null;
		HttpEntity entity = httpResponse.getEntity();
		try {
			InputStream inputStream = entity.getContent();
			return inputStream;
		} catch (Exception e) {
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
	public String getStringFromResponse(HttpResponse httpResponse,String charSet){
		InputStream inputStream = this.getInputStreamFromResponse(httpResponse);
		String result = "";
		if(inputStream!=null)
			result = stream2String(inputStream,charSet);

		return result;
	}
	/**
	 * 从响应中获取字符串
	 * @param httpResponse
	 * @return
	 */
	public String getStringFromResponse(HttpResponse httpResponse){
		InputStream inputStream = this.getInputStreamFromResponse(httpResponse);
		String result = "";
		if(inputStream!=null)
			result = stream2String(inputStream);

		return result;
	}
	/**
	 * 字节流转成字符串
	 * @param in
	 * @return
	 */
	private String stream2String(InputStream in,String charSet) {
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
			return new String(buffer.toString().getBytes(),charSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "error:"+e.getMessage();
		}
	}
	/**
	 * 字节流转成字符串
	 * @param in
	 * @return
	 */
	private String stream2String(InputStream in) {
		BufferedReader reader=null;
		reader = new BufferedReader(new InputStreamReader(in));
		StringBuffer buffer=new StringBuffer();
		String str=null;
		try{
			while((str=new String(reader.readLine().getBytes("gb2312")))!=null){
				buffer.append(str+"\n");
			}	
			reader.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		try {
			return new String(buffer.toString().getBytes(),"gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "error:"+e.getMessage();
		}
	}
	/**
	 * 解析html内容
	 * @param htmlContent
	 * @return
	 */
	public void parserHtml(InputStream htmlContent){

		try {
			document = Jsoup.parse(htmlContent,"UTF-8","");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 解析html内容
	 * @param htmlContent
	 * @return
	 */
	public void parserHtml(String htmlContent){
		document = Jsoup.parse(htmlContent);
	}
	/**
	 * 获取html中的特定元素集合
	 * @param htmlContent
	 * @param selector
	 * @return
	 */
	public Elements getElementsBySelector(String selector){
		return document.select(selector);
	}
	/**
	 * 获取某个名称的tag元素
	 * @param document
	 * @param tagName
	 * @param htmlParserFilter
	 */
	public Elements getElementsByTag(String tagName){
		Elements elements = document.getElementsByTag(tagName);

		return elements;
	}
	/**
	 * 获取某个名称的tag元素
	 * @param document
	 * @param tagName
	 * @param htmlParserFilter
	 */
	public Element getElementByTag(String tagName){
		Elements elements = document.getElementsByTag(tagName);
		if(elements!=null&&elements.size()>0){

			return elements.get(0);
		}
		return null;
	}
	/**
	 * @param string
	 */
	public Element getElementBySelector(String string) {
		// TODO Auto-generated method stub
		Elements elements = document.select(string);
		if(elements!=null&&elements.size()>0)
			return elements.get(0);

		return null;
	}
	public Element getElementById(String id){
		return document.getElementById(id);
	}
	public void writeLinksToText(List<String> links,String filePath) throws Exception{
		File linkFile = new File(filePath);
		if(!linkFile.exists()){

			linkFile.createNewFile();

		}
		FileOutputStream outputStream = new FileOutputStream(linkFile,true);
		for(String link:links){
			outputStream.write((link+"\n").getBytes());
		}

		outputStream.close();
	}
	/**
	 * 写入excel
	 * @param infos
	 * @throws Exception 
	 */
	public void writeToExcel(List<StudentInfo> infos,String filePath,String sheetName) throws Exception{
		File excelFile = new File(filePath);
		Workbook workbook = null;
		if(!excelFile.exists()){
			workbook = new HSSFWorkbook();
		}else{
			InputStream inputStream = new FileInputStream(excelFile);
			workbook = WorkbookFactory.create(inputStream);
		}
		Sheet sheet = workbook.getSheet(sheetName);
		if(sheet == null){
			sheet = workbook.createSheet(sheetName);
		}
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 6000);
		sheet.setColumnWidth(4, 6000);
		sheet.setColumnWidth(5, 6000);
		sheet.setColumnWidth(6, 6000);
		sheet.setColumnWidth(7, 6000);
		sheet.setColumnWidth(8, 6000);
		sheet.setColumnWidth(9, 6000);
		sheet.setColumnWidth(10, 6000);
		sheet.setColumnWidth(11, 6000);
		sheet.setColumnWidth(12, 6000);
		
		int rowNum = sheet.getLastRowNum();
		if(rowNum == 0){
			int i = 0;
			Row row = sheet.createRow(0);
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			HSSFFont font = (HSSFFont) workbook.createFont();
			font.setFontHeightInPoints((short) 16);//设置字体大小
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
			font.setFontHeightInPoints((short) 12);
			style.setFont(font);
			for(String key:infos.get(0).keySet()){
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(key);
				i++;
			}
		}
		rowNum++;
		for(StudentInfo info:infos){
			Row row = sheet.createRow(rowNum);
			rowNum++;
			int i=0;
			for(String key:info.keySet()){
				Cell cell = row.createCell(i);

				cell.setCellValue(info.getInfo(key));

				i++;
			}
		}
		FileOutputStream fileOut = new FileOutputStream(filePath);
		workbook.write(fileOut);
		fileOut.close();
	}
	public abstract List<StudentInfo> collecte(String lastName);

	public String getProjectPath(){
		String path = this.getClass().getClassLoader().getResource("").getPath();
		return path+"\\data\\";
	}
	public Document getDocument() {
		return document;
	}
	/**
	 * @param httpResponse
	 * @param string
	 * @param info
	 */
	public void saveImage(HttpResponse httpResponse, String path, String fileName) {
		// TODO Auto-generated method stub
		InputStream inputStream = this.getInputStreamFromResponse(httpResponse);
		if(inputStream == null)
			return;
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String imageName = path+"\\"+fileName.trim();
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(imageName));
			byte b[] = new byte[1024];
			int i=0;
			while((i = inputStream.read(b))!=-1){
				outputStream.write(b);
			}
			
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
