package com.shisj.kline.chart.kline.ext;

import com.shisj.kline.chart.kline.CandleData;
import com.shisj.kline.chart.kline.KLine;

/**
 * 
 * @author shishengjie
 *
 */
public class MACDUtil {

	int longEMA=26,shortEMA=12,spanDEA=9;
	KLine kLine;//用于获取上下文数据
	
	public MACDUtil(KLine kLine){
		this.kLine=kLine;
	}
	
	
	/**
	 * 计算
	 * @param candleIndex
	 * @param span
	 * @return
	 */
	private float EMA(int candleIndex,int N){
		float molecule=0.0f,denominator=0.0f;//分子和分母
		float alpha=2.0f/(N+1);
		float alphaDif=1-alpha;//平滑指数,2/(N+1)
		for(int i=0;i<N;i++){
			CandleData data=kLine.getQueue().getCandleData(candleIndex+i);
			if(data!=null){
				float tmp=(float) Math.pow(alphaDif, i);
				molecule+=tmp*data.getClosing();//分子
				denominator+=tmp;//分母
			}
		}
		return molecule/denominator;
	}
	
	/**
	 * 计算DEA
	 * @param candleIndex
	 * @return
	 */
	private float DEA(int candleIndex){
		float N=spanDEA;//设置N的值
		float molecule=0.0f,denominator=0.0f;//分子和分母
		float alpha=2.0f/(N+1);
		float alphaDif=1-alpha;//平滑指数,2/(N+1)
		for(int i=0;i<N;i++){
			float diff=getCandleDIFF(candleIndex+i);//获取diff
			float tmp=(float) Math.pow(alphaDif, i);
			molecule+=tmp*diff;//分子
			denominator+=tmp;//分母
		}
		return molecule/denominator;
	}
	
	/**
	 * 获取DIFF
	 * @param candleIndex
	 * @return
	 */
	public float getCandleDIFF(int candleIndex){
		CandleData data=kLine.getQueue().getCandleData(candleIndex);
		if(data==null)//无数据
			return 0;
		CandleDataQuota quota=(CandleDataQuota) data.getData();
		if(quota==null){//未缓存
			float emaLong=EMA(candleIndex,longEMA);
			float emaShort=EMA(candleIndex,shortEMA);
			quota=new CandleDataQuota();
			
			//测试代码
//			quota.setShortEMA(emaShort);
//			quota.setLongEMA(emaLong);
			
			quota.setDiff(emaShort-emaLong);
			data.setData(quota);
			return quota.getDiff();
		}else{//有缓存
			float diff=quota.getDiff();
			if(diff==Float.MIN_VALUE){//需要取值
				float emaLong=EMA(candleIndex,longEMA);
				float emaShort=EMA(candleIndex,shortEMA);
				
				//测试代码
//				quota.setShortEMA(emaShort);
//				quota.setLongEMA(emaLong);
				
				quota.setDiff(emaShort-emaLong);
				return quota.getDiff();
			}else{
				return diff;
			}
		}
	}
	
	public float getCandleShort(int candleIndex){
		CandleData data=kLine.getQueue().getCandleData(candleIndex);
		if(data==null)//无数据
			return 0;
		CandleDataQuota quota=(CandleDataQuota) data.getData();
		if(quota!=null){//未缓存
			return quota.getShortEMA();
		}
		return 0;
	}
	
	public float getCandleLong(int candleIndex){
		CandleData data=kLine.getQueue().getCandleData(candleIndex);
		if(data==null)//无数据
			return 0;
		CandleDataQuota quota=(CandleDataQuota) data.getData();
		if(quota!=null){//未缓存
			return quota.getLongEMA();
		}
		return 0;
	}
	
	
	public float getCandleDEA(int candleIndex){
		CandleData data=kLine.getQueue().getCandleData(candleIndex);
		if(data==null)//无数据
			return 0;
		CandleDataQuota quota=(CandleDataQuota) data.getData();
		if(quota==null){//未缓存
			float dea=DEA(candleIndex);
			quota=new CandleDataQuota();
			quota.setDea(dea);
			data.setData(quota);
			return dea;
		}else{//有缓存
			float dea=quota.getDea();
			if(dea==Float.MIN_VALUE){//需要取值
				dea=DEA(candleIndex);
				quota.setDea(dea);
				return quota.getDea();
			}else{
				return dea;
			}
		}
	}
	
