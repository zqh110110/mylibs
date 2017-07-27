package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.AccountAdapter;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.market.EveningupDetailActivity;

import android.R.menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 股票交易中心
 * 
 * @author 朱继洋 QQ7617812 2013-5-20
 */
public class TradeCenterActivity extends BaseActivity {
	HomeActivityGroup parentActivity1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tradecenter);
		parentActivity1 = (HomeActivityGroup) getParent();
		initTitleButton();
		getBaocangbili();
		initUI();
		initTitle();
		FlurryAgent.onPageView();
		
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		getBaocangbili();
	}

	private void getBaocangbili(){
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 数据获取
			/*	String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				Map<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("request", "accountStocks");
				hashMap.put("from", "2"); hashMap.put("mark", getMark());
				hashMap.put("appid", getDeviceId());
				hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));

				String result = null;
				try {
					result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.v("test", "返回"+result);
					if (result!=null && result.length()>0) {
						Message message =  new Message();
						message.what=1;
						message.obj=result;
						handler.sendMessage(message);
	
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	private Handler  handler  = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String result  = (String) msg.obj;
				try {
					JSONObject  jsonObject =  JSONObject.parseObject(result);
					JSONObject jsonObject3 = jsonObject.getJSONObject("data");
					
					String jinzhi  =  jsonObject3.getString("saccount_jz");
					String zhanyong  =  jsonObject3.getString("saccount_zy");
					String baocangbili   = jsonObject3.getString("baoCangXsp");
					jinzhiTextView.setText(jinzhi);
					zyMoney.setText(zhanyong);
					baocangbiliTextView.setText(baocangbili);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};
	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("股票交易中心");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//parentActivity1.goBack();
				finish();
			}
		});
	}

	private ListView listView;
	View  view ;
	private TextView  jinzhiTextView,zyMoney,baocangbiliTextView;
	private String array[] = {"自选股","持仓明细","平仓明细","交易报表"};
	private void initUI() {

		listView = (ListView) findViewById(R.id.listView1);
		//添加footer
		LayoutInflater layoutInflater  = getLayoutInflater();
		 view  = layoutInflater.inflate(R.layout.baocangbili, null);
		 jinzhiTextView  = (TextView) view.findViewById(R.id.textView2);
		 zyMoney  = (TextView) view.findViewById(R.id.textView4);
		 baocangbiliTextView  =  (TextView) view.findViewById(R.id.textView6);
		listView.addFooterView(view);
		listView.setAdapter(new AccountAdapter(this,array));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					if (Cache.getCache("fromR")==null) {
						parentActivity1.startChildActivity("SelfSelectActivity", new Intent(
								TradeCenterActivity.this, SelfSelectActivity.class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
					}
					else {				
						startActivity(new Intent(TradeCenterActivity.this, SelfSelectActivity.class));
					}
					break;
				case 1:
					//parentActivity1.startChildActivity("PositionDetailActivity", new Intent(
					//		TradeCenterActivity.this, PositionDetailActivity.class)
					//		.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
					startActivity(new Intent(TradeCenterActivity.this, PositionDetailActivity.class));
					break;
				case 2:
					//parentActivity1.startChildActivity("EveningupDetailActivity", new Intent(
					//		TradeCenterActivity.this, EveningupDetailActivity.class)
					//		.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
					startActivity(new Intent(TradeCenterActivity.this, EveningupDetailActivity.class));
					break;
				case 3:
					//parentActivity1.startChildActivity("TransactionReportsActivity", new Intent(
					//		TradeCenterActivity.this, TransactionReportsActivity.class)
					//		.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
					startActivity(new Intent(TradeCenterActivity.this, TransactionReportsActivity.class));
					break;

				default:
					break;
				}
			}
		});
	}
}
