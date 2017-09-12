/*
 * Created on: 2015年9月26日
 */
package ager.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 李海涛
 * @version 1.0
 */
public class AgeUtil {
	private Date birthday; // 公历生日
	private int nextBirthWeekNum; // 下次生日星期几
	
	public AgeUtil(String birthStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.birthday = sdf.parse(birthStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回虚岁，虚岁用农历算
	 * 算法：虚岁 = 今年 - 出生年 + 1
	 * 
	 * @param birthday 公历生日，格式为yyyy-MM-dd
	 * @return
	 */
	public int getNominalAge() {
		ChineseCalendar birthCal = new ChineseCalendar(birthday);
		ChineseCalendar nowCal = new ChineseCalendar(new Date());
		int nominalAge = nowCal.getYear() - birthCal.getYear() + 1;
		return nominalAge;
	}
	
	/**
	 * 返回周岁，周岁用公历算
	 * 算法：周岁 = 今年 - 出生年（已过生日）（未过生日还要-1）
	 * 
	 * @param birthday 公历生日，格式为yyyy-MM-dd
	 * @return
	 */
	public int getAge() {
		int age = 0;
		Calendar birthCal = Calendar.getInstance();
		birthCal.setTime(birthday);
		int birthYear = birthCal.get(Calendar.YEAR);
		int birthMonth = birthCal.get(Calendar.MONTH);
		int birthDay = birthCal.get(Calendar.DAY_OF_MONTH);
		Calendar nowCal = Calendar.getInstance();
		int nowYear = nowCal.get(Calendar.YEAR);
		int nowMonth = nowCal.get(Calendar.MONTH);
		int nowDay = nowCal.get(Calendar.DAY_OF_MONTH);
		if (nowMonth < birthMonth || (nowMonth == birthMonth && nowDay < birthDay)) {
			// 未过生日
			age = nowYear - birthYear - 1;
		} else {
			// 已过生日
			age = nowYear - birthYear;
		}
		return age;
	}
	
	/**
	 * 返回生肖
	 * 
	 * @return
	 */
	public String getAnimalsYear() {
		ChineseCalendar chineseCal = new ChineseCalendar(birthday);
		return chineseCal.getAnimalsYear();
	}
	
	/**
	 * 返回星座
	 * 
	 * @return
	 */
	public String getStarSigns() {
		Calendar birthCal = Calendar.getInstance();
		birthCal.setTime(birthday);
		int birthMonth = birthCal.get(Calendar.MONTH) + 1;
		int birthDay = birthCal.get(Calendar.DAY_OF_MONTH);
		return CalendarUtil.getStarSigns(birthMonth, birthDay);
	}
	
	/**
	 * 返回公历出生日期
	 * 
	 * @return
	 */
	public String getGLBirthday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(birthday);
	}
	
	/**
	 * 返回生日当天公历节日
	 */
	public String getGLFestival() {
		Calendar birthCal = Calendar.getInstance();
		birthCal.setTime(birthday);
		int birthMonth = birthCal.get(Calendar.MONTH) + 1;
		int birthDay = birthCal.get(Calendar.DAY_OF_MONTH);
		return CalendarUtil.getFestival(birthMonth, birthDay);
	}
	
	/**
	 * 返回农历出生日期
	 * 
	 * @return
	 */
	public String getNLBirthday() {
		ChineseCalendar chineseCal = new ChineseCalendar(birthday);
		return chineseCal.toString();
	}
	
	/**
	 * 返回生日当天农历节日
	 */
	public String getNLFestival() {
		ChineseCalendar chineseCal = new ChineseCalendar(birthday);
		return chineseCal.getFestival();
	}
	
	/**
	 * 返回生活天数
	 * 
	 * @return
	 */
	public long getDays() {
		Date today = new Date();
		long days = (today.getTime() - birthday.getTime()) / 1000 / 3600 / 24 + 1; // 加1天
		return days;
	}
	
	/**
	 * 返回距下次公历生日天数
	 * 
	 * @return
	 */
	public long getToGLBirthDays() {
		long days = 0;
		int nextBirthYear = 0; // 下次生日年份
		Calendar birthCal = Calendar.getInstance();
		Calendar nowCal = Calendar.getInstance();
		Calendar nextBirthCal = Calendar.getInstance(); // 下次生日日历对象
		birthCal.setTime(birthday);
		int birthMonth = birthCal.get(Calendar.MONTH);
		int birthDay = birthCal.get(Calendar.DAY_OF_MONTH);
		int nowYear = nowCal.get(Calendar.YEAR);
		int nowMonth = nowCal.get(Calendar.MONTH);
		int nowDay = nowCal.get(Calendar.DAY_OF_MONTH);
		
		if (birthMonth + 1 == 2 && birthDay == 29) { // 如果生日是2月20日，birthMonth值为0-11，所以加1
			if (CalendarUtil.isLeapYear(nowYear)) { // 如果今年是闰年
				if (nowMonth < birthMonth || (nowMonth == birthMonth && nowDay < birthDay)) {
					// 未过生日
					nextBirthYear = nowYear; // 今年
				} else {
					// 已过生日
					nextBirthYear = nowYear + 4; // 4年后
				}
			} else {
				for (int i = 1; i <= 3; i++) { // 如果今年是平年，距离闰年最大年数是3
					if (CalendarUtil.isLeapYear(nowYear + i)) {
						nextBirthYear = nowYear + i;
						break;
					}
				}
			}
		} else {
			if (nowMonth < birthMonth || (nowMonth == birthMonth && nowDay < birthDay)) {
				// 未过生日
				nextBirthYear = nowYear; // 今年
			} else {
				// 已过生日
				nextBirthYear = nowYear + 1; // 明年
			}
		}
		
		nextBirthCal.set(nextBirthYear, birthMonth, birthDay, 0, 0, 0);; // 下次生日年份 生日月 生日日
		this.nextBirthWeekNum = nextBirthCal.get(Calendar.DAY_OF_WEEK); // 赋值星期
		Date nextBirth = nextBirthCal.getTime();
		Date today = nowCal.getTime();
		days = (nextBirth.getTime() - today.getTime()) / 1000 / 3600 / 24;
		return days;
	}
	
	/**
	 * 返回下次生日星期，必须先调用getToGLBirthDays()
	 * 
	 * @return
	 */
	public String getNextGLBirthWeek() {
		String[] week = {"日", "一", "二", "三", "四", "五", "六"};
		return week[this.nextBirthWeekNum - 1]; // 返回的星期是1-7，所以减一
	}
	
}
