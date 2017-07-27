package com.kfd.activityfour;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kfd.api.AppContext;
import com.kfd.api.HttpRequest;
import com.kfd.api.Tools;
import com.kfd.common.AES;
import com.kfd.common.Define;
import com.kfd.common.LogUtils;
import com.kfd.common.Logcat;
import com.kfd.common.StringUtils;
import com.kfd.common.UpdateService;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

/**
 * 用户登录界面
 * 
 * @author
 * 
 */

public class LoginActivity extends BaseActivity {
	Dialog dialog;

	private ProgressDialog loginWaitDialog;
	private String name = null;
	private String password = null;
	private String urlString;
	private CheckBox checkBox1;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setRequestedOrientation(1);
		setContentView(R.layout.login);
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		// String markString = getMark();
		// Cache.put("mark", markString);

		initUI();
		initTitle("用户登陆");
		backButton.setVisibility(View.GONE);
		// 版本检测接口http://my.brnew.com/?mobile=android&checkversion=checkversion
		if (isConnectingToInternet()) {
			checkVersion();
			// CheckVersionAsynTask checkVersionAsynTask = new
			// CheckVersionAsynTask();
			// checkVersionAsynTask.execute("");
			// if
			// (!SharePersistent.getInstance().getPerference(getApplicationContext(),
			// "install").toString().equals(getVersionName())) {
			// LogUtils.v("test", "install-----");
			// install();
			// }
		}

	}

	/**
	 * 第一次调用install
	 */
	private void install() {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				try {
					HashMap<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "basicIndex");
					hashMap.put("from", "2");
					hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					hashMap.put("version", getVersionName());
					Log.v("test", getDeviceId());
					String string = HttpRequest.sendPostRequest(ConstantInfo.parenturl, hashMap, "UTF-8");
					Log.v("test", "install返回" + string);
					if (!StringUtils.isEmpty(string)) {
						try {
							JSONObject jsonObject = new JSONObject(string);
							boolean result = jsonObject.getBoolean("result");
							if (result) {
								LogUtils.log("test", "install保存OK");
								SharePersistent.getInstance().savePerference(getApplicationContext(), "install", getVersionName());

								Message message2 = new Message();
								message2.what = 4;
								handler.sendMessage(message2);

							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 获取服务器版本
	 */
	private void getVersion() {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				try {
					LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
					hashMap.put("version", "basic");

					String string = HttpRequest.sendGetRequestWithMd5(LoginActivity.this, Define.host + "/api-main/version", hashMap);
					Log.v("test", "getVersion返回" + string);
					if (!StringUtils.isEmpty(string)) {
						try {
							JSONObject jsonObject = new JSONObject(string);
							JSONObject jsonObject2 = jsonObject.getJSONObject("data");
							String versionstring = jsonObject2.getString("version");
							String version = versionstring.substring(versionstring.indexOf("v") + 1, versionstring.length());
							LogUtils.v("test", "getVersion" + version);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @return true/false
	 */
	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private ImageView usernameclear, passwordclear;
	private EditText userEditText, passwordEditText;
	private Button loginButton, registerButton;
	private TextView findpw;

	private void initUI() {
		findpw = (TextView) findViewById(R.id.findpw1);
		findpw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), FindPwdActivity.class));
			}
		});
		registerButton = (Button) findViewById(R.id.registerbutton);
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
			}
		});
		userEditText = (EditText) findViewById(R.id.editText1);

		passwordEditText = (EditText) findViewById(R.id.editText2);

		passwordEditText.setOnKeyListener(onKey);
		findViewById(R.id.login_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tools.hideInputBoard(LoginActivity.this);
			}
		});
		loginButton = (Button) findViewById(R.id.button1);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("test", "点击登录");
				name = userEditText.getText().toString().trim();
				password = passwordEditText.getText().toString().trim();
				// name = "mina01";
				// password = "123456";
				doLogin();
				Tools.hideInputBoard(LoginActivity.this);
				// startActivity(new Intent(getApplicationContext(),
				// MainActivity.class));

				// LoginActivity.this.finish();
				// loginApp(name, password);
			}
		});

		usernameclear = (ImageView) findViewById(R.id.image1);
		passwordclear = (ImageView) findViewById(R.id.image2);
		userEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (userEditText.getText().toString().trim().length() > 0) {
					usernameclear.setVisibility(View.VISIBLE);
					usernameclear.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							userEditText.setText("");
							usernameclear.setVisibility(View.GONE);

						}
					});

				} else {
					usernameclear.setVisibility(View.GONE);
				}

			}
		});

		passwordEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (passwordEditText.getText().toString().trim().length() > 0) {
					passwordclear.setVisibility(View.VISIBLE);
					passwordclear.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							passwordEditText.setText("");
							passwordclear.setVisibility(View.GONE);
						}
					});

				} else {
					passwordclear.setVisibility(View.GONE);
				}

			}
		});

		boolean isChecked = SharePersistent.getInstance().isChekced(getApplicationContext());
		if (isChecked) {
			String name = SharePersistent.getInstance().getPerference(this, "username");
			userEditText.setText(name);
			checkBox1.setChecked(true);
		} else {
			checkBox1.setChecked(false);
		}
	}

	public static String toUtf8(String str) {
		String string;
		try {
			string = new String(str.getBytes("UTF-8"), "UTF-8");
			return string;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private int count = 0;

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void loginApp(final String username, final String password) {
		/*
		 * try { showDialog("登陆中，请稍候..."); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		executorService.execute(new Runnable() {

			@Override
			public void run() {

				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();

				hashMap.put("account", username);
				hashMap.put("password", password);

				try {

					String result = HttpRequest.sendGetRequestWithMd5(LoginActivity.this, Define.host + Define.LOGIN_STRING, hashMap);
					LogUtils.v("test", "result--- " + result);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						handler.sendMessage(message);

					} else {
						Message message = new Message();
						message.what = 0;
						message.obj = "连接超时，请重试";
						handler.sendMessage(message);
						// handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {

					// LogUtils.log("test", "err1");
					// if (count<1) {
					// ++count;
					// doLogin();
					// }else {
					// handler.sendEmptyMessage(2);
					// }
					e.printStackTrace();
				}
			}
		});

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (loginWaitDialog != null && loginWaitDialog.isShowing()) {
				loginWaitDialog.dismiss();
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
						if (checkBox1.isChecked()) {
							SharePersistent.getInstance().savePerference(getApplicationContext(), "username", name);
							SharePersistent.getInstance().savePerference(getApplicationContext(), "isChecked", true);
						} else {
							SharePersistent.getInstance().savePerference(getApplicationContext(), "isChecked", false);
						}

						AppContext.getInstance().setLogin(true);

						// startActivity(new Intent(getApplicationContext(),
						// MainActivity.class));
						// LoginActivity.this.finish();
						updateUserInfo();

					} else {
						showToast(msgdata);
					}
				} catch (Exception e) {
				}

				break;
			case 2:
				showToast("登录连接超时，请重试");
				break;
			case 3:
				showToast("请更新版本后再登陆！");
				break;
			case 4:
				CheckVersionAsynTask checkVersionAsynTask = new CheckVersionAsynTask();
				checkVersionAsynTask.execute("");
				break;
			default:
				break;
			}
		};
	};

	private void updateUserInfo() {
		showDialog("更新资料中");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				// if (!StringUtils.isEmpty(nickname)) {
				// hashMap.put("nickname", nickname);
				// }
				// if (!StringUtils.isEmpty(province)) {
				// hashMap.put("province", province);
				// }
				// if (!StringUtils.isEmpty(city)) {
				// hashMap.put("city", city);
				// }
				// if (!StringUtils.isEmpty(face)) {
				// hashMap.put("face", face);
				// }
				// if (!StringUtils.isEmpty(sex)) {
				// hashMap.put("sex", sex);
				// }
				hashMap.put("clientid", SharePersistent.getInstance().getPerference(getApplicationContext(), "clientid"));

				String result = HttpRequest.sendPostRequestWithMd5(LoginActivity.this, Define.host + "/api-user-main/update", hashMap);

				if (!StringUtils.isEmpty(result)) {
					LoginActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							dismissDialog();
							LoginActivity.this.finish();
						}
					});
				} else {
					// handler.sendEmptyMessage(0);
				}
			}
		});
	}

	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

	private String url;

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(192, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void doLogin() {

		if (!StringUtils.isEmpty(this.name)) {
			if (!StringUtils.isEmpty(this.password)) {

				try {

					showDialog();
					Log.v("test", "开始登录");
					// loginRequest(name, password);
					String pwdencrypt = AES.Encrypt(password, "a6ce962f31d4a3d9");
					// String pwdencrypt= encrypt(password,
					// "a6ce962f31d4a3d9").toString();

					Logcat.v("test", "加密结果  " + pwdencrypt);
					loginApp(name, pwdencrypt);
				} catch (Exception e) {

					e.printStackTrace();
				}

			} else {

				Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
			}
		} else
			Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
	}

	private void showDialog() {
		if (loginWaitDialog == null) {
			loginWaitDialog = new ProgressDialog(LoginActivity.this);
			loginWaitDialog.setMessage("正在登录,请稍候!");
		}
		if (!loginWaitDialog.isShowing()) {
			loginWaitDialog.show();
		}

	}

	OnKeyListener onKey = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {

			// TODO Auto-generated method stub

			if (keyCode == KeyEvent.KEYCODE_ENTER) {

				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

				if (imm.isActive()) {
					// 隐藏键盘的同时直接登录
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

				}

				return true;

			}

			return false;

		}

	};

	// 获取当前应用的版本号：

	private String getVersionName() {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
			String version = packInfo.versionName;
			LogUtils.v("MainActivity", "当前版本" + version);
			return version;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private void checkVersion() {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();

				hashMap.put("version", getVersionName());
				final String result = HttpRequest.sendGetRequestWithMd5(LoginActivity.this, Define.host + "/api-main/version", hashMap);

				LogUtils.v("sss", "CheckVersionAsynTask----" + result);
				if (!StringUtils.isEmpty(result)) {
					runOnUiThread(new Runnable() {
						public void run() {
							try {

								JSONObject object = new JSONObject(result);
								JSONObject jsonObject = object.optJSONObject("data");
								JSONObject jsonObject1 = jsonObject.optJSONObject("info");
								String version = jsonObject1.getString("version");

								if (isVersionLow(getVersionName(), version)) {

									urlString = jsonObject1.getString("downurl");
									String message = jsonObject1.getString("msg");
									// 出现提示对话框
									AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).setTitle("版本更新提示").setMessage(message)
											.setPositiveButton("确定", new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
													// TODO Auto-generated
													// method stub
													Intent intent = new Intent();
													intent.putExtra("url", urlString);
													intent.setClass(getApplicationContext(), UpdateService.class);
													startService(intent);
												}
											}).create();
									dialog.show();
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

				}
			}
		});
	}

	/**
	 * 检查版本
	 * 
	 * @author candyzhu 2012-12-12
	 * 
	 */
	class CheckVersionAsynTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();

			hashMap.put("version", getVersionName());

			try {
				String result = HttpRequest.sendGetRequestWithMd5(LoginActivity.this, Define.host + "/api-main/version", hashMap);
				LogUtils.v("sss", "CheckVersionAsynTask----" + result);
				if (!StringUtils.isEmpty(result)) {
					return result;

				} else {
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		private String version;

		@Override
		protected void onPostExecute(String result) {
			if (result != null && result.length() > 1) {
				JSONObject object;
				try {
					object = new JSONObject(result);
					/**
					 * version 当前版本 msg 更新内容 downurl 下载地址 size 大小
					 */

					// version = object.getString("version");
					version = object.getString("version");
					// LogUtils.v("test", version);
					// url = object2.getString("url");

					// 对比当前软件的version和网站的version
					// float now_version = Float.parseFloat(getVersionName());
					// float net_version = Float.parseFloat(version);
					// 记录版本名称
					// SharePersistent.getInstance().savePerference(getApplicationContext(),
					// "net_version", version);
					// LogUtils.v("MainActivity", "now_version:" + now_version
					// + " net_version:" + net_version);
					if (isVersionLow(getVersionName(), version)) {
						// SharePersistent.getInstance().savePerference(getApplicationContext(),
						// "new", status.toString());
						urlString = object.getString("downurl");
						String message = object.getString("msg");
						// 出现提示对话框
						AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).setTitle("版本更新提示").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated
								// method stub
								Intent intent = new Intent();
								intent.putExtra("url", urlString);
								intent.setClass(getApplicationContext(), UpdateService.class);
								startService(intent);
							}
						}).create();
						dialog.show();

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	// 判断当前版本是否比服务器上的版本低
	public Boolean isVersionLow(String nowVer, String ServerVer) {
		String nowVer_array = nowVer.replace(".", "");
		String ServerVer_array = ServerVer.replace(".", "");
		if (Integer.valueOf(nowVer_array) >= Integer.valueOf(ServerVer_array)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (loginWaitDialog != null && loginWaitDialog.isShowing()) {
			if (executorService != null) {
				executorService.shutdownNow();
			}
			loginWaitDialog.dismiss();
		}

	}
}
