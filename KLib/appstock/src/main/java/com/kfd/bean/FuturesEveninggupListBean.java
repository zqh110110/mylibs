package com.kfd.bean;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;

public class FuturesEveninggupListBean extends Entity {

	private static final long serialVersionUID = 1L;

	private String maincatalog;
	private int platecatalog;
	private int pageSize;
	private int pageCount;
	private ArrayList<FuturesEveninggupBean> list = new ArrayList<FuturesEveninggupBean>();
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
	public ArrayList<FuturesEveninggupBean> getList() {
		return list;
	}
	public void setList(ArrayList<FuturesEveninggupBean> list) {
		this.list = list;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * 数据解析
	 * 
	 * @param string
	 * @return
	 */

	public static FuturesEveninggupListBean parseData(String string) {
		LogUtils.log("test", "平仓明细   " + string);

		FuturesEveninggupListBean listBean = new FuturesEveninggupListBean();
		try {
			if (string != null && string.length() > 0) {
				JSONObject jsonObject = JSONObject.parseObject(string);

				JSONObject jsonObject3 = jsonObject.getJSONObject("data");
				JSONArray array = jsonObject3.getJSONArray("list");

				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					FuturesEveninggupBean eveninggupBean = new FuturesEveninggupBean();
					eveninggupBean.setPid(jsonObject2.getString("id"));
					eveninggupBean.setNumber(jsonObject2.getString("number"));
					String createtime = jsonObject2.getString("create_time");
					eveninggupBean.setCreate_time(createtime);

					eveninggupBean.setFutrurescode(jsonObject2
							.getString("futures_code"));
					eveninggupBean.setFutures_class(jsonObject2
							.getString("futures_class"));
					eveninggupBean.setType(jsonObject2.getString("type"));
					eveninggupBean.setCreate_price(jsonObject2
							.getString("create_price"));
					eveninggupBean.setLow_price(jsonObject2
							.getString("low_price"));
					eveninggupBean.setShouxu_cost(jsonObject2
							.getString("shouxu_cost"));
					eveninggupBean.setHigh_price(jsonObject2
							.getString("high_price"));
					eveninggupBean.setNum(jsonObject2.getString("num"));
					eveninggupBean.setCreate_cost(jsonObject2
							.getString("create_cost"));
					/*eveninggupBean.setHot_cost(jsonObject2
							.getString("hot_cost"));*/
					
					eveninggupBean.setWt_closeprice(jsonObject2
							.getString("wt_closeprice"));
					eveninggupBean.setCloseout_price(jsonObject2
							.getString("close_price"));
					String closeout_time = jsonObject2
								.getString("pingcang_time");

					eveninggupBean.setCloseout_time(closeout_time);
					eveninggupBean.setCloseout_cost(jsonObject2
							.getString("closeout_cost"));
					eveninggupBean.setQzpc_cost(jsonObject2
							.getString("qzpc_cost"));
					eveninggupBean.setGzf_cost(jsonObject2
							.getString("gzf_cost"));
					eveninggupBean.setLive_cost(jsonObject2
							.getString("live_cost"));
					eveninggupBean.setLive_day(jsonObject2
							.getString("live_day"));
					eveninggupBean.setState(jsonObject2.getString("state"));
					eveninggupBean.setYk(jsonObject2.getString("yk"));
					eveninggupBean.setStock_name(jsonObject2
							.getString("stock_name"));
					listBean.getList().add(eveninggupBean);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listBean;

	}
}
