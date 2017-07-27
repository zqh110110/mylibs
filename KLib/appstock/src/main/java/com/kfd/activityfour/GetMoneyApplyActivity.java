package com.kfd.activityfour;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.api.HttpRequest;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

/**
 * 取款申请
 * 
 * @autho
 */
public class GetMoneyApplyActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getmoneyapply);
		initTitleButton();
		initTitle();
		initUI();
		FlurryAgent.onPageView();
		getData();
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		getData();
	}

	private TextView textView, textView2, textView3, textView4, textView5,
			textView6;
	private Button button;
	ProgressBar progressBar;
	private void initUI() {
		progressBar = (ProgressBar) findViewById(R.id.titleProgress);
		button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(this);

		textView = (TextView) findViewById(R.id.textView4);
		textView2 = (TextView) findViewById(R.id.textView5);
		textView3 = (TextView) findViewById(R.id.textView7);
		textView4 = (TextView) findViewById(R.id.textView8);
		textView5 = (TextView) findViewById(R.id.textView10);
		textView6 = (TextView) findViewById(R.id.textView11);
	}

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("取款申请");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void getData() {
		progressBar.setVisibility(View.VISIBLE);
		freshButton.setVisibility(View.GONE);
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					/*String userid = SharePersistent.getInstance()
							.getUserInfo(getApplicationContext()).getUserid();*/
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("c", "Storage");
					hashMap.put("a", "draw");
					hashMap.put("mobile", "android");

					//hashMap.put("uid", userid);
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.log("test", "返回 22      " + result);
					if (result.length() > 0) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						updateHandler.sendMessage(message);
					} else {
						updateHandler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	private Handler updateHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressBar.setVisibility(View.GONE);
			freshButton.setVisibility(View.VISIBLE);
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "没有读取到数据", 1000).show();
				break;
			case 1:
				String string = (String) msg.obj;
				try {
					JSONObject jsonObject = JSONObject.parseObject(string);
					stockAccount = jsonObject.getString("stockAccount");

					maxDraw = jsonObject.getString("maxDraw");

					textView.setText(stockAccount);
					textView2.setText(maxDraw);
					textView3.setText("0");
					textView4.setText("0");
					textView5.setText(stockAccount);
					textView6.setText(maxDraw);

				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			default:
				break;
			}
		};
	};

	private void  judgeTime(){
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
					try {
						/*String userid = SharePersistent.getInstance()
								.getUserInfo(getApplicationContext()).getUserid();*/
						Map<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("c", "Storage");
						hashMap.put("a", "draw");
						hashMap.put("mobile", "android");
						hashMap.put("act", "draw22");
						//hashMap.put("uid", userid);
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
						
						if (result.length() > 0) {
							Message message = new Message();
							message.what = 1;
							message.obj = result;
							judgeHandler.sendMessage(message);
						} else {
							judgeHandler.sendEmptyMessage(0);
						}
					} catch (Exception e) {
						e.printStackTrace();
						judgeHandler.sendEmptyMessage(0);
					}
			}
		});
	}
	
	
	private Handler  judgeHandler  = new  Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				showToast("连接服务器失败");
				break;
			case 1:
				String result  =  (String) msg.obj;
			    LogUtils.log("test", "result  "+result);
			    try {
					int  number  =  Integer.parseInt(result.trim());
					switch (number) {
					case 1:
						showToast("你好,每天只能取款一次！");
						break;
					case 3:
						showToast("你好,每天只能取款三次!");
						break;
					case 2://正常跳转
						if (maxDraw!=null && Double.parseDouble(maxDraw) > 0) {
											Intent intent = new Intent(getApplicationContext(),
													DrawMoneyActivity.class);
											intent.putExtra("maxDraw", maxDraw);
											startActivity(intent);
										} else {
											Toast.makeText(getApplicationContext(), "当前账户无法转出", 1000)
													.show();
										}
						break;
					default:
						break;
					}
				} catch (Exception e) {
					showToast("申请取款失败");
					e.printStackTrace();
				}

				break;
			default:
				break;
			}
		};
	};
	private String stockAccount, maxDraw;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			// 点击跳转到出款页面   首先判断当天的取款次数 
			judgeTime();
			break;
		default:
			break;
		}
	}
}
