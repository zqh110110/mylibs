package com.kfd.bean;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

/**
 * 取款bean
 * 
 * @author 朱继洋 QQ7617812 2013-5-30
 */
public class DrawBean {
	private String trueName, uid, username, password, name, sex, country,
			credentials_type, credentials_no, mobile, email, qq, msn, moli,
			realmoney, lastip, logintimes, lever, bank_account, bank_name,
			money_password, bank_address, money, tk_name, reg_time, bcbl, pid,
			groupid, agent_url, disable, last_action_time, total_brokerage,
			last_brokerage, total_transaction, not_brokerage_month, alltrans,
			monthtrans, is_return_brokerage, drawall, payall,
			send_message_time, maxDraw, drawFormalities, moenyRec,message;
	private double percent;

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getCredentials_type() {
		return credentials_type;
	}

	public void setCredentials_type(String credentials_type) {
		this.credentials_type = credentials_type;
	}

	public String getCredentials_no() {
		return credentials_no;
	}

	public void setCredentials_no(String credentials_no) {
		this.credentials_no = credentials_no;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getMoli() {
		return moli;
	}

	public void setMoli(String moli) {
		this.moli = moli;
	}

	public String getRealmoney() {
		return realmoney;
	}

	public void setRealmoney(String realmoney) {
		this.realmoney = realmoney;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public String getLogintimes() {
		return logintimes;
	}

	public void setLogintimes(String logintimes) {
		this.logintimes = logintimes;
	}

	public String getLever() {
		return lever;
	}

	public void setLever(String lever) {
		this.lever = lever;
	}

	public String getBank_account() {
		return bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	public String getMoney_password() {
		return money_password;
	}

	public void setMoney_password(String money_password) {
		this.money_password = money_password;
	}

	public String getBank_address() {
		return bank_address;
	}

	public void setBank_address(String bank_address) {
		this.bank_address = bank_address;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getTk_name() {
		return tk_name;
	}

	public void setTk_name(String tk_name) {
		this.tk_name = tk_name;
	}

	public String getReg_time() {
		return reg_time;
	}

	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}

	public String getBcbl() {
		return bcbl;
	}

	public void setBcbl(String bcbl) {
		this.bcbl = bcbl;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getAgent_url() {
		return agent_url;
	}

	public void setAgent_url(String agent_url) {
		this.agent_url = agent_url;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getLast_action_time() {
		return last_action_time;
	}

	public void setLast_action_time(String last_action_time) {
		this.last_action_time = last_action_time;
	}

	public String getTotal_brokerage() {
		return total_brokerage;
	}

	public void setTotal_brokerage(String total_brokerage) {
		this.total_brokerage = total_brokerage;
	}

	public String getLast_brokerage() {
		return last_brokerage;
	}

	public void setLast_brokerage(String last_brokerage) {
		this.last_brokerage = last_brokerage;
	}

	public String getTotal_transaction() {
		return total_transaction;
	}

	public void setTotal_transaction(String total_transaction) {
		this.total_transaction = total_transaction;
	}

	public String getNot_brokerage_month() {
		return not_brokerage_month;
	}

	public void setNot_brokerage_month(String not_brokerage_month) {
		this.not_brokerage_month = not_brokerage_month;
	}

	public String getAlltrans() {
		return alltrans;
	}

	public void setAlltrans(String alltrans) {
		this.alltrans = alltrans;
	}

	public String getMonthtrans() {
		return monthtrans;
	}

	public void setMonthtrans(String monthtrans) {
		this.monthtrans = monthtrans;
	}

	public String getIs_return_brokerage() {
		return is_return_brokerage;
	}

	public void setIs_return_brokerage(String is_return_brokerage) {
		this.is_return_brokerage = is_return_brokerage;
	}

	public String getDrawall() {
		return drawall;
	}

	public void setDrawall(String drawall) {
		this.drawall = drawall;
	}

	public String getPayall() {
		return payall;
	}

	public void setPayall(String payall) {
		this.payall = payall;
	}

	public String getSend_message_time() {
		return send_message_time;
	}

	public void setSend_message_time(String send_message_time) {
		this.send_message_time = send_message_time;
	}

	public String getMaxDraw() {
		return maxDraw;
	}

	public void setMaxDraw(String maxDraw) {
		this.maxDraw = maxDraw;
	}

	public String getDrawFormalities() {
		return drawFormalities;
	}

	public void setDrawFormalities(String drawFormalities) {
		this.drawFormalities = drawFormalities;
	}


	public String getMoenyRec() {
		return moenyRec;
	}

	public void setMoenyRec(String moenyRec) {
		this.moenyRec = moenyRec;
	}

	/**
	 * 数据解析
	 * 
	 * @param string
	 *            传递进来的字符串
	 * @return
	 */
	public static DrawBean parseData(String string) {
		DrawBean drawBean = new DrawBean();
		
		try {
			
			JSONObject jsonObject = JSONObject.parseObject(string);
			drawBean.setTrueName(jsonObject.getString("trueName"));
			JSONObject jsonObject2 = jsonObject.getJSONObject("userData");
			drawBean.setBank_name(jsonObject2.getString("bank_name"));
			drawBean.setBank_account(jsonObject2.getString("bank_account"));
			drawBean.setBank_address(jsonObject2.getString("bank_address"));
			drawBean.setCredentials_no(jsonObject2.getString("credentials_no"));
			drawBean.setMaxDraw(jsonObject.getString("maxDraw"));
			drawBean.setDrawFormalities(jsonObject.getString("drawFormalities"));
			drawBean.setPercent(jsonObject.getDouble("percent"));
			drawBean.setMessage(jsonObject.getString("message"));
			drawBean.setMoenyRec(jsonObject.getString("moenyRec"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return drawBean;
	}

	@Override
	public String toString() {
		Log.v("test", "DrawBean [trueName=" + trueName + ", uid=" + uid
				+ ", username=" + username + ", password=" + password
				+ ", name=" + name + ", sex=" + sex + ", country=" + country
				+ ", credentials_type=" + credentials_type
				+ ", credentials_no=" + credentials_no + ", mobile=" + mobile
				+ ", email=" + email + ", qq=" + qq + ", msn=" + msn
				+ ", moli=" + moli + ", realmoney=" + realmoney + ", lastip="
				+ lastip + ", logintimes=" + logintimes + ", lever=" + lever
				+ ", bank_account=" + bank_account + ", bank_name=" + bank_name
				+ ", money_password=" + money_password + ", bank_address="
				+ bank_address + ", money=" + money + ", tk_name=" + tk_name
				+ ", reg_time=" + reg_time + ", bcbl=" + bcbl + ", pid=" + pid
				+ ", groupid=" + groupid + ", agent_url=" + agent_url
				+ ", disable=" + disable + ", last_action_time="
				+ last_action_time + ", total_brokerage=" + total_brokerage
				+ ", last_brokerage=" + last_brokerage + ", total_transaction="
				+ total_transaction + ", not_brokerage_month="
				+ not_brokerage_month + ", alltrans=" + alltrans
				+ ", monthtrans=" + monthtrans + ", is_return_brokerage="
				+ is_return_brokerage + ", drawall=" + drawall + ", payall="
				+ payall + ", send_message_time=" + send_message_time
				+ ", maxDraw=" + maxDraw + ", drawFormalities="
				+ drawFormalities + ", percent=" + percent + ", moenyRec="
				+ moenyRec + "]");
		return "DrawBean [trueName=" + trueName + ", uid=" + uid
				+ ", username=" + username + ", password=" + password
				+ ", name=" + name + ", sex=" + sex + ", country=" + country
				+ ", credentials_type=" + credentials_type
				+ ", credentials_no=" + credentials_no + ", mobile=" + mobile
				+ ", email=" + email + ", qq=" + qq + ", msn=" + msn
				+ ", moli=" + moli + ", realmoney=" + realmoney + ", lastip="
				+ lastip + ", logintimes=" + logintimes + ", lever=" + lever
				+ ", bank_account=" + bank_account + ", bank_name=" + bank_name
				+ ", money_password=" + money_password + ", bank_address="
				+ bank_address + ", money=" + money + ", tk_name=" + tk_name
				+ ", reg_time=" + reg_time + ", bcbl=" + bcbl + ", pid=" + pid
				+ ", groupid=" + groupid + ", agent_url=" + agent_url
				+ ", disable=" + disable + ", last_action_time="
				+ last_action_time + ", total_brokerage=" + total_brokerage
				+ ", last_brokerage=" + last_brokerage + ", total_transaction="
				+ total_transaction + ", not_brokerage_month="
				+ not_brokerage_month + ", alltrans=" + alltrans
				+ ", monthtrans=" + monthtrans + ", is_return_brokerage="
				+ is_return_brokerage + ", drawall=" + drawall + ", payall="
				+ payall + ", send_message_time=" + send_message_time
				+ ", maxDraw=" + maxDraw + ", drawFormalities="
				+ drawFormalities + ", percent=" + percent + ", moenyRec="
				+ moenyRec + "]";
	}

}
