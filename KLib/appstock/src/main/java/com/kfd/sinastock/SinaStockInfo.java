/*
 * Copyright zh.weir.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kfd.sinastock;

import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;

/**
 * 新浪股票信息
 * 
 * @author 朱继洋 QQ7617812 2013-5-24
 */
public class SinaStockInfo {

	// 股票名字
	private String mName;
	// 今日开盘价
	private float mTodayPrice;
	// 昨日收盘价
	private float mYestodayPrice;
	// 当前价
	private float mNowPrice;
	// 今日最高价
	private float mHighestPrice;
	// 今日最低价
	private float mLowestPrice;
	// 买一价
	private float mBuy1Price;
	// 卖一价
	private float mSell1Price;
	// 成交股票数，单位“股”。100股为1手。
	private long mTradeCount;
	// 成交额，单位“元”。一般需要转换成“万元”。
	private float mTradeMoney;

	// 日期
	private String mDate;
	// 时间
	private String mTime;
	private long buy1Count;
	private float buy1Price;
	private long sell1Count;
	private float sell1Price;

	private long buy2Count;
	private float buy2Price;
	private long sell2Count;
	private float sell2Price;

	private long buy3Count;
	private float buy3Price;
	private long sell3Count;
	private float sell3Price;

	private long buy4Count;
	private float buy4Price;
	private long sell4Count;
	private float sell4Price;

	private long buy5Count;
	private float buy5Price;
	private long sell5Count;
	private float sell5Price;

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public float getmTodayPrice() {
		return mTodayPrice;
	}

	public void setmTodayPrice(float mTodayPrice) {
		this.mTodayPrice = mTodayPrice;
	}

	public float getmYestodayPrice() {
		return mYestodayPrice;
	}

	public void setmYestodayPrice(float mYestodayPrice) {
		this.mYestodayPrice = mYestodayPrice;
	}

	public float getmNowPrice() {
		return mNowPrice;
	}

	public void setmNowPrice(float mNowPrice) {
		this.mNowPrice = mNowPrice;
	}

	public float getmHighestPrice() {
		return mHighestPrice;
	}

	public void setmHighestPrice(float mHighestPrice) {
		this.mHighestPrice = mHighestPrice;
	}

	public float getmLowestPrice() {
		return mLowestPrice;
	}

	public void setmLowestPrice(float mLowestPrice) {
		this.mLowestPrice = mLowestPrice;
	}

	public float getmBuy1Price() {
		return mBuy1Price;
	}

	public void setmBuy1Price(float mBuy1Price) {
		this.mBuy1Price = mBuy1Price;
	}

	public float getmSell1Price() {
		return mSell1Price;
	}

	public void setmSell1Price(float mSell1Price) {
		this.mSell1Price = mSell1Price;
	}

	public long getmTradeCount() {
		return mTradeCount;
	}

	public void setmTradeCount(long mTradeCount) {
		this.mTradeCount = mTradeCount;
	}

	public float getmTradeMoney() {
		return mTradeMoney;
	}

	public void setmTradeMoney(Float mTradeMoney) {
		this.mTradeMoney = mTradeMoney;
	}

	public String getmDate() {
		return mDate;
	}

