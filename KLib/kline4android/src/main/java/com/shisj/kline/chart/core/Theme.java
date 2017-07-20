package com.shisj.kline.chart.core;

import java.util.HashMap;
import java.util.Map;


import android.graphics.Color;

/**
 * 保存颜色、字体大小等相关配置
 * @author shishengjie
 *
 */
public class Theme {
	
	public static Map<String,Object> map=new HashMap<String,Object>(30); 
	public static float DENSITYDEFAULT=2;
	public static float DENSITY=2;
	
	public static void addColor(String key,int  color){
		map.put(key,color);
	}
	public static float getFloat(String key){
		float ret=20.0f;
		try{
			ret=(Float) map.get(key);
		}catch(Exception e){
			ret=(Integer) map.get(key);
		}
		return ret;
	}
	public static int getColor(String key){
		int color=(Integer) map.get(key);
		if(color==0){
			color=Color.BLUE;
		}
		return color;
	}
	
	public static void resetSize(){
		map.put("TIMELINE_Y_SPAN",100f);
		map.put("AXIS_Y_TEXT_SIZE", 20f);//  X轴文字大小
		map.put("AXIS_X_TEXT_SIZE", 20f);//  X轴文字大小
		map.put("PRICE_LINE_TEXT_SIZE", 20f);//  价格线-文字大小
		map.put("CROSS_X_TEXT_SIZE", 20f);// 十字线-X轴文字大小
		map.put("PRICE_TEXT_TEXT_SIZE", 25f);// 价格文字的文字大小
		map.put("MACD_TEXT_SIZE", 20f);//  
		map.put("TIMELINE_INFO_TEXT_SIZE", 20f);//
		map.put("CROSS_TIP_TEXT_SIZE", 20f);//重置十字线矩形文字大小
		map.put("CROSS_TIP_RECT_WIDTH", 200f);//
		map.put("CROSS_TIP_RECT_HEIGHT",160f);//
		
	}
	public static void reset(){

		map.put("MAIN_BG", Color.BLACK);//蜡烛体背景颜色
		map.put("AXIS_Y_BG", Color.BLACK);// Y轴背景色
		map.put("AXIS_Y_BORDER_BG", Color.GREEN);// Y轴边界线颜色
		map.put("AXIS_Y_LINE_BG", Color.DKGRAY);// Y轴对应的横线颜色
		map.put("AXIS_Y_TEXT_BG", Color.CYAN);// Y轴文字颜色
		map.put("AXIS_X_BG", Color.BLACK);// X轴背景色
		map.put("AXIS_X_BORDER_BG", Color.GREEN);// X轴边界线颜色
		map.put("AXIS_X_LINE_BG", Color.DKGRAY);// X轴对应的横线颜色
		map.put("AXIS_X_TEXT_BG", Color.CYAN);//  X轴文字颜色
		
		
		map.put("CANDLE_RISE_BG", Color.RED);//  蜡烛体―涨-背景色
		map.put("CANDLE_DOWN_BG", Color.GREEN);//  蜡烛体―跌-背景色
		map.put("CROSS_LINE_BG", Color.WHITE);//  十字线-线段颜色
		map.put("CROSS_X_TEXT_BG", Color.BLACK);//  十字线-X轴文字颜色
		map.put("CROSS_X_RECT_BG", Color.rgb(254, 198, 189));//  十字线-X轴矩形背景
		map.put("CROSS_Y_TEXT_BG", Color.BLACK);//  十字线-Y轴文字颜色
		map.put("CROSS_TIP_RECT_BG", Color.argb(220,0,0,0));//  十字线-Y轴文字颜色
		map.put("CROSS_TIP_TEXT_BG", Color.WHITE);//  十字线-Y轴文字颜色
		
		map.put("CROSS_Y_RECT_BG", Color.rgb(254, 198, 189));//  十字线-Y轴矩形背景
		map.put("PRICE_TEXT_TEXT_BG", Color.WHITE);// 
		map.put("MACD_ZERO_LINE_BG", Color.GRAY);//  
		
		map.put("MACD_TEXT_BG", Color.CYAN);//  
		map.put("MACD_DIFF_LINE_BG", Color.rgb(255, 219, 52));//  
		map.put("MACD_DEA_LINE_BG", Color.WHITE);//  
		map.put("MACD_SIGN_UP_BG", Color.RED);//  
		map.put("MACD_SIGN_DOWN_BG", Color.rgb(12, 227, 179));//  
		
		
		map.put("PRICE_LINE_BG", Color.WHITE);//  
		map.put("PRICE_LINE_RECT_BG", Color.BLUE);//  
		map.put("PRICE_LINE_TEXT_BG", Color.WHITE);// 
		
		map.put("WINDOW_TOPLINE_BG", Color.WHITE);// 
		map.put("ASSIST_POINT_BG", Color.rgb(66, 75, 255));// 
		map.put("ASSIST_LINE_BG", Color.rgb(66, 75, 255));// 
		
		
		
		map.put("MV_LINE_BG",Color.BLUE);
		map.put("MV_BOLL_LINE_BG",Color.BLUE);
		map.put("MV_BOLL_TOP_BG",Color.BLUE);
		map.put("MV_BOLL_DOWN_BG",Color.BLUE);
		
		map.put("TIMELINE_CHART_BG",Color.rgb(70, 130, 255));
		map.put("TIMELINE_INFO_TEXT_BG",Color.WHITE);
		map.put("TIMELINE_POINT_LEFT_BG",Color.WHITE);
		map.put("TIMELINE_POINT_RIGHT_BG",Color.WHITE);
		map.put("TIMELINE_Y_UP_BG",Color.RED);
		map.put("TIMELINE_Y_DOWN_BG",Color.GREEN);
		
		
		map.put("FINA_LINE_BG",Color.rgb(255, 147, 32));
		
		map.put("CROSS_TIP_RECT_WIDTH", 250f);//
		map.put("CROSS_TIP_RECT_HEIGHT",200f);//
		map.put("CROSS_TIP_TEXT_SIZE", 22f);//
	}
	
	
	
