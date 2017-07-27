package com.kfd.activityfour;

import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.kfd.api.HttpRequest;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class UserVipActivity  extends   BaseActivity {
	
	ImageView selectimageView1,selectimageView2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uservip);
		selectimageView1 = (ImageView) findViewById(R.id.selectimageView1);
		selectimageView2= (ImageView) findViewById(R.id.selectimageView2);
		loadData();
		initTitle("VIP服务");
		findViewById(R.id.applyviplayout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				applyVip();	
			}
		});
		findViewById(R.id.viplayout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (vipInfo.getIsvip().equals("1")) {
					startActivity(new Intent(getApplicationContext(), SelectTeacherActivity.class));
				}
			}
		});
		loadSecondData();
		
	}
	
	 private void loadSecondData(){
			
		   executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				String  result  = HttpRequest.sendGetRequestWithMd5(UserVipActivity.this, Define.host+"/api-user-main/get", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what=1;
					message.obj=  result;
					updateHandler.sendMessage(message);
				}else {
					updateHandler.sendEmptyMessage(0);
				}
			}
		});
	   }
	 /**
	  * withdraw 取款

injection
注资 

	  */
	 private String  withdraw,injection;
	 private Handler  updateHandler  = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			   case 0:
				
				break;
			   case 1:
				   String  resulString = (String) msg.obj;

					try {
						JSONObject jsonObject = new JSONObject(resulString);
						String ret  = jsonObject.getString("ret");
						if (ret.equals("0")) {
							JSONObject jsonObject2  =  jsonObject.getJSONObject("data");
							withdraw  = jsonObject2.optString("withdraw");
							injection  = jsonObject2.optString("injection");
						
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					break;
					default :
						break;
						}
		}; 
	 };
	private void applyVip(){
		showDialog("请稍候...");
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
		
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host+"/api-find-vip/apply", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message= new Message();
					message.what=1;
					message.obj = result;
					applyHandler.sendMessage(message);
				}else {
					applyHandler.sendEmptyMessage(0);
				}
			}
		});
	}
	private Handler   applyHandler = new Handler(){
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 0:
				showToast("升级失败");
				break;
			case 1:
				String  result  =  (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String  ret  = jsonObject.optString("ret");
					String  messgage  = jsonObject.optString("msg");
					showToast(messgage);
					if (ret.equals("5002")) {
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								runOnUiThread( new Runnable() {
									public void run() {
										if (!StringUtils.isEmpty(withdraw)) {
											Intent intent  =  new Intent(getApplicationContext(), ZhuzhiActivity.class);
											intent.putExtra("type", "zhuzhi");
											intent.putExtra("url", injection);
											startActivity(intent);
										}
									}
								});
							}
						}, 3000);
					
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
	private void loadData(){
		showDialog("请稍候...");
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
		
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host+"/api-find-vip/get", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message= new Message();
					message.what=2;
					message.obj = result;
					updateUIHandler.sendMessage(message);
				}else {
					updateUIHandler.sendEmptyMessage(3);
				}
			}
		});
	}
	VipInfo   vipInfo;
	private Handler updateUIHandler =  new Handler(){
		public void handleMessage(Message msg) {
		
			dismissDialog();
			switch (msg.what) {
	
			case 2:
	
				String resultsecond = (String) msg.obj;
				try {
					JSONObject jsonObject  = new JSONObject(resultsecond);
					JSONObject  data  =  jsonObject.optJSONObject("data");
					 vipInfo = new VipInfo();
					vipInfo.setIsvip(data.optString("isvip"));
					vipInfo.setIsto(data.optString("isto"));
					/**
					 * isvip
 是否VIP 1-是 0-否
 
isto
 是否达到vip 1-是 0-否 

					 */
					if (vipInfo.getIsvip().equals("1")) {
						selectimageView2.setVisibility(View.VISIBLE);
						selectimageView1.setVisibility(View.GONE);
					}else {
						selectimageView2.setVisibility(View.GONE);
						selectimageView1.setVisibility(View.VISIBLE);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case  3:
				
				break;
			default:
				break;
			}
		};
	};
	
	private   class  VipInfo{
		private String isvip,isto;

		public String getIsvip() {
			return isvip;
		}

		public void setIsvip(String isvip) {
			this.isvip = isvip;
		}

		public String getIsto() {
			return isto;
		}

		public void setIsto(String isto) {
			this.isto = isto;
		}
		
	}

}
