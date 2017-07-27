package com.kfd.activityfour;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.kfd.api.HttpRequest;
import com.kfd.common.Define;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;

public class AlarmActivity extends BaseActivity {
	String typename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// JumpLogin();
		setContentView(R.layout.alarm);
		initTitle("预警设置");
		initUI();
		if (getIntent().getStringExtra("typename") != null) {
			typename = getIntent().getStringExtra("typename");
		}
		getset();
	}

	private void getset() {
		// ：/api-user-main/getconfig
		showDialog("请稍候...");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				try {
					hashMap.put("typename", typename);
					String result = HttpRequest.sendGetRequestWithMd5(AlarmActivity.this, Define.host + "/api-market/warning", hashMap);
					LogUtils.v("test", "result--- " + result);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						updateHandler.sendMessage(message);
					} else {
						Message message = new Message();
						message.what = 0;
						message.obj = "连接超时，请重试";
						updateHandler.sendMessage(message);
						// handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

	}

	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
			}

			switch (msg.what) {
			case 0:
				// showToast("账号或者密码错误，请重试");
				String message = (String) msg.obj;
				showToast(message);
				break;
			case 1:
				String result = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret = jsonObject.getString("ret");
					String msgdata = jsonObject.getString("msg");
					/**
					 * "isfloat": "0",
					 * 
					 * "base_price": "",
					 * 
					 * "float_price": "",
					 * 
					 * "ispoint": "0",
					 * 
					 * "point_max": "",
					 * 
					 * "point_min": ""
					 */
					if (ret.equals("0")) {
						JSONObject data = jsonObject.optJSONObject("data");
						JSONObject config = data.optJSONObject("config");
						String isfloat = config.getString("isfloat");
						String base_price = config.getString("base_price");
						String float_price = config.getString("float_price");
						String ispoint = config.getString("ispoint");
						String point_max = config.getString("point_max");
						String point_min = config.getString("point_min");
						if (isfloat.equals("1")) {
							toggleButton.setChecked(true);
						} else {
							toggleButton.setChecked(false);
						}
						basepricEditText.setText(base_price);
						floatEditText.setText(float_price);

						if (ispoint.equals("1")) {
							toggleButton2.setChecked(true);
						} else {
							toggleButton2.setChecked(false);
						}
						highEditText.setText(point_max);
						lowEditText.setText(point_min);

					} else {
						showToast(msgdata);
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
	ToggleButton toggleButton, toggleButton2;
	EditText basepricEditText, floatEditText, highEditText, lowEditText;

	private void initUI() {
		toggleButton = (ToggleButton) findViewById(R.id.switch1);
		toggleButton2 = (ToggleButton) findViewById(R.id.switch2);
		basepricEditText = (EditText) findViewById(R.id.baselineeditText1);
		floatEditText = (EditText) findViewById(R.id.amounteditText1);
		highEditText = (EditText) findViewById(R.id.bigeditText1);
		lowEditText = (EditText) findViewById(R.id.smalleditText1);

		findViewById(R.id.commitbutton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setdata();
			}
		});
		findViewById(R.id.ringbutton1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), RingActivity.class));
			}
		});

	}

	private void setdata() {
		// ：/api-user-main/getconfig
		final boolean isfloat = toggleButton.isChecked();
		final boolean ispoint = toggleButton2.isChecked();
		final String base_price = basepricEditText.getText().toString().trim();
		final String float_price = floatEditText.getText().toString().trim();
		final String point_max = highEditText.getText().toString().trim();
		final String point_min = lowEditText.getText().toString().trim();

		if (StringUtils.isEmpty(base_price) && !StringUtils.isEmpty(float_price)) {
			showToast("基准价不能为空!");
			return;
		}
		if (!StringUtils.isEmpty(base_price) && StringUtils.isEmpty(float_price)) {
			showToast("浮动值不能为空!");
			return;
		}

		if (StringUtils.isEmpty(point_min) && StringUtils.isEmpty(point_max) && StringUtils.isEmpty(base_price) && StringUtils.isEmpty(float_price)) {

			showToast("请设置预警");
			return;
		}

		// if(!isfloat && !ispoint)
		// {
		// return;
		// }

		showDialog("请稍候...");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				try {
					if (isfloat) {
						hashMap.put("isfloat", "1");
					} else {
						hashMap.put("isfloat", "0");
					}

					if (ispoint) {
						hashMap.put("ispoint", "1");
					} else {
						hashMap.put("ispoint", "0");
					}
					hashMap.put("base_price", TextUtils.isEmpty(base_price.trim()) ? "0" : base_price);
					hashMap.put("float_price", TextUtils.isEmpty(float_price.trim()) ? "0" : float_price);
					hashMap.put("point_max", TextUtils.isEmpty(point_max.trim()) ? "0" : point_max);
					hashMap.put("point_min", TextUtils.isEmpty(point_min.trim()) ? "0" : point_min);

					hashMap.put("typename", typename);
					String result = HttpRequest.sendPostRequestWithMd5(AlarmActivity.this, Define.host + "/api-market/dowarning", hashMap);
					LogUtils.v("test", "result--- " + result);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						sethandler.sendMessage(message);
					} else {
						Message message = new Message();
						message.what = 0;
						message.obj = "连接超时，请重试";
						sethandler.sendMessage(message);
						// handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

	}

	private Handler sethandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
			}

			switch (msg.what) {
			case 0:
				// showToast("账号或者密码错误，请重试");
				String message = (String) msg.obj;
				showToast(message);
				break;
			case 1:
				String result = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret = jsonObject.getString("ret");
					String msgdata = jsonObject.getString("msg");

					if (ret.equals("0")) {
						showToast("设置成功 ！");

					} else {
						showToast(msgdata);
					}
				} catch (Exception e) {
				}

				break;

			default:
				break;
			}
		};
	};
}
