package com.kfd.activityfour;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kfd.adapter.ActiveAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.ActiveBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ActiveEndFragement extends Fragment {
	View view;
	LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		view = inflater.inflate(R.layout.active, null);
		initUI();
		mPtrFrameLayout.autoRefresh();
		return view;
	}

	private PtrFrameLayout mPtrFrameLayout;

	private ListView mListView;
	ActiveAdapter adapter;

	private void initUI() {

		mListView = (ListView) view.findViewById(R.id.load_more_small_image_list_view);
		adapter = new ActiveAdapter(arrayList, inflater, ImageLoader.getInstance(), false);
		mListView.setAdapter(adapter);

		// pull to refresh
		mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.load_more_list_view_ptr_frame);

		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

				// here check list view, not content.
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				currentpage = 1;
				load(currentpage);
			}
		});
	}

	ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void load(final int pageindex) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("ps", "25");
				hashMap.put("typeid", "2");
				hashMap.put("p", pageindex + "");
				try {
					String result = HttpRequest.sendGetRequestWithMd5(getActivity(), Define.host + "/api-find-active/list", hashMap);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 2;
						message.obj = result;
						updateUIHandler.sendMessage(message);
					} else {
						updateUIHandler.sendEmptyMessage(3);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
	}

	private int currentpage;
	private ArrayList<ActiveBean> arrayList = new ArrayList<ActiveBean>();

	private Handler updateUIHandler = new Handler() {
		public void handleMessage(Message msg) {
			mPtrFrameLayout.refreshComplete();
			switch (msg.what) {

			case 2:
				currentpage++;
				String resultsecond = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(resultsecond);
					JSONObject data = jsonObject.optJSONObject("data");
					JSONArray priceArray = data.optJSONArray("list");
					arrayList.clear();
					for (int i = 0; i < priceArray.length(); i++) {
						JSONObject jsonObject2 = priceArray.optJSONObject(i);

						ActiveBean activeBean = new ActiveBean();
						activeBean.setId(jsonObject2.optString("id"));
						activeBean.setTitle(jsonObject2.optString("title"));
						activeBean.setThumb(jsonObject2.optString("thumb"));
						activeBean.setStarttime(jsonObject2.optString("starttime"));
						activeBean.setUrl(jsonObject2.optString("url"));
						activeBean.setIsjoin(jsonObject2.optString("isjoin"));
						arrayList.add(activeBean);
					}

					adapter.notifyDataSetChanged();

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 3:

				break;
			default:
				break;
			}
		};
	};
}
