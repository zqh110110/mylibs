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
import com.kfd.bean.PayRecordBean;
import com.kfd.common.LogUtils;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class PayRecordAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<PayRecordBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

	public PayRecordAdapter(Context context, List<PayRecordBean> listItems,
			RelativeLayout mHead) {
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
			convertView = listContainer.inflate(R.layout.listitem9, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			holder.time = (TextView) convertView.findViewById(R.id.textView1);
			holder.amount = (TextView) convertView.findViewById(R.id.textView2);
			holder.type = (TextView) convertView.findViewById(R.id.textView3);
			holder.state = (TextView) convertView.findViewById(R.id.textView4);
			holder.remark = (TextView) convertView.findViewById(R.id.textView5);

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
		PayRecordBean bean = listItems.get(position);

		holder.time.setText(bean.getPay_time());
		holder.amount.setText(bean.getCz_money());
		holder.type.setText(bean.getCz_type());
		holder.state.setText(bean.getState());
		holder.remark.setText(bean.getRemark());

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
		TextView time;
		TextView amount;
		TextView type;
		TextView state, remark;

		HorizontalScrollView scrollView;
	}

}