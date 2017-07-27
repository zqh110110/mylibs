package com.kfd.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kfd.activityfour.R;
import com.kfd.bean.InvestBean;
import com.kfd.bean.InvestBean.InvestNewcomments;
import com.kfd.common.EmojiUtil;
import com.kfd.common.StringUtils;

public class InvestListItemAdapter extends BaseAdapter {
	private ArrayList<InvestBean.InvestNewcomments> arrayList;
	private Context context;
	private LayoutInflater layoutInflater;

	private Bitmap[] emoticons;

	public InvestListItemAdapter(ArrayList<InvestNewcomments> arrayList, Context context, LayoutInflater layoutInflater, Bitmap[] emoticons) {
		super();
		this.emoticons = emoticons;
		this.arrayList = arrayList;
		this.context = context;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler viewHodler;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.investitemlistitem, null);
			viewHodler = new ViewHodler();
			viewHodler.nameTextView = (TextView) convertView.findViewById(R.id.textView1);
			viewHodler.contentTextView = (TextView) convertView.findViewById(R.id.textView2);
			convertView.setTag(viewHodler);
		} else {
			viewHodler = (ViewHodler) convertView.getTag();
		}
		InvestBean.InvestNewcomments investNewcomments = arrayList.get(position);
		viewHodler.nameTextView.setText(investNewcomments.getNickname());
		viewHodler.contentTextView.setText(investNewcomments.getContent());

		if (!StringUtils.isEmpty(investNewcomments.getContent())) {
			String temp_title = EmojiUtil.convertTag(investNewcomments.getContent());
			// viewHodler.contentTextView.setText(Html.fromHtml(temp_title,
			// emojiGetter, null));
			viewHodler.contentTextView.setText(Html.fromHtml(temp_title, imageGetter, null));
		}

		return convertView;
	}

	ImageGetter imageGetter = new ImageGetter() {
		public Drawable getDrawable(String source) {
			Drawable d = new BitmapDrawable(context.getResources(), emoticons[Integer.parseInt(source.replace(".png", "")) - 100]);
			d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			return d;
		}
	};

	class ViewHodler {
		TextView nameTextView, contentTextView;
	}

}
