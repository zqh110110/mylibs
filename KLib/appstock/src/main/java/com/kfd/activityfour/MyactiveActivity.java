package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.kfd.adapter.WodeActiveAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.WodeActive;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.ui.PullToRefreshListView;
import com.kfd.ui.PullToRefreshListView.OnRefreshListener;

public class MyactiveActivity extends BaseActivity {

	private ArrayList<WodeActive> arrayList = new ArrayList<WodeActive>();
	PullToRefreshListView pullToRefreshListView1;
	WodeActiveAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messagelist2);
		initTitle("我的活动");
		pullToRefreshListView1 = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
		adapter = new WodeActiveAdapter(arrayList, getApplicationContext(), getLayoutInflater());
		pullToRefreshListView1.setAdapter(adapter);
		pullToRefreshListView1.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				arrayList.clear();
				loadData();
			}
		});
		pullToRefreshListView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!StringUtils.isEmpty(arrayList.get(position - 1).getUrl())) {
					Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(arrayList.get(position).getUrl()));
					it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
					startActivity(it);
				}

			}
		});
		loadData();

	}

	private void loadData() {
		showDialog("请稍候...");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(MyactiveActivity.this, Define.host + "/api-find-active/my", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 1;
					message.obj = result;
					handler.sendMessage(message);
				} else {
					handler.sendEmptyMessage(0);
				}
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dismissDialog();
			pullToRefreshListView1.onRefreshComplete();
			switch (msg.what) {
			case 1:
				String resulString = (String) msg.obj;

				try {
					JSONObject jsonObject = new JSONObject(resulString);
					String ret = jsonObject.getString("ret");
					if (ret.equals("0")) {
						JSONObject jsonObject2 = jsonObject.optJSONObject("data");
						JSONArray array = jsonObject2.optJSONArray("list");
						/*
						 * "dateline": "1435161411",
						 * 
						 * "url": "m-main/active?id=3",
						 * 
						 * "id": "3",
						 * 
						 * "title": "史上首家以被推荐人入金量计算奖金 奖金高达累计净入金量3%",
						 * 
						 * "endtime": "1438358400"
						 */

						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonObject3 = array.optJSONObject(i);
							WodeActive messageBean = new WodeActive();
							messageBean.setDateline(jsonObject3.optString("dateline"));
							messageBean.setId(jsonObject3.optString("id"));
							messageBean.setUrl(jsonObject3.optString("url"));
							messageBean.setTitle(jsonObject3.optString("title"));
							messageBean.setEndtime(jsonObject3.optString("endtime"));
							arrayList.add(messageBean);
						}

					}
				} catch (Exception e) {
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