	public static void black(){
		 map.put("MAIN_BG",Color.rgb(24, 28, 41));
		 map.put("AXIS_Y_BG",Color.rgb(24, 28, 41));
		 map.put("AXIS_Y_BORDER_BG",Color.BLACK);
		 map.put("AXIS_Y_LINE_BG",Color.rgb(62, 63, 64));
		 map.put("AXIS_Y_TEXT_BG",Color.WHITE);
		 map.put("AXIS_X_BG",Color.rgb(24, 28, 41));
		 map.put("AXIS_X_BORDER_BG",Color.BLACK);
		 map.put("AXIS_X_LINE_BG",Color.rgb(62, 63, 64));
		 map.put("AXIS_X_TEXT_BG",Color.WHITE);
		 map.put("CANDLE_RISE_BG",Color.RED);
		 map.put("CANDLE_DOWN_BG",Color.rgb(0, 231, 178));
		 
		 map.put("CROSS_LINE_BG",Color.rgb(224, 224, 224));
		 map.put("CROSS_X_TEXT_BG",Color.BLACK);
		 map.put("CROSS_X_RECT_BG",Color.rgb(224, 224, 224));
		 map.put("CROSS_Y_TEXT_BG",Color.BLACK);
		 map.put("CROSS_Y_RECT_BG",Color.rgb(224, 224, 224));
		 
		 map.put("WINDOW_TOPLINE_BG",Color.BLACK);
		 
		 map.put("PRICE_LINE_RECT_BG",Color.rgb(224, 224, 224));
		 map.put("PRICE_LINE_TEXT_BG",Color.BLACK);
		 map.put("PRICE_LINE_BG",Color.rgb(224, 224, 224));
		 map.put("PRICE_TEXT_TEXT_BG",Color.WHITE);
		 
		 map.put("AXIS_X_TEXT_SIZE",20f);
		 map.put("CROSS_X_TEXT_SIZE",20f);
		 map.put("PRICE_TEXT_TEXT_SIZE",25f);
		 map.put("PRICE_LINE_TEXT_SIZE",20f);
		 
		 map.put("MV_LINE_BG",Color.rgb(7, 186, 12));
		 map.put("MV_BOLL_LINE_BG",Color.rgb(139, 2, 169));
		 map.put("MV_BOLL_TOP_BG",Color.rgb(139, 2, 169));
		 map.put("MV_BOLL_DOWN_BG",Color.rgb(139, 2, 169));
		 
		 map.put("ASSIST_POINT_BG", Color.rgb(66, 75, 255));// 
		 map.put("ASSIST_LINE_BG", Color.rgb(66, 75, 255));// 
	}
	
