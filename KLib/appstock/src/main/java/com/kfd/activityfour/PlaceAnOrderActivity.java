package com.kfd.activityfour;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.R.integer;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.bean.StockBean;
import com.kfd.bean.StockInfo;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.market.FuturesMarketActivity;
import com.kfd.market.ListBean;
import com.kfd.market.MarketCenterActivity;

/**
 * 股票下单窗口
 * 
 * @author 2013-05-30
 */

public class PlaceAnOrderActivity extends BaseActivity implements
		OnClickListener {
	private StockBean stockBean;
	private String code,from="-1";
	private String buytype="1";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy);
		initTitle();
		FlurryAgent.onPageView();
		
			if (getIntent().getSerializableExtra("bean") != null) {
				stockBean = (StockBean) getIntent().getSerializableExtra("bean");
				stockBean.toString();
			}
			if (getIntent().getStringExtra("code") != null) {
				code = getIntent().getStringExtra("code");
			}
			if (getIntent().getStringExtra("from") != null) {
				from = getIntent().getStringExtra("from");
			}
		
		
		initTitleButton();
		initUI();
		if (from.toString().equals("-1")) {
			initData();		
		}
		
		//judgeActive();
		initTradePopupButton();

	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		if (from.toString().equals("-1")) {
			initData();		
		}
	
	}
	//首先判断用户有没有激活如果没有激活就弹出对话框
	private void  judgeActive(){
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// http://mm.ber9999.com/?c=&a=&code=sz000006
				try {
					/*String userid = SharePersistent.getInstance()
							.getUserInfo(getApplicationContext()).getUserid();*/
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("c", "user");
					hashMap.put("a", "getDataAndroid");
				

					hashMap.put("mobile", "android");
					//hashMap.put("uid", userid);
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					//Log.v("test", "!!!   "+result);
					if (result!=null && result.length()>0) {
						Message  message   = new Message();
						message.what=1;
						message.obj= result;	 
						showDialogHandler.sendMessage(message);
					}else {
						//正常情况不做处理
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}
	private Handler  showDialogHandler  = new  Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
		
			case 1:
				String   result = (String) msg.obj;
				Log.v("test", result);
				try {
					JSONObject jsonObject  = JSONObject.parseObject(result);
					if (jsonObject.getString("message")!=null) {
						AlertDialog  alertDialog  =  new AlertDialog.Builder(PlaceAnOrderActivity.this)
						.setMessage(jsonObject.getString("message")).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								showToast("不激活信息无法交易");
								finish();
							}
						}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								//将stockcode传递过去，
								Intent intent  =new Intent(getApplicationContext(), ActiveAccountActivity.class);
								intent.putExtra("bean", stockBean);
								startActivity(intent);
								finish();
							}
						}).create();
						alertDialog.show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
			default:
				break;
			}
		}
	};
	

	/**
	 * 获取数据
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void initData() {
		try {
			progressDialog = new ProgressDialog(getParent());
			progressDialog.setMessage("加载数据中，请稍候.....");
			
			progressDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				try {	
					HashMap<String, String>  hashMap  = new HashMap<String, String>();
					hashMap.put("request", "stocksSinglereal");
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					hashMap.put("type", buytype);
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));

						if (stockBean == null) {
							hashMap.put("stock_code", code);
						} else {
							hashMap.put("stock_code", stockBean.getStockcode());
						}
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.log("test", result);
						JSONObject jsonObject  =  JSONObject.parseObject(result);
						messageinfo  = jsonObject.getString("message");
						LogUtils.log("test", messageinfo);
						 
				       JSONObject  listBean = jsonObject.getJSONObject("data");
						if (result != null && result.length() > 1) {
							Message message = new Message();
							message.what = 1;
							message.obj = listBean;
							updateUIHandler.sendMessage(message);
						} else {
							updateUIHandler.sendEmptyMessage(0);
						}
				
				
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	private TextView stockcodeTextView, availableMoneyTextView,canbuytTextView,
			stockepriceTextView, amounTextView, stocknameTextView,
			setupcostTextView, hotcostTextView, gzfcostTextView;
	private RadioButton timeButton, entrustButton,buyhighButtonRadio,buylowButtonRadio;
	private EditText countEditText, setuppriceEditText, lowpricEditText,
			highEditText,TradeText;
	private View relativeLayout1 ,linearLayoutdown,linearLayout15,linearLayout25,linearLayout35;
	private Button buyhigh, buylowButton;
	private RadioGroup radioGroup,radioGrouphigh_low;
	private TextView  logoTextView,ckdsTextView,jcTextView,jcdwTextView;
	private String  open_cost,hot_cost,gzf_cost,nowprice;
	
	private void initUI() {
		logoTextView =  (TextView) findViewById(R.id.textView1);
		ckdsTextView  = (TextView) findViewById(R.id.textView5);
		jcTextView  = (TextView) findViewById(R.id.textView10);
		jcdwTextView  =(TextView) findViewById(R.id.textView11);
		
		stockcodeTextView = (TextView) findViewById(R.id.textView2);
		availableMoneyTextView = (TextView) findViewById(R.id.textView3);
		stockepriceTextView = (TextView) findViewById(R.id.textView6);
		amounTextView = (TextView) findViewById(R.id.textView8);
		canbuytTextView = (TextView) findViewById(R.id.canbuy);
		timeButton = (RadioButton) findViewById(R.id.radio0);
		entrustButton = (RadioButton) findViewById(R.id.radio1);
		
		buyhighButtonRadio = (RadioButton) findViewById(R.id.buyhigh);
		buylowButtonRadio = (RadioButton) findViewById(R.id.buylow);

		countEditText = (EditText) findViewById(R.id.editText1);
		
		lowpricEditText = (EditText) findViewById(R.id.editText3);
		highEditText = (EditText) findViewById(R.id.editText4);
		stocknameTextView = (TextView) findViewById(R.id.textView15);
		TradeText = (EditText) findViewById(R.id.tradeText);
		
		setupcostTextView = (TextView) findViewById(R.id.editText38);
		hotcostTextView = (TextView) findViewById(R.id.editText18);
		gzfcostTextView = (TextView) findViewById(R.id.editText28);

		buyhigh = (Button) findViewById(R.id.button1);
		buyhigh.setVisibility(View.VISIBLE);
		buylowButton = (Button) findViewById(R.id.button2);
		//设置两个flage，如果没有建仓建仓成功是不能点击第二次的
		
		relativeLayout1 = findViewById(R.id.relativeLayout1); 
		linearLayoutdown = findViewById(R.id.linearLayoutdown);
		
		linearLayout35 = findViewById(R.id.linearLayout35);
		linearLayout25 = findViewById(R.id.linearLayout25);
		linearLayout15 = findViewById(R.id.linearLayout15);
		
		if (from.toString().equals("0")) {
			relativeLayout1.setVisibility(View.GONE);
			TradeText.setVisibility(View.VISIBLE);
			linearLayoutdown.setVisibility(View.GONE);		
			freshButton.setVisibility(View.GONE);
			linearLayout35.setVisibility(View.GONE);
			linearLayout25.setVisibility(View.GONE);
			linearLayout15.setVisibility(View.GONE);
		} else {

		}
		
		buyhigh.setOnClickListener(this);
		buylowButton.setOnClickListener(this);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == timeButton.getId()) {

					dealtype = "0";
				} else {

					dealtype = "1";
				}
			}
		});
		
		radioGrouphigh_low = (RadioGroup) findViewById(R.id.radioGroup2);
		radioGrouphigh_low.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == buyhighButtonRadio.getId()) {
					buyhigh.setVisibility(View.VISIBLE);
					buylowButton.setVisibility(View.GONE);
					buytype = "1";
					initData();
					countEditText.setText("");
				}else {
					buytype = "0";
					buyhigh.setVisibility(View.GONE);
					buylowButton.setVisibility(View.VISIBLE);
					initData();
					countEditText.setText("");
				}
			}
		});
		
		countEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String countString = countEditText.getText().toString().trim();
				if (nowprice!=null&&open_cost!=null&&hot_cost!=null&&gzf_cost!=null) {
					if (countString!=null&&countString.length()>0
							&& nowprice.length()>0
							&& open_cost.length()>0
							&& hot_cost.length()>0
							&& gzf_cost.length()>0
							) {
						float f1 = Float.parseFloat(countString);
						float f2 = Float.parseFloat(nowprice);
						
						float open = Float.parseFloat(open_cost);
						float hot = Float.parseFloat(hot_cost);
						float gzf = Float.parseFloat(gzf_cost);
						
						setupcostTextView.setText((f1*f2*open)+"元");
						hotcostTextView.setText((f1*f2*hot)+"元");
						gzfcostTextView.setText((f1*f2*gzf)+"元");
					}else {
						setupcostTextView.setText("0.0元");
						hotcostTextView.setText("0.0元");
						gzfcostTextView.setText("0.0元");
					}
					
				}
				

			}
		});

	}

	

	private ImageView backButton;
	private TextView titleTextView;
	public ImageView popButton;
	private PopupWindow popupWindow;
	private LinearLayout layout;
	private ListView listView;
	private String title[] = { "沪深A股", "沪深300"};

	private void initTitle() {
		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("A股行情");	
	}
	
	private void initTradePopupButton() {
		backButton = (ImageView) findViewById(R.id.back);
		popButton = (ImageView) findViewById(R.id.popimage);
		if (from.toString().equals("0")) {
			backButton.setVisibility(View.GONE);
			popButton.setVisibility(View.VISIBLE);
		}
		titleTextView = (TextView) findViewById(R.id.title_text);
		final View line = findViewById(R.id.titleline);
		
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		popButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int y = line.getBottom()*3/2;
				int x = getWindowManager().getDefaultDisplay().getWidth() / 4;

				showTradePopupWindow(x, y);
			}
		});
		if (from.toString().equals("0")) {
			titleTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int y = line.getBottom()*3/2;
					int x = getWindowManager().getDefaultDisplay().getWidth() / 4;
	
					showTradePopupWindow(x, y);
				}
			});
		}
	}
	
	public void showTradePopupWindow(int x, int y) {
		layout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.market_popupwindow, null);
		listView = (ListView) layout.findViewById(R.id.lv_dialog);
		listView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.text, R.id.tv_text, title));

		popupWindow = new PopupWindow(layout,
				getWindowManager().getDefaultDisplay().getWidth() / 2,
				WindowManager.LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		//popupWindow.setWidth(getWindowManager().getDefaultDisplay().getWidth() / 2);
		
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		//popupWindow.setContentView(layout);
		popupWindow.showAtLocation(findViewById(R.id.title_text), Gravity.LEFT
				| Gravity.TOP, x, y);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//titleTextView.setText(title[arg2]);
				if (arg2==0) {
					
					
				} else if(arg2==1){
					HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
					parentActivity1.startChildActivity("FuturesAnOrderActivity", new Intent(
							PlaceAnOrderActivity.this, FuturesAnOrderActivity.class)
							.putExtra("from", "0")
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
					
				}
				
				
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
	}

	private String buycount, setupprice, lowprice, highprice,stockcode;
	private String dealtype = "0";

	private boolean  buyflage= true;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		buycount = countEditText.getText().toString().trim();
		lowprice = lowpricEditText.getText().toString();
		highprice = highEditText.getText().toString().trim();
		stockcode = TradeText.getText().toString().trim();
		
		switch (v.getId()) {
		case R.id.button1:
			if (!from.toString().equals("-1")) {
				if (stockcode.length()==6||stockcode.length()==8) {
					
					
					
				}else {
					if (stockcode.length()>0) {
						showToast("股票代码错误");
						return;
					}else {
						showToast("请输入股票代码");
						return;
					}
				}
			}
			
				if (buycount!=null && buycount.length()>0) {
					if (buyflage) {
						buyflage  = false;
						committype="1";
						try {
							progressDialog = new ProgressDialog(getParent());
							progressDialog.setMessage("请稍候.....");
							
							progressDialog.show();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

						
							//commitData("0");
							judge("1");
					
					}else {
					}
				}else {
					showToast("请输入建仓股数");
				}
					
			
			break;
		case R.id.button2:

			if (!from.toString().equals("-1")) {
				if (stockcode.length()==6||stockcode.length()==8) {
					
					
					
				}else {
					if (stockcode.length()>0) {
						showToast("股票代码错误");
						return;
					}else {
						showToast("请输入股票代码");
						return;
					}
				}
			}
			
				if (buycount!=null && buycount.length()>0) {
					if (buyflage) {
						buyflage  = false;
						committype="0";
						try {
							progressDialog = new ProgressDialog(getParent());
							progressDialog.setMessage("请稍候.....");
							
							progressDialog.show();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					
							//commitData("1");
							judge("0");
						
					}else {
					}
				}else {
					showToast("请输入建仓股数");
				}
			break;

		default:
			break;
		}
	}
	
	private void buyFutures(final String type) {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("c", "handle");
					hashMap.put("a", "futuresDeal");

					String url = ApiClient.makeUrl(ConstantInfo.parenturl,
							hashMap);
					LogUtils.log("test", "url   " + url);
					Map<String, String> hashMap1 = new HashMap<String, String>();
					//hashMap1.put("uid", userid);
					hashMap1.put("deal[code]", stockcodeTextView.getText()
							.toString().trim());
					hashMap1.put("deal[dealtype]", dealtype);
					hashMap1.put("deal[dealnum]", buycount);
					LogUtils.log("test", "subtype    " + type);
					LogUtils.log("test", "dealtype    " + dealtype);
					hashMap1.put("deal[low_price]", lowprice);
					hashMap1.put("deal[high_price]", highprice);
					hashMap1.put("mobile", "android");
					hashMap1.put("deal[subtype]", type);
					//hashMap1.put("codename", futuresBean2.getCodename());
					try {
						String result = HttpRequest.sendHttpClientPost(url,
								hashMap1, "UTF-8").trim();
						LogUtils.log("test", result);
						if (result != null && result.length() > 0) {
							Message message = new Message();
							message.what = 2;
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
			//}
		});
	}
	
	
	
	
	private String committype;
	
	private void judge(final String type){
		executorService.execute(new Runnable() {
		
				public void run() {
				//	if (type.equals("0")) {
						//c=Stockhandle&a=stockDealData&code=sz000001&type=0&uid=87",
						
						
					/*	String userid = SharePersistent.getInstance()
								.getUserInfo(getApplicationContext()).getUserid();*/
						//if (userid != null && userid.length() > 0) {
							Map<String, String> hashMap = new HashMap<String, String>();
							
							if (String.valueOf(dealtype)=="0") {
								hashMap.put("request", "stocksCheckpt");
								LogUtils.v("test", "实时建仓");
							} else {
								LogUtils.v("test", "委托建仓");
								hashMap.put("request", "stocksCheckatt");
							}
							//hashMap.put("a", "stockDealData");
							//hashMap.put("uid", userid);
							hashMap.put("from", "2"); hashMap.put("mark", getMark());
							hashMap.put("appid", getDeviceId());
							hashMap.put("type", type);
							hashMap.put("num", buycount);
							if (from.toString().equals("0")) {
								LogUtils.v("test", "实时建仓"+stockcode);
								hashMap.put("stock_code", stockcode);
							} else {
								hashMap.put("stock_code", stockcodeTextView.getText()
										.toString().trim());
							}
							hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
							
							Log.v("test", "1");
							try {
								String result = HttpRequest.sendPostRequest(ConstantInfo.parenturl, hashMap, "utf-8");
								LogUtils.log("test", result);
								LogUtils.log("test", "aaaa   "+result);
								if (result!=null) {
									Message    message = new Message();
									message.what=1;
									message.obj=result;
									
									judgetHandler.sendMessage(message);
								}else {
									judgetHandler.sendEmptyMessage(0);
								}
//								if (result != null && result.length() > 0) {
//									Message message = new Message();
//									message.what = 2;
//									message.obj = result;
//									updateUIHandler.sendMessage(message);
//								} else {
//									updateUIHandler.sendEmptyMessage(0);
//								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					//}
				//	}
				});
	}
	private  Handler  judgetHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				commitData(committype);
				break;
			case 1:
				String resulString = (String) msg.obj;
			
			
			 
				LogUtils.v("test", "re "+resulString);
			
				try {
				/*	LogUtils.v("test", resulString.charAt(1));
					LogUtils.v("test", resulString.charAt(3));
					int  re1  =  Integer.parseInt(resulString);*/
//					int  re1  =  Integer.parseInt(String.valueOf(resulString.charAt(1)));
//					int re2  = Integer.parseInt(String.valueOf(resulString.charAt(3)));
				//	LogUtils.v("test", "re1"+re1);
				//	LogUtils.v("test", "re2"+re2);
					//int   res = Integer.valueOf(resulString.trim());
					//LogUtils.v("test", "res shu "+res);
					//Log.v("test", messageinfo);
					if ((resulString.toString().trim().equals("1"))&& messageinfo!=null && messageinfo.length()>0) {
						AlertDialog   dialog  =  new AlertDialog.Builder(PlaceAnOrderActivity.this)
						.setMessage(messageinfo).setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
								//commitData(committype);
							}
						})/*.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								//finish();
							}
						})*/.create();
						dialog.show();
						
					}else{
						Log.v("test","@@");
						commitData(committype);
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
	
	
	

	//提交数据的时候倒计时15秒添加
	private void showWaitFiveTeenTime(final String type){
		//首先去掉当前的dialog
		dismissDialog();
		count=15;
		mytype  = type;
		 timer  =  new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Message  message  = new Message();
				message.what=count--;
				message.obj = mytype;
				LogUtils.log("test", count);
				
				handler.sendMessage(message);
			}
		}, 0,1000);
		
	}
	Timer  timer;
	private String mytype;
	TimerTask mytask  = new TimerTask() {
		
		@Override
		public void run() {
			Message  message  = new Message();
			message.what=count--;
			message.obj = mytype;
			LogUtils.log("test", count);
			
			handler.sendMessage(message);
		}
	};
	
	private int  count=15;
	private Handler handler =  new  Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (msg.what>0) {
				isshowDialog= true;
				dismissDialog();
				try {
					showDialog("你好，你的订单已进入处理阶段，请稍候...."+"\r\n"+"页面将在("+msg.what+")s后进行跳转....");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}else {
				dismissDialog();
				isshowDialog = false;
				timer.cancel();
				 type=  (String) msg.obj;
				executBuy(type);
			}
		}
	};
	private boolean  isshowDialog=false;
	public boolean onKeyDown(int keyCode, KeyEvent event) {

        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
        	if (isshowDialog) {
        		return true;
			}else {
				if (!from.toString().equals("0")) {
					finish();			
				}
			}
        }
        if (Cache.getCache("fromR")!=null) {
			finish();
			return true;
		}
        
        if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog();
			return true;
		}

        return false;

	}

	String type;
	//提交数据的时候倒计时15秒添加
	private void commitData(final String type) {
		showWaitFiveTeenTime(type);
		//executBuy(type);
	}
	private void executBuy(final String type){
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					if (String.valueOf(dealtype)=="0") {
						hashMap.put("request", "tradePosition");
						LogUtils.v("test", "实时建仓");
					}else {
						hashMap.put("request", "tradeAddtrust");
					}
					

					String url = ApiClient.makeUrl(ConstantInfo.parenturl,
							hashMap);
					LogUtils.log("test", "url   " + url);
					Map<String, String> hashMap1 = new HashMap<String, String>();
					//hashMap1.put("uid", userid);
					hashMap1.put("request", "stocksPosition");
					
					if (from.toString().equals("0")) {
						hashMap1.put("stock_code", stockcode);
					} else {
						hashMap1.put("stock_code", stockcodeTextView.getText()
								.toString().trim());
					}
					
					hashMap1.put("deal[dealtype]", dealtype);
					hashMap1.put("num", buycount);
					LogUtils.log("test", "subtype    " + type);
					LogUtils.log("test", "dealtype    " + dealtype);
					hashMap1.put("low_price", lowprice);
					hashMap1.put("high_price", highprice);
					hashMap1.put("from", "2");
					hashMap1.put("mark", getMark());
					hashMap1.put("appid", getDeviceId());
					hashMap1.put("type", type);
					hashMap1.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					
				
				
					
					try {
						//String result = JsonParseTool.sendHttpClientPost(url,
						//		hashMap1, "UTF-8").trim();
						String result = HttpRequest.sendPostRequest(ConstantInfo.parenturl, hashMap1, "utf-8");
						
						LogUtils.log("test", result);
						if (result != null && result.length() > 0) {
							Message message = new Message();
							message.what = 2;
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
			//}
		});
	}
	
