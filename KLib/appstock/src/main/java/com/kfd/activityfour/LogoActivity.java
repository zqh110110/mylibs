package com.kfd.activityfour;

import com.igexin.sdk.PushManager;
import com.kfd.activityfour.R;
import com.kfd.common.StringUtils;
import com.kfd.db.SharePersistent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class LogoActivity extends BaseActivity {

	private AlphaAnimation aa;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.logo, null);
		setContentView(view);
		PushManager.getInstance().initialize(this.getApplicationContext());

		// System.out.println(System.currentTimeMillis());
		// 渐变展示启动屏
		aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);

	}

	@Override
	protected void onResume() {

		super.onResume();
		if (checknetwork()) {
			aa.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationEnd(Animation arg0) {
					redirectTo();
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationStart(Animation animation) {
				}

			});
		} else {
			openNet("网络信息", "无网络连接，请检查网络");
		}
	}

	private void openNet(String title, String message) {
		// 让用户检查网络
		AlertDialog.Builder adb = new AlertDialog.Builder(LogoActivity.this);
		final AlertDialog ad = adb.create();
		// String current = "当前无法获取定位信息";
		ad.setTitle(title);
		ad.setMessage(message);
		ad.setButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 由于4.0以上把原来的设置方式舍弃了所以上面的代码舍去
				if (android.os.Build.VERSION.SDK_INT > 13) {
					// 3.2以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
					startActivity(new Intent(
							android.provider.Settings.ACTION_SETTINGS));
				} else {
					startActivity(new Intent(
							android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				}

				ad.dismiss();

			}
		});
		ad.setButton2("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ad.dismiss();
				System.exit(0);
			}
		});
		ad.show();
	}

	public boolean checknetwork() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
			return false;
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null) {
			return false;
		}
		if (netinfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 跳转到...
	 */
	private void redirectTo() {
		if (StringUtils.isEmpty(SharePersistent.getInstance().getPerference(getApplicationContext(), "first"))) {
			startActivity(new Intent(getApplicationContext(),
					WhatsNewActivity.class));
		}else {
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));
		}
		finish();
	}

}
