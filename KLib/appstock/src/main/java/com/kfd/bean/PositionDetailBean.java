package com.kfd.bean;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PositionDetailBean extends Entity {

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

	public static PositionDetailBean parseData(String string) {
		// LogUtils.log("test", "市场    "+string);
		PositionDetailBean listBean = new PositionDetailBean();
		try {
			if (string != null && string.length() > 0) {
				JSONObject jsonObject = JSONObject.parseObject(string);
				// JSONObject jsonObject1 = jsonObject.getJSONObject("data");
				// String total =jsonObject1.getString("counts");
				JSONArray array = jsonObject.getJSONArray("data");
				
				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					StockBean stockBean = new StockBean();
					
					listBean.getList().add(stockBean);
					
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return listBean;

	}

}