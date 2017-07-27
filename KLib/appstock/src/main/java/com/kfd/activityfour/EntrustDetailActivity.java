package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.EntrustDetailAdapter;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.bean.EntrustDetailBean;
import com.kfd.bean.EntrustDetailListBean;
import com.kfd.bean.StockBean;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.ui.PullToRefreshListView;

/**
 * 委托明细
 * 
 * @author 
 */
public class EntrustDetailActivity extends BaseActivity implements
		OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entrustdetail);
		initTitle();
		initUI();
		initListView();
		initTitleButton();
		initData();
		initBottom();
		FlurryAgent.onPageView();
	
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		data.clear();
		loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
	}

	private Button button1, button2, button3, button4, button5;

	private void initBottom() {

		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		findViewById(R.id.r5).setVisibility(View.GONE);
		button5.setVisibility(View.GONE);
		button1.setText("开始日期");
		button2.setText("结束日期");
		button3.setText("刷新");
		button4.setText("返回");
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
	}

	private PullToRefreshListView pullToRefreshListView;// 自定义的listview
	private EntrustDetailAdapter adapter;
	private ArrayList<EntrustDetailBean> data = new ArrayList<EntrustDetailBean>();
	private View listView_footer;
	private TextView listView_foot_more;
	private ProgressBar listView_foot_progress;
	private int sumdata;

	private void initListView() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
		// adapter = new RefreshListViewAdapter(getApplicationContext(), data);
		adapter = new EntrustDetailAdapter(getApplicationContext(), data, mHead);
		listView_footer = getLayoutInflater().inflate(R.layout.listview_footer,
				null);
		listView_foot_more = (TextView) listView_footer
				.findViewById(R.id.listview_foot_more);
		listView_foot_progress = (ProgressBar) listView_footer
				.findViewById(R.id.listview_foot_progress);
		pullToRefreshListView.addFooterView(listView_footer);// 添加底部视图
																// 必须在setAdapter前
		pullToRefreshListView.setAdapter(adapter);
		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 点击头部、底部栏无效
				if (arg2 == 0 || arg1 == listView_footer)
					return;
				final EntrustDetailBean bean = data.get(arg2 - 1);
				if (bean == null) {
					return;
				}
				//调用取消委托的接口
				AlertDialog  alertDialog = new AlertDialog.Builder(EntrustDetailActivity.this)
				.setMessage("是否取消委托").setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						cancelEntrust(bean);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).create();
				alertDialog.show();
				

			}
		});

		pullToRefreshListView
				.setOnScrollListener(new AbsListView.OnScrollListener() {
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						pullToRefreshListView.onScrollStateChanged(view,
								scrollState);

						// 数据为空--不用继续下面代码了
						if (data.isEmpty())
							return;

						// 判断是否滚动到底部
						boolean scrollEnd = false;
						try {
							if (view.getPositionForView(listView_footer) == view
									.getLastVisiblePosition())
								scrollEnd = true;
						} catch (Exception e) {
							scrollEnd = false;
						}

						int lvDataState = StringUtils
								.toInt(pullToRefreshListView.getTag());
						if (scrollEnd
								&& lvDataState == ConstantInfo.LISTVIEW_DATA_MORE) {
							pullToRefreshListView
									.setTag(ConstantInfo.LISTVIEW_DATA_LOADING);
							listView_foot_more.setText(R.string.load_ing);
							listView_foot_progress.setVisibility(View.VISIBLE);
							// 当前pageIndex
							int pageIndex = sumdata / ConstantInfo.PAGE_SIZE
									+ 1;
							loadData(pageIndex, listviewHandler,
									ConstantInfo.LISTVIEW_ACTION_SCROLL);
						}
					}

					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						pullToRefreshListView.onScroll(view, firstVisibleItem,
								visibleItemCount, totalItemCount);
					}
				});
		pullToRefreshListView
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
					public void onRefresh() {
						loadData(1, listviewHandler,
								ConstantInfo.LISTVIEW_ACTION_REFRESH);
					}
				});

		pullToRefreshListView
				.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
	}
	
	/*
	 *取消委托
	 */
	private void cancelEntrust(final EntrustDetailBean  entrustDetailBean){
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
				/*	String userid = SharePersistent.getInstance()
							.getUserInfo(getApplicationContext()).getUserid();*/
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("c", "Deal");
					hashMap.put("a", "cancelDetail");
					hashMap.put("mobile", "android");
					hashMap.put("number", entrustDetailBean.getNumber());
					//hashMap.put("uid", userid);
			

					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					Log.v("test", "cancelresult "+result);
					if (result!=null && result.length()>0) {
						Message  message  = new Message();
						message.what=1;
						message.obj=result;
						handler.sendMessage(message);
					}else {
					handler.sendEmptyMessage(0);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	private Handler handler  =  new  Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				showToast("取消委托失败");
				break;
			case 1:
				String result  =  (String) msg.obj;
				try {
					JSONObject jsonObject =  JSONObject.parseObject(result);
					int  status  =  jsonObject.getIntValue("status");
					boolean dataresult  =  jsonObject.getBooleanValue("data");
					if (dataresult==true) {
						showToast("取消委托成功");
						//刷新
						data.clear();
						loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
					}else {
						showToast("取消委托失败");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};

	private Handler listviewHandler;

	private void initData() {
		// 初始化Handler
		listviewHandler = this.getLvHandler(pullToRefreshListView, adapter,
				listView_foot_more, listView_foot_progress,
				ConstantInfo.PAGE_SIZE);

		// 加载数据 pageindex从1开始
		if (data.isEmpty()) {
			loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_INIT);
		}
	}

	/**
	 * 获取listview的初始化Handler
	 * 
	 * @param lv
	 * @param adapter
	 * @return
	 */
	private Handler getLvHandler(final PullToRefreshListView lv,
			final BaseAdapter adapter, final TextView more,
			final ProgressBar progress, final int pageSize) {
		return new Handler() {
			public void handleMessage(Message msg) {
				progressBar.setVisibility(View.GONE);
				freshButton.setVisibility(View.VISIBLE);
				if (msg.what >= 0) {
					// listview数据处理
					handleLvData(msg.what, msg.obj, msg.arg1);

					if (msg.what < pageSize) { // 如果list元素的个数小于pagesize则全部加载，more显示加载全部，否则显示加载更多
						lv.setTag(ConstantInfo.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);
					} else if (msg.what == pageSize) {
						lv.setTag(ConstantInfo.LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_more);
					}
				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lv.setTag(ConstantInfo.LISTVIEW_DATA_MORE);
					more.setText(R.string.load_error);
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.errormessage),
							Toast.LENGTH_SHORT).show();
					Log.v("test", "bug1");
				}
				if (adapter.getCount() == 0) {
					lv.setTag(ConstantInfo.LISTVIEW_DATA_EMPTY);
					more.setText(R.string.load_empty);
				}
				progress.setVisibility(ProgressBar.GONE);
				if (msg.arg1 == ConstantInfo.LISTVIEW_ACTION_REFRESH) {
					lv.onRefreshComplete(getString(R.string.pull_to_refresh_update)
							+ new Date().toLocaleString());
					lv.setSelection(0);
				} else if (msg.arg1 == ConstantInfo.LISTVIEW_ACTION_CHANGE_CATALOG) {
					lv.onRefreshComplete();
					lv.setSelection(0);
				}
			}
		};
	}

	/**
	 * listview数据处理
	 * 
	 * @param what
	 *            数量
	 * @param obj
	 *            数据
	 * @param actiontype
	 *            操作类型
	 */
	private void handleLvData(int what, Object obj, int actiontype) {

		switch (actiontype) {
		case ConstantInfo.LISTVIEW_ACTION_REFRESH:
			EntrustDetailListBean nlist1 = (EntrustDetailListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist1.getList());
			}

			break;
		case ConstantInfo.LISTVIEW_ACTION_INIT:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			EntrustDetailListBean nlist = (EntrustDetailListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist.getList());
			}
			break;
		case ConstantInfo.LISTVIEW_ACTION_SCROLL:
			EntrustDetailListBean list = (EntrustDetailListBean) obj;
			sumdata += what;
			if (data.size() > 0 && list != null && list.getList() != null) {
				for (EntrustDetailBean bean : list.getList()) {
					boolean b = false;
					for (EntrustDetailBean bean2 : data) {
						if (bean.getStockcode() == bean2.getStockcode()) {
							b = true;
							break;
						}
					}
					if (!b)
						data.add(bean);
				}
			} else {
				if (list != null && list.getList() != null) {
					data.addAll(list.getList());
				}

			}
			break;

		}

	}

	/**
	 * 线程加载数据
	 * 
	 * @param pageIndex
	 *            当前页面
	 * @param handler
	 *            处理器
	 * @param action
	 *            动作标识
	 */
	// String markettype,String tradetype
	private void loadData(final int pageIndex, final Handler handler,
			final int action) {
		progressBar.setVisibility(View.VISIBLE);
		freshButton.setVisibility(View.GONE);
		new Thread() {
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if (action == ConstantInfo.LISTVIEW_ACTION_REFRESH
						|| action == ConstantInfo.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					// 数据获取
					/*String userid = SharePersistent.getInstance()
							.getUserInfo(getApplicationContext()).getUserid();*/
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "tradeEtlist");
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					hashMap.put("entrust_state", "1");
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));

					int  start   = (pageIndex-1)*ConstantInfo.PAGE_SIZE;
					hashMap.put("limit", String.valueOf(start));
					hashMap.put("offset", String.valueOf(ConstantInfo.PAGE_SIZE));
					//hashMap.put("uid", userid);

					Map<String, String> hashMap1 = new HashMap<String, String>();
				//	hashMap1.put("uid", userid);

					String url = ApiClient.makeUrl(ConstantInfo.parenturl,
							hashMap);
					boolean issearch = false;

					if (endtime != null && starttime != null
							&& endtime.length() > 3 && starttime.length() > 3) {
						hashMap1.put("start_date", starttime);
						hashMap1.put("end_date", endtime);
						issearch = true;
					}

					String result = null;

					if (issearch) {
						result = HttpRequest.sendHttpClientPost(url,
								hashMap1, "UTF-8");

					} else {
						result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
					}
					LogUtils.log("test", "委托明细    " + result);
					if (result == null || result.length() < 1) {
						msg.what = 0;
					} else {
						// 数据解析
						EntrustDetailListBean entrustDetailListBean = EntrustDetailListBean
								.parseData(result);
						msg.what = (int) entrustDetailListBean.getList().size();
						msg.obj = entrustDetailListBean;
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

	RelativeLayout mHead;

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("委托明细 ");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private TextView stockename;
	private TextView entrusttime;
	private TextView type;
	private TextView entrustprice;
	private TextView lowprice;
	private TextView highprice;
	private TextView recentprice;
	private TextView count;
	private TextView number;
	private ProgressBar progressBar;

	private void initUI() {
		progressBar = (ProgressBar) findViewById(R.id.titleProgress);

		mHead = (RelativeLayout) findViewById(R.id.head);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		stockename = (TextView) mHead.findViewById(R.id.stockname);
		entrusttime = (TextView) mHead.findViewById(R.id.entrusttime);
		type = (TextView) mHead.findViewById(R.id.type);
		entrustprice = (TextView) mHead.findViewById(R.id.entrustprice);
		lowprice = (TextView) mHead.findViewById(R.id.lowprice);
		highprice = (TextView) mHead.findViewById(R.id.highprice);
		recentprice = (TextView) mHead.findViewById(R.id.recentprice);
		count = (TextView) mHead.findViewById(R.id.count);
		number = (TextView) mHead.findViewById(R.id.number);

		stockename.setText("编码名称");
		entrusttime.setText("委托时间");
		type.setText("类型");
		entrustprice.setText("委托价");
		lowprice.setText("止损价");
		highprice.setText("止盈价");
		recentprice.setText("现价");
		count.setText("股数");
		number.setText("单号");

		// mHead.setBackgroundColor(Color.parseColor("#b2d235"));
		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
			HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			headSrcrollView.onTouchEvent(arg1);
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			isstart = "1";
			showDate();
			break;
		case R.id.button2:
			isstart = "0";
			showDate();
			break;
		case R.id.button3:// 刷新
			data.clear();
			loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
			break;
		case R.id.button4:
			finish();
			break;

		default:
			break;
		}
	}

	private String starttime;
	private String endtime;
	private String isstart;

	private void showDate() {
		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);

		mMonth = c.get(Calendar.MONTH);

		mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog datePickerDialog = new DatePickerDialog(this,
				mDateSetListener, mYear, mMonth,

				mDay);
		datePickerDialog.show();

	}

	private int mYear;
	private int mMonth;
	private int mDay;
	/**
	 * 
	 * 日期控件的事件
	 */

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,

		int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			String date = new StringBuilder().append(mYear).append("-").append(

			(mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
					.append(

					(mDay < 10) ? "0" + mDay : mDay).toString();
			if (isstart.equals("1")) {
				starttime = date;
				button1.setText(starttime);
			}
			if (isstart.equals("0")) {
				endtime = date;
				button2.setText(endtime);
				// System.out.println(starttime+"            "+endtime);
				// 点击完结束时间后重新加载数据
				data.clear();
				loadData(1, listviewHandler,
						ConstantInfo.LISTVIEW_ACTION_REFRESH);
			}

		}

	};

}
