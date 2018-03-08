package com.keruiyun.saike.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	/**
	 * 显示时间格式,HH:mm
	 */
	public static final String HOUR_MINUTE_FORMAT = "HH:mm";

	/**
	 * 显示时间格式,HH:mm
	 */
	public static final String HOUR_MINUTE_FORMAT_1 = "HH:mm:ss";

	/**
	 * 显示日期的格式,yyyy-MM
	 */
	public static final String MONTH_FORMAT = "yyyy-MM";

	/**
	 * 显示日期的格式,yyyy-MM-dd
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 显示日期的格式,yyyyMMdd
	 */
	public static final String DATE_FORMAT_1 = "yyyyMMdd";

	/**
	 * 显示日期的格式,yyyy-MM-dd HH:mm:ss
	 */
	public static final String TIMEF_FORMATS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 显示日期的格式,yyyy/MM/dd HH:mm
	 */
	public static final String TIMEF_FORMAT = "yyyy/MM/dd HH:mm";

	/**
	 * 显示日期的格式,yyyy年MM月dd日
	 */
	public static final String DATECH_TIME_FORMAT = "yyyy年MM月dd日";

	/**
	 * 显示日期的格式,yyyy年MM月dd日HH时mm分ss秒
	 */
	public static final String ZHCN_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";

	/**
	 * 显示日期的格式,yyyyMMddHHmmss
	 */
	public static final String TIME_STR_FORMAT = "yyyyMMddHHmmss";
	/**
	 * 显示日期的格式,yyyyMMddHHmmssSSS
	 */
	public static final String TIMESSS_STR_FORMAT = "yyyyMMddHHmmssSSS";
	public static final String TIMESSS_STR_NONTH = "yyyyMM";
	/**
	 * 显示日期的格式,yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static final String TIMESSS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 获得当前时间在十----N分钟后的字符串时间
	 * 
	 * @param n
	 * @return String
	 */
	public static String getTimebyMinAfter(int n) {
		DateFormat dateTimeFormat = new SimpleDateFormat();
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.add(Calendar.MINUTE, n);
		return dateTimeFormat.format(now.getTime());
	}

	/**
	 * 获得当前时间在十--N秒钟后的字符串时间
	 * 
	 * @param n
	 * @return String
	 */
	public static String getTimebySecAfter(int n) {
		DateFormat dateTimeFormat = new SimpleDateFormat();
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.add(Calendar.SECOND, n);
		return dateTimeFormat.format(now.getTime());
	}

	/**
	 * 获取定义的DateFormat格式
	 * 
	 * @param formatStr
	 * @return
	 */
	private static DateFormat getDateFormat(String formatStr) {
		return new SimpleDateFormat(formatStr);
	}

	/**
	 * 将String转换成Date
	 * 
	 * @param date
	 *            日期类型字符串
	 * @param formatStr
	 *            格式化类型
	 * @return
	 * @throws ParseException
	 */
	public static Date strToFormatDate(String date, String formatStr)
			throws ParseException {
		DateFormat df = getDateFormat(formatStr);
		return df.parse(date);
	}

	/**
	 * 将Date转换成formatStr格式的字符串
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateToDateString(Date date, String formatStr) {
		DateFormat df = getDateFormat(formatStr);
		return df.format(date);
	}

	/**
	 * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
	 * 
	 * @param date
	 *            日期
	 * @return String 字符串
	 */
	public static String dateToDateString(Date date) {
		return dateToDateString(date, TIMEF_FORMATS);
	}

	/**
	 * 将Date转换为yyMMddHHmmss的形式
	 * 
	 * @param date
	 * @return 日期的字符串形式,格式：yyMMddHHmmss
	 */
	public static String convertDateToString(Date date) {
		return dateToDateString(date, TIME_STR_FORMAT);
	}

	/**
	 * 时间转为化为字符串 格式为：yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getDateToString(Date date) {
		return dateToDateString(date, TIMESSS_STR_FORMAT);
	}

	/**
	 * 将小时数换算成返回以毫秒为单位的时间
	 * 
	 * @param hours
	 * @return
	 */
	public static long getMicroSec(BigDecimal hours) {
		BigDecimal bd;
		bd = hours.multiply(new BigDecimal(3600 * 1000));
		return bd.longValue();
	}

	/**
	 * 获取今天的日期，格式自定
	 * 
	 * @param pattern
	 *            -设定显示格式
	 * @return String - 返回今天的日期
	 */
	public static String getToday(String pattern) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		DateFormat sdf = getDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getDefault());
		return (sdf.format(now.getTime()));
	}

	/**
	 * 得到系统当前的分钟数,如当前时间是上午12:00,系统当前的分钟数就是12*60
	 * 
	 * @return
	 */
	public static int getCurrentMinutes() {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		int iMin = now.get(Calendar.HOUR_OF_DAY) * 60
				+ now.get(Calendar.MINUTE);
		return iMin;
	}

	/**
	 * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
	 * 
	 * @return 当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
	 */
	public static String getCurZhCNDateTime() {
		return dateToDateString(new Date(), ZHCN_TIME_FORMAT);
	}

	/**
	 * @return 得到本月月份 如09
	 */
	public static String getMonth() {
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH) + 1;
		String monStr = String.valueOf(month);

		// 对于10月份以下的月份,加"0"在前面
		if (month < 10) {
			monStr = "0" + monStr;
		}
		return monStr;
	}

	/**
	 * 根据失效时间判断是否依然有效
	 * 
	 * @param expireTime
	 *            失效时间的字符串形式,格式要求:yyMMddHHmmss
	 * @return true:失效 false:没有失效
	 * @throws ParseException
	 */
	public static boolean isTimeExpired(String expireTime)
			throws ParseException {
		boolean ret = false;

		// SimpleDateFormat不是线程安全的,所以每次调用new一个新的对象

		Date date = new SimpleDateFormat(TIME_STR_FORMAT).parse(expireTime);
		Calendar expire = Calendar.getInstance();
		expire.setTime(date);
		Calendar now = Calendar.getInstance();
		// 将毫秒字段设为0,保持精度一致性

		now.set(Calendar.MILLISECOND, 0);
		if (now.after(expire)) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 根据开始时间和可用时间计算出失效时间
	 * 
	 * @param startTime开始时间
	 * @param availableTime有效时长
	 *            （单位：秒）
	 * @return 失效时间
	 * @throws ParseException
	 */
	public static String getExpireTimeByCalculate(Date startTime,
			int availableTime) {
		Calendar expire = Calendar.getInstance();

		// 设置为开始时间
		expire.setTime(startTime);

		// 开始时间向后有效时长秒得到失效时间
		expire.add(Calendar.SECOND, availableTime);

		// 返回失效时间字符串
		// SimpleDateFormat不是线程安全的,所以每次调用new一个新的对象

		return new SimpleDateFormat(TIME_STR_FORMAT).format(expire.getTime());

	}

	/**
	 * 将yyMMddHHmmss形式的字符串转换成Date的形式
	 * 
	 * @param date
	 *            yyMMddHHmmss形式的日期字符串
	 * @return Date对象
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String date) throws ParseException {
		return new SimpleDateFormat(TIME_STR_FORMAT).parse(date);
	}

	/**
	 * 将yy-MM-dd形式的字符串转换成Date的形式
	 * 
	 * @param date
	 *            yy-MM-dd形式的日期字符串
	 * @return Date对象
	 * @throws ParseException
	 */
	public static Date convertSimpleStringToDate(String date)
			throws ParseException {
		return new SimpleDateFormat(DATE_FORMAT).parse(date);
	}

	/**
	 * 获取昨日日期 返回格式：yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getYesterdayDate() {
		// 获取昨日的日期
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date yesterday = cal.getTime();
		return new SimpleDateFormat(DATE_FORMAT).format(yesterday);
	}

	/**
	 * 根据Calendar对象、字符串日期类型获得字符串日期
	 * 
	 * @param date
	 *            Calendar对象
	 * @param strDatetype
	 *            字符串日期类型（1：XXXX年XX月XX日，2：XX月XX日，3，XXXX年，4：XXXX-XX-XX，5：XX-XX，6：
	 *            XXXX）
	 * @return
	 */
	public static String getStrDate(Calendar calendar, int strDateType) {
		String strDate = "";

		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? "0"
				+ (calendar.get(Calendar.MONTH) + 1) : String.valueOf((calendar
				.get(Calendar.MONTH) + 1));
		String day = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0"
				+ calendar.get(Calendar.DAY_OF_MONTH) : String.valueOf(calendar
				.get(Calendar.DAY_OF_MONTH));

		switch (strDateType) {
		case 1:
			strDate = year + "年" + month + "月" + day + "日";
			break;
		case 2:
			strDate = month + "月" + day + "日";
			break;
		case 3:
			strDate = year + "年";
			break;
		case 4:
			strDate = year + "-" + month + "-" + day;
			break;
		case 5:
			strDate = month + "-" + day;
			break;
		case 6:
			strDate = year;
			break;

		default:
			strDate = year + "-" + month + "-" + day;
			break;
		}

		return strDate;
	}

	/**
	 * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * @param dateOffset
	 *            （向前移正数，向后移负数）
	 * @param type
	 *            指定不同的格式（1：年月日，2：年月日时，3：年月日时分）
	 * @return 时间（java.util.Date），没有时分秒
	 */
	public static Date getOffsetSimpleDate(Date protoDate, int dateOffset,
			int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
				- dateOffset);
		if (type == 1) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
		}
		if (type == 2) {
			cal.set(Calendar.MINUTE, 0);
		}
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 根据原来的时间（Date）获得相对偏移 N 小时的时间（Date）
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * @param offsetHour
	 *            （向前移正数，向后移负数）
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetHourDate(Date protoDate, int offsetHour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)
				- offsetHour);
		return cal.getTime();
	}

	/**
	 * 获得两个日前之间相差的天数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 天数
	 */
	public static int getDayCountBetweenDate(Date startDate, Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		int i = 0;
		while (endCalendar.compareTo(startCalendar) >= 0) {
			startCalendar.set(Calendar.DAY_OF_MONTH,
					startCalendar.get(Calendar.DAY_OF_MONTH) + 1);
			i++;
		}
		return i;
	}

	/**
	 * 获得当前一小时前的时间（例如：当前为09，获得的值为08）
	 * 
	 * @return
	 */
	public static String getHourBeforeOne() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		hour = hour - 1;
		String hourStr = String.valueOf(hour);

		if (hour < 10) {
			hourStr = "0" + hourStr;
		}

		if (hour < 1) {
			hourStr = "23";
		}

		return hourStr;
	}

	/**
	 * 获得当前日期的星期数,需要使用者在使用该方法时为它进行本地化操作
	 * 
	 * @param pTime
	 *            需要判断的时间
	 * @return getDayOfWeek 一周内星期数
	 * @Exception 发生异常
	 */
	public static int getDayOfWeek(String date, String formatStr)
			throws Exception {
		DateFormat format = getDateFormat(formatStr);
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(date));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 根据原来的时间（Date）获得当前周的开始日期
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * @param formatStr
	 *            时间格式
	 * 
	 * @return 时间（java.util.Date），没有时分秒
	 */
	public static String getFirstDayOfWeek(Date protoDate, String formatStr) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.DAY_OF_WEEK,
				cal.getActualMinimum(Calendar.DAY_OF_WEEK));
		return dateToDateString(cal.getTime(), formatStr);
	}

	/**
	 * 根据原来的时间（Date）获得当前周的结束日期
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * @param formatStr
	 *            时间格式
	 * @return 时间（java.util.Date），没有时分秒
	 */
	public static String getLastDayOfWeek(Date protoDate, String formatStr) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.DAY_OF_WEEK,
				cal.getActualMaximum(Calendar.DAY_OF_WEEK));
		return dateToDateString(cal.getTime(), formatStr);
	}

	/**
	 * 根据原来的时间（Date）获得当月的起始日期
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * @return 时间（java.util.Date），没有时分秒
	 */
	public static String getFirstDayOfMonth(Date protoDate, String formatStr) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateToDateString(cal.getTime(), formatStr);
	}

	/**
	 * 根据原来的时间（Date）获得当月的结束日期
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * @return 时间（java.util.Date），没有时分秒
	 */
	public static String getLastDayOfMonth(Date protoDate, String formatStr) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateToDateString(cal.getTime(), formatStr);
	}

	/**
	 * 
	 * 根据原来的时间（Date）获得当前时间是一个星期星期几
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * @return 周几
	 * @author zqzhu
	 */
	public static String getDayOfWeek(Date protoDate) {
		int day = protoDate.getDay();
		switch (day) {
		case 1:
			return "周一";
		case 2:
			return "周二";
		case 3:
			return "周三";
		case 4:
			return "周四";
		case 5:
			return "周五";
		case 6:
			return "周六";
		default:
			return "周日";
		}
	}

}
