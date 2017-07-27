package com.kfd.activityfour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.kfd.api.AppContext;

/**
 * 发现
 * 
 * 2015-6-7
 */
public class FindActivity extends BaseActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find);
		initTitle("发现");
		backButton.setVisibility(View.GONE);

		findViewById(R.id.investlayout).setOnClickListener(this);
		findViewById(R.id.etflayout).setOnClickListener(this);

		findViewById(R.id.activelayout).setOnClickListener(this);
		findViewById(R.id.viplayout).setOnClickListener(this);

		findViewById(R.id.kfdlayout).setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {

		AlertDialog alertDialog = new AlertDialog.Builder(FindActivity.this).setMessage("是否退出应用?").setPositiveButton("确定", new DialogInterface.OnClickListener() {

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.investlayout:
			startActivity(new Intent(getApplicationContext(), InvestListActivity.class));
			break;

		case R.id.etflayout:
			startActivity(new Intent(getApplicationContext(), ETFPostionActivity.class));
			break;

		case R.id.activelayout:
			if (!AppContext.getInstance().isLogin()) {
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				return;
			}
			startActivity(new Intent(getApplicationContext(), YouhuiActivity.class));
			break;

		case R.id.viplayout:
			if (!AppContext.getInstance().isLogin()) {
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				return;
			}
			startActivity(new Intent(getApplicationContext(), UserVipActivity.class));
			break;

		case R.id.kfdlayout:
			showToast("该功能即将开放，敬请期待");
			break;

		default:
			break;
		}
	}
}
