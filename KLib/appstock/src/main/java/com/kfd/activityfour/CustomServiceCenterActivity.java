package com.kfd.activityfour;

import java.util.ArrayList;

import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 客服中心
 * 
 * 
 */
public class CustomServiceCenterActivity extends BaseActivity {
	HomeActivityGroup parentActivity1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customservicecenter);
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
		titleTextView.setText("客服中心");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.goBack();
			}
		});
	}

	private ListView listView;

	private void initUI() {

		listView = (ListView) findViewById(R.id.listView1);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("在线客服");
		arrayList.add("股票交易规则");
		arrayList.add("期货交易规则");
		ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
				R.layout.listitem, R.id.textView1, arrayList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					parentActivity1.startChildActivity("OnLineCustomServiceActivity", new Intent(
							CustomServiceCenterActivity.this, OnLineCustomServiceActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
					break;
				case 1:
					// 跳转至交易规则
					parentActivity1.startChildActivity("TradeRuleActivity", new Intent(
							CustomServiceCenterActivity.this, TradeRuleActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
					break;
				case 2:
					// 跳转至交易规则
					parentActivity1.startChildActivity("FuturesTradeRuleActivity", new Intent(
							CustomServiceCenterActivity.this, FuturesTradeRuleActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
					break;
				default:
					break;
				}
			}
		});
	}
}
