package com.kfd.activityfour;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.kfd.api.HttpRequest;
import com.kfd.api.Tools;
import com.kfd.common.AES;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.ui.TimeButton;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

//设置密码找回密码
public class FindPwdActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpwd);
		initUI();
		if (getIntent().getStringExtra("type") != null) {
			type = getIntent().getStringExtra("type");
			initTitle("修改密码");
		} else {
			initTitle("找回密码");
		}
		// setContentView(R.layout.setnewpwd);
	}

	EditText telEditText, verfyEditText;
	TimeButton timeButton;

	private void initUI() {
		telEditText = (EditText) findViewById(R.id.editText1);
		verfyEditText = (EditText) findViewById(R.id.editText2);
		timeButton = (TimeButton) findViewById(R.id.timeButton1);
		timeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phone = telEditText.getText().toString().trim();
				if (StringUtils.isEmpty(phone)) {
					showToast("手机号码不能为空");
					return;
				}
				sendTelPhoneCode(phone);
			}
		});
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String code = verfyEditText.getText().toString().trim();
				Tools.hideInputBoard(FindPwdActivity.this);
				showDialog("请稍候...");
				String phone = telEditText.getText().toString().trim();
				if (StringUtils.isEmpty(phone)) {
					showToast("手机号码不能为空");
					return;
				}
				if (StringUtils.isEmpty(code)) {
					showToast("验证码不能为空");
					return;
				}
				sendverifycode(phone, code);
			}
		});
	}

	String type = "forget";

	private void sendverifycode(final String mobile, final String code) {

		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("mobile", mobile);
				hashMap.put("code", code);
				hashMap.put("type", type);

				try {
					String result = HttpRequest.sendGetRequestWithMd5(FindPwdActivity.this, Define.host + "/api-user/checkcode", hashMap);
					if (!StringUtils.isEmpty(result)) {
						Message msg = new Message();
						msg.what = 2;
						msg.obj = result;
						codeHandler.sendMessage(msg);
					} else {
						showToast("当前网络较差，请重试");
					}
				} catch (Exception e) {
					showToast("当前网络较差，请重试");
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 
	 * @description 发送短信验证码
	 * @author zhu
	 * @update 2015年5月22日 下午3:46:58
	 */
	private void sendTelPhoneCode(final String mobile) {
		showDialog("请稍候...");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("mobile", mobile);
				if (getIntent().getStringExtra("type") != null) {
					hashMap.put("type", "password");
				} else {
					hashMap.put("type", "forget");
				}

				try {
					String result = HttpRequest.sendGetRequestWithMd5(FindPwdActivity.this, Define.host + "/api-user/code", hashMap);
					if (!StringUtils.isEmpty(result)) {
						Message msg = new Message();
						msg.what = 1;
						msg.obj = result;
						codeHandler.sendMessage(msg);
					} else {
						showToast("当前网络较差，请重试");
					}
				} catch (Exception e) {
					showToast("当前网络较差，请重试");
					e.printStackTrace();
				}
			}
		});
	}

	private Handler codeHandler = new Handler() {
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 0:

				break;
			case 1:
				String resultString = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(resultString);
					String ret = jsonObject.getString("ret");
					String message = jsonObject.getString("msg");
					if (ret.equals("0")) {
						showToast(message);
						// 跳转到设置用户名和密码页面

					} else {
						showToast(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
				String resultString1 = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(resultString1);
					String ret = jsonObject.getString("ret");
					String message = jsonObject.getString("msg");
					JSONObject data = jsonObject.optJSONObject("data");

					if (ret.equals("0")) {
						token = data.optString("token");
						setContentView(R.layout.setnewpwd);
						initSecondUI();
					} else {
						showToast(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};
	String token;
	EditText pwdEditText, pwdconfirmEditText;

	private void initSecondUI() {
		initTitle("设置新密码");
		pwdEditText = (EditText) findViewById(R.id.editText1);
		pwdconfirmEditText = (EditText) findViewById(R.id.editText2);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pwdString = pwdEditText.getText().toString().trim();
				String confirString = pwdconfirmEditText.getText().toString().trim();
				if (!StringUtils.isEmpty(pwdString) && !StringUtils.isEmpty(confirString) && pwdString.equals(confirString)) {
					setpwd(pwdString);
				} else {
					showToast("密码不能为空，且两次密码必须相同");
				}
			}
		});
	}

	private void setpwd(final String pwd) {
		showDialog("请稍候....");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				try {
					LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
					String pwdencrypt = AES.Encrypt(pwd, "a6ce962f31d4a3d9");
					hashMap.put("password", pwdencrypt);
					// hashMap.put("password", pwd);
					hashMap.put("token", token);
					try {
						String result = HttpRequest.sendGetRequestWithMd5(FindPwdActivity.this, Define.host + "/api-user/setpassword", hashMap);
						if (!StringUtils.isEmpty(result)) {
							Message msg = new Message();
							msg.what = 1;
							msg.obj = result;
							handler.sendMessage(msg);
						} else {
							showToast("当前网络较差，请重试");
						}
					} catch (Exception e) {
						showToast("当前网络较差，请重试");
						e.printStackTrace();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				break;
			case 1:
				String resultString = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(resultString);
					String ret = jsonObject.getString("ret");
					String message = jsonObject.getString("msg");
					if (ret.equals("0")) {
						showToast(message);
						finish();
						// 跳转到设置用户名和密码页面

					} else {
						showToast(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};
}
