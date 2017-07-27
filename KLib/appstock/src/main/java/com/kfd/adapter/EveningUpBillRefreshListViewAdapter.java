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
import com.kfd.bean.EveningUpBillBean;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class EveningUpBillRefreshListViewAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<EveningUpBillBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

	public EveningUpBillRefreshListViewAdapter(Context context,
			List<EveningUpBillBean> listItems, RelativeLayout mHead) {
		super();
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		this.mHead = mHead;
	}
	private String type;
	public EveningUpBillRefreshListViewAdapter(Context context,
			List<EveningUpBillBean> listItems, RelativeLayout mHead,String string) {
		super();
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		this.mHead = mHead;
		this.type =  string;
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
			convertView = listContainer.inflate(R.layout.listitem6, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			holder.stockname = (TextView) convertView
					.findViewById(R.id.codingName);
			holder.stockcode = (TextView) convertView
					.findViewById(R.id.stockcode);

			holder.time = (TextView) convertView.findViewById(R.id.buyingTime);
			holder.type = (TextView) convertView.findViewById(R.id.type);
			holder.createprice = (TextView) convertView
					.findViewById(R.id.buyingPrice);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.eveningupprice = (TextView) convertView
					.findViewById(R.id.closeOutPrice);
			holder.create_cost = (TextView) convertView
					.findViewById(R.id.buildsFee);
			holder.closeout_cost = (TextView) convertView
					.findViewById(R.id.closeOutFee);
			holder.qzpc_cost = (TextView) convertView
					.findViewById(R.id.forceCloseOut);
			holder.hot_cost = (TextView) convertView
					.findViewById(R.id.hotPoundage);
			holder.gzd_cosTextView = (TextView) convertView
					.findViewById(R.id.highPriceFee);
			holder.live_cost = (TextView) convertView
					.findViewById(R.id.keepWarehouseFee);
			holder.yk = (TextView) convertView.findViewById(R.id.profit);
			holder.number = (TextView) convertView.findViewById(R.id.numbers);

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
		EveningUpBillBean bean = listItems.get(position);

		holder.stockname.setText(bean.getStock_name());
		holder.stockcode.setText(bean.getStockcode());
		holder.time.setText(bean.getCloseout_time());
		holder.type.setText(bean.getType());
		holder.createprice.setText(bean.getCreate_price());
		holder.count.setText(bean.getNum());
		holder.eveningupprice.setText(bean.getCloseout_price());
		holder.create_cost.setText(bean.getCreate_cost());
		holder.closeout_cost.setText(bean.getCloseout_cost());
		
		holder.qzpc_cost.setText(bean.getQzpc_cost());
		holder.hot_cost.setText(bean.getHot_cost());
		holder.gzd_cosTextView.setText(bean.getGzf_cost());
		holder.live_cost.setText(bean.getLive_cost());
		holder.yk.setText(bean.getYk());
		holder.number.setText(bean.getNumber());
		
		if (this.type!=null && this.type.length()>0) {
			holder.stockname.setText(bean.getCloseout_time());
			holder.time.setText(bean.getFutures_code());
			holder.type.setText(bean.getType());
			holder.createprice.setText(bean.getLow_price());
			holder.count.setText(bean.getHigh_price());
			holder.eveningupprice.setText(bean.getNum());
			holder.create_cost.setText(bean.getCreate_time());
			holder.closeout_cost.setText(bean.getCloseout_price());
			holder.qzpc_cost.setText(bean.getShouxu_cost());
			holder.hot_cost.setText(bean.getFujia_cost());
			holder.gzd_cosTextView.setText(bean.getYk());
		}
		
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
		TextView stockname, stockcode;
		TextView time;
		TextView type;
		TextView createprice;
		TextView count;
		TextView eveningupprice;
		TextView create_cost;
		TextView closeout_cost;
		TextView qzpc_cost;
		TextView hot_cost;
		TextView gzd_cosTextView;
		TextView live_cost;
		TextView yk;
		TextView number;

		HorizontalScrollView scrollView;
	}

}