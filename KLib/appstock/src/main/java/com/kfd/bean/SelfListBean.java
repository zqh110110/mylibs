package com.kfd.bean;

import java.util.ArrayList;

import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kfd.common.LogUtils;

/**
 * 数据列表类
 * 
 * @author 朱继洋 QQ7617812 2013-5-22
 */
public class SelfListBean extends Entity {

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
	 * 数据解析
	 * 
	 * @param string
	 * @return
	 */

	public static SelfListBean parseData(String string) {
		
		SelfListBean listBean = new SelfListBean();
		
		try {
			if (string != null && string.length() > 0) {
				JSONObject jsonObject = JSONObject.parseObject(string);
				// JSONObject jsonObject1 = jsonObject.getJSONObject("data");
				// String total =jsonObject1.getString("counts");
				JSONArray array = jsonObject.getJSONArray("data");
				if (array!=null &&array.size()>0) {
					for (int i = 0; i < array.size(); i++) {
						JSONObject jsonObject2 = array.getJSONObject(i);
						StockBean stockBean = new StockBean();
						if (jsonObject2!=null) {
							stockBean.setStockcode(jsonObject2.getString("stklabel"));
							stockBean.setStockname(jsonObject2.getString("name"));
							stockBean.setTodayopen(jsonObject2.getString("open"));
							stockBean.setYesterdayclose(jsonObject2.getString("preclose"));
							stockBean.setHigh(jsonObject2.getString("high"));
							stockBean.setLow(jsonObject2.getString("low"));
							stockBean.setRecentprice(jsonObject2.getString("nowprice"));
							stockBean.setVolume(jsonObject2.getString("vol"));
							stockBean.setMairu(jsonObject2.getString("mairu"));
							stockBean.setMaichu(jsonObject2.getString("maichu"));
							stockBean.setUpdownamount(jsonObject2.getString("zhangdie"));
							stockBean
							.setUpdownrange(jsonObject2.getString("zhangfu") + "%");
							stockBean.setColor(jsonObject2.getString("color"));
							stockBean.setTime(jsonObject2.getString("time"));
						}
						listBean.getList().add(stockBean);
						
					}
				}else {
					
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		return listBean;

	}

}
