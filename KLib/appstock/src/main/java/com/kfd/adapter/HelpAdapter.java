package com.kfd.adapter;

import java.util.ArrayList;

import com.kfd.activityfour.R;
import com.kfd.adapter.MessageListAdapter.ViewHodler;
import com.kfd.bean.HelpBean;
import com.kfd.bean.MessageBean;
import com.kfd.common.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpAdapter   extends BaseAdapter{
	private ArrayList<HelpBean>  arrayList  ;
	private Context  context;
	private LayoutInflater layoutInflater;
	ImageLoader  imageLoader;
	 
	public HelpAdapter(ArrayList<HelpBean> arrayList, Context context,LayoutInflater layoutInflater) {
		super();
		this.arrayList = arrayList;
		this.context = context;
		this.layoutInflater = layoutInflater;
		imageLoader = imageLoader.getInstance();
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
		ViewHodler viewHodler = null;
		if (viewHodler==null) {
			viewHodler  = new ViewHodler();
			convertView  = layoutInflater.inflate(R.layout.helpitem, null);
			viewHodler.titleTextView  = (TextView) convertView.findViewById(R.id.text);
			viewHodler.contentTextView  = (TextView) convertView.findViewById(R.id.text2);
			

		    convertView.setTag(viewHodler);
		}else {
			viewHodler = (ViewHodler) convertView.getTag();
		}
		HelpBean  countryBean  = arrayList.get(position);
		viewHodler.titleTextView.setText(countryBean.getTitle());
		viewHodler.contentTextView.setText(countryBean.getContent());
		
		
		
		return convertView;
	}
	
	class ViewHodler{
		
		TextView contentTextView, titleTextView;
	
	}
	


}

