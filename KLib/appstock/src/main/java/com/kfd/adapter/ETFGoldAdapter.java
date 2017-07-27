package com.kfd.adapter;

import java.util.ArrayList;

import u.aly.v;

import com.kfd.activityfour.R;
import com.kfd.bean.ETFPostionBean;
import com.kfd.common.StringUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ETFGoldAdapter  extends  BaseAdapter{
	private ArrayList<ETFPostionBean> arrayList;
	private Context context;
	private LayoutInflater inflater;
	
	public ETFGoldAdapter(ArrayList<ETFPostionBean> arrayList, Context context,
			LayoutInflater inflater) {
		super();
		this.arrayList = arrayList;
		this.context = context;
		this.inflater = inflater;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if (viewHolder==null) {
			viewHolder= new ViewHolder();
			convertView  = inflater.inflate(R.layout.etfitem,null);
			viewHolder.numsTextView = (TextView) convertView.findViewById(R.id.textView2);
			viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.textView1);
			viewHolder.amountTextView = (TextView) convertView.findViewById(R.id.textView3);
		}else {
			viewHolder= (ViewHolder) convertView.getTag();
		}
		ETFPostionBean etfPostionBean = arrayList.get(position);
		viewHolder.amountTextView .setText(etfPostionBean.getChange_nums());
		viewHolder.dateTextView.setText(StringUtils.phpdateformat(etfPostionBean.getDateline()));
		viewHolder.numsTextView.setText(etfPostionBean.getNums());
		if (etfPostionBean.getChange_nums().startsWith("-")) {
			viewHolder.amountTextView.setTextColor(Color.parseColor("#53b84e"));
		}else if (etfPostionBean.getChange_nums().startsWith("+")) {
			viewHolder.amountTextView.setTextColor(Color.parseColor("#fc4441"));
		}else {
			viewHolder.amountTextView.setTextColor(Color.parseColor("#b3b3b3"));
		}
		return convertView;
	}
	class  ViewHolder{
		private TextView numsTextView,dateTextView,amountTextView;
	}
	
}
