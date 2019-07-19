package jobs.toolkit.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间日期工具集
 * @author jobs
 *
 */
public class DateUtils {
		
	/**
	 * 格式化当前日期时间为yyyy-MM-dd HH:mm:ss格式，并返回
	 * @return
	 */
	public static String getFormatNow(){
		return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", Calendar.getInstance());
	}
	/**
	 * 格式化当前日期时间为yyyy-MM-dd HH:mm:ss格式，并返回
	 * @return
	 */
	public static String formatDate(Date date){
		return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", date);
	}
}
