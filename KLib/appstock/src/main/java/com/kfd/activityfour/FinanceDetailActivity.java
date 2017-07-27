package com.kfd.activityfour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.AccountAdapter;

/**
 * 
 * @ 财务明细
 *
 */
public class FinanceDetailActivity extends BaseActivity{
	HomeActivityGroup parentActivity1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.financedetail);
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
		titleTextView.setText("财务明细");
		
		 backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.goBack();
			}
		});
		 
	}
	
	private LinearLayout payLayout,getmoneyLayout,apply;
	
	private void initUI() {
		// TODO Auto-generated method stub
		payLayout = (LinearLayout) findViewById(R.id.Pay);
		getmoneyLayout = (LinearLayout) findViewById(R.id.getmoney);
		apply = (LinearLayout) findViewById(R.id.apply);
		payLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//parentActivity1.startChildActivity("PayRecordActivity", new Intent(
				//		FinanceDetailActivity.this, PayRecordActivity.class)
				//		.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				
				startActivity(new Intent(FinanceDetailActivity.this, PayRecordActivity.class));
			}
		});
		
		getmoneyLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//parentActivity1.startChildActivity("GetMoneyRecordeActivity", new Intent(
				//		FinanceDetailActivity.this, GetMoneyRecordeActivity.class)
				//		.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				startActivity(new Intent(FinanceDetailActivity.this, GetMoneyRecordeActivity.class));
			}
		});
		
		apply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//parentActivity1.startChildActivity("PayRecordActivity", new Intent(
				//		FinanceDetailActivity.this, PayRecordActivity.class)
				//		.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				
				startActivity(new Intent(FinanceDetailActivity.this, OnLineActivity.class));
			}
		});
	}
}
