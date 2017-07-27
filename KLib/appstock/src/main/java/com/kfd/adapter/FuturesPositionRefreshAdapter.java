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
import com.kfd.adapter.PositionRefreshAdapter.OnScrollChangedListenerImp;
import com.kfd.adapter.PositionRefreshAdapter.ViewHolder;
import com.kfd.bean.FuturesPositionBean;
import com.kfd.bean.PositionBean;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class FuturesPositionRefreshAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<FuturesPositionBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

	public FuturesPositionRefreshAdapter(Context context,
			List<FuturesPositionBean> listItems, RelativeLayout mHead) {
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
			convertView = listContainer.inflate(R.layout.listitem12, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			// 获取控件对象
			holder.t1 = (TextView) convertView.findViewById(R.id.t1);
			holder.t2 = (TextView) convertView.findViewById(R.id.t2);
			holder.t3 = (TextView) convertView.findViewById(R.id.t3);
			holder.t4 = (TextView) convertView.findViewById(R.id.t4);
			holder.t5 = (TextView) convertView.findViewById(R.id.t5);
			holder.t6 = (TextView) convertView.findViewById(R.id.t6);
			holder.t7 = (TextView) convertView.findViewById(R.id.t7);
			holder.t8 = (TextView) convertView.findViewById(R.id.t8);
			holder.t9 = (TextView) convertView.findViewById(R.id.t9);
			holder.t10 = (TextView) convertView.findViewById(R.id.t10);
			holder.t11 = (TextView) convertView.findViewById(R.id.t11);
			holder.t12 = (TextView) convertView.findViewById(R.id.t12);
			holder.t13 = (TextView) convertView.findViewById(R.id.t13);

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

		// if (position % 2 == 0) {
		// convertView.setBackgroundResource(R.color.gray);
		// } else {
		// convertView.setBackgroundResource(R.color.white);
		// }
		FuturesPositionBean positionBean = listItems.get(position);

		holder.t1.setText(positionBean.getCreate_time());
		holder.t2 .setText(positionBean.getFuturescode());
		holder.t3 .setText(positionBean.getCodename());
		holder.t4 .setText(positionBean.getType());
		holder.t5 .setText(positionBean.getCreate_price());
		holder.t6 .setText(positionBean.getLow_price());
		holder.t7 .setText(positionBean.getHigh_price());
		holder.t8 .setText(positionBean.getNow_price());
		holder.t9 .setText(positionBean.getNum());
		holder.t10 .setText(positionBean.getCreate_cost());
		holder.t11 .setText(positionBean.getLive_day());
		holder.t12 .setText(positionBean.getLive_cost());
		holder.t13 .setText(positionBean.getYk());

		// holder.name.setText(stockBean.getBianmaname());

		// holder.recentprice.setText(stockBean.getNowjia());
		/*
		 * holder.updownrange.setText(stockBean.get);
		 * holder.updownamount.setText(stockBean.getUpdownamount());
		 * holder.volume.setText(stockBean.getVolume());
		 * holder.open.setText(stockBean.getTodayopen());
		 * holder.preclose.setText(stockBean.getYesterdayclose());
		 * holder.stockecode.setText(stockBean.getStockcode());
		 */
		// 颜色转换例如#345---->#334455
		// String colorString = stockBean.getColor();
		/*
		 * String newcolor = "#" + colorString.charAt(1) + colorString.charAt(1)
		 * + colorString.charAt(2) + colorString.charAt(2) +
		 * colorString.charAt(3) + colorString.charAt(3);
		 */
		// holder.recentprice.setTextColor(Color.parseColor(newcolor));

		/*
		 * String colorString = "#FFF"+stockBean.getColor().substring(1,
		 * stockBean.getColor().length());
		 * listItemView.name.setTextColor(Color.parseColor(colorString));
		 * listItemView.recentprice.setTextColor(Color.parseColor(colorString));
		 * listItemView.updownrange.setTextColor(Color.parseColor(colorString));
		 * listItemView
		 * .updownamount.setTextColor(Color.parseColor(colorString));
		 * listItemView.volume.setTextColor(Color.parseColor(colorString));
		 * listItemView.open.setText(Color.parseColor(colorString)) ;
		 * listItemView.preclose.setText(Color.parseColor(colorString));
		 */

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
		private TextView t1;
		private TextView t2;
		private TextView t3;
		private TextView t4;
		private TextView t5;
		private TextView t6;
		private TextView t7;
		private TextView t8;
		private TextView t9;
		private TextView t10;
		private TextView t11;
		private TextView t12;
		private TextView t13;

		HorizontalScrollView scrollView;
	}

}