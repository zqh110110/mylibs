package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kfd.adapter.DirectseedAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.KFDPrice;
import com.kfd.bean.KuaiXun;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.ui.PullToRefreshListView;
import com.kfd.ui.PullToRefreshListView.OnRefreshListener;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class KuaiXunFragment extends  Fragment {
	/**
	 * 添加关注
	 * 
	 * @param stockBean
	 */
	public ExecutorService executorService = Executors.newFixedThreadPool(5);
	View view;
	PullToRefreshListView   pullToRefreshListView;
	DirectseedAdapter  directseedAdapter;
	private ArrayList<KuaiXun>  arrayList  = new ArrayList<KuaiXun>();
	Context context;
	LinearLayout loading ,llpreLoading,llLoadingFailed,nomoreload;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.kuaixun, null);
		context  =  inflater.getContext();
		pullToRefreshListView  = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView1);
		directseedAdapter   =new DirectseedAdapter(arrayList, inflater.getContext(), inflater);
		pullToRefreshListView.setAdapter(directseedAdapter);
		loading = (LinearLayout) inflater.inflate(R.layout.generic_loading, null);
	    llpreLoading = (LinearLayout) loading.findViewById(R.id.llPreLoading);
	    llLoadingFailed = (LinearLayout) loading.findViewById(R.id.llLoadingFailed);
	    nomoreload  = (LinearLayout) loading.findViewById(R.id.foot_layout_no_more);
	    pullToRefreshListView.addFooterView(loading);
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				currentpage=1;
				loadNEWS(currentpage);
			}
		});
		pullToRefreshListView.getParent().requestDisallowInterceptTouchEvent(true);
		   pullToRefreshListView.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO Auto-generated method stub
					if (view.getLastVisiblePosition() == view.getCount() - 1  && !isLastPage) {
						if(isLoadMore){
							return;
						}
						currentpage++;
						isLoadMore = true;
						loadNEWS(currentpage);
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
		loadNEWS(currentpage);
		
		return view;
	}
	private boolean isLoadMore;
	private boolean isLastPage  = false;
	/**
	* 动态设置ListView的高度
	* @param listView
	*/
	public  void setListViewHeightBasedOnChildren(ListView listView) {
		try {
			if(listView == null) return;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	}

	private int  currentpage=1;
	private int  pageSize=50;
	/**
	 * 快讯直播
	 */
	private void loadNEWS(final  int  pageindex){
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				hashMap.put("ps", pageSize+"");
				hashMap.put("p", pageindex+"");
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host+"/api-market/info", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message= new Message();
					message.what=2;
					message.obj = result;
					updateUIHandler.sendMessage(message);
				}else {
					updateUIHandler.sendEmptyMessage(3);
				}
				
				
			}
		});
	}
	private Handler updateUIHandler =  new Handler(){
		public void handleMessage(Message msg) {
			pullToRefreshListView.onRefreshComplete();
			isLoadMore = false;
			loading.setVisibility(View.GONE);
            llpreLoading.setVisibility(View.GONE);
            llLoadingFailed.setVisibility(View.GONE);
		//	dismissDialog();
			switch (msg.what) {
	
			case 2:
				currentpage++;
				String resultsecond = (String) msg.obj;
				try {
					JSONObject jsonObject  = new JSONObject(resultsecond);
					JSONObject  data  =  jsonObject.optJSONObject("data");
					JSONArray   priceArray  = data.optJSONArray("list");
					if (currentpage==1) {
						
						arrayList.clear();
					}
					for (int i = 0; i < priceArray.length(); i++) {
						JSONObject  jsonObject2  =  priceArray.optJSONObject(i);
						KuaiXun kuaiXun  = new KuaiXun();
						kuaiXun.setContent(jsonObject2.optString("content"));
						kuaiXun.setDateline(jsonObject2.optString("dateline"));
						kuaiXun.setId(jsonObject2.optString("id"));
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
					directseedAdapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(pullToRefreshListView);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case  3:
				
				break;
			default:
				break;
			}
		};
	};
}