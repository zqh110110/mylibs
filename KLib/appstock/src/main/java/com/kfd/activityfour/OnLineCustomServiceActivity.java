package com.kfd.activityfour;

import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.common.StringUtils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 在线客服
 * 
 * @author 
 */
public class OnLineCustomServiceActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.online_customservice);
		initTitle();
		FlurryAgent.onPageView();
	TextView  textView = (TextView) findViewById(R.id.text10);
	textView.setText(StringUtils.ToDBC(getResources().getString(R.string.hk_adress)));
	initTitleButton();
	freshButton.setVisibility(View.GONE);
	}

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {
		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("在线客服");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
