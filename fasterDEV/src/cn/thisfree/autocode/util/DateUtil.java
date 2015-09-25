package cn.thisfree.autocode.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author xiaolong.huang
 *
 */
public class DateUtil {
	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getCurrentDate(){
		return format.format(new Date());
	}
}
