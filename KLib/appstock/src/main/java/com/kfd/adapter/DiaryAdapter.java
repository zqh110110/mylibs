package com.kfd.adapter;

import java.util.ArrayList;

import com.kfd.activityfour.R;
import com.kfd.adapter.FianceCountryAdapter.ViewHodler;
import com.kfd.bean.FinaceCountryBean;
import com.kfd.bean.Holiday;
import com.kfd.common.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DiaryAdapter extends BaseAdapter{

	private ArrayList<Holiday>  arrayList  ;
	private Context  context;
	private LayoutInflater layoutInflater;
	ImageLoader  imageLoader;
	 
	public DiaryAdapter(ArrayList<Holiday> arrayList, Context context,LayoutInflater layoutInflater) {
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
			convertView  = layoutInflater.inflate(R.layout.finacecountryitem, null);
			viewHodler.titleTextView  = (TextView) convertView.findViewById(R.id.textView1);
			viewHodler.countryTextView  = (TextView) convertView.findViewById(R.id.textView3);
			viewHodler.timeTextView  =(TextView) convertView.findViewById(R.id.textView2);

		    convertView.setTag(viewHodler);
		}else {
			viewHodler = (ViewHodler) convertView.getTag();
		}
		Holiday  countryBean  = arrayList.get(position);
		viewHodler.titleTextView.setText(countryBean.getContent());
		viewHodler.timeTextView.setText(StringUtils.phpdateformat(countryBean.getTime()));
		viewHodler.countryTextView.setText("影响 :"+countryBean.getCountry());
		
		
		return convertView;
	}
	
	class ViewHodler{
		
		TextView timeTextView, titleTextView,countryTextView;
	}
	

}
