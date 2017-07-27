package com.kfd.bean;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GetMoneyRecordeListBean extends Entity {

	private static final long serialVersionUID = 1L;
	/*
	 * public final static int CATALOG_ALL = 2; public final static int
	 * CATALOG_INVALIDATE = 0; public final static int CATALOG_VALIDATE = 1;
	 */

	private String maincatalog;
	private int platecatalog;
	private int pageSize;
	private int pageCount;
	private ArrayList<GetMoneyRecordeBean> list = new ArrayList<GetMoneyRecordeBean>();

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

	public ArrayList<GetMoneyRecordeBean> getList() {
		return list;
	}

	public void setList(ArrayList<GetMoneyRecordeBean> list) {
		this.list = list;
	}

	/**
	 * 数据解析
	 * 
	 * @param string
	 * @return
	 */

	public static GetMoneyRecordeListBean parseData(String string) {
		// LogUtils.log("test", "市场    "+string);
		GetMoneyRecordeListBean listBean = new GetMoneyRecordeListBean();
		try {
			
		if (string != null && string.length() > 0) {
			JSONObject jsonObject = JSONObject.parseObject(string);

			JSONArray array = jsonObject.getJSONArray("data");

			for (int i = 0; i < array.size(); i++) {
				JSONObject jsonObject2 = array.getJSONObject(i);
				GetMoneyRecordeBean bean = new GetMoneyRecordeBean();
				//bean.setDid(jsonObject2.getString("did"));
				//bean.setRemark(jsonObject2.getString("remark"));
				//bean.setOrderid(jsonObject2.getString("orderid"));
				bean.setCreate_time(jsonObject2.getString("apply_time"));
				//bean.setBank_account(jsonObject2.getString("maccount"));
				//bean.setBank_name(jsonObject2.getString("bank_name"));
				//bean.setD_name(jsonObject2.getString("d_name"));
				//bean.setBank_address(jsonObject2.getString("bank_address"));
				bean.setBank_money(jsonObject2.getString("tx_money"));
				bean.setBank_state(jsonObject2.getString("state"));
				//bean.setAnswer_time(jsonObject2.getString("answer_time"));
				//bean.setSend_time(jsonObject2.getString("send_time"));
				bean.setApply(jsonObject2.getString("apply_money"));
				bean.setAnswer(jsonObject2.getString("answer"));
				bean.setTx_cost(jsonObject2.getString("tx_cost"));
				bean.setApply_account(jsonObject2.getString("apply_account"));

				listBean.getList().add(bean);

			}
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return listBean;

	}

}
