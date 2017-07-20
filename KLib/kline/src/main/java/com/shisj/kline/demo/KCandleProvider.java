package com.shisj.kline.demo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.kline.CandleData;
import com.shisj.kline.chart.kline.CandleDataProvider;
import com.shisj.kline.chart.kline.KLine;

/**
 * 数据提供者，需要自己定义
 * 
 * @author shishengjie
 * 
 */
public class KCandleProvider extends CandleDataProvider {

	ArrayList<CandleData> datas;
	public HttpGet httpGet = null;
	public HttpResponse response = null;
	float lastClosePrice = 0f;
	String serverAddr = null;

	private int _type;
	private int _num;

	public KCandleProvider(String curr, String fmt, String type) {
		this.setCurrType(curr);
		this.setTimeFormate(fmt);
		this.setDateType(type);
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;

		char a = this.dateType.charAt(0);
		if (a == '0') {
			this._type = Calendar.MINUTE;
			this._num = 1;
		} else if (a == '1') {
			this._type = Calendar.MINUTE;
			this._num = 1;
		} else if (a == '2') {
			this._type = Calendar.MINUTE;
			this._num = 5;
		} else if (a == '3') {
			this._type = Calendar.MINUTE;
			this._num = 30;
		} else if (a == '4') {
			this._type = Calendar.HOUR;
			this._num = 2;
		} else if (a == '5') {
			this._type = Calendar.DAY_OF_MONTH;
			this._num = 1;
		} else if (a == '6') {
			this._type = Calendar.WEEK_OF_YEAR;
			this._num = 1;
		} else if (a == '7') {
			this._type = Calendar.MONTH;
			this._num = 1;
		}

	}

	private float randSeg(){
		return 10*new Random().nextFloat();
	}
	private CandleData rand(){
		CandleData candle = new CandleData();
		float base=2792.89f;
		candle.setClosing(base + randSeg());
		candle.setHighest(base + randSeg());
		candle.setLowest(base );
		candle.setOpened(base + randSeg());
		candle.setDate("20160520", "yyyyMMdd");
		return candle;
	}
	
	private List<CandleData> parseUpdateData(String json) {
		List<CandleData> datas = new ArrayList<CandleData>();
		datas.add(rand());
		/*JSONObject main = (JSONObject) JSONObject.parse(json);
		String c1 = main.getString("Candle1");
		String c2 = main.getString("Candle2");
		JSONArray candle1 = (JSONArray) JSONArray.parse(c1);
		JSONArray candle2 = (JSONArray) JSONArray.parse(c2);
		if (candle1 != null) {
			CandleData data = fillCandleData(candle1);
			if (data != null) {
				datas.add(data);
			}
			Log.e("prt", "candle1:" + debug(data));
		}
		if (candle2 != null) {
			CandleData data = fillCandleData(candle2);
			if (data != null) {
				datas.add(data);
			}
			Log.e("prt", "candle2:" + debug(data));
		}*/
		return datas;
	}

	private String debug(CandleData data) {
		SimpleDateFormat fmt = new SimpleDateFormat(getTimeFormate());
		if (data == null)
			return "null";
		return fmt.format(data.getDate()) + data.getOpened() + " "
				+ data.getClosing() + " " + data.getHighest() + " "
				+ data.getLowest();
	}

	private CandleData fillCandleData(JSONArray array) {
		CandleData data = new CandleData();
		data.setOpened(array.getFloatValue(0));
		data.setClosing(array.getFloatValue(1));
		data.setHighest(array.getFloatValue(2));
		data.setLowest(array.getFloatValue(3));
		String date = array.getString(4);
		if (date == null)
			return null;
		data.setDate(date, "yyyyMMddHHmm");
		return data;
	}

