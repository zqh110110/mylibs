package com.kfd.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kfd.activityfour.R;

public class OnlineAdapter extends BaseAdapter {
	private ArrayList<Integer> arrayList;
	private Context context;
	private LayoutInflater layoutInflater;

	public OnlineAdapter(ArrayList<Integer> arrayList, Context context,
			LayoutInflater layoutInflater) {
		super();
		this.arrayList = arrayList;
		this.context = context;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.online_items, null);

		} else {

		}

		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.imageView1);
		imageView.setImageResource(arrayList.get(position));

		return convertView;
	}
}