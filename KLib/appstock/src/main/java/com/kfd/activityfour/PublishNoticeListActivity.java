package com.kfd.activityfour;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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

import com.kfd.adapter.MessageListAdapter;
import com.kfd.adapter.PublishAdapter;
import com.kfd.api.AppContext;
import com.kfd.api.HttpRequest;
import com.kfd.bean.MessageBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.ui.PullToRefreshListView;
import com.kfd.ui.PullToRefreshListView.OnRefreshListener;

/**
 * 提醒消息
 * 
 * @author Administrator
 * 
 */
public class PublishNoticeListActivity extends BaseActivity {
	private ArrayList<MessageBean> arrayList = new ArrayList<MessageBean>();

	private PtrFrameLayout mPtrFrameLayout;
	private LoadMoreListViewContainer loadMoreListViewContainer;

	private ListView mListView;

	PublishAdapter adapter;

	private int p = 1;

	private int ps = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_list2);
		initTitle("提醒消息");

		// pull to refresh
		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_list_view_ptr_frame);

		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

				return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				arrayList.clear();
				p = 1;
				loadData();
			}
		});

		mListView = (ListView) findViewById(R.id.load_more_small_image_list_view);

		loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
		loadMoreListViewContainer.useDefaultHeader();

		adapter = new PublishAdapter(arrayList, getApplicationContext(), getLayoutInflater());
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent intent = new Intent(getApplicationContext(), PublishDetailActivity.class);

				intent.putExtra("messageBean", arrayList.get(position));
				startActivity(intent);
			}
		});
		
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				p ++;
				loadData();
			}
		});
		
		mPtrFrameLayout.autoRefresh();

	}

	private void loadData() {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("p", String.valueOf(p));
				hashMap.put("ps", String.valueOf(ps));
				String result = HttpRequest.sendGetRequestWithMd5(PublishNoticeListActivity.this, Define.host + "/api-user-msg/list", hashMap);
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
			mPtrFrameLayout.refreshComplete();
			loadMoreListViewContainer.loadMoreFinish(false, true);
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
						 * "id": "3",
						 * 
						 * "title": "活动上线啦",
						 * 
						 * "isnew": "1",
						 * 
						 * "dateline": "1435161852"
						 */

						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonObject3 = array.optJSONObject(i);
							MessageBean messageBean = new MessageBean();
							messageBean.setDateline(jsonObject3.optString("dateline"));
							messageBean.setId(jsonObject3.optString("id"));
							// messageBean.setIsnew(jsonObject3.optString("isnew"));
							messageBean.setTitle(jsonObject3.optString("title"));
							arrayList.add(messageBean);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};
}
