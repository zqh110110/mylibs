package com.kfd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kfd.activityfour.R;

public class MainGridviewAdapter extends BaseAdapter {
	private Context context;
	private final String[] labelStrings;
	private final int[] imamgeitem;

	public MainGridviewAdapter(Context context, String[] labelStrings,
			int[] imageitem) {
		this.context = context;
		this.labelStrings = labelStrings;
		this.imamgeitem = imageitem;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ViewHolder holder = null;

		if (convertView == null) {

			holder = new ViewHolder();

			// get layout
			convertView = inflater.inflate(R.layout.gridviewitem, null);

			// set value into textview
			holder.textView = (TextView) convertView
					.findViewById(R.id.grid_item_label);

			holder.imageView = (ImageView) convertView
					.findViewById(R.id.grid_item_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textView.setText(labelStrings[position]);
		holder.imageView.setImageResource(imamgeitem[position]);

		return convertView;
	}

	@Override
	public int getCount() {
		return labelStrings.length;
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
		private ImageView imageView;
		private TextView textView;
	}

}
