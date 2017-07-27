package com.kfd.adapter;

import java.util.ArrayList;

import com.kfd.activityfour.R;
import com.kfd.common.LogUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EmailMessageAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<String> arrayList1,arrayList2;
	private Context mContext;

	public EmailMessageAdapter(Context context, ArrayList<String> array,ArrayList<String> array2) {
		mContext = context;
		arrayList1 = new ArrayList<String>();
		arrayList2 = new ArrayList<String>();
		inflater = LayoutInflater.from(context);
		this.arrayList1 = array;
		this.arrayList2 = array2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList1.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList1.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.messageitem, null);
		TextView tv = (TextView) convertView.findViewById(R.id.text);
		TextView tv2 = (TextView) convertView.findViewById(R.id.text2);
		tv.setText(arrayList1.get(position));
		tv2.setText(arrayList2.get(position));
		return convertView;
	}

	private void setBackgroundDrawable(View view, int resID) {
		view.setBackgroundDrawable(mContext.getResources().getDrawable(resID));
	}
}

