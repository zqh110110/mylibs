package com.kfd.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.kfd.activityfour.MessageSetActivity.ItemData;
import com.kfd.activityfour.R;
import com.kfd.api.AppContext;

/**
 * 消息状态
 * @author Administrator
 *
 */
public class MsgSettingAdapter extends BaseAdapter{
	
//	private Context mContext;
	
	private LayoutInflater mInflater;
	
	private List<ItemData> mdatas;
	
	public MsgSettingAdapter(Context context, List<ItemData> datas)
	{
//		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.mdatas = datas;
	}

	@Override
	public int getCount() {
		return this.mdatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return this.mdatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		Holder holder = null;
		
		if(convertView == null)
		{
			holder = new Holder();
			convertView = mInflater.inflate(R.layout.msg_setting_item, null);
			holder.toggleButton = (ToggleButton) convertView.findViewById(R.id.switch1);
			holder.textView = (TextView) convertView.findViewById(R.id.textView);
			holder.divider =  convertView.findViewById(R.id.divider);
			convertView.setTag(holder);
		}
		else
		{
			holder = (Holder) convertView.getTag();
		}	
		
		final ItemData data = this.mdatas.get(position);
		
		holder.divider.setVisibility((position == mdatas.size() - 1) ? View.GONE : View.VISIBLE);
		
		holder.toggleButton.setChecked(data.isChecked);
		holder.textView.setText(data.title);
		
		holder.toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				data.isChecked = arg1;
				mdatas.set(position, data);
				
				MsgSettingAdapter.this.notifyDataSetChanged();
				
				AppContext.getInstance().setMsgState(mdatas.get(0).isChecked, mdatas.get(1).isChecked,
						mdatas.get(2).isChecked, mdatas.get(3).isChecked, mdatas.get(4).isChecked);
			}
		});
		
		return convertView;
	}

	private class Holder
	{
		ToggleButton toggleButton;
		
		TextView textView;
		
		View divider;
	}
}
