package com.kfd.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kfd.activityfour.R;
import com.kfd.adapter.EntrustDetailAdapter.OnScrollChangedListenerImp;
import com.kfd.adapter.EntrustDetailAdapter.ViewHolder;
import com.kfd.bean.EntrustDetailBean;
import com.kfd.bean.FuturesEntrustDetailBean;
import com.kfd.common.LogUtils;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class FuturesEntrustDetailAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<FuturesEntrustDetailBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();
	private String type;
	public FuturesEntrustDetailAdapter(Context context,
			List<FuturesEntrustDetailBean> listItems, RelativeLayout mHead,String type) {
		super();
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		this.mHead = mHead;
		this.type = type;
	}

	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return listItems.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		// 自定义视图
		ViewHolder holder = null;
		if (convertView == null) {
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.listitem14, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			// 获取控件对象
			holder.t1 = (TextView) convertView
					.findViewById(R.id.t1);
			holder.t2 = (TextView) convertView
					.findViewById(R.id.t2);

			holder.t3 = (TextView) convertView
					.findViewById(R.id.t3);
			holder.t4 = (TextView) convertView.findViewById(R.id.t4);
			holder.t5 = (TextView) convertView
					.findViewById(R.id.t5);
			holder.t6 = (TextView) convertView
					.findViewById(R.id.t6);
			holder.t7 = (TextView) convertView
					.findViewById(R.id.t7);
			holder.t8 = (TextView) convertView
					.findViewById(R.id.t8);
			holder.t9 = (TextView) convertView.findViewById(R.id.t9);
			
			holder.t10 = (TextView) convertView
					.findViewById(R.id.t10);
			holder.t11 = (TextView) convertView.findViewById(R.id.t11);


			// 设置表头滑动

			MyHScrollView headSrcrollView = (MyHScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			headSrcrollView
					.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
							scrollView1));

			// 设置控件集到convertView
			convertView.setTag(holder);
			mHolderList.add(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/*
		 * if (position % 2 == 0) {
		 * convertView.setBackgroundResource(R.color.gray); } else {
		 * convertView.setBackgroundResource(R.color.white); }
		 */
		FuturesEntrustDetailBean bean = listItems.get(position);
	
		holder.t1.setText(bean.getCreate_time());

		holder.t2.setText(bean.getFuturescode());
		holder.t3.setText(bean.getType());

		holder.t4.setText(bean.getCreate_price());
		
		holder.t5.setText(bean.getLow_price());
		holder.t6.setText(bean.getHigh_price());
		holder.t7.setText(bean.getNowprice());
		holder.t8.setText(bean.getNum());
		holder.t9.setText(bean.getWt_create_status());
		if (this.type!=null && this.type.toString().equals("close")) {
			holder.t10.setVisibility(View.VISIBLE);
			holder.t11.setVisibility(View.VISIBLE);
			LogUtils.v("ssss", "==================close");
			holder.t4.setText(bean.getCreate_price());
			holder.t5.setText(bean.getWt_closeprice());
			holder.t6.setText(bean.getLow_price());
			holder.t7.setText(bean.getHigh_price());
			holder.t8.setText(bean.getNowprice());
			holder.t9.setText(bean.getNum());
			holder.t10.setText(bean.getCloseout_time());
			holder.t11.setText(bean.getWt_create_status());
		}

		return convertView;
	}

	class OnScrollChangedListenerImp implements OnScrollChangedListener {
		MyHScrollView mScrollViewArg;

		public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
			mScrollViewArg = scrollViewar;
		}

		@Override
		public void onScrollChanged(int l, int t, int oldl, int oldt) {
			mScrollViewArg.smoothScrollTo(l, t);
		}
	};

	class ViewHolder {// 自定义控件集合
		TextView t1, t2;
		TextView t3;
		TextView t4;
		TextView t5;
		TextView t6;
		TextView t7;
		TextView t8;
		TextView t9;
		TextView t10;
		TextView t11;
	

		HorizontalScrollView scrollView;
	}

}