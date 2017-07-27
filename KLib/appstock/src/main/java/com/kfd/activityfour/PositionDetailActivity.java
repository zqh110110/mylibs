package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.PositionRefreshAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.PositionBean;
import com.kfd.bean.PositionListBean;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.market.TradeListActivity;
import com.kfd.ui.PullToRefreshListView;

/**
 * 持仓明细
 * 
 * @author
 */
public class PositionDetailActivity extends BaseActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.positiondetail);
		initTitle();
		initUI();
		initListView();
		initTitleButton();
		initData();
		initBottom();
		gestureDetector = new GestureDetector(PositionDetailActivity.this,onGestureListener);
		FlurryAgent.onPageView();
	
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		data.clear();
		loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
	}
	private void initBottom() {
		Button button1, button2, button3, button4, button5;
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button5.setVisibility(View.GONE);

		button1.setText("板块");
		button1.setVisibility(View.GONE);
		button2.setText("刷新");
		button3.setText("个股");
		button3.setVisibility(View.GONE);
		button4.setText("返回");
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
	}

	private PullToRefreshListView pullToRefreshListView;// 自定义的listview
	private PositionRefreshAdapter adapter;
	private ArrayList<PositionBean> data = new ArrayList<PositionBean>();
	private View listView_footer;
	private TextView listView_foot_more;
	private ProgressBar listView_foot_progress;
	private int sumdata;
	Boolean flagBoolean = false;
	private GestureDetector gestureDetector;

	// result回传刷新页面
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 12 && resultCode == 10) {
			data.clear();
			loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
		}
	}

	private void initListView() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
		// adapter = new RefreshListViewAdapter(getApplicationContext(), data);
		adapter = new PositionRefreshAdapter(getApplicationContext(), data,
				mHead);
		listView_footer = getLayoutInflater().inflate(R.layout.listview_footer,
				null);
		listView_foot_more = (TextView) listView_footer
				.findViewById(R.id.listview_foot_more);
		listView_foot_more.setText(getResources().getText(R.string.load_full));
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
				
				//手势识别，左右滑动时禁止弹出交易对话框，防止误操作。
				if (flagBoolean) {
					return;
				}

				PositionBean positionBean = data.get(arg2 - 1);
				Intent intent = new Intent(getApplicationContext(),
						CloseOutActivity.class);
				intent.putExtra("bean", positionBean);
				startActivityForResult(intent, 12);

				// startActivity(intent);
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
							listView_foot_more.setText(R.string.load_full);
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
						lv.setTag(ConstantInfo.LISTVIEW_DATA_FULL);
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
			PositionListBean nlist1 = (PositionListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist1.getList());
			}

			break;
		case ConstantInfo.LISTVIEW_ACTION_INIT:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			PositionListBean nlist = (PositionListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist.getList());
			}
			break;
		case ConstantInfo.LISTVIEW_ACTION_SCROLL:
			PositionListBean list = (PositionListBean) obj;
			sumdata += what;
			if (data.size() > 0) {
				for (PositionBean bean : list.getList()) {
					boolean b = false;
					for (PositionBean bean2 : data) {
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
					/*String userid = SharePersistent.getInstance()
							.getUserInfo(getApplicationContext()).getUserid();*/
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "stocksPtlist");
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					hashMap.put("p", String.valueOf(pageIndex));
					//hashMap.put("uid", userid);
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.log("test", "result@@ " + result);
					if (result == null || result.length() < 1) {
						msg.what = 0;
					} else {
						// 数据解析

						PositionListBean positionDetailBean = PositionListBean
								.parseData1(result);
						msg.what = (int) positionDetailBean.getList().size();
						msg.obj = positionDetailBean;
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
		titleTextView.setText("持仓明细 ");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private TextView bills;
	private TextView jianchangtime;
	private TextView bianmaname;
	private TextView type;
	private TextView jianchangjia;
	private TextView downjia;
	private TextView upjia;
	private TextView nowjia;
	private TextView count;
	private TextView jianchangfei;
	private TextView hotfei;
	private TextView updown;
	private TextView daycount;
	private TextView liuchangfei;
	private TextView fudongyin;
	private ProgressBar progressBar;

	private void initUI() {
		progressBar = (ProgressBar) findViewById(R.id.titleProgress);
		mHead = (RelativeLayout) findViewById(R.id.head);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		bianmaname = (TextView) mHead.findViewById(R.id.bianmaname);
		jianchangtime = (TextView) mHead.findViewById(R.id.jianchangtime);
		type = (TextView) mHead.findViewById(R.id.type);
		jianchangjia = (TextView) mHead.findViewById(R.id.jianchangjia);
		downjia = (TextView) mHead.findViewById(R.id.downjia);
		upjia = (TextView) mHead.findViewById(R.id.upjia);
		nowjia = (TextView) mHead.findViewById(R.id.nowjia);
		count = (TextView) mHead.findViewById(R.id.count);
		jianchangfei = (TextView) mHead.findViewById(R.id.jianchangfei);
		hotfei = (TextView) mHead.findViewById(R.id.hotfei);
		updown = (TextView) mHead.findViewById(R.id.updown);
		daycount = (TextView) mHead.findViewById(R.id.daycount);
		liuchangfei = (TextView) mHead.findViewById(R.id.liuchangfei);
		fudongyin = (TextView) mHead.findViewById(R.id.fudongyin);
		bills = (TextView) mHead.findViewById(R.id.bills);

		bianmaname.setText("编码名称");
		jianchangtime.setText("建仓时间");
		type.setText("类型");
		jianchangjia.setText("建仓价");
		downjia.setText("止损价");
		upjia.setText("止盈价");
		nowjia.setText("现价");
		count.setText("股数");
		jianchangfei.setText("手续费");
		hotfei.setText("附加费");
		updown.setText("高涨跌");
		daycount.setText("留仓天数");
		liuchangfei.setText("留仓费");
		fudongyin.setText("浮动盈亏");
		bills.setText("单号");

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
			gestureDetector.onTouchEvent(arg1); 
			return false;
		}
	}
	
	//手势识别，左右滑动时禁止弹出交易对话框，防止误操作。
		private GestureDetector.OnGestureListener onGestureListener =   
			 new GestureDetector.SimpleOnGestureListener() {  
			 @Override  
			 public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
			  float velocityY) {  
				 float x = e2.getX() - e1.getX();  
				 if (x==0) {
					 flagBoolean = false;
				}else {
					flagBoolean = true;
					
				}
		      return true;  
		   }  
			 /*public boolean onSingleTapConfirmed(MotionEvent e) {  
				 Log.i("MyGesture", "onSingleTapConfirmed");  
				 flagBoolean=false;
				 return super.onSingleTapConfirmed(e);  
			 }  */
			 
			 public boolean onSingleTapUp(MotionEvent e) {  
				 flagBoolean=false;
				 return super.onSingleTapUp(e);  
			 }  
		};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			// 跳转到板块列表页
			startActivity(new Intent(getApplicationContext(),
					TradeListActivity.class));
			break;
		case R.id.button2:// 刷新
			data.clear();
			loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
			break;
		case R.id.button3:
			// 显示键盘对话框
			break;
		case R.id.button4:
			finish();
			break;

		default:
			break;
		}
	}
}
