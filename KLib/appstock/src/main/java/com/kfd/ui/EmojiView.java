package com.kfd.ui;

/*
 * Copyright (c) 2014 The MaMaHelp_small_withReceiver_6.0.0 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kfd.common.AsyncImageLoader.ImageCallback11;
import com.kfd.activityfour.BaseActivity;
import com.kfd.activityfour.R;
import com.kfd.api.Tools;
import com.kfd.common.Define;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;




/**
 * @Function: TODO ADD FUNCTION. 
 * @author 张清田
 * @version
 * @Date: 2014年9月12日 上午11:48:24
 */
public class EmojiView extends LinearLayout implements OnClickListener,OnPageChangeListener{
	private Context context;
	
	private LinearLayout circle_1_ll;
	private LinearLayout circle_2_ll;
	private LinearLayout circle_3_ll;
	private LinearLayout circle_4_ll;
	private LinearLayout circle_5_ll;
	
	private ViewPager viewpager1;
	private ViewPager viewpager2;
	private ViewPager viewpager3;
	private ViewPager viewpager4;
	private ViewPager viewpager5;
	private ViewPagerAdapter vpAdapter1;
	private ViewPagerAdapter vpAdapter2;
	private ViewPagerAdapter vpAdapter3;
	private ViewPagerAdapter vpAdapter4;
	private ViewPagerAdapter vpAdapter5;
	
	private Button smail_btn;
	private Button flowers_btn;
	private Button bells_btn;
	private Button numbers_btn;
	private Button cars_btn;
	private Button delete_btn;
	
	
	private static int select_category = 0;// 表情当前选中的类
	private String[] smail = null;
	
	private EditText mEditText;
	
	public void setEditText(EditText editText) {
		this.mEditText = editText;
	}

	public EmojiView(Context context)
	{
		this(context,null);
	}

	public EmojiView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public EmojiView(Context ctt, AttributeSet attrs, int defStyle)
	{
		super(ctt, attrs);
		this.context = ctt;
		LayoutInflater.from(context).inflate(R.layout.lmall_layout_emoji, this, true);
		
		findViewById(R.id.emoji_ll).setVisibility(View.VISIBLE);
		
		circle_1_ll = (LinearLayout) findViewById(R.id.circle_1_ll);
		circle_2_ll = (LinearLayout) findViewById(R.id.circle_2_ll);
		circle_3_ll = (LinearLayout) findViewById(R.id.circle_3_ll);
		circle_4_ll = (LinearLayout) findViewById(R.id.circle_4_ll);
		circle_5_ll = (LinearLayout) findViewById(R.id.circle_5_ll);
		
		
		viewpager1 = (ViewPager) findViewById(R.id.viewpager1);
		viewpager2 = (ViewPager) findViewById(R.id.viewpager2);
		viewpager3 = (ViewPager) findViewById(R.id.viewpager3);
		viewpager4 = (ViewPager) findViewById(R.id.viewpager4);
		viewpager5 = (ViewPager) findViewById(R.id.viewpager5);
		
		smail_btn = (Button) findViewById(R.id.smail_btn);
		smail_btn.setOnClickListener(this);
		flowers_btn = (Button) findViewById(R.id.flowers_btn);
		flowers_btn.setOnClickListener(this);
		bells_btn = (Button) findViewById(R.id.bells_btn);
		bells_btn.setOnClickListener(this);
		numbers_btn = (Button) findViewById(R.id.numbers_btn);
		numbers_btn.setOnClickListener(this);
		cars_btn = (Button) findViewById(R.id.cars_btn);
		cars_btn.setOnClickListener(this);
		
		delete_btn = (Button) findViewById(R.id.delete_btn);
		delete_btn.setOnClickListener(this);
	}
	
