package com.kfd.bean;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;

public class EntrustDetailListBean extends Entity {

	private static final long serialVersionUID = 1L;

	private String maincatalog;
	private int platecatalog;
	private int pageSize;
	private int pageCount;
	private ArrayList<EntrustDetailBean> list = new ArrayList<EntrustDetailBean>();

	public ArrayList<EntrustDetailBean> getList() {
		return list;
	}

	public void setList(ArrayList<EntrustDetailBean> list) {
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

	public static EntrustDetailListBean parseData(String string) {
		LogUtils.log("test", "平仓明细   " + string);

		EntrustDetailListBean listBean = new EntrustDetailListBean();
		try {
			if (string != null && string.length() > 0) {
				JSONObject jsonObject = JSONObject.parseObject(string);

				JSONObject jsonObject3 = jsonObject.getJSONObject("data");

				JSONArray array = jsonObject3.getJSONArray("lists");

				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					EntrustDetailBean bean = new EntrustDetailBean();
					bean.setPid(jsonObject2.getString("pid"));
					bean.setNumber(jsonObject2.getString("number"));
					String createtime = jsonObject2.getString("create_time");
					try {
						String dateformat = StringUtils
								.phpdateformat(createtime);
						LogUtils.log("test", "date" + dateformat);
						bean.setCreate_time(dateformat);
					} catch (Exception e) {
						e.printStackTrace();
					}

					bean.setStockcode(jsonObject2.getString("stockcode"));
					bean.setStock_class(jsonObject2.getString("stock_class"));
					if (jsonObject2.getString("type").equals("0")) {
						bean.setType("买涨");
					} else if (jsonObject2.getString("type").equals("1")) {
						bean.setType("买跌");
					}

					bean.setNowprice(jsonObject2.getString("nowprice"));
					// bean.setType(jsonObject2.getString("type"));
					bean.setCreate_price(jsonObject2.getString("create_price"));
					bean.setLow_price(jsonObject2.getString("low_price"));
					bean.setHigh_price(jsonObject2.getString("high_price"));
					bean.setNum(jsonObject2.getString("num"));
					bean.setCreate_cost(jsonObject2.getString("create_cost"));
					bean.setHot_cost(jsonObject2.getString("hot_cost"));
					bean.setCloseout_price(jsonObject2
							.getString("closeout_price"));

					try {
						String closeout_time = jsonObject2
								.getString("closeout_time");
						// LogUtils.log("test", closeout_time);
						String dateformat1 = StringUtils
								.phpdateformat(closeout_time);

						bean.setCloseout_time(dateformat1);
					} catch (Exception e) {
						e.printStackTrace();
					}
					bean.setCloseout_cost(jsonObject2
							.getString("closeout_cost"));
					bean.setQzpc_cost(jsonObject2.getString("qzpc_cost"));
					bean.setGzf_cost(jsonObject2.getString("gzf_cost"));
					bean.setLive_cost(jsonObject2.getString("live_cost"));
					bean.setLive_day(jsonObject2.getString("live_day"));
					bean.setState(jsonObject2.getString("state"));
					bean.setYk(jsonObject2.getString("yk"));
					bean.setStock_name(jsonObject2.getString("stock_name"));
					listBean.getList().add(bean);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listBean;

	}

}