package jobs.toolkit.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jobs.toolkit.handler.FileHandler;
import jobs.toolkit.handler.FileProcesser;

/**
 * Created by jobs on 2015/8/26.
 */
public class CommonFileTools {
    /**
     * 使用广度优先搜索遍历一个文件目录,使用processer处理非目录文件,并返回结果集
     * @param root
     * @param processer
     * @return
     */
    public static <T> List<T> BFS(String root, FileProcesser<T> processer) throws FileNotFoundException {
        if(root == null || root.isEmpty())
            throw new NullPointerException("The param root is null!");
        File file = new File(root);
        return BFS(file, processer);
    }

    /**
     * 使用广度优先搜索遍历一个文件目录,使用processer处理非目录文件,并返回结果集
     * @param root
     * @param processer
     * @return
     */
    public static <T> List<T> BFS(File root, FileProcesser<T> processer) throws FileNotFoundException {
        if(root == null)
            throw new NullPointerException("The param root is null!");
        if(!root.exists())
            throw new FileNotFoundException("The file is not exists");
        List<T> results = new ArrayList<T>();
        Queue<File> queue = new LinkedList<File>();
        if(root.isDirectory()){
            queue.offer(root);
        }else{
            results.add(processer.process(root));
        }

        while(!queue.isEmpty()){
            File dir = queue.poll();
            File[] ls = dir.listFiles();
            if(ls == null)
                continue;
            for(File file : ls){
                if(file.isDirectory())
                    queue.offer(file);
                else
                    results.add(processer.process(file));

            }
        }
        return results;
    }

    /**
     * 使用广度优先搜索遍历一个文件目录 使用handler处理非目录文件
     * @param root
     * @param handler
     */
    public static void BFS(String root, FileHandler handler) throws FileNotFoundException {
        if(root == null || root.isEmpty())
            throw new NullPointerException("The param root is null!");
        File file = new File(root);
        BFS(file, handler);
    }
    /**
     * 使用广度优先搜索遍历一个文件目录, 使用handler处理非目录文件
     * @param root
     * @param handler
     */
    public static void BFS(File root, FileHandler handler) throws FileNotFoundException {
        if(root == null)
            throw new NullPointerException("The param root is null!");
        if(!root.exists())
            throw new FileNotFoundException("The file is not exists");
        Queue<File> queue = new LinkedList<File>();
        if(root.isDirectory()){
            queue.offer(root);
        }else{
            handler.handle(root);
        }

        while(!queue.isEmpty()){
            File dir = queue.poll();
            File[] ls = dir.listFiles();
            if(ls == null)
                continue;
            for(File file : ls){
                if(file.isDirectory())
                    queue.offer(file);
                else
                    handler.handle(file);

            }
        }
    }
    /**
     * 使用广度优先搜索遍历一个文件目录, 使用handler处理所有文件（包括目录）
     * @param root
     * @param handler
     */
    public static void BFSALL(String root, FileHandler handler) throws FileNotFoundException {
    	if(root == null || root.isEmpty())
            throw new NullPointerException("The param root is null!");
        File file = new File(root);
    	BFSALL(file, handler);
    }
    
    /**
     * 使用广度优先搜索遍历一个文件目录, 使用handler处理所有文件（包括目录）
     * @param root
     * @param handler
     */
    public static void BFSALL(File root, FileHandler handler) throws FileNotFoundException {
        if(root == null)
            throw new NullPointerException("The param root is null!");
        if(!root.exists())
            throw new FileNotFoundException("The file is not exists");
        Queue<File> queue = new LinkedList<File>();
        if(root.isDirectory()){
            queue.offer(root);
        }else{
            handler.handle(root);
        }

        while(!queue.isEmpty()){
            File dir = queue.poll();
            File[] ls = dir.listFiles();
            if(ls == null)
                continue;
            for(File file : ls){
                if(file.isDirectory()){
                    queue.offer(file);
                    handler.handle(file);
                }else
                    handler.handle(file);

            }
        }
    }
}
