package com.kfd.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.kfd.activityfour.AccountCenterActivity;
import com.kfd.activityfour.ActivityManager;
import com.kfd.activityfour.BaseActivity;
import com.kfd.activityfour.CustomInfoActivity;
import com.kfd.activityfour.FuturesAnOrderActivity;
import com.kfd.activityfour.HomeActivityGroup;
import com.kfd.activityfour.MainActivity;
import com.kfd.activityfour.PlaceAnOrderActivity;
import com.kfd.activityfour.SearchCodeActivity;
import com.kfd.activityfour.R;
import com.kfd.activityfour.R.id;
import com.kfd.activityfour.R.layout;
import com.kfd.activityfour.R.string;
import com.kfd.adapter.FuturesListAdapter;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.bean.FuturesBean;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.ui.MyDialog;
import com.kfd.ui.PullToRefreshListView;

public class FuturesMarketActivity extends BaseActivity  implements
OnClickListener  {
	private String searchString;
	HomeActivityGroup parentActivity1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.futureslayout);
		parentActivity1 = (HomeActivityGroup) getParent();
		initTitle();
		initUI();
		initListView();
		initPopupButton();
		initData();
		gestureDetector = new GestureDetector(FuturesMarketActivity.this,onGestureListener);  
		FlurryAgent.onPageView();
	}


private PullToRefreshListView pullToRefreshListView;// 自定义的listview
private FuturesListAdapter adapter;
private ArrayList<FuturesBean> data = new ArrayList<FuturesBean>();
private View listView_footer;
private TextView listView_foot_more;
private ProgressBar listView_foot_progress;
private int sumdata;
Boolean flagBoolean = false;
private GestureDetector gestureDetector;

