/*
 * Copyright (c) 2015 The MaMaHelp_small_withReceiver_6.8 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */

package com.kfd.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kfd.common.StringUtils;


/**
 * @Function: 换肤bean
 * @author zhu
 * @version
 * @Date: 2015-4-2 下午2:58:22
 */
public class DressUpBean   implements Serializable{
/**
 * •id:  "1",
•type:  "1",
•extid:  "333666",
•title:  "哗哈哈哈哈",
•desc:  "嘿哈哈哈哈哈哈",
•ios:  " http://ios.rar ",
•android:  " http://android.rar ",
•iosfilesize:  "12345678",
•androidfilesize:  "23456789",
•stime:  "1420041600",
•etime:  "2147443200",
•deltime:  "0",
•lstime:  "1427773971",
•dateline:  "1427687738",
•pic: [
•" http://pic30.nipic.com/20130701/12072732_104006034314_2.jpg ",
•" http://pic30.nipic.com/20130701/12072732_104006034314_2.jpg ",
•" http://pic30.nipic.com/20130701/12072732_104006034314_2.jpg ",
•" http://pic30.nipic.com/20130701/12072732_104006034314_2.jpg ",
•" http://pic30.nipic.com/20130701/12072732_104006034314_2.jpg " 
],
•topic:  " http://a4.att.hudong.com/50/35/01300069519924132037356629241.jpg " 
uid
 */
	
	private String id,type,extid,title,desc,ios,android,iosfilesize,
	androidfilesize,stime,etime,deltime,lstime,dateline,topic,uid,is_default;
	
	public String getIs_default() {
	return is_default;
}
public void setIs_default(String is_default) {
	this.is_default = is_default;
}
	private ArrayList<String>  pic ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExtid() {
		return extid;
	}
	public void setExtid(String extid) {
		this.extid = extid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getIos() {
		return ios;
	}
	public void setIos(String ios) {
		this.ios = ios;
	}
	public String getAndroid() {
		return android;
	}
	public void setAndroid(String android) {
		this.android = android;
	}
	public String getIosfilesize() {
		return iosfilesize;
	}
	public void setIosfilesize(String iosfilesize) {
		this.iosfilesize = iosfilesize;
	}
	public String getAndroidfilesize() {
		return androidfilesize;
	}
	public void setAndroidfilesize(String androidfilesize) {
		this.androidfilesize = androidfilesize;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getDeltime() {
		return deltime;
	}
	public void setDeltime(String deltime) {
		this.deltime = deltime;
	}
	public String getLstime() {
		return lstime;
	}
	public void setLstime(String lstime) {
		this.lstime = lstime;
	}
	public String getDateline() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline = dateline;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public ArrayList<String> getPic() {
		return pic;
	}
	public void setPic(ArrayList<String> pic) {
		this.pic = pic;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * down_status
 可下载状态按钮，格式同上
 
change_status
 可换肤状态按钮，格式同上
 
catch_status
 去获取状态按钮，格式同上
 
using_status
 使用中状态按钮，格式同上 

	 */
	
	private String check_status,down_status,change_status,catch_status,using_status;
	
	
	public String getCheck_status() {
		return check_status;
	}
	public void setCheck_status(String check_status) {
		this.check_status = check_status;
	}
	public String getDown_status() {
		return down_status;
	}
	public void setDown_status(String down_status) {
		this.down_status = down_status;
	}
	public String getChange_status() {
		return change_status;
	}
	public void setChange_status(String change_status) {
		this.change_status = change_status;
	}
	public String getCatch_status() {
		return catch_status;
	}
	public void setCatch_status(String catch_status) {
		this.catch_status = catch_status;
	}
	public String getUsing_status() {
		return using_status;
	}
	public void setUsing_status(String using_status) {
		this.using_status = using_status;
	}
	
	public static ArrayList<DressUpBean>   parseData(String string){
		if (StringUtils.isEmpty(string)) {
			return  null;
		}else {
			 ArrayList<DressUpBean>   arrayList  = new ArrayList<DressUpBean>();
			 try {
			  JSONObject jsonObject = new JSONObject(string);
			  String ret  = jsonObject.getString("ret");
			  if (ret.equals("0")) {
				JSONArray dataArray  = jsonObject.getJSONArray("data");
				if (dataArray.length()>0) {
					for (int i = 0; i < dataArray.length(); i++) {
						JSONObject jsonObject2  = dataArray.getJSONObject(i);
						DressUpBean dressUpBean = new DressUpBean();
						dressUpBean.setId(jsonObject2.optString("id"));
						dressUpBean.setType(jsonObject2.optString("type"));
						dressUpBean.setExtid(jsonObject2.optString("extid"));
						dressUpBean.setTitle(jsonObject2.optString("title"));
						dressUpBean.setDesc(jsonObject2.optString("desc"));
						dressUpBean.setAndroid(jsonObject2.optString("android"));
						dressUpBean.setIos(jsonObject2.optString("ios"));
						dressUpBean.setIosfilesize(jsonObject2.optString("iosfilesize"));
						dressUpBean.setAndroidfilesize(jsonObject2.optString("androidfilesize"));
						dressUpBean.setStime(jsonObject2.optString("stime"));
						dressUpBean.setEtime(jsonObject2.optString("etime"));
						dressUpBean.setDeltime(jsonObject2.optString("deltime"));
						dressUpBean.setLstime(jsonObject2.optString("lstime"));
						dressUpBean.setDateline(jsonObject2.optString("dateline"));
						JSONArray  picArray  = jsonObject2.getJSONArray("pic");
						ArrayList<String>   picList =  new ArrayList<String>();
						for (int j = 0; j < picArray.length(); j++) {
							picList.add(picArray.getString(j));
						}
						dressUpBean.setPic(picList);
						dressUpBean.setTopic(jsonObject2.optString("topic"));
						dressUpBean.setUid(jsonObject2.optString("uid"));
						dressUpBean.setIs_default(jsonObject2.getString("is_default"));
						//down_status,change_status,catch_status,using_status
						dressUpBean.setCheck_status(jsonObject2.optString("check_status"));
						dressUpBean.setDown_status(jsonObject2.optString("down_status"));
						dressUpBean.setChange_status(jsonObject2.optString("change_status"));
						dressUpBean.setCatch_status(jsonObject2.optString("catch_status"));
						dressUpBean.setUsing_status(jsonObject2.optString("using_status"));
						arrayList.add(dressUpBean);
					}
				}else {
					return null;
				}
				
			}else {
				return null;
			}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return  arrayList;
		}
	}
}

	