	public void setmDate(String mDate) {
		this.mDate = mDate;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public long getBuy1Count() {
		return buy1Count;
	}

	public void setBuy1Count(long buy1Count) {
		this.buy1Count = buy1Count;
	}

	public float getBuy1Price() {
		return buy1Price;
	}

	public void setBuy1Price(float buy1Price) {
		this.buy1Price = buy1Price;
	}

	public long getSell1Count() {
		return sell1Count;
	}

	public void setSell1Count(long sell1Count) {
		this.sell1Count = sell1Count;
	}

	public float getSell1Price() {
		return sell1Price;
	}

	public void setSell1Price(float sell1Price) {
		this.sell1Price = sell1Price;
	}

	public long getBuy2Count() {
		return buy2Count;
	}

	public void setBuy2Count(long buy2Count) {
		this.buy2Count = buy2Count;
	}

	public float getBuy2Price() {
		return buy2Price;
	}

	public void setBuy2Price(float buy2Price) {
		this.buy2Price = buy2Price;
	}

	public long getSell2Count() {
		return sell2Count;
	}

	public void setSell2Count(long sell2Count) {
		this.sell2Count = sell2Count;
	}

	public float getSell2Price() {
		return sell2Price;
	}

	public void setSell2Price(float sell2Price) {
		this.sell2Price = sell2Price;
	}

	public long getBuy3Count() {
		return buy3Count;
	}

	public void setBuy3Count(long buy3Count) {
		this.buy3Count = buy3Count;
	}

	public float getBuy3Price() {
		return buy3Price;
	}

	public void setBuy3Price(float buy3Price) {
		this.buy3Price = buy3Price;
	}

	public long getSell3Count() {
		return sell3Count;
	}

	public void setSell3Count(long sell3Count) {
		this.sell3Count = sell3Count;
	}

	public float getSell3Price() {
		return sell3Price;
	}

	public void setSell3Price(float sell3Price) {
		this.sell3Price = sell3Price;
	}

	public long getBuy4Count() {
		return buy4Count;
	}

	public void setBuy4Count(long buy4Count) {
		this.buy4Count = buy4Count;
	}

	public float getBuy4Price() {
		return buy4Price;
	}

	public void setBuy4Price(float buy4Price) {
		this.buy4Price = buy4Price;
	}

	public long getSell4Count() {
		return sell4Count;
	}

	public void setSell4Count(long sell4Count) {
		this.sell4Count = sell4Count;
	}

	public float getSell4Price() {
		return sell4Price;
	}

	public void setSell4Price(float sell4Price) {
		this.sell4Price = sell4Price;
	}

	public long getBuy5Count() {
		return buy5Count;
	}

	public void setBuy5Count(long buy5Count) {
		this.buy5Count = buy5Count;
	}

	public float getBuy5Price() {
		return buy5Price;
	}

	public void setBuy5Price(float buy5Price) {
		this.buy5Price = buy5Price;
	}

	public long getSell5Count() {
		return sell5Count;
	}

	public void setSell5Count(long sell5Count) {
		this.sell5Count = sell5Count;
	}

	public float getSell5Price() {
		return sell5Price;
	}

	public void setSell5Price(float sell5Price) {
		this.sell5Price = sell5Price;
	}

	public long getmCount() {
		return mCount;
	}

	public void setmCount(long mCount) {
		this.mCount = mCount;
	}

	public float getmPrice() {
		return mPrice;
	}

	public void setmPrice(float mPrice) {
		this.mPrice = mPrice;
	}

	// 数量。单位为“股”。100股为1手。
	long mCount;
	// 价格。
	float mPrice;

	public SinaStockInfo() {
		super();
	}

	/**
	 * 从一行响应字符串中解析得到SinaStockInfo数据结构。
	 * 
	 * @param source
	 *            参数的格式如： var hq_str_sh601006=
	 *            "大秦铁路,7.69,7.70,7.62,7.72,7.61,7.61,7.62,46358694,355190642,565201,7.61,984000,7.60,211900,7.59,476600,7.58,238500,7.57,295518,7.62,217137,7.63,241500,7.64,345900,7.65,419400,7.66,2012-02-29,15:03:07"
	 *            ;
	 * 
	 * @return SinaStockInfo
	 * @throws ParseStockInfoException
	 */
	public static SinaStockInfo parseStockInfo(String source)
			throws ParseStockInfoException {
		System.out.println(source);
		int start = source.indexOf('\"');
		String targetString = source.substring(start + 1, source.length() - 2);

		String[] infoStr = targetString.split(",");

		/*
		 * if (infoStr.length != 32) { throw new ParseStockInfoException(); }
		 */
		SinaStockInfo sinaStockInfo = new SinaStockInfo();
		
		try {
			
			sinaStockInfo.setmName(infoStr[0]);
			sinaStockInfo.setmTodayPrice(Float.parseFloat(infoStr[1]));
			sinaStockInfo.setmYestodayPrice(Float.parseFloat(infoStr[2]));
			sinaStockInfo.setmNowPrice(Float.parseFloat(infoStr[3]));
			sinaStockInfo.setmHighestPrice(Float.parseFloat(infoStr[4]));
			sinaStockInfo.setmLowestPrice(Float.parseFloat(infoStr[5]));
			sinaStockInfo.setmBuy1Price(Float.parseFloat(infoStr[6]));
			sinaStockInfo.setmSell1Price(Float.parseFloat(infoStr[7]));
			sinaStockInfo.setmTradeCount(Long.parseLong(infoStr[8]) / 100);
			sinaStockInfo.setmTradeMoney(Float.parseFloat(infoStr[9]) / 10000);// 转换成万元
			sinaStockInfo.setBuy1Count(Long.parseLong(infoStr[10]) / 100);
			
			sinaStockInfo.setBuy1Price(Float.parseFloat(infoStr[11]));
			sinaStockInfo.setBuy2Count(Long.parseLong(infoStr[12]) / 100);
			sinaStockInfo.setBuy2Price(Float.parseFloat(infoStr[13]));
			sinaStockInfo.setBuy3Count(Long.parseLong(infoStr[14]) / 100);
			sinaStockInfo.setBuy3Price(Float.parseFloat(infoStr[15]));
			sinaStockInfo.setBuy4Count(Long.parseLong(infoStr[16]) / 100);
			sinaStockInfo.setBuy4Price(Float.parseFloat(infoStr[17]));
			sinaStockInfo.setBuy5Count(Long.parseLong(infoStr[18]) / 100);
			sinaStockInfo.setBuy5Price(Float.parseFloat(infoStr[19]));
			
			sinaStockInfo.setSell1Count(Long.parseLong(infoStr[20]) / 100);
			sinaStockInfo.setSell1Price(Float.parseFloat(infoStr[21]));
			sinaStockInfo.setSell2Count(Long.parseLong(infoStr[22]) / 100);
			sinaStockInfo.setSell2Price(Float.parseFloat(infoStr[23]));
			sinaStockInfo.setSell3Count(Long.parseLong(infoStr[24]) / 100);
			sinaStockInfo.setSell3Price(Float.parseFloat(infoStr[25]));
			sinaStockInfo.setSell4Count(Long.parseLong(infoStr[26]) / 100);
			sinaStockInfo.setSell4Price(Float.parseFloat(infoStr[27]));
			sinaStockInfo.setSell5Count(Long.parseLong(infoStr[28]) / 100);
			sinaStockInfo.setSell5Price(Float.parseFloat(infoStr[29]));
			// 日期进行处理
			sinaStockInfo.setmDate(infoStr[30]);
			sinaStockInfo.setmTime(infoStr[31]);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		return sinaStockInfo;
	}

	/**
	 * 获取股票名称
	 * 
	 * @return 股票名称
	 */
	public String getName() {
		return mName;
	}

	/**
	 * 获取今日开盘价
	 * 
	 * @return 今日股票开盘价
	 */
	public float getTodayPrice() {
		return mTodayPrice;
	}

	/**
	 * 获取昨日收盘价
	 * 
	 * @return 昨日收盘价
	 */
	public float getYestodayPrice() {
		return mYestodayPrice;
	}

	/**
	 * 获取当前股价
	 * 
	 * @return 当前股价
	 */
	public float getNowPrice() {
		return mNowPrice;
	}

	/**
	 * 获取今日最高价
	 * 
	 * @return 今日最高价
	 */
	public float getHighestPrice() {
		return mHighestPrice;
	}

	/**
	 * 获取今日最低价
	 * 
	 * @return 今日最低价
	 */
	public float getLowestPrice() {
		return mLowestPrice;
	}

	/**
	 * 获取股票交易量。单位为“股”，100股为1手，请注意转换。
	 * 
	 * @return 股票交易量
	 */
	public long getTradeCount() {
		return mTradeCount;
	}

	/**
	 * 获取股票交易额。单位为“元”，如需显示“万元”，请注意转换。
	 * 
	 * @return 股票交易额
	 */
	public float getTradeMoney() {
		return mTradeMoney;
	}

	/**
	 * 获取对应股票信息的日期。例如周末，或者其他休市期间获取的数据将不是实时的。
	 * 
	 * @return 获取对应股票信息的日期。
	 */
	public String getDate() {
		return mDate;
	}

	/**
	 * 获取对应股票信息的时间。例如周末，或者其他休市期间获取的数据将不是实时的。
	 * 
	 * @return 获取对应股票信息的时间。
	 */
	public String getTime() {
		return mTime;
	}

	public static class ParseStockInfoException extends Exception {
		public ParseStockInfoException() {
			super("Parse StockInfo error!");
		}
	}

	@Override
	public String toString() {

		return "SinaStockInfo [mName=" + mName + ", mTodayPrice=" + mTodayPrice
				+ ", mYestodayPrice=" + mYestodayPrice + ", mNowPrice="
				+ mNowPrice + ", mHighestPrice=" + mHighestPrice
				+ ", mLowestPrice=" + mLowestPrice + ", mBuy1Price="
				+ mBuy1Price + ", mSell1Price=" + mSell1Price
				+ ", mTradeCount=" + mTradeCount + ", mTradeMoney="
				+ mTradeMoney + ", mDate=" + mDate + ", mTime=" + mTime
				+ ", buy1Count=" + buy1Count + ", buy1Price=" + buy1Price
				+ ", sell1Count=" + sell1Count + ", sell1Price=" + sell1Price
				+ ", buy2Count=" + buy2Count + ", buy2Price=" + buy2Price
				+ ", sell2Count=" + sell2Count + ", sell2Price=" + sell2Price
				+ ", buy3Count=" + buy3Count + ", buy3Price=" + buy3Price
				+ ", sell3Count=" + sell3Count + ", sell3Price=" + sell3Price
				+ ", buy4Count=" + buy4Count + ", buy4Price=" + buy4Price
				+ ", sell4Count=" + sell4Count + ", sell4Price=" + sell4Price
				+ ", buy5Count=" + buy5Count + ", buy5Price=" + buy5Price
				+ ", sell5Count=" + sell5Count + ", sell5Price=" + sell5Price
				+ ", mCount=" + mCount + ", mPrice=" + mPrice + "]";
	}

}
