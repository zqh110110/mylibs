package com.kfd.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.kfd.bean.Entity;
import com.kfd.bean.FuturesPositionBean;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;

public class FuturesPositionListBean extends Entity {

	private static final long serialVersionUID = 1L;
	/*
	 * public final static int CATALOG_ALL = 2; public final static int
	 * CATALOG_INVALIDATE = 0; public final static int CATALOG_VALIDATE = 1;
	 */

	private String maincatalog;
	private int platecatalog;
	private int pageSize;
	private int pageCount;
	private ArrayList<FuturesPositionBean> list = new ArrayList<FuturesPositionBean>();
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
	public ArrayList<FuturesPositionBean> getList() {
		return list;
	}
	public void setList(ArrayList<FuturesPositionBean> list) {
		this.list = list;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public static FuturesPositionListBean parseData1(String string){
		FuturesPositionListBean  listBean  = new FuturesPositionListBean();
		try {
			if (string != null && string.length() > 0) {	
				Gson gson  =  new Gson();
				PostData  data  =  gson.fromJson(string, PostData.class);
				HashMap<String, FuturesPositionBean> hashMap2  = data.data;
				for (Entry<String, FuturesPositionBean> entry : hashMap2.entrySet()) {
					FuturesPositionBean  select  =  entry.getValue();
					listBean.getList().add(select);	
				}
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return  listBean;
	}
public class PostData{
	public String status,message;
	public HashMap<String, FuturesPositionBean> data;
	public String all_floatMoney;
}
	
	public static FuturesPositionListBean parseData(String string){
		FuturesPositionListBean  listBean  = new FuturesPositionListBean();
		try {
			if (string != null && string.length() > 0) {
				JSONObject jsonObject = JSONObject.parseObject(string);
				// JSONObject jsonObject1 = jsonObject.getJSONObject("data");
				// String total =jsonObject1.getString("counts");
				//JSONObject jsonObject3 = jsonObject.getJSONObject("data");
				JSONObject jsonObject1 = jsonObject.getJSONObject("data");
				JSONArray array = jsonObject1.getJSONArray("list");

				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					FuturesPositionBean positionBean = new FuturesPositionBean();
					positionBean.setPid(jsonObject2.getString("id"));
					positionBean.setNumber(jsonObject2.getString("number"));
					String createtime = jsonObject2.getString("create_time");
					positionBean.setCreate_time(createtime);

					positionBean.setFuturescode(jsonObject2
							.getString("futures_code"));
					positionBean.setFutures_class(jsonObject2
							.getString("futures_class"));
					positionBean.setCodename(jsonObject2
							.getString("codename"));
					positionBean.setJy_type(jsonObject2.getString("jy_type"));
					positionBean.setType(jsonObject2.getString("type"));
					positionBean.setCreate_price(jsonObject2
							.getString("create_price"));
					positionBean.setLow_price(jsonObject2
							.getString("low_price"));
					positionBean.setHigh_price(jsonObject2
							.getString("high_price"));
					positionBean.setNum(jsonObject2.getString("num"));
					positionBean.setCreate_cost(jsonObject2
							.getString("create_cost"));
					/*positionBean
							.setHost_cost(jsonObject2.getString("hot_cost"));*/
					positionBean.setCloseout_price(jsonObject2
							.getString("closeout_price"));
					/*positionBean.setCloseout_cost(jsonObject2
							.getString("closeout_cost"));*/
					/*positionBean.setQzpc_cost(jsonObject2
							.getString("qzpc_cost"));*/
					positionBean.setGzf_cost(jsonObject2.getString("gzf_cost"));
					positionBean.setLive_cost(jsonObject2
							.getString("live_cost"));
					positionBean.setLive_day(jsonObject2.getString("live_day"));
					positionBean.setState(jsonObject2.getString("state"));
					//positionBean.setNowprice(jsonObject2.getString("nowprice"));
					//positionBean.setName(jsonObject2.getString("name"));
					positionBean.setYk(jsonObject2.getString("yk"));
					positionBean.setNow_price(jsonObject2.getString("now_price"));
					positionBean.setFloatMoney(jsonObject2.getString("yk"));
					positionBean.setWt_close_status(jsonObject2.getString("wt_close_status"));
					/*positionBean.setStock_name(jsonObject2
							.getString("stock_name"));*/

					listBean.getList().add(positionBean);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return  listBean;
	}



}
