package com.kfd.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetWorkManagers {

	public static final int NETWORK_STATUS_GPRS = 0;// GPRS网络

	public static final int NETWORK_STATUS_WIFI = 1;// WIFI网络

	public static final int NETWORK_STATUS_NOT_AVAILABLE = -1;// 网络不可用

	public static final int SERVER_CONNECT_STATUS_SUCCESS = 0;// 服务器连接成功

	public static final int SERVER_CONNECT_STATUS_FAIL = -1;// 服务器连接失败

	private int networkStatus;// 网络连接状态

	private int serverConnectStatus;// 服务器连接状态

	private static NetWorkManagers instance;

	private NetWorkManagers() {

	}

	public static NetWorkManagers getInstance() {
		if (instance == null) {
			instance = new NetWorkManagers();
		}
		return instance;
	}

	public int setNetworkStatus() {
		return networkStatus;
	}

	public void setNetworkStatus(int networkStatus) {
		this.networkStatus = networkStatus;
	}

	public int getServerConnectStatus() {
		return serverConnectStatus;
	}

	public void setServerConnectStatus(int serverConnectStatus) {
		this.serverConnectStatus = serverConnectStatus;
	}

	/**
	 * 网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetWorkActive() {
		if (networkStatus == NETWORK_STATUS_GPRS
				|| networkStatus == NETWORK_STATUS_WIFI) {
			return true;
		} else if (networkStatus == NETWORK_STATUS_NOT_AVAILABLE) {
			return false;
		}
		return false;
	}

	/**
	 * 服务器连接是否可用
	 * 
	 * @return
	 */
	public boolean isServerConnectActive() {
		if (serverConnectStatus == SERVER_CONNECT_STATUS_SUCCESS) {
			return true;
		} else if (serverConnectStatus == SERVER_CONNECT_STATUS_FAIL) {
			return false;
		}
		return false;
	}

	/**
	 * 判断当前是否有可用网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}

	/**
	 * 判断当前网络是否已连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnectioned(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetWorkTypeName(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.getTypeName().toLowerCase();
		}
		return null;
	}

	/**
	 * 获取当前网络制试
	 * 
	 * @param context
	 * @return
	 */
	public static int getPhoneType(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return manager.getPhoneType();
	}
}
