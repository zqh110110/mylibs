package com.shisj.kline.chart.kline;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.shisj.kline.chart.core.AbstractChart;




public abstract class CandleDataProvider {

	public static String DEFAULT_FORMAT[]=null;
	public static int priceDigit=5;
	public static int precentDigit=3;
	public String currType;//币种对
	public String timeFormate;//x轴日期格式
	public String dateType;//时间类型
	
	/**
	 * 获取当前时间的数据
	 * @param count
	 * @return
	 */
	public abstract List<CandleData> getInitData();
	
	public abstract float getLastClosePrice();
	/**
	 * 获取指定时间开始的后续数据
	 * @param begin
	 * @param count
	 * @return
	 */
	public abstract void getData(AbstractChart kchart,Date begin,int count);
	
	/**
	 * 获取最新的数据
	 * @return
	 */
	public abstract int getLatest(AbstractChart kchart);
	
	public  String getDateTypeShow(){
		return "";
	}

	public String getCurrType() {
		return currType;
	}

	public void setCurrType(String currType) {
		this.currType = currType;
	}

	public String getTimeFormate() {
		return timeFormate;
	}

	public void setTimeFormate(String timeformate) {
		this.timeFormate = timeformate;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	
	
	
}
