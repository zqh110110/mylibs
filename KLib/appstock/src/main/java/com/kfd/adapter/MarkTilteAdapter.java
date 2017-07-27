package com.kfd.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.kfd.activityfour.MarkActivity;
import com.kfd.activityfour.R;
import com.kfd.common.DensityUtil;
import com.kfd.entity.MarkPrice;

public class MarkTilteAdapter extends BaseAdapter{

	private List<MarkPrice> mDatas;
	
	private LayoutInflater mInflater;
	
	private SparseBooleanArray stateMap = new SparseBooleanArray();
	
	private int itemWidth = 0;
	
	private int mSelectedIndex = 0;
	
	public MarkTilteAdapter(Context context, List<MarkPrice> datas) {
		mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
		initStates(0);

		WindowManager windowManager = ((Activity)mInflater.getContext()).getWindowManager();
		int width = windowManager.getDefaultDisplay().getWidth();
		itemWidth = (width - 1) / 3;
	}
	
	private void initStates(int position)
	{
		for (int i = 0; i < mDatas.size(); i++) {
			stateMap.put(i, position == i ? true : false);
			
			if(position == i)
			{
				mSelectedIndex = i;
			}
		}
	}
	
	public  int getSelectedIndex()
	{
		return mSelectedIndex;
	}
	
	public void setSelectedAt(int position) 
	{
		initStates(position);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mDatas.size();
	}
	
	public MarkPrice getSelectedItem() {
		
		return  mDatas.get(mSelectedIndex);
	}

	@Override
	public MarkPrice getItem(int arg0) {
		
		return  mDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder holder = null;
		if(arg1 == null)
		{
			holder = new Holder();
			arg1 = mInflater.inflate(R.layout.mark_title_item, null);
			holder.textView1 = (TextView) arg1.findViewById(R.id.hsnametextView);
			holder.textView2 = (TextView) arg1.findViewById(R.id.hspricetextView);
			holder.textView3 = (TextView) arg1.findViewById(R.id.hsrangetextView);
			holder.icon = (ImageView) arg1.findViewById(R.id.hsrangeimageview);
			holder.contentLayout = (LinearLayout) arg1.findViewById(R.id.content_layout);
			
			holder.bottomLine =  arg1.findViewById(R.id.hsline);
			
			LayoutParams params = new LayoutParams(itemWidth, DensityUtil.dip2px(mInflater.getContext(), 69));
			holder.contentLayout.setLayoutParams(params);
			
			android.view.ViewGroup.LayoutParams params2 = holder.bottomLine.getLayoutParams();
			if(params2 == null)
			{
				params2 = new LayoutParams(itemWidth, DensityUtil.dip2px(mInflater.getContext(), 0.5f));
			}
			else
			{
				params2.width = itemWidth;
				params2.height = DensityUtil.dip2px(mInflater.getContext(), 0.5f);
			}
			holder.bottomLine.setLayoutParams(params2);
			
			arg1.setTag(holder);
		}
		else
		{
			holder = (Holder) arg1.getTag();
		}
		
		MarkPrice data = mDatas.get(arg0);
		
		holder.textView1.setText(data.getName());
		
		holder.textView2.setText(data.getPrice());
		holder.textView3.setText(data.getChange_pro());
		
		
		if (data.getChange_pro().startsWith("-"))
		{
			holder.icon.setImageDrawable(mInflater.getContext().getResources().getDrawable(R.drawable.down_ico));
			holder.textView2.setTextColor(Color.parseColor("#53b84e"));
			holder.textView3.setTextColor(Color.parseColor("#53b84e"));

		} else
		{
			holder.icon.setImageDrawable(mInflater.getContext().getResources().getDrawable(R.drawable.up_ico));

			holder.textView2.setTextColor(Color.parseColor("#fc423e"));
			holder.textView3.setTextColor(Color.parseColor("#fc423e"));
		}
		
		holder.bottomLine.setBackgroundColor(Color.parseColor(stateMap.get(arg0) ? "#b12a31" : "#c3c3c3") );
		
		return arg1;
	}
	
	private class Holder
	{
		TextView textView1,textView2, textView3;
		
		ImageView icon;
		
		View bottomLine;
		
		LinearLayout contentLayout;
	}

}