	public static void white(){
		 map.put("MAIN_BG",Color.WHITE);
		 map.put("AXIS_Y_BG",Color.WHITE);
		 map.put("AXIS_Y_BORDER_BG",Color.rgb(235, 235, 235));
		 map.put("AXIS_Y_LINE_BG",Color.rgb(237, 237, 237));
		 map.put("AXIS_Y_TEXT_BG",Color.BLACK);
		 
		 map.put("AXIS_X_BG",Color.WHITE);
		 map.put("AXIS_X_BORDER_BG",Color.rgb(235, 235, 235));
		 map.put("AXIS_X_LINE_BG",Color.rgb(237, 237, 237));
		 map.put("AXIS_X_TEXT_BG",Color.BLACK);
		 
		 map.put("CANDLE_RISE_BG",Color.rgb(245, 16, 16));
		 map.put("CANDLE_DOWN_BG",Color.rgb(48, 195, 83));
		 
		 map.put("CROSS_LINE_BG",Color.rgb(50, 50, 50));
		 map.put("CROSS_X_TEXT_BG",Color.WHITE);
		 map.put("CROSS_X_RECT_BG",Color.rgb(252,144,220));
		 map.put("CROSS_Y_TEXT_BG",Color.WHITE);
		 map.put("CROSS_Y_RECT_BG",Color.rgb(252,144,220));
		 
		 map.put("PRICE_LINE_BG",Color.BLUE);
		 map.put("PRICE_LINE_RECT_BG",Color.BLUE);
		 map.put("PRICE_LINE_TEXT_BG",Color.WHITE);
		 map.put("PRICE_TEXT_TEXT_BG",Color.rgb(93, 98, 110));
		 map.put("WINDOW_TOPLINE_BG",Color.DKGRAY);
		 
		 map.put("ASSIST_POINT_BG", Color.CYAN);// 
		 
		 map.put("AXIS_X_TEXT_SIZE",20f);
		 map.put("CROSS_X_TEXT_SIZE",20f);
		 map.put("PRICE_TEXT_TEXT_SIZE",25f);
		 map.put("PRICE_LINE_TEXT_SIZE",20f);
		 
		 map.put("TIMELINE_INFO_TEXT_BG",Color.BLUE);
		 map.put("TIMELINE_Y_UP_BG",Color.rgb(245, 16, 16));
		 map.put("TIMELINE_Y_DOWN_BG",Color.rgb(43, 193, 78));
		 map.put("TIMELINE_POINT_LEFT_BG",Color.rgb(69, 201, 100));
		 map.put("TIMELINE_POINT_RIGHT_BG",Color.rgb(69, 201, 100));
	}
	
	
	/**
	 * 此处添加后需要重置
	 * @param density
	 */
	public static void setDensity(float density){
		resetSize();
		DENSITY=density;
		setDensity("AXIS_Y_TEXT_SIZE");
		setDensity("AXIS_X_TEXT_SIZE");
		setDensity("CROSS_X_TEXT_SIZE");
		setDensity("PRICE_TEXT_TEXT_SIZE");
		setDensity("PRICE_LINE_TEXT_SIZE");
		setDensity("MACD_TEXT_SIZE");
		setDensity("TIMELINE_INFO_TEXT_SIZE");//("TIMELINE_INFO_TEXT_SIZE");//
		setDensity("TIMELINE_Y_SPAN");//("TIMELINE_INFO_TEXT_SIZE");//
		setDensity("CROSS_TIP_TEXT_SIZE");//("TIMELINE_INFO_TEXT_SIZE");//
		setDensity("CROSS_TIP_RECT_WIDTH");//("TIMELINE_INFO_TEXT_SIZE");//
		setDensity("CROSS_TIP_RECT_HEIGHT");//("TIMELINE_INFO_TEXT_SIZE");//		
	}
	
	private static void setDensity(String key){
		float old=20;
		Object obj=map.get(key);
		if (obj instanceof Integer) {
			Integer i = (Integer) obj;
			old=i;
		}
		if (obj instanceof Float) {
			Float f = (Float) obj;
			old=f;
		}
		map.put(key,old/DENSITYDEFAULT*DENSITY);
	}
	
	public static float dip2px(float dip){
		return (dip*DENSITY+0.5f);
	}
	
	public static float dip2pxExt(float dip){
		return (dip/DENSITYDEFAULT*DENSITY);
	}
	
	
	static{
		 reset();
		 white();
	}
}
