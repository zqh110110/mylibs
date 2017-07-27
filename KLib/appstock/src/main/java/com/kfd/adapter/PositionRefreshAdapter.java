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
import com.kfd.bean.PositionBean;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class PositionRefreshAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<PositionBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

	public PositionRefreshAdapter(Context context,
			List<PositionBean> listItems, RelativeLayout mHead) {
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
			convertView = listContainer.inflate(R.layout.listitem5, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			// 获取控件对象

			holder.bianmaname = (TextView) convertView
					.findViewById(R.id.bianmaname);
			holder.jianchangtime = (TextView) convertView
					.findViewById(R.id.jianchangtime);
			holder.type = (TextView) convertView.findViewById(R.id.type);
			holder.jianchangjia = (TextView) convertView
					.findViewById(R.id.jianchangjia);
			holder.downjia = (TextView) convertView.findViewById(R.id.downjia);
			holder.upjia = (TextView) convertView.findViewById(R.id.upjia);
			holder.nowjia = (TextView) convertView.findViewById(R.id.nowjia);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.jianchangfei = (TextView) convertView
					.findViewById(R.id.jianchangfei);
			holder.hotfei = (TextView) convertView.findViewById(R.id.hotfei);
			holder.updown = (TextView) convertView.findViewById(R.id.updown);
			holder.daycount = (TextView) convertView
					.findViewById(R.id.daycount);
			holder.liuchangfei = (TextView) convertView
					.findViewById(R.id.liuchangfei);
			holder.fudongyin = (TextView) convertView
					.findViewById(R.id.fudongyin);
			holder.numbers = (TextView) convertView.findViewById(R.id.bills);
			holder.stockcode = (TextView) convertView
					.findViewById(R.id.stockcode);

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
		PositionBean positionBean = listItems.get(position);

		holder.bianmaname.setText(positionBean.getStock_name());
		holder.stockcode.setText(positionBean.getStockcode());
		holder.jianchangtime.setText(positionBean.getCreate_time());
		holder.type.setText(positionBean.getType());
		holder.jianchangjia.setText(positionBean.getCreate_price());
		holder.downjia.setText(positionBean.getLow_price());
		holder.upjia.setText(positionBean.getHigh_price());
		holder.nowjia.setText(positionBean.getNowprice());
		holder.count.setText(positionBean.getNum());
		holder.jianchangfei.setText(positionBean.getCreate_cost());
		holder.hotfei.setText(positionBean.getFujia_cost());
		holder.updown.setText(positionBean.getGzf_cost());
		holder.daycount.setText(positionBean.getLive_day());
		holder.liuchangfei.setText(positionBean.getLive_cost());
		holder.fudongyin.setText(positionBean.getFloat_vk());
		holder.numbers.setText(positionBean.getNumber());

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
		TextView bianmaname;
		TextView jianchangtime;
		TextView type;
		TextView jianchangjia;
		TextView downjia;
		TextView upjia;
		TextView nowjia;
		TextView count;
		TextView jianchangfei;
		TextView hotfei;
		TextView updown;
		TextView daycount;
		TextView liuchangfei;
		TextView fudongyin;
		TextView numbers, stockcode;

		HorizontalScrollView scrollView;
	}

}