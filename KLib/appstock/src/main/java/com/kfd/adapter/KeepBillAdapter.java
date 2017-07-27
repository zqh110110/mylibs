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
import com.kfd.bean.KeepBillBean;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class KeepBillAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<KeepBillBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();
	private String type;
	public KeepBillAdapter(Context context, List<KeepBillBean> listItems,
			RelativeLayout mHead,String type) {
		super();
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		this.mHead = mHead;
		this.type=type;
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
			convertView = listContainer.inflate(R.layout.listitem7, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			holder.codingName = (TextView) convertView
					.findViewById(R.id.codingName);
			holder.stockcode = (TextView) convertView
					.findViewById(R.id.stockcode);

			holder.buyingTime = (TextView) convertView
					.findViewById(R.id.buyingTime);
			holder.type = (TextView) convertView.findViewById(R.id.type);
			holder.buyingPrice = (TextView) convertView
					.findViewById(R.id.buyingPrice);
			holder.stopLostPrice = (TextView) convertView
					.findViewById(R.id.stopLostPrice);
			holder.stopWinPrice = (TextView) convertView
					.findViewById(R.id.stopWinPrice);
			holder.currentPrice = (TextView) convertView
					.findViewById(R.id.currentPrice);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.buildsFee = (TextView) convertView
					.findViewById(R.id.buildsFee);
			holder.hotPoundage = (TextView) convertView
					.findViewById(R.id.hotPoundage);
			holder.live_day = (TextView) convertView
					.findViewById(R.id.highPriceFee);
			holder.keepWarehouseFee = (TextView) convertView
					.findViewById(R.id.keepWarehouseFee);
			holder.floatingProfit = (TextView) convertView
					.findViewById(R.id.floatingProfit);
			holder.numbers = (TextView) convertView.findViewById(R.id.numbers);

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
		KeepBillBean bean = listItems.get(position);

		holder.codingName.setText(bean.getStock_name());
		holder.stockcode.setText(bean.getStockcode());

		holder.buyingTime.setText(bean.getCreate_time());
		holder.type.setText(bean.getType());
		holder.buyingPrice.setText(bean.getCreate_price());
		holder.stopLostPrice.setText(bean.getLow_price());
		holder.stopWinPrice.setText(bean.getHigh_price());
		holder.currentPrice.setText(bean.getNowprice());
		holder.count.setText(bean.getNum());
		holder.buildsFee.setText(bean.getCreate_cost());
		if (type!=null && type.equals("futures")) {
			holder.hotPoundage.setVisibility(View.GONE);
			holder.live_day.setVisibility(View.GONE);
			holder.codingName.setVisibility(View.GONE);
		}else {
		holder.hotPoundage.setText(bean.getHot_cost());
		holder.live_day.setText(bean.getLive_day());
		}
		holder.keepWarehouseFee.setText(bean.getLive_cost());
		holder.floatingProfit.setText(bean.getFloat_yk());
		holder.numbers.setText(bean.getNumber());

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

		TextView codingName, stockcode;// 编码名称
		TextView buyingTime;// 建仓时间
		TextView type; // 类型
		TextView buyingPrice;// 建仓价
		TextView stopLostPrice;// 止损价
		TextView stopWinPrice;// 止赢价
		TextView currentPrice;// 现价
		TextView count; // 股数
		TextView buildsFee;// 建仓费
		TextView hotPoundage;// 热门手续费
		TextView live_day;// 高涨跌幅费
		TextView keepWarehouseFee;// 留仓费
		TextView floatingProfit; // 浮动盈亏
		TextView numbers; // 单号

		HorizontalScrollView scrollView;
	}

}