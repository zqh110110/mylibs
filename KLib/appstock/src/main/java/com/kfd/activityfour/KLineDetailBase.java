package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.kfd.api.AppContext;
import com.kfd.api.HttpRequest;
import com.kfd.api.Tools;
import com.kfd.bean.KLineDataBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.entity.MarkPrice;
import com.kfd.entity.Price;

/**
 * k线图基类
 * 
 * @author zhanhongyi
 * 
 */
public abstract class KLineDetailBase extends BaseActivity implements OnClickListener {
	
	private static final String CHART_BG = "#EEEEEE";
	private static final String BAR_BG = "#a1a1a1";
	private static final String XLINE_BG = "#c1c1c1";
	private static final String Y_VALUE_COLOR = "#666666";
	private static final String X_VALUE_COLOR = "#666666";

	protected static final float MAIN_WEIGHT = 7f;//主图权重
	protected static final float SUB_WEIGHT = 3f;//子图权重
	protected static final float BAR_WEIGHT = 1f;//条条权重
	protected static final float WEIGHT_SUM = MAIN_WEIGHT + SUB_WEIGHT + BAR_WEIGHT;//权重数

	protected static final float MAX_SCALE = 2;

	private static final int MORE_CALCULATE = 0;// 计算范围时多计算的点数

	protected static int[] sunbChartColors = { Color.parseColor("#12eabf"), Color.parseColor("#ff4c94"), Color.parseColor("#ffb400") };
	protected static int[] mainChartColors = { Color.parseColor("#12eabf"), Color.parseColor("#ff4c94"), Color.parseColor("#ffb400") };

	protected static final int NETEORK_FAIL = 0x10;
	protected static final int DATA_FAIL = 0x11;
	protected static final int DATA_SUCCESS = 0x12;
	protected static final int PRICE_DATA_SUCCESS = 0x13;
	protected static final int DATA_LOAD_MORE_SUCCESS = 0x14;
	protected static final int DATA_ADD_SUCCESS = 0x15;

	protected int mIndex = 0;

	protected int timeType = 1;

	private TextView title_text;

	/**
	 * 主图标
	 */
	protected CombinedChart mChart;

	private ImageView leftImageView, rightImageView;
	private RelativeLayout oneminutelayout, fifteenlayout, halfhourlayout, hourlayout;
	private ImageView oneminuteImageView, fifteenImageView, halfhourImageView, hourImageView;
	private TextView oneminuteTextView, fifteenTextView, halfhourTextView, hourTextView;
	private TextView priceTextView, pricechangeTextView, priceamountTextView, highTextView, lowTextView, yestodaycloseTextView, todayopenTextView;

	private RadioGroup group1, group2;

	/**
	 * 数据
	 */
	protected List<KLineDataBean> mDatas = new ArrayList<KLineDataBean>();

	/**
	 * 图标一页显示的最多x值
	 */
	protected int countOfPage = 0;

	/**
	 * 0:MA; 1:BOLL
	 */
	protected int mMainChartType = 0;
	protected int mSubChartType = 0;

	protected TextView main_yAxText1, main_yAxText2, main_yAxText3, main_yAxText4, main_yAxText5;
	protected TextView sub_yAxText1, sub_yAxText2, sub_yAxText3, sub_yAxText4, sub_yAxText5;
	protected TextView xAxText1, xAxText2, xAxText3, xAxText4, xAxText5, xAxText6, xAxText7;
	
	protected View sub_Line1, sub_Line2, sub_Line3;
	

