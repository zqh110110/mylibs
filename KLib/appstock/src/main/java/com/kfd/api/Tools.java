package com.kfd.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kfd.activityfour.BaseActivity;
import com.kfd.activityfour.R;
import com.kfd.common.Define;
import com.kfd.common.Logcat;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.provider.MediaStore.MediaColumns;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class Tools {
	
	private static final String TAG = "Tools";
	
	public static final String sdCardNotMounted = "没有SD卡";
	public static final String sdCardIsFull = "SD卡已满";
	public static  CookieStore cookieStore;
	private static float density = 2.0f;
	private static String versionName;
	private static int versonCode;
	private static String packageName = "com.wangzhi.MaMaHelp";
	
	//计算图片的缩放值
		 public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
		     final int height = options.outHeight;
		     final int width = options.outWidth;
		     int inSampleSize = 1;

		     if (height > reqHeight || width > reqWidth) {
		              final int heightRatio = Math.round((float) height/ (float) reqHeight);
		              final int widthRatio = Math.round((float) width / (float) reqWidth);
		              inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		     }
		         return inSampleSize;
		 }
		 
		// 根据路径获得图片并压缩，返回bitmap用于显示
		 public static Bitmap getSmallBitmap(String filePath) {
		         final BitmapFactory.Options options = new BitmapFactory.Options();
		         options.inJustDecodeBounds = true;
		         BitmapFactory.decodeFile(filePath, options);

		         // Calculate inSampleSize
		     options.inSampleSize = calculateInSampleSize(options, 400, 400);

		         // Decode bitmap with inSampleSize set
		     options.inJustDecodeBounds = false;

		     return BitmapFactory.decodeFile(filePath, options);
		     }
	/**
     * 读取照片exif信息中的旋转角度
     * 
     * @param path
     *            照片路径
     * @return角度  获取从相册中选中图片的角度
     */
    public static int readPictureDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
            }
        } catch (Exception e) {
        }
        return degree;
    }
    /***
     * 图片旋转
     * @param b 图片
     * @param degrees 旋转角度
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap b, int degrees) {    
        if (degrees != 0 && b != null) {    
            Matrix m = new Matrix();    
            m.setRotate(degrees,    
                    (float) b.getWidth() / 2, (float) b.getHeight() / 2);    
            try {    
                Bitmap b2 = Bitmap.createBitmap(    
                        b, 0, 0, b.getWidth(), b.getHeight(), m, true);    
                if (b != b2) {    
                    b.recycle();  //Bitmap操作完应该显示的释放    
                    b = b2;    
                }    
            } catch (OutOfMemoryError ex) {    
                // 建议大家如何出现了内存不足异常，最好return 原始的bitmap对象。.    
            }    
        }    
        return b;    
    }  
    
public static Bitmap getSmallBitmap(String filePath, int _width ,int _height) {  
        
        final BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = false;  
        Bitmap icon = BitmapFactory.decodeFile(filePath, options);  
        if(icon == null){  
            return  null;  
        }  
        int degree = readPictureDegree(filePath);  
        icon = rotateBitmap(icon,degree) ; 
        int realWidth = icon.getWidth();
        int realHeight = icon.getHeight();
        if(_width<=0){
        	_width = realWidth;
        }
        if(_height<=0){
        	_height = realHeight;
        }
        float scaleRate = (float)_width/realWidth < (float)_height/realHeight ? (float)_width/realWidth : (float)_height/realHeight;
        Matrix matrix = new Matrix();
        matrix.postScale( scaleRate,  scaleRate);
        Bitmap bm = Bitmap.createBitmap(icon, 0, 0, realWidth, realHeight, matrix, true);

        ByteArrayOutputStream baos = null ;  
        try{  
            baos = new ByteArrayOutputStream();  
            bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);  
              
        }finally{  
            try {  
                if(baos != null)  
                    baos.close() ;  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return bm ;  
  
    }  
	// 保存cookie
	public static void saveCookie(Context context, CookieStore cookiestore) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		try {
			JSONObject jsonObject_1 = new JSONObject();
			JSONArray json = new JSONArray();
			for (int i = 0; i < cookiestore.getCookies().size(); i++) {
				Cookie cookie = cookiestore.getCookies().get(i);
				String cookieName = cookie.getName();
				String cookieValue = cookie.getValue();
				String cookieDomain = cookie.getDomain();
				Logcat.v(cookieName + "cookieName" + cookieValue
						+ "cookieValue" + cookieDomain + "cookieDomain");
				JSONObject local_jsonObject = new JSONObject();
				local_jsonObject.put("cookieName", cookieName);
				local_jsonObject.put("cookieValue", cookieValue);
				local_jsonObject.put("cookieDomain", cookieDomain);
				json.put(local_jsonObject);
			}
			jsonObject_1.put("userInfo", json);
			Editor editor = sp.edit();
			editor.putString("userInfo", jsonObject_1.toString());
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 清理cookie
	public static void clearCookie(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putString("userInfo", "{\"userInfo\":[]}");
		editor.commit();
	}

	public static CookieStore getCookie(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		try {
			String userInfo = sp.getString("userInfo", "{\"userInfo\":[]}");
			JSONArray rawJson = (new JSONObject(userInfo))
					.getJSONArray("userInfo");
			if (rawJson.length() == 0) {
				return null;
			} else {
				CookieStore cookieStore = new BasicCookieStore();
				for (int i = 0; i < rawJson.length(); i++) {
					JSONObject object = rawJson.getJSONObject(i);
					String cookieName = object.getString("cookieName");
					String cookieValue = object.optString("cookieValue", "");
					String cookieDomain = object.optString("cookieDomain", "");
					BasicClientCookie cookie = new BasicClientCookie(
							cookieName, cookieValue);
					cookie.setVersion(0);
					cookie.setDomain(cookieDomain);
					cookie.setPath("/");
					Logcat.v("getCookie=" + cookieName + "cookieName"
							+ cookieValue + "cookieValue" + cookieDomain
							+ "cookieDomain");
					cookieStore.addCookie(cookie);
				}

				return cookieStore;
			}
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * @description 判断当前是否可以联网 context 是null 返回false!
	 * @author <a href="mailto:vfishv@gmail.com">张清田</a>
	 * @update 2014年8月30日 下午2:55:46
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context == null) {
			return false;
		}
		boolean status = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm != null) {
				NetworkInfo netInfo = cm.getActiveNetworkInfo();
				if (netInfo != null) {
					status = netInfo.isAvailable() && netInfo.isConnected();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return status;
	}
	
	private static boolean isVisibleChar(char c)
	{
		final char BACKSLASH_R = '\r';
		final char BACKSLASH_N = '\n';
		final char QUANJIAO_SPACE = '　';
		final char ASCII_SPACE = ' ';
		final char TABLE = '\t';
		return (c != ASCII_SPACE) && (c != QUANJIAO_SPACE) && (c != BACKSLASH_R) && (c != BACKSLASH_N) && (c != TABLE);
	}

	/**
	 * 
	 * 检查输入的字符是否有前后空格,半角全角都检查
	 * 全角空格转换，开头结尾的全部除去，内部的全部保留
	 * 
	 */
	public static String quanjiaoSpaceTrim(String checkString)
	{
		
		final char[] src = checkString.trim().toCharArray();
		
		StringBuffer stringBuffer = new StringBuffer(20);

		int start = -1;
		int end = 0;

		for (int i = 0; i < src.length; i++)
		{
			if (isVisibleChar(src[i]))
			{
				start = i;
				break;
			}
		}

		if (start == -1)
		{
			return "";
		}

		for (int i = src.length - 1; i >= start; i--)
		{

			if (isVisibleChar(src[i]))
			{
				end = i;
				break;
			}
		}

		for (int i = start; i <= end; i++)
		{
			stringBuffer.append(src[i]);
		}

		return stringBuffer.toString();
	}
	
	public static final Point screenSize = new Point();
	
	/**
	 * 
	 * @description 获取屏幕宽高
	 * @author <a href="mailto:vfishv@gmail.com">张清田</a>
	 * @update 2014年8月30日 下午4:51:48
	 */
	public static Point getScreenSize(Context ctt) {
		if (ctt == null) {
			return screenSize;
		}
		WindowManager wm = (WindowManager) ctt.getSystemService(Context.WINDOW_SERVICE);
		if (wm != null) {
			DisplayMetrics mDisplayMetrics = new DisplayMetrics();
			Display diplay = wm.getDefaultDisplay();
			if (diplay != null)
			{
//				if (Build.VERSION.SDK_INT > 16)// Build.VERSION_CODES.JELLY_BEAN
//				{
//					diplay.getRealMetrics(mDisplayMetrics);
//				}
//				else
//				{
					diplay.getMetrics(mDisplayMetrics);
//				}
				int W = mDisplayMetrics.widthPixels;
				int H = mDisplayMetrics.heightPixels;
				if (W * H > 0 && (W > screenSize.x || H > screenSize.y)) {
					screenSize.set(W, H);
					//Log.i(TAG, "screen size:" + screenSize.toString());
				}
			}
		}
		return screenSize;
	}
	
	public static void initDensity(Context ctt) {
		if (ctt != null) {
			WindowManager wm = (WindowManager) ctt.getSystemService(Context.WINDOW_SERVICE);
			if (wm != null) {
				DisplayMetrics mDisplayMetrics = new DisplayMetrics();
				Display diplay = wm.getDefaultDisplay();
				if (diplay != null) {
					diplay.getMetrics(mDisplayMetrics);
					density = mDisplayMetrics.density;
					Logcat.v(TAG, "density:" + density);
				}
			}
		}
	}

	/**
	 * 检查Intent是否可用
	 * @author <a href="mailto:vfishv@gmail.com">张清田</a>
	 * @param context
	 * @param intent
	 * @return
	 */
	public static boolean isIntentAvailable(Context context, Intent intent) {
		if (context != null && intent != null) {
			final PackageManager packageManager = context.getPackageManager();
			List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
			return list != null && list.size() > 0;
		}
		return false;
	}
	
	public static void viewUri(final Context context, final String local_url)
	{
		try
		{
			Uri uri = Uri.parse(local_url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			//if (Tools.isIntentAvailable(context, intent))
			//{
				context.startActivity(intent);
			//}
		}
		catch (Exception e)
		{
			// TODOAuto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @description 返回字符串GBK字节数
	 * @update 2014年10月21日 下午12:29:44
	 */
	public static int getGBKStringBytesLenth(String str)
	{
		int len = 0;
		if (str == null)
		{
			return len;
		}
		len = str.length();//默认值
		//len = str.getBytes().length;//这个长度长
		String charsetName = "GBK";
		if (Charset.isSupported(charsetName))
		{
			try
			{
				len = str.getBytes(charsetName).length;
			}
			catch (UnsupportedEncodingException e)
			{
				// TODOAuto-generated catch block
				e.printStackTrace();
			}
		}
		return len;
	}
	
	/**
	 * 调用浏览器打开一个url
	 * @author <a href="mailto:vfishv@gmail.com">张清田</a>
	 * @update 2014年9月2日 下午8:00:44
	 */
	public static void openUrlwithBrowsable(Context context, String url) {
		if (url != null) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	        intent.addCategory(Intent.CATEGORY_BROWSABLE);
	        if(isIntentAvailable(context, intent))
	        {
	        	context.startActivity(intent);
	        }
		}
	}
	
	/**
	 * 
	 * @description 如果是intel x86 的cpu返回 true
	 * @author 张清田
	 * @update 2014年9月19日 下午2:18:04
	 */
	public static boolean isIntel() {
		return "i686".equalsIgnoreCase(getCpuArch());
	}
	
	public static boolean isMips() {
		return "mips".equalsIgnoreCase(getCpuArch());
	}
	
	
	public static String getCpuArch()
	{
		String arch = "";
		try {
			arch = System.getProperty("os.arch");
		} catch (Exception e) {
			// TODOAuto-generated catch block
			e.printStackTrace();
		}
		Log.e(TAG, arch);
		return arch;
	}
	
	public static void showLongToast(final Activity activity, final String str) {
		if (activity != null)
		{
			try
			{
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						Toast.makeText(activity, str, Toast.LENGTH_LONG).show();//3.5s
					}
				});
			}
			catch (Exception e)
			{
				// TODOAuto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void showLongToast(final Activity activity, final int strResId) {
		if (activity != null)
		{
			try
			{
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						Toast.makeText(activity, strResId, Toast.LENGTH_SHORT).show();//2s
					}
				});
			}
			catch (Exception e)
			{
				// TODOAuto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void showShortToast(final Activity activity, final String str)
	{
		if (activity != null)
		{
			try
			{
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
					}
				});
			}
			catch (Exception e)
			{
				// TODOAuto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void showShortToast(final Activity activity, final int strResId)
	{
		if (activity != null)
		{
			try
			{
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						Toast.makeText(activity, strResId, Toast.LENGTH_SHORT).show();
					}
				});
			}
			catch (Exception e)
			{
				// TODOAuto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static boolean isFrontActivity(Activity context)
	{
		boolean result = false;
		android.app.ActivityManager am = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(2).get(0).topActivity;
		if (cn != null)
		{
			if (context.getClass().getName().equals(cn.getClassName()))
			{
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 一个按钮的对话框
	 * @author <a href="mailto:vfishv@gmail.com">张清田</a>
	 * @param title
	 * @param msg
	 * @param iconResId
	 * @param okListener
	 */
	public static void showOneButtonDialog(Context context, String title, String msg, int postBtnRes, DialogInterface.OnClickListener okListener)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (!TextUtils.isEmpty(title))
		{
			builder.setTitle(title);
		}
		AlertDialog dialog = builder.setMessage(msg)
		.setNegativeButton(postBtnRes, okListener).create();
		dialog.show();
		
		dialogTitleLineColor(dialog, Color.parseColor("#FF6F84"));
	}
	
	/**
	 * 确认对话框
	 * @author <a href="mailto:vfishv@gmail.com">张清田</a>
	 * @param title
	 * @param msg
	 * @param iconResId
	 * @param okListener
	 */
	public static void showConfirmDialog(Context context, String title, String msg, int postBtnRes, DialogInterface.OnClickListener okListener)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (!TextUtils.isEmpty(title))
		{
			builder.setTitle(title);
		}
		AlertDialog dialog = builder.setMessage(msg)
		.setPositiveButton(postBtnRes, okListener)
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			}).show();
		
		dialogTitleLineColor(dialog, Color.parseColor("#FF6F84"));
	}
	
	public static final void dialogTitleLineColor(AlertDialog dialog, int color)
	{
		try
		{
			Context context = dialog.getContext();
			int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
			View divider = dialog.findViewById(divierId);
			if (divider != null)
			{
				divider.setBackgroundColor(color);
			}
		}
		catch (Exception e)
		{
			// TODOAuto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void showConfirmDialog(Context context, String title, String msg, String postBtnRes, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener)
	{
		AlertDialog dialog = new AlertDialog.Builder(context)
		.setTitle(title)
		//.setIcon(iconResId)
		.setMessage(msg)
		.setPositiveButton(postBtnRes, okListener)
		.setNegativeButton("取消", cancelListener)
		.show();
		dialogTitleLineColor(dialog, Color.parseColor("#FF6F84"));
	}
	
	/**
	 * 让系统扫描图片
	 * @author <a href="mailto:vfishv@gmail.com">张清田</a>
	 * @author xiaobo.lin
	 * @update 2014年9月9日 下午2:53:32
	 */
	public static void notifyScanFile(final Context context,Uri uri)
	{
		if (context == null) {
			return;
		}
		if (Build.VERSION.SDK_INT < 19) {
			Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, uri);
			//if(isIntentAvailable(context, intent))
			{
				context.sendBroadcast(intent);
			}
		}
		else
		{
			try
			{
				/*
				try
				{
					File file = new File(uri.getPath());
				}
				catch (Exception e)
				{
					// TODOAuto-generated catch block
					e.printStackTrace();
				}
				*/
				//Log.e("uri", uri + ":");
				MediaScannerConnection.scanFile(context, new String[] { uri.toString() }, null, new MediaScannerConnection.OnScanCompletedListener()
				{
					public void onScanCompleted(String path, Uri uri)
					{
						//Log.e("ExternalStorage", "Scanned " + path + ":");
						//Log.e("ExternalStorage", "-> uri=" + uri);
					}
				});
			}
			catch (Exception e)
			{
				// TODOAuto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Return date in specified format.
	 * @param milliSeconds Date in milliseconds
	 * @param dateFormat Date format 
	 * @return String representing date in specified format
	 */
	public static String getDate(long milliSeconds, String dateFormat) {
		String date = "";
		try {
			// Create a DateFormatter object for displaying date in specified format.
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			// Create a calendar object that will convert the date and time value in milliseconds to date.
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(milliSeconds);
			date =  formatter.format(calendar.getTime());
		} catch (Exception e) {
			// TODOAuto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	
	/**
	 * 秒转时分秒
	 * @param second
	 * @return
	 */
	public static String cal(long milliSeconds) {
		if(milliSeconds<0)
		{
			return "";
		}
		
		final int MINUTE = 60;// 60 seconds
		final int HOUR = 3600;// 60*60 seconds
		final int DAY = 3600 * 24;// 24*60*60 seconds
		
		long second = milliSeconds/1000;
		long d = 0;//天
		long h = 0;//小时
		long m = 0;//分
		long s = 0;//秒

		if (second >= DAY)// 大于一天
		{
			d = second / DAY;
			second = second % DAY;
		}

		if (second >= HOUR)// 大于一小时
		{
			h = second / HOUR;
			second = second % HOUR;
		}

		if (second >= MINUTE)// 大于一分
		{
			m = second / MINUTE;
		}
		
		s = second % MINUTE;
		
		StringBuffer sb = new StringBuffer();
		if (d > 0) {
			sb.append(d).append("天");
		}
		if (d + h > 0) {
			sb.append(h).append("小时");
		}
		if (d + h + m > 0) {
			sb.append(m).append("分");
		}
		if (d + h + m + s > 0) {
			sb.append(s).append("秒");
		}

		return sb.toString();
	}
	
	private static String IMEI;

	// 获取IMEI码
	public static String getIMEI(Context context)
	{
		if (!TextUtils.isEmpty(IMEI))
		{
			return IMEI;
		}
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		IMEI = telephonyManager.getDeviceId();
		return IMEI;
	}

	// 获取本机mac地址
	public static String getLocalMacAddress(Context context) {
		String macAddress = null;
		WifiManager wifiMgr = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info) {
			macAddress = info.getMacAddress();
		}
		return macAddress;
	}

	public static void getFitBitmapByWidth(String path, String result_file,
			int width) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, opt);
		if (opt.outWidth >= width) {
			int s = 1;
			int result_width = opt.outWidth;
			while (result_width >= width) {
				s *= 2;
				result_width = result_width / 2;
			}
			opt.inSampleSize = s;
			opt.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(path, opt);
		} else {
			opt.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(path, opt);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(result_file);
			if (null != fos) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String removeExt(String filename) {
		if (filename.lastIndexOf(".") >= 0) {
			return filename.substring(0, filename.lastIndexOf("."));
		} else {
			return filename;
		}
	}

	public static double distanceByLngLat(double lng1, double lat1,
			double lng2, double lat2) {
		double radLat1 = lat1 * Math.PI / 180;
		double radLat2 = lat2 * Math.PI / 180;
		double a = radLat1 - radLat2;
		double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	public static String getVersionName() {
		return versionName;
	}
	
	public static String getVersionName(Context context) {
		if (TextUtils.isEmpty(versionName) && context != null) {
			versionName = getAppVersionName(context);
		}
		return versionName;
	}

	//获取应用名
//		public static String getAppName(Context context){
//			
//			if(context ==null){
//				return "";
//			}
//			try {
//				PackageInfo	pkg = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//				return pkg.applicationInfo.loadLabel(context.getPackageManager()).toString();  
//			} catch (NameNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
//	        
//			return "";
////			String appName = pkg.applicationInfo.loadLabel(activity.getPackageManager()).toString();  
//		}
		//获取本地IP
	    public static String getLocalIpAddress() {  
	           try {  
	               for (Enumeration<NetworkInterface> en = NetworkInterface  
	                               .getNetworkInterfaces(); en.hasMoreElements();) {  
	                           NetworkInterface intf = en.nextElement();  
	                          for (Enumeration<InetAddress> enumIpAddr = intf  
	                                   .getInetAddresses(); enumIpAddr.hasMoreElements();) {  
	                               InetAddress inetAddress = enumIpAddr.nextElement();  
	                               if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {  
	                               return inetAddress.getHostAddress().toString();  
	                               }  
	                          }  
	                       }  
	                   } catch (SocketException ex) {  
	                       Log.e("IpAddress", ex.toString());  
	                   }  
	                return "";  
	   } 
		
	
	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		if(context==null)
		{
			return versionName;
		}
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}
	
	public static int getAppVersionCode(Context context)
	{
		if (context != null)
		{
			try
			{
				// ---get the package info---
				PackageManager pm = context.getPackageManager();
				PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
				versonCode = pi.versionCode;
				return versonCode;
			}
			catch (Exception e)
			{
				Log.e("VersionInfo", "Exception", e);
			}
		}
		return versonCode;
	}
	
	public static String getAppPackageName(Context context)
	{
		if (context != null)
		{
			try
			{
				// ---get the package info---
				PackageManager pm = context.getPackageManager();
				PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
				packageName = pi.packageName;
				return packageName;
			}
			catch (Exception e)
			{
				Log.e("VersionInfo", "Exception", e);
			}
		}
		return packageName;
	}
	
	public static void initAppVersionName(Context context) {
		getVersionName(context);
	}

	// 获取手机型号
	public static String getPhoneType() {
		String model = Build.MODEL;
		return model;
	}

	// 获取操作系统版本
	public static String getPhoneOSVersion() {
		String model = Build.VERSION.RELEASE;
		return model;
	}

	// 获取设备ID
	public static String getDeviceID(Context context) {
		return Secure
				.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	// 显示键盘
	public static void showInputBoard(Activity activity)
	{
		// 显示键盘输入
		IBinder iBinder = null;
		if (activity != null)
		{
			View view = activity.getCurrentFocus();
			if (view != null)
			{
				iBinder = view.getWindowToken();
			}
		}
		if (iBinder != null)
		{
			InputMethodManager imm = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
			if (imm != null)
			{
				imm.showSoftInputFromInputMethod(iBinder, InputMethodManager.SHOW_FORCED);
			}
		}
	}
	
	// 隐藏键盘
	public static void hideInputBoard(Activity activity)
	{
		// 隐藏键盘输入
		IBinder iBinder = null;
		if (activity != null)
		{
			View view = activity.getCurrentFocus();
			if (view != null)
			{
				iBinder = view.getWindowToken();
			}
		}
		if (iBinder != null)
		{
			InputMethodManager imm = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
			if (imm != null)
			{
				imm.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	/**
	 * @description 隐藏软键盘
	 * @author xiaobo.lin
	 * @update 2014年9月15日 下午12:14:50
	 */
	public static void hideSoftInputFromWindow(Context context,View view)
	{
		IBinder iBinder = null;
		if(view!=null)
		{
			iBinder = view.getWindowToken();
		}
		if (iBinder != null && context != null) {
			try {
				InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); 
				imm.hideSoftInputFromWindow(iBinder, InputMethodManager.RESULT_UNCHANGED_SHOWN);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, e.toString());
			}
		}
		
	}

	public static boolean checkEmail(String email) {
		Pattern pattern = Pattern
				.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	static Bitmap getFitBitmap(String path) {
		final int destWidth = 40;
		final int destHeight = 40;
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, opt);

		if (opt.outWidth < opt.outHeight) {
			if (opt.outWidth >= destWidth) {
				opt.inSampleSize = opt.outWidth / destWidth;
				opt.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeFile(path, opt);
			} else {
				opt.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeFile(path, opt);
			}
		} else {
			if (opt.outHeight >= destHeight) {
				opt.inSampleSize = opt.outHeight / destHeight;
				opt.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeFile(path, opt);
			} else {
				opt.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeFile(path, opt);
			}
		}
		return bitmap;
	}

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

	public static int getFitSample(int width, int height, int destWidth,
			int destHeight) {
		int sample = 1;
		while (width > destWidth) {
			if (width < destWidth + 200) {
				break;
			}
			sample = sample * 2;
			width = width / sample;
			height = height / sample;
		}

		while (height > destHeight) {
			if (height < destHeight + 200) {
				break;
			}
			sample = sample * 2;
			width = width / sample;
			height = height / sample;
		}
		return sample;
	}

	// 小图，提供给回复界面和分享界面
	public static int getFitSample1(int width, int height, int destWidth,
			int destHeight) {
		int sample = 1;
		while (width > destWidth) {
			if (width < destWidth + 50) {
				break;
			}
			sample = sample * 2;
			width = width / sample;
			height = height / sample;
		}

		while (height > destHeight) {
			if (height < destHeight + 50) {
				break;
			}
			sample = sample * 2;
			width = width / sample;
			height = height / sample;
		}
		return sample;
	}

	public static int getFitSample(int width, int destWidth) {
		int sample = 1;
		while (width > destWidth) {
			sample *= 2;
			width = width / sample;
		}

		return sample;
	}

	public static void getFitBitmapByWidth(String path, int width) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, opt);
		if (opt.outWidth >= width) {
			int s = 1;
			int result_width = opt.outWidth;
			while (result_width >= 800) {
				s *= 2;
				result_width = result_width / 2;
			}
			opt.inSampleSize = s;
			opt.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(path, opt);
		} else {
			opt.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(path, opt);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			if (null != fos) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public static String encode(String s) {
		try {
			byte[] s_byte = s.getBytes();
			String encrypt_key = MD5.md5("wzkj205");
			int ctr = 0;
			String tmp = "";
			int string_len = s.length();
			int encrypt_key_len = encrypt_key.length();
			byte[] encrypt_key_byte = encrypt_key.getBytes();
			for (int i = 0; i < string_len; i++) {
				ctr = ctr == encrypt_key_len ? 0 : ctr; // 三元运算
				char c = (char) (s_byte[i] ^ encrypt_key_byte[ctr++]);
				tmp += c;
			}
			tmp = android.util.Base64.encodeToString(tmp.getBytes(),
					Base64.DEFAULT); // 使用 MIME
										// base64
										// 对数据进行编码
			return tmp.replace("=", "");
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	
	

	public static String getRealPathFromURI(Activity act, Uri contentUri) {
		String[] proj = { MediaColumns.DATA };
		Cursor cursor = act.managedQuery(contentUri, proj, // Which columns to
															// return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	static boolean isHaveCamera() {
		try {
			Camera mCameraDevice = android.hardware.Camera.open();
			if (mCameraDevice != null) {
				mCameraDevice.release();
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			return false;
		}

	}

	// �ж�gps�Ƿ���
	public static boolean isGPSOpen(Context context) {
		LocationManager alm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			return true;
		} else {
			return false;
		}
	}

	public static HttpClient initHttp() {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,
				10000);
		// ��ʱ����
		client.getParams().setIntParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		// ���ӳ�ʱ
		return client;
	}



	// 根据经纬度获取省市
	public static String getPlaceByJW(Context context, Double j, Double w) {
		Geocoder gc = new Geocoder(context);
		List<Address> addresses = null;
		try {
			addresses = gc.getFromLocation(w, j, 1);
			if (addresses.size() > 0) {
				return addresses.get(0).getAdminArea() + ","
						+ addresses.get(0).getLocality();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isSdCardMounted() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isSDCardFull() {
		File path = Environment.getExternalStorageDirectory();
		StatFs statFs = new StatFs(path.getPath());
		long blockSize = statFs.getBlockSize();
		long totalBlocks = statFs.getBlockCount();
		long availableBlocks = statFs.getAvailableBlocks();
		if (availableBlocks * blockSize == 0) {
			return true;
		}
		return false;
	}
	
	

	public static void copyBitmapToSD(final Context context,final String filename,
			final Bitmap bitmap) {
		
			
				try {
					File outFile = new File(filename);
					if (outFile.exists())
						return;
					
					if (bitmap!=null) {
						
						InputStream in = Bitmap2IS(bitmap);
						OutputStream out = new FileOutputStream(outFile);
						
						// Transfer bytes from in to out
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						in.close();
						out.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	
	
	}
	

        
        /**  
         *   
         * @param fromFile 被复制的文件  
         * @param toFile 复制的目录文件  
         * @param rewrite 是否重新创建文件  
         *   
         * <p>文件的复制操作方法  
         */  
        public static void copyfile(File fromFile, File toFile,Boolean rewrite ){  
              
            if(!fromFile.exists()){  
                return;  
            }  
              
            if(!fromFile.isFile()){  
                return;  
            }  
            if(!fromFile.canRead()){  
                return;  
            }  
            if(!toFile.getParentFile().exists()){  
                toFile.getParentFile().mkdirs();  
            }  
            if(toFile.exists() && rewrite){  
                toFile.delete();  
            }  

            try {  
                FileInputStream fosfrom = new FileInputStream(fromFile);  
                FileOutputStream fosto = new FileOutputStream(toFile);  
                  
                byte[] bt = new byte[1024];  
                int c;  
                while((c=fosfrom.read(bt)) > 0){  
                    fosto.write(bt,0,c);  
                }  
                //关闭输入、输出流  
                fosfrom.close();  
                fosto.close();  
                  
                  
            } catch (FileNotFoundException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
              
        }  
      
 
	
	  private static  InputStream  Bitmap2IS(Bitmap bm){  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);  
	            InputStream sbs = new ByteArrayInputStream(baos.toByteArray());    
	            return sbs;  
	        }
	public static int formatNowTime(Context context) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(sdf.format(date));
	}

	public static String formatTimeStampString(Context context, long when,
			boolean fullFormat) {
		// when是时间戳，单位是秒，直接创建一个Date类型的对像，把时间表戳传进来
		Date date = new Date(when * 1000);
		Date now = new Date();
		if (fullFormat) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			return sdf.format(date);
		}
		// If the message is from a different year, show the date and year.
		if (date.getYear() != now.getYear()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format(date);
		} else if (date.getMonth() != now.getMonth()) {
			if (date.getDay() + 1 == now.getDay()
					&& now.getTime() - date.getTime() < 7 * 24 * 60 * 60 * 1000) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				return "昨天 " + sdf.format(date);
			} else if (date.getDay() + 2 == now.getDay()
					&& now.getTime() - date.getTime() < 7 * 24 * 60 * 60 * 1000) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				return "前天 " + sdf.format(date);
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
				return sdf.format(date);
			}
		} else if (date.getDay() == now.getDay()) {
			if (now.getTime() - date.getTime() < 7 * 24 * 60 * 60 * 1000) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				return "今天 " + sdf.format(date);
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
				return sdf.format(date);
			}
			// If it is from a different day than today, show only the date.
		} else {
			if (date.getDay() + 1 == now.getDay()
					&& now.getTime() - date.getTime() < 7 * 24 * 60 * 60 * 1000) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				return "昨天 " + sdf.format(date);
			} else if (date.getDay() + 2 == now.getDay()
					&& now.getTime() - date.getTime() < 7 * 24 * 60 * 60 * 1000) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				return "前天 " + sdf.format(date);
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
				return sdf.format(date);
			}
		}
	}

	public static String formatTimeStampString2(Context context, long when,
			boolean fullFormat) {
		// when是时间戳，单位是秒，直接创建一个Date类型的对像，把时间表戳传进来
		Date date = new Date(when);
		Date now = new Date();
		System.out.print(date.getHours() + "date.getHours()");
		Logcat.v("date.getYear()" + date.getYear());
		Logcat.v("date.getMonth()" + date.getMonth());
		Logcat.v("date.getDate()" + date.getDate());
		Logcat.v("date.getMinutes()" + date.getMinutes());
		Logcat.v("date.getSeconds()" + date.getSeconds());
		String str = " ";
		if (0 <= date.getHours() && date.getHours() < 6) {
			str = " 凌晨";
		} else if (6 <= date.getHours() && date.getHours() < 12) {
			str = " 上午";
		} else if (12 <= date.getHours() && date.getHours() < 14) {
			str = " 中午";
		} else if (14 <= date.getHours() && date.getHours() < 18) {
			str = " 下午";
		} else if (18 <= date.getHours() && date.getHours() < 24) {
			str = " 晚上";
		}
		if (fullFormat) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			// String
			// time=date.getYear()+"年"+date.getMonth()+"月"+date.getDay()+"日";
			return sdf1.format(date) + str + sdf.format(date);
		}
		if (date.getYear() != now.getYear()) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			// String
			// time=date.getYear()+"年"+date.getMonth()+"月"+date.getDay()+"日";
			return sdf1.format(date) + str + sdf.format(date);
		} else if (date.getMonth() != now.getMonth()) {

			SimpleDateFormat sdf1 = new SimpleDateFormat("MM月dd日");
			// String time=date.getMonth()+"月"+date.getDay()+"日";
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			return sdf1.format(date) + str + sdf2.format(date);
		} else if (date.getDay() == now.getDay()) {
			if (now.getTime() - date.getTime() < 7 * 24 * 60 * 60 * 1000) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				return str + sdf.format(date);
			} else {
				SimpleDateFormat sdf1 = new SimpleDateFormat("MM月dd日");
				// String time=date.getMonth()+"月"+date.getDay()+"日";
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
				return sdf1.format(date) + str + sdf2.format(date);
			}
			// If it is from a different day than today, show only the date.
		} else {
			if (date.getDay() + 1 == now.getDay()
					&& now.getTime() - date.getTime() < 7 * 24 * 60 * 60 * 1000) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				return "昨天 " + str + sdf.format(date);
			} else if (date.getDay() + 2 == now.getDay()
					&& now.getTime() - date.getTime() < 7 * 24 * 60 * 60 * 1000) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				return "前天 " + str + sdf.format(date);
			} else {
				SimpleDateFormat sdf1 = new SimpleDateFormat("MM月dd日");
				// String time=date.getMonth()+"月"+date.getDay()+"日";
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
				return sdf1.format(date) + str + sdf2.format(date);
			}
		}
	}

	/**
	 * 
	 * @param when 单位秒
	 * @update 2014年12月30日 下午4:16:43
	 */
	public static String getDiffByTimeStampString(long when) {
		try {
			// when是时间戳，单位是秒，直接创建一个Date类型的对像，把时间表戳传进来
			final long getTime = when * 1000;
			final long currTime = System.currentTimeMillis();
			final Date formatSysDate = new Date(currTime);
			// 判断当前总天数
			final int sysMonth = formatSysDate.getMonth() + 1;
			final int sysYear = formatSysDate.getYear();
			// 计算服务器返回时间与当前时间差值
			final long seconds = (currTime - getTime) / 1000;
			final long minute = seconds / 60;
			final long hours = minute / 60;
			final long day = hours / 24;
			final long month = day / calculationDaysOfMonth(sysYear, sysMonth);
			final long year = month / 12;
			if (year > 0) {
				return year + "年前";
			} else if (month > 0) {
				return month + "月前";
			} else if (day > 0) {
				return day + "天前";
			} else if (hours > 0) {
				return hours + "小时前";
			} else if (minute > 0) {
				return minute + "分钟前";
			} else if (seconds > 0) {
				return seconds + "秒前";
				// return seconds + context.getString(R.string.str_secondago);
			} else {
				// return "1" + context.getString(R.string.str_secondago);
				return "1" + "分钟前"; // 都换成分钟前
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * 计算月数
	 * 
	 * @return
	 */
	private static int calculationDaysOfMonth(int year, int month) {
		int day = 0;
		switch (month) {
		// 31天
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		// 30天
		case 4:
		case 6:
		case 9:
		case 11:
			day = 30;
			break;
		// 计算2月天数
		case 2:
			day = year % 100 == 0 ? year % 400 == 0 ? 29 : 28
					: year % 4 == 0 ? 29 : 28;
			break;
		}

		return day;
	}

	public static String getFullDateFromStampString(Context context, long when) {
		// when是时间戳，单位是秒，直接创建一个Date类型的对像，把时间表戳传进来
		Date date = new Date(when * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(date);
	}

	public static int getFullDateFromStampString(long when) {
		// when是时间戳，单位是秒，直接创建一个Date类型的对像，把时间表戳传进来
		Date date = new Date(when * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(sdf.format(date));
	}

	public static String formatBaoBaoStampString(Context context, long when, boolean fullFormat)
	{
		// when是时间戳，单位是秒，直接创建一个Date类型的对像，把时间表戳传进来
		Date date = new Date(when * 1000);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long l = System.currentTimeMillis() - calendar.getTimeInMillis();
		Calendar result_calendar = Calendar.getInstance();
		result_calendar.setTimeInMillis(l);
		int year = result_calendar.get(Calendar.YEAR) - 1970;
		int month = result_calendar.get(Calendar.MONTH);
		int day = result_calendar.get(Calendar.DAY_OF_MONTH);
		if (year == 0 && month == 0 && day == 0)
		{
			return "";
		}
		else
		{
			if (year == 0 && month == 0)
			{
				return day > 0 ? (day-1) + "天" : "";
			}
			else
			{
				return (year > 0 ? year + "岁" : "") + (month > 0 ? month + "个月" : "");
			}
		}
	}

	public static String formatMomStampString(Context context, long when,
			boolean fullFormat) {
		// when是时间戳，单位是秒，直接创建一个Date类型的对像，把时间表戳传进来
		Date date = new Date(when * 1000);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long l = System.currentTimeMillis() - calendar.getTimeInMillis();
		Calendar result_calendar = Calendar.getInstance();
		result_calendar.setTimeInMillis(l);
		int year = result_calendar.get(Calendar.YEAR) - 1970;
		int month = result_calendar.get(Calendar.MONTH);
		int day = result_calendar.get(Calendar.DAY_OF_MONTH);
		if (year == 0 && month == 0 && day == 0) {
			return "";
		} else {
			if (year == 0 && month == 0) {
				return day > 0 ? 1 + "岁" : "";
			} else if (year == 0) {
				return month > 0 ? 1 + "岁" : "";
			} else {
				return (year > 0 ? year + "岁" : "");
			}
		}
	}

	// 获取孕中期，孕早期，孕晚期
	public static int getYunQi(Context context, long when, boolean fullFormat) {
		// when是时间戳，单位是秒，直接创建一个Date类型的对像，把时间表戳传进来
		if ((when - (new Date().getTime() / 1000)) >= 28 * 7 * 24 * 60 * 60) {
			return 0;// 孕早期;
		} else if ((when - (new Date().getTime() / 1000)) >= 15 * 7 * 24 * 60
				* 60
				&& when - (new Date().getTime() / 1000) < 28 * 7 * 24 * 60 * 60) {
			return 1;// 孕中期
		} else {
			return 2;// 孕晚期
		}
	}

	public static String formatYuChangStampString(Context context, long when,
			boolean fullFormat) {
		// when是时间戳，单位是秒，直接创建一个Date类型的对像，把时间表戳传进来
		Date date = new Date(when * 1000);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long l = calendar.getTimeInMillis() - System.currentTimeMillis();
		Calendar result_calendar = Calendar.getInstance();
		result_calendar.setTimeInMillis(l);
		int month = 10 - result_calendar.get(Calendar.MONTH);
		if (month <= 0) {
			return 10 + "月";
		}
		return month + "月";
	}

	public static void scalePic(Bitmap bitmap, int sWidth, String filePath) {
		int bmpWidth = bitmap.getWidth();

		int bmpHeight = bitmap.getHeight();
		float scale_rate = 1;
		// 缩放图片的尺寸
		if (sWidth > bmpWidth) {
			scale_rate = 1;
		} else {
			scale_rate = sWidth / bmpWidth;
		}
		float scaleWidth = scale_rate * bmpWidth; // 按固定大小缩放 sWidth
													// 写多大就多大
		float scaleHeight = scale_rate * bmpHeight;
		Matrix matrix = new Matrix();

		matrix.postScale(scale_rate, scale_rate);

		// 产生缩放后的Bitmap对象

		Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth,
				bmpHeight, matrix, false);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			if (null != fos) {
				resizeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		resizeBitmap.recycle();
		bitmap.recycle();
	}

	public static int px2dip(Context context, float pxValue) {
		return (int) (pxValue / density + 0.5f);
	}

	public static int dip2px(Context context, float dipValue) {
		return (int) (dipValue * density + 0.5f);
	}

	public static void copyFile(File sourceFile, File targetFile) {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input;
		try {
			input = new FileInputStream(sourceFile);
			BufferedInputStream inBuff = new BufferedInputStream(input);
			// 新建文件输出流并对它进行缓冲
			FileOutputStream output = new FileOutputStream(targetFile);
			BufferedOutputStream outBuff = new BufferedOutputStream(output);
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();

			// 关闭流
			inBuff.close();
			outBuff.close();
			output.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String buildGetParamsString(String path,
			Map<String, String> params, String enc) throws Exception {

		StringBuilder sb = new StringBuilder(path);
		sb.append('?');
		// ?method=save&title=12345678&timelength=26&
		// 迭代Map拼接请求参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey()).append('=')
					.append(URLEncoder.encode(entry.getValue(), enc))
					.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);// 删除最后一个"&"

		return sb.toString();
	}

	public static void rotateImg(String path, float angle) {
		Bitmap bitMap = BitmapFactory.decodeFile(path);
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(),
				bitMap.getHeight(), matrix, true);// 使用矩阵来构建旋转后的图片
		try {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(path);
				if (null != fos) {
					bitMap.compress(Bitmap.CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();
				}
			} finally {
				if (bitMap != null && !bitMap.isRecycled()) {
					bitMap.recycle();
				}
			}
		} catch (Exception e) {

		}
	}

	// 中间裁剪图片 显示
	public static Bitmap deflate6(int mscreenWidth, int mscreenHeight,
			Bitmap mpicRes, int mwidth, int mheigth) {
		try {
			Bitmap picRes = mpicRes;
			Bitmap showPic;
			// 获取原图片的宽和高
			int picWidth;
			int picHeight;
			// 得到屏幕的长和宽
			int screenWidth = mscreenWidth; // 水平分辨率
			int screenHeight = mscreenHeight; // 垂直分辨率
			int width = mwidth;
			int heigth = mheigth;
			// 得到图片的长和宽
			picWidth = picRes.getWidth();
			picHeight = picRes.getHeight();
			// 计算缩放率，新尺寸除原始尺寸
			float scaleWidth = ((float) screenWidth) / picWidth;
			float scaleHeight = ((float) screenHeight) / picHeight;
			// 创建操作图片用的matrix对象
			Matrix matrix = new Matrix();
			// 缩放图片动作
			matrix.postScale(scaleWidth, scaleHeight);
			// 新得到的图片是原图片经过变换填充到整个屏幕的图片0
			Bitmap picNewRes = Bitmap.createBitmap(picRes, 0, 0, picWidth,
					picHeight, matrix, true);
			// bitmap = Bitmap.createBitmap(400, 480, Bitmap.Config.ARGB_8888);
			// canvas=new Canvas();
			// canvas.setBitmap(bitmap);

			showPic = Bitmap.createBitmap(picNewRes, screenWidth / 2 - 50,
					screenHeight / 2 - 50, width, heigth);

			return showPic;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Bitmap deflate(Bitmap mpicRes, int mwidth, int mheigth) {
		Bitmap picRes = mpicRes;
		Bitmap showPic;
		// 获取原图片的宽和高
		int picWidth;
		int picHeight;
		// 得到屏幕的长和宽
		// int screenWidth = mscreenWidth; // 水平分辨率
		// int screenHeight = mscreenHeight; // 垂直分辨率
		int width = mwidth;
		int heigth = mheigth;
		// 得到图片的长和宽
		picWidth = picRes.getWidth();
		picHeight = picRes.getHeight();
		float scaleWidth, scaleHeight;
		float x = 0, y = 0;
		if (mwidth > mheigth) {
			scaleHeight = (picHeight * mwidth) / picWidth;
			scaleWidth = mwidth;
			y = (scaleHeight - mheigth) / 2;
		} else if (mwidth == mheigth) {
			if (picWidth > picHeight) {
				scaleWidth = (picWidth * mheigth) / picHeight;
				scaleHeight = mheigth;
				x = (scaleWidth - mwidth) / 2;
			} else {
				scaleHeight = (picHeight * mwidth) / picWidth;
				scaleWidth = mwidth;
				y = (scaleHeight - mheigth) / 2;
			}
		} else {
			scaleWidth = (picWidth * mheigth) / picHeight;
			scaleHeight = mheigth;
			x = (scaleWidth - mwidth) / 2;
		}
		// 计算缩放率，新尺寸除原始尺寸
		// float scaleWidth = ((float) screenWidth) / picWidth;
		// float scaleHeight = ((float) screenHeight) / picHeight;
		float mscaleWidth = (scaleWidth) / picWidth;
		float mscaleHeight = (scaleHeight) / picHeight;
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 缩放图片动作
		matrix.postScale(mscaleWidth, mscaleHeight);
		// 新得到的图片是原图片经过变换填充到整个屏幕的图片0
		Bitmap picNewRes = Bitmap.createBitmap(picRes, 0, 0, picWidth,
				picHeight, matrix, true);
		// bitmap = Bitmap.createBitmap(400, 480, Bitmap.Config.ARGB_8888);
		// canvas=new Canvas();
		// canvas.setBitmap(bitmap);

		showPic = Bitmap.createBitmap(picNewRes, (int) x, (int) y, width,
				heigth);
		return showPic;
	}

	public static Bitmap adDeflate(Bitmap mpicRes, int mwidth, int mheigth) {
		Bitmap picRes = mpicRes;
		Bitmap showPic;
		// 获取原图片的宽和高
		int picWidth;
		int picHeight;
		// 得到图片的长和宽
		picWidth = picRes.getWidth();
		picHeight = picRes.getHeight();
		float scaleWidth, scaleHeight;
		scaleHeight = (picHeight * mwidth) / picWidth;
		scaleWidth = mwidth;
		// 计算缩放率，新尺寸除原始尺寸
		float mscaleWidth = (scaleWidth) / picWidth;
		float mscaleHeight = (scaleHeight) / picHeight;
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 缩放图片动作
		matrix.postScale(mscaleWidth, mscaleHeight);
		// 新得到的图片是原图片经过变换填充到整个屏幕的图片0
		Bitmap picNewRes = Bitmap.createBitmap(picRes, 0, 0, picWidth,
				picHeight, matrix, true);
		showPic = Bitmap.createBitmap(picNewRes, 0, 0, mwidth,
				(int) scaleHeight);
		return showPic;
	}

	// 中间裁剪图片 显示
	public static Bitmap deflate2(Bitmap mpicRes, int mwidth, int mheigth) {
		Bitmap picRes = mpicRes;
		Bitmap showPic;
		// 获取原图片的宽和高
		int picWidth;
		int picHeight;
		// 得到屏幕的长和宽
		// int screenWidth = mscreenWidth; // 水平分辨率
		// int screenHeight = mscreenHeight; // 垂直分辨率
		int width = mwidth;
		int heigth = mheigth;
		// 得到图片的长和宽
		picWidth = picRes.getWidth();
		picHeight = picRes.getHeight();
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) mwidth) / picWidth;
		float scaleHeight = ((float) mheigth) / picHeight;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		// 新得到的图片是原图片经过变换填充到整个屏幕的图片0
		Bitmap picNewRes = Bitmap.createBitmap(picRes, 0, 0, picWidth,
				picHeight, matrix, true);
		// bitmap = Bitmap.createBitmap(400, 480, Bitmap.Config.ARGB_8888);
		// canvas=new Canvas();
		// canvas.setBitmap(bitmap);

		showPic = Bitmap.createBitmap(picNewRes, 0, 0, width, heigth);
		return showPic;
	}

	// 中间裁剪图片 显示
	public static Bitmap deflate3(Bitmap mpicRes, int mwidth, int mheigth) {
		Bitmap picRes = mpicRes;
		Bitmap showPic;
		// 获取原图片的宽和高
		int picWidth;
		int picHeight;
		// 得到屏幕的长和宽
		// int screenWidth = mscreenWidth; // 水平分辨率
		// int screenHeight = mscreenHeight; // 垂直分辨率
		int width = mwidth;
		int heigth = mheigth;
		// 得到图片的长和宽
		picWidth = picRes.getWidth();
		picHeight = picRes.getHeight();
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) mwidth) / picWidth;
		float scaleHeight = ((float) mheigth) / picHeight;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		// 新得到的图片是原图片经过变换填充到整个屏幕的图片0
		Bitmap picNewRes = Bitmap.createBitmap(picRes, 0, 0, picWidth,
				picHeight, matrix, true);
		// bitmap = Bitmap.createBitmap(400, 480, Bitmap.Config.ARGB_8888);
		// canvas=new Canvas();
		// canvas.setBitmap(bitmap);

		showPic = Bitmap.createBitmap(picNewRes, 0, 0, width, heigth);
		return showPic;
	}

	// 播放时间转换
	public static String secondsToString(int time) {
		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		// time /= 1000;
		// int minute = time / 60;
		// int second = time % 60;
		// minute %= 60;
		return String.format("%02d:%02d", minute, second);
	}

	public static Bitmap deflate4(Bitmap mpicRes, int mwidth, int mheigth) {
		Bitmap picRes = mpicRes;
		Bitmap showPic;
		// 获取原图片的宽和高
		int picWidth;
		int picHeight;
		// 得到屏幕的长和宽
		// int screenWidth = mscreenWidth; // 水平分辨率
		// int screenHeight = mscreenHeight; // 垂直分辨率
		int width = mwidth;
		int heigth = mheigth;
		// 得到图片的长和宽
		picWidth = picRes.getWidth();
		picHeight = picRes.getHeight();
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) mwidth) / picWidth;
		float scaleHeight = ((float) mheigth) / picHeight;
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		if (scaleWidth < scaleHeight) {
			float scale = scaleHeight;// 取大的
			matrix.postScale(scale, scale);// 缩放比例
			int xStart = (int) (scaleWidth - scaleWidth / scale) / 2;
			showPic = Bitmap.createBitmap(mpicRes, 0, 0,
					(int) (picWidth / scale), picHeight, matrix, true);
		} else {
			float scale = scaleWidth;
			matrix.postScale(scale, scale);
			int yStart = (int) (scaleHeight - scaleHeight / scale) / 2;
			showPic = Bitmap.createBitmap(mpicRes, 0, 0, picWidth,
					(int) (picHeight / scale), matrix, true);
		}
		// // 缩放图片动作
		// matrix.postScale(scaleWidth, scaleHeight);
		// // 新得到的图片是原图片经过变换填充到整个屏幕的图片0
		// Bitmap picNewRes = Bitmap.createBitmap(picRes, 0, 0, picWidth,
		// picHeight, matrix, true);
		// // bitmap = Bitmap.createBitmap(400, 480, Bitmap.Config.ARGB_8888);
		// // canvas=new Canvas();
		// // canvas.setBitmap(bitmap);
		//
		// showPic = Bitmap.createBitmap(picNewRes, screenWidth / 2 - 50,
		// screenHeight / 2 - 50, width, heigth);
		return showPic;
	}

	// 删除文件夹
	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean deleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean hasSD() {
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}

	}

	// 获得文件夹大小
	static int totalFolder = 0;

	static int totalFile = 0;

	public static long getFileSize(File folder) {

		totalFolder++;

		Logcat.v("Folder: " + folder.getName());

		long foldersize = 0;

		File[] filelist = folder.listFiles();

		for (int i = 0; i < filelist.length; i++) {

			if (filelist[i].isDirectory()) {

				foldersize += getFileSize(filelist[i]);

			} else {

				totalFile++;

				foldersize += filelist[i].length();

			}

		}

		return foldersize;

	}

	/**
	 * 获取圆角位图的方法
	 * 
	 * @param bitmap
	 *            需要转化成圆角的位图
	 * @param pixels
	 *            圆角的度数，数值越大，圆角越大
	 * @return 处理后的圆角位图
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	private static TimeDiff timeDiff;

	public static TimeDiff dateDiff(String startTime, String endTime,
			String format) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff / nd;// 计算差多少天
			long hour = diff % nd / nh;// 计算差多少小时
			long min = diff % nd % nh / nm;// 计算差多少分钟
			long sec = diff % nd % nh % nm / ns;// 计算差多少秒
			Logcat.v("day" + day + "hour" + hour + "min" + min + "sec" + sec);
			// 输出结果
			timeDiff = new TimeDiff(day, hour, min, sec);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeDiff;
	}

	public static String getSDPath(Activity context) {
		File sdDir = null;
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		} else {
			showLongToast(context, "SD卡不存在，请插入SD卡");
			return "";
		}
		return sdDir.toString();

	}

	// 是否微模拟器 true 是模拟器
	public static boolean isEmulator() {
		try {
			// TelephonyManager tm = (TelephonyManager)
			// context.getSystemService(Context.TELEPHONY_SERVICE);
			// String imei = tm.getDeviceId();
			// if (imei != null && imei.equals("000000000000000")){
			// return true;
			// }
			// Logcat.v("Build.MODEL.equals(sdk)"+Build.MODEL.equals("sdk")+"Build.MODEL.equals(google_sdk)"+Build.MODEL.equals("google_sdk"));
			return (Build.MODEL.equals("sdk"))
					|| (Build.MODEL.equals("google_sdk"));
		} catch (Exception ioe) {

		}
		return false;
	}
	
	public static void emulate(Activity context)
	{
		if (Tools.isEmulator()) {
			//Log.e("App", "isEmulator");
			Tools.showLongToast(context, "辣妈帮禁止模拟器登录,请用真机启动!!!");
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
						android.os.Process.killProcess(android.os.Process.myPid());
				        System.exit(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	/**
	 * 
	 * @description 获取AndroidManifest.xml 中的meta信息
	 * @author 张清田
	 * @update 2014年10月9日 下午1:23:41
	 */
	public static String getMetaOfApplication(Context ctt,String name)
	{
		ApplicationInfo appInfo = null;
		String msg = "";
		try
		{
			if(ctt!=null)
			{
				appInfo = ctt.getPackageManager().getApplicationInfo(ctt.getPackageName(), PackageManager.GET_META_DATA);
			}
		}
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (appInfo != null)
		{
			msg = String.valueOf(appInfo.metaData.getString(name));
			//System.out.println(name + ":" + msg);
		}
		return msg;
	}
	
	// 排序
	public static Map.Entry[] getSortedHashtableByKey(Hashtable h) {

		Set set = h.entrySet();

		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set
				.size()]);

		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Object key1 = ((Map.Entry) arg0).getKey();
				Object key2 = ((Map.Entry) arg1).getKey();
				return ((Comparable) key1).compareTo(key2);
			}

		});

		return entries;
	}
	
	public static Map.Entry[] getSortedHashMapByKey(LinkedHashMap<String, String> h) {

		Set set = h.entrySet();

		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set
				.size()]);

		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Object key1 = ((Map.Entry) arg0).getKey();
				Object key2 = ((Map.Entry) arg1).getKey();
				return ((Comparable) key1).compareTo(key2);
			}

		});

		return entries;
	}

	// 获取星座
	public static int getAstro(int month, int day) {
		Logcat.v("month" + month);
		String[] astro = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座",
				"双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };
		int[] arr = new int[] { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };// 两个星座分割日
		int index = month;
		// 所查询日期在分割日之前，索引-1，否则不变
		if (day < arr[month - 1]) {
			index = index - 1;
		}
		// 返回索引指向的星座string
		return index;

	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOPen(final Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}
		return false;
	}

	/**
	 * 强制帮用户打开GPS
	 * 
	 * @param context
	 */
	public static final void openGPS(Context context) {
		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}

	public static String floatToString(float money)
	{
		DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		return decimalFormat.format(money);
	}

	

	/**
	 * 
	 * @description 返回金钱 比如  ¥5.00
	 * @author zhangqingtian
	 * @update 2015年1月4日 下午6:15:14
	 */
	public static Spanned getPositiveFloatSpanned(float money)
	{
		DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		return Html.fromHtml("+¥" + decimalFormat.format(money));
	}
	
	/**
	 * 
	 * @description 返回负数金钱 比如  -¥5.00
	 * @author zhangqingtian
	 * @update 2015年1月4日 下午6:15:14
	 */
	public static Spanned getMinusFloatSpanned(float money)
	{
		DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		return Html.fromHtml("-¥" + decimalFormat.format(money));
	}

	/***
	 * 
	 * @description 转颜色
	 * @author allen
	 * @update 2015-4-1 下午4:49:16
	 */
	public static int parseColor(String color)
	{
		if (TextUtils.isEmpty(color)) {
		return Color.TRANSPARENT;
		}
		if(!color.startsWith("#"))
		{
			color="#"+color;
		}
		if(color.length()<7)
		{
			color=(color+"000000").substring(0,7);
		}
		return	Color.parseColor(color);
	}

	/***
	 * 
	 * @description 常用类型转换
	 * @author allen
	 * @update 2015-4-8 上午11:19:09
	 * @param obj 待转换对象
	 * @param parseDefault 转换默认值(需根据默认值类型判断返回值类型)
	 */
	public static Object parseSimpleObject(Object obj,Object parseDefault)
	{
		if(parseDefault==null)
		{
			return null;
		}
		if(obj==null)
		{
			return parseDefault;
		}
		String str = obj.toString();
		Class<? extends Object> c= parseDefault.getClass();
		try
		{
			if(c.equals(String.class))
			{
				return str;
			}else if(c.equals(Boolean.class)){
				return Boolean.parseBoolean(str);
			}else if(c.equals(Integer.class)){
				return Integer.parseInt(str);
			}else if(c.equals(Float.class)){
				return Float.parseFloat(str);
			}else if(c.equals(Double.class)){
				return Double.parseDouble(str);
			}else if(c.equals(Long.class)){
				return Long.parseLong(str);
			}
		}
		catch(Exception e)
		{
			return parseDefault;
		}
		return parseDefault;
	}
	
	/***
	 * 
	 * @description url检查：是否http://或者https://开头
	 * @author allen
	 * @update 2015-4-17 下午2:39:33
	 */
	public static String urlCheck(String url)
	{
		if(TextUtils.isEmpty(url))
		{
			return "";
		}
		if(url.startsWith("http://") || url.startsWith("https://"))
		{
			return url;
		}
		return "http://"+url;
	}
	
	/***
	 * 
	 * @description 获取本机手机号码
	 * @author allen
	 * @update 2015-4-27 下午5:25:55
	 */
	public static String getPhoneNumber(Context context)
	{
		TelephonyManager tm=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if(tm==null){
			return "";
		}
		return tm.getLine1Number();
	}
	
	/***
	 * 
	 * @description 根据图片文件名称转为Bitmap
	 * @author allen
	 * @update 2015-4-30 下午6:36:07
	 */
	public static Bitmap parseBitmap(String path) {
//		BitmapFactory.Options opts = new BitmapFactory.Options();
//		// 设置为ture只获取图片大小
//		opts.inJustDecodeBounds = true;
//		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
//		return  BitmapFactory.decodeFile(path, opts);
		FileInputStream fis;
		try {
			fis = new FileInputStream(path);
			return  BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	

	
	/***
	 * 
	 * @description 读取文本文件
	 * @author allen
	 * @update 2015-5-6 下午4:12:38
	 */
	public static String ReadTxtFile(String path)
    {
		String content = ""; //文件内容字符串
		if(!TextUtils.isEmpty(path))
		{
            //打开文件
            File file = new File(path);
            if(file.exists())
            {
            	if(file.isFile())
            	{
            		try {
                        InputStream instream = new FileInputStream(file); 
                        if (instream != null) 
                        {
                            InputStreamReader inputreader = new InputStreamReader(instream);
                            BufferedReader buffreader = new BufferedReader(inputreader);
                            String line;
                            //分行读取
                            while (( line = buffreader.readLine()) != null) {
                                content += line;
                            }
                            instream.close();
                        }
                    } catch (FileNotFoundException e) {
					} catch (IOException e) {
					}
            	}
            }
		}
		return content;
    }
	
	  
	  /**
		* 方法1：检查某表列是否存在   根据 cursor.getColumnIndex(String columnName) 的返回值判断，如果为-1表示表中无此字段
		* @param db
		* @param tableName 表名
		* @param columnName 列名
		* @return
		*/
		public static  boolean checkColumnExist1(SQLiteDatabase db, String tableName
		        , String columnName) {
		    boolean result = false ;
		    Cursor cursor = null ;
		    try{
		        //查询一行
		        cursor = db.rawQuery( "SELECT * FROM " + tableName + " LIMIT 0"
		            , null );
		        result = cursor != null && cursor.getColumnIndex(columnName) != -1 ;
		    }catch (Exception e){
		         Logcat.e("db","checkColumnExists1..." + e.getMessage()) ;
		    }finally{
		        if(null != cursor && !cursor.isClosed()){
		            cursor.close() ;
		        }
		    }

		    return result ;
		}
		/***
		 * 
		 * @description 判断是否符合正则表达式
		 * @author allen
		 * @update 2015-5-13 下午4:39:32
		 * @param pattern 正则表达式
		 * @param input 目的字符串
		 */
		public static boolean isPatternMatch(String pattern,String input) {  
	        Pattern p = Pattern.compile(pattern);  
	        Matcher m = p.matcher(input);
	        return m.matches();  
	    }
		
		/***
		 * 
		 * @description 判断是否手机号码
		 * @author allen
		 * @update 2015-5-13 下午4:43:28
		 */
		public static boolean isPhoneNO(String mobiles) {  
			String pattern = "^1\\d{10}$";
			return isPatternMatch(pattern, mobiles);
	    }
		
		/***
		 * 
		 * @description 判断是否验证码
		 * @author allen
		 * @update 2015-5-13 下午4:43:28
		 */
		public static boolean isVerifyNO(String verifyno) {  
			String pattern = "^\\d{4}$";
			return isPatternMatch(pattern, verifyno);
	    }

		
		
		
		/***
		 * 
		 * @description 往SharedPreferences写入内容
		 * @author allen
		 * @update 2015-5-20 上午11:32:34
		 * @param context
		 * @param key 关键字
		 * @param obj 写入值
		 */
		public static void putSharedPreferences(Context context,String key,Object obj){
			if(context!=null && !TextUtils.isEmpty(key) && obj!=null){
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
				Editor editor = sp.edit();
				Class<? extends Object> c= obj.getClass();
				try{
					if(c.equals(Boolean.class)){
						editor.putBoolean(key, (Boolean)obj);
					}else if(c.equals(Float.class)){
						editor.putFloat(key, (Float)obj);
					}else if(c.equals(Integer.class)){
						editor.putInt(key, (Integer)obj);
					}else if(c.equals(Long.class)){
						editor.putLong(key, (Long)obj);
					}else if(c.equals(String.class)){
						editor.putString(key, (String)obj);
					}
				}catch(Exception e){}
				editor.commit();
			}
		}
		
		/***
		 * 
		 * @description 获取SharedPreferences内容
		 * @author allen
		 * @update 2015-5-20 上午11:32:34
		 * @param context
		 * @param key 关键字
		 * @param obj 默认值
		 */
		public static Object getSharedPreferences(Context context,String key,Object obj){
			if(context!=null && !TextUtils.isEmpty(key) && obj!=null){
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
				Class<? extends Object> c= obj.getClass();
				try{
					if(c.equals(Boolean.class)){
						return sp.getBoolean(key, (Boolean)obj);
					}else if(c.equals(Float.class)){
						return sp.getFloat(key, (Float)obj);
					}else if(c.equals(Integer.class)){
						return sp.getInt(key, (Integer)obj);
					}else if(c.equals(Long.class)){
						return sp.getLong(key, (Long)obj);
					}else if(c.equals(String.class)){
						return sp.getString(key, (String)obj);
					}
				}catch(Exception e){ 
					return ""; 
				}
			}
			return "";
		}

	/**
	 * 
	 * @description 字符串是否为空
	 * @author zhongwr
	 * @update 2015年5月26日 下午8:45:59
	 */
	public static boolean isEmpty(String strIsEmpty) {
		if (null == strIsEmpty || "".equals(strIsEmpty.trim()) || "null".equals(strIsEmpty)) {
			return true;
		}
		return false;
	}
}