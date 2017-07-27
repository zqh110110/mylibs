package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kfd.adapter.HelpAdapter;
import com.kfd.adapter.MessageListAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.HelpBean;
import com.kfd.bean.MessageBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.ui.PullToRefreshListView;
import com.kfd.ui.PullToRefreshListView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HelpCenterListActivity  extends   BaseActivity {
	private ArrayList<HelpBean>  arrayList   = new ArrayList<HelpBean>();
	PullToRefreshListView pullToRefreshListView1;
	HelpAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messagelist2);
		initTitle("帮助中心");
		pullToRefreshListView1  = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
		adapter  = new HelpAdapter(arrayList, getApplicationContext(), getLayoutInflater());
		pullToRefreshListView1.setAdapter(adapter);
		pullToRefreshListView1.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				arrayList.clear();
				loadData();
			}
		});
		pullToRefreshListView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
//				Intent intent  = new Intent(getApplicationContext(), MessageActivity.class);
//				
//				intent.putExtra("messageBean", arrayList.get(position-1));
//				startActivity(intent);
			}
		});
		loadData();
		
	}
	
	 private void loadData(){
		 showDialog("请稍候...");
		   executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				String  result  = HttpRequest.sendGetRequestWithMd5(HelpCenterListActivity.this, Define.host+"/api-main/help", hashMap);
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
			pullToRefreshListView1.onRefreshComplete();
			switch (msg.what) {
			case 1:
				  String  resulString = (String) msg.obj;

					try {
						JSONObject jsonObject = new JSONObject(resulString);
						String ret  = jsonObject.getString("ret");
						if (ret.equals("0")) {
							JSONObject  jsonObject2 = jsonObject.optJSONObject("data");
							JSONArray  array = jsonObject2.optJSONArray("help");
							/*      "id": "3",

                "title": "活动上线啦",

                "isnew": "1",

                "dateline": "1435161852"


							 */
							
							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject3 =  array.optJSONObject(i);
								HelpBean  helpBean  = new HelpBean();
								helpBean.setContent(jsonObject3.optString("content"));
								helpBean.setTitle(jsonObject3.optString("title"));
								arrayList.add(helpBean);
							}
					
							
							}
						}catch (Exception e) {
							e.printStackTrace();
						}
					adapter.notifyDataSetChanged();
					pullToRefreshListView1.invalidate();
				break;

			default:
				break;
			}
		}; 
	 };
}
