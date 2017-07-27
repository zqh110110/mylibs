package com.kfd.bean;

import com.alibaba.fastjson.JSONObject;

public class StockInfo {
	private String codenam, usablemoney, hotCommission, hightCommission,
			payStockNum, code, nowprice;

	public String getCodenam() {
		return codenam;
	}

	public void setCodenam(String codenam) {
		this.codenam = codenam;
	}

	public String getUsablemoney() {
		return usablemoney;
	}

	public void setUsablemoney(String usablemoney) {
		this.usablemoney = usablemoney;
	}

	public String getHotCommission() {
		return hotCommission;
	}

	public void setHotCommission(String hotCommission) {
		this.hotCommission = hotCommission;
	}

	public String getHightCommission() {
		return hightCommission;
	}

	public void setHightCommission(String hightCommission) {
		this.hightCommission = hightCommission;
	}

	public String getPayStockNum() {
		return payStockNum;
	}

	public void setPayStockNum(String payStockNum) {
		this.payStockNum = payStockNum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNowprice() {
		return nowprice;
	}

	public void setNowprice(String nowprice) {
		this.nowprice = nowprice;
	}

	public static StockInfo parseData(String string) {
		StockInfo stockInfo = new StockInfo();
		try {
			JSONObject jsonObject = JSONObject.parseObject(string);
			stockInfo.code = jsonObject.getString("code");
			stockInfo.codenam = jsonObject.getString("codename");
			stockInfo.setUsablemoney(jsonObject.getString("usablemoney"));
			stockInfo.setHotCommission(jsonObject.getString("hotCommission"));
			stockInfo.setHightCommission(jsonObject
					.getString("hightCommission"));
			stockInfo.setPayStockNum(jsonObject.getString("payStockNum"));
			stockInfo.setNowprice(jsonObject.getString("nowprice"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stockInfo;
	}
}