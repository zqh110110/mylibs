package com.kfd.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kfd.activityfour.BaseActivity;
import com.kfd.activityfour.LoginActivity;
import com.kfd.activityfour.MarkActivity;
import com.kfd.activityfour.R;
import com.kfd.activityfour.TradeDetailActivity;
import com.kfd.activityfour.ZhuzhiActivity;
import com.kfd.adapter.DirectseedAdapter.ViewHodler;
import com.kfd.api.AppContext;
import com.kfd.bean.KuaiXun;
import com.kfd.bean.TradeBean;
import com.kfd.common.StringUtils;
import com.kfd.db.SharePersistent;

public class TraderAdapter extends BaseAdapter
{
	private ArrayList<TradeBean> arrayList;

	private ArrayList<TradeBean> tradearrayListsecond;

	private Context context;
	private LayoutInflater layoutInflater;
	private MarkActivity markActivity;

	public TraderAdapter(ArrayList<TradeBean> arrayList, ArrayList<TradeBean> tradearrayListsecond, Context context, LayoutInflater layoutInflater,
			MarkActivity markActivity)
	{
		super();
		this.markActivity = markActivity;
		this.arrayList = arrayList;
		this.tradearrayListsecond = tradearrayListsecond;
		this.context = context;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount()
	{
		return arrayList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		ViewHodler viewHodler = null;
		if (viewHodler == null)
		{
			viewHodler = new ViewHodler();
			convertView = layoutInflater.inflate(R.layout.tradeitem, null);
			viewHodler.titleTextView = (TextView) convertView.findViewById(R.id.textView1);
			viewHodler.dateTextView = (TextView) convertView.findViewById(R.id.textView3);
			viewHodler.contentTextView = (TextView) convertView.findViewById(R.id.textView2);
			viewHodler.showallTextView = (TextView) convertView.findViewById(R.id.textView4);
			viewHodler.contentmsgtextView = (TextView) convertView.findViewById(R.id.contentmsgtextView);
			convertView.setTag(viewHodler);
		} else
		{
			viewHodler = (ViewHodler) convertView.getTag();
		}
		final TradeBean tradeBean = arrayList.get(position);
		viewHodler.titleTextView.setText(tradeBean.getTitle().trim());
		viewHodler.contentTextView.setText(tradeBean.getStdesc().trim());
		viewHodler.dateTextView.setText(StringUtils.phpdateformat(tradeBean.getDateline()));

		if (tradearrayListsecond.get(position).getContent() == null || TextUtils.isEmpty(tradearrayListsecond.get(position).getContent()))
		{
			viewHodler.contentmsgtextView.setVisibility(View.GONE);
			viewHodler.showallTextView.setVisibility(View.GONE);
		} else
		{
			viewHodler.contentmsgtextView.setVisibility(View.VISIBLE);

			if (StringUtils.isEmpty(tradeBean.getContent()))
			{
				viewHodler.contentmsgtextView.setVisibility(View.GONE);
				viewHodler.showallTextView.setText("查看全文");
			} else
			{
				viewHodler.contentmsgtextView.setVisibility(View.VISIBLE);
				viewHodler.contentmsgtextView.setText(tradeBean.getContent());
				viewHodler.showallTextView.setText("收起");
			}
		}

		viewHodler.showallTextView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(!AppContext.getInstance().isLogin())
				{
					context.startActivity(new Intent(context, LoginActivity.class));
					return;
				}
				
				/*
				 * Intent intent = new Intent(context,
				 * TradeDetailActivity.class); intent.putExtra("detail",
				 * tradeBean); intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 * context.startActivity(intent);
				 */

				/*
				 * Intent intent = new Intent(context, ZhuzhiActivity.class);
				 * 
				 * intent.putExtra("url", tradeBean.getUrl());
				 * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 * context.startActivity(intent);
				 */
				// Intent it = new Intent(Intent.ACTION_VIEW,
				// Uri.parse(tradeBean.getUrl()));
				// it.setClassName("com.android.browser",
				// "com.android.browser.BrowserActivity");
				// it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// context.startActivity(it);


				if (StringUtils.isEmpty(tradeBean.getContent()))
				{
					markActivity.setTradeItemShow(position);
				} else
				{
					markActivity.setTradeItemHide(position);
				}

			}
		});
		return convertView;
	}

	class ViewHodler
	{
		TextView titleTextView, dateTextView, contentTextView, showallTextView, contentmsgtextView;
	}

}
