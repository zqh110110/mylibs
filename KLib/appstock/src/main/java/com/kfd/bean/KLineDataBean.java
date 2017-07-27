package com.kfd.bean;

import java.io.Serializable;
import java.util.List;

/**
 * k线图数据
 * 
 * @author zhanhongyi
 * 
 */
public class KLineDataBean implements Serializable {
	public String high_price;
	public String low_price;
	public String open_price;
	public String close_price;
	public String dateline;
	public String price;

	public List<String> boll;
	public List<String> macd;
	public List<String> ma;
	public List<String> kdj;

	/*
	 * 获取最小，最大值 type: 0-MA; 1-BOLL
	 */
	public float[] getMainMinAndMax(int type) {

		float[] result = { Float.MAX_VALUE, Float.MAX_VALUE };

		try {
			float high = Float.parseFloat(this.high_price);
			float low = Float.parseFloat(this.low_price);
			float open = Float.parseFloat(this.open_price);
			float close = Float.parseFloat(this.close_price);

			result[0] = result[0] == Float.MAX_VALUE ? low : Math.min(low, result[0]);
			result[1] = result[1] == Float.MAX_VALUE ? high : Math.max(low, result[1]);

			result[0] = Math.min(high, result[0]);
			result[1] = Math.max(high, result[1]);

			result[0] = Math.min(open, result[0]);
			result[1] = Math.max(open, result[1]);

			result[0] = Math.min(close, result[0]);
			result[1] = Math.max(close, result[1]);

			List<String> list = type == 0 ? this.ma : this.boll;

			for (String str : list) {

				float rice = Float.parseFloat(str);

				result[0] = Math.min(rice, result[0]);
				result[1] = Math.max(rice, result[1]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new float[] { 0f, 0f };
		}

		return result;
	}

	/*
	 * 获取最小，最大值 type: 0-MACD; 1-KDJ
	 */
	public float[] getSubMinAndMax(int type) {

		float[] result = { Float.MAX_VALUE, Float.MAX_VALUE };

		try {
			List<String> list = type == 0 ? this.macd : this.kdj;

			for (String str : list) {
				float rice = Float.parseFloat(str);
		
				result[0] = result[0] == Float.MAX_VALUE ? rice : Math.min(rice, result[0]);
				result[1] = result[1] == Float.MAX_VALUE ? rice : Math.max(rice, result[1]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new float[] { 0f, 0f };
		}

		return result;
	}
}