	public float getCandleMACD(int candleIndex){
		float diff=getCandleDIFF(candleIndex);
		float dea=getCandleDEA(candleIndex);
		return 2*(diff-dea);
	}
	public float [] getMaxmin(float []a1,float []a2){
		float max=Math.max(a1[0], a2[0]);
		float min=Math.min(a1[1], a2[1]);
		return new float[]{max,min};
	}
	public float []  getDEAMaxmin(int begin,int end){
		float max=Float.MIN_VALUE,min=Float.MAX_VALUE;
		
		for(int i=begin;i<=end;i++){
			float dea=getCandleDEA(i);
			if(dea>max)
				max=dea;
			if(dea<min)
				min=dea;
		}
		//增加间隔
		float tmp=(max-min)/10.0f;
		max+=tmp;
		min-=tmp;
		return new float[]{max,min};
	}
	
	public float []  getDIFFMaxmin(int begin,int end){
		float max=Float.MIN_VALUE,min=Float.MAX_VALUE;
		
		for(int i=begin;i<=end;i++){
			float diff=getCandleDIFF(i);
			if(diff>max)
				max=diff;
			if(diff<min)
				min=diff;
		}
		
		//增加间隔
		float tmp=(max-min)/10.0f;
		max+=tmp;
		min-=tmp;
				
		return new float[]{max,min};
	}
	
	
	public float []  getMACDMaxmin(int begin,int end){
		float max=Float.MIN_VALUE,min=Float.MAX_VALUE;
		
		for(int i=begin;i<=end;i++){
			float macd=getCandleMACD(i);
			if(macd>max)
				max=macd;
			if(macd<min)
				min=macd;
		}
		
		//增加间隔
		float tmp=(max-min)/10.0f;
		max+=tmp;
		min-=tmp;
		
		return new float[]{max,min};
	}
	
	
//	public float getCandleEMALong(int candleIndex){
//		CandleData data=kChart.getQueue().getCandleData(candleIndex);
//		if(data==null)//无数据
//			return 0;
//		CandleDataQuota quota=(CandleDataQuota) data.getData();
//		if(quota==null){//未缓存
//			float ema=EMA(candleIndex,longEMA);
//			quota=new CandleDataQuota();
//			quota.setLongEMA(ema);
//			data.setData(quota);
//		}else{//有缓存
//			float ema=quota.getLongEMA();
//			if(ema==Float.MIN_VALUE){//需要取值
//				ema=EMA(candleIndex,longEMA);
//				quota.setLongEMA(ema);
//			}else{
//				return ema;
//			}
//		}
//		return 0;
//	}
//	public float getCandleEMAShort(int candleIndex){
//		CandleData data=kChart.getQueue().getCandleData(candleIndex);
//		if(data==null)//无数据
//			return 0;
//		CandleDataQuota quota=(CandleDataQuota) data.getData();
//		if(quota==null){//未缓存
//			float ema=EMA(candleIndex,shortEMA);
//			quota=new CandleDataQuota();
//			quota.setShortEMA(ema);
//			data.setData(quota);
//		}else{//有缓存
//			float ema=quota.getShortEMA();
//			if(ema==Float.MIN_VALUE){//需要取值
//				ema=EMA(candleIndex,shortEMA);
//				quota.setShortEMA(ema);
//			}else{
//				return ema;
//			}
//		}
//		return 0;
//	}
	
	
}
