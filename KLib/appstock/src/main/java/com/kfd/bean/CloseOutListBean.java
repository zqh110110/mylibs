package com.kfd.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.kfd.bean.CustomInfoBean.CustomInfoSelect;

public class CloseOutListBean {
	private String nowprice, time, id, number, create_time, stock_code,
			create_price, create_cost, stock_name, delist_recover_time,
			close_type,close_style,isqzpc,qzpcmsg;

	public String getQzpcmsg() {
		return qzpcmsg;
	}

	public void setQzpcmsg(String qzpcmsg) {
		this.qzpcmsg = qzpcmsg;
	}

	public String getIsqzpc() {
		return isqzpc;
	}

	public void setIsqzpc(String isqzpc) {
		this.isqzpc = isqzpc;
	}

	public String getClose_style() {
		return close_style;
	}

	public void setClose_style(String close_style) {
		this.close_style = close_style;
	}

	public String getNowprice() {
		return nowprice;
	}

	public void setNowprice(String nowprice) {
		this.nowprice = nowprice;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPid() {
		return id;
	}

	public void setPid(String pid) {
		this.id = pid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getStockcode() {
		return stock_code;
	}

	public void setStockcode(String stockcode) {
		this.stock_code = stockcode;
	}

	public String getCreate_price() {
		return create_price;
	}

	public void setCreate_price(String create_price) {
		this.create_price = create_price;
	}

	public String getCreate_cost() {
		return create_cost;
	}

	public void setCreate_cost(String create_cost) {
		this.create_cost = create_cost;
	}

	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public String getDelist_recover_time() {
		return delist_recover_time;
	}

	public void setDelist_recover_time(String delist_recover_time) {
		this.delist_recover_time = delist_recover_time;
	}

	public String getCloseout_type() {
		return close_type;
	}

	public void setCloseout_type(String closeout_type) {
		this.close_type = closeout_type;
	}

	private String type, num;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public static CloseOutListBean parseData(String string) {
		CloseOutListBean listBean = new CloseOutListBean();
		if (string.length() > 0) {
			
			Gson gson  = new Gson();
			CloseOutSelect closeoutSelect =  gson.fromJson(string, CloseOutSelect.class);
			
			CloseOutListBean select = closeoutSelect.data;
			
				listBean.setNowprice(select.nowprice);
				listBean.setTime(select.time);
				listBean.setPid(select.id);
				listBean.setNumber(select.number);
				listBean.setStock_name(select.stock_name);
				listBean.setCreate_time(select.create_time);
				listBean.setStockcode(select.stock_code);
				listBean.setCreate_price(select.create_price);
				listBean.setIsqzpc(select.isqzpc);
				listBean.setQzpcmsg(select.qzpcmsg);
				listBean.setCloseout_type(select.close_type);
				listBean.setClose_style(select.close_style);

				listBean.setNum(select.num);

		}

		return listBean;
	}
	
	
	class CloseOutSelect{
		public boolean  result;
		public int status;
		public CloseOutListBean data;
		public String message;
	}
	
	
	public static CloseOutListBean parseData1(String string) {
		CloseOutListBean listBean = new CloseOutListBean();
		try {
			if (string.length() > 0) {
				
				JSONObject jsonObject = JSONObject.parseObject(string);
				JSONObject jsonObject3 = jsonObject.getJSONObject("data");
				
				listBean.setNowprice(jsonObject3.getString("nowprice"));
				listBean.setTime(jsonObject3.getString("time"));
				listBean.setPid(jsonObject3.getString("id"));
				listBean.setNumber(jsonObject3.getString("number"));
				listBean.setStock_name(jsonObject3.getString("stock_name"));
				listBean.setCreate_time(jsonObject3.getString("create_time"));
				listBean.setStockcode(jsonObject3.getString("stock_code"));
				listBean.setCreate_price(jsonObject3.getString("create_price"));
				listBean.setIsqzpc(jsonObject3.getString("isqzpc"));
				listBean.setQzpcmsg(jsonObject3.getString("qzpcmsg"));
				listBean.setCloseout_type(jsonObject3.getString("close_type"));
				listBean.setClose_style(jsonObject3.getString("close_style"));
				
				listBean.setNum(jsonObject3.getString("num"));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return listBean;
	}

}
