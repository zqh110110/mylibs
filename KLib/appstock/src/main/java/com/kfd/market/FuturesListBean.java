package com.kfd.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.kfd.bean.Entity;
import com.kfd.bean.FuturesBean;
import com.kfd.bean.FuturesData;
import com.kfd.bean.ListBean;
import com.kfd.bean.StockBean;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.market.ListBean.StockeBeanSelect;
import com.kfd.market.ListBean.StockeSelect;

public class FuturesListBean  extends  Entity {

	private String maincatalog;
	private int platecatalog;
	private int pageSize;
	private int pageCount;
	private ArrayList<FuturesBean> list = new ArrayList<FuturesBean>();
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
	public ArrayList<FuturesBean> getList() {
		return list;
	}
	public void setList(ArrayList<FuturesBean> list) {
		this.list = list;
	}
	
	public static  FuturesListBean  parseData(String string){
		FuturesListBean  futuresListBean  =new FuturesListBean();
		Gson gson  = new Gson();
		FuturesData futuresData =  gson.fromJson(string, FuturesData.class);
		HashMap<String, FuturesBean>  hashMap  = futuresData.data;
		for (Entry<String, FuturesBean> entry : hashMap.entrySet()) {
			FuturesBean  select  =  entry.getValue();	
			futuresListBean.getList().add(select);
		}
		return futuresListBean;
		
	}
	
	/**
	 * 数据解析,列表解析
	 * 
	 * @param string
	 * @return
	 */

	public static FuturesListBean parseListData(String string) {
		// LogUtils.log("test", "市场    "+string);
		FuturesListBean listBean = new FuturesListBean();
		if (string != null && string.length() > 0) {
			Gson gson  = new Gson();
			FuturesSelect futuresSelect =  gson.fromJson(string, FuturesSelect.class);
			ArrayList<FuturesBeanSelect> arrayList = futuresSelect.data;			
			for (int i = 0; i < arrayList.size(); i++) {
				FuturesBeanSelect   select  = arrayList.get(i);
				FuturesBean futuresBean = new FuturesBean();
				if (Cache.getCache("search")!=null) {
					String searchString = Cache.getCache("search").toString();
					if (select.code.toString().equals(searchString)||select.codename.toString().equals(searchString)) {
						//LogUtils.v("test", "searchString--"+searchString);
						futuresBean.setCode(select.code);
						futuresBean.setCodename(select.codename);
						futuresBean.setBearamount(select.bearamount);
						futuresBean.setBusinessa(select.businessa);
						futuresBean.setBusinessb(select.businessb);
						futuresBean.setDownlimitp(select.downlimitp);
						futuresBean.setFutuclosep(select.futuclosep);
						futuresBean.setFutuhighp(select.futuhighp);
						futuresBean.setFutulastp(select.futulastp);
						futuresBean.setFutulowp(select.futulowp);
						futuresBean.setFutuopenp(select.futuopenp);
						futuresBean.setPreclosep(select.preclosep);
						futuresBean.setPresquarep(select.presquarep);
						futuresBean.setSquarep(select.squarep);
						futuresBean.setUplimitp(select.uplimitp);
						futuresBean.setUseMoney(select.useMoney);
						listBean.getList().add(futuresBean);
						Cache.put("search", null);
						searchString="";
						return listBean;
					}
				}else {
					futuresBean.setCode(select.code);
					futuresBean.setCodename(select.codename);
					futuresBean.setBearamount(select.bearamount);
					futuresBean.setBusinessa(select.businessa);
					futuresBean.setBusinessb(select.businessb);
					futuresBean.setDownlimitp(select.downlimitp);
					futuresBean.setFutuclosep(select.futuclosep);
					futuresBean.setFutuhighp(select.futuhighp);
					futuresBean.setFutulastp(select.futulastp);
					futuresBean.setFutulowp(select.futulowp);
					futuresBean.setFutuopenp(select.futuopenp);
					futuresBean.setPreclosep(select.preclosep);
					futuresBean.setPresquarep(select.presquarep);
					futuresBean.setSquarep(select.squarep);
					futuresBean.setUplimitp(select.uplimitp);
					futuresBean.setUseMoney(select.useMoney);
					listBean.getList().add(futuresBean);
				}

			}
		}

		return listBean;

	}
	
	class FuturesSelect{
		public int status;
		public boolean  result;
		public String total;
		public int  num;
		public String message;
		public  ArrayList<FuturesBeanSelect> data;
	}
	/*class StockData{
		
		public String counts;
	}*/
	class FuturesBeanSelect{
		public String bearamount,businessa,businessb,code,codename,downlimitp,futuclosep,futuhighp,
		futulastp,futulowp,futuopenp,preclosep,presquarep,squarep,uplimitp,useMoney;
	}
}
