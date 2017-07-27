package com.kfd.market;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.BaseActivity;
import com.kfd.activityfour.R;
import com.kfd.activityfour.R.id;
import com.kfd.activityfour.R.layout;
import com.kfd.activityfour.R.string;
import com.kfd.activityfour.R.style;
import com.kfd.adapter.EveningupRefreshListViewAdapter;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.bean.EveninggupBean;
import com.kfd.bean.EveninggupListBean;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.ui.MyDialog;
import com.kfd.ui.PullToRefreshListView;

/**
 * 平仓明细
 * 
 * @author
 */
public class EveningupDetailActivity extends BaseActivity implements
		OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eveningupdetail);
		initTitle();
		initUI();
		initListView();
		initTitleButton();
		initData();
		initBottom();
		initMap();
		FlurryAgent.onPageView();
		
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		seachtype = "1";

		data.clear();
		loadData(1, listviewHandler,
				ConstantInfo.LISTVIEW_ACTION_REFRESH);
	}

	Button button1, button2, button3, button4, button5, button6;

	private void initBottom() {

		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button1.setText("买卖");
		button2.setText("起始日期");
		button3.setText("结束日期");
		findViewById(R.id.r4).setVisibility(View.GONE);

		// button4.setText("刷新");
		button5.setText("个股");
		button6.setText("返回");
		findViewById(R.id.r6).setVisibility(View.GONE);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
	}

	private PullToRefreshListView pullToRefreshListView;// 自定义的listview
	private EveningupRefreshListViewAdapter adapter;
	private ArrayList<EveninggupBean> data = new ArrayList<EveninggupBean>();
	private View listView_footer;
	private TextView listView_foot_more;
	private ProgressBar listView_foot_progress;
	private int sumdata;

	private void initListView() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
		// adapter = new RefreshListViewAdapter(getApplicationContext(), data);
		adapter = new EveningupRefreshListViewAdapter(getApplicationContext(),
				data, mHead);
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

				/*
				 * StockBean bean = null;
				 * 
				 * 
				 * if (bean == null) return; // 跳转到股票详情页面
				 * ,同时将改stock的所有信息传递过去，以及整个list列表 Intent intent = new
				 * Intent(getApplicationContext(), StockDetailActivity.class);
				 * intent.putExtra("stockbean", bean); intent.putExtra("list",
				 * data); intent.putExtra("postion", arg2);
				 * startActivity(intent);
				 */
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
							LogUtils.log("test1", "pageindex  " + pageIndex);
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
			EveninggupListBean nlist1 = (EveninggupListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist1.getList());
			}

			break;
		case ConstantInfo.LISTVIEW_ACTION_INIT:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			EveninggupListBean nlist = (EveninggupListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist.getList());
			}
			break;
		case ConstantInfo.LISTVIEW_ACTION_SCROLL:
			EveninggupListBean list = (EveninggupListBean) obj;
			sumdata += what;
			if (data.size() > 0 && list != null) {
				for (EveninggupBean bean : list.getList()) {
					boolean b = false;
					for (EveninggupBean bean2 : data) {
						if (bean.getStockcode() == bean2.getStockcode()) {
							b = true;
							break;
						}
					}
					if (!b)
						data.add(bean);
				}
			} else {
				data.addAll(list.getList());
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
				/*	String userid = SharePersistent.getInstance()
							.getUserInfo(getApplicationContext()).getUserid();*/
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "stocksColselist");
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					int  start   = (pageIndex-1)*ConstantInfo.PAGE_SIZE;
					hashMap.put("limit", String.valueOf(start));
					hashMap.put("offset", String.valueOf(ConstantInfo.PAGE_SIZE));
					boolean issearch = false;
					if (seachtype != null && seachtype.length() > 0) {
						hashMap.put("type", seachtype);
						issearch = true;
					}
					if (endtime != null && starttime != null
							&& endtime.length() > 3 && starttime.length() > 3) {
						LogUtils.v("start_date", ""+starttime);
						LogUtils.v("end_date", ""+endtime);
						hashMap.put("start_date", starttime);
						hashMap.put("end_date", endtime);

						issearch = true;
					}
					if (stockcode != null && stockcode.length() > 1) {
						issearch = true;
						hashMap.put("stock_code", stockcode);
					}
					String result = null;
						// 非搜索调用get
					result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");

					
					if (result == null || result.length() < 1) {
						msg.what = 0;
					} else {
						// 数据解析

						EveninggupListBean selfListBean = EveninggupListBean
								.parseData(result);
						msg.what = (int) selfListBean.getList().size();
						msg.obj = selfListBean;
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
		titleTextView.setText("平仓明细 ");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	TextView stocknameTextView;
	TextView type;
	TextView setupprice;
	TextView stoplossprice;
	TextView stopearnprice;
	TextView count;
	TextView setuptime;
	TextView closeout_price;
	TextView setup_cost;
	TextView closeout_cost;
	TextView forcecloseout_cost;
	TextView hot_cost;
	TextView gzf_cost;
	TextView live_cost;
	TextView yk;
	TextView numbers;
	TextView closeout_time;
	private ProgressBar progressBar;

	private void initUI() {
		progressBar = (ProgressBar) findViewById(R.id.titleProgress);

		mHead = (RelativeLayout) findViewById(R.id.head);
		mHead.setFocusable(true);
		mHead.setClickable(true);

		stocknameTextView = (TextView) mHead.findViewById(R.id.stockname);
		closeout_time = (TextView) mHead.findViewById(R.id.closeout_time);
		type = (TextView) mHead.findViewById(R.id.type);
		setupprice = (TextView) mHead.findViewById(R.id.setupprice);
		stoplossprice = (TextView) mHead.findViewById(R.id.stoplossprice);
		stopearnprice = (TextView) mHead.findViewById(R.id.stopearnprice);
		count = (TextView) mHead.findViewById(R.id.count);
		setuptime = (TextView) mHead.findViewById(R.id.setuptime);
		closeout_price = (TextView) mHead.findViewById(R.id.closeout_price);
		setup_cost = (TextView) mHead.findViewById(R.id.setup_cost);
		closeout_cost = (TextView) mHead.findViewById(R.id.closeout_cost);
		forcecloseout_cost = (TextView) mHead
				.findViewById(R.id.forcecloseout_cost);
		hot_cost = (TextView) mHead.findViewById(R.id.hot_cost);
		gzf_cost = (TextView) mHead.findViewById(R.id.gzf_cost);
		live_cost = (TextView) mHead.findViewById(R.id.live_cost);
		yk = (TextView) mHead.findViewById(R.id.yk);
		numbers = (TextView) mHead.findViewById(R.id.numbers);

		stocknameTextView.setText("编码名称");
		closeout_time.setText("平仓时间");
		type.setText("类型");
		setupprice.setText("建仓价");
		stoplossprice.setText("止损价");
		stopearnprice.setText("止盈价");
		count.setText("数量");
		setuptime.setText("建仓时间");
		closeout_price.setText("平仓价");
		setup_cost.setText("建仓费");
		closeout_cost.setText("平仓费");
		forcecloseout_cost.setText("强制平仓费");
		hot_cost.setText("热门手续费");
		gzf_cost.setText("高涨跌幅费");
		live_cost.setText("留仓费");
		yk.setText("盈亏");
		numbers.setText("单号");

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

	/**
	 * 买卖pupopWindwo
	 */
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private int state;
	private ListView menulist;
	private View layout;
	private PopupWindow pop;

	private void initPopup() {
		button1 = (Button) findViewById(R.id.button1);
		if (state == 1 && pop.isShowing()) {
			state = 0;
			pop.dismiss();
		} else {
			/*** 弹出自定义的菜单 ***/
			layout = getLayoutInflater().inflate(R.layout.poplistview, null);
			menulist = (ListView) layout.findViewById(R.id.menulist);
			SimpleAdapter listAdapter = new SimpleAdapter(
					getApplicationContext(), list, R.layout.listitem11,
					new String[] { "menuItemName" },
					new int[] { R.id.menuitem });
			menulist.setAdapter(listAdapter);

			/**
			 * layout PopupWindow所显示的界面 myButton.getWidth() 设置PopupWindow宽度
			 * myButton.getHeight() * 3 + 5 设置PopupWindow宽度高度
			 */
			pop = new PopupWindow(layout, button1.getWidth(),
					button1.getHeight() * 2);

			ColorDrawable cd = new ColorDrawable(-0000);
			pop.setBackgroundDrawable(cd);
			// pop.showAsDropDown(v);

			pop.update();
			pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
			pop.setTouchable(true); // 设置popupwindow可点击
			pop.setOutsideTouchable(true); // 设置popupwindow外部可点击
			pop.setFocusable(true); // 获取焦点
			/* 设置popupwindow的位置 */
			pop.showAtLocation(layout, (button1.getHeight()) | Gravity.LEFT, 0,
					button1.getHeight() - 20);
			state = 1;
			pop.setTouchInterceptor(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					/**** 如果点击了popupwindow的外部，popupwindow也会消失 ****/
					if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						pop.dismiss();
						return true;
					}
					return false;
				}

			});
			/**** 点击listview中item的处理 ****/
			menulist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					switch (arg2) {
					case 0:
						// Toast.makeText(getApplicationContext(), "买  入",
						// Toast.LENGTH_SHORT).show();
						seachtype = "0";
						pop.dismiss();
						data.clear();
						loadData(1, listviewHandler,
								ConstantInfo.LISTVIEW_ACTION_REFRESH);
						break;
					case 1:
						// Toast.makeText(getApplicationContext(), "卖  出",
						// Toast.LENGTH_SHORT).show();
						seachtype = "1";
						pop.dismiss();
						data.clear();
						loadData(1, listviewHandler,
								ConstantInfo.LISTVIEW_ACTION_REFRESH);
						break;
					}
				}
			});
		}
	}

	private String seachtype;

	/**
	 * popup显示
	 */
	private void initMap() {
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("menuItemName", "买  涨");
		list.add(map1);
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("menuItemName", "买  跌");
		list.add(map2);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:// 买卖
			showTypeDialog();
			// initPopup();
			break;
		case R.id.button2:
			isstart = "1";
			showDate();
			break;
		case R.id.button3:
			isstart = "0";
			showDate();
			break;
		case R.id.button4:
			data.clear();
			loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
			break;
		case R.id.button5:
			showStockDialog();
			break;
		case R.id.button6:
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 买卖dialog
	 */
	private void showTypeDialog() {
		String type[] = { "买涨", "买跌" };

		AlertDialog alertDialog = new AlertDialog.Builder(
				EveningupDetailActivity.this).setTitle("买卖类型")
				.setItems(type, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							seachtype = "1";

							data.clear();
							loadData(1, listviewHandler,
									ConstantInfo.LISTVIEW_ACTION_REFRESH);
							break;
						case 1:
							seachtype = "0";

							data.clear();
							loadData(1, listviewHandler,
									ConstantInfo.LISTVIEW_ACTION_REFRESH);
							break;
						default:
							break;
						}
					}
				}).create();
		alertDialog.show();

	}

	private String stockcode;

	public void showStockDialog() {

		final Dialog dialog = new MyDialog(context, R.style.MyDialog);
		dialog.show();
		// 这种方法设置的dialog有边框
		final EditText editText = (EditText) dialog.findViewById(R.id.edtText);
		Button button = (Button) dialog.findViewById(R.id.button1);
		Button cancelbutton = (Button) dialog.findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean flage = false;

				stockcode = editText.getText().toString();
				if (stockcode != null && stockcode.length() > 0) {
					if (editText.length() < 8 || editText.length() > 8) {
						flage = true;
					}
					
				
						data.clear();
						loadData(1, listviewHandler,
								ConstantInfo.LISTVIEW_ACTION_REFRESH);
						dialog.dismiss();
					
				}

			}
		});
		cancelbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

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
				button2.setText(starttime);
			}
			if (isstart.equals("0")) {
				endtime = date;
				button3.setText(endtime);
				// System.out.println(starttime+"            "+endtime);
				// 点击完结束时间后重新加载数据
				data.clear();
				loadData(1, listviewHandler,
						ConstantInfo.LISTVIEW_ACTION_REFRESH);
			}

		}

	};

}
