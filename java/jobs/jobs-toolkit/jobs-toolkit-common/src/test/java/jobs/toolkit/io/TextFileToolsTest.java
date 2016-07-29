package jobs.toolkit.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.handler.TextHandler;
import jobs.toolkit.handler.TextProcessor;
import jobs.toolkit.io.TextFileTools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jobs on 2015/8/28.
 */
public class TextFileToolsTest {

    private InputStream inputStream;
    private String filePath;
    private File file;
    private TextHandler textHandler;
    private TextProcessor<Integer> textProcessor;
    private String charSet;
    private InputStream inputStream1 = null;
    private String filePath1 = null;
    private File file1 = null;
    private TextHandler textHandler1 = null;
    private TextProcessor<Integer> textProcessor1 = null;
    private String charSet1 = null;
    private InputStream inputStream2;
    private String filePath2;
    private File file2;
    private TextHandler textHandler2;
    private TextProcessor<Integer> textProcessor2;
    private String charSet2;
    @Before
    public void setUp() throws Exception {
//        this.filePath = "E:\\data\\config.json";
//        this.file = new File(this.filePath);
//        this.inputStream = new FileInputStream(this.file);
//        this.charSet = "GBK";
//        this.filePath1 = "E:\\data\\xxxxx.xxx";
//        this.file1 = new File(this.filePath1);
//        this.charSet1 = "TFFF";
        this.textHandler = new TextHandler() {
            @Override
            public void handle(String text, int lineNum) {
                System.out.println(text);
            }
        };
        this.textProcessor = new TextProcessor<Integer>() {
            @Override
            public Integer process(String text, Object... args) {
                System.out.println(text);
                return (Integer) args[0];
            }
        };
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadFileContent() throws Exception {
        System.out.println(TextFileTools.readFileContent(filePath));
    }
    @Test
    public void testCatFileLBLNew() throws Exception{
    	final List<String[]> list = new ArrayList<>();
    	final BufferedWriter writer  = new BufferedWriter(new FileWriter(new File("H:\\test.text")));
    	TextFileTools.catFileLBL("H:\\Monster.txt", new TextHandler() {
			
			@Override
			public void handle(String text, int lineNum) {
				// TODO Auto-generated method stub
				if(text.startsWith("//")){
					try {
						writer.write(text+"\r");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				
				if(text.equals("end")){
					try {
						writer.write(text+"\r");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				String[] array = text.trim().split("[\t ]+");
				if(array.length < 28){
					try {
						writer.write(text+"\r");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				if(array.length == 28){
					array[20] = Integer.valueOf(array[20])*10 + "";
				}else{
					array[21] = Integer.valueOf(array[21])*10 + "";
				}
				StringBuffer buffer = new StringBuffer();
	    		for(String str:array){
	    			buffer.append(str).append("\t");
	    		}
	    		buffer.append("\r");
	    		try {
					writer.write(buffer.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "GBK");
    	
    	writer.flush();
    	writer.close();
    }
    @Test
    public void testCatFileLBL() throws Exception {
        TextFileTools.catFileLBL(this.inputStream,this.textHandler);
        TextFileTools.catFileLBL(this.filePath,this.textHandler);
        TextFileTools.catFileLBL(this.file,this.textHandler);
    }
    @Test
    public void testCatFileLBL1() throws Exception {
        System.out.println(TextFileTools.catFileLBL(this.inputStream, this.textProcessor));
        System.out.println(TextFileTools.catFileLBL(this.filePath, this.textProcessor));
        System.out.println(TextFileTools.catFileLBL(this.file, this.textProcessor));
    }
    @Test
    public void testCatFileLBL2(){
        try{
            TextFileTools.catFileLBL(this.inputStream2, this.textHandler);
        }catch (Throwable e){
            System.out.println(e);
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.file1, this.textHandler);
        }catch (Throwable e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.filePath1, this.textHandler);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
    @Test
    public void testCatFileLBL3(){
        try{
            TextFileTools.catFileLBL(this.inputStream2, this.textProcessor);
        }catch (Throwable e){
            System.out.println(e);
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.file1, this.textProcessor);
        }catch (Throwable e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.filePath1, this.textProcessor);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
    @Test
    public void testCatFileLBL4(){
        try{
            TextFileTools.catFileLBL(this.inputStream2, this.textProcessor);
        }catch (Throwable e){
            System.out.println(e);
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.file2, this.textProcessor);
        }catch (Throwable e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.filePath2, this.textProcessor);
        }catch (Throwable e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.inputStream2, this.textHandler);
        }catch (Throwable e){
            System.out.println(e);
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.file2, this.textHandler);
        }catch (Throwable e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.filePath2, this.textHandler);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
    @Test
    public void testCatFileLBL5(){
        try{
            TextFileTools.catFileLBL(this.inputStream, this.textProcessor2);
        }catch (Throwable e){
            System.out.println(e);
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.file, this.textProcessor2);
        }catch (Throwable e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.filePath, this.textProcessor2);
        }catch (Throwable e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.inputStream, this.textHandler2);
        }catch (Throwable e){
            System.out.println(e);
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.file, this.textHandler2);
        }catch (Throwable e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.filePath, this.textHandler2);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
    @Test
    public void testCatFileLBL6(){
        try{
            TextFileTools.catFileLBL(this.inputStream, this.textProcessor,charSet1);
        }catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.file, this.textProcessor,charSet1);
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.filePath, this.textProcessor,charSet1);
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.inputStream, this.textHandler,charSet1);
        }catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.file, this.textHandler,charSet1);
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            TextFileTools.catFileLBL(this.filePath, this.textHandler,charSet1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}