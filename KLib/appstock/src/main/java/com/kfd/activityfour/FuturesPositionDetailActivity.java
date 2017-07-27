package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.PositionDetailActivity.ListViewAndHeadViewTouchLinstener;

import com.kfd.adapter.FuturesPositionRefreshAdapter;
import com.kfd.adapter.PositionRefreshAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.FuturesEntrustDetailBean;
import com.kfd.bean.FuturesPositionBean;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.market.FuturesPositionListBean;
import com.kfd.ui.PullToRefreshListView;
/**
 * 期货持仓单
 * @author Administrator
 *2013-9-16
 */
public class FuturesPositionDetailActivity extends BaseActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.futurespostiondetail);
		initTitle();
		initUI();
		initListView();
		initTitleButton();
		initData();
		//initBottom();
		gestureDetector = new GestureDetector(FuturesPositionDetailActivity.this,onGestureListener);  
		FlurryAgent.onPageView();
	
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		data.clear();
		loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
	}
	

	private PullToRefreshListView pullToRefreshListView;// 自定义的listview
	private FuturesPositionRefreshAdapter adapter;
	private ArrayList<FuturesPositionBean> data = new ArrayList<FuturesPositionBean>();
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
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 12 && resultCode == 10) {
			data.clear();
			loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
		}
	}

	private void initListView() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
		adapter = new FuturesPositionRefreshAdapter(getApplicationContext(), data,
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
				
				final FuturesPositionBean FuturesPositionBean = data.get(arg2 - 1);
				if (FuturesPositionBean.getWt_close_status().toString().equals("1")) {
					
					
					// 调用取消委托的接口
					AlertDialog alertDialog = new AlertDialog.Builder(
							FuturesPositionDetailActivity.this)
							.setMessage("是否取消委托")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											cancelEntrust(FuturesPositionBean);
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

										}
									}).create();
					alertDialog.show();
					
					
					
				}else {
					
					String[]  item ={"平仓","委托平仓"};
					AlertDialog  alertDialog  =  new AlertDialog.Builder(FuturesPositionDetailActivity.this)
					.setItems(item,new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							switch (which) {
							case 0:
								Intent intent = new Intent(getApplicationContext(),
										FuturesCloseOutActivity.class);
								intent.putExtra("bean", FuturesPositionBean);
								startActivityForResult(intent, 12);
								break;
							case 1:
								Intent intent1 = new Intent(getApplicationContext(),
										FuturesCloseOutActivity.class);
								intent1.putExtra("bean", FuturesPositionBean);
								intent1.putExtra("type", "entrust");
								startActivityForResult(intent1, 12);
								break;
							default:
								break;
							}
						}
					}).create();
					alertDialog.show();			
				}		
				
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
			FuturesPositionListBean nlist1 = (FuturesPositionListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist1.getList());
			}

			break;
		case ConstantInfo.LISTVIEW_ACTION_INIT:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			FuturesPositionListBean nlist = (FuturesPositionListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist.getList());
			}
			break;
		case ConstantInfo.LISTVIEW_ACTION_SCROLL:
			FuturesPositionListBean list = (FuturesPositionListBean) obj;
			sumdata += what;
			if (data.size() > 0) {
				for (FuturesPositionBean bean : list.getList()) {
					boolean b = false;
					for (FuturesPositionBean bean2 : data) {
						if (bean.getFuturescode() == bean2.getFuturescode()) {
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
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "futuresFtlist");
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
	
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.log("test", "result@@ " + result);
					if (result == null || result.length() < 1) {
						msg.what = 0;
					} else {
						// 数据解析

						FuturesPositionListBean positionDetailBean = FuturesPositionListBean
								.parseData(result);
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
	
	
	/*
	 * 取消委托
	 */
	private void cancelEntrust(
			final FuturesPositionBean futuresPositionBean) {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				try {
					// &a=cancelDetail&number=20130917153509'
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "futuresCanceltrust");
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					hashMap.put("pid", futuresPositionBean.getPid());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					Log.v("test", "cancelresult " + result);
					if (result != null && result.length() > 0) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						handler.sendMessage(message);
					} else {
						handler.sendEmptyMessage(0);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				showToast("取消委托失败");
				break;
			case 1:
				String result = (String) msg.obj;
				try {
					JSONObject jsonObject = JSONObject.parseObject(result);
					String status = jsonObject.getString("status");
					String mesString = jsonObject.getString("message");
					if (status.toString().equals("1")) {
						showToast(mesString);
						// 刷新
						data.clear();
						loadData(1, listviewHandler,
								ConstantInfo.LISTVIEW_ACTION_REFRESH);
					} else {
						showToast(mesString);
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

	RelativeLayout mHead;

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("期货持仓明细 ");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private TextView t1;
	private TextView t2;
	private TextView t3;
	private TextView t4;
	private TextView t5;
	private TextView t6;
	private TextView t7;
	private TextView t8;
	private TextView t9;
	private TextView t10;
	private TextView t11;
	private TextView t12;
	private TextView t13;

	private ProgressBar progressBar;

	private void initUI() {
		progressBar = (ProgressBar) findViewById(R.id.titleProgress);
		mHead = (RelativeLayout) findViewById(R.id.head);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		t1 = (TextView) mHead.findViewById(R.id.t1);
		t2 = (TextView) mHead.findViewById(R.id.t2);
		t3 = (TextView) mHead.findViewById(R.id.t3);
		t4 = (TextView) mHead.findViewById(R.id.t4);
		t5 = (TextView) mHead.findViewById(R.id.t5);
		t6 = (TextView) mHead.findViewById(R.id.t6);
		t7 = (TextView) mHead.findViewById(R.id.t7);
		t8 = (TextView) mHead.findViewById(R.id.t8);
		t9 = (TextView) mHead.findViewById(R.id.t9);
		t10 = (TextView) mHead.findViewById(R.id.t10);
		t11 = (TextView) mHead.findViewById(R.id.t11);
		t12 = (TextView) mHead.findViewById(R.id.t12);
		t13 = (TextView) mHead.findViewById(R.id.t13);
	

		t1.setText("建仓时间");
		t2.setText("合约代码");
		t3.setText("合约名称");
		t4.setText("类型");
		t5.setText("建仓价");
		t6.setText("止损价");
		t7.setText("止盈价");
		t8.setText("现价");
		t9.setText("合约数");
		t10.setText("建仓费");
		t11.setText("天数");
		t12.setText("留仓费");
		t13.setText("浮动盈亏");
		

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

