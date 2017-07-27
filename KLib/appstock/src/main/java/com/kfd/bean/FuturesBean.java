package com.kfd.bean;

import com.alibaba.fastjson.JSONObject;

import android.R.fraction;
import android.util.Log;

public class FuturesBean extends Entity {
	private String bearamount,businessa,businessb,code,codename,downlimitp,futuclosep,futuhighp,
	futulastp,futulowp,futuopenp,preclosep,presquarep,squarep,uplimitp,useMoney;

	public String getBearamount() {
		return bearamount;
	}

	public void setBearamount(String bearamount) {
		this.bearamount = bearamount;
	}

	public String getBusinessa() {
		return businessa;
	}

	

	public void setBusinessa(String businessa) {
		this.businessa = businessa;
	}

	public String getBusinessb() {
		return businessb;
	}

	public void setBusinessb(String businessb) {
		this.businessb = businessb;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public String getDownlimitp() {
		return downlimitp;
	}

	public void setDownlimitp(String downlimitp) {
		this.downlimitp = downlimitp;
	}

	public String getFutuclosep() {
		return futuclosep;
	}

	public void setFutuclosep(String futuclosep) {
		this.futuclosep = futuclosep;
	}

	public String getFutuhighp() {
		return futuhighp;
	}

	public void setFutuhighp(String futuhighp) {
		this.futuhighp = futuhighp;
	}

	public String getFutulastp() {
		return futulastp;
	}

	public void setFutulastp(String futulastp) {
		this.futulastp = futulastp;
	}

	public String getFutulowp() {
		return futulowp;
	}

	public void setFutulowp(String futulowp) {
		this.futulowp = futulowp;
	}

	public String getFutuopenp() {
		return futuopenp;
	}

	public void setFutuopenp(String futuopenp) {
		this.futuopenp = futuopenp;
	}

	public String getPreclosep() {
		return preclosep;
	}

	public void setPreclosep(String preclosep) {
		this.preclosep = preclosep;
	}

	public String getPresquarep() {
		return presquarep;
	}

	public void setPresquarep(String presquarep) {
		this.presquarep = presquarep;
	}

	public String getSquarep() {
		return squarep;
	}

	public void setSquarep(String squarep) {
		this.squarep = squarep;
	}

	public String getUplimitp() {
		return uplimitp;
	}

	public void setUplimitp(String uplimitp) {
		this.uplimitp = uplimitp;
	}
	public String getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(String useMoney) {
		this.useMoney = useMoney;
	}
	private String maxhand;
	
	public String getMaxhand() {
		return maxhand;
	}

	public void setMaxhand(String maxhand) {
		this.maxhand = maxhand;
	}

	public static  FuturesBean  parseData(String string){
		FuturesBean  futuresBean  = new FuturesBean();
		try {
			JSONObject jsonObject = JSONObject.parseObject(string);
			futuresBean.code = jsonObject.getString("code");
			futuresBean.codename = jsonObject.getString("codename");
			futuresBean.setBearamount(jsonObject.getString("bearamount"));
			futuresBean.setBusinessa(jsonObject.getString("businessa"));
			futuresBean.setBusinessb(jsonObject
					.getString("businessb"));
			futuresBean.setDownlimitp(jsonObject.getString("downlimitp"));
			futuresBean.setFutuclosep(jsonObject.getString("futuclosep"));
			futuresBean.setFutuhighp(jsonObject.getString("futuhighp"));
			futuresBean.setFutulastp(jsonObject.getString("futulastp"));
			futuresBean.setFutulowp(jsonObject.getString("futulowp"));
			futuresBean.setFutuopenp(jsonObject.getString("futuopenp"));
			futuresBean.setPreclosep(jsonObject.getString("preclosep"));
			futuresBean.setPresquarep(jsonObject.getString("presquarep"));
			futuresBean.setSquarep(jsonObject.getString("squarep"));
			futuresBean.setUplimitp(jsonObject.getString("uplimitp"));
			futuresBean.setMaxhand(jsonObject.getString("maxhand"));
			futuresBean.setUseMoney(jsonObject.getString("useMoney"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return futuresBean;
	}
}
