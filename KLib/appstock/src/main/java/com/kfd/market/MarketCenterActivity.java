package com.kfd.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.ActivityManager;
import com.kfd.activityfour.BaseActivity;
import com.kfd.activityfour.HomeActivityGroup;
import com.kfd.activityfour.PlaceAnOrderActivity;
import com.kfd.activityfour.R;
import com.kfd.activityfour.SearchCodeActivity;
import com.kfd.activityfour.StockDetailActivity;
import com.kfd.adapter.RefreshListViewAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.StockBean;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.ui.MyDialog;
import com.kfd.ui.PullToRefreshListView;

/**
 * 行情中心
 * 
 * @author 
 */
public class MarketCenterActivity extends BaseActivity implements
		OnClickListener { 
	private String searchString;
	HomeActivityGroup parentActivity1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marketcenter);
		parentActivity1 = (HomeActivityGroup) getParent();
		initTitle();
		initUI();
		initListView();
		initPopupButton();
		initData();
		gestureDetector = new GestureDetector(MarketCenterActivity.this,onGestureListener);  
		FlurryAgent.onPageView();
		//initBottom();
		//getTypeData();
	}

	private void initBottom() {
		Button button1, button2, button3, button4;
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		Button button5 = (Button) findViewById(R.id.button5);

		button1.setText("市场");
		button2.setText("行业");
		button3.setText("刷新");
		button4.setText("个股");
		button5.setText("返回");
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);

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
		pullToRefreshListView.addFooterView(listView_footer);// 添加底部视图
																// 必须在setAdapter前
		pullToRefreshListView.setAdapter(adapter);
		
		
		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// 点击头部、底部栏无效
				if (arg2 == 0 || arg1 == listView_footer)
					return;
				final StockBean bean = data.get(arg2 - 1);
				if (bean == null) {
					return;
				}
				
				//手势识别，左右滑动时禁止弹出交易对话框，防止误操作。
				if (flagBoolean) {
					return;
				}
				
				String[] items = { "关注", "K线图", "交易" };
				//String[] items = { "关注",  "交易" };
				AlertDialog dialog = new AlertDialog.Builder(
						getParent()).setItems(items,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								switch (which) {
								case 0:
									addStockToSelfBase(bean);

									break;
								case 1:
									// 跳转到股票详情页面 ,同时将改stock的所有信息传递过去，以及整个list列表
									/*Intent intent = new Intent(
											getApplicationContext(),
											StockDetailActivity.class);
									intent.putExtra("stockbean", bean);
									intent.putExtra("list", data);
									LogUtils.log("test",
											"list size" + data.size());
									intent.putExtra("postion", arg2);
									startActivity(intent);*/
				    				parentActivity1.startChildActivity("StockDetailActivity", new Intent(
				    						MarketCenterActivity.this, StockDetailActivity.class)
				    						.putExtra("stockbean", bean)
				    						.putExtra("list", data)
				    						.putExtra("postion", arg2)
				    						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
									
									break;
								case 2:
									try {
										double  volume  =  Double.parseDouble(bean.getVolume());
								
										if (volume>0) {
						    				parentActivity1.startChildActivity("PlaceAnOrderActivity", new Intent(
						    						MarketCenterActivity.this, PlaceAnOrderActivity.class)
						    						.putExtra("bean", bean)
						    						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
										}else {
											showToast("股票停盘无法交易");
										}
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									
								
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
							
							//第一页小于10的时候
							if (sumdata<10) {
								sumdata = 10;
							}
							
							// 当前pageIndex
							 pageIndex = sumdata / ConstantInfo.PAGE_SIZE
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
	private  int pageIndex;
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

					if (msg.what<1) { // 如果list元素的个数小于pagesize则全部加载，more显示加载全部，否则显示加载更多
						lv.setTag(ConstantInfo.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);
						if (msg.what==0) {
							showToast(msgString);
						}
					} else{
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
			ListBean nlist1 = (ListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist1.getList());
			}

			break;
		case ConstantInfo.LISTVIEW_ACTION_INIT:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			ListBean nlist = (ListBean) obj;
			if (obj != null) {
				sumdata = what;
				data.clear();// 先清除原有数据
				data.addAll(nlist.getList());
			}
			break;
		case ConstantInfo.LISTVIEW_ACTION_SCROLL:
			ListBean list = (ListBean) obj;
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

	private boolean issingle = false;
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
		new Thread() {
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if (action == ConstantInfo.LISTVIEW_ACTION_REFRESH
						|| action == ConstantInfo.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					HashMap<String, String>  hashMap  = new HashMap<String, String>();
					hashMap.put("request", "stocksCodes");
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					
					hashMap.put("detail", "1");
					int  start   = (pageIndex-1)*ConstantInfo.PAGE_SIZE;
					hashMap.put("limit",String.valueOf(start));
					hashMap.put("offset", String.valueOf(ConstantInfo.PAGE_SIZE));
					
					if (searchString!=null) {
						hashMap.put("codes",searchString);
					}
					searchString=null;
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
			
					String result = null;
					// 市场分类行业分类
					if (stockSort != null && !stockSort.toString().equals("0")) {
						LogUtils.log("test", "stockSort " + stockSort);
						hashMap.put("inid", stockSort);
					}
					result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
					
					
					LogUtils.log("test", "result " + result);
					JSONObject  jsonObject  = JSONObject.parseObject(result);
					msgString =  jsonObject.getString("message");

					if (result == null || result.length() < 1) {
						msg.what = 0;
					} else {
						ListBean listBean = null;
						// 数据解析
						if (issingle && stockON != null) {

//							listBean = ListBean
//									.parseSingleData(result, stockON);
							listBean =ListBean.parseSingleData(result);
						} else {// 如果不是按照股票代码查找的
							listBean = ListBean.parseData(result);
						}

						msg.what = (int) listBean.getList().size();
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
		titleTextView.setText("行情中心");
		
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
				Cache.put("type", "stock");
				parentActivity1.startChildActivity("SearchCodeActivity", new Intent(
						MarketCenterActivity.this, SearchCodeActivity.class)
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

				} else if(arg2==1){
					parentActivity1.startChildActivity("FuturesMarketActivity", new Intent(
							MarketCenterActivity.this, FuturesMarketActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				}else {
					
				}
				
				
				popupWindow.dismiss();
				popupWindow = null;
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


	private String stockType;
	private String todo;
	private String stockSort;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			AlertDialog aDialog = new AlertDialog.Builder(
					getParent()).setTitle("市场")
					.setItems(maintype, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							stockType = String.valueOf(which);
							stockSort = null;
							todo = "1";
							loadData(1, listviewHandler,
									ConstantInfo.LISTVIEW_ACTION_REFRESH);
						}
					}).create();
			aDialog.show();
			// 调整dialog尺寸大小
			WindowManager.LayoutParams params = aDialog.getWindow()
					.getAttributes();
			params.width = 460;
			params.height = 560;
			// params.x =-20;
			params.y = +30;
			aDialog.getWindow().setAttributes(params);
			break;

		case R.id.button2:
			if (list.size() > 0) {
				String[] stockSorttype = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					stockSorttype[i] = list.get(i).getName();
				}

				AlertDialog alertDialog = new AlertDialog.Builder(
						getParent())
						.setTitle("行业")
						.setItems(stockSorttype,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										stockSort = String.valueOf(which);
										loadData(
												1,
												listviewHandler,
												ConstantInfo.LISTVIEW_ACTION_REFRESH);
									}
								}).create();
				alertDialog.show();
				WindowManager.LayoutParams params1 = alertDialog.getWindow()
						.getAttributes();
				params1.width = 460;
				params1.height = 560;
				alertDialog.getWindow().setAttributes(params1);
			}
			break;
		case R.id.button3:// 刷新
			data.clear();
			getTypeData();// 重新获取一下市场
			loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_REFRESH);
			break;
		case R.id.button4:
			showDialog();
			break;
		case R.id.button5:
			finish();
			break;

		default:
			break;
		}
	}

	private String stockON;// 单个查询时候的股票代码

	/**
	 * 用户索定股票编码进行查看
	 */
	public void showDialog() {

		final Dialog dialog = new MyDialog(getParent(), R.style.MyDialog);
		dialog.show();
		// 这种方法设置的dialog有边框
		final EditText editText = (EditText) dialog.findViewById(R.id.edtText);
		Button button = (Button) dialog.findViewById(R.id.button1);
		Button cancelbutton = (Button) dialog.findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean flage = false;
				stockON = editText.getText().toString();
				if (stockON != null && stockON.length() > 0) {
					if (editText.length() < 8 || editText.length() > 8) {
						flage = true;
					}
					String start = stockON.substring(0, 2);

//					if (start.equals("sz") || start.equals("sh")) {
//						flage = true;
//					} else {
//						flage = false;
//
//					}
					if (flage) {
						todo = "3";
						// 调用查询方法
						loadData(1, listviewHandler,
								ConstantInfo.LISTVIEW_ACTION_REFRESH);
						dialog.dismiss();
					} else {
						showToast("请输入正确格式,例:000001");
					}
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

	/**
	 * 获取数据解析类
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void getTypeData() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
			//	if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "stocksInid");
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
		
					try {
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
						LogUtils.log("test", "返回数据###" + result);
						if (result != null) {
							//手动添加全部
							IndustryBean industryBean0 = new IndustryBean();
							industryBean0.setId("0");
							industryBean0.setName("全部");
							list.add(industryBean0);
							JSONObject jsonObject = JSONObject.parseObject(result);
							JSONArray array = jsonObject.getJSONArray("data");

							for (int i = 0; i < array.size(); i++) {
								JSONObject jsonObject2 = array.getJSONObject(i);
								IndustryBean industryBean = new IndustryBean();
								industryBean.setId(jsonObject2.getString("inid"));
								industryBean.setName(jsonObject2.getString("title"));
								list.add(industryBean);

							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			
			//}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//String string =	(String) Cache.getCache("");
		
		if (Cache.getCache("search")!=null) {
			
			searchString = Cache.getCache("search").toString();
			LogUtils.v("sssss", "searchString----"+searchString.toString());
			loadData(1, listviewHandler, ConstantInfo.LISTVIEW_ACTION_INIT);
			Cache.put("search", null);
		}
	}

	private String[] maintype;

	private ArrayList<IndustryBean> list = new ArrayList<IndustryBean>();
	
	
	public static class GetCategoryBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			 type = bundle.getString("type");
		}

	}
	static String type;//根据此type去获取数据
	
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
				//MarketCenterActivity.this.finish();
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
