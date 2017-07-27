package com.kfd.activityfour;

import java.io.IOException;
import java.nio.channels.AlreadyConnectedException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.flurry.android.FlurryAgent;
import com.flurry.org.apache.avro.io.DirectBinaryEncoder;
import com.kfd.activityfour.R;
import com.kfd.api.HttpRequest;
import com.kfd.bean.UserBean;
import com.kfd.common.Cache;
import com.kfd.common.Define;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.market.FuturesMarketActivity;
import com.kfd.market.MarketCenterActivity;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;



public class MainActivity extends TabActivity {
	public static int DIALOG_LOCATION = 100;
	public static int DIALOG_QUIT = 101;
	TabHost.TabSpec aroundTabSpec;
	boolean first;
	TabHost.TabSpec shoppingTabSpec;
	Handler handler;
	private boolean loginChecked;
	private Integer[] myMenuRes;
	TabHost.TabSpec cheapskateCardTabSpec;
	String[] names;
	TabHost.TabSpec discountTabSpec;
	TabHost.TabSpec indexTabSpec;
	TabHost tabHost;
	TabWidget tabWidget;



	public static TabHost localTabHost;
	public final static String TAB_TAG_mark = "行情";
	public final static String TAB_TAG_zhibo = "直播";
	public final static String TAB_TAG_find = "发现";
	public final static String TAB_TAG_mine = "我的";

	public int  currentindex=0;
  	private void setupIntent() {
		localTabHost = getTabHost();
		tabWidget  =getTabWidget();
		Intent woDeBangIntent = new Intent(this, MarkActivity.class);
		//推送埋点
		localTabHost.addTab(localTabHost.newTabSpec(TAB_TAG_mark)
				.setIndicator(buildIndicator("", R.drawable.menubtn01_curr, 0))
				.setContent(woDeBangIntent));

		localTabHost.addTab(localTabHost.newTabSpec(TAB_TAG_zhibo)
				.setIndicator(buildIndicator("", R.drawable.menubtn02, 1))
				.setContent(new Intent(this, ZhiBoActivity.class)));

		localTabHost.addTab(localTabHost.newTabSpec(TAB_TAG_find)
				.setIndicator(buildIndicator("", R.drawable.menubtn03, 2))
				.setContent(new Intent(this, FindActivity.class)));

		localTabHost.addTab(localTabHost.newTabSpec(TAB_TAG_mine)
				.setIndicator(buildIndicator("", R.drawable.menubtn04, 3))
				.setContent(new Intent(this, WodeActivity.class)));

	
		localTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals(TAB_TAG_mark)) {
					currentindex=0;
					
					
				}	else if (tabId.equals(TAB_TAG_zhibo)) {
					currentindex=4;
					
					
				} else if (tabId.equals(TAB_TAG_find)) {
					currentindex=3;
				
	
					
				} else if (tabId.equals(TAB_TAG_mine)) {
					currentindex=2;
					
					
				}
			}
		});
	}
  	ExecutorService executorService =  Executors.newFixedThreadPool(5);
	/**
	 * 获取更新地区数据
	 */
	private void getRegion(){
		executorService.execute(new Runnable() { 
			public void run() {  
				try {
					String data =getRegion(MainActivity.this);
					if(!data.equals("")){
						PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("area_string", data).commit(); 
					}
				} catch (Exception e) { 
					e.printStackTrace();
				}
			}
		});  
	}
	/**
	 * 获取地区数据
	 */
	public static String getRegion(Activity activity) throws Exception{
		String uriAPI = Define.host + "/api-user/address";
		LinkedHashMap<String, String>  hashMap = new LinkedHashMap<String, String>();
		String strResult  =  HttpRequest.sendGetRequestWithMd5(activity, uriAPI, hashMap); 
		String areaData =""; 
		JSONObject jsonObject = null; 
		try {
			jsonObject = new JSONObject(strResult); 
			String ret = jsonObject.optString("ret");
			if("0".equals(ret)){
				areaData = jsonObject.optString("data");
			}
		} catch (JSONException e) {
		 
		}
		return areaData; 
	}
  	LinearLayout wodeView,dynamicView,findView,recommendview;
	private LinearLayout buildIndicator(String resLabel, int resIcon, int id) {
		LinearLayout tabIndicator = null;
		switch (id) {
		case 0:
			wodeView=(LinearLayout) LayoutInflater.from(this).inflate(
					R.layout.main_tab_indicator_1, null, false);
			tabIndicator = wodeView;
			break;
		case 1:
			dynamicView= (LinearLayout) LayoutInflater.from(this).inflate(
					R.layout.main_tab_indicator_2, null, false);
			tabIndicator = dynamicView;
			break;
		case 2:
			findView  =  (LinearLayout) LayoutInflater.from(this).inflate(
					R.layout.main_tab_indicator_3, null, false);
			tabIndicator = findView;
			break;
		case 3:
			recommendview= (LinearLayout) LayoutInflater.from(this).inflate(
					R.layout.main_tab_indicator_4, null, false);
			tabIndicator = recommendview;
			break;
		
		default:
			break;
		}
		return tabIndicator;
	}
	
	

