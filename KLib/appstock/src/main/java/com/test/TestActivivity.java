package com.test;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONObject;
import com.kfd.activityfour.BaseActivity;
import com.kfd.api.HttpRequest;
import com.kfd.common.StringUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

import android.telephony.TelephonyManager;
import android.util.Log;



public class TestActivivity extends  BaseActivity {
	
	
	private ExecutorService executorService  = Executors.newFixedThreadPool(5);
	private void test(){
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					HashMap<String, String>  hashMap  = new HashMap<String, String>();
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					
					hashMap.put("account","xuwenhu");
					hashMap.put("pawd", "xuwenhu");
					Log.v("test", getDeviceId());
					String string  = HttpRequest.sendHttpClientPost(ConstantInfo.parenturl+"userLogin", hashMap, "UTF-8");
					Log.v("test", "测试返回"+string);
					if (!StringUtils.isEmpty(string)) {
						try {
							
							JSONObject  jsonObject  = JSONObject.parseObject(string);
							String  token =  jsonObject.getString("token");
							SharePersistent.getInstance().savePerference(getApplicationContext(), "token", token);
							test2();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void test2(){
executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					HashMap<String, String>  hashMap  = new HashMap<String, String>();
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					
					hashMap.put("start","0");
					hashMap.put("number", "10");
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
				
					String string  = HttpRequest.sendHttpClientPost(ConstantInfo.parenturl+"stocksCodes", hashMap, "UTF-8");
					Log.v("test", "测试1返回"+string);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String  getDeviceId(){
		  TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);  
		  String   deviceId =tm.getDeviceId();
		  return  deviceId;
	}
}	
