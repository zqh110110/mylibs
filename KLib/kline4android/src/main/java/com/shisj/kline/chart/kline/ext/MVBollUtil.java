package com.shisj.kline.chart.kline.ext;

import com.shisj.kline.chart.kline.CandleData;
import com.shisj.kline.chart.kline.KLine;


public class MVBollUtil {
	private final int MVCOUNT=10;//移动平均线SMA 计算的数量
	private final int BOOLCOUNT=20;//BOOL 计算的数量
	KLine kChart;//用于获取上下文数据
	boolean boll;//布林带标志
	public boolean isBoll() {
		return boll;
	}

	public void setBoll(boolean boll) {
		this.boll = boll;
	}

	public MVBollUtil(KLine kChart){
		this.kChart=kChart;
	}
	
	/**
	 * 获取index~index+9的收盘平均值
	 * @param index
	 * @return
	 */
	public MVResult getMAverage(int index){
		float sum=0f;
		int count=0;
		float average;
		int count_t=getCount();
		for(int i=0;i<count_t;i++){
			CandleData data=kChart.getQueue().getCandleData(index+i);
			if(data!=null){
				sum+=data.getClosing();
				count++;
			}
		}
		average=sum/count;//平均值
		
		//再次循环，计算标准差
		float devia=getDeviation(average,index);
		return new MVResult(average,count,devia);
	}
	
	/**
	 * 计算标准差
	 * @param average
	 * @param beg
	 * @param end
	 * @return
	 */
	private float getDeviation(float average,int index){
		float sum=0f;//sum 此次保存平方之和
		int count=0;
		int count_t=getCount();
		for(int i=0;i<count_t;i++){
			CandleData data=kChart.getQueue().getCandleData(index+i);
			if(data!=null){
				float tmp=data.getClosing()-average;
				tmp*=tmp;//平方
				sum+=tmp;
				count++;
			}
		}
		
		return (float) Math.sqrt(sum/count);
		
	}
	/**
	 * 根据上个moving average获取当前
	 * @param prev
	 * @param index
	 * @return
	 */
	public MVResult getMAverage(MVResult lastMV,int candleIndex){
		if(lastMV==null){
			return getMAverage(candleIndex);
		}
		int count_t=getCount();
		CandleData prev=kChart.getQueue().getCandleData(candleIndex-1);
		CandleData last=kChart.getQueue().getCandleData(candleIndex-1+count_t);
		float prevPrice=0.0f;
		float lastPrice=0.0f;
		if(prev!=null){
			prevPrice=prev.getClosing();
		}
		int count=lastMV.getCount();//由于从当前到MVCOUNT不一定有数值,需要保存住数量，以免除错
		float average=lastMV.getSum();//上次的和
		if(last!=null){
			lastPrice=last.getClosing();
		}else{
			count--;
		}
		average=(average-prevPrice+lastPrice)/count;
		
		float devia=getDeviation(average,candleIndex);
		return new MVResult(average,count,devia);
	}
//	points[j]=kChart.getXPx(candleIndex);//获取x轴坐标
//	points[j+1]=kChart.getYPx(average);//获取y坐标	
	
	
	private int getCount(){
		if(boll)
			return BOOLCOUNT;
		return MVCOUNT;
	}
	
}
/**
 * 保存结果
 * @author shishengjie
 *
 */
class MVResult{
	float average;//平均值
	int count;//数量
	float deviation;//标准差
	public float getDeviation() {
		return deviation;
	}
	public void setDeviation(float deviation) {
		this.deviation = deviation;
	}
	public MVResult(float average,int count,float devia){
		this.average=average;
		this.count=count;
		this.deviation=devia;
	}
	public float getAverage() {
		return average;
	}
	public void setAverage(float average) {
		this.average = average;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public float getSum(){
		return this.count*this.average;
	}
	
	public float getTop(){
		return average+2*deviation;
	}
	
	public float getDown(){
		return average-2*deviation;
	}
	
}
