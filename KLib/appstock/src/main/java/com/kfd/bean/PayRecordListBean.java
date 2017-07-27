package com.kfd.bean;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kfd.common.LogUtils;

public class PayRecordListBean extends Entity {

	private static final long serialVersionUID = 1L;

	private String maincatalog;
	private int platecatalog;
	private int pageSize;
	private int pageCount;
	private ArrayList<PayRecordBean> list = new ArrayList<PayRecordBean>();

	public ArrayList<PayRecordBean> getList() {
		return list;
	}

	public void setList(ArrayList<PayRecordBean> list) {
		this.list = list;
	}

	public String getMaincatalog() {
		return maincatalog;
	}

	public void setMaincatalog(String maincatalog) {
		this.maincatalog = maincatalog;
	}

	public int getPlatecatalog() {
		return platecatalog;
	}

	public void setPlatecatalog(int platecatalog) {
		this.platecatalog = platecatalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 数据解析
	 * 
	 * @param string
	 * @return
	 */

	public static PayRecordListBean parseData(String string) {
		LogUtils.log("test", "pay    " + string);

		PayRecordListBean listBean = new PayRecordListBean();
		try {
			if (string != null && string.length() > 0) {
				JSONObject jsonObject = JSONObject.parseObject(string);
				JSONArray array = jsonObject.getJSONArray("data");

				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					PayRecordBean bean = new PayRecordBean();
					bean.setPay_time(jsonObject2.getString("create_time"));
					bean.setCz_account(jsonObject2.getString("cz_account"));
					bean.setCz_money(jsonObject2.getString("cz_money"));
					bean.setState(jsonObject2.getString("cz_status"));
					bean.setRemark(jsonObject2.getString("remark"));
					bean.setOrderid(jsonObject2.getString("orderid"));
					bean.setCz_type(jsonObject2.getString("cz_type"));
					listBean.getList().add(bean);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listBean;

	}

}