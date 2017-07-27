package com.kfd.adapter;

import java.util.ArrayList;

import com.kfd.activityfour.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class TryoutReportBankChoiceAdapter extends  BaseAdapter {
	Context  context;
	private ArrayList<TryoutBankInfo>  arrayList;

	public TryoutReportBankChoiceAdapter(Context context,ArrayList<TryoutBankInfo>  arrayList) {
		super();
		this.context = context;
		this.arrayList = arrayList;
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


	public View getView(final int position, View convertView,
			final ViewGroup parent) {

			
		TryoutBankInfo  bankInfo =arrayList.get(position);
			final ViewHodler holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.lmall_tryout_bank_item, null);
				holder = new ViewHodler();
				holder.bankNameTextView = (TextView) convertView.findViewById(R.id.banktextView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHodler) convertView.getTag();
			}
			if (bankInfo!=null) {
				holder.bankNameTextView.setText(bankInfo.getName());
				
			}
	
		return convertView;
	}
	class   ViewHodler{
		TextView  bankNameTextView;
	}
}
