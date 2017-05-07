/*
 * Created on : 2015年9月26日
 */
package ager.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * 日历工具类
 * 
 * @author 李海涛
 * @version 1.0
 */
public class CalendarUtil {

	/**
	 * 获取星座
	 * 
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getStarSigns(int month, int day) {
		String starSigns = "";
		if ((month == 1 && day <= 19) || (month == 12 && day >= 22)) {
			starSigns = "摩羯座";
		} else if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
			starSigns = "水瓶座";
		} else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
			starSigns = "双鱼座";
		} else if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
			starSigns = "白羊座";
		} else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
			starSigns = "金牛座";
		} else if ((month == 5 && day >= 21) || (month == 6 && day <= 21)) {
			starSigns = "双子座";
		} else if ((month == 6 && day >= 22) || (month == 7 && day <= 22)) {
			starSigns = "巨蟹座";
		} else if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) {
			starSigns = "狮子座";
		} else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
			starSigns = "处女座";
		} else if ((month == 9 && day >= 23) || (month == 10 && day <= 23)) {
			starSigns = "天秤座";
		} else if ((month == 10 && day >= 24) || (month == 11 && day <= 22)) {
			starSigns = "天蝎座";
		} else if ((month == 11 && day >= 23) || (month == 12 && day <= 21)) {
			starSigns = "射手座";
		}
		return starSigns;
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date);
	}
	
	/**
	 * 今天到指定日期相隔多少天
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static long getToDays(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		Date date = cal.getTime();
		Date today = new Date();
		return (date.getTime() - today.getTime()) / 1000 / 3600 / 24;
	}
	
	/**
	 * 返回公历节日
	 */
	public static String getFestival(int month, int day) {
		String festival = "";
		if (month == 1 && day == 1) {
			festival = "元旦";
		} else if (month == 2 && day == 14) {
			festival = "情人节";
		} else if (month == 3 && day == 8) {
			festival = "妇女节";
		} else if (month == 5 && day == 1) {
			festival = "劳动节";
		} else if (month == 6 && day == 1) {
			festival = "儿童节";
		} else if (month == 7 && day == 1) {
			festival = "建党节&香港回归";
		} else if (month == 8 && day == 1) {
			festival = "建军节";
		} else if (month == 9 && day == 10) {
			festival = "教师节";
		} else if (month == 10 && day == 1) {
			festival = "国庆节";
		} else if (month == 12 && day == 20) {
			festival = "澳门回归";
		} else if (month == 12 && day == 24) {
			festival = "平安夜";
		} else if (month == 12 && day == 25) {
			festival = "圣诞节";
		}
		return festival;
	}
	
	/**
	 * 判断某年是否闰年
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		GregorianCalendar cal = new GregorianCalendar();
		return cal.isLeapYear(year); // 是否闰年
	}

}
