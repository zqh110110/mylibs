/*
 * Copyright (c) 2014 The MaMaHelp_small_withReceiver_6.0.0 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */

package com.kfd.adapter;

import java.util.List;


import com.kfd.activityfour.R;
import com.kfd.bean.Cityinfo;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @Function: TODO ADD FUNCTION.
 * @author xiaobo.lin
 * @version
 * @Date: 2014年8月7日 上午11:09:36
 */
public class MallSelectProvAdapter extends BaseAdapter {
	private List<Cityinfo> list;
	private Context context;
	private LayoutInflater mInflater;

	public MallSelectProvAdapter(List<Cityinfo> list, Context context) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = mInflater.inflate(R.layout.lmall_mall_select_prov_list_item,
				null);
		TextView text = (TextView) convertView.findViewById(R.id.text);
		text.setText(list.get(position).getCity_name());
		return convertView;

	}

}
