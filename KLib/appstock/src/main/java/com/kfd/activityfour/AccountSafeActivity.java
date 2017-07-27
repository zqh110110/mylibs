package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;









import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.AccountAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.CustomInfoBean;
import com.kfd.bean.TotalfundsBean;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.market.FuturesMarketActivity;
import com.kfd.market.MarketCenterActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 账户安全
 * 
 * @
 */
public class AccountSafeActivity extends BaseActivity {
	HomeActivityGroup parentActivity1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountsafe);
		parentActivity1 = (HomeActivityGroup) getParent();
		initUI();
		initTitleButton();
		freshButton.setVisibility(View.GONE);
		initTitle();
		getsafe();		
		FlurryAgent.onPageView();
	}

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		 backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("账户安全");
		
		 backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.goBack();
			}
		});
		 
	}

	private TextView accountsafe;
	private LinearLayout changepassword,emailcheck,phonecheck,changegetmoney;
	

	private void initUI() {
		
		accountsafe = (TextView) findViewById(R.id.accountsafe);
		changepassword = (LinearLayout) findViewById(R.id.changepassword);
		emailcheck = (LinearLayout) findViewById(R.id.emailcheck);
		phonecheck = (LinearLayout) findViewById(R.id.phonecheck);
		changegetmoney = (LinearLayout) findViewById(R.id.changegetmoney);
		
		changepassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("ChangePasswordActivity", new Intent(
						AccountSafeActivity.this, ChangePasswordActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
		
		emailcheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("EmailCheckActivity", new Intent(
						AccountSafeActivity.this, EmailCheckActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});

		phonecheck.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("CheckPhoneNumberActivity", new Intent(
						AccountSafeActivity.this, CheckPhoneNumberActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});

		changegetmoney.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("ChangeDrawMoneyPasswordActivity", new Intent(
						AccountSafeActivity.this, ChangeDrawMoneyPasswordActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
	}
	
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter(); 
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
	
        
    	private ExecutorService executorService = Executors.newFixedThreadPool(5);
    	
    	/*
    	 * 安全退出
    	 */
    	
    	private void getsafe() {
    		executorService.execute(new Runnable() {
    			@Override
    			public void run() {
    					Map<String, String> hashMap = new HashMap<String, String>();
    					hashMap.put("request", "userSaferank");
    					hashMap.put("appid", getDeviceId());
    					hashMap.put("from", "2"); hashMap.put("mark", getMark());
    					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
    					try {
    						String result = HttpRequest.sendPostRequest(
    								ConstantInfo.parenturl, hashMap, "UTF-8");


    						if (result != null && result.length() > 0) {
    							Message message = new Message();
    							message.what = 1;
    							message.obj = result;
    							updateUIHandler.sendMessage(message);
    						} else {
    							updateUIHandler.sendEmptyMessage(0);
    						}
    						LogUtils.log("test", "返回数据" + result);
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}

    				}
    		});
    	}

    	/**
    	 * ui数据匹配
    	 */
    	private Handler updateUIHandler = new Handler() {
    		public void handleMessage(android.os.Message msg) {
    			switch (msg.what) {
    			case 0:

    				break;
    			case 1:
    				try {
    					String string = (String) msg.obj;
    					JSONObject jsonObj1 = JSON.parseObject(string);
    					JSONObject jsonObj = jsonObj1.getJSONObject("data");
    					String string4 = jsonObj.getString("rank");
    					accountsafe.setText(string4);
						
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
	
}
