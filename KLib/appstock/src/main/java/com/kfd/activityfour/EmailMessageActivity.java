package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.AccountAdapter;
import com.kfd.adapter.EmailMessageAdapter;
import com.kfd.api.AppContext;
import com.kfd.api.HttpRequest;
import com.kfd.bean.CustomInfoBean;
import com.kfd.bean.StockBean;
import com.kfd.bean.TotalfundsBean;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.ui.SwitchButton;
import com.kfd.ui.SwitchButton.OnCheckedChangeListener;

/**
 * 
 * @ 消息中心
 *
 */
public class EmailMessageActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emailmessage);
		initTitleButton();
		freshButton.setVisibility(View.GONE);
		initTitle();
		getdata();
		FlurryAgent.onPageView();
	}

	private ImageView backButton;
	private TextView titleTextView;
	private ProgressDialog progressDialog;

	private void initTitle() {

		 backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("消息中心");
		
		 backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
				parentActivity1.goBack();
			}
		});
		 
	}

	
	private ArrayList<String> arry1,arry2;
	private ListView list1;
	
	private void initUI() {
		list1=(ListView) findViewById(R.id.listView1);
		list1.setCacheColorHint(0);
		list1.setAdapter(new EmailMessageAdapter(EmailMessageActivity.this, arry1, arry2));
	}
	
	/*
	 * 安全退出
	 */
	
	private void getdata() {
		try {
			progressDialog = new ProgressDialog(getParent());
			progressDialog.setMessage("加载数据中，请稍候.....");
			
			progressDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					//hashMap.put("request", "spreadPlacard");
					hashMap.put("request", "spreadAds");
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
			//}
		});
	}
	
	private Handler updateUIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case 0:
				showToast("加载数据失败");
				break;			
			case 1:
				
				try {
					String string = (String) msg.obj;
					JSONObject jsonObject = JSONObject.parseObject(string);
					JSONArray array = jsonObject.getJSONArray("data");
					arry1 = new ArrayList<String>();
					arry2 = new ArrayList<String>();
					if (array.size()>0) {
						for (int i = 0; i < array.size(); i++) {
							JSONObject jsonObject2 = array.getJSONObject(i);
							arry1.add(jsonObject2.getString("time"));
							arry2.add(jsonObject2.getString("title")+": "+jsonObject2.getString("descrip"));
						}					
					}else {
						showToast("没有消息");
					}
					initUI();
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
