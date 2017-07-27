package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;









import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.AccountAdapter;
import com.kfd.api.AppContext;
import com.kfd.api.HttpRequest;
import com.kfd.bean.CustomInfoBean;
import com.kfd.bean.TotalfundsBean;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.market.FuturesMarketActivity;
import com.kfd.market.MarketCenterActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 账户中心
 * 
 * @
 */
public class AccountCenterActivity extends BaseActivity {
	HomeActivityGroup parentActivity1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountcenter);
		parentActivity1 = (HomeActivityGroup) getParent();
		initUI();
		initTitleButton();
		freshButton.setVisibility(View.GONE);
		initTitle();
		getCountData();
		FlurryAgent.onPageView();
		ActivityManager.push(this);
	}

	private ImageView backButton;
	private TextView titleTextView;
	private ProgressDialog progressDialog;

	private void initTitle() {

		 backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("我的账户");
		
		 backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.goBack();
			}
		});
		 
	}

	private TextView username,stock,futures;
	private LinearLayout infoLayout,safeLayout,accountmoneyLayout,centerLayout
			,detailLayout,stock_centerLayout,futures_centerLayout;
	
	private Button exitButton;
	
	private void initUI() {
		
		username = (TextView) findViewById(R.id.username);
		username.setText(SharePersistent.getInstance().getPerference(getApplicationContext(), "account").toString());
		stock = (TextView) findViewById(R.id.stock);
		futures = (TextView) findViewById(R.id.futures);
		exitButton = (Button) findViewById(R.id.exit);
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(getParent());
				builder.setMessage("是否退出账号？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						((AppContext) context.getApplicationContext())
						.clearAppCache();
						userLogout();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

				builder.create().show();
			}
		});
		
		infoLayout = (LinearLayout) findViewById(R.id.info);
		safeLayout = (LinearLayout) findViewById(R.id.safe);
		accountmoneyLayout = (LinearLayout) findViewById(R.id.accountmoney);
		centerLayout = (LinearLayout) findViewById(R.id.center);
		detailLayout = (LinearLayout) findViewById(R.id.detail);
		stock_centerLayout = (LinearLayout) findViewById(R.id.stock_center);
		futures_centerLayout = (LinearLayout) findViewById(R.id.futures_center);
		
		infoLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("CustomInfoActivity", new Intent(
						AccountCenterActivity.this, CustomInfoActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
		
		safeLayout.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("AccountSafeActivity", new Intent(
						AccountCenterActivity.this, AccountSafeActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
		accountmoneyLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("AccountFundActivity", new Intent(
						AccountCenterActivity.this, AccountFundActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
		
		centerLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("AccessCenterActivity", new Intent(
						AccountCenterActivity.this, AccessCenterActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
		
		detailLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("FinanceDetailActivity", new Intent(
						AccountCenterActivity.this, FinanceDetailActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
		
		stock_centerLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("TradeCenterActivity", new Intent(
						AccountCenterActivity.this, TradeCenterActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
		
		futures_centerLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.startChildActivity("FuturesCenterActivity", new Intent(
						AccountCenterActivity.this, FuturesCenterActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
	}
	
        public static void setListViewHeightBasedOnChildren(ListView listView) {
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
        }
	
        
    	private ExecutorService executorService = Executors.newFixedThreadPool(5);

    	private void getData() {
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
    				/*String userid = SharePersistent.getInstance()
    						.getUserInfo(getApplicationContext()).getUserid();*/
    				//if (userid != null && userid.length() > 0) {
    					Map<String, String> hashMap = new HashMap<String, String>();
    					hashMap.put("request", "userBasic");
    					hashMap.put("appid", getDeviceId());
    					hashMap.put("from", "2"); hashMap.put("mark", getMark());
    					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
    					try {
    						String result = HttpRequest.sendPostRequest(
    								ConstantInfo.parenturl, hashMap, "UTF-8");

    						CustomInfoBean bean = CustomInfoBean.parseData(result);
    						if (bean != null) {
    							Message message = new Message();
    							message.what = 1;
    							message.obj = bean;
    							updateUIHandler.sendMessage(message);
    						} else {
    							updateUIHandler.sendEmptyMessage(0);
    						}
    						LogUtils.log("test", "返回数据" + result);
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}

    				}
    			//}
    		});
    	}
    	
    	private void getCountData() {
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
    				/*String userid = SharePersistent.getInstance()
    						.getUserInfo(getApplicationContext()).getUserid();*/
    				//if (userid != null && userid.length() > 0) {
    					Map<String, String> hashMap = new HashMap<String, String>();
    					hashMap.put("request", "accountTotalfunds");
    					hashMap.put("appid", getDeviceId());
    					hashMap.put("from", "2"); hashMap.put("mark", getMark());
    					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
    					String result=null;
    					try {
    						result = HttpRequest.sendPostRequest(
    								ConstantInfo.parenturl, hashMap, "UTF-8");

    						TotalfundsBean bean = TotalfundsBean.parseData(result);
    						if (bean != null) {
    							Message message = new Message();
    							message.what = 2;
    							message.obj = bean;
    							updateUIHandler.sendMessage(message);
    						} else {
    							updateUIHandler.sendEmptyMessage(0);
    						}
    						LogUtils.log("test", "返回数据" + result);
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						Message message = new Message();
							message.what = 1;
							message.obj = result;
							updateUIHandler.sendMessage(message);
    						e.printStackTrace();
    					}

    				}
    			//}
    		});
    	}
    	
    	/*
    	 * 安全退出
    	 */
    	
    	private void userLogout() {
    		try {
    			progressDialog = new ProgressDialog(getParent());
    			progressDialog.setMessage("正在退出，请稍候...");
    			
    			progressDialog.show();
    		} catch (Exception e) {
    			// TODO: handle exception
    			e.printStackTrace();
    		}
    		
    		executorService.execute(new Runnable() {
    			@Override
    			public void run() {
    				/*String userid = SharePersistent.getInstance()
    						.getUserInfo(getApplicationContext()).getUserid();*/
    				//if (userid != null && userid.length() > 0) {
    					Map<String, String> hashMap = new HashMap<String, String>();
    					hashMap.put("request", "userLogout");
    					hashMap.put("appid", getDeviceId());
    					hashMap.put("from", "2"); hashMap.put("mark", getMark());
    					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
    					try {
    						String result = HttpRequest.sendPostRequest(
    								ConstantInfo.parenturl, hashMap, "UTF-8");


    						if (result != null && result.length() > 0) {
    							Message message = new Message();
    							message.what = 3;
    							message.obj = result;
    							updateUIHandler.sendMessage(message);
    						} else {
    							updateUIHandler.sendEmptyMessage(0);
    						}
    						LogUtils.log("test", "返回数据" + result);
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}

    				}
    			//}
    		});
    	}

    	/**
    	 * ui数据匹配
    	 */
    	private Handler updateUIHandler = new Handler() {
    		public void handleMessage(android.os.Message msg) {
    			progressDialog.dismiss();
    			switch (msg.what) {
    			case 0:
    				showToast("加载超时，请重试...");
    				break;
    			case 1:
    				//CustomInfoBean bean = (CustomInfoBean) msg.obj;
    				//bean.toString();
    				//username.setText(bean.getUserName());
    				
    				//getCountData();
    				try {
						
    				String string1 = (String) msg.obj;
    				JSONObject jsonObject1 = JSON.parseObject(string1);
    				String mesString = jsonObject1.getString("message");
    				showToast(mesString);
    				} catch (Exception e) {
    					// TODO: handle exception
    					e.printStackTrace();
    				}
    				break;
    				
    			case 2:
    				TotalfundsBean totalfundsbean = (TotalfundsBean) msg.obj;
    				totalfundsbean.toString();
    				stock.setText(totalfundsbean.getSaccount_money());
    				futures.setText(totalfundsbean.getFaccount_money());
    				break;
    				
    			case 3:
    				String string = (String) msg.obj;
    				try {
	    				JSONObject jsonObject = JSONObject.parseObject(string);
						String string3 = jsonObject.getString("message");
						String string4 = jsonObject.getString("status");
						showToast(string3);
						if (string4.equals("1")) {
							//finish();
							startActivity(new Intent(AccountCenterActivity.this,LoginActivity.class));		
							ActivityManager.popall();
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
}
