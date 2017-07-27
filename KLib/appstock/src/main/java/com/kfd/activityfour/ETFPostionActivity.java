package com.kfd.activityfour;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 ETF持仓
 */
public class ETFPostionActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.etfpostion);
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		initUI();
	}

	RelativeLayout rlgold, rlsliver;
	ImageView ivgold, ivsiver;
	TextView tvgold, tvsliver;
	FrameLayout goldcontent, slivercontent;

	private void initUI() {

		goldcontent = (FrameLayout) findViewById(R.id.goldcontent);
		slivercontent = (FrameLayout) findViewById(R.id.slivercontent);
		tvgold = (TextView) findViewById(R.id.tvgold);
		tvsliver = (TextView) findViewById(R.id.tvsliver);
		ivgold = (ImageView) findViewById(R.id.ivgold);
		ivsiver = (ImageView) findViewById(R.id.ivsliver);
		rlgold = (RelativeLayout) findViewById(R.id.rlgold);
		rlsliver = (RelativeLayout) findViewById(R.id.rlsliver);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentByTag("ETFGoldFragment");
		if (fragment == null) {
			ETFGoldFragment etfGoldFragment = new ETFGoldFragment();

			fm.beginTransaction().add(R.id.goldcontent, etfGoldFragment, "ETFGoldFragment").commit();
		} else {
			ETFGoldFragment etfGoldFragment = new ETFGoldFragment();

			fm.beginTransaction().replace(R.id.goldcontent, etfGoldFragment, "ETFGoldFragment").commit();
		}

		rlgold.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getSupportFragmentManager();
				Fragment fragment = fm.findFragmentByTag("ETFGoldFragment");
				if (fragment == null) {
					ETFGoldFragment etfGoldFragment = new ETFGoldFragment();

					fm.beginTransaction().add(R.id.goldcontent, etfGoldFragment, "ETFGoldFragment").commit();
				} else {
					ETFGoldFragment etfGoldFragment = new ETFGoldFragment();

					fm.beginTransaction().replace(R.id.goldcontent, etfGoldFragment, "ETFGoldFragment").commit();
				}
				ivgold.setVisibility(View.VISIBLE);
				ivsiver.setVisibility(View.GONE);
				tvgold.setTextColor(Color.parseColor("#ffffff"));
				tvsliver.setTextColor(Color.parseColor("#eebbbf"));
				goldcontent.setVisibility(View.VISIBLE);
				slivercontent.setVisibility(View.GONE);
			}
		});
		rlsliver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getSupportFragmentManager();
				Fragment fragment = fm.findFragmentByTag("ETFSliverFragment");
				if (fragment == null) {
					ETFSliverFragment etfGoldFragment = new ETFSliverFragment();

					fm.beginTransaction().add(R.id.slivercontent, etfGoldFragment, "ETFSliverFragment").commit();
				} else {
					ETFSliverFragment etfGoldFragment = new ETFSliverFragment();

					fm.beginTransaction().replace(R.id.slivercontent, etfGoldFragment, "ETFSliverFragment").commit();
				}
				ivgold.setVisibility(View.GONE);
				ivsiver.setVisibility(View.VISIBLE);
				tvsliver.setTextColor(Color.parseColor("#ffffff"));
				tvgold.setTextColor(Color.parseColor("#eebbbf"));
				goldcontent.setVisibility(View.GONE);
				slivercontent.setVisibility(View.VISIBLE);
			}
		});
	}
}
