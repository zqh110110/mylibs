package com.kfd.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kfd.common.LogUtils;

/**
 * 总账户资金
 * 
 * @author 邓贞贵  QQ378933409 2014-4-3
 */
public class TotalfundsBean {
	private String zaccount_money, saccount_money, saccount_cash, saccount_reflect,
	faccount_money, faccount_cash, faccount_reflect,saccount_take,faccount_take;


	public String getSaccount_take() {
		return saccount_take;
	}


	public void setSaccount_take(String saccount_take) {
		this.saccount_take = saccount_take;
	}


	public String getFaccount_take() {
		return faccount_take;
	}


	public void setFaccount_take(String faccount_take) {
		this.faccount_take = faccount_take;
	}


	public String getZaccount_money() {
		return zaccount_money;
	}


	public void setZaccount_money(String zaccount_money) {
		this.zaccount_money = zaccount_money;
	}


	public String getSaccount_money() {
		return saccount_money;
	}


	public void setSaccount_money(String saccount_money) {
		this.saccount_money = saccount_money;
	}


	public String getSaccount_cash() {
		return saccount_cash;
	}


	public void setSaccount_cash(String saccount_cash) {
		this.saccount_cash = saccount_cash;
	}


	public String getSaccount_reflect() {
		return saccount_reflect;
	}


	public void setSaccount_reflect(String saccount_reflect) {
		this.saccount_reflect = saccount_reflect;
	}


	public String getFaccount_money() {
		return faccount_money;
	}


	public void setFaccount_money(String faccount_money) {
		this.faccount_money = faccount_money;
	}


	public String getFaccount_cash() {
		return faccount_cash;
	}


	public void setFaccount_cash(String faccount_cash) {
		this.faccount_cash = faccount_cash;
	}


	public String getFaccount_reflect() {
		return faccount_reflect;
	}


	public void setFaccount_reflect(String faccount_reflect) {
		this.faccount_reflect = faccount_reflect;
	}


	/**
	 * 数据解析
	 * 
	 * @param inputStream
	 *            从服务器获取的数据流
	 * @return
	 */

	public static TotalfundsBean parseData(String string) {
		TotalfundsBean totalfundsBean = new TotalfundsBean();
		LogUtils.log("test", "数据   " + string);
		
		try {
			JSONObject jsonObj1 = JSON.parseObject(string);
			JSONObject jsonObj = jsonObj1.getJSONObject("data");
			JSONObject jsonObjS = jsonObj.getJSONObject("stocks");
			JSONObject jsonObJF = jsonObj.getJSONObject("futures");
			
			totalfundsBean.setSaccount_reflect(jsonObjS.getString("saccount_reflect"));// 股票账户可提现资金
			totalfundsBean.setSaccount_money(jsonObjS.getString("saccount_money"));// 股票账户资金
			totalfundsBean.setSaccount_cash(jsonObjS.getString("saccount_cash"));// 股票账户可可转账资金
			totalfundsBean.setSaccount_take(jsonObjS.getString("saccount_take"));
			
			totalfundsBean.setZaccount_money(jsonObj.getString("zaccount_money"));// 总账户资金
			totalfundsBean.setFaccount_cash(jsonObJF.getString("faccount_cash"));// 期货账户可转账资金
			totalfundsBean.setFaccount_reflect(jsonObJF.getString("faccount_reflect"));//期货账户可提现资金
			totalfundsBean.setFaccount_money(jsonObJF.getString("faccount_money"));// 期货股票资金	
			totalfundsBean.setFaccount_take(jsonObJF.getString("faccount_take"));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		
		return totalfundsBean;

	}
}