private void initListView() {
	pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
	// adapter = new FuturesListAdapter(getApplicationContext(), data);
	adapter = new FuturesListAdapter(getApplicationContext(), data,
			mHead);
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
		public void onItemClick(AdapterView<?> arg0, View arg1,
				final int arg2, long arg3) {
			// 点击头部、底部栏无效
			if (arg2 == 0 || arg1 == listView_footer)
				return;
			final FuturesBean bean = data.get(arg2 - 1);
			if (bean == null) {
				return;
			}
			
			//手势识别，左右滑动时禁止弹出交易对话框，防止误操作。
			if (flagBoolean) {
				return;
			}
			
			String[] items = {  "K线图", "交易" };
			AlertDialog dialog = new AlertDialog.Builder(
					getParent()).setItems(items,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							switch (which) {
							
							case 0:
								// 跳转到股票详情页面 ,同时将改stock的所有信息传递过去，以及整个list列表
							/*	Intent intent = new Intent(
										getApplicationContext(),
										StockDetailActivity.class);
								intent.putExtra("FuturesBean", bean);
								intent.putExtra("list", data);
								LogUtils.log("test",
										"list size" + data.size());
								intent.putExtra("postion", arg2);
								startActivity(intent);*/
								break;
							case 1:
								
								parentActivity1.startChildActivity("FuturesAnOrderActivity", new Intent(
			    						FuturesMarketActivity.this, FuturesAnOrderActivity.class)
			    						.putExtra("bean", bean)
			    						.putExtra("type", "futures")
			    						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
								break;

							default:
								break;
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
	FuturesListBean nlist1 = (FuturesListBean) obj;
		if (obj != null) {
			sumdata = what;
			data.clear();// 先清除原有数据
			data.addAll(nlist1.getList());
		}

		break;
	case ConstantInfo.LISTVIEW_ACTION_INIT:
		int newdata = 0;// 新加载数据-只有刷新动作才会使用到
		FuturesListBean nlist = (FuturesListBean) obj;
		if (obj != null) {
			sumdata = what;
			data.clear();// 先清除原有数据
			data.addAll(nlist.getList());
		}
		break;
	case ConstantInfo.LISTVIEW_ACTION_SCROLL:
		FuturesListBean list = (FuturesListBean) obj;
		sumdata += what;
		if (data.size() > 0) {
			for (FuturesBean bean : list.getList()) {
				boolean b = false;
				for (FuturesBean bean2 : data) {
					if (bean.getCode() == bean2.getCode()) {
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
	new Thread() {
		public void run() {
			Message msg = new Message();
			boolean isRefresh = false;
			if (action == ConstantInfo.LISTVIEW_ACTION_REFRESH
					|| action == ConstantInfo.LISTVIEW_ACTION_SCROLL)
				isRefresh = true;
			try {
				// 数据获取
				Map<String, String>  hashMap  = new HashMap<String, String>();
					hashMap.put("request", "futuresCodes");
				hashMap.put("from", "2"); hashMap.put("mark", getMark());
				hashMap.put("appid", getDeviceId());
				int  start   = (pageIndex-1)*ConstantInfo.PAGE_SIZE;
				hashMap.put("start",String.valueOf(start));
				hashMap.put("number", String.valueOf(ConstantInfo.PAGE_SIZE));
				hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
				
				if (searchString!=null) {
					hashMap.put("codes",searchString);
				}
				searchString=null;
				
				String result = null;
			
					result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
				

				Log.v("test", "result " + result);
				if (result == null || result.length() < 1) {
					msg.what = 0;
				} else {
					FuturesListBean futuresListBean = null;
					futuresListBean   = FuturesListBean.parseListData(result);
					msg.what = (int) futuresListBean.getList().size();
					msg.obj = futuresListBean;
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

private ImageView backButton,searchButton;
private TextView titleTextView;
private PopupWindow popupWindow;
private LinearLayout layout;
private ListView listView;
private String title[] = { "沪深A股", "沪深300"};

private void initTitle() {

	backButton = (ImageView) findViewById(R.id.back);
	searchButton = (ImageView) findViewById(R.id.search);

	titleTextView = (TextView) findViewById(R.id.title_text);
	titleTextView.setText("沪深300");
	backButton.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
	
	searchButton.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub 
			Cache.put("type", "");
			parentActivity1.startChildActivity("SearchCodeActivity", new Intent(
					FuturesMarketActivity.this, SearchCodeActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		}
	});
}

public void initPopupButton(){
	titleTextView = (TextView) findViewById(R.id.title_text);
	popButton = (ImageView) findViewById(R.id.popimage);
	popButton.setVisibility(View.VISIBLE);
	searchButton = (ImageView) findViewById(R.id.search);
	searchButton.setVisibility(View.VISIBLE);
	final View line = findViewById(R.id.titleline);
	
	popButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			int y = line.getBottom()*3/2;
			int x = getWindowManager().getDefaultDisplay().getWidth() / 4;

			showPopupWindow(x, y);
		}
	});
	
	titleTextView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			int y = line.getBottom()*3/2;
			int x = getWindowManager().getDefaultDisplay().getWidth() / 4;

			showPopupWindow(x, y);
		}
	});
}




public void showPopupWindow(int x, int y) {
	layout = (LinearLayout) LayoutInflater.from(this).inflate(
			R.layout.market_popupwindow, null);
	listView = (ListView) layout.findViewById(R.id.lv_dialog);
	listView.setAdapter(new ArrayAdapter<String>(this,
			R.layout.text, R.id.tv_text, title));

	popupWindow = new PopupWindow(layout,
			getWindowManager().getDefaultDisplay().getWidth() / 2,
			WindowManager.LayoutParams.WRAP_CONTENT);
	popupWindow.setBackgroundDrawable(new BitmapDrawable());
	popupWindow.setOutsideTouchable(true);
	popupWindow.setFocusable(true);
	popupWindow.showAtLocation(findViewById(R.id.title_text), Gravity.LEFT
			| Gravity.TOP, x, y);

	listView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//titleTextView.setText(title[arg2]);
			if (arg2==0) {
				
				parentActivity1.startChildActivity("MarketCenterActivity", new Intent(
						FuturesMarketActivity.this, MarketCenterActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			} else if(arg2==1){
				
			}else {
				
			}
			
			
			popupWindow.dismiss();
			popupWindow = null;
		}
	});
}

private TextView name;
private TextView recentprice;
private TextView openprice;
private TextView preprice;
private TextView high;
private TextView low;
private TextView strikeamount,bearamount,cjlTextView;


private ProgressBar progressBar;

private void initUI() {
	progressBar = (ProgressBar) findViewById(R.id.titleProgress);
	mHead = (RelativeLayout) findViewById(R.id.head);
	mHead.setFocusable(true);
	mHead.setClickable(true);
	name = (TextView) mHead.findViewById(R.id.t1);
	recentprice = (TextView) mHead.findViewById(R.id.t2);
	openprice = (TextView) mHead.findViewById(R.id.t3);
	preprice = (TextView) mHead.findViewById(R.id.t4);
	high = (TextView) mHead.findViewById(R.id.t5);
	low = (TextView) mHead.findViewById(R.id.t6);
	strikeamount = (TextView) mHead.findViewById(R.id.t7);
	cjlTextView= (TextView) mHead.findViewById(R.id.t8);
	bearamount = (TextView) mHead.findViewById(R.id.t9);
	// stockcode = (TextView) mHead.findViewById(R.id.stockecode);
	name.setText("期货名称");
	recentprice.setText("最新价");
	openprice.setText("开盘价");
	preprice.setText("昨日收盘价");
	high.setText("最高价");
	low.setText("最低价");
	strikeamount.setText("成交量");
	cjlTextView.setText("成交额");
	cjlTextView.setVisibility(View.GONE);
	bearamount.setText("总持量");
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

/**
 * 退出系统弹出dialog
 */
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
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

@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	if (Cache.getCache("search")!=null) {
		
		searchString = Cache.getCache("search").toString();
		LogUtils.v("test", "searchString----"+searchString.toString());
		loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_INIT);
		//Cache.put("search", null);
	}
}

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	
};
}
