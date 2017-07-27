package com.kfd.market;

import com.google.gson.Gson;

public class DrawFuturesBean {
	private String username,idcode,bankname,bankcode,bankaddr,money,rand;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdcode() {
		return idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getBankaddr() {
		return bankaddr;
	}

	public void setBankaddr(String bankaddr) {
		this.bankaddr = bankaddr;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getRand() {
		return rand;
	}

	public void setRand(String rand) {
		this.rand = rand;
	}
	public static  DrawFuturesBean  parseData(String  string){
		DrawFuturesBean bean = null;
		try {
			Gson gson  = new Gson();
			 bean =  gson.fromJson(string, DrawFuturesBean.class);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return  bean;
	}
}
