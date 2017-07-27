package com.kfd.bean;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;

public class FuturesEntrustDetailListBean extends Entity {

	private static final long serialVersionUID = 1L;

	private String maincatalog;
	private int platecatalog;
	private int pageSize;
	private int pageCount;
	private ArrayList<FuturesEntrustDetailBean> list = new ArrayList<FuturesEntrustDetailBean>();


	public ArrayList<FuturesEntrustDetailBean> getList() {
		return list;
	}

	public void setList(ArrayList<FuturesEntrustDetailBean> list) {
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

	public static FuturesEntrustDetailListBean parseData(String string) {
		LogUtils.log("test", "平仓明细   " + string);

		FuturesEntrustDetailListBean listBean = new FuturesEntrustDetailListBean();
		try {
			if (string != null && string.length() > 0) {
				JSONObject jsonObject = JSONObject.parseObject(string);

				JSONArray array = jsonObject.getJSONArray("data");

				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					FuturesEntrustDetailBean bean = new FuturesEntrustDetailBean();
					bean.setPid(jsonObject2.getString("id"));
					bean.setNumber(jsonObject2.getString("number"));
					String createtime = jsonObject2.getString("create_time");

					bean.setCreate_time(createtime);

					bean.setFuturescode(jsonObject2.getString("futures_code"));
					bean.setFutures_class(jsonObject2.getString("futures_class"));

					bean.setNowprice(jsonObject2.getString("nowprice"));
					bean.setType(jsonObject2.getString("type"));
					bean.setCreate_price(jsonObject2.getString("create_price"));
					bean.setLow_price(jsonObject2.getString("low_price"));
					bean.setHigh_price(jsonObject2.getString("high_price"));
					bean.setNum(jsonObject2.getString("num"));
					bean.setCreate_cost(jsonObject2.getString("create_cost"));
				//	bean.setHot_cost(jsonObject2.getString("hot_cost"));
					bean.setWt_closeprice(jsonObject2
							.getString("wt_closeprice"));
					String closeout_time = jsonObject2
								.getString("wt_close_time");
					bean.setCloseout_time(closeout_time);
					
					bean.setCloseout_cost(jsonObject2
							.getString("closeout_cost"));
					bean.setWt_create_status(jsonObject2
							.getString("wt_create_status"));
					
				//	bean.setQzpc_cost(jsonObject2.getString("qzpc_cost"));
					bean.setGzf_cost(jsonObject2.getString("gzf_cost"));
					bean.setLive_cost(jsonObject2.getString("live_cost"));
					bean.setLive_day(jsonObject2.getString("live_day"));
					bean.setState(jsonObject2.getString("state"));
					bean.setYk(jsonObject2.getString("yk"));
					bean.setNowprice(jsonObject2.getString("nowprice"));
					bean.setFloat_yk(jsonObject2.getString("float_yk"));
					//bean.setStock_name(jsonObject2.getString("stock_name"));
					listBean.getList().add(bean);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listBean;

	}
}


