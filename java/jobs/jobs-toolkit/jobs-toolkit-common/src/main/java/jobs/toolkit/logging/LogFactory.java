package jobs.toolkit.logging;

/**
 * Created by jobs on 2015/8/28.
 */
public class LogFactory {

    public static Log getLog(Class<?> clazz){
        return getLog(clazz.getName());
    }
    public static Log getLog(String name){
    	return new Log(name);
    }
}
