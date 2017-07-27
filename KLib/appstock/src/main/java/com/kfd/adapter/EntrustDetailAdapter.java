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
import com.kfd.bean.EntrustDetailBean;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

/**
 * 委托明细adapter
 * 
 * @author
 * 
 */
public class EntrustDetailAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<EntrustDetailBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

	public EntrustDetailAdapter(Context context,
			List<EntrustDetailBean> listItems, RelativeLayout mHead) {
		super();
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		this.mHead = mHead;
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
			convertView = listContainer.inflate(R.layout.listitem3, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			// 获取控件对象
			holder.stockename = (TextView) convertView
					.findViewById(R.id.stockname);
			holder.stockcode = (TextView) convertView
					.findViewById(R.id.stockcode);

			holder.entrusttime = (TextView) convertView
					.findViewById(R.id.entrusttime);
			holder.type = (TextView) convertView.findViewById(R.id.type);
			holder.entrustprice = (TextView) convertView
					.findViewById(R.id.entrustprice);
			holder.lowprice = (TextView) convertView
					.findViewById(R.id.lowprice);
			holder.highprice = (TextView) convertView
					.findViewById(R.id.highprice);
			holder.recentprice = (TextView) convertView
					.findViewById(R.id.recentprice);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.number = (TextView) convertView.findViewById(R.id.number);

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
		EntrustDetailBean bean = listItems.get(position);

		holder.stockename.setText(bean.getStock_name());
		holder.stockcode.setText(bean.getStockcode());
		holder.stockename.setText(bean.getStock_name());
		holder.entrusttime.setText(bean.getCreate_time());
		holder.type.setText(bean.getType());
		holder.entrustprice.setText(bean.getCreate_price());
		holder.lowprice.setText(bean.getLow_price());
		holder.highprice.setText(bean.getHigh_price());
		holder.recentprice.setText(bean.getNowprice());
		holder.count.setText(bean.getNum());
		holder.number.setText(bean.getNumber());

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
		TextView stockename, stockcode;
		TextView entrusttime;
		TextView type;
		TextView entrustprice;
		TextView lowprice;
		TextView highprice;
		TextView recentprice;
		TextView count;
		TextView number;

		HorizontalScrollView scrollView;
	}

}