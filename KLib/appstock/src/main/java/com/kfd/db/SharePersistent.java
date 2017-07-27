package com.kfd.db;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.kfd.activityfour.ActivityManager;
import com.kfd.bean.User;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;

/**
 * 管理SharedPreferences数据
 * 
 * 
 * 
 */
public class SharePersistent {
	public static final String ACTIVATE = "activate";
	public static final String DEFAULT_PREFS_NAME = "brclient";
	public static final String USERINFO = "userinfo";
	public static final String VERSION = "versiondata";
	private static SharePersistent instance;

	public static String get(String paramString) {
		return getInstance().getPerference(ActivityManager.getCurrent(),
				paramString);
	}

	public static boolean getActivateState(Context paramContext) {
		return paramContext.getSharedPreferences("brclient", 0).getBoolean(
				"isActivate", false);
	}

	public static SharePersistent getInstance() {
		if (instance == null)
			instance = new SharePersistent();
		return instance;
	}

	public static String getPhone(String paramString) {
		// Object localObject;
		String str = null;
		try {
			str = get("customer_mobile");
			if (StringUtils.isEmpty(str))
				str = null;
			else
				str = (String) new JSONObject(str).get(paramString);
		} catch (Exception localException) {
			LogUtils.e("test", localException);
			str = null;
		}
		return str;
	}

	public static String getVersion(Context paramContext) {
		return paramContext.getSharedPreferences("versiondata", 0).getString(
				"version", null);
	}

	private void putStringIfNotEmpty(SharedPreferences.Editor paramEditor,
			String paramString1, String paramString2) {
		if (!StringUtils.isEmpty(paramString2))
			paramEditor.putString(paramString1, paramString2);
	}

	public static void saveActivateState(Context paramContext) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("brclient", 0).edit();
		localEditor.putBoolean("isActivate", true);
		localEditor.commit();
	}

	public static void savePhone(String paramString1, String paramString2) {
		try {
			String localObject = get("customer_mobile");
			if (!StringUtils.isEmpty(localObject))
				;
			// for (localObject = new JSONObject((String)localObject); ;
			// localObject = localObject)
			// {
			// ((JSONObject)localObject).put(paramString1, paramString2);
			// set("customer_mobile", ((JSONObject)localObject).toString());
			// break;
			// localObject = new JSONObject();
			// }
		} catch (Exception localException) {
			LogUtils.e("test", localException);
		}
	}

	public static void saveVersion(Context paramContext, String paramString) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("versiondata", 0).edit();
		localEditor.putString("version", paramString);
		localEditor.commit();

	}

	public static void set(String paramString1, String paramString2) {
		getInstance().savePerference(ActivityManager.getCurrent(),
				paramString1, paramString2);
	}

	public void clearAccessToken(Context paramContext) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("tokeninfo", 0).edit();
		localEditor.putString("accesstoken", null);
		localEditor.putString("mtokensecret", null);
		localEditor.commit();
	}

	/*
	 * public AccessToken getAccessToken(Context paramContext) {
	 * SharedPreferences localSharedPreferences = paramContext
	 * .getSharedPreferences("tokeninfo", 0); return new
	 * AccessToken(localSharedPreferences.getString("accesstoken", null),
	 * localSharedPreferences.getString("mtokensecret", null)); }
	 */

	public String getPerference(Context paramContext, String paramString) {
		return paramContext.getSharedPreferences("brclient", 0).getString(
				paramString, "");
	}

	public String getLovePerference(Context paramContext, String paramString) {
		return paramContext.getSharedPreferences("couponlove", 0).getString(
				paramString, "");
	}

	public String getHatePerference(Context paramContext, String paramString) {
		return paramContext.getSharedPreferences("couponhate", 0).getString(
				paramString, "");
	}

	public void saveLovePerferences(Context paramContext, String id, String time) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("couponlove", 0).edit();
		localEditor.putString(id, time);
		localEditor.commit();
	}

	public void saveHatePerferences(Context paramContext, String id, String time) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("couponhate", 0).edit();
		localEditor.putString(id, time);
		localEditor.commit();
	}

	public boolean getPerferenceBoolean(Context paramContext, String paramString) {
		return paramContext.getSharedPreferences("brclient", 0).contains(
				paramString);
	}

	public boolean isChekced(Context paramContext)
	{
		return paramContext.getSharedPreferences("brclient", 0).getBoolean("isChecked", false);
	}
	
	public User getUserInfo(Context paramContext) {

		SharedPreferences localSharedPreferences = paramContext
				.getSharedPreferences("userinfo", 0);

		User localUser = new User();
		localUser.account = localSharedPreferences.getString("account", "");
		localUser.email = localSharedPreferences.getString("email", "");
		localUser.qq = localSharedPreferences.getString("qq", "");
		localUser.name = localSharedPreferences.getString("name", "");
		localUser.genre = localSharedPreferences.getString("genre", "");
		localUser.phone = localSharedPreferences.getString("phone", "");

		return localUser;
	}

	public void removePerference(Context paramContext, String paramString) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("brclient", 0).edit();
		localEditor.remove(paramString);
		localEditor.commit();
	}

	/**
	 * 保存信息
	 * 
	 * @param paramContext
	 * @param paramAccessToken
	 */

	/*
	 * public void saveAccessToken(Context paramContext, AccessToken
	 * paramAccessToken) { SharedPreferences.Editor localEditor = paramContext
	 * .getSharedPreferences("tokeninfo", 0).edit();
	 * localEditor.putString("accesstoken", paramAccessToken.getToken());
	 * localEditor.putString("mtokensecret", paramAccessToken.getSecret());
	 * localEditor.commit(); }
	 */

	public void savePerference(Context paramContext, String paramString1,
			String paramString2) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("brclient", 0).edit();
		localEditor.putString(paramString1, paramString2);
		localEditor.commit();
	}

	public void savePerference(Context paramContext, String paramString1,
			String paramString2, String paramString3, String paramString4) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("brclient", 0).edit();
		localEditor.putString(paramString1, paramString2);
		localEditor.putString(paramString3, paramString4);
		localEditor.commit();
	}

	public void removePerference1(Context paramContext, String string) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("brclient", 0).edit();
		localEditor.remove(string);
		localEditor.commit();

	}

	public void removePerference(Context paramContext, String paramString1,
			String paramString2) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("brclient", 0).edit();
		localEditor.remove(paramString1);
		localEditor.remove(paramString2);
		localEditor.commit();

	}

	public void savePerference(Context paramContext, String paramString,
			boolean paramBoolean) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("brclient", 0).edit();
		localEditor.putBoolean(paramString, paramBoolean);
		localEditor.commit();
	}

	public void saveUserInfo(Context paramContext, User paramUser) {
		LogUtils.log("test", paramUser);
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("userinfo", 0).edit();
		//userinfo.xml
		putStringIfNotEmpty(localEditor, "account", paramUser.account);
		putStringIfNotEmpty(localEditor, "name", paramUser.name);
		putStringIfNotEmpty(localEditor, "phone", paramUser.phone);
		putStringIfNotEmpty(localEditor, "email", paramUser.email);
		putStringIfNotEmpty(localEditor, "qq", paramUser.qq);
		putStringIfNotEmpty(localEditor, "genre", paramUser.genre);
	
		localEditor.commit();
	}

	public void removeUserInfo(Context paramContext) {
		SharedPreferences.Editor lEditor = paramContext.getSharedPreferences(
				"userinfo", 0).edit();
		lEditor.clear();
		lEditor.commit();

	}

}