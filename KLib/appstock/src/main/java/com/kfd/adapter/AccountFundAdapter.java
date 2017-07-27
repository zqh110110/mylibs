package com.kfd.adapter;

import java.util.ArrayList;



import com.kfd.activityfour.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccountFundAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private String[] array;
	private Context mContext;
	private ArrayList<String> array2;

	public AccountFundAdapter(Context context, String[] array,ArrayList<String> array2) {
		mContext = context;
		inflater = LayoutInflater.from(context);
		array2 = new ArrayList<String>();
		this.array = array;
		this.array2 = array2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return array[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.accountcentitem, null);
		TextView tv = (TextView) convertView.findViewById(R.id.text);
		TextView tv1 = (TextView) convertView.findViewById(R.id.text1);
		tv.setText(array[position]);
		if (array2.size()!=0) {
			
			tv1.setText(array2.get(position));
		}else {
			tv1.setText("0");
		}
		if (array.length == 1) {
			setBackgroundDrawable(convertView,
					R.drawable.list_round_selector);
		} else if (array.length == 2) {
			if (position == 0) {
				setBackgroundDrawable(convertView,
						R.drawable.list_top_selector);
			} else if (position == array.length - 1) {
				setBackgroundDrawable(convertView,
						R.drawable.list_bottom_selector);
			}
		} else {
			if (position == 0) {
				setBackgroundDrawable(convertView,
						R.drawable.list_top_selector);
			} else if (position == array.length - 1) {
				setBackgroundDrawable(convertView,
						R.drawable.list_bottom_selector);
			} else {
				setBackgroundDrawable(convertView,
						R.drawable.list_rect_selector);
			}
		}
		return convertView;
	}

	private void setBackgroundDrawable(View view, int resID) {
		view.setBackgroundDrawable(mContext.getResources().getDrawable(resID));
	}
}