private String messageinfo;
	private StockInfo stockInfo;
	
	private Handler updateUIHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			dismissDialog();
			//buyflage  = true;
			switch (msg.what) {

			case 0:
				buyflage  = true;
				Toast.makeText(getApplicationContext(), "数据获取错误,请重试", 1000)
						.show();
				break;
			case 1:
				JSONObject listBean  = (JSONObject) msg.obj;
					//stockInfo = (StockInfo) 
					if (listBean!=null) {
						stockcodeTextView.setText(stockBean.getStockcode());
						stockepriceTextView.setText(listBean.getString("nowprice"));
						stocknameTextView.setText(stockBean.getStockname());
						availableMoneyTextView.setText(listBean.getString("canfunds"));
						amounTextView.setText(listBean.getString("maxnum"));
						canbuytTextView.setText(listBean.getString("maxnum"));
						open_cost = listBean.getString("open_cost");
						hot_cost = listBean.getString("hot_cost");
						gzf_cost = listBean.getString("gzf_cost");
						nowprice = listBean.getString("nowprice");
						//hotcostTextView.setText(stockInfo.getHotCommission());
						//fcostTextView.setText(stockInfo.getHightCommission());					
					}
			
				break;
			case 2:
				String string = (String) msg.obj;
				
				try {
					JSONObject jsonObject = JSONObject.parseObject(string);
					String string3 = jsonObject.getString("message");
					String string4 = jsonObject.getString("status");
					showToast(string3);
					buyflage  = true;
				} catch (Exception e) {
					showToast("提交订单失败，请重试...");
				
					e.printStackTrace();
				}

				break;
			
				
			default:
				break;
			}
		};
	};
	
	/**
	 * 退出系统弹出dialog
	 */
	/*@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}*/
	
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
