package com.kfd.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kfd.common.LogUtils;

/**
 * 资金中心
 * 
 * @author 朱继洋 QQ7617812 2013-5-20
 */
public class FundCenterBean {
	private String accountfund, netfund, occupyFund, availableFund,
			totalBareProfitloss, blasteWarehousePoint, blasteScale;

	public String getAccountfund() {
		return accountfund;
	}

	public void setAccountfund(String accountfund) {
		this.accountfund = accountfund;
	}

	public String getNetfund() {
		return netfund;
	}

	public void setNetfund(String netfund) {
		this.netfund = netfund;
	}

	public String getOccupyFund() {
		return occupyFund;
	}

	public void setOccupyFund(String occupyFund) {
		this.occupyFund = occupyFund;
	}

	public String getAvailableFund() {
		return availableFund;
	}

	public void setAvailableFund(String availableFund) {
		this.availableFund = availableFund;
	}

	public String getTotalBareProfitloss() {
		return totalBareProfitloss;
	}

	public void setTotalBareProfitloss(String totalBareProfitloss) {
		this.totalBareProfitloss = totalBareProfitloss;
	}

	public String getBlasteWarehousePoint() {
		return blasteWarehousePoint;
	}

	public void setBlasteWarehousePoint(String blasteWarehousePoint) {
		this.blasteWarehousePoint = blasteWarehousePoint;
	}

	public String getBlasteScale() {
		return blasteScale;
	}

	public void setBlasteScale(String blasteScale) {
		this.blasteScale = blasteScale;
	}

	/**
	 * 数据解析
	 * 
	 * @param inputStream
	 *            从服务器获取的数据流
	 * @return
	 */

	public static FundCenterBean parseData(String string) {
		FundCenterBean fundCenterBean = new FundCenterBean();
		
		try {
			LogUtils.log("test", "数据   " + string);
			JSONObject jsonObj1 = JSON.parseObject(string);
			JSONObject jsonObj = jsonObj1.getJSONObject("data");
			fundCenterBean.setAccountfund(jsonObj.getString("saccount_money"));// 账户资金
			fundCenterBean.setNetfund(jsonObj.getString("saccount_jz"));// 净值
			fundCenterBean.setOccupyFund(jsonObj.getString("saccount_zy"));// 占用资金
			fundCenterBean.setAvailableFund(jsonObj.getString("saccount_ky"));// 可用资金
			fundCenterBean.setTotalBareProfitloss(jsonObj.getString("saccount_zyk"));// 总盈亏
			fundCenterBean.setBlasteWarehousePoint(jsonObj.getString("baoCangXs"));// 爆仓点
			fundCenterBean.setBlasteScale(jsonObj.getString("baoCangXsp"));// 爆仓比例
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return fundCenterBean;

	}
	
	
	public static FundCenterBean parseData2(String string) {
		FundCenterBean fundCenterBean = new FundCenterBean();
		try {
			LogUtils.log("test", "数据   " + string);
			JSONObject jsonObj1 = JSON.parseObject(string);
			JSONObject jsonObj = jsonObj1.getJSONObject("data");
			fundCenterBean.setAccountfund(jsonObj.getString("faccount_money"));// 账户资金
			fundCenterBean.setNetfund(jsonObj.getString("faccount_jz"));// 净值
			fundCenterBean.setOccupyFund(jsonObj.getString("faccount_zy"));// 占用资金
			fundCenterBean.setAvailableFund(jsonObj.getString("faccount_ky"));// 可用资金
			fundCenterBean.setTotalBareProfitloss(jsonObj.getString("faccount_zyk"));// 总盈亏
			fundCenterBean.setBlasteWarehousePoint(jsonObj.getString("baoCangXs"));// 爆仓点
			fundCenterBean.setBlasteScale(jsonObj.getString("baoCangXsp"));// 爆仓比例
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return fundCenterBean;

	}
}
