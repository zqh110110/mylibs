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
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.market.FuturesMarketActivity;
import com.kfd.market.MarketCenterActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 搜索
 * 
 * @
 */
public class SearchCodeActivity extends BaseActivity {
	public final static int RESULT_CODE=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchcode);
		initUI();
		initTitleButton();
		freshButton.setVisibility(View.GONE);
		initTitle();
		//getsafe();		
		FlurryAgent.onPageView();
	}

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		 backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		if (Cache.getCache("type").toString().equals("")) {
			LogUtils.v("test", "----"+getIntent().getStringExtra("type"));
			titleTextView.setText("沪深300");
		} else {
			LogUtils.v("test", "----sdfsdf");
			titleTextView.setText("A股行情");
		}
		
		 backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
				parentActivity1.goBack();
			}
		});
		 
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initUI();
		initTitle();
	}

	private ListView listView1;
	private AutoCompleteTextView search;
	
	private ListView list1,list2,list3;
	
	private Button searchButton,clearButton;
	 private ArrayAdapter<String> adapter_history; 

	private void initUI() {
		search = (AutoCompleteTextView) findViewById(R.id.auto);
		search.setThreshold(1); 
		
		searchButton = (Button) findViewById(R.id.button1);
		
		SharedPreferences sp = getSharedPreferences("history_strs", 0); 
        String save_history = sp.getString("history", ""); 
        final String[] hisArrays = save_history.split(","); 
        /* 
        adapter_history = new ArrayAdapter<String>(this, 
                android.R.layout.simple_dropdown_item_1line, hisArrays); 
       		if (hisArrays.length > 5) { 
            String[] newArrays = new String[50]; 
            System.arraycopy(hisArrays, 0, newArrays, 0, 50); 
            adapter_history = new ArrayAdapter<String>(this, 
                    android.R.layout.simple_dropdown_item_1line, newArrays); 
        } */
        searchButton.setOnClickListener(new Button.OnClickListener() { 


            public void onClick(View v) { 
                // TODO Auto-generated method stub 
            	String text = search.getText().toString();
            	if (text.equals("")) {
            		showToast("请输入要搜索的股票代码或名称");
            		return;
            	}
            	Save();
            	Cache.put("search", text);
            	
            	
            	if (Cache.getCache("type").toString().equals("")) {
            		//HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
    				//parentActivity1.startChildActivity("FuturesMarketActivity", new Intent(
    				//		SearchCodeActivity.this, FuturesMarketActivity.class)
    				//		.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    				finish();
					
				} else {
					//HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
    				//parentActivity1.startChildActivity("MarketCenterActivity", new Intent(
    				//		SearchCodeActivity.this, MarketCenterActivity.class)
    				//		.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    				finish();
				}
            	

            } 
        }); 
		
		clearButton = (Button) findViewById(R.id.clearbutton);
		
		clearButton.setOnClickListener(new Button.OnClickListener() { 


	            public void onClick(View v) { 
	                // TODO Auto-generated method stub 
	            	SharedPreferences sp = getSharedPreferences("history_strs", 0); 
	            	sp.edit().clear().commit();
	            	initUI();
	            } 
	        }); 
		
		ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
				R.layout.listitem22, R.id.textView1, hisArrays);
		listView1 = (ListView) findViewById(R.id.listView1);
		listView1.setAdapter(adapter); 
		
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				search.setText(hisArrays[arg2]);
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
        
        /**
         * 保存搜索记录
         */
        
        private void Save() {
            String text = search.getText().toString(); 
            SharedPreferences sp = getSharedPreferences("history_strs", 0); 
            String save_Str = sp.getString("history", ""); 
            String[] hisArrays = save_Str.split(","); 
            for(int i=0;i<hisArrays.length;i++) 
            { 
                if(hisArrays[i].equals(text)) 
                { 
                    return; 
                } 
            } 
            StringBuilder sb = new StringBuilder(save_Str); 
            sb.append(text + ","); 
            sp.edit().putString("history", sb.toString()).commit();  
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
    				String string = (String) msg.obj;
    				try {
						
    					JSONObject jsonObj1 = JSON.parseObject(string);
    					JSONObject jsonObj = jsonObj1.getJSONObject("data");
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
