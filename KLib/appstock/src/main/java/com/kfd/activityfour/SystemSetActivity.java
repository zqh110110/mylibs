package com.kfd.activityfour;

import com.kfd.api.Tools;
import com.kfd.db.SharePersistent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SystemSetActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.systemset);
		initTitle("系统设置");
		findViewById(R.id.ablayout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						AboutActivity.class));
			}
		});
		findViewById(R.id.assistlayout).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						startActivity(new Intent(getApplicationContext(),
								AssistActivity.class));
					}
				});

		findViewById(R.id.exitlayout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						SystemSetActivity.this)
						.setMessage("退出登录?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										finish();
										ActivityManager.popall();

										Tools.clearCookie(getApplicationContext());
										SharePersistent
												.getInstance()
												.removePerference(
														getApplicationContext(),
														"username");
										System.exit(0);
									}

								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).create();
				alertDialog.show();
			}
		});
		findViewById(R.id.helplayout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						HelpCenterListActivity.class));
			}
		});
		findViewById(R.id.msglayout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						MessageSetActivity.class));
			}
		});

	}

}
