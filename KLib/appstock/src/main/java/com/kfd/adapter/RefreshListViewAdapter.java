package com.kfd.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.kfd.activityfour.R;
import com.kfd.bean.StockBean;
import com.kfd.common.LogUtils;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class RefreshListViewAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<StockBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public RefreshListViewAdapter(Context context, List<StockBean> data,
			RelativeLayout mHead) {
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = data;
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
			convertView = listContainer.inflate(R.layout.listitem2, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			// 获取控件对象
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.recentprice = (TextView) convertView
					.findViewById(R.id.recentprice);
			holder.updownrange = (TextView) convertView
					.findViewById(R.id.updownrange);
			holder.updownamount = (TextView) convertView
					.findViewById(R.id.updownamount);
			holder.volume = (TextView) convertView.findViewById(R.id.volume);
			holder.open = (TextView) convertView.findViewById(R.id.open);
			holder.preclose = (TextView) convertView
					.findViewById(R.id.preclose);
			holder.stockecode = (TextView) convertView
					.findViewById(R.id.stockecode);
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
		StockBean stockBean = listItems.get(position);

		holder.name.setText(stockBean.getStockname());
		holder.name.setTextColor(Color.parseColor("#2a2a2a"));

		holder.recentprice.setText(stockBean.getRecentprice());
		holder.updownrange.setText(stockBean.getUpdownamount());
		holder.updownamount.setText(stockBean.getUpdownrange());
		holder.updownrange.setTextColor(Color.parseColor("#FFFFFF"));
		holder.updownamount.setTextColor(Color.parseColor("#FFFFFF"));
		holder.volume.setText(stockBean.getVolume());
		holder.open.setText(stockBean.getTodayopen());
		holder.preclose.setText(stockBean.getYesterdayclose());
		holder.stockecode.setText(stockBean.getStockcode());
		holder.stockecode.setTextColor(Color.parseColor("#555a5d"));

	//	LogUtils.v("test", stockBean.getColor());
		// 颜色转换例如#345---->#334455
		try {
			String colorString = stockBean.getColor();
			int newcolor;
			if (colorString.equals("#090")) {
				//newcolor = "#34f40e";
				newcolor = R.drawable.button_green;
			}else {
				newcolor = R.drawable.button_red;
			}
			
			//holder.recentprice.setTextColor(Color.parseColor(newcolor));
			//holder.updownrange.setTextColor(Color.parseColor(newcolor));
			holder.updownrange.setBackgroundResource(newcolor);
			holder.updownamount.setBackgroundResource(newcolor);
			//holder.volume.setTextColor(Color.parseColor(newcolor));
			//holder.open.setTextColor(Color.parseColor(newcolor));
			//holder.preclose.setTextColor(Color.parseColor(newcolor));
			// holder.stockecode.setTextColor(Color.parseColor(newcolor));
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		TextView name;
		TextView recentprice;
		TextView updownrange;
		TextView updownamount;
		TextView volume;
		TextView open;
		TextView preclose;
		TextView stockecode;
		HorizontalScrollView scrollView;
	}
}