	protected TextView mainTopValue1, mainTopValue2, mainTopValue3, mainTopValue4;
	protected TextView subTopValue1, subTopValue2, subTopValue3, subTopValue4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.klinedetail);

		mIndex = getIntent().getIntExtra("index", 0);

		initUI();
		initPriceUI();

		initChartUI();
		setTitleInfo(MarkActivity.mTitles.get(mIndex).getName());
	}

	@Override
	protected void onResume() {
		super.onResume();
		startLoop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		isLoopPause = true;
	}

	private void initChartUI() {
		sub_Line1 = findViewById(R.id.sub_line1);
		sub_Line2 = findViewById(R.id.sub_line2);
		sub_Line3 = findViewById(R.id.sub_line3);
		
		main_yAxText1 = (TextView) findViewById(R.id.mainAx1);
		main_yAxText2 = (TextView) findViewById(R.id.mainAx2);
		main_yAxText3 = (TextView) findViewById(R.id.mainAx3);
		main_yAxText4 = (TextView) findViewById(R.id.mainAx4);
		main_yAxText5 = (TextView) findViewById(R.id.mainAx5);
		
		sub_yAxText1 = (TextView) findViewById(R.id.subAx1);
		sub_yAxText2 = (TextView) findViewById(R.id.subAx2);
		sub_yAxText3 = (TextView) findViewById(R.id.subAx3);
		sub_yAxText4 = (TextView) findViewById(R.id.subAx4);
		sub_yAxText5 = (TextView) findViewById(R.id.subAx5);
		
		main_yAxText1.setTextColor(Color.parseColor(Y_VALUE_COLOR));
		main_yAxText2.setTextColor(Color.parseColor(Y_VALUE_COLOR));
		main_yAxText3.setTextColor(Color.parseColor(Y_VALUE_COLOR));
		main_yAxText4.setTextColor(Color.parseColor(Y_VALUE_COLOR));
		main_yAxText5.setTextColor(Color.parseColor(Y_VALUE_COLOR));
		
		sub_yAxText1.setTextColor(Color.parseColor(Y_VALUE_COLOR));
		sub_yAxText2.setTextColor(Color.parseColor(Y_VALUE_COLOR));
		sub_yAxText3.setTextColor(Color.parseColor(Y_VALUE_COLOR));
		sub_yAxText4.setTextColor(Color.parseColor(Y_VALUE_COLOR));
		sub_yAxText5.setTextColor(Color.parseColor(Y_VALUE_COLOR));
		

		xAxText1 = (TextView) findViewById(R.id.mainX1);
		xAxText2 = (TextView) findViewById(R.id.mainX2);
		xAxText3 = (TextView) findViewById(R.id.mainX3);
		xAxText4 = (TextView) findViewById(R.id.mainX4);
		xAxText5 = (TextView) findViewById(R.id.mainX5);
		xAxText6 = (TextView) findViewById(R.id.mainX6);
		xAxText7 = (TextView) findViewById(R.id.mainX7);
		
		xAxText1.setTextColor(Color.parseColor(X_VALUE_COLOR));
		xAxText2.setTextColor(Color.parseColor(X_VALUE_COLOR));
		xAxText3.setTextColor(Color.parseColor(X_VALUE_COLOR));
		xAxText4.setTextColor(Color.parseColor(X_VALUE_COLOR));
		xAxText5.setTextColor(Color.parseColor(X_VALUE_COLOR));
		xAxText6.setTextColor(Color.parseColor(X_VALUE_COLOR));
		xAxText7.setTextColor(Color.parseColor(X_VALUE_COLOR));
		
		initMACD();
	}

	private void initPriceUI() {
		priceTextView = (TextView) findViewById(R.id.pricetextView1);
		pricechangeTextView = (TextView) findViewById(R.id.pricechangetextView2);
		priceamountTextView = (TextView) findViewById(R.id.priceamounttextView2);
		highTextView = (TextView) findViewById(R.id.hightextView4);
		lowTextView = (TextView) findViewById(R.id.lowtextView4);
		yestodaycloseTextView = (TextView) findViewById(R.id.yestodayclosetextView4);
		todayopenTextView = (TextView) findViewById(R.id.todayopentextView4);
	}

	private void setTitleInfo(String type) {
		title_text.setText(type);
		leftImageView.setVisibility(mIndex == 0 ? View.GONE : View.VISIBLE);
		rightImageView.setVisibility(mIndex == (MarkActivity.mTitles.size() - 1) ? View.GONE : View.VISIBLE);
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.title_text);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		leftImageView = (ImageView) findViewById(R.id.leftimageView);
		rightImageView = (ImageView) findViewById(R.id.rightimageView);
		leftImageView.setOnClickListener(this);
		rightImageView.setOnClickListener(this);
		oneminuteImageView = (ImageView) findViewById(R.id.oneimageView);
		oneminutelayout = (RelativeLayout) findViewById(R.id.oneminutelayout);
		fifteenImageView = (ImageView) findViewById(R.id.fifteenimageView);
		fifteenlayout = (RelativeLayout) findViewById(R.id.fifteenlayout);

		halfhourImageView = (ImageView) findViewById(R.id.halfhourimageView);
		halfhourlayout = (RelativeLayout) findViewById(R.id.halfhourlayout);

		hourImageView = (ImageView) findViewById(R.id.hourimageView);
		hourlayout = (RelativeLayout) findViewById(R.id.hourlayout);

		oneminutelayout.setOnClickListener(this);
		fifteenlayout.setOnClickListener(this);
		halfhourlayout.setOnClickListener(this);
		hourlayout.setOnClickListener(this);

		oneminuteTextView = (TextView) findViewById(R.id.onetextView1);
		fifteenTextView = (TextView) findViewById(R.id.fifteentextView1);
		halfhourTextView = (TextView) findViewById(R.id.halfhourtextView1);
		hourTextView = (TextView) findViewById(R.id.hourtextView1);

		initMainChart();

		group1 = (RadioGroup) findViewById(R.id.radioGroup);
		group2 = (RadioGroup) findViewById(R.id.radioGroup2);
		
		findViewById(R.id.x_line).setBackgroundColor(Color.parseColor(XLINE_BG));
		findViewById(R.id.chart_bg).setBackgroundColor(Color.parseColor(CHART_BG));
		
		group1.setBackgroundColor(Color.parseColor(BAR_BG));
		group2.setBackgroundColor(Color.parseColor(BAR_BG));

		group1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				changeMainTab(checkedId == R.id.mall ? 0 : 1);
			}
		});
		group2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				changeSubTab(checkedId == R.id.macd ? 0 : 1);
			}
		});

		mainTopValue1 = (TextView) findViewById(R.id.main_top1);
		mainTopValue2 = (TextView) findViewById(R.id.main_top2);
		mainTopValue3 = (TextView) findViewById(R.id.main_top3);
		mainTopValue4 = (TextView) findViewById(R.id.main_top4);
		
		subTopValue1 = (TextView) findViewById(R.id.sub_top1);
		subTopValue2 = (TextView) findViewById(R.id.sub_top2);
		subTopValue3 = (TextView) findViewById(R.id.sub_top3);
		subTopValue4 = (TextView) findViewById(R.id.sub_top4);

		mainTopValue1.setTextColor(Color.parseColor(X_VALUE_COLOR));
		mainTopValue2.setTextColor(mainChartColors[0]);
		mainTopValue3.setTextColor(mainChartColors[1]);
		mainTopValue4.setTextColor(mainChartColors[2]);
		
		subTopValue1.setTextColor(Color.parseColor(X_VALUE_COLOR));
		subTopValue2.setTextColor(mainChartColors[0]);
		subTopValue3.setTextColor(mainChartColors[1]);
		subTopValue4.setTextColor(mainChartColors[2]);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.oneminutelayout:
			setTimeUI(1);
			choiceDate(1);
			break;
		case R.id.fifteenlayout:
			setTimeUI(2);
			choiceDate(2);
			break;
		case R.id.halfhourlayout:
			setTimeUI(3);
			choiceDate(3);
			break;
		case R.id.hourlayout:
			setTimeUI(4);
			choiceDate(4);
			break;
		case R.id.button1:
			if (!AppContext.getInstance().isLogin()) {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				return;
			}

			Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
			intent.putExtra("typename", MarkActivity.mTitles.get(mIndex).getTypename());
			startActivity(intent);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.leftimageView:
			if(mIndex == 0)
			{
				return;
			}
			
			mIndex --;

			setTitleInfo(MarkActivity.mTitles.get(mIndex).getName());
			changeType(MarkActivity.mTitles.get(mIndex).getTypename());
			break;

		case R.id.rightimageView: {
			if(mIndex == (MarkActivity.mTitles.size() - 1))
			{
				return;
			}
			
			mIndex ++;

			setTitleInfo(MarkActivity.mTitles.get(mIndex).getName());
			changeType(MarkActivity.mTitles.get(mIndex).getTypename());
		}
			break;
		}
	}

	private void setTimeUI(int index) {
		timeType = index;
		oneminuteImageView.setBackgroundColor(Color.parseColor("#c3c3c3"));
		fifteenImageView.setBackgroundColor(Color.parseColor("#c3c3c3"));
		halfhourImageView.setBackgroundColor(Color.parseColor("#c3c3c3"));
		hourImageView.setBackgroundColor(Color.parseColor("#c3c3c3"));
		oneminuteTextView.setTextColor(Color.parseColor("#9a9a9a"));
		fifteenTextView.setTextColor(Color.parseColor("#9a9a9a"));
		halfhourTextView.setTextColor(Color.parseColor("#9a9a9a"));
		hourTextView.setTextColor(Color.parseColor("#9a9a9a"));
		switch (index) {
		case 1:
			oneminuteImageView.setBackgroundColor(Color.parseColor("#fc4231"));
			oneminuteTextView.setTextColor(Color.parseColor("#fc4231"));
			break;
		case 2:
			fifteenImageView.setBackgroundColor(Color.parseColor("#fc4231"));
			fifteenTextView.setTextColor(Color.parseColor("#fc4231"));
			break;
		case 3:
			halfhourImageView.setBackgroundColor(Color.parseColor("#fc4231"));
			halfhourTextView.setTextColor(Color.parseColor("#fc4231"));
			break;
		case 4:
			hourImageView.setBackgroundColor(Color.parseColor("#fc4231"));
			hourTextView.setTextColor(Color.parseColor("#fc4231"));
			break;
		default:
			break;
		}
	}

	private void loadPrice() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				Message message = new Message();
				try {
					LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
					hashMap.put("typename", MarkActivity.mTitles.get(mIndex).getTypename());
					String result = HttpRequest.sendGetRequestWithMd5(KLineDetailBase.this, Define.host + "/api-market/getprice", hashMap);
					if (result == null || StringUtils.isEmpty(result)) {
						message.what = NETEORK_FAIL;
						message.obj = KLineDetailBase.this;
						updateUIHandler.sendMessage(message);
					} else {
						JSONObject jsonObject = new JSONObject(result);
						JSONObject dataJsonObject = jsonObject.optJSONObject("data");
						JSONObject priceJsonObject = dataJsonObject.optJSONObject("price");
						Price price = new Price();
						price.setChange_num(priceJsonObject.optString("change_num"));
						price.setChange_pro(priceJsonObject.optString("change_pro"));
						price.setHigh_price(priceJsonObject.optString("high_price"));
						price.setLow_price(priceJsonObject.optString("low_price"));
						price.setName(priceJsonObject.optString("name"));
						price.setPrice(priceJsonObject.optString("price"));
						price.setStart_price(priceJsonObject.optString("start_price"));
						price.setTypename(priceJsonObject.optString("typename"));
						price.setYest_num(priceJsonObject.optString("yest_num"));

						message.what = PRICE_DATA_SUCCESS;
						message.obj = KLineDetailBase.this;
						Bundle data = new Bundle();
						data.putSerializable("price", price);
						message.setData(data);
						updateUIHandler.sendMessage(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
					message.what = DATA_FAIL;
					message.obj = KLineDetailBase.this;
					Bundle data = new Bundle();
					data.putString("message", e.getMessage());
					message.setData(data);
					updateUIHandler.sendMessage(message);
				}

			}
		});
	}

	/**
	 * 设置头部价格值
	 * 
	 * @param price
	 */
	private void setPriceData(Price price) {
		priceTextView.setText(price.getPrice());
		if (price.getPrice().startsWith("-")) {
			priceTextView.setTextColor(Color.parseColor("#53b84e"));
		} else {
			priceTextView.setTextColor(Color.parseColor("#fc4231"));
		}
		pricechangeTextView.setText(price.getChange_num());
		if (price.getChange_num().startsWith("-")) {
			pricechangeTextView.setTextColor(Color.parseColor("#53b84e"));
		} else {
			pricechangeTextView.setTextColor(Color.parseColor("#fc4231"));
		}
		priceamountTextView.setText(price.getChange_pro());
		if (price.getChange_pro().startsWith("-")) {
			priceamountTextView.setTextColor(Color.parseColor("#53b84e"));
		} else {
			priceamountTextView.setTextColor(Color.parseColor("#fc4231"));
		}
		highTextView.setText(price.getHigh_price());
		if (price.getHigh_price().startsWith("-")) {
			highTextView.setTextColor(Color.parseColor("#53b84e"));
		} else {
			highTextView.setTextColor(Color.parseColor("#fc4231"));
		}
		lowTextView.setText(price.getLow_price());
		if (price.getLow_price().startsWith("-")) {
			lowTextView.setTextColor(Color.parseColor("#53b84e"));
		} else {
			lowTextView.setTextColor(Color.parseColor("#fc4231"));
		}
		yestodaycloseTextView.setText(price.getYest_num());
		if (price.getYest_num().startsWith("-")) {
			yestodaycloseTextView.setTextColor(Color.parseColor("#53b84e"));
		} else {
			yestodaycloseTextView.setTextColor(Color.parseColor("#fc4231"));
		}
		todayopenTextView.setText(price.getStart_price());
		if (price.getStart_price().startsWith("-")) {
			todayopenTextView.setTextColor(Color.parseColor("#53b84e"));
		} else {
			todayopenTextView.setTextColor(Color.parseColor("#fc4231"));
		}
	}

	protected static Handler updateUIHandler = new Handler() {
		public void handleMessage(Message msg) {
			KLineDetailBase activity = (KLineDetailBase) msg.obj;
			Bundle data = msg.getData();

			switch (msg.what) {
			case NETEORK_FAIL:
				Toast.makeText(activity, "网络异常", Toast.LENGTH_SHORT).show();
				break;

			case DATA_FAIL:
				Toast.makeText(activity, data.getString("message"), Toast.LENGTH_SHORT).show();
				break;

			case PRICE_DATA_SUCCESS:
				activity.setPriceData((Price) data.getSerializable("price"));
				break;

			default:
				break;
			}
		};
	};

	private int lastDx = 0;
	private int lastMinX = 0;

	/**
	 * 初始化主图表
	 */
	private void initMainChart() {
		mChart = (CombinedChart) findViewById(R.id.chart1);

//		mChart.setBackgroundColor(Color.parseColor("#2a2e36"));
		mChart.setBackgroundColor(Color.TRANSPARENT);
		mChart.setDrawGridBackground(false);
		mChart.setDrawBarShadow(false);

		mChart.setDescription("");
		
		// draw bars behind lines
		mChart.setDrawOrder(new DrawOrder[] { DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER });
		
		// enable value highlighting
		mChart.setHighlightEnabled(true);

		// enable touch gestures
		mChart.setTouchEnabled(true);

		// enable scaling and dragging
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(true);
		mChart.setDrawGridBackground(false);

		// if disabled, scaling can be done on x- and y-axis separately
		mChart.setPinchZoom(true);

		// set an alternative background color

		mChart.getAxisRight().setEnabled(true);
		
		mChart.fitScreen();
		
		mChart.setHighlightPerDragEnabled(false);

		mChart.setScaleYEnabled(false);

		Legend l = mChart.getLegend();
		l.setEnabled(false);

		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setStartAtZero(false);
		leftAxis.setPosition(YAxisLabelPosition.INSIDE_CHART);
		leftAxis.setDrawGridLines(false);
		leftAxis.setSpaceBottom(Tools.dip2px(context, 30));
		leftAxis.setEnabled(false);

		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setStartAtZero(false);
		rightAxis.setPosition(YAxisLabelPosition.INSIDE_CHART);
		rightAxis.setDrawGridLines(false);
		
		rightAxis.setEnabled(false);
		rightAxis.setSpaceTop(Tools.dip2px(context, 30));

		XAxis xAxis = mChart.getXAxis();

		xAxis.setPosition(XAxisPosition.BOTTOM_INSIDE);
		xAxis.setDrawLabels(false);
		// xAxis.setDrawGridLines(true);
		// xAxis.setGridColor(Color.parseColor("#E1E1E1"));
		// xAxis.setLabelsToSkip(1);
		xAxis.setDrawGridLines(false);
		xAxis.setAvoidFirstLastClipping(true);
		xAxis.setSpaceBetweenLabels(5);
		xAxis.setEnabled(false);
		
		mChart.setOnChartGestureListener(new OnChartGestureListener() {
			@Override
			public void onChartTranslate(MotionEvent me, float dX, float dY) {
				int minX = mChart.getLowestVisibleXIndex();
				int maxX = mChart.getHighestVisibleXIndex();

				if (lastDx == (int) dX)// 过滤掉太小的滑动
				{
					return;
				}

				lastDx = (int) dX;

				if (lastMinX != minX && maxX < mDatas.size() - 1) {

					calculateRange();
					// calculateSubRange(minX, minX + countOfPage,
					// mSubChartType);
					
					lastMinX = minX;

				}

				if (dX > 1000 && mChart.getLowestVisibleXIndex() == 0 && !isLoading) {
					isLoading = true;
					chartFlingLeft();
					return;
				}
			}

			@Override
			public void onChartSingleTapped(MotionEvent me) {
				// int minX = mChart.getLowestVisibleXIndex();
				// if (lastMinX != minX && minX < mDatas.size() - countOfPage) {
				// calculateMainRange(minX, minX + countOfPage);
				// calculateSubRange(minX, minX + countOfPage, mSubChartType);
				// }
				
				calculateRange();
			}

			@Override
			public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
			}

			@Override
			public void onChartLongPressed(MotionEvent me) {

			}

			@Override
			public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
				if (me2.getX() - me1.getX() > 200) {
					chartFlingLeft();
				}
			}

			@Override
			public void onChartDoubleTapped(MotionEvent me) {

			}
		});
	}

	protected boolean isLoading = false;

	protected void calculateRange() {

		int start = mChart.getLowestVisibleXIndex();
		int end = mChart.getHighestVisibleXIndex();
		
		mainTopValue1.setText(mMainChartType == 0 ? "(5,10,20)" : "(20,2)");
		List<String> dataList =  mMainChartType == 0 ? mDatas.get(start).ma : mDatas.get(start).boll;
		mainTopValue2.setText((mMainChartType == 0 ? "MA1:" : "UP:") + dataList.get(0));
		mainTopValue3.setText((mMainChartType == 0 ? "MA2:" : "MID:") + dataList.get(1));
		mainTopValue4.setText((mMainChartType == 0 ? "MA3:" : "DOWN:") + dataList.get(2));
		
		subTopValue1.setText(mSubChartType == 0 ? "(12,26,58)" : "(9,3,3)");
		List<String> subList =  mSubChartType == 0 ? mDatas.get(start).macd : mDatas.get(start).kdj;
		subTopValue2.setText((mSubChartType == 0 ? "DEA:" : "K:") + subList.get(0));
		subTopValue3.setText((mSubChartType == 0 ? "DIF:" : "D:") + subList.get(1));
		subTopValue4.setText((mSubChartType == 0 ? "MACD:" : "J:") + subList.get(2));
		
		//蜡烛图的最大值与最小值
		float mainMinValue = 0, mainMaxValue = 0;
		//子图表的最大值与最小值
		float subMinValue = 0, subMaxValue = 0;

		int realStart = (start - MORE_CALCULATE) < 0 ? 0 : (start - MORE_CALCULATE);
		int realEnd = (end + MORE_CALCULATE) > mDatas.size() ? mDatas.size() : (end + MORE_CALCULATE);
		
		for (int i = realStart; i < realEnd; i++) {

			KLineDataBean data = mDatas.get(i);
			
			float mainMinAndMax[] = data.getMainMinAndMax(mMainChartType);
			mainMinValue = mainMinValue == 0f ? mainMinAndMax[0] : Math.min(mainMinAndMax[0], mainMinValue);
			mainMaxValue = mainMaxValue == 0f ? mainMinAndMax[1] : Math.max(mainMinAndMax[1], mainMaxValue);
			
			
			float subMinAndMax[] = data.getSubMinAndMax(mSubChartType);
			subMinValue = subMinValue == 0f ? subMinAndMax[0] : Math.min(subMinAndMax[0], subMinValue);
			subMaxValue = subMaxValue == 0f ? subMinAndMax[1] : Math.max(subMinAndMax[1], subMaxValue);

		}
		
		float newMainMin = mainMaxValue - (mainMaxValue - mainMinValue) * (WEIGHT_SUM / MAIN_WEIGHT);
		mChart.getAxisLeft().setAxisMinValue(newMainMin);
		mChart.getAxisLeft().setAxisMaxValue(mainMaxValue);
		
		if(mSubChartType == 0)//固定0位置
		{
			subMaxValue = Math.max(Math.abs(subMinValue), Math.abs(subMaxValue));
			subMinValue = - subMaxValue;
		}
		
		float newMaxValue = (subMaxValue - subMinValue) * (WEIGHT_SUM / SUB_WEIGHT) + subMinValue;
		mChart.getAxisRight().setAxisMinValue(subMinValue);
		mChart.getAxisRight().setAxisMaxValue(newMaxValue);
		
		setMainYValue(mainMinValue, mainMaxValue);
		setSubYValue(subMinValue, subMaxValue);
		setXValue(start, end);
		mChart.notifyDataSetChanged();
	}

	// 设置Y轴值
	private void setMainYValue(float minVaule, float maxVaule) {
		main_yAxText1.setText(Tools.floatToString(maxVaule));
		main_yAxText2.setText(Tools.floatToString((maxVaule - minVaule) * 3 / 4 + minVaule));
		main_yAxText3.setText(Tools.floatToString((maxVaule - minVaule) * 2 / 4 + minVaule));
		main_yAxText4.setText(Tools.floatToString((maxVaule - minVaule) / 4 + minVaule));
		main_yAxText5.setText(Tools.floatToString(minVaule));
	}
	
	// 设置Y轴值
	private void setSubYValue(float minVaule, float maxVaule) {
		sub_yAxText1.setText(Tools.floatToString(maxVaule));
		sub_yAxText2.setText(Tools.floatToString((maxVaule - minVaule) * 3 / 4 + minVaule));
		sub_yAxText3.setText(Tools.floatToString((maxVaule - minVaule) * 2 / 4 + minVaule));
		sub_yAxText4.setText(Tools.floatToString((maxVaule - minVaule) / 4 + minVaule));
		sub_yAxText5.setText(Tools.floatToString(minVaule));
	}

	// 设置X轴值
	private void setXValue(int start, int end) {

		long time_start = 0;
		long time_end = 0;
		String endDate = "";
		try {

			String startDate = mDatas.get(start).dateline;
			endDate = mDatas.get(end - 1).dateline;
			time_start = Long.parseLong(startDate);
			time_end = Long.parseLong(endDate);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		long time2 = time_start + (time_end - time_start) / 6;
		long time3 = time_start + (time_end - time_start) / 3;
		long time4 = time_start + (time_end - time_start) / 2;
		long time5 = time_start + (time_end - time_start) * 2 / 3;
		long time6 = time_start + (time_end - time_start) * 5 / 6;

		xAxText1.setText(StringUtils.phpdateformat11(String.valueOf(time_start)));
		xAxText2.setText(StringUtils.phpdateformat4(String.valueOf(time2)));
		xAxText3.setText(StringUtils.phpdateformat4(String.valueOf(time3)));
		xAxText4.setText(StringUtils.phpdateformat4(String.valueOf(time4)));
		xAxText5.setText(StringUtils.phpdateformat4(String.valueOf(time5)));
		xAxText6.setText(StringUtils.phpdateformat4(String.valueOf(time6)));
		xAxText7.setText(StringUtils.phpdateformat11(String.valueOf(endDate)));

	}

	/**
	 * 选择时间类型
	 * 
	 * @param dateType
	 */
	protected abstract void choiceDate(int dateType);

	/**
	 * 切换类型
	 * 
	 * @param type
	 */
	protected abstract void changeType(String type);

	/**
	 * 切换 MA/BOLL type: 0:MA; 1:BOLL
	 */
	protected abstract void changeMainTab(int type);

	/**
	 * 切换MACD/KDJ type: 0:MACD; 1:KDJ
	 */
	protected abstract void changeSubTab(int type);

	/**
	 * 图表左滑
	 */
	protected abstract void chartFlingLeft();

	/**
	 * 加载数据
	 * 
	 * @param typename
	 *            伦敦金/伦敦银...
	 * @param timetype
	 *            时间类型: 1:5分钟;2:15分钟;3：30分钟;4:小时
	 * @param beforetime
	 *            最早时间;第一次为为0;需要传最早时间拉取更早数据
	 * @param loadType
	 *            加载类型:1、重新加载；2、加载更早数据;3、获取最新数据
	 */
	protected abstract void getDatas(String typename, int timetype, int beforetime, int loadType);

	/**
	 * 获取主图蜡烛图数据
	 * 
	 * @param yVals1
	 * @return
	 */
	protected CandleData getCandleDataSet(List<CandleEntry> yVals1) {
		// create a data object with the datasets
		CandleDataSet set1 = new CandleDataSet(yVals1, "");
		set1.setAxisDependency(AxisDependency.LEFT);
		// set1.setColor(Color.rgb(80, 80, 80));
		set1.setShadowColor(Color.WHITE);
		set1.setShadowWidth(0.7f);
		set1.setDecreasingColor(Color.parseColor("#33ff33"));
		set1.setDecreasingPaintStyle(Paint.Style.FILL);
		set1.setIncreasingColor(Color.parseColor("#ff2d19"));
		set1.setIncreasingPaintStyle(Paint.Style.FILL);

		set1.setShadowColorSameAsCandle(true);

		set1.setHighlightEnabled(true);
		set1.setDrawValues(false);

		set1.setHighlightLineWidth(1);
		set1.setHighLightColor(Color.parseColor("#b1b1b1"));

		CandleData data = new CandleData();
		data.addDataSet(set1);

		return data;
	}

	/**
	 * 主图线数据
	 * 
	 * @param mainLineLists
	 * @param mainType
	 * @return
	 */
	protected List<LineDataSet> getMainLineDatas(List<ArrayList<Entry>> mainLineLists, int mainType) {
		List<LineDataSet> list = new ArrayList<LineDataSet>();
		for (int i = 0; i < mainLineLists.size(); i++) {
			LineDataSet set = new LineDataSet(mainLineLists.get(i), "");
			set.setDrawFilled(false); // 设置包括的范围区域填充颜色
			set.setAxisDependency(AxisDependency.LEFT);
			set.setColor(mainChartColors[i]);
			set.setCircleColor(Color.WHITE);
			set.setLineWidth(1f);
			set.setCircleSize(1f);
			set.setHighLightColor(Color.rgb(244, 117, 117));
			set.setDrawCircleHole(false);

			set.setDrawCircles(false); // 设置有圆点
			set.setDrawValues(false);
			set.setDrawCubic(true); // 设置曲线为圆滑的线

			set.setDrawCircleHole(false);
			set.setHighlightEnabled(false);

			list.add(set);

		}

		return list;
	}

	/**
	 * 子图线数据
	 * 
	 * @param mainLineLists
	 * @param mainType
	 * @return
	 */
	protected List<LineDataSet> getSubLineDatas(List<ArrayList<Entry>> mainLineLists, int subType) {
		List<LineDataSet> list = new ArrayList<LineDataSet>();

		for (int i = 0; i < mainLineLists.size(); i++) {

			LineDataSet set = new LineDataSet(mainLineLists.get(i), "");
			set.setDrawFilled(false); // 设置包括的范围区域填充颜色
			set.setAxisDependency(AxisDependency.RIGHT);
			set.setColor(subType == 0 ? sunbChartColors[i] : sunbChartColors[i]);
			set.setCircleColor(Color.WHITE);
			set.setLineWidth(1f);
			set.setCircleSize(1f);
			set.setHighLightColor(Color.rgb(244, 117, 117));
			set.setDrawCircleHole(false);

			set.setDrawCircles(false); // 设置有圆点
			set.setDrawValues(false);
			set.setDrawCubic(true); // 设置曲线为圆滑的线
			set.setDrawCircleHole(false);
			set.setHighlightEnabled(false);
			list.add(set);
		}
		return list;
	}

	protected BarData getBarData(List<BarEntry> list) {
		BarDataSet set = new BarDataSet(list, "");
		set.setBarSpacePercent(40f);
		set.setColor(Color.rgb(240, 120, 124));

		BarData data = new BarData();
		data.addDataSet(set);
		return null;
	}

	private Object lockObject = new Object();

	private boolean isLoopPause = true;

	private boolean isWorking = false;

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

						loadPrice();// 3秒更新

						i++;

						if (i == 30) {
							i = 0;
							 getDatas(MarkActivity.mTitles.get(mIndex).getTypename(), timeType, 0, 3);//获取最新数据
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

	protected void initMACD() {
		sub_yAxText2.setVisibility(View.INVISIBLE);
		sub_yAxText3.setVisibility(View.INVISIBLE);
		sub_yAxText4.setVisibility(View.INVISIBLE);
		
		sub_Line1.setVisibility(View.INVISIBLE);
		sub_Line3.setVisibility(View.INVISIBLE);
		
		sub_yAxText1.setVisibility(View.VISIBLE);
		sub_yAxText5.setVisibility(View.VISIBLE);
	}
	
	protected void initKDJ() {
		sub_yAxText2.setVisibility(View.VISIBLE);
		sub_yAxText3.setVisibility(View.VISIBLE);
		sub_yAxText4.setVisibility(View.VISIBLE);
		
		sub_Line1.setVisibility(View.VISIBLE);
		sub_Line3.setVisibility(View.VISIBLE);
		
		sub_yAxText1.setVisibility(View.INVISIBLE);
		sub_yAxText5.setVisibility(View.INVISIBLE);
	}
}
