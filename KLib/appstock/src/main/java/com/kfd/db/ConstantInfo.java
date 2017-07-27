package com.kfd.db;

/**
 * 常量接口类
 * 

 */
public interface ConstantInfo {
	//public static  final  String  parenturl="http://mydaofu3.daofu5.com/webapi/";
	//第3版 ： public static final String parenturl = "http://my.daofu5.com/webapi/";
	 public static final String parenturl = "http://59.188.216.228:60731/post/?url=";
	//public static final String parenturl = "http://59.188.216.248:60731/post/?url=";
	//public static  final  String  parenturl="http://my.daofu3.com/webapi/?name=";
	public static final String parenturl1 = "http://my.daofu5.com";//新闻列表（去掉了）
//	public static final String parenturl2 = "http://www.br9999.com";//注册

//	public static final String parenturl = "http://my.brnew.com";//除去注册的
//	public static final String parenturl1 = "http://my.brnew.com";//新闻列表（去掉了）
	public static final String parenturl2 = "http://my.daofu5.com";//注册
	public final static int LISTVIEW_ACTION_INIT = 0x01;
	public final static int LISTVIEW_ACTION_REFRESH = 0x02;
	public final static int LISTVIEW_ACTION_SCROLL = 0x03;
	public final static int LISTVIEW_ACTION_CHANGE_CATALOG = 0x04;

	public final static int LISTVIEW_DATA_MORE = 0x01;
	public final static int LISTVIEW_DATA_LOADING = 0x02;
	public final static int LISTVIEW_DATA_FULL = 0x03;
	public final static int LISTVIEW_DATA_EMPTY = 0x04;
	public static final int PAGE_SIZE = 10;// 默认分页大小
	
	public static final String appid = "OBESJ77anOOXRc6";
	//public static final String appid = "FLEUHU8mjSFXB80";

	
	public static final String URL = "http://www.webxml.com.cn/WebServices/ChinaStockWebService.asmx/getStockInfoByCode?theStockCode=";
	// K线图接口
	public static final String StockImageUrl = "http://www.webxml.com.cn/WebServices/ChinaStockWebService.asmx/getStockImage_kByCode?theStockCode=";
	public static final String StockHourKLine = "http://www.webxml.com.cn/WebServices/ChinaStockWebService.asmx/getStockImageByCode?theStockCode=";
	public static final String DayKeyWord = "&theType=D";
	public static final String WeekKeyWord = "&theType=W";
	public static final String MonthKeyWord = "&theType=M";
	

}
