/*
 * Copyright (c) 2014 The MaMaHelp_7_27 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */

package com.kfd.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.conn.Wire;

import com.kfd.activityfour.BaseActivity;
import com.kfd.activityfour.R;
import com.kfd.activityfour.WriteInvestActivity;
import com.kfd.bean.Images;
import com.kfd.bean.MessageBoardPicture;
import com.kfd.common.CallbackImpl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;



/**
 * @Function:附近留言板图片
 * @author xiaobo.lin
 * @version
 * @Date: 2014-4-4 下午4:59:31
 */
public class MessageBoardPhotoAddAdapter extends BaseAdapter {
	Context mContext;
	Activity activity;
	private List<Images> myPictures;

	private List<MessageBoardPicture> pics;
	public MessageBoardPhotoAddAdapter(Context context, Activity activity,
			List<Images> myPictures) {
		mContext = context;
		this.activity = activity;
		this.myPictures = myPictures;
	
		pics= new ArrayList<MessageBoardPicture>();
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		if (position < myPictures.size()) {
			return myPictures.get(position);
		} else {
			return position;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		final ViewHolder holder;
		if (convertView == null) {
			view = View.inflate(mContext,
					R.layout.message_board_photo_add_gv_items, null);
			holder = new ViewHolder();
			holder.photoIV = (ImageView) view
					.findViewById(R.id.photo_iv);
			holder.photoDeleteIV= (ImageView) view
					.findViewById(R.id.photo_delete_iv);
			holder.photoDeleteIV.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					WriteInvestActivity.imageChose.remove(myPictures
							.get(position));
//					myPictures.remove(myPictures
//							.get(position));
					if(WriteInvestActivity.imageChose.size()>0){
						
						WriteInvestActivity.photoNumBtn.setVisibility(View.VISIBLE);
						WriteInvestActivity.photoNumBtn.setText(WriteInvestActivity.imageChose.size()+"");
					}else{
						WriteInvestActivity.photoNumBtn.setVisibility(View.GONE);
					}
					notifyDataSetChanged();
//					Intent intent = new Intent(mContext, GeRenZiLiao.class);
//					intent.putExtra("uid", msgBoardDetailComment.getUid());
//					mContext.startActivity(intent);

				}
			});
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		if (position < myPictures.size()) {
			holder.photoDeleteIV.setVisibility(View.VISIBLE);
			Images images = myPictures.get(position);
			if (images != null && images.getThumbnails() != null) {
				CallbackImpl callbackImpl = new CallbackImpl(holder.photoIV);
				Bitmap cacheImage = BaseActivity.loaders.loadDrawable(images.get_data(), callbackImpl);
				if (cacheImage != null) {
					holder.photoIV.setBackgroundDrawable(new BitmapDrawable(cacheImage));
//					holder.photoIV.setBackgroundDrawable(new BitmapDrawable(cacheImage));
				}
			}else if(images != null){
				CallbackImpl callbackImpl = new CallbackImpl(holder.photoIV);
				Bitmap cacheImage = BaseActivity.loaders.loadDrawable(images.get_data(), callbackImpl);
				if (cacheImage != null) {
					holder.photoIV.setBackgroundDrawable(new BitmapDrawable(cacheImage));
//					holder.photoIV.setBackgroundDrawable(new BitmapDrawable(cacheImage));
				}
			}
		}else{
			holder.photoDeleteIV.setVisibility(View.GONE);
			holder.photoIV
			.setBackgroundDrawable(activity
					.getResources().getDrawable(R.drawable.btn_msgboard_add));
		}

		

		return view;
	}

	static class ViewHolder {
		ImageView photoIV,photoDeleteIV;
	}

}
