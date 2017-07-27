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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kfd.adapter.ETFGoldAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.ETFPostionBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

public class ETFSliverFragment extends Fragment {
	View view;
	
	private ListView mListView;

	private PtrFrameLayout mPtrFrameLayout;
	
	ETFGoldAdapter  adapter;
	private ArrayList<ETFPostionBean>  arrayList;
	LinearLayout loading ,llpreLoading,llLoadingFailed,nomoreload;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		view=  inflater.inflate(R.layout.etfgold,	 null);
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
				arrayList.clear();
				loadData();
			}
		});

		mListView = (ListView) view.findViewById(R.id.load_more_small_image_list_view);
		arrayList  =  new ArrayList<ETFPostionBean>();
		adapter   = new ETFGoldAdapter(arrayList, inflater.getContext(), inflater);
		mListView.setAdapter(adapter);
		loading = (LinearLayout)inflater.inflate(R.layout.generic_loading, null);
	    llpreLoading = (LinearLayout) loading.findViewById(R.id.llPreLoading);
	    llLoadingFailed = (LinearLayout) loading.findViewById(R.id.llLoadingFailed);
	    nomoreload  = (LinearLayout) loading.findViewById(R.id.foot_layout_no_more);
	    mListView.addFooterView(loading);
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (view.getLastVisiblePosition() == view.getCount() - 1  && !isLastPage) {
					if(isLoadMore){
						return;
					}
				
					isLoadMore = true;
					loadData();
					loading.setVisibility(View.VISIBLE);
		            llpreLoading.setVisibility(View.VISIBLE);
		            llLoadingFailed.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mPtrFrameLayout.autoRefresh();
		return view;
	}
	private boolean isLoadMore;

	private int  pageSize=25;
	private boolean isLastPage  = false;
	private int  currentpage=1;

	ExecutorService executorService  = Executors.newFixedThreadPool(5);
	private void loadData(){
			executorService.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					LinkedHashMap<String, String>  hashMap  =  new LinkedHashMap<String, String>();
					hashMap.put("typeid", "2");
					hashMap.put("ps", "25");
					hashMap.put("p", currentpage+"");
					try {
						String  result  =HttpRequest.sendGetRequestWithMd5(getActivity(), Define.host+"/api-find-main/eft", hashMap);
						if (!StringUtils.isEmpty(result)) {
							Message message = new Message();
							message.what=1;
							message.obj = result;
							updateHandler.sendMessage(message);
						}else {
							updateHandler.sendEmptyMessage(0);
							
						}
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}
	private Handler updateHandler = new Handler(){
		public void handleMessage(Message msg) {
			mPtrFrameLayout.refreshComplete();
			isLoadMore = false;
			loading.setVisibility(View.GONE);
            llpreLoading.setVisibility(View.GONE);
            llLoadingFailed.setVisibility(View.GONE);
			switch (msg.what) {
			case 0:
				
				break;
			case 1:
				if (currentpage==1) {
					arrayList.clear();
				}
				currentpage++;
				String resultsecond = (String) msg.obj;
				try {
					JSONObject jsonObject  = new JSONObject(resultsecond);
					JSONObject  data  =  jsonObject.optJSONObject("data");
					JSONArray   priceArray  = data.optJSONArray("list");
				
					for (int i = 0; i < priceArray.length(); i++) {
						JSONObject  jsonObject2  =  priceArray.optJSONObject(i);
						ETFPostionBean kuaiXun  = new ETFPostionBean();
						kuaiXun.setChange_nums(jsonObject2.optString("change_nums"));
						kuaiXun.setDateline(jsonObject2.optString("dateline"));
						kuaiXun.setNums(jsonObject2.optString("nums"));
						arrayList.add(kuaiXun);
					}
					if ( priceArray.length()<pageSize) {
						isLastPage = true;
					}
					if(isLastPage){
						//显示没有更多了
						//
						loading.setVisibility(View.VISIBLE);
						llpreLoading.setVisibility(View.GONE);
						llLoadingFailed.setVisibility(View.GONE);
						nomoreload.setVisibility(View.VISIBLE);
					}
				
					adapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};
	
}
