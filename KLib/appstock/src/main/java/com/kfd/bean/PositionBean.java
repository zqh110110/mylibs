package com.kfd.bean;

import java.io.Serializable;

/**
 * 持仓明细bean
 * 
 * @author 朱继洋 QQ7617812 2013-5-28
 */
public class PositionBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String id, number, create_time, stock_code, stock_class, type,
			create_price, low_price, high_price, num, create_cost, hot_cost,
			closeout_price, closeout_cost, qzpc_cost, gzf_cost, live_cost,
			state, user_id, closeout_time, yk, stock_name, live_day, live_time,
			live_type, delist_time, delist_type, delist_recover_time,
			closeout_type, name, now_price, float_vk,fujia_cost;

	public String getFujia_cost() {
		return fujia_cost;
	}

	public void setFujia_cost(String fujia_cost) {
		this.fujia_cost = fujia_cost;
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

	public String getStock_class() {
		return stock_class;
	}

	public void setStock_class(String stock_class) {
		this.stock_class = stock_class;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreate_price() {
		return create_price;
	}

	public void setCreate_price(String create_price) {
		this.create_price = create_price;
	}

	public String getLow_price() {
		return low_price;
	}

	public void setLow_price(String low_price) {
		this.low_price = low_price;
	}

	public String getHigh_price() {
		return high_price;
	}

	public void setHigh_price(String high_price) {
		this.high_price = high_price;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCreate_cost() {
		return create_cost;
	}

	public void setCreate_cost(String create_cost) {
		this.create_cost = create_cost;
	}

	public String getHot_cost() {
		return hot_cost;
	}

	public void setHot_cost(String host_cost) {
		this.hot_cost = host_cost;
	}

	public String getCloseout_price() {
		return closeout_price;
	}

	public void setCloseout_price(String closeout_price) {
		this.closeout_price = closeout_price;
	}

	public String getCloseout_cost() {
		return closeout_cost;
	}

	public void setCloseout_cost(String closeout_cost) {
		this.closeout_cost = closeout_cost;
	}

	public String getQzpc_cost() {
		return qzpc_cost;
	}

	public void setQzpc_cost(String qzpc_cost) {
		this.qzpc_cost = qzpc_cost;
	}

	public String getGzf_cost() {
		return gzf_cost;
	}

	public void setGzf_cost(String gzf_cost) {
		this.gzf_cost = gzf_cost;
	}

	public String getLive_cost() {
		return live_cost;
	}

	public void setLive_cost(String live_cost) {
		this.live_cost = live_cost;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCloseout_time() {
		return closeout_time;
	}

	public void setCloseout_time(String closeout_time) {
		this.closeout_time = closeout_time;
	}

	public String getYk() {
		return yk;
	}

	public void setYk(String yk) {
		this.yk = yk;
	}

	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public String getLive_day() {
		return live_day;
	}

	public void setLive_day(String live_day) {
		this.live_day = live_day;
	}

	public String getLive_time() {
		return live_time;
	}

	public void setLive_time(String live_time) {
		this.live_time = live_time;
	}

	public String getLive_type() {
		return live_type;
	}

	public void setLive_type(String live_type) {
		this.live_type = live_type;
	}

	public String getDelist_time() {
		return delist_time;
	}

	public void setDelist_time(String delist_time) {
		this.delist_time = delist_time;
	}

	public String getDelist_type() {
		return delist_type;
	}

	public void setDelist_type(String delist_type) {
		this.delist_type = delist_type;
	}

	public String getDelist_recover_time() {
		return delist_recover_time;
	}

	public void setDelist_recover_time(String delist_recover_time) {
		this.delist_recover_time = delist_recover_time;
	}

	public String getCloseout_type() {
		return closeout_type;
	}

	public void setCloseout_type(String closeout_type) {
		this.closeout_type = closeout_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNowprice() {
		return now_price;
	}

	public void setNowprice(String nowprice) {
		this.now_price = nowprice;
	}

	public String getFloat_vk() {
		return float_vk;
	}

	public void setFloat_vk(String float_vk) {
		this.float_vk = float_vk;
	}

}
