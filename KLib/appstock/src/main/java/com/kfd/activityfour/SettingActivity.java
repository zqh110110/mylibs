package com.kfd.activityfour;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.LoginActivity.CheckVersionAsynTask;

import com.kfd.adapter.AccountAdapter;
import com.kfd.api.AppContext;
import com.kfd.api.HttpRequest;
import com.kfd.bean.CustomInfoBean;
import com.kfd.bean.TotalfundsBean;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.common.UpdateService;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.ui.SwitchButton;
import com.kfd.ui.SwitchButton.OnCheckedChangeListener;

/**
 * 
 * @ 设置
 *
 */
public class SettingActivity extends BaseActivity{
	HomeActivityGroup parentActivity1;
	ProgressDialog progressDialog;
	private String urlString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		parentActivity1 = (HomeActivityGroup) getParent();
		initUI();
		initTitleButton();
		freshButton.setVisibility(View.GONE);
		initTitle();
		FlurryAgent.onPageView();
	}

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		 backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("设置");
		
		 backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.goBack();
			}
		});
		 
	}
	
	private LinearLayout clearLayout,update,service,idea;
	private SwitchButton switchButton;
	private Button exitButton;
	
	private void initUI() {
		// TODO Auto-generated method stub
		clearLayout = (LinearLayout) findViewById(R.id.clearLayout);
		update = (LinearLayout) findViewById(R.id.update);
		service = (LinearLayout) findViewById(R.id.service);
		idea = (LinearLayout) findViewById(R.id.idea);
		
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
		
		switchButton = (SwitchButton) findViewById(R.id.switchbtn);

		//设定默认显示
		if (StringUtils.isEmpty(SharePersistent.getInstance().getPerference(getApplicationContext(), "switchButton"))) {
			switchButton.setChecked(true);
		}else {
			switchButton.setChecked(SharePersistent.getInstance().getPerference(getApplicationContext(), "switchButton").toString().equals("1"));		
		}
		

		switchButton.setTextOn("是");
		switchButton.setTextOff("否");
		switchButton.setOnCheckedChangeListener(onCheckedChange);
		clearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder builder = new Builder(getParent());
				builder.setMessage("是否清除缓存？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						((AppContext) context.getApplicationContext())
						.clearAppCache();
						showToast("缓存已清除");
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
		
		update.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						AlertDialog.Builder builder = new Builder(getParent());
						builder.setMessage("是否检查更新？");
						builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								try {
									progressDialog = new ProgressDialog(getParent());
									progressDialog.setMessage("正在检查更新，请稍候...");
									
									progressDialog.show();
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
								CheckVersionAsynTask checkVersionAsynTask = new CheckVersionAsynTask();
								checkVersionAsynTask.execute("");
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
		service.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					parentActivity1.startChildActivity("CustomServiceCenterActivity", new Intent(
							SettingActivity.this, CustomServiceCenterActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				}
			});
		idea.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	
	
	public OnCheckedChangeListener onCheckedChange = new OnCheckedChangeListener() {
		public void checkedChange(boolean isChecked) {
			Log.d("this isChecked", isChecked + "-------");
			String string = "1";
			if (isChecked) {
				string="1";
			}else {
				string="0";
			}
			SharePersistent.getInstance().savePerference(getApplicationContext(), "switchButton", string);
		}
	};
	
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
							message.what = 1;
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
	
	private Handler updateUIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case 0:
				showToast("退出账号失败");
				break;			
			case 1:
				try {
					String string = (String) msg.obj;
					JSONObject jsonObject = JSONObject.parseObject(string);
					String string3 = jsonObject.getString("message");
					String string4 = jsonObject.getString("status");
					showToast(string3);
					if (string4.equals("1")) {
						finish();
						startActivity(new Intent(SettingActivity.this,LoginActivity.class));
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
	
	/**
	 * 检查版本
	 * 
	 * @author candyzhu 2012-12-12
	 * 
	 */
	class CheckVersionAsynTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {		
			HashMap<String, String>  hashMap  = new HashMap<String, String>();
			hashMap.put("request", "basic");
			hashMap.put("from", "2"); hashMap.put("mark", getMark());
			hashMap.put("version", getVersionName());
			hashMap.put("appid", getDeviceId());
			
			HttpResponse response;
			try {
				String result  = HttpRequest.sendPostRequest(ConstantInfo.parenturl, hashMap, "UTF-8");
				LogUtils.v("sss", "CheckVersionAsynTask----"+result);
				if (result.length()>0 && result!=null) {

					return result;
				} else {
				}

				// System.out.println("返回码" +
				// response.getStatusLine().getStatusCode());

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		private String url;
		private String status;

		@Override
		protected void onPostExecute(String result) {
			if (progressDialog!=null&&progressDialog.isShowing()) {
				progressDialog.dismiss();			
			}
			if (result != null && result.length() > 1) {
				JSONObject object;
				try {
					object = JSONObject.parseObject(result);
						 //version = object.getString("version");
						 status = object.getString("status");
						// LogUtils.v("test", version);
						//url = object2.getString("url");

						// 对比当前软件的version和网站的version
						//float now_version = Float.parseFloat(getVersionName());
						//float net_version = Float.parseFloat(version);
						//记录版本名称
						//SharePersistent.getInstance().savePerference(getApplicationContext(), "net_version", version);
						//LogUtils.v("MainActivity", "now_version:" + now_version
						//		+ " net_version:" + net_version);
						if (status.toString().equals("2")) {
							SharePersistent.getInstance().savePerference(getApplicationContext(), "new", status.toString());
							urlString = object.getString("remove");
							String message = object.getString("message");
							// 出现提示对话框
							AlertDialog dialog = new AlertDialog.Builder(
									SettingActivity.this)
									.setTitle("版本更新提示")
									.setMessage(message)
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub
													Intent intent = new Intent();
													intent.putExtra("url", urlString);
													intent.setClass(
															getApplicationContext(),
															UpdateService.class);
													startService(intent);
												}
											})
									.create();
							dialog.show();

						}else {
							showToast("暂无版本更新！");
							SharePersistent.getInstance().removePerference(
									getApplicationContext(), "new");
						}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
	
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
}
