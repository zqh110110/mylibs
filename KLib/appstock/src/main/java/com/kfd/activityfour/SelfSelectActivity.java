package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Type;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
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

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.RefreshListViewAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.SelfListBean;
import com.kfd.bean.StockBean;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.market.FuturesMarketActivity;
import com.kfd.market.MarketCenterActivity;
import com.kfd.market.TradeListActivity;
import com.kfd.ui.PullToRefreshListView;

/**
 * 自选股
 * 
 * @author 
 */
public class SelfSelectActivity extends BaseActivity implements OnClickListener {
	HomeActivityGroup parentActivity1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selfselect);
		parentActivity1 = (HomeActivityGroup) getParent();
		initTitle();
		initUI();
		initListView();
		initTitleButton();
		initData();
		gestureDetector = new GestureDetector(SelfSelectActivity.this,onGestureListener);  
		FlurryAgent.onPageView();
		initBottom();
		
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		data.clear();
		loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
	}

	private void initBottom() {
		if (getIntent().getStringExtra("type") != null) {
			backButton.setVisibility(View.GONE);
		}	
		Button button1, button2, button3, button4;
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		findViewById(R.id.button5).setVisibility(View.GONE);
		button1.setText("板块");
		button1.setVisibility(View.GONE);
		button2.setText("刷新");
		button3.setText("个股");
		button4.setText("返回");
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
	}

	private PullToRefreshListView pullToRefreshListView;// 自定义的listview
	private RefreshListViewAdapter adapter;
	private ArrayList<StockBean> data = new ArrayList<StockBean>();
	private View listView_footer;
	private TextView listView_foot_more;
	private ProgressBar listView_foot_progress;
	private int sumdata;
	Boolean flagBoolean = false;
	private GestureDetector gestureDetector;

	private void initListView() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
		// adapter = new RefreshListViewAdapter(getApplicationContext(), data);
		adapter = new RefreshListViewAdapter(getApplicationContext(), data,
				mHead);
		listView_footer = getLayoutInflater().inflate(R.layout.listview_footer,
				null);
		listView_foot_more = (TextView) listView_footer
				.findViewById(R.id.listview_foot_more);
		listView_foot_progress = (ProgressBar) listView_footer
				.findViewById(R.id.listview_foot_progress);
		// 由于自选股现在规定就17个所以去掉下面的页脚以及滑动监听代码
		// pullToRefreshListView.addFooterView(listView_footer);// 添加底部视图
		// 必须在setAdapter前
		pullToRefreshListView.setAdapter(adapter);
		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// 点击头部、底部栏无效
				if (arg2 == 0 || arg1 == listView_footer)
					return;

				final StockBean bean = data.get(arg2 - 1);
				if (bean == null
						|| ((bean.getStockcode() == null) || bean
								.getStockcode().trim().length() < 6)) {
					return;
				}
				
				//手势识别，左右滑动时禁止弹出交易对话框，防止误操作。
				if (flagBoolean) {
					return;
				}
				
				Context context;
				if (Cache.getCache("fromR")!=null) {
					context = SelfSelectActivity.this;
					LogUtils.v("sssss", "----ffffffffffffff-----------------");
				} else {
					context = getParent();
					LogUtils.v("sssss", "----nnnnnnnnnnnn-----------------");
				}
				
				String[] items = { "取消关注", "K线图", "交易" };
				AlertDialog dialog = new AlertDialog.Builder(
						context).setItems(items,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								switch (which) {
								case 0:
									deleteStockToSelf(bean);

									break;
								case 1:
									// 跳转到股票详情页面 ,同时将改stock的所有信息传递过去，以及整个list列表
									if (Cache.getCache("fromR")==null) {
									
									parentActivity1.startChildActivity("StockDetailActivity", new Intent(
				    						SelfSelectActivity.this, StockDetailActivity.class)
				    						.putExtra("stockbean", bean)
				    						.putExtra("list", data)
				    						.putExtra("postion", arg2)
				    						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
									}else {
										Intent intent = new Intent(
												getApplicationContext(),
												StockDetailActivity.class);
										intent.putExtra("stockbean", bean);
										intent.putExtra("list", data);
										intent.putExtra("postion", arg2);
										startActivity(intent);
										
									}
									
									break;
								case 2:
									if (Cache.getCache("fromR")==null) {
									parentActivity1.startChildActivity("PlaceAnOrderActivity", new Intent(
											SelfSelectActivity.this, PlaceAnOrderActivity.class)
				    						.putExtra("bean", bean)
				    						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
									}else {
										Intent intent1 = new Intent(
												getApplicationContext(),
												PlaceAnOrderActivity.class);
										intent1.putExtra("bean", bean);
										startActivity(intent1);
									}
									break;
								default:
									break;
								/*
								 * // 判断是否是TextView if (arg1 instanceof
								 * TextView) { bean = (StockBean) arg1.getTag();
								 * } else { TextView tv = (TextView)
								 * arg1.findViewById(R.id.name); bean =
								 * (StockBean) tv.getTag(); } if (bean == null)
								 * return; // 跳转到股票详情页面
								 * ,同时将改stock的所有信息传递过去，以及整个list列表 Intent intent
								 * = new Intent(getApplicationContext(),
								 * StockDetailActivity.class);
								 * intent.putExtra("stockbean", bean);
								 * intent.putExtra("list", data);
								 * intent.putExtra("postion", arg2);
								 * startActivity(intent);
								 */
								}
							}
						}).create();
				dialog.show();

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

	/**
	 * 取消关注
	 * 
	 * @param stockBean
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void deleteStockToSelf(final StockBean stockBean) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				HashMap<String, String>  hashMap  = new HashMap<String, String>();
				hashMap.put("request", "stocksUnfollow");
				hashMap.put("from", "2"); hashMap.put("mark", getMark());
				hashMap.put("appid", getDeviceId());
				hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
				hashMap.put("stock_code", stockBean.getStockcode());
				try {
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.log("test", "取消关注返回数据---> " + result);
					if (result != null && result.length() > 0) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						updateUIHandler.sendMessage(message);

					} else {
						updateUIHandler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private Handler updateUIHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				showToast("取消关注不成功");
				break;
			case 1:
				try {
					String string = (String) msg.obj;
					JSONObject jsonObject = JSONObject.parseObject(string);
					showToast(jsonObject.getString("message"));
					data.clear();
					loadData(1, listviewHandler,
							ConstantInfo.LISTVIEW_ACTION_REFRESH);
					
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
						if (msg.what==0 && !msgString.equals("success")) {
							showToast(msgString);
						}
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
				freshButton.setVisibility(View.VISIBLE);
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
			SelfListBean nlist1 = (SelfListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist1.getList());
			}

			break;
		case ConstantInfo.LISTVIEW_ACTION_INIT:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			SelfListBean nlist = (SelfListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist.getList());
			}
			break;
		case ConstantInfo.LISTVIEW_ACTION_SCROLL:
			SelfListBean list = (SelfListBean) obj;
			sumdata += what;
			if (data.size() > 0) {
				for (StockBean bean : list.getList()) {
					boolean b = false;
					for (StockBean bean2 : data) {
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
	
	private String  msgString="";

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
					HashMap<String, String>  hashMap  = new HashMap<String, String>();
					hashMap.put("request", "stocksFree");
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
				
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
			

					//hashMap.put("uid", userid);
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					
					JSONObject  jsonObject  = JSONObject.parseObject(result);
					msgString =  jsonObject.getString("message");
					
					LogUtils.log("test", "result  selft    " + result);
					if (result == null || result.length() < 1) {
						msg.what = 0;
					} else {
						// 数据解析

						SelfListBean selfListBean = SelfListBean
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
		titleTextView.setText("自选股");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private TextView name;
	private TextView recentprice;
	private TextView updownrange;
	private TextView updownamount;
	private TextView volume;
	private TextView open;
	private TextView preclose;
	private TextView stockcode;
	private ProgressBar progressBar;

	private void initUI() {
		progressBar = (ProgressBar) findViewById(R.id.titleProgress);

		mHead = (RelativeLayout) findViewById(R.id.head);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		name = (TextView) mHead.findViewById(R.id.name);
		recentprice = (TextView) mHead.findViewById(R.id.recentprice);
		updownrange = (TextView) mHead.findViewById(R.id.updownrange);
		updownamount = (TextView) mHead.findViewById(R.id.updownamount);
		volume = (TextView) mHead.findViewById(R.id.volume);
		open = (TextView) mHead.findViewById(R.id.open);
		preclose = (TextView) mHead.findViewById(R.id.preclose);
		// stockcode = (TextView) mHead.findViewById(R.id.stockecode);
		name.setText("股票名称");
		recentprice.setText("最新价");
		updownrange.setText("涨跌幅");
		updownamount.setText("涨跌额");
		volume.setText("成交量");
		open.setText("今开");
		preclose.setText("昨收");
		// stockcode.setText("股票代码");
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
			showOneStock();
			break;
		case R.id.button4:
			finish();
			break;

		default:
			break;
		}
	}
	
	/**
	 * 退出系统弹出dialog
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (Cache.getCache("fromR")!=null) {
			finish();
			return true;
		}
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void dialog() {
		AlertDialog.Builder builder = new Builder(getParent());
		builder.setMessage("是否退出软件？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//MainActivity.this.finish();
				ActivityManager.popall();
				//System.exit(0);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		try {
			
			builder.create().show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActivityManager.popall();
			//MainActivity.this.finish();
		}
	}
}
