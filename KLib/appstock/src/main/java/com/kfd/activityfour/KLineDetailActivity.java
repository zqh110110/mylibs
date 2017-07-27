package com.kfd.activityfour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.kfd.api.HttpRequest;
import com.kfd.bean.KLineDataBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

public class KLineDetailActivity extends KLineDetailBase {
	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			final KLineDetailActivity activity = (KLineDetailActivity) msg.obj;
			Bundle data = msg.getData();

			switch (msg.what) {
			case NETEORK_FAIL:
				Toast.makeText(activity, "网络异常", Toast.LENGTH_SHORT).show();
				break;

			case DATA_FAIL:
				Toast.makeText(activity, data.getString("message"), Toast.LENGTH_SHORT).show();
				break;

			case DATA_SUCCESS:
				activity.initKLine(activity.mMainChartType, activity.mSubChartType);
				break;

			case DATA_LOAD_MORE_SUCCESS:
				int size = msg.arg1;
				if (size == 0) {
					return;
				}
				// @SuppressWarnings("unchecked")
				// List<KLineDataBean> list = (List<KLineDataBean>)
				// data.getSerializable("list");

				activity.initKLine(activity.mMainChartType, activity.mSubChartType);

				this.postDelayed(new Runnable() {
					@Override
					public void run() {
						activity.isLoading = false;
					}
				}, 5000);
				break;

			case DATA_ADD_SUCCESS:
//				@SuppressWarnings("unchecked")
//				List<KLineDataBean> subList = (List<KLineDataBean>) data.getSerializable("list");
				activity.addKLineData();
				break;

			default:
				break;
			}
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 首次加载
		choiceDate(timeType);
	};

	protected void addKLineData() {
		initKLine(mMainChartType, mSubChartType);
	}

	/**
	 * 加载主图标数据
	 * 
	 * @param mainType
	 *            0:MA; 1:BOLL
	 */
	private void initKLine(int mainType, int subType) {
		try {

			// data.setData(getMainLineData(mDatas, type));

			mChart.setData(getChartData(mDatas, mainType, subType));

			mChart.setVisibleXRangeMinimum(countOfPage / MAX_SCALE);
			mChart.setVisibleXRangeMaximum(countOfPage);

			mChart.invalidate();
			calculateRange();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取x轴值
	 * 
	 * @param datas
	 * @return
	 */
	private List<String> getDates(List<KLineDataBean> datas) {
		List<String> dates = new ArrayList<String>();
		try {
			int i = 0;
			for (KLineDataBean data : datas) {
				i++;
				// dates.add(StringUtils.phpdateformat9(String.valueOf(data.dateline)));
				dates.add(String.valueOf(i));
			}
		} catch (Exception e) {

		}
		return dates;
	}

	/**
	 * 获取蜡烛图数据
	 * 
	 * @return
	 */
	private CombinedData getChartData(List<KLineDataBean> datas, int mainType, int subType) {
		CombinedData combinedData = new CombinedData(getDates(datas));

		ArrayList<CandleEntry> yVals1 = new ArrayList<CandleEntry>();

		List<ArrayList<Entry>> mainList = new ArrayList<ArrayList<Entry>>();

		List<ArrayList<Entry>> entries = new ArrayList<ArrayList<Entry>>();
		
		ArrayList<BarEntry> barentries = new ArrayList<BarEntry>();
		
		int[] colors = new int[datas.size()];

		for (int i = 0; i < datas.size(); i++) {
			KLineDataBean data = datas.get(i);

			float high_price = Float.parseFloat(data.high_price);
			float low_price = Float.parseFloat(data.low_price);
			float open_price = Float.parseFloat(data.open_price);
			float close_price = Float.parseFloat(data.close_price);

			CandleEntry entry = new CandleEntry(i, high_price, low_price, open_price, close_price);

			yVals1.add(entry);
			// ////////////////////////////////////////////////////////

			List<String> lineList = mainType == 0 ? datas.get(i).ma : datas.get(i).boll;
			for (int j = 0; j < lineList.size(); j++) {
				if (mainList.size() < j + 1) {
					mainList.add(new ArrayList<Entry>());
				}

				float price = Float.parseFloat(lineList.get(j));
				mainList.get(j).add(new Entry(price, i));
			}
			// ////////////////////////////////////////////////////////////
			List<String> subLineList = subType == 0 ? datas.get(i).macd : datas.get(i).kdj;
			for (int j = 0; j < subLineList.size(); j++) {
				if (entries.size() < j + 1) {
					entries.add(new ArrayList<Entry>());
				}
				
				float price = Float.parseFloat(subLineList.get(j));
				if(j == 2 && subType == 0)
				{
					BarEntry barEntry = new BarEntry(price, i);
					barentries.add(barEntry);
					
					colors[i] = price > 0 ? Color.parseColor("#ff2d19") : Color.parseColor("#33ff33");
					continue;
				}

				entries.get(j).add(new Entry(price, i));
			}

		}

		combinedData.setData(getCandleDataSet(yVals1));

		LineData d = new LineData();
		List<LineDataSet> list = getMainLineDatas(mainList, mainType);
		List<LineDataSet> list2 = getSubLineDatas(entries, subType);
		list.addAll(list2);
		for (LineDataSet set : list) {
			d.addDataSet(set);
		}
		combinedData.setData(d);
		
		if(subType == 0)
		{
		
		 BarDataSet set = new BarDataSet(barentries, "");
	        set.setBarSpacePercent(40f);
	        
//	        set.setColor(Color.rgb(240, 120, 124));
	        
	        set.setColors(colors);
	        
	        set.setAxisDependency(AxisDependency.RIGHT);
	        set.setDrawValues(false);
	        BarData data = new BarData();
	        data.setValueTextSize(10f);
	        data.setDrawValues(false);
	        data.addDataSet(set);
		
		combinedData.setData(data);
		}

		return combinedData;
	}

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
	@Override
	protected synchronized void getDatas(final String typename, final int timetype, final int beforetime, final int loadType) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("typename", String.valueOf(typename));
				hashMap.put("timetype", String.valueOf(timetype));
				hashMap.put("beforetime", String.valueOf(beforetime));
				String result = HttpRequest.sendGetRequestWithMd5(KLineDetailActivity.this, Define.host + "/api-market/kchart", hashMap);
				Message msg = new Message();
				if (result == null || StringUtils.isEmpty(result)) {
					msg.what = NETEORK_FAIL;
					msg.obj = KLineDetailActivity.this;
					handler.sendMessage(msg);
					return;
				}

				try {
					JSONObject resultJson = new JSONObject(result);

					boolean ret = resultJson.has("ret") ? resultJson.optBoolean("ret") : false;
					String messageStr = resultJson.has("msg") ? resultJson.optString("msg") : "";
					String time = resultJson.has("time") ? resultJson.optString("time") : "";

					JSONObject dataJson = resultJson.getJSONObject("data");

					/*
					 * if (!ret) { msg.what = DATA_FAIL; msg.obj =
					 * KLineDetailActivity.this; Bundle data = new Bundle();
					 * data.putString("message", messageStr);
					 * handler.sendMessage(msg); return; }
					 */

					JSONArray listJsonArr = dataJson.getJSONArray("list");

					List<KLineDataBean> dataList = new ArrayList<KLineDataBean>();

					for (int i = 0; i < listJsonArr.length(); i++) {
						KLineDataBean data = new KLineDataBean();

						JSONObject objJson = listJsonArr.getJSONObject(i);

						data.high_price = objJson.optString("high_price"); // 4020.60S
																			// ,
						data.low_price = objJson.optString("low_price"); // 4016.00",
						data.open_price = objJson.optString("open_price"); // 4020.00",
						data.close_price = objJson.optString("close_price"); // 4016.60",
						data.dateline = objJson.optString("dateline"); // 1439432100",
						data.price = objJson.optString("price"); // 4020.00",

						JSONArray bollJson = objJson.getJSONArray("boll");
						data.boll = new ArrayList<String>();
						for (int j = 0; j < bollJson.length(); j++) {
							data.boll.add(bollJson.optString(j));
						}

						JSONArray macdJson = objJson.getJSONArray("macd");
						data.macd = new ArrayList<String>();
						for (int k = 0; k < macdJson.length(); k++) {
							data.macd.add(macdJson.optString(k));
						}

						JSONArray maJson = objJson.getJSONArray("ma");
						data.ma = new ArrayList<String>();
						for (int k = 0; k < maJson.length(); k++) {
							data.ma.add(maJson.optString(k));
						}

						JSONArray kdjJson = objJson.getJSONArray("kdj");
						data.kdj = new ArrayList<String>();
						for (int k = 0; k < kdjJson.length(); k++) {
							data.kdj.add(kdjJson.optString(k));
						}

						dataList.add(data);
					}

					switch (loadType) {
					case 1:// 重新加载

						countOfPage = dataList.size();
						mDatas.clear();
						mDatas.addAll(dataList);
						msg.what = DATA_SUCCESS;
						msg.obj = KLineDetailActivity.this;
						handler.sendMessage(msg);
						break;

					case 2:// 加载早些数据
						mDatas.addAll(0, dataList);
						msg.what = DATA_LOAD_MORE_SUCCESS;
						msg.arg1 = dataList.size();
						msg.obj = KLineDetailActivity.this;
						Bundle data = new Bundle();
						data.putSerializable("list", (Serializable) dataList);
						msg.setData(data);
						handler.sendMessage(msg);
						break;

					case 3:// 添加新数据
						if (mDatas.size() == 0) {
							mDatas.addAll(0, dataList);
							msg.what = DATA_SUCCESS;
							msg.arg1 = dataList.size();
							msg.obj = KLineDetailActivity.this;
							Bundle data1 = new Bundle();
							data1.putSerializable("list", (Serializable) dataList);
							msg.setData(data1);
							handler.sendMessage(msg);
							break;
						}

						int index = -1;

						for (int j = 0; j < dataList.size(); j++) {

							if (mDatas.get(mDatas.size() - 1).dateline.equals(dataList.get(j).dateline)) {
								index = j;
								break;
							}
						}

						List<KLineDataBean> subList = dataList.subList(index, dataList.size());

						if(dataList != null && dataList.size() != 0)
						{
							mDatas.addAll(subList);
							msg.what = DATA_ADD_SUCCESS;
							msg.arg1 = dataList.size();
							msg.obj = KLineDetailActivity.this;
							Bundle data1 = new Bundle();
	//						data1.putSerializable("list", (Serializable) subList);
							msg.setData(data1);
						handler.sendMessage(msg);
						}

						break;

					default:
						break;
					}

				} catch (Exception e) {
					e.printStackTrace();
					msg.what = DATA_FAIL;
					msg.obj = KLineDetailActivity.this;
					Bundle data = new Bundle();
					data.putString("message", "json解析错误:" + e.getMessage());
					handler.sendMessage(msg);
				}

			}
		});
	}

	@Override
	protected void choiceDate(int dateType) {
		getDatas(MarkActivity.mTitles.get(mIndex).getTypename(), dateType, 0, 1);
	}

	@Override
	protected void changeType(String type) {
		getDatas(type, timeType, 0, 1);
	}

	@Override
	protected void changeMainTab(int type) {
		mMainChartType = type;
		initKLine(mMainChartType, mSubChartType);
	}

	@Override
	protected void changeSubTab(int type) {
		mSubChartType = type;
		if(mSubChartType == 0)
		{
			initMACD();
		}
		else
		{
			initKDJ();
		}
		initKLine(mMainChartType, mSubChartType);
	}

	@Override
	protected void chartFlingLeft() {
		getDatas(MarkActivity.mTitles.get(mIndex).getTypename(), timeType, Integer.parseInt(mDatas.get(0).dateline), 2);
	}

}
