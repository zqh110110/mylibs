package com.kfd.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kfd.activityfour.R;
import com.kfd.adapter.RefreshListViewAdapter.OnScrollChangedListenerImp;
import com.kfd.adapter.RefreshListViewAdapter.ViewHolder;
import com.kfd.bean.FuturesBean;
import com.kfd.bean.StockBean;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class FuturesDetailListAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<FuturesBean> listItems;// 数据集合
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
	public FuturesDetailListAdapter(Context context, List<FuturesBean> data,
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
			convertView = listContainer.inflate(R.layout.futurelistitem, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			holder.name = (TextView) convertView.findViewById(R.id.t1);
			holder.recentprice = (TextView) convertView.findViewById(R.id.t2);
			holder.openprice = (TextView) convertView.findViewById(R.id.t3);
			holder.preprice = (TextView) convertView.findViewById(R.id.t4);
			holder.high = (TextView) convertView.findViewById(R.id.t5);
			holder.low = (TextView) convertView.findViewById(R.id.t6);
			holder.strikeamount = (TextView) convertView.findViewById(R.id.t7);
			holder.cjlTextView= (TextView) convertView.findViewById(R.id.t8);
			holder.bearamount = (TextView) convertView.findViewById(R.id.t9);
			holder.code = (TextView) convertView.findViewById(R.id.t11);
		
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
     FuturesBean  futuresBean  =  listItems.get(position);
		holder.code.setText(futuresBean.getCode());
		
		holder.name.setText(futuresBean.getCodename());
		holder.recentprice.setText(futuresBean.getFutulastp()); 
		holder.openprice.setText(futuresBean.getFutuopenp());
		holder.preprice.setText(futuresBean.getPresquarep());
		holder.high.setText(futuresBean.getUplimitp());
		holder.low.setText(futuresBean.getFutulowp());
		holder.strikeamount.setText(futuresBean.getBusinessa());
		holder.cjlTextView.setText(futuresBean.getBusinessb());
		holder.bearamount.setText(futuresBean.getBearamount());
		
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
		private TextView name,code;
		private TextView recentprice;
		private TextView openprice;
		private TextView preprice;
		private TextView high;
		private TextView low;
		private TextView strikeamount,bearamount,cjlTextView;
		HorizontalScrollView scrollView;
	}
}