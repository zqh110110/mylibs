package com.kfd.adapter;

import java.util.ArrayList;

import com.kfd.activityfour.R;
import com.kfd.activityfour.SelectTeacherActivity;
import com.kfd.activityfour.ZhuzhiActivity;
import com.kfd.api.Tools;
import com.kfd.bean.TeacherBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.ui.MyRatingBar;
import com.kfd.ui.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectTeacherAdapter extends BaseAdapter {
	private ArrayList<TeacherBean> arrayList;
	private SelectTeacherActivity activity;
	private LayoutInflater layoutInflater;
	private Context context;

	public SelectTeacherAdapter(ArrayList<TeacherBean> arrayList, SelectTeacherActivity activity, LayoutInflater layoutInflater, Context convertView) {
		super();
		this.context = context;
		this.arrayList = arrayList;
		this.activity = activity;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount() {

		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {

		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHodler viewHodler;
		if (convertView == null) {
			viewHodler = new ViewHodler();
			convertView = layoutInflater.inflate(R.layout.seleteteacheritem, null);
			viewHodler.roundImageView = (RoundImageView) convertView.findViewById(R.id.roundImageView1);
			viewHodler.nameTextView = (TextView) convertView.findViewById(R.id.textView1);
			viewHodler.timeTextView = (TextView) convertView.findViewById(R.id.timetextView1);
			viewHodler.contentTextView = (TextView) convertView.findViewById(R.id.contenttextView2);
			viewHodler.myRatingBar = (MyRatingBar) convertView.findViewById(R.id.myRatingBar1);
			viewHodler.selectImageView = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(viewHodler);
		} else {
			viewHodler = (ViewHodler) convertView.getTag();
		}
		final TeacherBean teacherBean = arrayList.get(position);
		ImageLoader.getInstance().displayImage(teacherBean.getFace(), viewHodler.roundImageView);
		viewHodler.nameTextView.setText(teacherBean.getRealname());

		viewHodler.timeTextView.setText(StringUtils.phpdateformat10(teacherBean.getChoose_time()));

		viewHodler.contentTextView.setText(teacherBean.getContent());
		viewHodler.myRatingBar.setRating(Float.parseFloat(teacherBean.getLevel()));
		viewHodler.myRatingBar.setClickable(false);
		viewHodler.myRatingBar.setFocusable(false);
		if (teacherBean.getIschoose().equals("1")) {
			viewHodler.selectImageView.setVisibility(View.VISIBLE);

		} else {

			viewHodler.selectImageView.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (teacherBean.getIschoose().equals("1")) {
					// http://kfd.demo.golds-cloud.com/pm?uid=19"
					Intent intent = new Intent(activity, ZhuzhiActivity.class);
					intent.putExtra("chat", "chat");
					intent.putExtra("url", teacherBean.getUrl());
					activity.startActivity(intent);
				} else {
					activity.selectTeacher(teacherBean.getId(), position);

				}
			}
		});

		return convertView;
	}

	class ViewHodler {
		RoundImageView roundImageView;
		TextView nameTextView, timeTextView, contentTextView;
		MyRatingBar myRatingBar;
		ImageView selectImageView;

	}
}
