package com.kfd.adapter;

import java.util.ArrayList;

import com.kfd.activityfour.R;
import com.kfd.adapter.TraderAdapter.ViewHodler;
import com.kfd.bean.Base;
import com.kfd.bean.EventBean;
import com.kfd.bean.FinaceCountryBean;
import com.kfd.bean.Holiday;
import com.kfd.bean.TitleBase;
import com.kfd.bean.TradeBean;
import com.kfd.common.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FianceCountryAdapter extends BaseAdapter {
	// http://www.longdw.com/
	private ArrayList<Base> arrayList;
	private Context context;
	private LayoutInflater layoutInflater;
	ImageLoader imageLoader;

	public FianceCountryAdapter(ArrayList<Base> arrayList, Context context,
			LayoutInflater layoutInflater) {
		super();
		this.arrayList = arrayList;
		this.context = context;
		this.layoutInflater = layoutInflater;
		imageLoader = imageLoader.getInstance();
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
	public int getViewTypeCount() {
		return 4;
	}

	private int FinaceCountryBeanType = 1;
	private int HolidayType = 2;
	private int EventBeanType = 3;
	private int LableType = 0;

	// FinaceCountryBean Holiday EventBean
	@Override
	public int getItemViewType(int position) {
		Base base = arrayList.get(position);
		return base.getType();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if (type == FinaceCountryBeanType) {
			ViewHodler viewHodler = null;
			if (viewHodler == null) {
				viewHodler = new ViewHodler();
				convertView = layoutInflater.inflate(
						R.layout.finacecountryitem, null);
				viewHodler.titleTextView = (TextView) convertView
						.findViewById(R.id.textView3);
				viewHodler.countryImageView = (ImageView) convertView
						.findViewById(R.id.roundImageView1);
				viewHodler.timeTextView = (TextView) convertView
						.findViewById(R.id.textView1);
				viewHodler.before_numTextView = (TextView) convertView
						.findViewById(R.id.textView4);
				viewHodler.forecastTextView = (TextView) convertView
						.findViewById(R.id.textView5);
				viewHodler.resultTextView = (TextView) convertView
						.findViewById(R.id.textView6);
				viewHodler.resultButton = (Button) convertView
						.findViewById(R.id.button1);
				viewHodler.textView7 = (TextView) convertView
						.findViewById(R.id.textView7);
				convertView.setTag(viewHodler);
			} else {
				viewHodler = (ViewHodler) convertView.getTag();
			}
			FinaceCountryBean countryBean = (FinaceCountryBean) arrayList
					.get(position);
			viewHodler.titleTextView.setText(countryBean.getContent());
			viewHodler.timeTextView.setText(countryBean.getTime());
			viewHodler.before_numTextView.setText("前值:"
					+ countryBean.getBefore_num());
			viewHodler.forecastTextView.setText("预测:"
					+ countryBean.getForecast());
			viewHodler.resultTextView.setText("结果:" + countryBean.getResult());
			if (countryBean.getImportant().equals("高")) {
				viewHodler.resultButton.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.boxstyle_reg));
				viewHodler.resultTextView.setTextColor(Color
						.parseColor("#b02b31"));
			} else if (countryBean.getImportant().equals("中")) {
				viewHodler.resultButton.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.boxstyle_blue));
				viewHodler.resultTextView.setTextColor(Color
						.parseColor("#3faaf5"));
			} else if (countryBean.getImportant().equals("低")) {
				viewHodler.resultTextView.setTextColor(Color
						.parseColor("#54b650"));
				viewHodler.resultButton.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.boxstyle_green));
			}

			String resultStr = countryBean.getResult().trim().replace("%", "");
			String beforeStr = countryBean.getBefore_num().trim()
					.replace("%", "");
			String forecastStr = countryBean.getForecast().trim()
					.replace("%", "");

			try {
				viewHodler.textView7.setVisibility(View.VISIBLE);

				if (countryBean.getForecast().equals("持平")) {
					forecastStr = beforeStr;
				}

				if (Float.parseFloat(resultStr) > Float.parseFloat(forecastStr)) {
					viewHodler.textView7.setText("利多金银");
					viewHodler.textView7
							.setBackgroundResource(R.drawable.fan_tag_bg1);

				} else if (Float.parseFloat(resultStr) < Float
						.parseFloat(forecastStr)) {
					viewHodler.textView7.setText("利空金银");
					viewHodler.textView7
							.setBackgroundResource(R.drawable.fan_tag_bg2);

				} else {

					viewHodler.textView7.setText("影响较小");
					viewHodler.textView7
							.setBackgroundResource(R.drawable.fan_tag_bg3);
				}

			} catch (NumberFormatException e) {
				viewHodler.textView7.setVisibility(View.INVISIBLE);
			}

			imageLoader.displayImage(countryBean.getCountry_img(),
					viewHodler.countryImageView);
			viewHodler.resultButton.setText(countryBean.getImportant());
		} else if (type == HolidayType) {
			DiaryViewHodler viewHodler = null;
			if (viewHodler == null) {
				viewHodler = new DiaryViewHodler();
				convertView = layoutInflater
						.inflate(R.layout.holidayitem, null);
				viewHodler.titleTextView = (TextView) convertView
						.findViewById(R.id.textView1);
				viewHodler.countryTextView = (TextView) convertView
						.findViewById(R.id.textView4);
				viewHodler.timeTextView = (TextView) convertView
						.findViewById(R.id.textView2);

				convertView.setTag(viewHodler);
			} else {
				viewHodler = (DiaryViewHodler) convertView.getTag();
			}

			Holiday countryBean = (Holiday) arrayList.get(position);
			viewHodler.titleTextView.setText(countryBean.getContent());
			if (!StringUtils.isEmpty(countryBean.getTime())) {
				try {
					viewHodler.timeTextView.setText(StringUtils
							.phpdateformat(countryBean.getTime()));

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				viewHodler.timeTextView.setText("00:00");
			}
			viewHodler.countryTextView.setText("影响 :"
					+ countryBean.getCountry());
		} else if (type == EventBeanType) {
			EventViewHodler viewHodler = null;
			if (viewHodler == null) {
				viewHodler = new EventViewHodler();
				convertView = layoutInflater.inflate(R.layout.eventitem, null);
				viewHodler.titleTextView = (TextView) convertView
						.findViewById(R.id.textView1);
				viewHodler.countryTextView = (TextView) convertView
						.findViewById(R.id.textView3);
				viewHodler.timeTextView = (TextView) convertView
						.findViewById(R.id.textView2);
				viewHodler.importanTextView = (TextView) convertView
						.findViewById(R.id.textView4);
				convertView.setTag(viewHodler);
			} else {
				viewHodler = (EventViewHodler) convertView.getTag();
			}
			EventBean countryBean = (EventBean) arrayList.get(position);
			viewHodler.titleTextView.setText(countryBean.getContent());

			if (!StringUtils.isEmpty(countryBean.getTime())) {
				try {
					if (countryBean.getTime().contains(":")) {
						int index = countryBean.getTime().indexOf(":");
						if (countryBean.getTime().length() >= index + 3) {

							viewHodler.timeTextView.setText(countryBean
									.getTime().substring(0, index + 3));
						} else {

							viewHodler.timeTextView.setText(countryBean
									.getTime());
						}
					} else if (countryBean.getTime().startsWith("--")) {
						// viewHodler.timeTextView.setText("日期格式错误");
						viewHodler.timeTextView.setText("--");
					} else {
						viewHodler.timeTextView.setText(StringUtils
								.phpdateformat(countryBean.getTime()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			viewHodler.countryTextView.setText("前值 :"
					+ countryBean.getCountry());
			viewHodler.importanTextView.setText(countryBean.getImportant());
			if (countryBean.getImportant().equals("高")) {
				viewHodler.importanTextView.setTextColor(Color
						.parseColor("#b02b31"));
			} else {
				viewHodler.importanTextView.setTextColor(Color
						.parseColor("#54b650"));
			}
		} else if (type == LableType) {
			
			//下一条数据也是lable;没有这种类型的数据 不显示当前lable
			if(arrayList.size() >= position + 1 && ((arrayList.size() - 1) == position|| LableType == getItemViewType(position + 1) ))
			{
				return new View(context);
			}
			
			convertView = layoutInflater.inflate(R.layout.finacetitle, null);

			TitleBase titlebase = (TitleBase) arrayList.get(position);
			TextView textView = (TextView) convertView
					.findViewById(R.id.textView1);
			if (titlebase.getItem() == 1) {
				textView.setText("财经数据");
			}
			if (titlebase.getItem() == 2) {
				textView.setText("假期预告");
			}
			if (titlebase.getItem() == 3) {
				textView.setText("财经事件");
			}
		}

		return convertView;
	}

	class ViewHodler {
		ImageView countryImageView;
		Button resultButton;
		TextView timeTextView, titleTextView, before_numTextView,
				forecastTextView, resultTextView, textView7;
	}

	class DiaryViewHodler {

		TextView timeTextView, titleTextView, countryTextView;
	}

	class EventViewHodler {

		TextView timeTextView, titleTextView, countryTextView,
				importanTextView;
	}

}
