package com.kfd.activityfour;

import java.util.ArrayList;

import com.kfd.activityfour.R;
import com.kfd.db.SharePersistent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class WhatsNewActivity extends BaseActivity
{

	private ViewPager mViewPager;

	private int currIndex = 0;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.whatsnew_viewpager);
		mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);

		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.whats1, null);
		View view2 = mLi.inflate(R.layout.whats2, null);
		View view3 = mLi.inflate(R.layout.whats3, null);
		View view4 = mLi.inflate(R.layout.whats4, null);

		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);

		SharePersistent.getInstance().savePerference(getApplicationContext(), "first", "yes");

		PagerAdapter mPagerAdapter = new PagerAdapter()
		{

			@Override
			public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == arg1;
			}

			@Override
			public int getCount()
			{
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object)
			{
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position)
			{
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		view4.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(WhatsNewActivity.this, MainActivity.class));
				WhatsNewActivity.this.finish();
			}
		});
		view1.findViewById(R.id.closelayout).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(WhatsNewActivity.this, MainActivity.class));
				WhatsNewActivity.this.finish();
			}
		});
		view2.findViewById(R.id.closelayout).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(WhatsNewActivity.this, MainActivity.class));
				WhatsNewActivity.this.finish();
			}
		});

		view3.findViewById(R.id.closelayout).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(WhatsNewActivity.this, MainActivity.class));
				WhatsNewActivity.this.finish();
			}
		});

		view4.findViewById(R.id.closelayout).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(WhatsNewActivity.this, MainActivity.class));
				WhatsNewActivity.this.finish();
			}
		});

		mViewPager.setAdapter(mPagerAdapter);
	}
}