	private List<CandleData> parseChartData(String json) {
		String last = null;
		List<CandleData> datas = new ArrayList<CandleData>();
		try {
			JSONObject main = (JSONObject) JSONObject.parse(json);
			try {
				Object yts = main.get("YstClosePrice");
				if (yts != null)
					last = String.valueOf(yts);
			} catch (Exception e) {
			}
			if (last != null && last.trim().length() > 0) {
				this.lastClosePrice = main.getFloat("YstClosePrice");
			} else {
				this.lastClosePrice = 0f;
			}
			JSONArray array = main.getJSONArray("List");
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				CandleData data = new CandleData();
				data.setOpened(obj.getFloatValue("OpenPrice"));
				data.setClosing(obj.getFloatValue("ClosePrice"));
				data.setHighest(obj.getFloatValue("HighPrice"));
				data.setLowest(obj.getFloatValue("LowPrice"));
				data.setDate(obj.getString("EffectTs"), "yyyyMMddHHmm");
				datas.add(data);
			}
		} catch (Exception e) {
			Log.e("prt", "get msg:" + e.getMessage());
		}
		return datas;
	}

	@Override
	public List<CandleData> getInitData() {
		ArrayList<CandleData> datas = new ArrayList<CandleData>();

		String str1 = "[['2792.89','2825.48','2825.95','2785.08','2016-05-20'],['2802.31','2806.91','2829.40','2801.55','2016-05-19'],['2828.18','2807.51','2828.26','2781.42','2016-05-18'],['2850.93','2843.68','2860.32','2832.46','2016-05-17'],['2816.78','2850.86','2851.23','2804.99','2016-05-16'],['2828.46','2827.11','2850.09','2814.11','2016-05-13'],['2812.11','2835.86','2839.28','2781.24','2016-05-12'],['2843.54','2837.04','2857.25','2818.70','2016-05-11'],['2822.33','2832.59','2845.24','2820.16','2016-05-10'],['2896.16','2832.11','2896.16','2821.83','2016-05-09'],['2998.40','2913.25','3003.59','2913.04','2016-05-06'],['2987.02','2997.84','2999.12','2977.20','2016-05-05'],['2983.03','2991.27','3004.42','2978.33','2016-05-04'],['2940.39','2992.64','2993.53','2929.81','2016-05-03'],['2935.38','2938.32','2950.58','2930.36','2016-04-29'],['2955.74','2945.59','2959.51','2916.37','2016-04-28'],['2967.19','2953.67','2976.02','2949.43','2016-04-27'],['2944.72','2964.70','2965.43','2933.97','2016-04-26'],['2949.97','2946.67','2954.09','2917.02','2016-04-25'],['2933.03','2959.24','2960.21','2926.78','2016-04-22'],['2954.37','2952.89','2990.67','2943.46','2016-04-21'],['3050.38','2972.58','3055.69','2905.05','2016-04-20'],['3047.13','3042.82','3054.50','3024.90','2016-04-19'],['3058.46','3033.66','3058.46','3022.97','2016-04-18'],['3085.03','3078.12','3089.95','3066.87','2016-04-15'],['3080.09','3082.36','3086.70','3056.99','2016-04-14'],['3041.36','3066.64','3097.16','3041.36','2016-04-13'],['3031.30','3023.65','3036.82','3001.32','2016-04-12'],['3006.91','3033.96','3048.98','3006.91','2016-04-11'],['2988.20','2984.96','2996.17','2960.46','2016-04-08'],['3058.34','3008.42','3062.36','3007.06','2016-04-07'],['3039.74','3050.59','3059.79','3029.00','2016-04-06'],['3000.94','3053.07','3057.33','2993.15','2016-04-05'],['2997.09','3009.53','3009.67','2956.25','2016-04-01'],['3009.37','3003.92','3023.41','2992.92','2016-03-31'],['2941.22','3000.65','3001.11','2941.22','2016-03-30'],['2956.71','2919.83','2962.20','2905.25','2016-03-29'],['2988.01','2957.82','3008.17','2948.53','2016-03-28'],['2956.20','2979.43','2981.41','2952.11','2016-03-25'],['2986.80','2960.97','2998.15','2960.54','2016-03-24'],['2991.17','3009.96','3012.56','2980.86','2016-03-23'],['3001.63','2999.36','3019.10','2988.43','2016-03-22'],['2978.46','3018.80','3028.32','2973.76','2016-03-21'],['2915.52','2955.15','2971.55','2908.74','2016-03-18'],['2875.41','2904.83','2921.00','2857.19','2016-03-17'],['2858.71','2870.43','2881.53','2854.19','2016-03-16'],['2853.98','2864.37','2865.79','2819.79','2016-03-15'],['2830.08','2859.50','2889.82','2823.03','2016-03-14'],['2781.60','2810.31','2815.61','2772.55','2016-03-11'],['2847.57','2804.73','2863.18','2803.48','2016-03-10'],['2839.41','2862.56','2863.01','2811.72','2016-03-09'],['2895.67','2901.39','2902.62','2802.56','2016-03-08'],['2886.64','2897.34','2911.84','2871.35','2016-03-07'],['2848.54','2874.15','2880.37','2808.85','2016-03-04'],['2847.33','2859.76','2878.45','2840.87','2016-03-03'],['2733.77','2849.68','2852.70','2732.54','2016-03-02'],['2688.38','2733.17','2747.56','2668.76','2016-03-01'],['2754.81','2687.98','2755.89','2638.96','2016-02-29'],['2760.06','2767.21','2784.75','2715.87','2016-02-26'],['2922.24','2741.25','2922.24','2729.85','2016-02-25'],['2889.88','2928.90','2929.87','2872.27','2016-02-24'],['2925.71','2903.33','2928.05','2872.28','2016-02-23'],['2888.60','2927.18','2933.96','2880.35','2016-02-22'],['2854.90','2860.02','2872.72','2840.49','2016-02-19'],['2881.78','2862.89','2893.21','2857.70','2016-02-18'],['2829.76','2867.34','2868.70','2824.36','2016-02-17'],['2758.58','2836.57','2840.62','2758.58','2016-02-16'],['2684.96','2746.20','2760.36','2682.09','2016-02-15'],['2783.08','2763.49','2790.06','2762.16','2016-02-05'],['2751.43','2781.02','2793.30','2751.31','2016-02-04'],['2719.57','2739.25','2746.07','2696.88','2016-02-03'],['2687.98','2749.57','2755.16','2687.98','2016-02-02'],['2730.98','2688.85','2735.26','2655.62','2016-02-01'],['2652.85','2737.60','2755.37','2649.79','2016-01-29'],['2711.16','2655.66','2740.54','2647.49','2016-01-28'],['2756.08','2735.56','2768.77','2638.30','2016-01-27'],['2907.72','2749.79','2911.99','2743.84','2016-01-26'],['2934.08','2938.51','2955.78','2911.83','2016-01-25'],['2911.11','2916.56','2931.36','2851.73','2016-01-22'],['2934.39','2880.48','2998.79','2880.08','2016-01-21']"
				+ ",['2993.01','2976.69','3016.28','2951.92','2016-01-20'],['2914.41','3007.74','3012.07','2906.40','2016-01-19'],['2847.54','2913.84','2945.45','2844.70','2016-01-18'],['2988.05','2900.97','3001.71','2883.87','2016-01-15'],['2874.05','3007.65','3012.29','2867.55','2016-01-14'],['3041.11','2949.60','3059.01','2949.29','2016-01-13'],['3026.16','3022.86','3047.66','2978.46','2016-01-12'],['3131.85','3016.70','3166.22','3016.70','2016-01-11'],['3194.63','3186.41','3235.45','3056.88','2016-01-08'],['3309.66','3125.00','3309.66','3115.89','2016-01-07'],['3291.19','3361.84','3362.97','3288.93','2016-01-06'],['3196.65','3287.71','3328.14','3189.60','2016-01-05'],['3536.59','3296.26','3538.69','3295.74','2016-01-04'],['3570.47','3539.18','3580.60','3538.35','2015-12-31'],['3566.73','3572.88','3573.68','3538.11','2015-12-30'],['3528.40','3563.74','3564.17','3515.52','2015-12-29'],['3635.77','3533.78','3641.59','3533.78','2015-12-28'],['3614.05','3627.91','3635.26','3601.74','2015-12-25'],['3631.31','3612.49','3640.22','3572.28','2015-12-24'],['3653.28','3636.09','3684.57','3633.03','2015-12-23'],['3645.99','3651.77','3652.63','3616.87','2015-12-22'],['3568.58','3642.47','3651.06','3565.75','2015-12-21'],['3574.94','3578.96','3614.70','3568.16','2015-12-18'],['3533.63','3580.00','3583.41','3533.63','2015-12-17'],['3522.09','3516.19','3538.69','3506.29','2015-12-16'],['3518.13','3510.35','3529.96','3496.85','2015-12-15'],['3403.51','3520.67','3521.78','3399.28','2015-12-14'],['3441.60','3434.58','3455.55','3410.92','2015-12-11'],['3469.81','3455.50','3503.65','3446.27','2015-12-10'],['3462.58','3472.44','3495.70','3454.88','2015-12-09'],['3518.65','3470.07','3518.65','3466.79','2015-12-08'],['3529.81','3536.93','3543.95','3506.62','2015-12-07'],['3558.15','3524.99','3568.97','3510.41','2015-12-04'],['3525.73','3584.82','3591.73','3517.23','2015-12-03'],['3450.28','3536.91','3538.85','3427.66','2015-12-02'],['3442.44','3456.31','3483.41','3417.54','2015-12-01'],['3433.85','3445.40','3470.37','3327.81','2015-11-30'],['3616.54','3436.30','3621.90','3412.43','2015-11-27'],['3659.57','3635.55','3668.38','3629.86','2015-11-26'],['3614.07','3647.93','3648.37','3607.52','2015-11-25'],['3602.89','3616.11','3616.48','3563.10','2015-11-24'],['3630.87','3610.31','3654.75','3598.87','2015-11-23'],['3620.79','3630.50','3640.53','3607.92','2015-11-20'],['3573.78','3617.06','3618.21','3561.04','2015-11-19'],['3605.06','3568.47','3617.07','3558.70','2015-11-18'],['3629.98','3604.80','3678.27','3598.07','2015-11-17'],['3522.46','3606.96','3607.61','3519.42','2015-11-16'],['3600.76','3580.84','3632.56','3564.81','2015-11-13'],['3656.82','3632.90','3659.31','3603.23','2015-11-12'],['3635.00','3650.25','3654.88','3605.62','2015-11-11'],['3617.40','3640.49','3669.53','3607.89','2015-11-10'],['3588.50','3646.88','3673.76','3588.50','2015-11-09'],['3514.44','3590.03','3596.38','3508.83','2015-11-06'],['3459.22','3522.82','3585.66','3455.53','2015-11-05'],['3325.62','3459.64','3459.65','3325.62','2015-11-04'],['3330.32','3316.70','3346.27','3302.18','2015-11-03'],['3337.58','3325.08','3391.06','3322.31','2015-11-02']]";
		// String
		// str="[['2792.89','2825.48','2825.95','2785.08','20160520115000'],['-1','-1','-1','-1','20160520114000'],['2828.18','2807.51','2828.26','2781.42','20160520113000'],['2850.93','2843.68','2860.32','2832.46','20160520112000'],['2816.78','2850.86','2851.23','2804.99','20160520111000']]";
		// String
		// str2="[['2792.89','2825.48','2825.95','2785.08','20160520115000'],['2802.31','2806.91','2829.40','2801.55','20160520114000'],['2828.18','2807.51','2828.26','2781.42','20160520113000'],['2850.93','2843.68','2860.32','2832.46','20160520112000'],['2816.78','2850.86','2851.23','2804.99','20160520111000']]";
		String str2 = "[['2792.89','2792.89','2792.89','2792.89','20160520115000'],['2792.89','2792.89','2792.89','2792.89','20160520114000'],['2792.89','2792.89','2792.89','2792.89','20160520113000'],['2792.89','2792.89','2792.89','2792.89','20160520112000'],['2792.89','2792.89','2792.89','2792.89','20160520111000']]";

		JSONArray array = (JSONArray) JSONArray.parse(str1);
		for (int i = 0; i < array.size(); i++) {
			JSONArray arr = array.getJSONArray(i);
			CandleData data = new CandleData();
			data.setOpened(arr.getFloatValue(0));
			// data.setClosing(arr.getFloatValue(1)+new
			// Random().nextFloat()*10);
			data.setClosing(arr.getFloatValue(1));
			data.setHighest(arr.getFloatValue(2));
			data.setLowest(arr.getFloatValue(3));
			data.setDate(arr.getString(4), "yyyy-MM-dd");
			datas.add(data);
		}

		return datas;
	}

	@Override
	public void getData(final AbstractChart kchart, final Date begin, int count) {

		Runnable r = new Runnable() {

			@Override
			public void run() {
				String date = getNextDate(begin);
				Log.e("prt", "getData begin!! date=" + date);
				String res = getResponse(serverAddr
						+ "DBForexChartsInfoQry?PrdCode=" + getCurrType()
						+ "&Period=" + getDateType() + "&NewestEffectTs="
						+ date);
				Log.e("prt", "getData return http response!!");
				if (res == null)
					return;

				List<CandleData> datas = parseChartData(res);
				KLine kline = (KLine) kchart;
				kline.getQueue().append(datas);
				kline.loadOver();// 需要设置标志
			}
		};
		new Thread(r).start();

	}

	private String getNextDate(Date begin) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(begin);
		cal.add(this._type, -this._num);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmm");
		return fmt.format(cal.getTime());
	}

	// "http://172.27.35.19:8080/peweb/DBForexRTPriceQry?PrdCode=EURUSD&QryType=1"
	private String getResponse(String url) {
		String resString = null;
		httpGet = new HttpGet(url);
		/*try {
			response = new DefaultHttpClient().execute(httpGet);
		} catch (ClientProtocolException e) {
			Log.e("prt", e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e("prt", e.getMessage());
			return null;
		}*/
		
		if (response != null && response.getStatusLine().getStatusCode() == 200) {

			try {
				resString = EntityUtils.toString(response.getEntity());
			} catch (org.apache.http.ParseException e) {
				Log.e("prt", e.getMessage());
				return null;
			} catch (IOException e) {
				Log.e("prt", e.getMessage());
				return null;
			}
		}
		return resString;
	}

	/**
	 * 0: 无需刷新 -1:需要重新加载 1:需要刷新
	 */
	@Override
	public int getLatest(AbstractChart kchart) {
		KLine kline = (KLine) kchart;
		Log.e("prt", "getLatest for update!!");
		String res = getResponse(serverAddr + "DBForexCandleInfoQry?PrdCode="
				+ getCurrType() + "&Period=" + getDateType());
		Log.e("prt", "getData return http response!!");
//		if (res == null || res.length() == 0)
//			return 0;

		List<CandleData> datas = parseUpdateData(res);
		CandleData first = kline.getQueue().getCandleData(0);
		if (first == null)
			return 0;
		Date lastDate = first.getDate();
		CandleData candle1 = datas.get(0);
		CandleData candle2 = null;
		if (datas.size() == 2)
			candle2 = datas.get(1);

		if (candle1.getDate().equals(lastDate)) {// 如果candle1的时间与第0个时间相等
			kline.update(candle1, 0);
//			kline.update(candle2, 1);
		} else if (candle1.getDate().after(lastDate)) {// candle1时间比第0个时间靠后
			// 如果candle1的事件比last的date要大2，重新加载
			Calendar cal = Calendar.getInstance();
			cal.setTime(lastDate);// 第1条数据的时间
			cal.add(this._type, this._num);
			if (candle1.getDate().after(cal.getTime())) {
				return -1;
			}

			kline.prepend(candle1);
			kline.update(candle2, 1);
			kline.update(candle2, 2);
		}

		return 1;
	}

	public float getLastClosePrice() {
		return this.lastClosePrice;
	}

	@Override
	public String getDateTypeShow() {
		if (this.dateType.equals("1")) {
			return "M1";
		} else if (this.dateType.equals("2")) {
			return "M5";
		} else if (this.dateType.equals("3")) {
			return "M30";
		} else if (this.dateType.equals("4")) {
			return "H2";
		} else if (this.dateType.equals("5")) {
			return "D1";
		} else if (this.dateType.equals("6")) {
			return "W1";
		} else if (this.dateType.equals("7")) {
			return "MN";
		} else if (this.dateType.equals("0")) {
			return "M1";
		}
		return "";
	}

}
