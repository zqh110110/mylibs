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
import com.kfd.bean.GetMoneyRecordeBean;
import com.kfd.ui.MyHScrollView;
import com.kfd.ui.MyHScrollView.OnScrollChangedListener;

public class GetMoneyRecordeAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<GetMoneyRecordeBean> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private RelativeLayout mHead;
	public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

	public GetMoneyRecordeAdapter(Context context,
			List<GetMoneyRecordeBean> listItems, RelativeLayout mHead) {
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
			convertView = listContainer.inflate(R.layout.listitem10, null);

			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			holder.scrollView = scrollView1;

			holder.apply_time = (TextView) convertView.findViewById(R.id.textView1);
			holder.count = (TextView) convertView
					.findViewById(R.id.textView2);
			holder.number = (TextView) convertView
					.findViewById(R.id.textView3);
			holder.tx_apply = (TextView) convertView
					.findViewById(R.id.textView4);
			holder.remark = (TextView) convertView.findViewById(R.id.textView5);
			
			holder.state = (TextView) convertView.findViewById(R.id.textView6);
			holder.applyperson = (TextView) convertView
					.findViewById(R.id.textView7);
			holder.bank_address = (TextView) convertView.findViewById(R.id.textView8);
			holder.answer_time = (TextView) convertView
					.findViewById(R.id.textView9);
			holder.answer = (TextView) convertView
					.findViewById(R.id.textView10);

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
		GetMoneyRecordeBean bean = listItems.get(position);

		holder.apply_time.setText(bean.getCreate_time());
		holder.count.setText(bean.getApply());
		holder.number.setText(bean.getTx_cost());
		holder.tx_apply.setText(bean.getBank_money());
		holder.remark.setText(bean.getApply_account());
		holder.state.setText(bean.getBank_state());
		
		//holder.bank_name.setVisibility(View.GONE);
		holder.applyperson.setVisibility(View.GONE);
		holder.bank_address.setVisibility(View.GONE);
		holder.answer_time.setVisibility(View.GONE);
		holder.answer.setVisibility(View.GONE);
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
		 TextView number;
		 TextView bank_name;
		 TextView applyperson;
		 TextView bank_address;
		 TextView count;
		 TextView state;
		 TextView apply_time,tx_apply;
		 TextView remark, answer_time, answer;

		HorizontalScrollView scrollView;
	}

}
