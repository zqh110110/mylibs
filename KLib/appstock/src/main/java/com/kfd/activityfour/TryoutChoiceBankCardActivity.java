package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.kfd.adapter.TryoutBankInfo;
import com.kfd.adapter.TryoutReportBankChoiceAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.ui.PullToRefreshListView;
import com.kfd.ui.PullToRefreshListView.OnRefreshListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;



public class TryoutChoiceBankCardActivity extends  BaseActivity{
	private Context context;

	
	LayoutInflater mInflater;
	private PullToRefreshListView  listView;
	private TryoutReportBankChoiceAdapter  adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmall_tryout_bank);
		initTitle("选择银行卡");
		mInflater  =getLayoutInflater();
	
		listView  = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
	

	
	//    listView.addFooterView(loading);
	    listView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				arrayList.clear();
				loadData();
			}
		});
	    adapter  = new TryoutReportBankChoiceAdapter(getApplicationContext(), arrayList);
	    listView.setAdapter(adapter);
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent  intent  = new Intent();
				if ((position-1)>=0) {
					intent.putExtra("TryoutBankInfo", arrayList.get(position-1));					
				}
				TryoutChoiceBankCardActivity.this.setResult(102, intent);
				finish();
			}
		});
	    
		loadData();
	}
	private ArrayList<TryoutBankInfo>  arrayList = new ArrayList<TryoutBankInfo>();
	private void loadData(){
		showDialog("请稍候..");
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					Message message  = new Message();
					LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
					String url=Define.host+"/api-user-main/getbank";

					String result = HttpRequest.sendGetRequestWithMd5(TryoutChoiceBankCardActivity.this, url, hashMap);
					if (!StringUtils.isEmpty(result)) {
					ArrayList<TryoutBankInfo> arrayList = TryoutBankInfo.parseInfo(result);
						if (arrayList!=null) {
							message.what=1;
							message.obj = arrayList;
							updateHandler.sendMessage(message);
						}else {
							updateHandler.sendEmptyMessage(0);
						}
					}
				} catch (Exception e) {
					updateHandler.sendEmptyMessage(0);
					e.printStackTrace();
				}
			}
		});
	}
	private Handler   updateHandler = new Handler(){
		public void handleMessage(Message msg) {
			dismissDialog();
		
		
			listView.onRefreshComplete();

	
			switch (msg.what) {
			case 0:
				listView.setVisibility(View.GONE);
				
				break;
			case 1:
				ArrayList<TryoutBankInfo> bankarrayList= (ArrayList<TryoutBankInfo>) msg.obj;
				arrayList.clear();
				arrayList.addAll(bankarrayList);
			    adapter.notifyDataSetChanged();
			    listView.invalidate();
				break;

			default:
				break;
			}
		};
	};


}

