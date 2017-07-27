package com.kfd.common;



/*
 * 定义一些常量
 */


public class Define {
	
	public final  static  String verify_host="http://img8.lamaqun.com";
	public static String emoji_prefix = Define.verify_host+"/images/emoji/";
	private Define(){};

	public final static int DEFAULT_SO_TIMEOUT = 60000;// default socket timeout
	public final static int DEFAULT_CONNECTION_TIMEOUT = 60000;// 建立连接默认超时时间

	public final static boolean DEBUG = true;//release改为false
	public static boolean RELEASE = true;//  RELEASE 改成true,其他是false
	public static final  String   PARAM_V="1.1.0";
	
	public static final  String host  ="http://live.kfd9999.com";
//	public static final  String host  ="http://kfd.beta.golds-cloud.com/";
	public static final  String LOGIN_STRING  ="/api-user/login";

}

