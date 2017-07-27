package com.kfd.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kfd.activityfour.XinwenDetailActivity;
import com.kfd.common.LogUtils;

public class FuturesCloseOutListBean implements  Serializable{
		private ArrayList<FuturesCloseOutBean> arrayList  = new  ArrayList<FuturesCloseOutBean>();

		public ArrayList<FuturesCloseOutBean> getArrayList() {
			return arrayList;
		}

		public void setArrayList(ArrayList<FuturesCloseOutBean> arrayList) {
			this.arrayList = arrayList;
		}
		public static   FuturesCloseOutListBean parseData(String string){
			FuturesCloseOutListBean  listBean  =  new FuturesCloseOutListBean();
			
			try {
				if (string.length() > 0) {
					JSONObject jsonObject = JSONObject.parseObject(string);
					JSONObject jsonArray = jsonObject.getJSONObject("data");
					FuturesCloseOutBean  outBean  = new FuturesCloseOutBean();
					
					outBean.setPid(jsonArray.getString("id"));
					outBean.setNumber(jsonArray.getString("number"));
					outBean.setFuturescode(jsonArray.getString("futures_code"));
					//outBean.setType(jsonObject1.getString("type"));
					outBean.setNum(jsonArray.getString("num"));
					outBean.setNow_price(jsonArray.getString("nowprice"));
					outBean.setCloseout_type(jsonArray.getString("close_type"));
					outBean.setIsqzpc(jsonArray.getString("isqzpc"));
					outBean.setQzpcmsg(jsonArray.getString("qzpcmsg"));
					
					listBean.getArrayList().add(outBean);
					
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return  listBean;
			
		}
}
