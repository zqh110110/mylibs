package com.kfd.activityfour;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

/**
 * 手机验证
 * 
 
 */
public class CheckPhoneNumberActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkphonenumber);
		initTitle();
		initUI();
		FlurryAgent.onPageView();
		initTitleButton();
		freshButton.setVisibility(View.GONE);
	}

	// private Button backButton;
	private TextView titleTextView;

	private void initTitle() {

		// backButton = (Button) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("手机验证 ");
		/*
		 * backButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub finish(); } });
		 */
	}

	private Button commitButton,nextButton,getnumberbutton;
	private EditText oldpasswordEditText, phoneNumberEditText,
			smscodeEditText;
	
	private LinearLayout llLayout1 ,llLayout2,llLayout3;
	private View line1,line2;
	private TextView tx1,tx2;

	private void initUI() {
		llLayout1 = (LinearLayout) findViewById(R.id.lin1);
		llLayout2 = (LinearLayout) findViewById(R.id.lin2);
		llLayout3 = (LinearLayout) findViewById(R.id.lin3);
		tx1 = (TextView) findViewById(R.id.textView1);
		tx2 = (TextView) findViewById(R.id.textView2);
		
		line1 = findViewById(R.id.line1);
		line2 = findViewById(R.id.line2);
		nextButton = (Button) findViewById(R.id.button1);
		commitButton = (Button) findViewById(R.id.button2);
		getnumberbutton = (Button) findViewById(R.id.getnumberbutton);
		oldpasswordEditText = (EditText) findViewById(R.id.editText1);
		phoneNumberEditText = (EditText) findViewById(R.id.editText2);
		smscodeEditText = (EditText) findViewById(R.id.editText3);
		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String oldpassword = oldpasswordEditText.getText().toString()
						.trim();
				if (oldpassword.length() > 0) {
					checkPassword(oldpassword);

				} else {
					Toast.makeText(getApplicationContext(), "请确认密码不为空",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		
		commitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String oldpassword = oldpasswordEditText.getText().toString()
						.trim();
				String phoneNumber = phoneNumberEditText.getText().toString()
						.trim();
				String smscode = smscodeEditText.getText()
						.toString().trim();
				changePassword(oldpassword, phoneNumber, smscode);
			}
		});
		
		
		getnumberbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phoneNumber = phoneNumberEditText.getText().toString()
						.trim();
				if (phoneNumber.toString().equals("")) {
					showToast("请输入手机号码");
				}else {
					getnumber();					
				}
			}
		});
	}
	
	
	/**
	 * 抖动一下子
	 */

	public void showEorrorOnEditText(EditText ed1, String dis) {

		ed1.setHint(dis);
		Animation shakeAnim = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.shake_x);
		ed1.startAnimation(shakeAnim);

	}

	/**
	 * 验证手机校验码
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void changePassword(final String oldpassword,
			final String phoneNumber, final String smscode) {
		try {
			progressDialog = new ProgressDialog(getParent());
			progressDialog.setMessage("正在验证手机验证码，请稍候.....");
			
			progressDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		executorService.execute(new Runnable() {

			@Override
			public void run() {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "userCheckcode");
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					hashMap.put("check_phone", "1");
					hashMap.put("mobile", phoneNumber);
					hashMap.put("smscode", smscode);
					try {
						String result = HttpRequest.sendPostRequest(ConstantInfo.parenturl,
								hashMap, "UTF-8").trim();
						// String string = UnicodeConverter.decode(result);
						JSONObject jsonObject = JSONObject.parseObject(result);
						String status = jsonObject.getString("status");
						String message = jsonObject.getString("message");
						String data = jsonObject.getString("data");
						if (status.equals("1")) {
							Message msg = new Message();
							msg.obj = message;
							msg.what = 1;
							updateUIHandler.sendMessage(msg);

						} else {
							Message msg = new Message();
							msg.obj = message;
							msg.what = 0;
							updateUIHandler.sendMessage(msg);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			//}
		});

	}
	
	/**
	 * 获取手机验证码
	 */
	
	private void getnumber() {
		// TODO Auto-generated method stub
		try {
			progressDialog = new ProgressDialog(getParent());
			progressDialog.setMessage("正在发送手机验证码，请稍候.....");
			
			progressDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		executorService.execute(new Runnable() {

			@Override
			public void run() {
					String phoneNumber = phoneNumberEditText.getText().toString()
						.trim();
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "userSendcode");
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("mobile", phoneNumber);
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					try {
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
						// String string = UnicodeConverter.decode(result);
						JSONObject jsonObject = JSONObject.parseObject(result);
						String status = jsonObject.getString("status");
						String message = jsonObject.getString("message");
						String data = jsonObject.getString("data");
						if (status.equals("1")) {
							Message msg = new Message();
							msg.obj = message;
							msg.what = 0;
							updateUIHandler.sendMessage(msg);
						}else {
							Message msg = new Message();
							msg.obj = message;
							msg.what = 0;
							updateUIHandler.sendMessage(msg);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
		});
	}
	
	/**
	 * 
	 * @param 检查密码
	 */
	
	private void checkPassword(final String oldpassword) {
		// TODO Auto-generated method stub
		try {
			progressDialog = new ProgressDialog(getParent());
			progressDialog.setMessage("验证身份中，请稍候.....");
			
			progressDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		executorService.execute(new Runnable() {

			@Override
			public void run() {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "userDopawd");
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("account", SharePersistent.getInstance().getPerference(getApplicationContext(), "account"));
					hashMap.put("pawd", oldpassword);
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					try {
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
						// String string = UnicodeConverter.decode(result);
						JSONObject jsonObject = JSONObject.parseObject(result);
						String status = jsonObject.getString("status");
						String message = jsonObject.getString("message");
						String data = jsonObject.getString("data");
						if (status.equals("1")) {
							Message msg = new Message();
							//msg.obj = message;
							msg.what = 2;
							updateUIHandler.sendMessage(msg);

						} else {
							Message msg = new Message();
							msg.obj = message;
							msg.what = 0;
							updateUIHandler.sendMessage(msg);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
		});
	}


	private Handler updateUIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (progressDialog!=null&&progressDialog.isShowing()) {	
				progressDialog.dismiss();
			}
			switch (msg.what) {
			case 0:
				String string0 = (String) msg.obj;
				Toast.makeText(getApplicationContext(), string0,
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				String string = (String) msg.obj;
				Toast.makeText(getApplicationContext(), string,
						Toast.LENGTH_SHORT).show();
				finish();
				break;
				
			case 2:
				String string1 = (String) msg.obj;
				llLayout1.setVisibility(View.GONE);
				llLayout2.setVisibility(View.VISIBLE);
				llLayout3.setVisibility(View.VISIBLE);
				nextButton.setVisibility(View.GONE);
				commitButton.setVisibility(View.VISIBLE);
				tx1.setTextColor((ColorStateList) getResources().getColorStateList(R.color.gray));
				tx2.setTextColor((ColorStateList) getResources().getColorStateList(R.color.titlecolor));
				line1.setBackgroundResource(R.color.gray_deep);
				line2.setBackgroundResource(R.color.titlecolor);
				break;
			default:
				break;
			}
		};
	};

}
