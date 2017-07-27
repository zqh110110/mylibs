package com.kfd.bean;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;

public class PositionListBean extends Entity {

	private static final long serialVersionUID = 1L;
	/*
	 * public final static int CATALOG_ALL = 2; public final static int
	 * CATALOG_INVALIDATE = 0; public final static int CATALOG_VALIDATE = 1;
	 */

	private String maincatalog;
	private int platecatalog;
	private int pageSize;
	private int pageCount;
	private ArrayList<PositionBean> list = new ArrayList<PositionBean>();

	public ArrayList<PositionBean> getList() {
		return list;
	}

	public void setList(ArrayList<PositionBean> list) {
		this.list = list;
	}

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

	/**
	 * 数据解析
	 * 
	 * @param string
	 * @return
	 */

	public static PositionListBean parseData(String string) {
		LogUtils.log("test", "持仓明细    " + string);

		PositionListBean listBean = new PositionListBean();
		try {
			
			Gson gson  = new Gson();
			JSONObject jsonObject = JSONObject.parseObject(string);
			JSONObject jsonObject3 = jsonObject.getJSONObject("data");
			PositionBeanSelect PositionSelect =  gson.fromJson(jsonObject3.toString(), PositionBeanSelect.class);
			ArrayList<PositionBean> arrayList = PositionSelect.list;
			if (string != null && string.length() > 0) {

				for (int i = 0; i < arrayList.size(); i++) {
					PositionBean select = arrayList.get(i);
					PositionBean positionBean = new PositionBean();
					positionBean.setPid(select.id);
					positionBean.setNumber(select.number);
					String createtime = select.create_time;

					positionBean.setCreate_time(createtime);

					positionBean.setStockcode(select.stock_code);
					positionBean.setStock_class(select.stock_class);
					positionBean.setType(select.type);
					positionBean.setCreate_price(select.create_price);
					positionBean.setLow_price(select.low_price);
					positionBean.setHigh_price(select.high_price);
					positionBean.setNum(select.num);
					positionBean.setCreate_cost(select.create_cost);
					positionBean
							.setHot_cost(select.hot_cost);
					positionBean.setCloseout_price(select.closeout_price);
					positionBean.setCloseout_cost(select.closeout_cost);
					positionBean.setQzpc_cost(select.qzpc_cost);
					positionBean.setGzf_cost(select.gzf_cost);
					positionBean.setLive_cost(select.live_cost);
					positionBean.setLive_day(select.live_day);
					positionBean.setState(select.state);
					positionBean.setNowprice(select.now_price);
					positionBean.setName(select.name);
					positionBean.setFloat_vk(select.yk);
					positionBean.setStock_name(select.stock_name);

					listBean.getList().add(positionBean);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listBean;

	}
	
	class PositionSelect{
		public boolean  result;
		public int status;
		public String message;
		public ArrayList<List<PositionBean>> data;
	}
	
	class PositionBeanSelect{
		public ArrayList<PositionBean> list;
	}
	
	public static PositionListBean parseData1(String string) {
		LogUtils.log("test", "持仓明细    " + string);

		PositionListBean listBean = new PositionListBean();
		try {
			
			JSONObject jsonObject = JSONObject.parseObject(string);
			JSONObject jsonObject3 = jsonObject.getJSONObject("data");
			JSONArray jsonObject4 = jsonObject3.getJSONArray("list");
			if (string != null && string.length() > 0) {

				for (int i = 0; i < jsonObject4.size(); i++) {
					JSONObject select = (JSONObject) jsonObject4.get(i);
					PositionBean positionBean = new PositionBean();
					positionBean.setPid(select.getString("id"));
					positionBean.setNumber(select.getString("number"));
					String createtime = select.getString("create_time");

					positionBean.setCreate_time(createtime);

					positionBean.setStockcode(select.getString("stock_code"));
					positionBean.setStock_class(select.getString("stock_class"));
					positionBean.setType(select.getString("type"));
					positionBean.setCreate_price(select.getString("create_price"));
					positionBean.setLow_price(select.getString("low_price"));
					positionBean.setHigh_price(select.getString("high_price"));
					positionBean.setNum(select.getString("num"));
					positionBean.setCreate_cost(select.getString("create_cost"));
					positionBean
							.setHot_cost(select.getString("hot_cost"));
					positionBean.setCloseout_price(select.getString("closeout_price"));
					positionBean.setCloseout_cost(select.getString("closeout_cost"));
					positionBean.setQzpc_cost(select.getString("qzpc_cost"));
					positionBean.setGzf_cost(select.getString("gzf_cost"));
					positionBean.setLive_cost(select.getString("live_cost"));
					positionBean.setLive_day(select.getString("live_day"));
					positionBean.setState(select.getString("state"));
					positionBean.setNowprice(select.getString("now_price"));
					positionBean.setName(select.getString("name"));
					positionBean.setFloat_vk(select.getString("yk"));
					positionBean.setStock_name(select.getString("stock_name"));
					positionBean.setFujia_cost(select.getString("fujia_cost"));

					listBean.getList().add(positionBean);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listBean;

	}

}