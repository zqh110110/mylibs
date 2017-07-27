package com.kfd.activityfour;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.kfd.api.HttpRequest;
import com.kfd.common.Define;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.common.UpdateService;

import u.aly.n;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AboutActivity  extends  BaseActivity{
	
	 private void loadData(){
		 showDialog("请稍候...");
		   executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				String  result  = HttpRequest.sendGetRequestWithMd5(AboutActivity.this, Define.host+"/api-main/about", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what=1;
					message.obj=  result;
					handler.sendMessage(message);
				}else {
					handler.sendEmptyMessage(0);
				}
			}
		});
	   }
	 private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 1:
				  String  resulString = (String) msg.obj;

					try {
						JSONObject jsonObject = new JSONObject(resulString);
						String ret  = jsonObject.getString("ret");
						if (ret.equals("0")) {
							JSONObject  jsonObject2 = jsonObject.optJSONObject("data");
							/*8
							 * "data": {

        "tel": "400-689-1818",

        "qq": "800028539",

        "email": "kefe@kfd9999.com",

        "about": "http://kfd.demo.golds-cloud.com/info/about"

							 */
							String tel  =  jsonObject2.optString("tel");
							String  qq =  jsonObject2.optString("qq");
							String email  = jsonObject2.optString("email");
								about  =  jsonObject2.optString("about");
						
							
							}
						}catch (Exception e) {
							e.printStackTrace();
						}
				break;

			default:
				break;
			}
		}; 
	 };
	 String  about ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		initTitle("关于我们");
		loadData();
		findViewById(R.id.introlayout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (about!=null ) {
					Intent intent  =  new Intent(getApplicationContext(), ZhuzhiActivity.class);
		
					intent.putExtra("url", about);
					startActivity(intent);
				}
			}
		});
findViewById(R.id.feedbacklayout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  =  new Intent(getApplicationContext(), FeedBackActivity.class);
				
			
				startActivity(intent);
			}
		});
		
		findViewById(R.id.ablayout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  =  new Intent(getApplicationContext(), AboutUsActivity.class);
				
			
				startActivity(intent);
			}
		});
		findViewById(R.id.versionlayout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isConnectingToInternet()) {
					checkVersion();
//					CheckVersionAsynTask  checkVersionAsynTask  = new CheckVersionAsynTask();
//					checkVersionAsynTask.execute("");
					//if (!SharePersistent.getInstance().getPerference(getApplicationContext(), "install").toString().equals(getVersionName())) {
					//	LogUtils.v("test", "install-----");
					//install();
					//}
				}
			}
		});
		TextView versionTextView =	(TextView) findViewById(R.id.versiontextView2);
		versionTextView.setText(getVersionName());
	}
	// 获取当前应用的版本号：

		private String getVersionName() {
			// 获取packagemanager的实例
			PackageManager packageManager = getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo;
			try {
				packInfo = packageManager.getPackageInfo(getPackageName(), 0);
				String version = packInfo.versionName;
				LogUtils.v("MainActivity", "当前版本" + version);
				return version;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}
		
		// 判断当前版本是否比服务器上的版本低
		public Boolean isVersionLow(String nowVer, String ServerVer) {
			String nowVer_array = nowVer.replace(".", "");
			String ServerVer_array = ServerVer.replace(".", "");
			if (Integer.valueOf(nowVer_array) >= Integer.valueOf(ServerVer_array)) {
				return false;
			} else {
				return true;
			}
		}
		String  urlString;
	private void checkVersion(){
		showDialog("请稍候...");
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				
				hashMap.put("version", getVersionName());
				final	String result  = HttpRequest.sendGetRequestWithMd5(AboutActivity.this,Define.host+"/api-main/version", hashMap);
					
				LogUtils.v("sss", "CheckVersionAsynTask----"+result);
				if (!StringUtils.isEmpty(result)) {
						runOnUiThread(new Runnable() {
							public void run() {
								try {
									dismissDialog();
									JSONObject object = new JSONObject(result);
									JSONObject jsonObject = object.optJSONObject("data");
									JSONObject jsonObject1 = jsonObject.optJSONObject("info");
									String version = jsonObject1.getString("version");
										
										if (isVersionLow(getVersionName(),version)) {
											
											urlString = jsonObject1.getString("downurl");
											String message = jsonObject1.getString("msg");
											// 出现提示对话框
											AlertDialog dialog = new AlertDialog.Builder(
													AboutActivity.this)
													.setTitle("版本更新提示")
													.setMessage(message)
													.setPositiveButton(
															"确定",
															new DialogInterface.OnClickListener() {
																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	// TODO Auto-generated
																	// method stub
																	Intent intent = new Intent();
																	intent.putExtra("url", urlString);
																	intent.setClass(
																			getApplicationContext(),
																			UpdateService.class);
																	startService(intent);
																}
															})
													.create();
											dialog.show();
											}else {
												showToast("当前版本为最新版本");
											}
									
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}
						});
					
					}
			}
		});
	}
	
	/**
	 * 判断网络是否连接
	 * 
	 * @return true/false
	 */
	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
