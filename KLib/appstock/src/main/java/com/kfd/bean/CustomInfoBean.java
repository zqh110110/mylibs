package com.kfd.bean;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.kfd.common.LogUtils;

/**
 * 客户信息自选Bean
 * 
 * @author 2013-05-21
 */

public class CustomInfoBean extends Entity {

	private String userId, account, name, sex, country, phone, email, bank_has,
	msn, qq,mobile,bank_id,maccount,check_phone,card_no,address,bark_name,check_email;

	public String getBank_has() {
		return bank_has;
	}

	public void setBank_has(String bank_has) {
		this.bank_has = bank_has;
	}

	public String getCheck_email() {
		return check_email;
	}

	public void setCheck_email(String check_email) {
		this.check_email = check_email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBark_name() {
		return bark_name;
	}

	public void setBark_name(String bark_name) {
		this.bark_name = bark_name;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCheckphone() {
		return check_phone;
	}

	public void setCheckphone(String checkphone) {
		this.check_phone = checkphone;
	}

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	public String getMaccount() {
		return maccount;
	}

	public void setMaccount(String maccount) {
		this.maccount = maccount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return account;
	}

	public void setUserName(String userName) {
		this.account = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTel() {
		return phone;
	}

	public void setTel(String tel) {
		this.phone = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Override
	public String toString() {
		LogUtils.log("test", "CustomInfoBean [userId=" + userId + ", userName=" + account
				+ ", name=" + name + ", sex=" + sex + ", country=" + country
				+ ", tel=" + phone + ", email=" + email + ", msn=" + msn
				+ ", qq=" + qq + "]");
		return "CustomInfoBean [userId=" + userId + ", userName=" + account
				+ ", name=" + name + ", sex=" + sex + ", country=" + country
				+ ", tel=" + phone + ", email=" + email + ", msn=" + msn
				+ ", qq=" + qq + "]";
	}

	/**
	 * 数据解析
	 * 
	 * @param inputStream
	 *            从服务器获取的数据流
	 * @return
	 */
	public static CustomInfoBean parseData(String string) {
		CustomInfoBean customInfoBean = new CustomInfoBean();
		try {
			Gson gson  = new Gson();
			CustomInfoSelect stockeSelect =  gson.fromJson(string, CustomInfoSelect.class);
			JSONObject jsonObject = JSONObject.parseObject(string);
			JSONObject jsonObject3 = jsonObject.getJSONObject("data");
			CustomInfobankSelect stockebankSelect =  gson.fromJson(jsonObject3.toString(), CustomInfobankSelect.class);
			CustomInfoBean select = stockeSelect.data;
			CustomInfoBean bankselect = stockebankSelect.bank;
			
			// LogUtils.log("test", "数据   " + string);
			//customInfoBean.setUserId(select.userId);// 用户ID
			customInfoBean.setUserName(select.account);// 用户名
			customInfoBean.setName(select.name);// 姓名
			//customInfoBean.setSex(select.sex);// 姓别
			//customInfoBean.setCountry(select.country);// 国家
			customInfoBean.setTel(select.phone); // 手机号码
			String check_phone;
			if (select.check_phone.toString().equals("0")) {
				check_phone = "未验证";
			} else {
				check_phone = "已验证";
			}
			customInfoBean.setCheckphone(check_phone);
			String check_email;
			if (select.check_email.toString().equals("0")) {
				check_email = "未验证";
			} else {
				check_email = "已验证";
			}
			customInfoBean.setCheck_email(check_email);
			customInfoBean.setEmail(select.email);// 邮箱
			//customInfoBean.setMsn(select.msn);// msn
			customInfoBean.setQq(select.qq); // QQ
			customInfoBean.setCard_no(select.card_no); //身份证
			//customInfoBean.setMobile(select.mobile);
			customInfoBean.setBank_id(select.bank_id);

			if (select.bank_id.toString().equals("0")) {
				
			}else {
				customInfoBean.setMaccount(bankselect.maccount);
				customInfoBean.setAddress(bankselect.address);
				customInfoBean.setBark_name(bankselect.bark_name);		
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return customInfoBean;
		
	}
	
	/**
	 * 数据解析
	 * 
	 * @param inputStream
	 *            从服务器获取的数据流
	 * @return
	 */
	public static CustomInfoBean parseData1(String string) {
		CustomInfoBean customInfoBean = new CustomInfoBean();
		try {
			JSONObject jsonObject = JSONObject.parseObject(string);
			JSONObject jsonObject3 = jsonObject.getJSONObject("data");
			JSONObject jsonObject4 = jsonObject3.getJSONObject("bank");
			
			// LogUtils.log("test", "数据   " + string);
			//customInfoBean.setUserId(select.userId);// 用户ID
			customInfoBean.setUserName(jsonObject3.getString("account"));// 用户名
			customInfoBean.setName(jsonObject3.getString("name"));// 姓名
			//customInfoBean.setSex(select.sex);// 姓别
			//customInfoBean.setCountry(select.country);// 国家
			customInfoBean.setTel(jsonObject3.getString("phone")); // 手机号码
			String check_phone;
			if (jsonObject3.getString("check_phone").toString().equals("0")) {
				check_phone = "未验证";
			} else {
				check_phone = "已验证";
			}
			customInfoBean.setCheckphone(check_phone);
			String check_email;
			if (jsonObject3.getString("check_email").toString().equals("0")) {
				check_email = "未验证";
			} else {
				check_email = "已验证";
			}
			customInfoBean.setCheck_email(check_email);
			customInfoBean.setEmail(jsonObject3.getString("email"));// 邮箱
			//customInfoBean.setMsn(select.msn);// msn
			customInfoBean.setQq(jsonObject3.getString("qq")); // QQ
			customInfoBean.setCard_no(jsonObject3.getString("card_no")); //身份证
			//customInfoBean.setMobile(jsonObject3.getString("mobile"));
			customInfoBean.setBank_id(jsonObject3.getString("bank_id"));
			customInfoBean.setBank_has(jsonObject3.getString("bank_has"));

			if (jsonObject3.getString("bank_has").toString().equals("0")) {
				
			}
			else {
				customInfoBean.setMaccount(jsonObject4.getString("maccount"));
				customInfoBean.setAddress(jsonObject4.getString("address"));
				customInfoBean.setBark_name(jsonObject4.getString("bark_name"));		
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return customInfoBean;
		
	}
	
	
	class CustomInfoSelect{
		public boolean  result;
		public int status;
		public CustomInfoBean data;
		public String message;
	}
	
	class CustomInfobankSelect{
		public CustomInfoBean bank;
	}
}
