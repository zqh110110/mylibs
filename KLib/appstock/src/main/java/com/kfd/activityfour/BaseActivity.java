package com.kfd.activityfour;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.api.HttpRequest;
import com.kfd.bean.StockBean;
import com.kfd.common.AsyncImageLoader;
import com.kfd.common.AsyncImageLoaders;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.market.FuturesMarketActivity;
import com.kfd.market.MarketCenterActivity;
import com.kfd.ui.MyDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.analytics.MobclickAgent;



public  class BaseActivity extends FragmentActivity {
	protected Context context = null;
	public ImageLoader imageLoader = ImageLoader.getInstance();

	public static final int qqAuth = 1938;
	public static AsyncImageLoaders loaders = new AsyncImageLoaders();

	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory(false).cacheOnDisk(true).build();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		ActivityManager.push(this);
		//FlurryAgent.onPageView();
		//FlurryAgent.onStartSession(this, "Y2VB5XRBP7J68VCFKNQQ"); 
		MobclickAgent.updateOnlineConfig( this );
		imageLoader.init(ImageLoaderConfiguration.createDefault(BaseActivity.this));
	}
	
	public  void JumpLogin(){
		if (StringUtils.isEmpty(SharePersistent.getInstance().getPerference(getApplicationContext(), "islogin"))) {
			showToast("请先登陆!");
			startActivity(new Intent(getApplicationContext(), LoginActivity.class));
			finish();
			return;
		}
		
	}
	public String  getDeviceId(){
		  //TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);  
		 // String   deviceId =tm.getDeviceId();
		String   deviceId = ConstantInfo.appid;
		  return  deviceId;
	}
	
	public  String  getMark(){
		  TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);  
		  String   deviceId =tm.getDeviceId();
		  return  deviceId;
	}
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//FlurryAgent.onEndSession(this); 
		ActivityManager.pop();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public Button freshButton;
	public TextView titleTextView;
	public ImageView popButton,searchButton,backButton;

	public void initTitle(String title) {

	
		titleTextView = (TextView) findViewById(R.id.title_text);

		titleTextView.setText(title);
		backButton = (ImageView) findViewById(R.id.back);

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	
	public void initTitleButton(){
		backButton = (ImageView) findViewById(R.id.back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		freshButton  =(Button) findViewById(R.id.refresh);
		freshButton.setVisibility(View.VISIBLE);
		freshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onRefresh();
			}
		});
	}
	
	public   void onRefresh(){
		
	};

	/**
	 * 添加关注
	 * 
	 * @param stockBean
	 */
	public ExecutorService executorService = Executors.newFixedThreadPool(5);

	public void addStockToSelfBase(final StockBean stockBean) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				HashMap<String, String>  hashMap  = new HashMap<String, String>();
				hashMap.put("request", "stocksAttention");
				hashMap.put("from", "2"); hashMap.put("mark", getMark());
				hashMap.put("appid", getDeviceId());
			
				hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
				hashMap.put("stock_code", stockBean.getStockcode());
		
				try {
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.log("test", "返回数据---> " + result);
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

	public Handler updateUIHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				showToast("添加关注不成功");
				break;
			case 1:
				try {
					String string = (String) msg.obj;
					JSONObject jsonObject = JSONObject.parseObject(string);
					showToast(jsonObject.getString("message"));
				} catch (Exception e) {
					e.printStackTrace();
				}	
				break;
			default:
				break;
			}
		};
	};
	public static AsyncImageLoader asyncImageLoader;

	public static AsyncImageLoader getAsyncImageLoader(Context context) {
		if (asyncImageLoader == null) {
			asyncImageLoader = new AsyncImageLoader(context);
		}
		return asyncImageLoader;

	}
	public static AsyncImageLoader getAsyncImageLoaderInstance(Context context) {
		if (asyncImageLoader == null) {
			asyncImageLoader = new AsyncImageLoader(context);
		}
		return asyncImageLoader;
	}
	public void showOneStock() {

		final Dialog dialog = new MyDialog(context, R.style.MyDialog);
		dialog.show();

		/*
		 * LayoutInflater layoutInflater = getLayoutInflater(); LinearLayout
		 * layout = (LinearLayout) layoutInflater.inflate(R.layout.mydialog,
		 * null); final AlertDialog alertDialog = new
		 * AlertDialog.Builder(context).setView(layout).create();
		 */
		// 这种方法设置的dialog有边框
		final EditText editText = (EditText) dialog.findViewById(R.id.edtText);
		Button button = (Button) dialog.findViewById(R.id.button1);
		Button cancelbutton = (Button) dialog.findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean flage = false;
				String string = editText.getText().toString();
				if (editText.length() < 8 || editText.length() > 8) {
					flage = true;
				}
				String start = string.substring(0, 2);
				System.out.println(start);
				if (start.equals("sz") || start.equals("sh")) {
					flage = true;
				} else {
					flage = false;

				}
				if (flage) {

					// Intent intent = new Intent(context,
					// StockDetailActivity.class);
					// intent.putExtra("code", string);
					// startActivity(intent);
				} else {
					showToast("请输入正确格式/n例:sz000001");
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

	public void dialog(String title, String message, final int arg) {

		try {
			AlertDialog.Builder builder = new Builder(BaseActivity.this);
			builder.setTitle(title);
			builder.setMessage(message);
			builder.setIcon(R.drawable.miniicon);
			builder.setPositiveButton(getString(R.string.confirm),
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

						}
					});
			builder.setNegativeButton(getString(R.string.cancel),
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProgressDialog progressDialog;

	public void showDialog(String string) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(context);
		}
		progressDialog.setMessage(string);
		
		if(progressDialog != null && progressDialog.isShowing())
		{
			return;
		}
		progressDialog.show();
	}

	public void dismissDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	public void showToast(String text) {
		if (text!=null) {
			Toast.makeText(context, text, 1000).show();
		}
	}
	
	public static int segmentPosition;

	

	
	


	

	

	
	
	

}