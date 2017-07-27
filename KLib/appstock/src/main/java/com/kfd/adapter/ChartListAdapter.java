package com.kfd.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kfd.activityfour.R;
import com.kfd.bean.ChartListData;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChartListAdapter extends BaseAdapter
{

	private Context context;

	private LayoutInflater mInflater;

	private List<ChartListData> mDatas;
	
	private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(true).build();

	public ChartListAdapter(Context context, List<ChartListData> mDatas)
	{
		this.context = context;
		this.mDatas = mDatas;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		return mDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0)
	{
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{
		Holder holder = null;
		if (arg1 == null)
		{
			holder = new Holder();
			arg1 = mInflater.inflate(R.layout.chart_item, null);
			holder.text_View = (TextView) arg1.findViewById(R.id.text_View);
			holder.name_view = (TextView) arg1.findViewById(R.id.name_text);
			holder.time_view = (TextView) arg1.findViewById(R.id.time_text);
			holder.count_view = (TextView) arg1.findViewById(R.id.count_View);
			holder.headView = (ImageView) arg1.findViewById(R.id.head_view);
			holder.divider =  arg1.findViewById(R.id.divider);
			arg1.setTag(holder);
		} else
		{
			holder = (Holder) arg1.getTag();
		}

		ChartListData data = mDatas.get(arg0);
		holder.text_View.setText(data.getLastmsg());
		
		holder.name_view.setText(data.getNickname());
		holder.time_view.setText(StringUtils.phpdateformat7(data.getDateline()));
		
		if(data.getNewnum() == null || TextUtils.isEmpty(data.getNewnum()) || data.getNewnum().equals("0"))
		{
			holder.count_view.setVisibility(View.GONE);
		}
		else
		{
			holder.count_view.setVisibility(View.VISIBLE);
			holder.count_view.setText("+" + data.getNewnum());
		}
		
//		holder.divider.setVisibility(arg0 == mDatas.size() - 1 ? View.INVISIBLE : View.VISIBLE); 
		
		ImageLoader.getInstance().displayImage(Define.host + data.getFace(), holder.headView, options);
		return arg1;
	}

	private class Holder
	{
		TextView text_View, name_view, time_view, count_view;

		ImageView headView;
		
		View divider;
	}

}
