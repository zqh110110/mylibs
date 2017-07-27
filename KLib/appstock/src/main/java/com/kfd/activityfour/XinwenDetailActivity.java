package com.kfd.activityfour;

import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.bean.MainMesageBean;
import com.kfd.common.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 新闻详情页面
 * 
 * @author 
 */
public class XinwenDetailActivity extends Activity {
	private MainMesageBean mainMesageBean;
	private TextView newstitleTextView, newstimeTextView, abstractTextView,
			contentTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdetails);
		initTitle();
		FlurryAgent.onPageView();
		newstitleTextView = (TextView) findViewById(R.id.textView1);
		newstimeTextView = (TextView) findViewById(R.id.textView2);
		// abstractTextView = (TextView) findViewById(R.id.textView4);
		contentTextView = (TextView) findViewById(R.id.textView3);

		if (getIntent().getSerializableExtra("bean") != null) {
			mainMesageBean = (MainMesageBean) getIntent().getSerializableExtra(
					"bean");
			newstitleTextView.setText(StringUtils.ToDBC(mainMesageBean
					.getTitle()));
			newstimeTextView.setText(mainMesageBean.getAdd_time());
			// abstractTextView.setText(StringUtils.ToDBC(mainMesageBean.getAbstractmessage()));
			contentTextView.setText(StringUtils.ToDBC(mainMesageBean
					.getContent()));
		}

	}

	private TextView titleTextView;

	private void initTitle() {
		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("新闻详情");
	}

}
