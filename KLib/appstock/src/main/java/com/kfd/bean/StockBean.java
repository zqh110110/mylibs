package com.kfd.bean;

import java.io.Serializable;

import com.kfd.common.LogUtils;

/**
 * 股票实体类
 * 
 * @author 朱继洋 QQ7617812 2013-5-22
 */
public class StockBean implements Serializable {

	private String stockcode, stockname, recentprice, updownrange,
			updownamount, volume, todayopen, yesterdayclose, amount;
	private String high, low, mairu, maichu, color, time;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * 持仓明细
	 * 
	 * @return
	 */

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getMairu() {
		return mairu;
	}

	public void setMairu(String mairu) {
		this.mairu = mairu;
	}

	public String getMaichu() {
		return maichu;
	}

	public void setMaichu(String maichu) {
		this.maichu = maichu;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 涨跌幅
	 * 
	 * @return
	 */
	public String getStockcode() {
		return stockcode;
	}

	public void setStockcode(String stockcode) {
		this.stockcode = stockcode;
	}

	public String getStockname() {
		return stockname;
	}

	public void setStockname(String stockname) {
		this.stockname = stockname;
	}

	public String getRecentprice() {
		return recentprice;
	}

	public void setRecentprice(String recentprice) {
		this.recentprice = recentprice;
	}

	/**
	 * 涨跌幅
	 * 
	 * @return
	 */
	public String getUpdownrange() {
		return updownrange;
	}

	public void setUpdownrange(String updownrange) {
		this.updownrange = updownrange;
	}

	/**
	 * 涨跌额
	 * 
	 * @return
	 */
	public String getUpdownamount() {
		return updownamount;
	}

	public void setUpdownamount(String updownamount) {
		this.updownamount = updownamount;
	}

	/**
	 * 成交量
	 * 
	 * @return
	 */
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getTodayopen() {
		return todayopen;
	}

	public void setTodayopen(String todayopen) {
		this.todayopen = todayopen;
	}

	public String getYesterdayclose() {
		return yesterdayclose;
	}

	public void setYesterdayclose(String yesterdayclose) {
		this.yesterdayclose = yesterdayclose;
	}

	@Override
	public String toString() {
		LogUtils.log("test", "StockBean [stockcode=" + stockcode + ", stockname=" + stockname
				+ ", recentprice=" + recentprice + ", updownrange="
				+ updownrange + ", updownamount=" + updownamount + ", volume="
				+ volume + ", todayopen=" + todayopen + ", yesterdayclose="
				+ yesterdayclose + ", amount=" + amount + ", high=" + high
				+ ", low=" + low + ", mairu=" + mairu + ", maichu=" + maichu
				+ ", color=" + color + ", time=" + time + "]");
		return "StockBean [stockcode=" + stockcode + ", stockname=" + stockname
				+ ", recentprice=" + recentprice + ", updownrange="
				+ updownrange + ", updownamount=" + updownamount + ", volume="
				+ volume + ", todayopen=" + todayopen + ", yesterdayclose="
				+ yesterdayclose + ", amount=" + amount + ", high=" + high
				+ ", low=" + low + ", mairu=" + mairu + ", maichu=" + maichu
				+ ", color=" + color + ", time=" + time + "]";
	}
	

}
