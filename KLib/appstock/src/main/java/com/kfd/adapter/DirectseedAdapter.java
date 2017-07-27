package com.kfd.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kfd.activityfour.R;
import com.kfd.bean.KuaiXun;
import com.kfd.common.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DirectseedAdapter extends BaseAdapter
{
	private ArrayList<KuaiXun> arrayList;
	private Context context;
	private LayoutInflater layoutInflater;

	public static DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(true).build();

	public DirectseedAdapter(ArrayList<KuaiXun> arrayList, Context context, LayoutInflater layoutInflater)
	{
		super();
		this.arrayList = arrayList;
		this.context = context;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount()
	{
		return arrayList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHodler viewHodler = null;

		KuaiXun kuaiXun = arrayList.get(position);
		boolean hasPicture = kuaiXun.getPicture() != null && !TextUtils.isEmpty(kuaiXun.getPicture());

		if (convertView == null)
		{
			viewHodler = new ViewHodler();

			convertView = layoutInflater.inflate(R.layout.directseeditem, null);
			viewHodler.image_view = (ImageView) convertView.findViewById(R.id.image_view);

			viewHodler.timetextView = (TextView) convertView.findViewById(R.id.textView1);
			viewHodler.dateTextView = (TextView) convertView.findViewById(R.id.textView2);
			viewHodler.contentTextView = (TextView) convertView.findViewById(R.id.contenttextView);
			viewHodler.textView = (TextView) convertView.findViewById(R.id.text2);

			convertView.setTag(viewHodler);
		} else
		{
			viewHodler = (ViewHodler) convertView.getTag();
		}

		viewHodler.timetextView.setText(StringUtils.phpdateformat7(kuaiXun.getDateline()));
		viewHodler.dateTextView.setText(StringUtils.phpdateformat6(kuaiXun.getDateline()));
		viewHodler.contentTextView.setText(kuaiXun.getContent());
		
		if(kuaiXun.getDatainfo() != null && !TextUtils.isEmpty(kuaiXun.getDatainfo()))
		{
			viewHodler.textView.setVisibility(View.VISIBLE);
			
			if(kuaiXun.getDatacolor() != null && !TextUtils.isEmpty(kuaiXun.getDatacolor()))
			{
				try
				{
					viewHodler.textView.setTextColor(Color.parseColor(kuaiXun.getDatacolor()));
				}catch(Exception e)
				{
				}
			}
			
			if(kuaiXun.getDatapic() != null && !TextUtils.isEmpty(kuaiXun.getDatapic()))
			{
				ImageLoader.getInstance().loadImage(kuaiXun.getDatapic(), new ImageLoadingListener() {
					
					@Override
					public void onLoadingStarted(String arg0, View arg1) {
					}
					
					@Override
					public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					}
					
					@Override
					public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					}
					
					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
						
					}
				});
			}
		}
		else
		{
			viewHodler.textView.setVisibility(View.GONE);
		}

		if (kuaiXun.getIsbold() != null && kuaiXun.getIsbold().equals("1"))
		{
			TextPaint tp = viewHodler.contentTextView.getPaint();
			tp.setFakeBoldText(true);
		} else
		{
			TextPaint tp = viewHodler.contentTextView.getPaint();
			tp.setFakeBoldText(false);
		}

		if (kuaiXun.getColor() != null && !kuaiXun.equals(""))
		{
			try
			{
				viewHodler.contentTextView.setTextColor(Color.parseColor(kuaiXun.getColor()));
			} catch (Exception e)
			{
				viewHodler.contentTextView.setTextColor(Color.parseColor("#6d6d6d"));
			}
		} else
		{
			viewHodler.contentTextView.setTextColor(Color.parseColor("#6d6d6d"));
		}

		if (hasPicture)
		{
			viewHodler.image_view.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(kuaiXun.getPicture(), viewHodler.image_view, options);
		}
		else
		{
			viewHodler.image_view.setVisibility(View.GONE);
		}

		return convertView;
	}

	class ViewHodler
	{
		TextView timetextView, dateTextView, contentTextView, textView;

		ImageView image_view;
	}

}
