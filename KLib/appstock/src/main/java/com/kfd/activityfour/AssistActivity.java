package com.kfd.activityfour;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.kfd.activityfour.LoginActivity.CheckVersionAsynTask;
import com.kfd.api.HttpRequest;
import com.kfd.common.Define;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.db.SharePersistent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class AssistActivity   extends  BaseActivity{
	ToggleButton toggleButton,toggleButton2,toggleButton3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assist);
		initTitle("辅助功能");
		toggleButton= (ToggleButton) findViewById(R.id.switch1);
		toggleButton2= (ToggleButton) findViewById(R.id.switch2);
		toggleButton3= (ToggleButton) findViewById(R.id.switch3);
		getset();
toggleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setdata();
			}
		});
toggleButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setdata();
			}
		});
		toggleButton3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setdata();
			}
		});
		findViewById(R.id.clearimagelayout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imageLoader.clearDiscCache();
				imageLoader.clearMemoryCache();
				showToast("清除缓存数据完成");
			}
		});
	findViewById(R.id.clearchatlayout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   clearChat();
			}
		});
		
	}
	
	private void setdata(){
		//：/api-user-main/getconfig
	final	boolean isphoto   = toggleButton.isChecked();
	final	boolean  iswift   = toggleButton2.isChecked();
	final	boolean  deny_vedio  = toggleButton3.isChecked();
	showDialog("请稍候...");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				try {
					if (isphoto) {
						hashMap.put("isphoto", "1");
					}else {
						hashMap.put("isphoto", "0");
					}
					
					if (iswift) {
						hashMap.put("iswift", "1");
					}else {
						hashMap.put("iswift", "0");
					}
					
					if (deny_vedio) {
						hashMap.put("deny_vedio", "1");
					}else {
						hashMap.put("deny_vedio", "0");
					}
					String result  = HttpRequest.sendPostRequestWithMd5(AssistActivity.this,Define.host+"/api-user-main/setconfig", hashMap);
					LogUtils.v("test", "result--- "+result);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						sethandler.sendMessage(message);
					}else {
						Message message = new Message();
						message.what = 0;
						message.obj = "连接超时，请重试";
						sethandler.sendMessage(message);
						//handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		
	}
	
	private Handler  sethandler  =  new  Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			switch (msg.what) {
			case 0:
				//showToast("账号或者密码错误，请重试");
				String message=(String) msg.obj;
				showToast(message);
				break;
			case 1:
				String result= (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret  = jsonObject.getString("ret");
					String msgdata  = jsonObject.getString("msg");
					
					if (ret.equals("0")) {
						showToast("设置成功 ！");
						final	boolean isphoto   = toggleButton.isChecked();
						final	boolean  iswift   = toggleButton2.isChecked();
						final	boolean  deny_vedio  = toggleButton3.isChecked();
						if (isphoto) {
							SharePersistent.getInstance().savePerference(getApplicationContext(), "isphoto", "1");
						}else {
							SharePersistent.getInstance().savePerference(getApplicationContext(), "isphoto", "0");
						}
						
						if (iswift) {
							SharePersistent.getInstance().savePerference(getApplicationContext(), "iswift", "1");
						}else {
							SharePersistent.getInstance().savePerference(getApplicationContext(), "iswift", "0");
						}
						
						if (deny_vedio) {
							SharePersistent.getInstance().savePerference(getApplicationContext(), "deny_vedio", "1");
						}else {
							SharePersistent.getInstance().savePerference(getApplicationContext(), "deny_vedio", "0");
						}
					
					}else {
						showToast(msgdata);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				break;
			
			default:
				break;
			}
		};
	};

	private void getset(){
		//：/api-user-main/getconfig
		showDialog("请稍候...");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				try {
					String result  = HttpRequest.sendGetRequestWithMd5(AssistActivity.this,Define.host+"/api-user-main/getconfig", hashMap);
					LogUtils.v("test", "result--- "+result);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						updateHandler.sendMessage(message);
					}else {
						Message message = new Message();
						message.what = 0;
						message.obj = "连接超时，请重试";
						updateHandler.sendMessage(message);
						//handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		
	}
	private Handler  updateHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			try {
				dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			switch (msg.what) {
			case 0:
				//showToast("账号或者密码错误，请重试");
				String message=(String) msg.obj;
				showToast(message);
				break;
			case 1:
				String result= (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret  = jsonObject.getString("ret");
					String msgdata  = jsonObject.getString("msg");
					
					if (ret.equals("0")) {
						JSONObject   data  = jsonObject.optJSONObject("data");
						JSONObject  config  =  data.optJSONObject("config");
						String  isphoto   = config.getString("isphoto");
						String  iswift   = config.getString("iswift");
						String  deny_vedio   = config.getString("deny_vedio");
						if (!StringUtils.isEmpty(isphoto)&&isphoto.equals("1")) {
							toggleButton.setChecked(true);
							SharePersistent.getInstance().savePerference(getApplicationContext(), "isphoto", "1");
						}else {
							toggleButton.setChecked(false);
							SharePersistent.getInstance().savePerference(getApplicationContext(), "isphoto", "0");
						}
						
						if (!StringUtils.isEmpty(iswift)&&iswift.equals("1")) {
							toggleButton2.setChecked(true);
							SharePersistent.getInstance().savePerference(getApplicationContext(), "iswift", "1");
						}else {
							toggleButton2.setChecked(false);
							SharePersistent.getInstance().savePerference(getApplicationContext(), "iswift", "0");
						}
						

						if (!StringUtils.isEmpty(deny_vedio)&&iswift.equals("1")) {
							toggleButton3.setChecked(true);
							SharePersistent.getInstance().savePerference(getApplicationContext(), "deny_vedio", "1");
						}else {
							toggleButton3.setChecked(false);
							SharePersistent.getInstance().savePerference(getApplicationContext(), "deny_vedio", "0");
						}
					}else {
						showToast(msgdata);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				break;
			
			default:
				break;
			}

		}
	};
	private void clearChat(){
		try {
			showDialog("请稍候...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
		

				
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
		
				
				try {
					
					String result  = HttpRequest.sendGetRequestWithMd5(AssistActivity.this,Define.host+"/api-user-main/clearChat", hashMap);
					LogUtils.v("test", "result--- "+result);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						handler.sendMessage(message);
						
				
					}else {
						Message message = new Message();
						message.what = 0;
						message.obj = "连接超时，请重试";
						handler.sendMessage(message);
						//handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		
		
	}
	
	private Handler  handler  =  new  Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			switch (msg.what) {
			case 0:
				//showToast("账号或者密码错误，请重试");
				String message=(String) msg.obj;
				showToast(message);
				break;
			case 1:
				String result= (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret  = jsonObject.getString("ret");
					String msgdata  = jsonObject.getString("msg");
					
					if (ret.equals("0")) {
						showToast("清除成功 ！");
						
					}else {
						showToast(msgdata);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				break;
			
			default:
				break;
			}
		};
	};
}
