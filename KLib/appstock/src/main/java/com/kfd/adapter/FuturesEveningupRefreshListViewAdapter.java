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
import com.kfd.adapter.EveningupRefreshListViewAdapter.OnScrollChangedListenerImp;
import com.kfd.adapter.EveningupRefreshListViewAdapter.ViewHolder;
import com.kfd.bean.EveninggupBean;
import com.kfd.bean.FuturesEveninggupBean;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class FuturesEveningupRefreshListViewAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<FuturesEveninggupBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

	public FuturesEveningupRefreshListViewAdapter(Context context,
			List<FuturesEveninggupBean> listItems, RelativeLayout mHead) {
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
			convertView = listContainer.inflate(R.layout.listitem13, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

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
			holder.t14 = (TextView) convertView.findViewById(R.id.t14);
			holder.t15 = (TextView) convertView.findViewById(R.id.t15);

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
		FuturesEveninggupBean eveninggupBean = listItems.get(position);

		holder.t1.setText(eveninggupBean.getCloseout_time());
		holder.t2.setText(eveninggupBean.getFutrurescode());
		holder.t3.setText(eveninggupBean.getType());
		holder.t4.setText(eveninggupBean.getCreate_price());
		holder.t5.setText(eveninggupBean.getLow_price());
		holder.t6.setText(eveninggupBean.getLow_price());
		holder.t7.setText(eveninggupBean.getNum());
		holder.t8.setText(eveninggupBean.getCreate_time());
		holder.t9.setText(eveninggupBean.getCloseout_price());
		holder.t10.setText(eveninggupBean.getShouxu_cost());
		holder.t11.setText(eveninggupBean.getQzpc_cost());
		holder.t12.setText(eveninggupBean.getYk());
		holder.t13.setVisibility(View.GONE);
		holder.t14.setVisibility(View.GONE);
		holder.t15.setVisibility(View.GONE);


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
		private TextView t13,t14,t15;

		HorizontalScrollView scrollView;
	}

}