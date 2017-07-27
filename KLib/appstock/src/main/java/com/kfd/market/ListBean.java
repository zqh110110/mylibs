package com.kfd.market;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.kfd.bean.Entity;
import com.kfd.bean.StockBean;
import com.kfd.common.LogUtils;

/**
 * 数据列表类
 * 
 * @author 朱继洋 QQ7617812 2013-5-22
 */
public class ListBean extends Entity {

	private static final long serialVersionUID = 1L;
	/*
	 * public final static int CATALOG_ALL = 2; public final static int
	 * CATALOG_INVALIDATE = 0; public final static int CATALOG_VALIDATE = 1;
	 */

	private String maincatalog;
	private int platecatalog;
	private int pageSize;
	private int pageCount;
	private ArrayList<StockBean> list = new ArrayList<StockBean>();

	public String getMaincatalog() {
		return maincatalog;
	}

	public void setMaincatalog(String maincatalog) {
		this.maincatalog = maincatalog;
	}

	public int getPlatecatalog() {
		return platecatalog;
	}

	public void setPlatecatalog(int platecatalog) {
		this.platecatalog = platecatalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public ArrayList<StockBean> getList() {
		return list;
	}

	public void setList(ArrayList<StockBean> list) {
		this.list = list;
	}

	/**
	 * 数据解析,列表解析
	 * 
	 * @param string
	 * @return
	 */

	public static ListBean parseData(String string) {
		ListBean listBean = new ListBean();
		try {
		
		Gson gson  = new Gson();
		StockeSelect stockeSelect =  gson.fromJson(string, StockeSelect.class);
		
		ArrayList<StockeBeanSelect> arrayList = stockeSelect.data;
		for (int i = 0; i < arrayList.size(); i++) {
			StockeBeanSelect   select  = arrayList.get(i);
			StockBean stockBean = new StockBean();
			// stklabel,name,open,preclose,high,low,nowprice,vol,amount,mairu,maichu,zhangdie,zhangfu,color,time
				stockBean.setStockcode(select.stklabel);
				stockBean.setStockname(select.name);
				stockBean.setTodayopen(select.open);
				stockBean.setYesterdayclose(select.preclose);
				stockBean.setHigh(select.high);
				stockBean.setLow(select.low);
				stockBean.setRecentprice(select.nowprice);
				stockBean.setVolume(select.vol);
				stockBean.setAmount(select.amount);
				stockBean.setMaichu(select.maichu);
				stockBean.setMairu(select.mairu);
				stockBean.setUpdownrange(select.zhangdie);
				stockBean.setUpdownamount(select.zhangfu);
				stockBean.setColor(select.color);
				stockBean.setTime(select.time);
				listBean.getList().add(stockBean);
		}
		
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listBean;

	}

	/**
	 * 数据解析,单条数据解析
	 * 
	 * @param string
	 * @return
	 */

	public static ListBean parseSingleData(String string, String stockON) {
		
		ListBean listBean = new ListBean();
		try {
		
		if (string != null && string.length() > 0) {
			JSONObject jsonObject = JSONObject.parseObject(string);
			JSONObject jsonObject1 = jsonObject.getJSONObject("data");
			String total = jsonObject1.getString("counts");
			JSONObject jsonObject2 = jsonObject1.getJSONObject("lists");
			
			StockBean stockBean = new StockBean();
			JSONObject jsonObject3 = jsonObject2.getJSONObject(stockON);
			stockBean.setStockcode(jsonObject3.getString("stklabel"));
			stockBean.setStockname(jsonObject3.getString("name"));
			stockBean.setTodayopen(jsonObject3.getString("open"));
			stockBean.setYesterdayclose(jsonObject3.getString("preclose"));
			stockBean.setHigh(jsonObject3.getString("high"));
			stockBean.setLow(jsonObject3.getString("low"));
			stockBean.setRecentprice(jsonObject3.getString("nowprice"));
			stockBean.setVolume(jsonObject3.getString("vol"));
			stockBean.setAmount(jsonObject3.getString("amount"));
			stockBean.setMairu(jsonObject3.getString("mairu"));
			stockBean.setMaichu(jsonObject3.getString("maichu"));
			stockBean.setUpdownamount(jsonObject3.getString("zhangdie"));
			stockBean.setUpdownrange(jsonObject3.getString("zhangfu") + "%");
			stockBean.setColor(jsonObject3.getString("color"));
			stockBean.setTime(jsonObject3.getString("time"));
			listBean.getList().add(stockBean);

		}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listBean;
	}
	/**
	 * GSON解析搜索的数据
	 * @param string
	 * @return
	 */
	public static ListBean parseSingleData(String string) {
		ListBean listBean = new ListBean();
		Gson gson  = new Gson();
		StockeSelect stockeSelect =  gson.fromJson(string, StockeSelect.class);
		
		ArrayList<StockeBeanSelect> arrayList = stockeSelect.data;
		for (int i = 0; i < arrayList.size(); i++) {
			StockeBeanSelect   select  = arrayList.get(i);
			StockBean stockBean = new StockBean();
			// stklabel,name,open,preclose,high,low,nowprice,vol,amount,mairu,maichu,zhangdie,zhangfu,color,time
			stockBean.setStockcode(select.stklabel);
			stockBean.setStockname(select.name);
			stockBean.setTodayopen(select.open);
			stockBean.setYesterdayclose(select.preclose);
			stockBean.setHigh(select.high);
			stockBean.setLow(select.low);
			stockBean.setRecentprice(select.nowprice);
			stockBean.setVolume(select.vol);
			stockBean.setAmount(select.amount);
			stockBean.setMaichu(select.maichu);
			stockBean.setMairu(select.mairu);
			stockBean.setUpdownrange(select.zhangdie);
			stockBean.setUpdownamount(select.zhangfu);
			stockBean.setColor(select.color);
			stockBean.setTime(select.time);
			listBean.getList().add(stockBean);
		}
		return listBean;
	}
	
	class StockeSelect{
		public int status;
		public boolean  result;
		public String total;
		public int  num;
		public String message;
		public  ArrayList<StockeBeanSelect> data;
	}
	/*class StockData{
		
		public String counts;
	}*/
	
	class StockeBeanSelect{
		public String stklabel,name,open,preclose,high,low,nowprice,vol,amount,mairu,maichu,zhangdie,zhangfu,color,time;
		
	}
	

}
