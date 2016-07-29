package jobs.toolkit.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jobs.toolkit.handler.TextHandler;
import jobs.toolkit.handler.TextProcessor;

/**
 *
 * Created by jobs on 2015/8/26.
 */
public class TextFileTools {

    public final static String DEFAULT_CHARSET = "UTF-8";

    /**
     * 读取文件的全部内容并返回
     * @param inputStream
     * @return
     */
    public static String readFileContent(InputStream inputStream) throws IOException {
        return readFileContent(inputStream, DEFAULT_CHARSET);
    }

    /**
     * 读取文件的全部内容并返回
     * @param filePath
     * @return
     */
    public static String readFileContent(String filePath) throws IOException {
        return readFileContent(filePath, DEFAULT_CHARSET);
    }

    /**
     * 读取文件的全部内容并返回
     * @param file
     * @return
     */
    public static String readFileContent(File file) throws IOException {
        return readFileContent(file, DEFAULT_CHARSET);
    }
    /**
     * 读取文件的全部内容并返回
     * @param filePath
     * @param charSet
     * @return
     */
    public static String readFileContent(String filePath, String charSet) throws IOException {
        if(filePath == null || filePath.isEmpty())
            throw new NullPointerException("The param filePath can't be null!");
        return readFileContent(new File(filePath), charSet);
    }

    /**
     *读取文件的全部内容并返回
     * @param file
     * @param charSet
     * @return
     */
    public static String readFileContent(File file, String charSet) throws IOException {
        if(file == null)
            throw new NullPointerException("The param file can't be null!");

        return readFileContent(new FileInputStream(file),charSet);
    }


    /**
     * 读取文件的全部内容并返回
     * @param inputStream
     * @param charSet
     * @return
     */
    public static String readFileContent(InputStream inputStream, String charSet) throws IOException {
        if(inputStream == null)
            throw new NullPointerException("The param inputStream can't be null!");
        final StringBuffer buffer = new StringBuffer();

        catFileLBL(inputStream, new TextHandler() {
            @Override
            public void handle(String line, int lineNum) {
                buffer.append(line);
            }
        }, charSet);

        return buffer.toString();
    }

    /**
     * 按行读取文件, 使用TextHandler处理行数据, LBL表示line by line
     * @param inputStream 文件流
     * @param handler 文件行数据处理接口
     * @param charSet 文件编码
     */
    public static void catFileLBL(InputStream inputStream, TextHandler handler, String charSet) throws IOException{

        if(inputStream == null)
            throw new NullPointerException("The param inputStream can't be null!");

        if(handler == null)
            throw new NullPointerException("The param handler can't be null!");

        BufferedReader bufferedReader = null;
        try{
            bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, charSet));
            int lineNum = 0;
            for(String line = bufferedReader.readLine(); line != null ; line = bufferedReader.readLine()){
                handler.handle(line, ++lineNum);
            }
        }catch(IOException e) {
            // TODO Auto-generated catch block
            throw new IOException(e);
        }finally {
            if(bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    /**
     *按行读取文件, 使用TextHandler处理行数据, LBL表示line by line
     * @param file
     * @param handler
     * @param charSet
     */
    public static void catFileLBL(File file, TextHandler handler, String charSet) throws IOException {
        if(file == null)
            throw new NullPointerException("The param file can't be null!");
        catFileLBL(new FileInputStream(file), handler, charSet);
    }

    /**
     *按行读取文件, 使用TextHandler处理行数据, LBL表示line by line
     * @param filePath
     * @param handler
     * @param charSet
     */
    public static void catFileLBL(String filePath, TextHandler handler, String charSet) throws IOException {
        if(filePath == null || filePath.isEmpty())
            throw new NullPointerException("The param filePath can't be null!");
        catFileLBL(new File(filePath), handler, charSet);
    }

    /**
     *按行读取文件, 使用TextHandler处理行数据, LBL表示line by line
     * @param inputStream
     * @param handler
     */
    public static void catFileLBL(InputStream inputStream, TextHandler handler) throws IOException {
        catFileLBL(inputStream, handler, DEFAULT_CHARSET);
    }

    /**
     * 按行读取文件, 使用TextHandler处理行数据, LBL表示line by line
     * @param file
     * @param handler
     */
    public static void catFileLBL(File file, TextHandler handler) throws IOException {
        catFileLBL(file, handler, DEFAULT_CHARSET);
    }

    /**
     * 按行读取文件, 使用TextHandler处理行数据, LBL表示line by line
     * @param filePath
     * @param handler
     */
    public static void catFileLBL(String filePath, TextHandler handler) throws IOException {
        catFileLBL(filePath, handler, DEFAULT_CHARSET);
    }

    /**
     * 按行处理文本文件
     * @param inputStream 文件流
     * @param processer 行处理器
     * @param charSet 文件编码
     * @param <T> 返回值类型
     * @return
     */
    public static <T> List<T> catFileLBL(InputStream inputStream, TextProcessor<T> processer, String charSet) throws IOException {
        if(inputStream == null)
            throw new NullPointerException("The param inputStream can't be null!");

        if(processer == null)
            throw new NullPointerException("The param processer can't be null!");
        BufferedReader bufferedReader = null;
        List<T> results = new ArrayList<T>();
        try{
            bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, charSet));
            int lineNum = 0;
            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()){
                results.add(processer.process(line, ++lineNum));
            }
            return results.size() == 0 ? null : results;
        }catch(IOException e) {
            // TODO Auto-generated catch block
            throw new IOException(e);
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     *按行处理文本文件
     * @param file
     * @param processer
     * @param charSet
     * @param <T>
     * @return
     */
    public static <T> List<T> catFileLBL(File file, TextProcessor<T> processer, String charSet) throws IOException {
        if(file == null)
            throw new NullPointerException("The param file can't be null!");
        return catFileLBL(new FileInputStream(file), processer, charSet);
    }

    /**
     *按行处理文本文件
     * @param filePath
     * @param processer
     * @param charSet
     * @param <T>
     * @return
     */
    public static <T> List<T> catFileLBL(String filePath, TextProcessor<T> processer, String charSet) throws IOException {
        if(filePath == null || filePath.isEmpty())
            throw new NullPointerException("The filePath file can't be null!");
        return catFileLBL(new File(filePath), processer, charSet);
    }

    /**
     *按行处理文本文件
     * @param inputStream
     * @param processer
     * @param <T>
     * @return
     */
    public static <T> List<T> catFileLBL(InputStream inputStream, TextProcessor<T> processer) throws IOException {
        return catFileLBL(inputStream, processer, DEFAULT_CHARSET);
    }

    /**
     *按行处理文本文件
     * @param filePath
     * @param processer
     * @param <T>
     * @return
     */
    public static <T> List<T> catFileLBL(String filePath, TextProcessor<T> processer) throws IOException {
        return catFileLBL(filePath, processer, DEFAULT_CHARSET);
    }

    /**
     * 按行处理文本文件
     * @param file
     * @param processer
     * @param <T>
     * @return
     */
    public static <T> List<T> catFileLBL(File file, TextProcessor<T> processer) throws IOException {
        return catFileLBL(file, processer, DEFAULT_CHARSET);
    }
}
