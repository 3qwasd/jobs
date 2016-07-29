package jobs.toolkit.logging;

import jobs.toolkit.utils.DateUtils;

/**
 * Created by jobs on 2015/8/28.
 */
public class Log {

    String clazz;

    protected Log(String clazz){
        this.clazz = clazz;
    }

    public void info(Appendable message){
        System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                append("-").append(clazz).
                append("-").append(message));
    }
    public void info(String message, Object ...args){
    	if(args == null)
    		System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                append("-").append(clazz).
                append("-").append(message));
    	else
    		System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                    append("-").append(clazz).
                    append("-").append(String.format(message, args)));
    }
    public void info(Appendable message, Throwable exception){
        System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                append("-").append(clazz).
                append("-").append(message));
        exception.printStackTrace();
    }
    public void info(String message, Throwable exception){
        System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                append("-").append(clazz).
                append("-").append(message));
        exception.printStackTrace();
    }
    public void debug(String message, Object ...args){
    	if(args != null)
    		System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                    append("-").append(clazz).
                    append("-").append(String.format(message, args)));
    	else
    		System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                append("-").append(clazz).
                append("-").append(message));
    }
    public void debug(String message, Throwable exception){
    	System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                append("-").append(clazz).
                append("-").append(message));
    	exception.printStackTrace();
    }
    public void error(String message, Throwable throwable){
    	System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                append("-").append(clazz).
                append("-").append(message));
    	throwable.printStackTrace();
    }
    public void error(String message){
    	System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                append("-").append(clazz).
                append("-").append(message));
    }
    public void warn(String message){
    	System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                append("-").append(clazz).
                append("-").append(message));
    }
    public void warn(String message, Throwable throwable){
    	System.out.println(new StringBuffer(DateUtils.getFormatNow()).
                append("-").append(clazz).
                append("-").append(message));
    	throwable.printStackTrace();
    }
}
