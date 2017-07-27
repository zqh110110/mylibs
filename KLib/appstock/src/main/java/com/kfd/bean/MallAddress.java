/*
 * Copyright (c) 2014 The MaMaHelp_small_withReceiver_6.0.0 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */

package com.kfd.bean;

import java.io.Serializable;

import org.json.JSONObject;

import com.kfd.common.StringUtils;




/**
 * @Function:收货地址
 * @author liubin
 * @version
 * @Date: 2014年8月4日 下午8:03:21
 */
public class MallAddress implements Serializable {

	private String address_id;//地址id
	private String prov;//省
	private String city;//城市
	private String district;//区
	private String details;//详细地址
	private String phone;//手机号
	private String name;//用户名
	private String default_setting;//是否默认
	private String user_id; //用户id
	private String id_card;
	private String id_name;
	private String id_photo;
	private String id_photo_contrary;
	private String is_idcard;
	private String address_name;
	private String email;
	private String country;
	private String zipcode;
	private String tel;
	private String is_default;
	private String create_time;
	
	public String getAddress_name() {
		return address_name;
	}

	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefault_setting() {
		return default_setting;
	}

	public void setDefault_setting(String default_setting) {
		this.default_setting = default_setting;
	}


	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getId_name() {
		return id_name;
	}

	public void setId_name(String id_name) {
		this.id_name = id_name;
	}

	public String getId_photo() {
		return id_photo;
	}

	public void setId_photo(String id_photo) {
		this.id_photo = id_photo;
	}

	public String getId_photo_contrary() {
		return id_photo_contrary;
	}

	public void setId_photo_contrary(String id_photo_contrary) {
		this.id_photo_contrary = id_photo_contrary;
	}

	public String getIs_idcard() {
		return is_idcard;
	}

	public void setIs_idcard(String is_idcard) {
		this.is_idcard = is_idcard;
	}


	public MallAddress(String address_id, String prov, String city,
			String district, String details, String phone, String name,
			String default_setting, String id_card, String id_name,
			String id_photo, String id_photo_contrary, String is_idcard) {
		super();
		this.address_id = address_id;
		this.prov = prov;
		this.city = city;
		this.district = district;
		this.details = details;
		this.phone = phone;
		this.name = name;
		this.default_setting = default_setting;
		this.id_card = id_card;
		this.id_name = id_name;
		this.id_photo = id_photo;
		this.id_photo_contrary = id_photo_contrary;
		this.is_idcard = is_idcard;
	}

	public MallAddress() {
		// TODO Auto-generated constructor stub
	}

	public String getString() {
		return address_id + prov + city + district + details + phone + name
				+ default_setting;
	}

	public static  MallAddress  parseinfo (String  string){
		
		MallAddress mallAddress  = new MallAddress();
		if (!StringUtils.isEmpty(string)) {
			try {
				JSONObject jsonObject = new JSONObject(string);		
				mallAddress.setAddress_id(jsonObject.getString("address_id"));
				mallAddress.setAddress_name(jsonObject.getString("address_name"));
				mallAddress.setUser_id(jsonObject.getString("user_id"));
				mallAddress.setName(jsonObject.getString("name"));
				mallAddress.setEmail(jsonObject.getString("email"));
				mallAddress.setCountry(jsonObject.getString("country"));
				mallAddress.setProv(jsonObject.getString("prov"));
				mallAddress.setCity(jsonObject.getString("city"));
				mallAddress.setDistrict(jsonObject.getString("district"));
				mallAddress.setDetails(jsonObject.getString("details"));
				mallAddress.setZipcode(jsonObject.getString("zipcode"));
				mallAddress.setTel(jsonObject.getString("tel"));
				mallAddress.setPhone(jsonObject.getString("phone"));
				mallAddress.setIs_default(jsonObject.getString("is_default"));
				mallAddress.setIs_idcard(jsonObject.getString("is_idcard"));
				mallAddress.setCreate_time(jsonObject.getString("create_time"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mallAddress;
	}
}
