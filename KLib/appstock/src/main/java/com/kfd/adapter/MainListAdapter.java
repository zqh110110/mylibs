package com.kfd.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kfd.activityfour.R;
import com.kfd.bean.MainMesageBean;
import com.kfd.common.StringUtils;

/**
 * 主页显示adapter
 * 
 * @author 朱继洋 QQ7617812 2013-7-2
 */
public class MainListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<MainMesageBean> arrayList;

	public MainListAdapter(Context context, ArrayList<MainMesageBean> arrayList) {
		super();
		this.context = context;
		this.arrayList = arrayList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.mainitem, null);
			holder.textView = (TextView) convertView
					.findViewById(R.id.textView1);
			holder.textView2 = (TextView) convertView
					.findViewById(R.id.textView2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MainMesageBean mainMesageBean = arrayList.get(position);
	
		holder.textView.setText(StringUtils.ToDBC(mainMesageBean.getTitle()));
		holder.textView2.setText(mainMesageBean.getAdd_time());
		return convertView;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {

		private TextView textView, textView2;
	}

}
