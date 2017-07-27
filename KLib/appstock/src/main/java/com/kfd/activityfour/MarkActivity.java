package com.kfd.activityfour;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devsmart.android.ui.HorizontalListView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.kfd.adapter.DirectseedAdapter;
import com.kfd.adapter.FianceCountryAdapter;
import com.kfd.adapter.MarkTilteAdapter;
import com.kfd.adapter.TraderAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.Base;
import com.kfd.bean.EventBean;
import com.kfd.bean.FinaceCountryBean;
import com.kfd.bean.Holiday;
import com.kfd.bean.KFDPrice;
import com.kfd.bean.KuaiXun;
import com.kfd.bean.TitleBase;
import com.kfd.bean.TradeBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.entity.MarkLine;
import com.kfd.entity.MarkPrice;
import com.kfd.ui.PullToRefreshListViewSecond;

/*
 * 行情中心
 */
public class MarkActivity extends BaseActivity implements OnClickListener {

	ImageView mt4ImageView;
	PullToRefreshListViewSecond pullToRefreshListView;
	private View headView;

	private HorizontalListView mHListView;

	public static List<MarkPrice> mTitles = new ArrayList<MarkPrice>();

	private MarkTilteAdapter mTitleAdapter;

	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mark);
		initTitle("      凯福德金业\r\n www.kfd9999.com");
		pullToRefreshListView = (PullToRefreshListViewSecond) findViewById(R.id.pullToRefreshListView1);
		headView = getLayoutInflater().inflate(R.layout.markheader, null);
		pullToRefreshListView.addHeaderView(headView);
		loading = (LinearLayout) getLayoutInflater().inflate(R.layout.generic_loading, null);
		llpreLoading = (LinearLayout) loading.findViewById(R.id.llPreLoading);
		llLoadingFailed = (LinearLayout) loading.findViewById(R.id.llLoadingFailed);
		nomoreload = (LinearLayout) loading.findViewById(R.id.foot_layout_no_more);
		pullToRefreshListView.addFooterView(loading);
		mt4ImageView = (ImageView) findViewById(R.id.search);
		mt4ImageView.setImageDrawable(getResources().getDrawable(R.drawable.header_mt4_ico));
		mt4ImageView.setVisibility(View.VISIBLE);
		mt4ImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (judge()) {
					Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("net.metaquotes.metatrader4");
					startActivity(LaunchIntent);
				} else {
					// 下载app
					AlertDialog alertDialog = new AlertDialog.Builder(MarkActivity.this).setMessage("尚未安装MT4软件，是否安装?").setPositiveButton("确定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							UpdateManager_manual updateManager_manual = new UpdateManager_manual(MarkActivity.this, getApplicationContext());
							updateManager_manual.showDownloadDialog();
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					}).create();
					alertDialog.show();

				}
			}
		});
		backButton.setImageDrawable(getResources().getDrawable(R.drawable.header_notice_ico));
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到公告
				startActivity(new Intent(getApplicationContext(), AnnListActivity.class));
			}
		});

		initUI();
		loadData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		type = "hs300";
		startLoop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		isLoopPause = true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		AlertDialog alertDialog = new AlertDialog.Builder(MarkActivity.this).setMessage("是否退出应用?").setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
				ActivityManager.popall();
				System.exit(0);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		}).create();
		alertDialog.show();
	}

	PackageInfo packageInfo;

	private boolean judge() {

		try {
			packageInfo = getPackageManager().getPackageInfo("net.metaquotes.metatrader4", 0);

		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
			return false;
		}
		if (packageInfo == null) {
			return false;
		} else {
			return true;
		}
	}

	RelativeLayout financecalendarlayout, kuaixunlayout, tradetacticslayout;
	TextView silvernametextView, silverpricetextView, silverrangetextView;
	TextView goldnametextView, goldpricetextView, goldrangetextView;
	TextView dollarnametextView, dollarpricetextView, dollarrangetextView;
	TextView yuanyounametextView, yuanyoupricetextView, yuanyourangetextView;

	TextView amyuanyounametextView, amyuanyoupricetextView, amyuanyourangetextView;
	TextView hsnametextView, hspricetextView, hsrangetextView;
	ImageView dollarrangeimageview, goldrangeimageview, silverrangeimageview, yuanyourangeimageview, amyuanyourangeimageview, hsrangeimageview;
	ImageView zhiboiv, tradetacticsiv, financecaiv;

	// FrameLayout tradetacticscontent,directcontent,finacetcontent;

	@Override
	public void onClick(View v) {
		if (v == financecalendarlayout) {
			showFinaceCalender();
		} else if (v == kuaixunlayout) {
			showkuaixun();
		} else if (v == tradetacticslayout) {
			showtradetactics();
		}

		if (v == datelayout1) {
			changeTextColor(1);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(0).getTime()));
		} else if (v == datelayout2) {
			changeTextColor(2);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(1).getTime()));
		} else if (v == datelayout3) {
			changeTextColor(3);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(2).getTime()));
		} else if (v == datelayout4) {
			changeTextColor(4);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(3).getTime()));
		} else if (v == datelayout5) {
			changeTextColor(5);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(4).getTime()));
		} else if (v == datelayout6) {
			changeTextColor(6);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(5).getTime()));
		} else if (v == datelayout7) {
			changeTextColor(7);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(6).getTime()));
		} else if (v == datelayout8) {
			changeTextColor(8);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(7).getTime()));
		} else if (v == datelayout9) {
			changeTextColor(9);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(8).getTime()));
		} else if (v == datelayout10) {
			changeTextColor(10);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(9).getTime()));
		} else if (v == datelayout11) {
			changeTextColor(11);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(10).getTime()));
		} else if (v == datelayout12) {
			changeTextColor(12);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(11).getTime()));
		} else if (v == datelayout13) {
			changeTextColor(13);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(12).getTime()));
		} else if (v == datelayout14) {
			changeTextColor(14);
			loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(13).getTime()));
		}
	}

	private void initUI() {
		mHListView = (HorizontalListView) findViewById(R.id.h_listview);
		mHListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
				mTitleAdapter.setSelectedAt(arg2);

				executorService.execute(new Runnable() {

					@Override
					public void run() {
						loadKLineData(mTitles.get(arg2).getTypename(), "0");
					}
				});
			}
		});
		// tradetacticscontent=(FrameLayout)
		// findViewById(R.id.tradetacticscontent);
		// directcontent = (FrameLayout) findViewById(R.id.directcontent);
		// finacetcontent = (FrameLayout) findViewById(R.id.finacetcontent);
		zhiboiv = (ImageView) headView.findViewById(R.id.zhiboiv);
		tradetacticsiv = (ImageView) headView.findViewById(R.id.tradetacticsiv);
		tradetacticsiv = (ImageView) headView.findViewById(R.id.tradetacticsiv);
		financecaiv = (ImageView) headView.findViewById(R.id.financecaiv);
		financecalendarlayout = (RelativeLayout) headView.findViewById(R.id.financecalendarlayout);
		kuaixunlayout = (RelativeLayout) headView.findViewById(R.id.kuaixunlayout);
		tradetacticslayout = (RelativeLayout) headView.findViewById(R.id.tradetacticslayout);
		hsnametextView = (TextView) findViewById(R.id.hsnametextView);
		hspricetextView = (TextView) findViewById(R.id.hspricetextView);
		hsrangetextView = (TextView) findViewById(R.id.hsrangetextView);

		hsrangeimageview = (ImageView) findViewById(R.id.hsrangeimageview);
		financecalendarlayout.setOnClickListener(this);
		kuaixunlayout.setOnClickListener(this);
		tradetacticslayout.setOnClickListener(this);
		initSecondUI(headView);
		showkuaixun();
	}

	LinearLayout datelayout1, datelayout2, datelayout3, datelayout4, datelayout5, datelayout6, datelayout, datelayout7, datelayout8, datelayout9, datelayout10, datelayout11, datelayout12,
			datelayout13, datelayout14;
	TextView datetextView1, datetextView2, datetextView3, datetextView4, datetextView5, datetextView6, datetextView7, datetextView8, datetextView9, datetextView10, datetextView11, datetextView12,
			datetextView13, datetextView14;
	TextView weektextView1, weektextView2, weektextView3, weektextView4, weektextView5, weektextView6, weektextView7, weektextView8, weektextView9, weektextView10, weektextView11, weektextView12,
			weektextView13, weektextView14;

	private void initSecondUI(View view) {
		datelayout = (LinearLayout) view.findViewById(R.id.datelayout);
		datelayout1 = (LinearLayout) view.findViewById(R.id.datelayout1);
		datelayout2 = (LinearLayout) view.findViewById(R.id.datelayout2);
		datelayout3 = (LinearLayout) view.findViewById(R.id.datelayout3);
		datelayout4 = (LinearLayout) view.findViewById(R.id.datelayout4);
		datelayout5 = (LinearLayout) view.findViewById(R.id.datelayout5);
		datelayout6 = (LinearLayout) view.findViewById(R.id.datelayout6);
		datelayout7 = (LinearLayout) view.findViewById(R.id.datelayout7);
		datelayout8 = (LinearLayout) view.findViewById(R.id.datelayout8);
		datelayout9 = (LinearLayout) view.findViewById(R.id.datelayout9);
		datelayout10 = (LinearLayout) view.findViewById(R.id.datelayout10);
		datelayout11 = (LinearLayout) view.findViewById(R.id.datelayout11);
		datelayout12 = (LinearLayout) view.findViewById(R.id.datelayout12);
		datelayout13 = (LinearLayout) view.findViewById(R.id.datelayout13);
		datelayout14 = (LinearLayout) view.findViewById(R.id.datelayout14);

		datetextView1 = (TextView) view.findViewById(R.id.datetextView1);
		datetextView2 = (TextView) view.findViewById(R.id.datetextView2);
		datetextView3 = (TextView) view.findViewById(R.id.datetextView3);
		datetextView4 = (TextView) view.findViewById(R.id.datetextView4);
		datetextView5 = (TextView) view.findViewById(R.id.datetextView5);
		datetextView6 = (TextView) view.findViewById(R.id.datetextView6);
		datetextView7 = (TextView) view.findViewById(R.id.datetextView7);
		datetextView8 = (TextView) view.findViewById(R.id.datetextView8);
		datetextView9 = (TextView) view.findViewById(R.id.datetextView9);
		datetextView10 = (TextView) view.findViewById(R.id.datetextView10);
		datetextView11 = (TextView) view.findViewById(R.id.datetextView11);
		datetextView12 = (TextView) view.findViewById(R.id.datetextView12);
		datetextView13 = (TextView) view.findViewById(R.id.datetextView13);
		datetextView14 = (TextView) view.findViewById(R.id.datetextView14);

		weektextView1 = (TextView) view.findViewById(R.id.weektextView1);
		weektextView2 = (TextView) view.findViewById(R.id.weektextView2);
		weektextView3 = (TextView) view.findViewById(R.id.weektextView3);
		weektextView4 = (TextView) view.findViewById(R.id.weektextView4);
		weektextView5 = (TextView) view.findViewById(R.id.weektextView5);
		weektextView6 = (TextView) view.findViewById(R.id.weektextView6);
		weektextView7 = (TextView) view.findViewById(R.id.weektextView7);
		weektextView8 = (TextView) view.findViewById(R.id.weektextView8);
		weektextView9 = (TextView) view.findViewById(R.id.weektextView9);
		weektextView10 = (TextView) view.findViewById(R.id.weektextView10);
		weektextView11 = (TextView) view.findViewById(R.id.weektextView11);
		weektextView12 = (TextView) view.findViewById(R.id.weektextView12);
		weektextView13 = (TextView) view.findViewById(R.id.weektextView13);
		weektextView14 = (TextView) view.findViewById(R.id.weektextView14);

		datelayout1.setOnClickListener(this);
		datelayout2.setOnClickListener(this);
		datelayout3.setOnClickListener(this);
		datelayout4.setOnClickListener(this);
		datelayout5.setOnClickListener(this);
		datelayout6.setOnClickListener(this);
		datelayout7.setOnClickListener(this);
		datelayout8.setOnClickListener(this);
		datelayout9.setOnClickListener(this);
		datelayout10.setOnClickListener(this);
		datelayout11.setOnClickListener(this);
		datelayout12.setOnClickListener(this);
		datelayout13.setOnClickListener(this);
		datelayout14.setOnClickListener(this);
		datelayout.setVisibility(View.GONE);
	}

	private synchronized void loadHeadData() {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-market/price", hashMap);

		if (!StringUtils.isEmpty(result)) {

			try {
				JSONObject jsonObject = new JSONObject(result);
				JSONObject data = jsonObject.optJSONObject("data");
				JSONArray priceArray = data.optJSONArray("price");
				mTitles.clear();
				for (int i = 0; i < priceArray.length(); i++) {
					JSONObject pricJsonObject = priceArray.optJSONObject(i);
					MarkPrice kPrice = new MarkPrice();

					kPrice.setChange_pro(pricJsonObject.optString("change_pro"));
					kPrice.setName(pricJsonObject.optString("name"));
					kPrice.setPrice(pricJsonObject.optString("price"));
					kPrice.setTypename(pricJsonObject.optString("typename"));

					mTitles.add(kPrice);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			Message message = new Message();
			message.what = 2;
			kupdateUIHandler.sendMessage(message);

		} else {
			kupdateUIHandler.sendEmptyMessage(0);

		}

	}

	private synchronized void loadKLineData(final String typename, final String time) {
		type = typename;

		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("typename", typename);
		hashMap.put("lasttime", time);
		String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-market", hashMap);

		if (!StringUtils.isEmpty(result)) {

			try {
				JSONObject jsonObject = new JSONObject(result);
				JSONObject data = jsonObject.optJSONObject("data");
				// JSONArray priceArray = data.optJSONArray("price");
				// mTitles.clear();
				// for (int i = 0; i < priceArray.length(); i++) {
				// JSONObject pricJsonObject = priceArray.optJSONObject(i);
				// MarkPrice kPrice = new MarkPrice();
				//
				// kPrice.setChange_pro(pricJsonObject.optString("change_pro"));
				// kPrice.setName(pricJsonObject.optString("name"));
				// kPrice.setPrice(pricJsonObject.optString("price"));
				// kPrice.setTypename(pricJsonObject.optString("typename"));
				//
				// mTitles.add(kPrice);
				// }

				klinesArrayList.clear();
				JSONArray listArray = data.optJSONArray("list");
				for (int i = 0; i < listArray.length(); i++) {
					MarkLine markLine = new MarkLine();
					JSONArray itemArr = listArray.getJSONArray(i);
					markLine.setTime(itemArr.getLong(0));
					markLine.setPrice(itemArr.getString(1));
					klinesArrayList.add(markLine);

					float price = Float.parseFloat(markLine.getPrice());

					if (i == 0) {
						minvalue = price;
						maxvalue = price;
					} else {
						minvalue = Math.min(minvalue, price);
						maxvalue = Math.max(maxvalue, price);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			Message message = new Message();
			message.what = 1;
			kupdateUIHandler.sendMessage(message);
		} else {
			kupdateUIHandler.sendEmptyMessage(0);
		}

	}

	private ArrayList<MarkLine> klinesArrayList = new ArrayList<MarkLine>();
	private Handler kupdateUIHandler = new Handler() {
		public void handleMessage(Message msg) {

			System.out.println("-------------收到消息");

			dismissDialog();
			switch (msg.what) {
			case 0:

				break;
			case 1:
				System.out.println("-----------------更新line");

				// if (mTitleAdapter == null) {
				// mTitleAdapter = new MarkTilteAdapter(MarkActivity.this,
				// mTitles);
				// mHListView.setAdapter(mTitleAdapter);
				// } else {
				// mTitleAdapter.notifyDataSetChanged();
				// }
				initKLine();

				break;

			case 2:
				System.out.println("-----------------更新head");
				if (mTitleAdapter == null) {
					mTitleAdapter = new MarkTilteAdapter(MarkActivity.this, mTitles);
					mHListView.setAdapter(mTitleAdapter);
				} else {
					mTitleAdapter.notifyDataSetChanged();
				}
				break;

			default:
				break;
			}
		};
	};
	private float minvalue, maxvalue;

	private LineChart mChart;

	private synchronized void initKLine() {
		mChart = (LineChart) headView.findViewById(R.id.chart1);

		// mChart.setOnChartValueSelectedListener(this);

		// no description text
		mChart.setDescription("");
		mChart.setNoDataTextDescription("没有数据");

		// enable value highlighting
		mChart.setHighlightEnabled(true);

		// enable touch gestures
		mChart.setTouchEnabled(true);

		mChart.setDragDecelerationFrictionCoef(0.9f);

		mChart.setNoDataText("No data available.");

		// enable scaling and dragging
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(true);
		mChart.setDrawGridBackground(false);
		mChart.setHighlightPerDragEnabled(true);

		// if disabled, scaling can be done on x- and y-axis separately
		mChart.setPinchZoom(true);

		// set an alternative background color
		mChart.setBackgroundColor(Color.WHITE);

		// mChart.animateX(500);
		// mChart.animateY(500);

		Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

		// get the legend (only possible after setting data)
		Legend l = mChart.getLegend();

		// modify the legend ...
		// l.setPosition(LegendPosition.LEFT_OF_CHART);
		// l.setForm(LegendForm.LINE);
		// l.setTypeface(tf);
		// l.setTextSize(11f);
		// l.setTextColor(Color.WHITE);
		// l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		// l.setFormSize(0);
		l.setEnabled(false);

		XAxis xAxis = mChart.getXAxis();
		xAxis.setTypeface(tf);
		xAxis.setTextColor(Color.BLACK);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawAxisLine(false);
		xAxis.setSpaceBetweenLabels(1);
		xAxis.setPosition(XAxisPosition.BOTTOM);

		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setTypeface(tf);
		leftAxis.setTextColor(Color.BLACK);
		leftAxis.setAxisLineColor(Color.TRANSPARENT);
		leftAxis.setAxisMaxValue(maxvalue + (maxvalue - minvalue) / 10);
		leftAxis.setAxisMinValue(minvalue - (maxvalue - minvalue) / 10);
		leftAxis.setDrawGridLines(true);
		leftAxis.setStartAtZero(false);
		leftAxis.setGridColor(Color.parseColor("#E1E1E1"));

		leftAxis.setValueFormatter(new ValueFormatter() {

			@Override
			public String getFormattedValue(float value) {
				DecimalFormat mFormat = new DecimalFormat("##0.00");
				return mFormat.format(value);
			}
		});

		mChart.getAxisRight().setEnabled(false);

		// 设置点击chart图对应的数据弹出标注
		MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
		// define an offset to change the original position of the marker
		// (optional)
		// set the marker to the chart
		mChart.setMarkerView(mv);

		setData();

		// 刷新图表
		mChart.invalidate();

		mChart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), KLineDetailActivity.class);
				intent.putExtra("index", mTitleAdapter.getSelectedIndex());
				startActivity(intent);
			}
		});
	}

	private synchronized void setData() {

		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> yVals = new ArrayList<Entry>();
		for (int i = 0; i < klinesArrayList.size(); i++) {
			MarkLine kline = klinesArrayList.get(i);
			xVals.add(StringUtils.phpdateformat9(String.valueOf(klinesArrayList.get(i).getTime())));
			yVals.add(new Entry(Float.parseFloat(kline.getPrice()), i));
		}
		

		LineDataSet set1 = new LineDataSet(yVals, mTitleAdapter.getSelectedItem().getName());

		// LineDataSet set1 = new LineDataSet(yVals, "");

		// set1.setCubicIntensity(0.2f);
		// set1.setDrawFilled(true); // 设置包括的范围区域填充颜色
		// set1.setDrawCubic(false); // 设置曲线为圆滑的线
		// set1.setDrawCircles(false); // 设置有圆点
		// set1.setLineWidth(1f); // 设置线的宽度
		// set1.setCircleSize(2f); // 设置小圆的大小
		// set1.setCircleColor(Color.parseColor("#000000"));
		// set1.setHighLightColor(Color.parseColor("#000000"));
		// set1.setColor(Color.parseColor("#000000")); // 设置曲线的颜色
		// set1.setDrawValues(false);

		// LineDataSet set1 = new LineDataSet(yVals1, "DataSet 1");
		set1.setDrawFilled(true); // 设置包括的范围区域填充颜色
		set1.setAxisDependency(AxisDependency.LEFT);
		set1.setColor(Color.parseColor("#2a4b4c"));
		set1.setCircleColor(Color.WHITE);
		set1.setLineWidth(1f);
		set1.setCircleSize(1f);
		set1.setFillAlpha(65);
		set1.setFillColor(ColorTemplate.getHoloBlue());
		set1.setHighLightColor(Color.rgb(244, 117, 117));
		set1.setDrawCircleHole(false);
		set1.setFillColor(Color.parseColor("#2bbcc0"));

		set1.setDrawCircles(false); // 设置有圆点
		set1.setDrawValues(false);
		set1.setDrawCubic(true); // 设置曲线为圆滑的线

		// create a data object with the datasets
		LineData data = new LineData(xVals, set1);

		// set data
		mChart.setData(data);
	}

	/**
	 * 行情首页价格
	 */
	private void loadData() {
		showDialog("请稍候..");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-market/price", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 1;
					message.obj = result;
					updateUIHandler.sendMessage(message);
				} else {
					updateUIHandler.sendEmptyMessage(0);
				}

			}
		});
	}

	private Handler updateUIHandler = new Handler() {
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 0:

				break;
			case 1:
				String result = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONObject data = jsonObject.optJSONObject("data");
					JSONArray priceArray = data.optJSONArray("price");
					for (int i = 0; i < priceArray.length(); i++) {
						JSONObject pricJsonObject = priceArray.optJSONObject(i);
						KFDPrice kPrice = new KFDPrice();
						kPrice.setChange_pro(pricJsonObject.optString("change_pro"));
						kPrice.setPrice(pricJsonObject.optString("price"));
						kPrice.setTypename(pricJsonObject.optString("typename"));
						// 适配UI
						/*
						 * 类型 silver-伦敦银 gold-伦敦金 usdx-美元指数
						 */
						if (kPrice.getTypename().equals("silver")) {
							silvernametextView.setText("伦敦银");
							silverpricetextView.setText(kPrice.getPrice());
							silverrangetextView.setText(kPrice.getChange_pro());
							if (kPrice.getChange_pro().startsWith("-")) {
								silverrangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.down_ico));
								silverpricetextView.setTextColor(Color.parseColor("#53b84e"));
								silverrangetextView.setTextColor(Color.parseColor("#53b84e"));

							} else {
								silverrangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.up_ico));

								silverpricetextView.setTextColor(Color.parseColor("#fc423e"));
								silverrangetextView.setTextColor(Color.parseColor("#fc423e"));
							}

						} else if (kPrice.getTypename().equals("gold")) {

							goldnametextView.setText("伦敦金");
							goldpricetextView.setText(kPrice.getPrice());
							goldrangetextView.setText(kPrice.getChange_pro());
							if (kPrice.getChange_pro().startsWith("-")) {
								goldrangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.down_ico));

								goldpricetextView.setTextColor(Color.parseColor("#53b84e"));
								goldrangetextView.setTextColor(Color.parseColor("#53b84e"));
							} else {
								goldpricetextView.setTextColor(Color.parseColor("#fc423e"));
								goldrangetextView.setTextColor(Color.parseColor("#fc423e"));
								goldrangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.up_ico));
							}
						} else if (kPrice.getTypename().equals("usdx")) {

							dollarnametextView.setText("美元指数");
							dollarpricetextView.setText(kPrice.getPrice());
							dollarrangetextView.setText(kPrice.getChange_pro());
							if (kPrice.getChange_pro().startsWith("-")) {
								dollarpricetextView.setTextColor(Color.parseColor("#53b84e"));
								dollarrangetextView.setTextColor(Color.parseColor("#53b84e"));
								dollarrangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.down_ico));
							} else {
								dollarpricetextView.setTextColor(Color.parseColor("#fc423e"));
								dollarrangetextView.setTextColor(Color.parseColor("#fc423e"));

								dollarrangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.up_ico));
							}
						} else if (kPrice.getTypename().equals("ukoilq5")) {

							amyuanyounametextView.setText("英国原油");
							amyuanyoupricetextView.setText(kPrice.getPrice());
							amyuanyourangetextView.setText(kPrice.getChange_pro());
							if (kPrice.getChange_pro().startsWith("-")) {
								amyuanyoupricetextView.setTextColor(Color.parseColor("#53b84e"));
								amyuanyourangetextView.setTextColor(Color.parseColor("#53b84e"));
								amyuanyourangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.down_ico));
							} else {
								amyuanyoupricetextView.setTextColor(Color.parseColor("#fc423e"));
								amyuanyourangetextView.setTextColor(Color.parseColor("#fc423e"));

								amyuanyourangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.up_ico));
							}
						} else if (kPrice.getTypename().equals("usoiln5")) {

							yuanyounametextView.setText("美国原油");
							yuanyoupricetextView.setText(kPrice.getPrice());
							yuanyourangetextView.setText(kPrice.getChange_pro());
							if (kPrice.getChange_pro().startsWith("-")) {
								yuanyoupricetextView.setTextColor(Color.parseColor("#53b84e"));
								yuanyourangetextView.setTextColor(Color.parseColor("#53b84e"));
								yuanyourangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.down_ico));
							} else {
								yuanyoupricetextView.setTextColor(Color.parseColor("#fc423e"));
								yuanyourangetextView.setTextColor(Color.parseColor("#fc423e"));

								yuanyourangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.up_ico));
							}
						} else if (kPrice.getTypename().equals("hs300")) {

							hsnametextView.setText("沪深300");
							hspricetextView.setText(kPrice.getPrice());
							hsrangetextView.setText(kPrice.getChange_pro());
							if (kPrice.getChange_pro().startsWith("-")) {
								hspricetextView.setTextColor(Color.parseColor("#53b84e"));
								hsrangetextView.setTextColor(Color.parseColor("#53b84e"));
								hsrangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.down_ico));
							} else {
								hspricetextView.setTextColor(Color.parseColor("#fc423e"));
								hsrangetextView.setTextColor(Color.parseColor("#fc423e"));

								hsrangeimageview.setImageDrawable(getResources().getDrawable(R.drawable.up_ico));
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};

	DirectseedAdapter directseedAdapter;
	private ArrayList<KuaiXun> kuaixunarrayList = new ArrayList<KuaiXun>();
	private boolean isLoadMore;
	private boolean isLastPage = false;
	private int currentpage = 1;
	private int pageSize = 20;
	LinearLayout loading, llpreLoading, llLoadingFailed, nomoreload;

	private void showkuaixun() {
		datelayout.setVisibility(View.GONE);
		isLoadMore = false;
		isLastPage = false;
		currentpage = 1;
		pageSize = 20;
		// FragmentManager fm = getSupportFragmentManager();
		// Fragment fragment = fm.findFragmentByTag("KuaiXunFragment");
		// if(fragment==null){
		// KuaiXunFragment etfGoldFragment = new KuaiXunFragment();
		//
		// fm.beginTransaction().add(R.id.directcontent, etfGoldFragment,
		// "KuaiXunFragment").commit();
		// }else {
		// KuaiXunFragment etfGoldFragment = new KuaiXunFragment();
		//
		// fm.beginTransaction().replace(R.id.directcontent, etfGoldFragment,
		// "KuaiXunFragment").commit();
		// }
		zhiboiv.setVisibility(View.VISIBLE);
		tradetacticsiv.setVisibility(View.GONE);
		financecaiv.setVisibility(View.GONE);
		// tradetacticscontent.setVisibility(View.GONE);
		// directcontent.setVisibility(View.VISIBLE);
		// finacetcontent.setVisibility(View.GONE);

		directseedAdapter = new DirectseedAdapter(kuaixunarrayList, getApplicationContext(), getLayoutInflater());
		pullToRefreshListView.setAdapter(directseedAdapter);
		pullToRefreshListView.setDividerHeight(0);
		pullToRefreshListView.setDivider(getResources().getDrawable(R.drawable.transparent_background));
		// pullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {
		//
		// @Override
		// public void onRefresh() {
		// // TODO Auto-generated method stub
		// currentpage=1;
		// loadNEWS(currentpage);
		// }
		// });

		pullToRefreshListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (view.getLastVisiblePosition() == view.getCount() - 1 && !isLastPage) {
					if (isLoadMore) {
						return;
					}

					isLoadMore = true;
					loadNEWS(currentpage);
					loading.setVisibility(View.VISIBLE);
					llpreLoading.setVisibility(View.VISIBLE);
					llLoadingFailed.setVisibility(View.GONE);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
		loadNEWS(currentpage);
	}

	/**
	 * 快讯直播
	 */
	private void loadNEWS(final int pageindex) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("ps", pageSize + "");
				hashMap.put("p", pageindex + "");
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-market/info", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 2;
					message.obj = result;
					updatekuaixunUIHandler.sendMessage(message);
				} else {
					updatekuaixunUIHandler.sendEmptyMessage(3);
				}

			}
		});
	}

	private Handler updatekuaixunUIHandler = new Handler() {
		public void handleMessage(Message msg) {
			pullToRefreshListView.onRefreshComplete();
			isLoadMore = false;
			loading.setVisibility(View.GONE);
			llpreLoading.setVisibility(View.GONE);
			llLoadingFailed.setVisibility(View.GONE);
			// dismissDialog();
			switch (msg.what) {

			case 2:
				if (currentpage == 1) {

					kuaixunarrayList.clear();
				}
				currentpage++;
				String resultsecond = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(resultsecond);
					JSONObject data = jsonObject.optJSONObject("data");
					JSONArray priceArray = data.optJSONArray("list");

					for (int i = 0; i < priceArray.length(); i++) {
						JSONObject jsonObject2 = priceArray.optJSONObject(i);
						KuaiXun kuaiXun = new KuaiXun();
						kuaiXun.setContent(jsonObject2.optString("content"));
						kuaiXun.setDateline(jsonObject2.optString("dateline"));
						kuaiXun.setId(jsonObject2.optString("id"));
						kuaiXun.setColor(jsonObject2.optString("color"));
						kuaiXun.setIsbold(jsonObject2.optString("isbold"));
						kuaiXun.setPicture(jsonObject2.optString("picture"));
						kuaiXun.setDatapic(jsonObject2.optString("datapic"));
						kuaiXun.setDatainfo(jsonObject2.optString("datainfo"));
						kuaiXun.setDatacolor(jsonObject2.optString("datacolor"));
						
						kuaixunarrayList.add(kuaiXun);
					}
					if (priceArray.length() < pageSize) {
						isLastPage = true;
					}
					if (isLastPage) {
						// 显示没有更多了
						//
						loading.setVisibility(View.VISIBLE);
						llpreLoading.setVisibility(View.GONE);
						llLoadingFailed.setVisibility(View.GONE);
						nomoreload.setVisibility(View.VISIBLE);
					}

					directseedAdapter.notifyDataSetChanged();
					// setListViewHeightBasedOnChildren(pullToRefreshListView);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 3:

				break;
			default:
				break;
			}
		};
	};

	public void setTradeItemShow(int postion) {

		TradeBean tradeBeansecond = tradearrayListsecond.get(postion);
		tradearrayList.get(postion).setContent(tradeBeansecond.getContent());
		traderAdapter.notifyDataSetChanged();
	}

	public void setTradeItemHide(int postion) {
		tradearrayList.get(postion).setContent("");
		traderAdapter.notifyDataSetChanged();

	}

	TraderAdapter traderAdapter;
	private ArrayList<TradeBean> tradearrayList = new ArrayList<TradeBean>();
	private ArrayList<TradeBean> tradearrayListsecond = new ArrayList<TradeBean>();

	private void showtradetactics() {
		datelayout.setVisibility(View.GONE);
		isLoadMore = false;
		isLastPage = false;
		currentpage = 1;
		pageSize = 20;
		traderAdapter = new TraderAdapter(tradearrayList, tradearrayListsecond, this, getLayoutInflater(), MarkActivity.this);
		pullToRefreshListView.setAdapter(traderAdapter);
		pullToRefreshListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (view.getLastVisiblePosition() == view.getCount() - 1 && !isLastPage) {
					if (isLoadMore) {
						return;
					}

					isLoadMore = true;
					loadTrade(currentpage);
					loading.setVisibility(View.VISIBLE);
					llpreLoading.setVisibility(View.VISIBLE);
					llLoadingFailed.setVisibility(View.GONE);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
		// pullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {
		//
		//
		// @Override
		// public void onRefresh() {
		// // TODO Auto-generated method stub
		// currentpage=1;
		// loadTrade(currentpage);
		// }
		// });
		loadTrade(currentpage);
		// FragmentManager fm = getSupportFragmentManager();
		// Fragment fragment = fm.findFragmentByTag("TradetacticsFragment");
		// if(fragment==null){
		// TradetacticsFragment etfGoldFragment = new TradetacticsFragment();
		//
		// fm.beginTransaction().add(R.id.tradetacticscontent, etfGoldFragment,
		// "TradetacticsFragment").commit();
		// }else {
		// TradetacticsFragment etfGoldFragment = new TradetacticsFragment();
		//
		// fm.beginTransaction().replace(R.id.tradetacticscontent,
		// etfGoldFragment, "TradetacticsFragment").commit();
		// }
		zhiboiv.setVisibility(View.GONE);
		tradetacticsiv.setVisibility(View.VISIBLE);
		financecaiv.setVisibility(View.GONE);
		// directcontent.setVisibility(View.GONE);
		// tradetacticscontent.setVisibility(View.VISIBLE);
		// finacetcontent.setVisibility(View.GONE);
	}

	/**
	 * 快讯直播
	 */
	private void loadTrade(final int pageindex) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("ps", pageSize + "");
				hashMap.put("p", pageindex + "");
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-market/strategy", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 2;
					message.obj = result;
					updatetradeUIHandler.sendMessage(message);
				} else {
					updatetradeUIHandler.sendEmptyMessage(3);
				}

			}
		});
	}

	private Handler updatetradeUIHandler = new Handler() {
		public void handleMessage(Message msg) {
			pullToRefreshListView.onRefreshComplete();

			isLoadMore = false;
			loading.setVisibility(View.GONE);
			llpreLoading.setVisibility(View.GONE);
			llLoadingFailed.setVisibility(View.GONE);
			// dismissDialog();
			switch (msg.what) {

			case 2:
				if (currentpage == 1) {
					tradearrayList.clear();
				}
				currentpage++;
				String resultsecond = (String) msg.obj;

				try {
					JSONObject jsonObject = new JSONObject(resultsecond);
					JSONObject data = jsonObject.optJSONObject("data");
					JSONArray priceArray = data.optJSONArray("list");

					for (int i = 0; i < priceArray.length(); i++) {
						JSONObject jsonObject2 = priceArray.optJSONObject(i);
						TradeBean tradeBean = new TradeBean();
						tradeBean.setId(jsonObject2.optString("id"));
						tradeBean.setDateline(jsonObject2.optString("dateline"));
						tradeBean.setStdesc(jsonObject2.optString("stdesc"));
						tradeBean.setTitle(jsonObject2.optString("title"));
						tradeBean.setUrl(jsonObject2.optString("url"));
						tradearrayList.add(tradeBean);

						TradeBean tradeBeansecond = new TradeBean();
						tradeBeansecond.setId(jsonObject2.optString("id"));
						tradeBeansecond.setDateline(jsonObject2.optString("dateline"));
						tradeBeansecond.setStdesc(jsonObject2.optString("stdesc"));
						tradeBeansecond.setTitle(jsonObject2.optString("title"));
						tradeBeansecond.setUrl(jsonObject2.optString("url"));
						String content = jsonObject2.optString("content");
						tradeBeansecond.setContent(content != null && !content.equals("null") ? content : "");
						tradearrayListsecond.add(tradeBeansecond);
					}
					if (priceArray.length() < pageSize) {
						isLastPage = true;
					}
					if (isLastPage) {
						// 显示没有更多了
						//
						loading.setVisibility(View.VISIBLE);
						llpreLoading.setVisibility(View.GONE);
						llLoadingFailed.setVisibility(View.GONE);
						nomoreload.setVisibility(View.VISIBLE);
					}
					traderAdapter.notifyDataSetChanged();
					// directseedAdapter =new TraderAdapter(arrayList,
					// inflater.getContext(), inflater);
					pullToRefreshListView.setAdapter(traderAdapter);
					// setListViewHeightBasedOnChildren(pullToRefreshListView);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 3:

				break;
			default:
				break;
			}
		};
	};

	private void showFinaceCalender() {
		isLoadMore = false;
		isLastPage = false;
		currentpage = 1;
		pageSize = 20;
		datelayout.setVisibility(View.VISIBLE);
		// FragmentManager fm = getSupportFragmentManager();
		// Fragment fragment = fm.findFragmentByTag("KuaiXunFragment");
		// if(fragment==null){
		// FinaceCalenderFragment etfGoldFragment = new
		// FinaceCalenderFragment();
		//
		// fm.beginTransaction().add(R.id.finacetcontent, etfGoldFragment,
		// "FinaceCalenderFragment").commit();
		// }else {
		// FinaceCalenderFragment etfGoldFragment = new
		// FinaceCalenderFragment();
		//
		// fm.beginTransaction().replace(R.id.finacetcontent, etfGoldFragment,
		// "FinaceCalenderFragment").commit();
		// }
		zhiboiv.setVisibility(View.GONE);
		tradetacticsiv.setVisibility(View.GONE);
		financecaiv.setVisibility(View.VISIBLE);
		getNowDay();

		// directcontent.setVisibility(View.GONE);
		// tradetacticscontent.setVisibility(View.GONE);
		// finacetcontent.setVisibility(View.VISIBLE);
	}

	private void getNowDay() {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-market/financeDate", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.obj = result;
					message.what = 1;
					updatecalenderHandler.sendMessage(message);
				}
			}
		});
	}

	private Handler updatecalenderHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				break;
			case 1:
				String resultString = (String) msg.obj;
				try {

					JSONObject jsonObject = new JSONObject(resultString);
					JSONObject data = jsonObject.optJSONObject("data");
					JSONArray priceArray = data.optJSONArray("list");
					calendrearrayList.clear();
					for (int i = 0; i < priceArray.length(); i++) {
						JSONObject jsonObject2 = priceArray.optJSONObject(i);
						FinaceDay day = new FinaceDay();
						day.setTime(jsonObject2.optString("time"));
						day.setToday(jsonObject2.optString("today"));
						day.setWeek(jsonObject2.optString("week"));
						calendrearrayList.add(day);
					}

					if (calendrearrayList != null) {
						datelayout1.setVisibility(View.VISIBLE);
						datelayout2.setVisibility(View.VISIBLE);
						datelayout3.setVisibility(View.VISIBLE);
						datelayout4.setVisibility(View.VISIBLE);
						datelayout5.setVisibility(View.VISIBLE);
						datelayout6.setVisibility(View.VISIBLE);
						datelayout7.setVisibility(View.VISIBLE);
						datelayout8.setVisibility(View.VISIBLE);
						datelayout9.setVisibility(View.VISIBLE);
						datelayout10.setVisibility(View.VISIBLE);
						datelayout11.setVisibility(View.VISIBLE);
						datelayout12.setVisibility(View.VISIBLE);
						datelayout13.setVisibility(View.VISIBLE);
						datelayout14.setVisibility(View.VISIBLE);
						if (calendrearrayList.size() >= 1) {
							datelayout1.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 2) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 3) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 4) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 5) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
							datelayout5.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 6) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
							datelayout5.setVisibility(View.VISIBLE);
							datelayout6.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 7) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
							datelayout5.setVisibility(View.VISIBLE);
							datelayout6.setVisibility(View.VISIBLE);
							datelayout7.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 8) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
							datelayout5.setVisibility(View.VISIBLE);
							datelayout6.setVisibility(View.VISIBLE);
							datelayout7.setVisibility(View.VISIBLE);
							datelayout8.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 9) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
							datelayout5.setVisibility(View.VISIBLE);
							datelayout6.setVisibility(View.VISIBLE);
							datelayout7.setVisibility(View.VISIBLE);
							datelayout8.setVisibility(View.VISIBLE);
							datelayout9.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 10) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
							datelayout5.setVisibility(View.VISIBLE);
							datelayout6.setVisibility(View.VISIBLE);
							datelayout7.setVisibility(View.VISIBLE);
							datelayout8.setVisibility(View.VISIBLE);
							datelayout9.setVisibility(View.VISIBLE);
							datelayout10.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 11) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
							datelayout5.setVisibility(View.VISIBLE);
							datelayout6.setVisibility(View.VISIBLE);
							datelayout7.setVisibility(View.VISIBLE);
							datelayout8.setVisibility(View.VISIBLE);
							datelayout9.setVisibility(View.VISIBLE);
							datelayout10.setVisibility(View.VISIBLE);
							datelayout11.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 12) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
							datelayout5.setVisibility(View.VISIBLE);
							datelayout6.setVisibility(View.VISIBLE);
							datelayout7.setVisibility(View.VISIBLE);
							datelayout8.setVisibility(View.VISIBLE);
							datelayout9.setVisibility(View.VISIBLE);
							datelayout10.setVisibility(View.VISIBLE);
							datelayout11.setVisibility(View.VISIBLE);
							datelayout12.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 13) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
							datelayout5.setVisibility(View.VISIBLE);
							datelayout6.setVisibility(View.VISIBLE);
							datelayout7.setVisibility(View.VISIBLE);
							datelayout8.setVisibility(View.VISIBLE);
							datelayout9.setVisibility(View.VISIBLE);
							datelayout10.setVisibility(View.VISIBLE);
							datelayout11.setVisibility(View.VISIBLE);
							datelayout12.setVisibility(View.VISIBLE);
							datelayout13.setVisibility(View.VISIBLE);
						} else if (calendrearrayList.size() >= 14) {
							datelayout1.setVisibility(View.VISIBLE);
							datelayout2.setVisibility(View.VISIBLE);
							datelayout3.setVisibility(View.VISIBLE);
							datelayout4.setVisibility(View.VISIBLE);
							datelayout5.setVisibility(View.VISIBLE);
							datelayout6.setVisibility(View.VISIBLE);
							datelayout7.setVisibility(View.VISIBLE);
							datelayout8.setVisibility(View.VISIBLE);
							datelayout9.setVisibility(View.VISIBLE);
							datelayout10.setVisibility(View.VISIBLE);
							datelayout11.setVisibility(View.VISIBLE);
							datelayout12.setVisibility(View.VISIBLE);
							datelayout13.setVisibility(View.VISIBLE);
							datelayout14.setVisibility(View.VISIBLE);
						}
					}
					for (int j = 0; j < calendrearrayList.size(); j++) {
						FinaceDay day = calendrearrayList.get(j);
						String time = StringUtils.phpdateformat3(day.getTime());
						String weekday = day.getWeek();
						if (j == 0) {
							datetextView1.setText(time + "");
							weektextView1.setText(weekday);
						}
						if (j == 1) {
							datetextView2.setText(time + "");
							weektextView2.setText(weekday);

						}
						if (j == 2) {
							datetextView3.setText(time + "");
							weektextView3.setText(weekday);
						}
						if (j == 3) {
							datetextView4.setText(time + "");
							weektextView4.setText(weekday);
						}
						if (j == 4) {
							datetextView5.setText(time + "");
							weektextView5.setText(weekday);
						}
						if (j == 5) {
							datetextView6.setText(time + "");
							weektextView6.setText(weekday);
						}
						if (j == 6) {
							datetextView7.setText(time + "");
							weektextView7.setText(weekday);
						}
						if (j == 7) {
							datetextView8.setText(time + "");
							weektextView8.setText(weekday);
						}
						if (j == 8) {
							datetextView9.setText(time + "");
							weektextView9.setText(weekday);
						}
						if (j == 9) {
							datetextView10.setText(time + "");
							weektextView10.setText(weekday);
						}
						if (j == 10) {
							datetextView11.setText(time + "");
							weektextView11.setText(weekday);
						}
						if (j == 11) {
							datetextView12.setText(time + "");
							weektextView12.setText(weekday);
						}
						if (j == 12) {
							datetextView13.setText(time + "");
							weektextView13.setText(weekday);
						}
						if (j == 13) {
							datetextView14.setText(time + "");
							weektextView14.setText(weekday);
						}

						if (day.getToday().equals("1")) {
							changeTextColor(j + 1);
							loadCalenderList(StringUtils.phpdateformat5(calendrearrayList.get(j).getTime()));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};

	private void loadCalenderList(final String date) {

		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				// hashMap.put("ps", "25");
				// hashMap.put("p", pageindex+"");
				hashMap.put("date", date);
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-market/finance", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 2;
					message.obj = result;
					updatecalenderUIHandler.sendMessage(message);
				} else {
					updatecalenderUIHandler.sendEmptyMessage(3);
				}

			}
		});
	}

	private ArrayList<Base> arrayList2 = new ArrayList<Base>();
	private ArrayList<FinaceCountryBean> countryList = new ArrayList<FinaceCountryBean>();
	private ArrayList<Holiday> holidayArrayList = new ArrayList<Holiday>();
	private ArrayList<EventBean> eventList = new ArrayList<EventBean>();
	FianceCountryAdapter fianceCountryAdapter;
	private Handler updatecalenderUIHandler = new Handler() {
		public void handleMessage(Message msg) {

			// dismissDialog();
			switch (msg.what) {

			case 2:
				currentpage++;
				String resultsecond = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(resultsecond);
					JSONObject data = jsonObject.optJSONObject("data");
					JSONArray priceArray = data.optJSONArray("finance");
					arrayList2.clear();
					countryList.clear();
					TitleBase titleBase = new TitleBase();
					titleBase.setType(0);
					titleBase.setItem(1);

					// if (priceArray!=null && priceArray.length()>0) {
					arrayList2.add(titleBase);
					// }
					for (int i = 0; i < priceArray.length(); i++) {
						JSONObject jsonObject2 = priceArray.optJSONObject(i);
						FinaceCountryBean countryBean = new FinaceCountryBean();
						countryBean.setBefore_num(jsonObject2.optString("before_num"));
						countryBean.setContent(jsonObject2.optString("content"));
						countryBean.setCountry(jsonObject2.optString("country"));
						countryBean.setCountry_img(jsonObject2.optString("country_img"));
						countryBean.setForecast(jsonObject2.optString("forecast"));
						countryBean.setImportant(jsonObject2.optString("important"));
						countryBean.setResult(jsonObject2.optString("result"));
						countryBean.setTime(jsonObject2.optString("time"));
						countryBean.setType(1);
						countryList.add(countryBean);
						arrayList2.add(countryBean);
					}
					TitleBase titleBase2 = new TitleBase();
					titleBase2.setType(0);
					titleBase2.setItem(2);

					JSONArray holidayArray = data.optJSONArray("holiday");
					// if (holidayArray!=null && holidayArray.length()>0) {

					arrayList2.add(titleBase2);
					// }
					holidayArrayList.clear();
					for (int i = 0; i < holidayArray.length(); i++) {
						JSONObject jsonObject2 = holidayArray.optJSONObject(i);
						Holiday holiday = new Holiday();
						holiday.setContent(jsonObject2.optString("content"));
						holiday.setCountry(jsonObject2.optString("country"));
						holiday.setTime(jsonObject2.optString("time"));
						holiday.setType(2);
						holidayArrayList.add(holiday);
						arrayList2.add(holiday);
					}

					TitleBase titleBase3 = new TitleBase();
					titleBase3.setType(0);
					titleBase3.setItem(3);

					JSONArray eventArray = data.optJSONArray("event");
					// if (eventArray!=null && eventArray.length()>0) {
					arrayList2.add(titleBase3);
					// }

					eventList.clear();
					for (int i = 0; i < eventArray.length(); i++) {
						JSONObject jsonObject2 = eventArray.optJSONObject(i);
						EventBean holiday = new EventBean();
						holiday.setContent(jsonObject2.optString("content"));
						holiday.setCountry(jsonObject2.optString("country"));
						holiday.setTime(jsonObject2.optString("time"));
						holiday.setImportant(jsonObject2.optString("important"));
						holiday.setType(3);
						eventList.add(holiday);
						arrayList2.add(holiday);
					}

					fianceCountryAdapter = new FianceCountryAdapter(arrayList2, getApplicationContext(), getLayoutInflater());
					pullToRefreshListView.setAdapter(fianceCountryAdapter);
					pullToRefreshListView.setDividerHeight(0);
					pullToRefreshListView.setDivider(getResources().getDrawable(R.drawable.transparent_background));
					pullToRefreshListView.removeFooterView(loading);
					pullToRefreshListView.setOnScrollListener(new OnScrollListener() {

						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
							// TODO Auto-generated method stub

						}
					});
					// setListViewHeightBasedOnChildren(listView1);

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 3:

				break;
			default:
				break;
			}
		};
	};

	class FinaceDay {
		/**
		 * time 时间
		 * 
		 * week 周几
		 * 
		 * today 是否今天 1-是 0-否
		 */
		private String time, week, today;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getWeek() {
			return week;
		}

		public void setWeek(String week) {
			this.week = week;
		}

		public String getToday() {
			return today;
		}

		public void setToday(String today) {
			this.today = today;
		}

	}

	private void changeTextColor(int index) {
		datelayout1.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout2.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout3.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout4.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout5.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout6.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout7.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout8.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout9.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout10.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout11.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout12.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout13.setBackgroundColor(Color.parseColor("#ffffff"));
		datelayout14.setBackgroundColor(Color.parseColor("#ffffff"));

		datetextView1.setTextColor(Color.parseColor("#999999"));
		datetextView2.setTextColor(Color.parseColor("#999999"));
		datetextView3.setTextColor(Color.parseColor("#999999"));
		datetextView4.setTextColor(Color.parseColor("#999999"));
		datetextView5.setTextColor(Color.parseColor("#999999"));
		datetextView6.setTextColor(Color.parseColor("#999999"));
		datetextView7.setTextColor(Color.parseColor("#999999"));
		datetextView8.setTextColor(Color.parseColor("#999999"));
		datetextView9.setTextColor(Color.parseColor("#999999"));
		datetextView10.setTextColor(Color.parseColor("#999999"));
		datetextView11.setTextColor(Color.parseColor("#999999"));
		datetextView12.setTextColor(Color.parseColor("#999999"));
		datetextView13.setTextColor(Color.parseColor("#999999"));
		datetextView14.setTextColor(Color.parseColor("#999999"));

		weektextView1.setTextColor(Color.parseColor("#999999"));
		weektextView2.setTextColor(Color.parseColor("#999999"));
		weektextView3.setTextColor(Color.parseColor("#999999"));
		weektextView4.setTextColor(Color.parseColor("#999999"));
		weektextView5.setTextColor(Color.parseColor("#999999"));
		weektextView6.setTextColor(Color.parseColor("#999999"));
		weektextView7.setTextColor(Color.parseColor("#999999"));
		weektextView8.setTextColor(Color.parseColor("#999999"));
		weektextView9.setTextColor(Color.parseColor("#999999"));
		weektextView10.setTextColor(Color.parseColor("#999999"));
		weektextView11.setTextColor(Color.parseColor("#999999"));
		weektextView12.setTextColor(Color.parseColor("#999999"));
		weektextView13.setTextColor(Color.parseColor("#999999"));
		weektextView14.setTextColor(Color.parseColor("#999999"));
		switch (index) {
		case 1:
			datelayout1.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView1.setTextColor(Color.parseColor("#ffffff"));
			weektextView1.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 2:
			datelayout2.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView2.setTextColor(Color.parseColor("#ffffff"));
			weektextView2.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 3:
			datelayout3.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView3.setTextColor(Color.parseColor("#ffffff"));
			weektextView3.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 4:
			datelayout4.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView4.setTextColor(Color.parseColor("#ffffff"));
			weektextView4.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 5:
			datelayout5.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView5.setTextColor(Color.parseColor("#ffffff"));
			weektextView5.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 6:
			datelayout6.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView6.setTextColor(Color.parseColor("#ffffff"));
			weektextView6.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 7:
			datelayout7.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView7.setTextColor(Color.parseColor("#ffffff"));
			weektextView7.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 8:
			datelayout8.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView8.setTextColor(Color.parseColor("#ffffff"));
			weektextView8.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 9:
			datelayout9.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView9.setTextColor(Color.parseColor("#ffffff"));
			weektextView9.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 10:
			datelayout10.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView10.setTextColor(Color.parseColor("#ffffff"));
			weektextView10.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 11:
			datelayout11.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView11.setTextColor(Color.parseColor("#ffffff"));
			weektextView11.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 12:
			datelayout12.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView12.setTextColor(Color.parseColor("#ffffff"));
			weektextView12.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 13:
			datelayout13.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView13.setTextColor(Color.parseColor("#ffffff"));
			weektextView13.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 14:
			datelayout14.setBackgroundColor(Color.parseColor("#b02b31"));
			datetextView14.setTextColor(Color.parseColor("#ffffff"));
			weektextView14.setTextColor(Color.parseColor("#ffffff"));
			break;
		default:
			break;
		}
	}

	private void startLoop() {
		if (!isLoopPause) {
			return;
		}

		isLoopPause = false;

		isWorking = false;

		executorService.execute(new Runnable() {

			@Override
			public void run() {

				synchronized (lockObject) {
					int i = 9;
					while (!isLoopPause) {

						while (isWorking) {
							try {
								lockObject.wait(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						isWorking = true;

						loadHeadData();// 3秒更新

						i++;

						if (i == 10) {
							i = 0;
							loadKLineData(type, "0");
						}

						try {
							Thread.sleep(3 * 1000);// 30秒更新
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						isWorking = false;
					}

				}

			}
		});
	}

	ArrayList<FinaceDay> calendrearrayList = new ArrayList<FinaceDay>();

	private Object lockObject = new Object();

	private boolean isLoopPause = true;

	private boolean isWorking = false;
}
