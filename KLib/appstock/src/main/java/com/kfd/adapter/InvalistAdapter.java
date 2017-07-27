package com.kfd.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kfd.activityfour.InvestDetailActivity;
import com.kfd.activityfour.InvestListActivity;
import com.kfd.activityfour.R;
import com.kfd.bean.InvestBean;
import com.kfd.common.EmojiUtil;
import com.kfd.common.StringUtils;
import com.kfd.ui.MyGridView;
import com.kfd.ui.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class InvalistAdapter extends BaseAdapter {
	private ArrayList<InvestBean> arrayList;
	private Activity context;
	private LayoutInflater layoutInflater;
	private ImageLoader imageLoader;
	private InvestListActivity activity;
	private Bitmap[] emoticons;

	public InvalistAdapter(ArrayList<InvestBean> arrayList, Activity context, LayoutInflater layoutInflater, ImageLoader imageLoader, InvestListActivity activity, Bitmap[] emoticons) {
		super();
		this.emoticons = emoticons;
		this.arrayList = arrayList;
		this.context = context;
		this.layoutInflater = layoutInflater;
		this.imageLoader = imageLoader;
		this.activity = activity;
		
		options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(true)
				.showImageOnLoading(R.drawable.ordinaryuser_ico)
				.showImageOnFail(R.drawable.ordinaryuser_ico).build();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public InvestBean getItem(int position) {

		return arrayList.get(position);

	}

	String emoji_path = "/lmbang/emoji/";
	private int fontSize;
	ImageGetter imageGetter = new ImageGetter() {
		public Drawable getDrawable(String source) {
			Drawable d = new BitmapDrawable(context.getResources(), emoticons[Integer.parseInt(source.replace(".png", "")) - 100]);
			d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			return d;
		}
	};

	ViewHodler viewHodler;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHodler viewHodler = null;

		if (convertView == null) {
			viewHodler = new ViewHodler();
			convertView = layoutInflater.inflate(R.layout.investitem, null);
			viewHodler.iconImageView = (RoundImageView) convertView.findViewById(R.id.roundImageView1);
			viewHodler.nicknameTextView = (TextView) convertView.findViewById(R.id.nametextView1);
			viewHodler.contentTextView = (TextView) convertView.findViewById(R.id.contenttextView1);
			viewHodler.gridView = (MyGridView) convertView.findViewById(R.id.fresh_news_photo_gv);
			viewHodler.timeTextView = (TextView) convertView.findViewById(R.id.time_textview);
			viewHodler.prisenameTextView = (TextView) convertView.findViewById(R.id.comment_name_tv);
			viewHodler.commentTextView = (TextView) convertView.findViewById(R.id.comment_count_tv);
			viewHodler.priseTextView = (TextView) convertView.findViewById(R.id.fresh_news_reply_tv);
			viewHodler.showmoreTextView = (TextView) convertView.findViewById(R.id.showmoretextView);
			viewHodler.listView1 = (ListView) convertView.findViewById(R.id.listView1);
			viewHodler.spline = (TextView) convertView.findViewById(R.id.spline);
			viewHodler.priselayout = (LinearLayout) convertView.findViewById(R.id.priselayout);
			viewHodler.priseimageview = (ImageView) convertView.findViewById(R.id.priseimageview);
			viewHodler.replyImageView = (ImageView) convertView.findViewById(R.id.fresh_news_praise_iv);
			convertView.setTag(viewHodler);
		} else {
			viewHodler = (ViewHodler) convertView.getTag();
		}
		this.viewHodler = viewHodler;
		final InvestBean investBean = arrayList.get(position);

		if(investBean.getFace() == null || TextUtils.isEmpty(investBean.getFace()))
		{
			viewHodler.iconImageView.setImageResource(R.drawable.ordinaryuser_ico);
		}
		else
		{
			imageLoader.displayImage(investBean.getFace(), viewHodler.iconImageView, options);
		}
		viewHodler.nicknameTextView.setText(investBean.getNickname());
		if (!StringUtils.isEmpty(investBean.getContent())) {
			String temp_title = EmojiUtil.convertTag(investBean.getContent());
			// viewHodler.contentTextView.setText(Html.fromHtml(temp_title,
			// emojiGetter, null));
			viewHodler.contentTextView.setText(Html.fromHtml(temp_title, imageGetter, null));
		}
		else
		{
			viewHodler.contentTextView.setText("");
		}

		// viewHodler.contentTextView.setText(investBean.getContent());
		if (investBean.getCommArrayList() != null && investBean.getCommArrayList().size() > 0) {
			if (investBean.getCommArrayList().size() >= 3) {
				viewHodler.showmoreTextView.setVisibility(View.VISIBLE);
			} else {
				viewHodler.showmoreTextView.setVisibility(View.GONE);
			}
			if (investBean.getCommArrayList().size() == 1) {
				viewHodler.listView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40));
			} else if (investBean.getCommArrayList().size() == 2) {
				viewHodler.listView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 90));
			} else if (investBean.getCommArrayList().size() == 3) {
				viewHodler.listView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 130));
			}
			viewHodler.listView1.setAdapter(new InvestListItemAdapter(investBean.getCommArrayList(), context, layoutInflater, emoticons));
			viewHodler.showmoreTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 显示全部
					Intent intent = new Intent(activity, InvestDetailActivity.class);
					intent.putExtra("investBean", investBean);
					activity.startActivity(intent);
					// InvalistAdapter.this.viewHodler.listView1.setLayoutParams(new
					// LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
					// LinearLayout.LayoutParams.WRAP_CONTENT));
				}
			});
			viewHodler.spline.setVisibility(View.VISIBLE);
			viewHodler.listView1.invalidate();
		} else {
			viewHodler.listView1.setVisibility(View.GONE);
			viewHodler.showmoreTextView.setVisibility(View.GONE);
		}
		// viewHodler.timeTextView.setText(StringUtils.friendly_time(investBean.get));
		StringBuilder stringBuilder = new StringBuilder();
		if (investBean.getArrayList().size() > 0) {
			for (int i = 0; i < investBean.getArrayList().size(); i++) {
				if (i != 0) {
					stringBuilder.append("," + investBean.getArrayList().get(i).getNickname());
				} else {
					stringBuilder.append(investBean.getArrayList().get(i).getNickname());
				}

			}

			viewHodler.prisenameTextView.setText(stringBuilder.toString());
		} else {
			viewHodler.spline.setVisibility(View.GONE);
			viewHodler.priselayout.setVisibility(View.GONE);
		}

		viewHodler.timeTextView.setText(StringUtils.phpdateformat(investBean.getDateline()));
		if (investBean.getPicListsArrayList().size() > 0) {
			DynamicImageAdapter dynamicImageAdapter = new DynamicImageAdapter(context, investBean.getPicListsArrayList(), imageLoader);
			viewHodler.gridView.setVisibility(View.VISIBLE);
			viewHodler.gridView.setAdapter(dynamicImageAdapter);
			viewHodler.gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					/*
					 * ArrayList<String> urlArrayList = new ArrayList<String>();
					 * for (int i = 0; i <
					 * investBean.getPicListsArrayList().size(); i++) {
					 * urlArrayList
					 * .add(investBean.getPicListsArrayList().get(i).
					 * getPicture()); } DressUpBean dressupbean = new
					 * DressUpBean(); dressupbean.setPic(urlArrayList); if
					 * (position < arrayList.size()) { Intent intent = new
					 * Intent(context, DressUpDetailBigImgActivity.class);
					 * intent.putExtra("dressUpBean",dressupbean);
					 * intent.putExtra("currSelectIndex", position);
					 * context.startActivity(intent); } else {
					 * Toast.makeText(context, "数据异常", 0).show(); }
					 */
				}
			});
		} else {
			viewHodler.gridView.setVisibility(View.GONE);
		}
		if (!StringUtils.isEmpty(investBean.getLikes())) {

			viewHodler.priseTextView.setText(investBean.getLikes());
		}
		if (!StringUtils.isEmpty(investBean.getComments())) {

			viewHodler.commentTextView.setText(investBean.getComments());
		}
		if (investBean.getIslike().equals("1")) {
			viewHodler.priseimageview.setImageDrawable(context.getResources().getDrawable(R.drawable.praise_green_ico));
		} else {
			viewHodler.priseimageview.setImageDrawable(context.getResources().getDrawable(R.drawable.praise_ico));
		}
		viewHodler.priseimageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tid = investBean.getId();
				if (investBean.getIslike().equals("1")) {
					activity.changeLike(false, tid, position);
				} else {
					activity.changeLike(true, tid, position);
				}

			}
		});
		viewHodler.commentTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activity.replyinvest(investBean.getId());
			}
		});
		viewHodler.replyImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activity.replyinvest(investBean.getId());
			}
		});
		return convertView;

	}

	private DisplayImageOptions options;

	class ViewHodler {
		private ImageView replyImageView;
		private RoundImageView iconImageView;
		private TextView nicknameTextView, contentTextView, timeTextView, commentTextView, priseTextView, prisenameTextView;
		private ListView listView1;
		private MyGridView gridView;
		private TextView showmoreTextView, spline;
		private LinearLayout priselayout;
		private ImageView priseimageview;

	}

}
