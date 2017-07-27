package com.kfd.activityfour;

import java.util.ArrayList;

import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.AccountAdapter;

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
 * 交易报表
 * 
 * @author 朱继洋 QQ7617812 2013-5-24
 */
public class TransactionReportsActivity extends BaseActivity {
	private String type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tradecenter);
		initTitleButton();
		freshButton.setVisibility(View.GONE);
		initUI();
		if (getIntent().getStringExtra("type")!=null) {
			type = getIntent().getStringExtra("type");
		}
		initTitle();
		FlurryAgent.onPageView();
	}

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("交易报表 ");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private ListView listView;
	private String array[] = {"平仓单","留仓单","汇总信息"};
	private void initUI() {

		listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(new AccountAdapter(this,array));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					if (type!=null && type.length()>0) {
						startActivity(new Intent(getApplicationContext(),
								FuturesEveningUpBillActivity.class));
					}else {
						startActivity(new Intent(getApplicationContext(),
								EveningUpBillActivity.class));
					}
					
					break;
				case 1:
					if (type!=null && type.length()>0) {
						startActivity(new Intent(getApplicationContext(),
								FuturesKeepBillActivity.class));
					}else {
					startActivity(new Intent(getApplicationContext(),
							KeepBillActivity.class));
					}
					break;
				case 2:
					Intent   intent  =new Intent(getApplicationContext(),
							CollectInformationActivity.class);
					if (type!=null && type.length()>0) {
						intent.putExtra("type", type);
					}else {
						
					}
					startActivity(intent);
					break;

				default:
					break;
				}
			}
		});
	}
}