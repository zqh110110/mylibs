/*
 * Copyright (c) 2014 The MaMaHelp_5.2.1 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */

package com.kfd.api;

/**
 * @Function: 时间差
 * @author xiaobo.lin
 * @version
 * @Date: 2014年2月24日 下午3:35:57
 */
public class TimeDiff {
	private long day;//计算差多少天
    private long hour;//计算差多少小时
    private long min;//计算差多少分钟
    private long sec;//计算差多少秒
	public long getDay() {
		return day;
	}
	public void setDay(long day) {
		this.day = day;
	}
	public long getHour() {
		return hour;
	}
	public void setHour(long hour) {
		this.hour = hour;
	}
	public long getMin() {
		return min;
	}
	public void setMin(long min) {
		this.min = min;
	}
	public long getSec() {
		return sec;
	}
	public void setSec(long sec) {
		this.sec = sec;
	}
	public TimeDiff(){
		
	}
   public TimeDiff(long day, long hour, long min , long sec){
		this.day=day;
		this.hour=hour;
		this.min=min;
		this.sec=sec;
	}
}

	
