package com.shisj.kline.chart.kline.ext;

/**
 * 记录相关指标的数据
 * @author shishengjie
 *
 */
public class CandleDataQuota {

	private float shortEMA=Float.MIN_VALUE,longEMA=Float.MIN_VALUE;
	private float diff=Float.MIN_VALUE,dea=Float.MIN_VALUE;
	public float getDiff() {
		return diff;
	}
	public void setDiff(float diff) {
		this.diff = diff;
	}
	public float getDea() {
		return dea;
	}
	public void setDea(float dea) {
		this.dea = dea;
	}
	public float getShortEMA() {
		return shortEMA;
	}
	public void setShortEMA(float shortEMA) {
		this.shortEMA = shortEMA;
	}
	public float getLongEMA() {
		return longEMA;
	}
	public void setLongEMA(float longEMA) {
		this.longEMA = longEMA;
	}
	
}
