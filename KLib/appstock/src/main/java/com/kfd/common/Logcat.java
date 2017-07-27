package com.kfd.common;



import android.util.Log;

/**
 * 定制的android.util.Log类，可以全局控制logcat打印
 * 
 * @author xiaobo.lin
 * @date May 30, 2012 11:45:53 AM
 */
public class Logcat {

	public static final String LOGTAG = "tag";


	public static void v(String msg) {
	
		if (Define.DEBUG)
			android.util.Log.v(LOGTAG, msg);
	}
	
	public static void v(String tag, String msg) {
		if (Define.DEBUG)
			android.util.Log.v(tag, msg);
	}

	public static void v(String msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.v(LOGTAG, msg, tr);
	}

	public static void e(String msg) {
		if (Define.DEBUG)
			android.util.Log.e(LOGTAG, msg);
	}
	
	public static void e(String tag,String msg) {
		if (Define.DEBUG)
			android.util.Log.e(tag, msg);
	}

	public static void e(String msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.e(LOGTAG, msg, tr);
	}

	/**
	 * 仅限于调试用，默认TAG为{@link #LOGTAG}
	 * 
	 * @param msg
	 *            调试信息
	 */
	public static void d(byte msg) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Byte.toString(msg));
	}

	public static void d(short msg) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Short.toString(msg));
	}

	public static void d(int msg) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Integer.toString(msg));
	}

	public static void d(long msg) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Long.toString(msg));
	}

	public static void d(float msg) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Float.toString(msg));
	}

	public static void d(double msg) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Double.toString(msg));
	}

	public static void d(char msg) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Character.toString(msg));
	}

	public static void d(boolean msg) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Boolean.toString(msg));
	}

	public static void d(Object msg) {
		if (msg == null) {
			if (Define.DEBUG)
				android.util.Log.d(LOGTAG, "null");
		} else {
			if (Define.DEBUG)
				android.util.Log.d(LOGTAG, msg.toString());
		}
	}

	/**
	 * 仅限于调试用, 可以使用自定义TAG
	 * 
	 * @param tag
	 *            tag标签
	 * @param msg
	 *            调试信息
	 */
	public static void d(String tag, String msg) {
		if (Define.DEBUG)
			android.util.Log.d(tag, msg);
	}

	public static void d(String tag, byte msg) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Byte.toString(msg));
	}

	public static void d(String tag, short msg) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Short.toString(msg));
	}

	public static void d(String tag, int msg) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Integer.toString(msg));
	}

	public static void d(String tag, long msg) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Long.toString(msg));
	}

	public static void d(String tag, float msg) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Float.toString(msg));
	}

	public static void d(String tag, double msg) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Double.toString(msg));
	}

	public static void d(String tag, char msg) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Character.toString(msg));
	}

	public static void d(String tag, boolean msg) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Boolean.toString(msg));
	}

	public static void d(String tag, Object msg) {
		if (Define.DEBUG)
			android.util.Log.d(tag, msg.toString());
	}

	/**
	 * 仅限于调试用，可以使用自定义TAG
	 * 
	 * @param tag
	 *            tag标签
	 * @param msg
	 *            调试信息
	 * @param tr
	 *            一个异常可以打印日志出来
	 */
	public static void d(String tag, String msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(tag, msg, tr);
	}

	public static void d(String tag, byte msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Byte.toString(msg), tr);
	}

	public static void d(String tag, short msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Short.toString(msg), tr);
	}

	public static void d(String tag, int msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Integer.toString(msg), tr);
	}

	public static void d(String tag, long msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Long.toString(msg), tr);
	}

	public static void d(String tag, float msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Float.toString(msg), tr);
	}

	public static void d(String tag, double msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Double.toString(msg), tr);
	}

	public static void d(String tag, char msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Character.toString(msg), tr);
	}

	public static void d(String tag, boolean msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(tag, Boolean.toString(msg), tr);
	}

	public static void d(String tag, Object msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(tag, msg.toString(), tr);
	}

	/**
	 * 仅限于调试用，默认TAG为{@link #LOGTAG}
	 * 
	 * @param msg
	 *            调试信息
	 * @param tr
	 *            一个异常可以打印日志出来
	 */
	public static void d(String msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, msg, tr);
	}

	public static void d(byte msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Byte.toString(msg), tr);
	}

	public static void d(short msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Short.toString(msg), tr);
	}

	public static void d(int msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Integer.toString(msg), tr);
	}

	public static void d(long msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Long.toString(msg), tr);
	}

	public static void d(float msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Float.toString(msg), tr);
	}

	public static void d(double msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Double.toString(msg), tr);
	}

	public static void d(char msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Character.toString(msg), tr);
	}

	public static void d(boolean msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, Boolean.toString(msg), tr);
	}

	public static void d(Object msg, Throwable tr) {
		if (Define.DEBUG)
			android.util.Log.d(LOGTAG, msg.toString(), tr);
	}
	
	// 类名：包括包名
	private static String className;
	// 方法名
	private static String methodName;
	// 打印的行号
	private static int lineNumber;
	// 仅仅是类名
	private static String fileName;

	/**
	 * be used for ： 获取方法名、类名、行号
	 * 
	 * @author zhongwr
	 * @2014-11-23
	 */
	private static void initData(StackTraceElement[] e) {
		className = e[1].getClassName();
		methodName = e[1].getMethodName();
		lineNumber = e[1].getLineNumber();
		fileName = e[1].getFileName();
	}

	/**
	 * 
	 * be used for：日志前缀
	 * 
	 * @author zhongwr
	 * @2014-11-23
	 * @return
	 */
	private static String getMsgPrefix() {
		return new StringBuilder().append("[").append(fileName).append(" : ").append(methodName).append("()")
				.append(" : ").append(lineNumber).append("]  ").toString();
	}

	/**
	 * @description Tag是类名
	 * @author zhongwr
	 * @update 2015年5月5日 下午12:29:39
	 */
	public static void vLog(String msg) {
		if (Define.DEBUG) {
			initData(new Throwable().getStackTrace());
			Log.v(className, getMsgPrefix() + msg);
		}
	}

	/**
	 * @description Tag是类名
	 * @author zhongwr
	 * @update 2015年5月5日 下午12:29:39
	 */
	public static void dLog(String msg) {
		if (Define.DEBUG) {
			initData(new Throwable().getStackTrace());
			Log.d(className, getMsgPrefix() + msg);
		}
	}

	/**
	 * 
	 * @description Tag是类名
	 * @author zhongwr
	 * @update 2015年5月5日 下午12:29:39
	 */
	public static void iLog(String msg) {
		if (Define.DEBUG) {
			initData(new Throwable().getStackTrace());
			Log.i(className, getMsgPrefix() + msg);
		}
	}

	/**
	 * 
	 * @description Tag是类名
	 * @author zhongwr
	 * @update 2015年5月5日 下午12:29:39
	 */
	public static void wLog(String msg) {
		if (Define.DEBUG) {
			initData(new Throwable().getStackTrace());
			Log.w(className, getMsgPrefix() + msg);
		}
	}
}