	public void init(EditText editText)
	{
		this.mEditText = editText;
		smail_btn.setBackgroundResource(R.drawable.lmall_smail_select);
		flowers_btn.setBackgroundResource(R.drawable.lmall_flowers_normal);
		bells_btn.setBackgroundResource(R.drawable.lmall_bells_normal);
		cars_btn.setBackgroundResource(R.drawable.lmall_cars_normal);
		numbers_btn.setBackgroundResource(R.drawable.lmall_number_normal);
		circle_1_ll.setVisibility(View.VISIBLE);
		int child_1_count = circle_1_ll.getChildCount();
		for (int i = 0; i < child_1_count; i++) {
			ImageView circle = (ImageView) circle_1_ll.getChildAt(i);
			if (i == 0) {
				circle.setImageResource(R.drawable.lmall_circle_select);
			} else {
				circle.setImageResource(R.drawable.lmall_circle_normal);
			}
		}
		circle_2_ll.setVisibility(View.GONE);
		circle_3_ll.setVisibility(View.GONE);
		circle_4_ll.setVisibility(View.GONE);
		circle_5_ll.setVisibility(View.GONE);
		// 初始化引导图片列表
		// 初始化引导图片列表
		
		List<View> views = new ArrayList<View>();
		for (int i = 0; i < 9; i++) {
			ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
			View ll = LayoutInflater.from(context).inflate(R.layout.lmall_emoji_page_view, null);
			GridView gd = (GridView) ll.findViewById(R.id.emoji_gd);
			smail = EmojiUtils.getSmail(i);
			final String[] temp_smail = smail;
			for (int j = 0; j < smail.length; j++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("smail", smail[j]);
				al.add(map);
			}
			EmojiAdapter sa = new EmojiAdapter(
					context, al, R.layout.lmall_emoji_item,
					new String[] { "smail" },
					new int[] { R.id.image_iv });
			gd.setAdapter(sa);
			gd.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					java.text.DecimalFormat format = new java.text.DecimalFormat("0000");
					setFace(temp_smail[arg2]);
				}
			});
			views.add(ll);
		}
		vpAdapter1 = new ViewPagerAdapter(views);
		viewpager1.setAdapter(vpAdapter1);
		viewpager1.setVisibility(View.VISIBLE);
		viewpager2.setVisibility(View.GONE);
		viewpager3.setVisibility(View.GONE);
		viewpager4.setVisibility(View.GONE);
		viewpager5.setVisibility(View.GONE);
		
		viewpager1.setOnPageChangeListener(this);
		viewpager2.setOnPageChangeListener(this);
		viewpager3.setOnPageChangeListener(this);
		viewpager4.setOnPageChangeListener(this);
		viewpager5.setOnPageChangeListener(this);
	}
	
	
	// 第2个参数 是图片要的名称，可以自己取 第3个参数就是 图片资源ID
	private void setFace(String faceImg) {
		if(mEditText==null){
			return;
		}
		final int font_size = Tools.dip2px(context, 20);
		final int start = mEditText.getSelectionStart();
		final String faceTitle = faceImg;
		final Spannable ss = mEditText.getText().insert(start, "<" + faceTitle + ">");
		Bitmap cachedImage =  BaseActivity.getAsyncImageLoaderInstance(context).loadDrawable(
				Define.emoji_prefix + faceImg+".png", faceTitle, new ImageCallback11() {
					@Override
					public void imageLoaded(Bitmap imageDrawable, String tag, String imageUrl) {
						Drawable d = new BitmapDrawable(imageDrawable);  
						d.setBounds(3, 3, font_size, font_size);
						ImageSpan span = new ImageSpan(d, faceTitle + ".gif", ImageSpan.ALIGN_BASELINE);
						ss.setSpan(span, start, start + ("<" + faceTitle + ">").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
				}, false);
		if (cachedImage == null) {
		} else {
			Drawable d = new BitmapDrawable(cachedImage);  
			d.setBounds(3, 3, font_size, font_size);
			ImageSpan span = new ImageSpan(d, faceTitle + ".gif", ImageSpan.ALIGN_BASELINE);
			ss.setSpan(span, start, start + ("<" + faceTitle + ">").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}

	@Override
	public void onClick(View v) {
		List<View> views = null;
		if (v==delete_btn) {
			if(mEditText!=null)
			{
				mEditText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
			}
		}else if (v==smail_btn) {
			views = new ArrayList<View>();
			select_category = 0;
			smail_btn.setBackgroundResource(R.drawable.lmall_smail_select);
			flowers_btn.setBackgroundResource(R.drawable.lmall_flowers_normal);
			bells_btn.setBackgroundResource(R.drawable.lmall_bells_normal);
			cars_btn.setBackgroundResource(R.drawable.lmall_cars_normal);
			numbers_btn.setBackgroundResource(R.drawable.lmall_number_normal);
			circle_1_ll.setVisibility(View.VISIBLE);
			int child_1_count = circle_1_ll.getChildCount();
			for (int i = 0; i < child_1_count; i++) {
				ImageView circle = (ImageView) circle_1_ll.getChildAt(i);
				if (i == 0) {
					circle.setImageResource(R.drawable.lmall_circle_select);
				} else {
					circle.setImageResource(R.drawable.lmall_circle_normal);
				}
			}
			circle_2_ll.setVisibility(View.GONE);
			circle_3_ll.setVisibility(View.GONE);
			circle_4_ll.setVisibility(View.GONE);
			circle_5_ll.setVisibility(View.GONE);
			// 初始化引导图片列表
			for (int i = 0; i < 9; i++) {
				ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
				View ll = LayoutInflater.from(context).inflate(R.layout.lmall_emoji_page_view, null);
				GridView gd = (GridView) ll.findViewById(R.id.emoji_gd);
				smail = EmojiUtils.getSmail(i);
				final String[] temp_smail = smail;
				for (int j = 0; j < smail.length; j++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("smail", smail[j]);
					al.add(map);
				}
				EmojiAdapter sa = new EmojiAdapter(context,
						al, R.layout.lmall_emoji_item, new String[] { "smail" },
						new int[] { R.id.image_iv });
				gd.setAdapter(sa);
				gd.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						// Log.v("tag", temp_smail[arg2] + "ff" + arg2);
						java.text.DecimalFormat format = new java.text.DecimalFormat("0000");
						setFace(temp_smail[arg2]);
					}
				});
				views.add(ll);
			}
			vpAdapter1 = new ViewPagerAdapter(views);
			viewpager1.setAdapter(vpAdapter1);
			viewpager1.setVisibility(View.VISIBLE);
			viewpager2.setVisibility(View.GONE);
			viewpager3.setVisibility(View.GONE);
			viewpager4.setVisibility(View.GONE);
			viewpager5.setVisibility(View.GONE);
		}else if (v==flowers_btn) {
			smail_btn.setBackgroundResource(R.drawable.lmall_smail_normal);
			flowers_btn.setBackgroundResource(R.drawable.lmall_flowers_select);
			bells_btn.setBackgroundResource(R.drawable.lmall_bells_normal);
			cars_btn.setBackgroundResource(R.drawable.lmall_cars_normal);
			numbers_btn.setBackgroundResource(R.drawable.lmall_number_normal);
			views = new ArrayList<View>();
			select_category = 1;
			circle_1_ll.setVisibility(View.GONE);
			circle_2_ll.setVisibility(View.VISIBLE);
			int child_2_count = circle_2_ll.getChildCount();
			for (int i = 0; i < child_2_count; i++) {
				ImageView circle = (ImageView) circle_2_ll.getChildAt(i);
				if (i == 0) {
					circle.setImageResource(R.drawable.lmall_circle_select);
				} else {
					circle.setImageResource(R.drawable.lmall_circle_normal);
				}
			}
			circle_3_ll.setVisibility(View.GONE);
			circle_4_ll.setVisibility(View.GONE);
			circle_5_ll.setVisibility(View.GONE);
			for (int i = 0; i < 6; i++) {
				ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
				View ll = LayoutInflater.from(context).inflate(
						R.layout.lmall_emoji_page_view, null);
				GridView gd = (GridView) ll.findViewById(R.id.emoji_gd);
				smail = EmojiUtils.getflowers(i);
				for (int j = 0; j < smail.length; j++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("smail", smail[j]);
					al.add(map);
				}
				EmojiAdapter sa = new EmojiAdapter(context,
						al, R.layout.lmall_emoji_item, new String[] { "smail" },
						new int[] { R.id.image_iv });
				gd.setAdapter(sa);
				final String[] temp_smail = smail;
				gd.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						java.text.DecimalFormat format = new java.text.DecimalFormat(
								"0000");
						setFace(temp_smail[arg2]);
					}
				});
				views.add(ll);
			}
			vpAdapter2 = new ViewPagerAdapter(views);
			viewpager2.setAdapter(vpAdapter2);
			viewpager1.setVisibility(View.GONE);
			viewpager2.setVisibility(View.VISIBLE);
			viewpager3.setVisibility(View.GONE);
			viewpager4.setVisibility(View.GONE);
			viewpager5.setVisibility(View.GONE);
		}else if (v==bells_btn) {
			smail_btn.setBackgroundResource(R.drawable.lmall_smail_normal);
			flowers_btn.setBackgroundResource(R.drawable.lmall_flowers_normal);
			bells_btn.setBackgroundResource(R.drawable.lmall_bells_select);
			cars_btn.setBackgroundResource(R.drawable.lmall_cars_normal);
			numbers_btn.setBackgroundResource(R.drawable.lmall_number_normal);
			views = new ArrayList<View>();
			select_category = 2;
			circle_1_ll.setVisibility(View.GONE);
			circle_2_ll.setVisibility(View.GONE);
			circle_3_ll.setVisibility(View.VISIBLE);
			int child_3_count = circle_3_ll.getChildCount();
			for (int i = 0; i < child_3_count; i++) {
				ImageView circle = (ImageView) circle_3_ll.getChildAt(i);
				if (i == 0) {
					circle.setImageResource(R.drawable.lmall_circle_select);
				} else {
					circle.setImageResource(R.drawable.lmall_circle_normal);
				}
			}
			circle_4_ll.setVisibility(View.GONE);
			circle_5_ll.setVisibility(View.GONE);
			for (int i = 0; i < 11; i++) {
				ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
				View ll = LayoutInflater.from(context).inflate(
						R.layout.lmall_emoji_page_view, null);
				GridView gd = (GridView) ll.findViewById(R.id.emoji_gd);
				smail = EmojiUtils.getBells(i);
				for (int j = 0; j < smail.length; j++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("smail", smail[j]);
					al.add(map);
				}
				EmojiAdapter sa = new EmojiAdapter(context,
						al, R.layout.lmall_emoji_item, new String[] { "smail" },
						new int[] { R.id.image_iv });
				gd.setAdapter(sa);
				final String[] temp_smail = smail;
				gd.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						java.text.DecimalFormat format = new java.text.DecimalFormat(
								"0000");
						setFace(temp_smail[arg2]);
					}
				});
				views.add(ll);
			}
			vpAdapter3 = new ViewPagerAdapter(views);
			viewpager3.setAdapter(vpAdapter3);
			viewpager1.setVisibility(View.GONE);
			viewpager2.setVisibility(View.GONE);
			viewpager3.setVisibility(View.VISIBLE);
			viewpager4.setVisibility(View.GONE);
			viewpager5.setVisibility(View.GONE);
		}else if (v==numbers_btn) {
			smail_btn.setBackgroundResource(R.drawable.lmall_smail_normal);
			flowers_btn.setBackgroundResource(R.drawable.lmall_flowers_normal);
			bells_btn.setBackgroundResource(R.drawable.lmall_bells_normal);
			cars_btn.setBackgroundResource(R.drawable.lmall_cars_normal);
			numbers_btn.setBackgroundResource(R.drawable.lmall_number_select);
			views = new ArrayList<View>();
			select_category = 4;
			circle_1_ll.setVisibility(View.GONE);
			circle_2_ll.setVisibility(View.GONE);
			circle_3_ll.setVisibility(View.GONE);
			circle_4_ll.setVisibility(View.GONE);
			circle_5_ll.setVisibility(View.VISIBLE);
			int child_5_count = circle_5_ll.getChildCount();
			for (int i = 0; i < child_5_count; i++) {
				ImageView circle = (ImageView) circle_5_ll.getChildAt(i);
				if (i == 0) {
					circle.setImageResource(R.drawable.lmall_circle_select);
				} else {
					circle.setImageResource(R.drawable.lmall_circle_normal);
				}
			}
			for (int i = 0; i < 10; i++) {
				ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
				View ll = LayoutInflater.from(context).inflate(
						R.layout.lmall_emoji_page_view, null);
				GridView gd = (GridView) ll.findViewById(R.id.emoji_gd);
				smail = EmojiUtils.getNumbers(i);
				for (int j = 0; j < smail.length; j++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("smail", smail[j]);
					al.add(map);
				}
				EmojiAdapter sa = new EmojiAdapter(context,
						al, R.layout.lmall_emoji_item, new String[] { "smail" },
						new int[] { R.id.image_iv });
				gd.setAdapter(sa);
				final String[] temp_smail = smail;
				gd.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						java.text.DecimalFormat format = new java.text.DecimalFormat(
								"0000");
						setFace(temp_smail[arg2]);
					}
				});
				views.add(ll);
			}
			vpAdapter4 = new ViewPagerAdapter(views);
			viewpager4.setAdapter(vpAdapter4);
			viewpager1.setVisibility(View.GONE);
			viewpager2.setVisibility(View.GONE);
			viewpager3.setVisibility(View.GONE);
			viewpager4.setVisibility(View.VISIBLE);
			viewpager5.setVisibility(View.GONE);
		}else if (v==cars_btn) {
			smail_btn.setBackgroundResource(R.drawable.lmall_smail_normal);
			flowers_btn.setBackgroundResource(R.drawable.lmall_flowers_normal);
			bells_btn.setBackgroundResource(R.drawable.lmall_bells_normal);
			cars_btn.setBackgroundResource(R.drawable.lmall_cars_select);
			numbers_btn.setBackgroundResource(R.drawable.lmall_number_normal);
			views = new ArrayList<View>();
			select_category = 3;
			circle_1_ll.setVisibility(View.GONE);
			circle_2_ll.setVisibility(View.GONE);
			circle_3_ll.setVisibility(View.GONE);
			circle_4_ll.setVisibility(View.VISIBLE);
			int child_4_count = circle_4_ll.getChildCount();
			for (int i = 0; i < child_4_count; i++) {
				ImageView circle = (ImageView) circle_4_ll.getChildAt(i);
				if (i == 0) {
					circle.setImageResource(R.drawable.lmall_circle_select);
				} else {
					circle.setImageResource(R.drawable.lmall_circle_normal);
				}
			}
			circle_5_ll.setVisibility(View.GONE);
			for (int i = 0; i < 5; i++) {
				ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
				View ll = LayoutInflater.from(context).inflate(
						R.layout.lmall_emoji_page_view, null);
				GridView gd = (GridView) ll.findViewById(R.id.emoji_gd);
				smail = EmojiUtils.getCars(i);
				for (int j = 0; j < smail.length; j++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("smail", smail[j]);
					al.add(map);
				}
				EmojiAdapter sa = new EmojiAdapter(context,
						al, R.layout.lmall_emoji_item, new String[] { "smail" },
						new int[] { R.id.image_iv });
				gd.setAdapter(sa);
				final String[] temp_smail = smail;
				gd.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						java.text.DecimalFormat format = new java.text.DecimalFormat(
								"0000");
						setFace(temp_smail[arg2]);
					}
				});
				views.add(ll);
			}
			vpAdapter5 = new ViewPagerAdapter(views);
			viewpager5.setAdapter(vpAdapter5);
			viewpager1.setVisibility(View.GONE);
			viewpager2.setVisibility(View.GONE);
			viewpager3.setVisibility(View.GONE);
			viewpager4.setVisibility(View.GONE);
			viewpager5.setVisibility(View.VISIBLE);
		}
		
			
	}
	
	public static class ViewPagerAdapter extends PagerAdapter {

		// 界面列表
		private List<View> views;

		public ViewPagerAdapter(List<View> views) {
			this.views = views;
		}

		// 销毁arg1位置的界面
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {

		}

		// 获得当前界面数
		@Override
		public int getCount() {
			if (views != null) {
				return views.size();
			}

			return 0;
		}

		// 初始化arg1位置的界面
		@Override
		public Object instantiateItem(View arg0, int arg1) {

			((ViewPager) arg0).addView(views.get(arg1), 0);

			return views.get(arg1);
		}

		// 判断是否由对象生成界面
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

	}

	public static class EmojiAdapter extends SimpleAdapter {
		private Context mContext;
		private List<? extends Map<String, ?>> al = new ArrayList<HashMap<String, Object>>();
		private LayoutInflater mlayoutInflater;

		public EmojiAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			al = data;
			mContext = context;
			mlayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view;
			if (convertView == null) {
				view = mlayoutInflater.inflate(R.layout.lmall_emoji_item, parent,
						false);
			} else {
				view = convertView;
			}
			final String smail = (String) al.get(position).get("smail");
			final ImageView image_iv = (ImageView) view
					.findViewById(R.id.image_iv);
			Bitmap cachedImage = BaseActivity.getAsyncImageLoaderInstance(mContext)
					.loadDrawable(Define.emoji_prefix + smail + ".png", smail,
							new ImageCallback11() {
								@Override
								public void imageLoaded(Bitmap imageDrawable,
										String tag, String imageUrl) {
									if (imageDrawable != null) {
										image_iv.setImageBitmap(imageDrawable);
									}
								}
							}, false);
			if (cachedImage == null) {

			} else {
				image_iv.setImageBitmap(cachedImage);
			}
			return view;
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int position) {
		
		LinearLayout circleLayout = null;
		
		switch (select_category) {
		case 0:
			circleLayout = circle_1_ll;
			break;
		case 1:
			circleLayout = circle_2_ll;
			break;
		case 2:
			circleLayout = circle_3_ll;
			break;
		case 3:
			circleLayout = circle_4_ll;
			break;
		case 4:
			circleLayout = circle_5_ll;
			break;
		}
		
		if(circleLayout!=null)
		{
			int childCount = circleLayout.getChildCount();
			for (int i = 0; i < childCount; i++) {
				ImageView circle = (ImageView) circleLayout.getChildAt(i);
				if (i == position) {
					circle.setImageResource(R.drawable.lmall_circle_select);
				} else {
					circle.setImageResource(R.drawable.lmall_circle_normal);
				}
			}
		}

	}

}

	
