package com.kfd.activityfour;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.kfd.api.AppContext;
import com.kfd.api.HttpRequest;
import com.kfd.bean.UserBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.ui.RoundImageView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我的 2015-6-14
 */
public class WodeActivity extends BaseActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wode);
		initTitle("我的");
		backButton.setVisibility(View.GONE);
		initUI();
		loadSecondData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (AppContext.getInstance().isLogin()) {
			loadData();
		}
	}

	@Override
	public void onBackPressed() {

		AlertDialog alertDialog = new AlertDialog.Builder(WodeActivity.this).setMessage("是否退出应用?").setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				ActivityManager.popall();
				System.exit(0);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		}).create();
		alertDialog.show();
	}

	private void loadSecondData() {

		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(WodeActivity.this, Define.host + "/api-user-main/get", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 1;
					message.obj = result;
					updateHandler.sendMessage(message);
				} else {
					updateHandler.sendEmptyMessage(0);
				}
			}
		});
	}

	/**
	 * withdraw 取款
	 * 
	 * injection 注资
	 */
	private String withdraw, injection;
	private Handler updateHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				break;
			case 1:
				String resulString = (String) msg.obj;

				try {
					JSONObject jsonObject = new JSONObject(resulString);
					String ret = jsonObject.getString("ret");
					if (ret.equals("0")) {
						JSONObject jsonObject2 = jsonObject.getJSONObject("data");
						withdraw = jsonObject2.optString("withdraw");
						injection = jsonObject2.optString("injection");

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
	RoundImageView roundImageView1;
	LinearLayout personlayout;
	TextView nicknametextView;

	private void initUI() {
		roundImageView1 = (RoundImageView) findViewById(R.id.roundImageView1);
		nicknametextView = (TextView) findViewById(R.id.nicknametextView);
		personlayout = (LinearLayout) findViewById(R.id.personlayout);
		personlayout.setOnClickListener(this);
		findViewById(R.id.syssetlayout).setOnClickListener(this);

		findViewById(R.id.getmoneylayout).setOnClickListener(this);
		findViewById(R.id.inputmoneylayout).setOnClickListener(this);

		findViewById(R.id.myactivelayout).setOnClickListener(this);
		findViewById(R.id.messagelayout).setOnClickListener(this);
		findViewById(R.id.chart).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;

		if (!AppContext.getInstance().isLogin()) {
			intent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			return;
		}

		switch (v.getId()) {
		case R.id.personlayout:
			intent = new Intent(getApplicationContext(), MineActivity.class);
			startActivity(intent);
			break;

		case R.id.syssetlayout:
			startActivity(new Intent(getApplicationContext(), SystemSetActivity.class));
			break;

		case R.id.getmoneylayout:
			if (!StringUtils.isEmpty(withdraw)) {
				intent = new Intent(getApplicationContext(), ZhuzhiActivity.class);
				intent.putExtra("type", "zhuzhi");
				intent.putExtra("url", withdraw);
				startActivity(intent);
			}
			break;

		case R.id.inputmoneylayout:
			if (!StringUtils.isEmpty(withdraw)) {
				intent = new Intent(getApplicationContext(), ZhuzhiActivity.class);
				intent.putExtra("type", "zhuzhi");
				intent.putExtra("url", injection);
				startActivity(intent);
			}
			break;

		case R.id.myactivelayout:
			startActivity(new Intent(getApplicationContext(), MyactiveActivity.class));
			break;
		case R.id.messagelayout:
			// 跳转到公告
			startActivity(new Intent(getApplicationContext(), PublishNoticeListActivity.class));
			break;
		case R.id.chart:
			// 跳转到聊天
			startActivity(new Intent(getApplicationContext(), ChartListActivity.class));
			break;
		}
	}

	private void loadData() {
		showDialog("请稍候");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(WodeActivity.this, Define.host + "/api-user-main/my", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 1;
					message.obj = result;
					handler.sendMessage(message);
				} else {
					handler.sendEmptyMessage(0);
				}
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 0:

				break;
			case 1:
				String resulString = (String) msg.obj;

				try {
					JSONObject jsonObject = new JSONObject(resulString);
					String ret = jsonObject.getString("ret");
					if (ret.equals("0")) {
						JSONObject jsonObject2 = jsonObject.getJSONObject("data");
						JSONObject jsonObject3 = jsonObject2.getJSONObject("user");
						// Gson gson = new Gson();
						// UserBean userBean =
						// gson.fromJson(jsonObject3.toString(),new
						// TypeToken<UserBean>(){}.getType());
						UserBean userBean = new UserBean();
						userBean.setUid(jsonObject3.optString("uid"));
						userBean.setBank(jsonObject3.optString("bank"));
						userBean.setBirthday(jsonObject3.optString("birthday"));
						userBean.setCity(jsonObject3.optString("city"));
						userBean.setFace(jsonObject3.optString("face"));
						userBean.setFollow_msg(jsonObject3.optString("follow_msg"));
						userBean.setIsemail(jsonObject3.optString("isemail"));
						userBean.setLike_msg(jsonObject3.optString("like_msg"));
						userBean.setLv(jsonObject3.optString("lv"));
						userBean.setMobile(jsonObject3.optString("mobile"));
						userBean.setNickname(jsonObject3.optString("nickname"));
						userBean.setProvince(jsonObject3.optString("province"));
						userBean.setRealname(jsonObject3.optString("realname"));
						userBean.setRec_msg(jsonObject3.optString("rec_msg"));
						userBean.setScores(jsonObject3.optString("scores"));
						userBean.setSex(jsonObject3.optString("sex"));
						userBean.setSignature(jsonObject3.optString("signature"));
						userBean.setSys_msg(jsonObject3.optString("sys_msg"));
						/**
						 * uid,mobile,nickname,birthday,province,city,face,
						 * signature,lv,
						 * scores,like_msg,follow_msg,rec_msg,sys_msg
						 * ,isemail,realname,bank,sex;
						 */
						imageLoader.displayImage(userBean.getFace(), roundImageView1, options);
						nicknametextView.setText(userBean.getNickname());
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
