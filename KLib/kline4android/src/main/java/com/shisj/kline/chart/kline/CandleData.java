package com.shisj.kline.chart.kline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CandleData {

	/**
	 * 分别记录开盘价、收盘价、最高价、最低价
	 */
	private float opened,closing,highest,lowest;
	private Date date;
	private Object data;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public CandleData(){}
	public CandleData(float opened, float closing, float highest, float lowest) {
		super();
		this.opened = opened;
		this.closing = closing;
		this.highest = highest;
		this.lowest = lowest;
	}

	public float getOpened() {
		return opened;
	}

	public void setOpened(float opened) {
		this.opened = opened;
	}

	public float getClosing() {
		return closing;
	}

	public void setClosing(float closing) {
		this.closing = closing;
	}

	public float getHighest() {
		return highest;
	}

	public void setHighest(float highest) {
		this.highest = highest;
	}

	public float getLowest() {
		return lowest;
	}

	public void setLowest(float lowest) {
		this.lowest = lowest;
	}
	
	public void setDate(String date,String dateFmt){
		if(dateFmt==null)
			dateFmt="yyyyMMddhhmm";
		SimpleDateFormat fmt=new SimpleDateFormat(dateFmt);
		try {
			this.date=fmt.parse(date);
		} catch (ParseException e) {
			this.date=null;
		}
		
	}
	public void setDate(Date date){
		this.date=date;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public String getDate(String fmt){
		if(fmt==null)
			fmt="yyyy-MM-dd HH:mm";
		SimpleDateFormat format=new SimpleDateFormat(fmt);
		return format.format(this.date);
	}
	
	
}
