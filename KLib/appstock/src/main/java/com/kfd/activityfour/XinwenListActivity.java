package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kfd.activityfour.R;
import com.kfd.adapter.MainListAdapter;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.bean.MainMesageBean;
import com.kfd.bean.MainMessageListBean;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.ui.PullToRefreshListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 新闻显示页面
 */
public class XinwenListActivity  extends  Activity  implements  OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newslist);
		initTitle();
		initUI();
		getData(ConstantInfo.LISTVIEW_ACTION_INIT);
	}
	
	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("资讯中心");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private Button newsbutton1, newsbutton2;

	private PullToRefreshListView pullToRefreshListView1,
			pullToRefreshListView2;
	private MainListAdapter adapter, adapter2;
	private ArrayList<MainMesageBean> arrayList = new ArrayList<MainMesageBean>();
	private ArrayList<MainMesageBean> arrayList1 = new ArrayList<MainMesageBean>();

	private View listView_footer;
	private TextView listView_foot_more;
	private ProgressBar listView_foot_progress;

	private void initUI() {
	
		newsbutton1 = (Button) findViewById(R.id.newsbutton1);
		newsbutton2 = (Button) findViewById(R.id.newsbutton2);
		newsbutton1.setOnClickListener(this);
		newsbutton2.setOnClickListener(this);
		listView_footer = getLayoutInflater().inflate(R.layout.listview_footer,
				null);
		listView_foot_more = (TextView) listView_footer
				.findViewById(R.id.listview_foot_more);
		listView_foot_progress = (ProgressBar) listView_footer
				.findViewById(R.id.listview_foot_progress);
		pullToRefreshListView1 = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
		pullToRefreshListView2 = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView2);
		adapter = new MainListAdapter(getApplicationContext(), arrayList);
		pullToRefreshListView1.addFooterView(listView_footer);
		pullToRefreshListView1.setAdapter(adapter);

		adapter2 = new MainListAdapter(getApplicationContext(), arrayList1);
		pullToRefreshListView2.addFooterView(listView_footer);
		pullToRefreshListView2.setAdapter(adapter2);

		pullToRefreshListView1
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// 点击头部、底部栏无效
						if (arg2 == 0 || arg1 == listView_footer)
							return;
						final MainMesageBean bean = arrayList.get(arg2 - 1);
						if (bean == null) {
							return;
						} else {
							// 跳转到新闻详情页面
							Intent intent = new Intent(getApplicationContext(),
									XinwenDetailActivity.class);
							intent.putExtra("bean", bean);
							startActivity(intent);

						}

					}
				});
		pullToRefreshListView2
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// 点击头部、底部栏无效
						if (arg2 == 0 || arg1 == listView_footer)
							return;
						final MainMesageBean bean = arrayList1.get(arg2 - 1);
						if (bean == null) {
							return;
						} else {

							// 跳转到新闻详情页面
							Intent intent = new Intent(getApplicationContext(),
									XinwenDetailActivity.class);
							intent.putExtra("bean", bean);
							startActivity(intent);
						}

					}
				});

		pullToRefreshListView1
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
					public void onRefresh() {
						getData(ConstantInfo.LISTVIEW_ACTION_REFRESH);

					}
				});
		pullToRefreshListView2
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
					public void onRefresh() {
						getData(ConstantInfo.LISTVIEW_ACTION_REFRESH);

					}
				});

	}

	private void getData(final int action) {
		listView_foot_progress.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {

				Message msg = new Message();
				try {
					// 数据获取

					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("mobile", "android");
					String url = ApiClient.makeUrl(ConstantInfo.parenturl1,
							hashMap);

					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl1, hashMap, "UTF-8");

					LogUtils.log("test", "result " + result);
					if (result == null || result.length() < 1) {
						msg.what = 0;
					} else {
						// 数据解析
						MainMessageListBean listBean = MainMessageListBean
								.parseData(result);
						msg.what = (int) (listBean.getDaofulist().size() + listBean
								.getFinancelist().size());
						msg.obj = listBean;
					}

				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				handler.sendMessage(msg);
			}
		}.start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			listView_foot_progress.setVisibility(View.GONE);

			if (msg.what >= 0) {

				// listview数据处理
				handleLvData(msg.what, msg.obj, msg.arg1);
			} else if (msg.what == -1) {
				// 有异常--显示加载出错 & 弹出错误消息

				Toast.makeText(getApplicationContext(), getResources().getString(R.string.errormessage),
						Toast.LENGTH_SHORT).show();
				Log.v("test", "bug1");
			}
			if (adapter.getCount() == 0) {
				pullToRefreshListView1.setTag(ConstantInfo.LISTVIEW_DATA_EMPTY);
				listView_foot_more.setText(R.string.load_empty);
				listView_foot_more.setVisibility(View.VISIBLE);
			}
			if (adapter2.getCount() == 0) {
				pullToRefreshListView2.setTag(ConstantInfo.LISTVIEW_DATA_EMPTY);
				listView_foot_more.setText(R.string.load_empty);
				listView_foot_more.setVisibility(View.VISIBLE);
			}

			if (msg.arg1 == ConstantInfo.LISTVIEW_ACTION_REFRESH) {
				// 刷新的时候判断是哪个在显示状态//一定要加上不然刷新的标示一直都在
				pullToRefreshListView1
						.onRefreshComplete(getString(R.string.pull_to_refresh_update)
								+ new Date().toLocaleString());
				pullToRefreshListView1.setSelection(0);
				pullToRefreshListView2
						.onRefreshComplete(getString(R.string.pull_to_refresh_update)
								+ new Date().toLocaleString());
				pullToRefreshListView2.setSelection(0);
			}

		};
	};

	private void handleLvData(int what, Object obj, int actiontype) {

		switch (actiontype) {
		case ConstantInfo.LISTVIEW_ACTION_REFRESH:
			MainMessageListBean nlist1 = (MainMessageListBean) obj;
			if (obj != null) {
				arrayList.clear();// 先清除原有数据
				arrayList.addAll(nlist1.getFinancelist());

				adapter.notifyDataSetChanged();
				pullToRefreshListView1.invalidate();
				pullToRefreshListView1.setTag(ConstantInfo.LISTVIEW_DATA_FULL);
				listView_foot_more.setText(R.string.load_full);
				arrayList1.clear();// 先清除原有数据
				arrayList1.addAll(nlist1.getDaofulist());

				LogUtils.log("test", "大小" + arrayList1.size());
				adapter2.notifyDataSetChanged();

				pullToRefreshListView2.invalidate();
				pullToRefreshListView2.setTag(ConstantInfo.LISTVIEW_DATA_FULL);
			}
			break;
		case ConstantInfo.LISTVIEW_ACTION_INIT:

			MainMessageListBean nlist2 = (MainMessageListBean) obj;
			if (obj != null) {

				arrayList.addAll(nlist2.getFinancelist());
				arrayList1.addAll(nlist2.getDaofulist());

				adapter.notifyDataSetChanged();
				pullToRefreshListView1.invalidate();

				adapter2.notifyDataSetChanged();
				pullToRefreshListView2.invalidate();

				pullToRefreshListView1.setTag(ConstantInfo.LISTVIEW_DATA_FULL);
				pullToRefreshListView2.setTag(ConstantInfo.LISTVIEW_DATA_FULL);
				listView_foot_more.setText(R.string.load_full);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			switch (v.getId()) {
					case R.id.newsbutton1:
						pullToRefreshListView1.setVisibility(View.VISIBLE);
						newsbutton1.setTextColor(getResources().getColor(R.color.white));
						newsbutton2.setTextColor(getResources().getColor(R.color.black));
						pullToRefreshListView2.setVisibility(View.GONE);
						isfirstshow = true;
						break;
					case R.id.newsbutton2:
						isfirstshow = false;
						pullToRefreshListView2.setVisibility(View.VISIBLE);
						pullToRefreshListView1.setVisibility(View.GONE);
						newsbutton2.setTextColor(getResources().getColor(R.color.white));
						newsbutton1.setTextColor(getResources().getColor(R.color.black));
						break;
					default:
						break;
			}
	}
	
	private boolean isfirstshow = true;
}
