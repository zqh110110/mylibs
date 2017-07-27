package com.kfd.adapter;

import java.util.ArrayList;

import com.kfd.activityfour.ActiveGoFragement;
import com.kfd.activityfour.R;
import com.kfd.adapter.DiaryAdapter.ViewHodler;
import com.kfd.bean.ActiveBean;
import com.kfd.bean.Holiday;
import com.kfd.common.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActiveAdapter  extends  BaseAdapter{

	private ArrayList<ActiveBean>  arrayList  ;

	private LayoutInflater layoutInflater;
	ImageLoader  imageLoader;
	
	 private boolean   isgo;
	 ActiveGoFragement activeGoFragement;
	public ActiveAdapter(ArrayList<ActiveBean> arrayList, LayoutInflater layoutInflater,ImageLoader imageLoader,boolean isgo,ActiveGoFragement  activeGoFragement) {
		super();
		this.arrayList = arrayList;
		this.isgo  = isgo;
		this.activeGoFragement  = activeGoFragement;
		this.layoutInflater = layoutInflater;
		this.imageLoader = imageLoader;
	}
	public ActiveAdapter(ArrayList<ActiveBean> arrayList, LayoutInflater layoutInflater,ImageLoader imageLoader,boolean isgo) {
		super();
		this.arrayList = arrayList;
		this.isgo  = isgo;
	
		this.layoutInflater = layoutInflater;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
	.cacheInMemory(false).cacheOnDisk(true).build();
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHodler viewHodler = null;
		if (viewHodler==null) {
			viewHodler  = new ViewHodler();
			convertView  = layoutInflater.inflate(R.layout.youhuiitem, null);
			viewHodler.titleTextView  = (TextView) convertView.findViewById(R.id.textView1);
			viewHodler.imageView1 =  (ImageView) convertView.findViewById(R.id.imageView1);
  			viewHodler.timeTextView  =(TextView) convertView.findViewById(R.id.textView2);
			viewHodler.addButton  = (Button) convertView.findViewById(R.id.button1);
		    convertView.setTag(viewHodler);
		}else {
			viewHodler = (ViewHodler) convertView.getTag();
		}
		final ActiveBean  activeBean  = arrayList.get(position);
		viewHodler.titleTextView.setText(activeBean.getTitle());
		viewHodler.timeTextView.setText(StringUtils.phpdateformat(activeBean.getStarttime()));
		//viewHodler.endtimeTextView.setText("结束时间 :"+activeBean.get());
		imageLoader.displayImage(activeBean.getThumb(), viewHodler.imageView1,options);
		if (!isgo) {
			viewHodler.addButton.setText("已经结束");
			viewHodler.addButton.setBackgroundDrawable(layoutInflater.getContext().getResources().getDrawable(R.drawable.btnstyle_gray));
		}else {
			if (activeBean.getIsjoin().equals("1")) {
				viewHodler.addButton.setText("已经加入");
				viewHodler.addButton.setBackgroundDrawable(layoutInflater.getContext().getResources().getDrawable(R.drawable.btnstyle_gray));
			}else {
				viewHodler.addButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						activeGoFragement.attend(position, activeBean.getId());
					}
				});
			}
		}
		return convertView;
	}
	
	class ViewHodler{
		
		TextView timeTextView, titleTextView;
		Button  addButton;
		ImageView  imageView1;
	}
	

}