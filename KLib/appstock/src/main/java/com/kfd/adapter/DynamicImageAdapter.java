/*
 * Copyright (c) 2015 The MaMaHelp_small_withReceiver_6.9 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */

package com.kfd.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupWindow;

import com.kfd.activityfour.InvestListActivity;
import com.kfd.activityfour.R;
import com.kfd.api.Tools;
import com.kfd.bean.InvestBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @Function: TODO ADD FUNCTION.
 * @author zhu
 * @version
 * @Date: 2015年6月19日 下午4:42:17
 */
public class DynamicImageAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<InvestBean.PicList> arrayList;
	private ImageLoader imageLoader;

	public DynamicImageAdapter(Context context, ArrayList<InvestBean.PicList> arrayList, ImageLoader imageLoader) {
		super();
		this.context = context;
		this.arrayList = arrayList;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.dynamicimageitem, null);
			holder = new ViewHolder();
			holder.photoIV = (ImageView) convertView.findViewById(R.id.message_board_photo_iv);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(arrayList.get(position).getThumb(), holder.photoIV, options);
		/*
		 * imageLoader.loadImage(arrayList.get(position).getPicture(), options,
		 * new ImageLoadingListener() {
		 * 
		 * @Override public void onLoadingStarted(String arg0, View arg1) {
		 * 
		 * // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onLoadingFailed(String arg0, View arg1,
		 * FailReason arg2) {
		 * 
		 * // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onLoadingComplete(String arg0, View arg1,
		 * Bitmap arg2) {
		 * 
		 * // TODO Auto-generated method stub
		 * holder.photoIV.setBackgroundDrawable(new BitmapDrawable(arg2)); }
		 * 
		 * @Override public void onLoadingCancelled(String arg0, View arg1) {
		 * 
		 * // TODO Auto-generated method stub
		 * 
		 * } });
		 */
		// imageLoader.displayImage(arrayList.get(position).getPicture(),
		// holder.photoIV , options);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final PopupWindow mPopupWindow = new PopupWindow(context);
				
			    ColorDrawable dw = new ColorDrawable(0xb0000000);
			    mPopupWindow.setBackgroundDrawable(dw);
				
				ImageView imageView = new ImageView(context);
//				imageView.setBackgroundColor(Color.parseColor("#aa000000"));
				imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				imageView.setScaleType(ScaleType.FIT_CENTER);
				imageLoader.displayImage(arrayList.get(position).getThumb(), imageView, options);
				mPopupWindow.setContentView(imageView);
				mPopupWindow.setWidth(Tools.getScreenSize(context).x);
				mPopupWindow.setHeight(Tools.getScreenSize(context).y);

				mPopupWindow.setFocusable(true);

				mPopupWindow.getContentView().setOnTouchListener(new OnTouchListener() {

					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_UP) {
							mPopupWindow.setFocusable(false);
							mPopupWindow.dismiss();
						}
						return true;
					}
				});

				mPopupWindow.setOutsideTouchable(false);

				mPopupWindow.showAtLocation(((InvestListActivity) context).getView(), Gravity.CENTER, 0, 0);
			}
		});
		return convertView;
	}

	public static DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(true).build();

	class ViewHolder {
		ImageView photoIV;
	}

}
