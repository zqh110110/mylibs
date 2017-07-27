package com.kfd.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 字符串操作工具包
 * 
 * @author 朱继洋
 * @QQ 7617812 2013-3-19 version 1.0
 */
public class StringUtils {
	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	public static String getBucketPath(String fullPath,String fileName){	
		try{
//			return fullPath.substring(0, fullPath.lastIndexOf("/")+1);
			if(fullPath!=null && fileName!=null)
			{
				int end = fullPath.lastIndexOf(fileName);
				if (end < 0 && end > fullPath.length()) {
					return fullPath;
				}
				return fullPath.substring(0, end);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd");
		}
	};

	/***
	 * 半角转换为全角 ,实现文字左右对齐
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static SimpleDateFormat date_formate5 = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static SimpleDateFormat date_formate6 = new SimpleDateFormat(
			"yyyyMMdd");

	public static String isDate(Date date) {
		try {
			return date_formate5.format(date);
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater2.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date toDate1(String sdate) {
		try {
			return dateFormater3.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * php时间转java
	 * 
	 * @param string
	 * @return
	 */
	public static String phpdateformat(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}
	
	public static String phpdateformat11(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}
	
	public static String phpdateformat10(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}
	
	public static String phpdateformat3(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}
	public static String phpdateformat2(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}
	public static String phpdateformat8(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}
	public static String phpdateformat9(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}
	public static String phpdateformat4(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}
	public static String phpdateformat5(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}
	public static String phpdateformat6(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}
	public static String phpdateformat7(String string) {
		String string2 = null;
		try {
			Long long1 = Long.parseLong(string + "000");
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			string2 = sdf.format(long1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string2;

	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;

			}
		}
		return b;
	}

	/**
	 * 判断是不是明天
	 * 
	 * @param sdate
	 * @return
	 */
	public static boolean isTomorrow(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		if (time != null) {
			String tomorrowDate = dateFormater2.get().format(date);
			String timeDate = dateFormater2.get().format(time);
			if (tomorrowDate.equals(timeDate)) {
				b = true;

			}
		}
		return b;
	}

	/**
	 * 判断字符串能不能转换成时间
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isTime(String time) {
		boolean b = false;
		try {
			Date date = null;
			date = dateFormater2.get().parse(time);
			b = true;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 验证手机号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCellphone(String str) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(str);
		System.out.println(m.matches() + "---");
		return m.matches();
	}

	/**
	 * 字符非空判断
	 * 
	 * @param value
	 * @return
	 */
	public static String isStrNull(Object value) {
		if (value != null && !value.equals("null") && !"".equals(value)) {
			return value.toString();
		}
		return "";
	}

	public static long copyFile(File f1, File f2) throws Exception {
		long time = new Date().getTime();
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		byte[] buffer = new byte[length];
		while (true) {
			int ins = in.read(buffer);
			if (ins == -1) {
				in.close();
				out.flush();
				out.close();
				return new Date().getTime() - time;
			} else
				out.write(buffer, 0, ins);
		}
	}

	public static Bitmap getLoacalBitmap(String url) {

		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); // /����ת��ΪBitmapͼƬ

		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/**
	 * 获取图片流
	 * 
	 * @param uri
	 *            图片地址
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public static InputStream GetImageByUrl(String uri)
			throws MalformedURLException {
		URL url = new URL(uri);
		URLConnection conn;
		InputStream is;
		try {
			conn = url.openConnection();
			conn.connect();
			is = conn.getInputStream();

			// 或者用如下方法

			// is=(InputStream)url.getContent();
			return is;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取Bitmap
	 * 
	 * 
	 * @param uri
	 *            图片地址
	 * @return
	 */
	public static Bitmap GetBitmapByUrl(String uri) {

		Bitmap bitmap;
		InputStream is;
		try {

			is = GetImageByUrl(uri);

			bitmap = BitmapFactory.decodeStream(is);
			is.close();

			return bitmap;

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * compare_date(时间比较)
	 * 
	 * @param @return 设定文件
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static Boolean compare_date(String DATE1, String DATE2) {

		Boolean result = false;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				result = true;
			} else if (dt1.getTime() < dt2.getTime()) {
				result = false;
			} else {
				result = false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}

	public static Boolean compare_date(String DATE1) {
		Boolean result = false;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = new Date();
			if (dt1.getTime() > dt2.getTime()) {
				result = true;
			} else if (dt1.getTime() < dt2.getTime()) {
				result = false;
			} else {
				result = true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}

}
