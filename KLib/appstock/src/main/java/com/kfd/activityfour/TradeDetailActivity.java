package com.kfd.activityfour;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.kfd.bean.MessageBean;
import com.kfd.bean.TradeBean;
import com.kfd.common.StringUtils;

public class TradeDetailActivity   extends BaseActivity{
		TextView  titleTextView,timeTextView,contentTextView;
		Intent intent;
		TradeBean tradeBean;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.message);
			initTitle("策略详情");
			titleTextView  =  (TextView) findViewById(R.id.textView1);
			timeTextView  = (TextView) findViewById(R.id.textView2);
			contentTextView  = (TextView) findViewById(R.id.textView3);
			if (getIntent().getSerializableExtra("detail")!=null) {
				tradeBean  = (TradeBean) getIntent().getSerializableExtra("detail");
				titleTextView.setText(tradeBean.getTitle());
				timeTextView.setText(StringUtils.phpdateformat(tradeBean.getDateline()));
				contentTextView.setText(tradeBean.getStdesc());
			}
		}
}