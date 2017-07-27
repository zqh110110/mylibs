package com.kfd.activityfour;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
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
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kfd.adapter.PublishAdapter;
import com.kfd.api.AppContext;
import com.kfd.api.HttpRequest;
import com.kfd.bean.MessageBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.common.ToastUtils;

/**
 * 公告列表
 * 
 * @author zhan_hongyi
 */
public class AnnListActivity extends BaseActivity {

	private static final int NETWORK_FAIL = 0;
	private static final int LOAD_DATA_COMPLETE = 1;
	private static final int LOAD_MORE_DATA_COMPLETE = 2;

	private PtrFrameLayout mPtrFrameLayout;
	private LoadMoreListViewContainer loadMoreListViewContainer;

	private ListView mListView;

	private int pageSize = 8;

	private int currentPage = 1;

	private static InternalHandler sHandler = null;

	static {
		sHandler = new InternalHandler(Looper.getMainLooper());
	}

	private ArrayList<MessageBean> mDatas = new ArrayList<MessageBean>();

	private PublishAdapter mAdapter;

	private static class InternalHandler extends Handler {
		InternalHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			AnnListActivity activity = (AnnListActivity) msg.obj;
			switch (msg.what) {
			case NETWORK_FAIL:
				ToastUtils.show(activity, "请检查网络");
				activity.loadMoreListViewContainer.loadMoreFinish(false, true);
				break;

			case LOAD_DATA_COMPLETE:
				if (msg.getData() != null && msg.getData().getSerializable("list") != null) {
					@SuppressWarnings("unchecked")
					List<MessageBean> list = (List<MessageBean>) msg.getData().getSerializable("list");
					activity.mDatas.clear();
					activity.mDatas.addAll(list);
					activity.mAdapter.notifyDataSetChanged();
					activity.loadMoreListViewContainer.loadMoreFinish(false, true);
				}
				break;

			case LOAD_MORE_DATA_COMPLETE:
				if (msg.getData() != null && msg.getData().getSerializable("list") != null) {
					@SuppressWarnings("unchecked")
					List<MessageBean> list = (List<MessageBean>) msg.getData().getSerializable("list");
					activity.mDatas.addAll(list);
					activity.mAdapter.notifyDataSetChanged();
					activity.loadMoreListViewContainer.loadMoreFinish(false, true);
				}
				break;

			default:
				break;
			}
			activity.mPtrFrameLayout.refreshComplete();
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ann_list_layout);
		initTitle("公告");
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
				currentPage = 1;
				loadData(currentPage);
			}
		});

		mListView = (ListView) findViewById(R.id.load_more_small_image_list_view);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AnnListActivity.this, AnnDetailActivity.class);

				intent.putExtra("messageBean", mDatas.get(position));
				startActivity(intent);
			}
		});

		loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
		loadMoreListViewContainer.useDefaultHeader();

		// binding view and data
		mAdapter = new PublishAdapter(mDatas, AnnListActivity.this, LayoutInflater.from(AnnListActivity.this));
		mListView.setAdapter(mAdapter);
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				if (!AppContext.getInstance().isLogin()) {
					loadMoreContainer.loadMoreFinish(false, true);
					Intent intent = new Intent(AnnListActivity.this, LoginActivity.class);
					AnnListActivity.this.startActivity(intent);
					return;
				}
				currentPage++;
				loadData(currentPage);
			}
		});
		
		loadData(currentPage);
	}

	private void loadData(int pageIndex) {

		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("p", String.valueOf(currentPage));
				hashMap.put("ps", String.valueOf(pageSize));

				String result = HttpRequest.sendGetRequestWithMd5(AnnListActivity.this, Define.host + "/api-market/notice", hashMap);

				if (!StringUtils.isEmpty(result)) {

					ArrayList<MessageBean> datas = new ArrayList<MessageBean>();
					try {
						JSONObject jsonObject = new JSONObject(result);
						String ret = jsonObject.getString("ret");
						if (ret.equals("0")) {
							JSONObject jsonObject2 = jsonObject.optJSONObject("data");
							JSONArray array = jsonObject2.optJSONArray("list");

							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject3 = array.optJSONObject(i);
								MessageBean messageBean = new MessageBean();
								messageBean.setDateline(jsonObject3.optString("dateline"));
								messageBean.setId(jsonObject3.optString("id"));
								// messageBean.setIsnew(jsonObject3.optString("isnew"));
								messageBean.setTitle(jsonObject3.optString("title"));
								datas.add(messageBean);
							}

						}

						Message message = new Message();
						message.what = currentPage == 1 ? LOAD_DATA_COMPLETE : LOAD_MORE_DATA_COMPLETE;
						message.obj = AnnListActivity.this;
						Bundle data = new Bundle();
						data.putSerializable("list", datas);
						message.setData(data);
						sHandler.sendMessage(message);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Message message = new Message();
					message.what = NETWORK_FAIL;
					message.obj = AnnListActivity.this;
					sHandler.sendMessage(message);
				}

			}
		});

	}
}
