package com.kfd.activityfour;

import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.kfd.adapter.ChartListAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.ChartListData;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.ui.PullToRefreshListView;
import com.kfd.ui.PullToRefreshListView.OnRefreshListener;

/**
 * 聊天列表
 * 
 * @author Administrator
 * 
 */
public class ChartListActivity extends BaseActivity {

	protected static final int NETEORK_FAIL = 0x10;
	protected static final int DATA_FAIL = 0x11;
	protected static final int DATA_SUCCESS = 0x12;

	
	private PtrFrameLayout mPtrFrameLayout;

	private ListView mListView;

	List<ChartListData> dataList = new ArrayList<ChartListData>();

	private ChartListAdapter mAdapter;

	private static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ChartListActivity activity = (ChartListActivity) msg.obj;

			Bundle data = msg.getData();

			switch (msg.what) {
			case NETEORK_FAIL:
				Toast.makeText(activity, "网络异常", Toast.LENGTH_SHORT).show();
				break;

			case DATA_FAIL:
				Toast.makeText(activity, data.getString("message"), Toast.LENGTH_SHORT).show();
				break;

			case DATA_SUCCESS:

				break;
			}

			activity.mPtrFrameLayout.refreshComplete();

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart_list);
		initTitle("消息列表");
		
		// pull to refresh
		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_list_view_ptr_frame);

		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

				// here check list view, not content.
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				loadData();
			}
		});

		mListView = (ListView) findViewById(R.id.load_more_small_image_list_view);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
				intent.putExtra("chat", dataList.get(arg2).getNickname());
				intent.putExtra("url", dataList.get(arg2).getUrl());
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loadData();
	}

	private void loadData() {
		executorService.execute(new Runnable() {

			@Override
			public void run() {

				Message msg = new Message();

				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-find-vip/pm", hashMap);

				if (result == null || StringUtils.isEmpty(result)) {
					msg.what = NETEORK_FAIL;
					msg.obj = ChartListActivity.this;
					handler.sendMessage(msg);
					return;
				}

				try {
					JSONObject resultJson = new JSONObject(result);

					boolean ret = resultJson.has("ret") ? resultJson.optBoolean("ret") : false;
					String messageStr = resultJson.has("msg") ? resultJson.optString("msg") : "";
					String time = resultJson.has("time") ? resultJson.optString("time") : "";

					JSONObject dataJson = resultJson.getJSONObject("data");

					/*
					 * if (!ret) { msg.what = DATA_FAIL; msg.obj =
					 * ChartListActivity.this; Bundle data = new Bundle();
					 * data.putString("message", messageStr);
					 * handler.sendMessage(msg); return; }
					 */

					JSONArray listJsonArr = dataJson.getJSONArray("list");

					List<ChartListData> datas = new ArrayList<ChartListData>();

					for (int i = 0; i < listJsonArr.length(); i++) {
						ChartListData data = new ChartListData();

						JSONObject objJson = listJsonArr.getJSONObject(i);

						data.setDateline(objJson.optString("dateline"));
						data.setFace(objJson.optString("face"));
						data.setLastmsg(objJson.optString("lastmsg"));
						data.setNewnum(objJson.optString("newnum"));
						data.setNickname(objJson.optString("nickname"));
						data.setUid(objJson.optString("uid"));
						data.setUrl(objJson.optString("url"));
						datas.add(data);
					}
					dataList.clear();
					dataList.addAll(datas);
					msg.what = DATA_SUCCESS;
					msg.obj = ChartListActivity.this;
					handler.sendMessage(msg);

				} catch (Exception e) {

					e.printStackTrace();
				}

				ChartListActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						
						if(mAdapter == null)
						{
							mAdapter = new ChartListAdapter(ChartListActivity.this, dataList);
							mListView.setAdapter(mAdapter);
						}
						else
						{
							mAdapter.notifyDataSetChanged();
						}
					}
				});
			}
		});
	}

}