@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	
	AlertDialog  alertDialog = new AlertDialog.Builder(MainActivity.this).setMessage("是否退出应用?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			finish();
			ActivityManager.popall();
			System.exit(0);
		}
	}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	}).create();
	alertDialog.show();
}
	@Override
	public void finish() {
		super.finish();
		ActivityManager.pop();
	}



	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setRequestedOrientation(1);
		ActivityManager.push(this);
		setContentView(R.layout.maintab);
		setupIntent();
	//	initUI();
		Cache.put("main", getParent());
		getRegion();
		loadData();
		//FlurryAgent.onStartSession(this, "Y2VB5XRBP7J68VCFKNQQ");  
	}
	
	 private void loadData(){
	
		   executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				String  result  = HttpRequest.sendGetRequestWithMd5(MainActivity.this, Define.host+"/api-user-main/my", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what=1;
					message.obj=  result;
					userhandler.sendMessage(message);
				}else {
					userhandler.sendEmptyMessage(0);
				}
			}
		});
	   }
	   private  Handler userhandler  = new Handler(){
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
							JSONObject jsonObject3 = jsonObject2.getJSONObject("user");
							//Gson gson  = new Gson();
							//UserBean userBean = gson.fromJson(jsonObject3.toString(),new TypeToken<UserBean>(){}.getType());
							UserBean  userBean  = new UserBean();
							userBean.setUid(jsonObject3.optString("uid"));
							userBean.setBank(jsonObject3.optString("bank"));
							userBean.setBirthday(jsonObject3.optString("birthday"));
							userBean.setCity(jsonObject3.optString("city"));
							userBean.setFace(jsonObject3.optString("face"));
							userBean.setFollow_msg(jsonObject3.optString("follow_msg"));
							userBean.setIsemail(jsonObject3.optString("isemail"));
							userBean.setLike_msg(jsonObject3.optString("like_msg"));
							userBean.setLv(jsonObject3.optString("lv"));
							userBean.setMobile(jsonObject3.optString("mobile"));
							userBean.setNickname(jsonObject3.optString("nickname"));
							userBean.setProvince(jsonObject3.optString("province"));
							userBean.setRealname(jsonObject3.optString("realname"));
							userBean.setRec_msg(jsonObject3.optString("rec_msg"));
							userBean.setScores(jsonObject3.optString("scores"));
							userBean.setSex(jsonObject3.optString("sex"));
							userBean.setSignature(jsonObject3.optString("signature"));
							userBean.setSys_msg(jsonObject3.optString("sys_msg"));
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("userid", userBean.getUid()).commit();
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				
				   break;
			default:
				break;
			}
		   };
	   };

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//FlurryAgent.onEndSession(this); 
	}
	
	
	
	/**
	 * 退出系统弹出dialog
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void dialog() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage("是否退出软件？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//MainActivity.this.finish();
				ActivityManager.popall();
				System.exit(0);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		try {
			
			builder.create().show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActivityManager.popall();
			//MainActivity.this.finish();
		}
	}
	
}
