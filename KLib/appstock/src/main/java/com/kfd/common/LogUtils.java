package com.kfd.common;

import android.util.Log;

public class LogUtils {
	public static final boolean LOGVV = true;

	public static void e(String paramString, Exception paramException) {
		Log.e(paramString, "", paramException);
	}

	public static void e(String paramString1, String paramString2,
			Throwable paramThrowable) {
		Log.e(paramString1, paramString2, paramThrowable);
	}

	public static void log(String paramString, Object paramObject) {
		Log.v(paramString, paramObject.toString());
	}

	public static void print(Object paramObject) {
		Log.v("test",paramObject.toString());
	}

	public static void v( String paramString,Object paramObject) {
		Log.v("test", paramObject.toString());
	}
}
