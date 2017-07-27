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
import com.kfd.bean.EveninggupBean;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class EveningupRefreshListViewAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<EveninggupBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

	public EveningupRefreshListViewAdapter(Context context,
			List<EveninggupBean> listItems, RelativeLayout mHead) {
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
			convertView = listContainer.inflate(R.layout.listitem4, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			// 获取控件对象
			holder.stockname = (TextView) convertView
					.findViewById(R.id.stockname);
			holder.stockcode = (TextView) convertView
					.findViewById(R.id.stockcode);

			holder.type = (TextView) convertView.findViewById(R.id.type);
			holder.setupprice = (TextView) convertView
					.findViewById(R.id.setupprice);
			holder.stoplossprice = (TextView) convertView
					.findViewById(R.id.stoplossprice);
			holder.stopearnprice = (TextView) convertView
					.findViewById(R.id.stopearnprice);
			holder.count = (TextView) convertView.findViewById(R.id.count);

			holder.setuptime = (TextView) convertView
					.findViewById(R.id.setuptime);
			holder.closeout_price = (TextView) convertView
					.findViewById(R.id.closeout_price);
			holder.setup_cost = (TextView) convertView
					.findViewById(R.id.setup_cost);
			holder.closeout_cost = (TextView) convertView
					.findViewById(R.id.closeout_cost);
			holder.forcecloseout_cost = (TextView) convertView
					.findViewById(R.id.forcecloseout_cost);
			holder.hot_cost = (TextView) convertView
					.findViewById(R.id.hot_cost);
			holder.gzf_cost = (TextView) convertView
					.findViewById(R.id.gzf_cost);

			holder.live_cost = (TextView) convertView
					.findViewById(R.id.live_cost);
			holder.yk = (TextView) convertView.findViewById(R.id.yk);
			holder.numbers = (TextView) convertView.findViewById(R.id.numbers);
			holder.closeout_time = (TextView) convertView
					.findViewById(R.id.closeout_time);

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

		// if (position % 2 == 0) {
		// convertView.setBackgroundResource(R.color.gray);
		// } else {
		// convertView.setBackgroundResource(R.color.white);
		// }
		EveninggupBean eveninggupBean = listItems.get(position);

		holder.stockname.setText(eveninggupBean.getStock_name());
		holder.stockcode.setText(eveninggupBean.getStockcode());
		holder.type.setText(eveninggupBean.getType());
		holder.setupprice.setText(eveninggupBean.getCreate_price());
		holder.closeout_time.setText(eveninggupBean.getCloseout_time());
		holder.stoplossprice.setText(eveninggupBean.getLow_price());
		holder.stopearnprice.setText(eveninggupBean.getHigh_price());
		holder.count.setText(eveninggupBean.getNum());
		holder.setuptime.setText(eveninggupBean.getCreate_time());
		holder.closeout_price.setText(eveninggupBean.getCloseout_price());
		holder.setup_cost.setText(eveninggupBean.getCreate_cost());
		holder.closeout_cost.setText(eveninggupBean.getCloseout_cost());
		holder.forcecloseout_cost.setText(eveninggupBean.getQzpc_cost());
		holder.hot_cost.setText(eveninggupBean.getHot_cost());
		holder.gzf_cost.setText(eveninggupBean.getGzf_cost());
		holder.live_cost.setText(eveninggupBean.getLive_cost());
		holder.yk.setText(eveninggupBean.getYk());
		holder.numbers.setText(eveninggupBean.getNumber());

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
		TextView type;
		TextView closeout_time;
		TextView setupprice;
		TextView stoplossprice;
		TextView stopearnprice;
		TextView count;
		TextView setuptime;
		TextView closeout_price;
		TextView setup_cost;
		TextView closeout_cost;
		TextView forcecloseout_cost;
		TextView hot_cost;
		TextView gzf_cost;
		TextView live_cost;
		TextView yk;
		TextView numbers;

		HorizontalScrollView scrollView;
	}

}