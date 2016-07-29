/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.ui.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * @author QiaoJian
 *
 */
public class ResourceManager {
	
	private static ResourceManager instance = null;
	
	private Properties universities;
	private List<String> schoolList;
	private List<String> lastNames;
	private List<String> fristNames;
	private List<String> messages = new ArrayList<String>();
	private ResourceManager(){
		loadUniversities();
		loadLastNames();
		loadFirstNames();
	}
	private void loadUniversities(){
		universities = new Properties();
		schoolList = new ArrayList<String>();
		try {
			InputStream inputStream = this.getFileInputStream(this.getClassPath()+"universities.properties");
			universities.load(inputStream);
			for(Object key:universities.keySet()){
				schoolList.add(key+"");
			}
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadFirstNames(){
		this.fristNames = new ArrayList<String>();
		File firstNameFile = new File(this.getClassPath()+"firstname.txt");
		try {
			String text = null;
			RandomAccessFile randomAccessFile = new RandomAccessFile(firstNameFile,"r");
			while((text=randomAccessFile.readLine())!=null){
				fristNames.add(text.trim());
			}
			randomAccessFile.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadLastNames(){
		lastNames = new ArrayList<String>();
		File lastNameFile = new File(this.getClassPath()+"lastName.dic");
		try {
			String text = null;
			RandomAccessFile randomAccessFile = new RandomAccessFile(lastNameFile,"r");
			while((text=randomAccessFile.readLine())!=null){
				lastNames.add(text);
			}
			randomAccessFile.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static synchronized ResourceManager getInstance(){
		if(instance == null){
			instance = new ResourceManager();
		}
		return instance;
	}
	/**
	 * 获取当前项目的类路径
	 * @return
	 */
	public String getClassPath(){
		
		String classPath = ResourceManager.class.getClassLoader().getResource("").getPath();
		return classPath;
	}
	/**
	 * 获取文件的输入流
	 * @return
	 * @throws Exception 
	 */
	public InputStream getFileInputStream(String path) throws Exception{
		File file = new File(path);
		return new FileInputStream(file);
	}
	public String getUniversitiyTaskName(String key){
		return this.universities.getProperty(key);
	}
	public List<String> getLastNames() {
		return lastNames;
	}
	public List<String> getSchoolList() {
		return schoolList;
	}
	public void setSchoolList(List<String> schoolList) {
		this.schoolList = schoolList;
	}
	public synchronized void addMessage(String msg){
		messages.add(msg);
	}
	public synchronized String getMessage(){
		if(messages.size()>0)
			return messages.remove(0);
		else
			return null;
	}
	/**
	 * @return
	 */
	public List<String> getFirstNames() {
		// TODO Auto-generated method stub
		return this.fristNames;
	}
}
