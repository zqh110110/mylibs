package com.shisj.kline.chart.kline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import android.util.Log;


/**
 * 保存数据的队列，是一个循环队列
 * @author shishengjie
 *
 */
public class CandleQueue {
	
	//用于处理日期
//	private CandleDate cDate;
	//货币对
//	private String current;
//	//时间格式
//	private String dType;
	//队列的节点数量
	private final int NODE_SIZE=128;
	//prepend
	QueueNode header=null;
	//队列数组
	QueueNode queue[]=null;
	//当前使用的节点，为queue数组的index
	private int nIndex=0;
	private boolean isEnd=false;
	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public int getCurrentIndex() {
		return nIndex;
	}
	
	public int size=0;//记录数据的数量
	public boolean updateSize=false;//是否需要更新size

	//数据提供者
	private CandleDataProvider provider;
	private float lastClosePrice;
	
	public CandleQueue(){
		initQueue();
	}
	
	/**
	 * 初始化队列
	 */
	private void initQueue(){
		nIndex=0;
		header=new QueueNode(new ArrayList<CandleData>(20));
		queue=new QueueNode[NODE_SIZE];
	}
	
	/**
	 * 设置前序节点的数据
	 * @param datas
	 */
	public void prepend(List<CandleData> datas){
		header.addData(datas);
		updateSize=true;
	}
	/**
	 * 设置前序节点的数据
	 * @param datas
	 */
	public void prepend(CandleData datas){
		header.addData(datas);
		updateSize=true;
	}
	
	/**
	 * 设置首个元素的数据
	 * @param data
	 */
	public void update(CandleData data,int index){
		CandleData first=getCandleData(index);
		first.setClosing(data.getClosing());
		first.setHighest(data.getHighest());
		first.setLowest(data.getLowest());
		first.setOpened(data.getOpened());
	}
	/**
	 * 获取当前所有数据的总数count
	 * @return
	 */
	public int size(){
		if(updateSize){//若需要更新size则重新计算，否则
			int count=0;
			if(header!=null){
				count=header.size();
			}
			for(int i=0;i<nIndex;i++){
				count+=queue[i].size();
			}
			this.size=count;
		}
		
		return this.size;
	}
	
	public CandleData last(){
		int size=size();
		return getCandleData(size-1);
	}
	
	
	/**
	 * 设置后续节点的数据
	 * @param datas
	 */
	public void append(List<CandleData> datas){
		if(nIndex>=NODE_SIZE||datas==null)return;
		if(datas.size()==0){
			setEnd(true);
			return;
		}
		//获取起始index
		int begin=0;
		if(nIndex>0){
			begin=queue[nIndex-1].lastIndex()+1;
		}
		QueueNode node=new QueueNode(datas,begin);
		queue[nIndex++]=node;
		updateSize=true;
		/*for(CandleData data:datas){
			Log.e("prt",String.format("%f %f %f %f", 
					data.getOpened(),data.getClosing(),data.getHighest(),data.getLowest()
				));
		}*/
	}
	
	
	
	/**
	 * 根据index获取蜡烛数据,index肯定>=0
	 * @param index
	 * @return
	 */
	public CandleData getCandleData(int index){
		int count=size(),size=0;
		if(index<0||index>=count){
			return null;
		}
		size=header.size();
		if(index<size){//在header中
			return header.get(size-1-index); 
		}else{
			index-=size;
			for(int i=0;i<nIndex;i++){
				size=queue[i].size();
				if(index>=size){//不在这个node中
					index-=size;
				}else{//在此node中
					return queue[i].get(index);
				}
			}
		}
		return null;
	}
	
	public CandleData getCandleDataRevert(int index){
		int size=size();
		return getCandleData(size-index-1);
	}
	
	/**
	 * 获取指定范围内的最大最小值
	 * 需要修改
	 * @param begin	0
	 * @param end	13
	 * @return
	 */
	public float[] getMaxmin(int begin,int end){
		float ret[]=new float[2];
		int length=this.size()-1;
        if(end>length){
            end=length;
        }
        for(int i=begin;i<=end;i++){//变量
        	float tmp[]=getMaxmin(i);
            if(i==begin)//第一个，为min赋初值
            	ret[1]=tmp[1];
            ret[0]=Math.max(ret[0],tmp[0]);
            ret[1]=Math.min(ret[1],tmp[1]);
        }
        return ret;
	}
	
	/**
	 * 获取index个数据的最大最小值
	 * @param index
	 * @return
	 */
	private float[] getMaxmin(int index){
		float ret[]=new float[2];
		CandleData data=this.getCandleData(index);
		ret[0]=ret[1]=data.getClosing();//最大最小默认为
		
		ret[0]=Math.max(ret[0], data.getHighest());
		ret[0]=Math.max(ret[0], data.getLowest());
		ret[0]=Math.max(ret[0], data.getOpened());
		
		ret[1]=Math.min(ret[1], data.getHighest());
		ret[1]=Math.min(ret[1], data.getLowest());
		ret[1]=Math.min(ret[1], data.getOpened());
//		
		return ret;
	}
	
	/**
	 * 获取区间内开盘价和收盘价的最大最小值
	 * @param begin
	 * @param end
	 * @return
	 */
	private float[] getOpenedMaxmin(int begin,int end){
		float ret[]=new float[2];
		ret[0]=0;//最大值
		ret[1]=Float.MAX_VALUE;//最小值
		int length=this.size()-1;
        if(end>length){
            end=length;
        }
        for(int index=begin;index<=end;index++){//变量
        	CandleData data=this.getCandleData(index);
        	if(data==null)break;
        	float open=data.getOpened();
        	if(open>ret[0]){//大于最大值
        		ret[0]=open;
        	}
        	if(open<ret[1]){//小于最小值
        		ret[1]=open;
        	}
        }
        return ret;
	}
	
	public float[] getOpenedMaxminExt(int begin,int end){
		int last=this.size()-1;
		if(last==-1)return new float[]{0,0};
		float[] ret=getOpenedMaxmin(begin,end);
//		这样不行，因为首个要居中,对真实的最大最小值进行处理
		CandleData data=this.getCandleData(last);
		float opend=data.getOpened();
		float a1=Math.abs(opend-ret[0]);
		float a2=Math.abs(opend-ret[1]);
		a1=Math.max(a1, a2);
		
		ret[0]=opend+a1;
		ret[1]=opend-a1;
		return ret;
	}
	
	/**
	 * 获取日期内容
	 * @param span
	 * @return
	 */
	public String getTime(int span){
//		return cDate.getTime(span-header.size());
		Date date=getDate(span);
		if(date!=null){
			if(this.provider!=null){
				String format=this.provider.getTimeFormate();
				SimpleDateFormat fmt=new SimpleDateFormat(format);
				return fmt.format(date);
			}
		}
		return "";//+span
	}
	
	public String getTimeRevert(int span){
		int size=size();
		return getTime(size-1-span);
	}
	/**
	 * 获取日期,span不为负数
	 * @param span
	 * @return
	 */
	public Date getDate(int span){
//		return cDate.getDate(span);
		CandleData data=getCandleData(span);
		if(data!=null){
			return data.getDate();
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getNextLoadDate(){
		CandleData data=last();
		return data.getDate();
	}
	/**
	 * 设置数据提供者
	 * @param provider
	 */
	public void setCandleDataProvider(CandleDataProvider provider) {
		if(provider==null)return;
		if(this.provider==null){//若为空，直接用这个
			this.provider=provider;
		}else{//不为空，修改里面的遍历
			this.provider.setCurrType(provider.getCurrType());
			this.provider.setTimeFormate(provider.getTimeFormate());
			this.provider.setDateType(provider.getDateType());
		}
		this.append(this.provider.getInitData());
		this.setLastClosePrice(this.provider.getLastClosePrice());
	}
	
	
	public void modify(){
		
	}
	
	private void setLastClosePrice(float lastClosePrice) {
		this.lastClosePrice=lastClosePrice;
	}

	public void resetData(){
		
	}
	public CandleDataProvider getCandleDataProvider(){
		return this.provider;
	}
	
	public void clear(){
		 header=null;
		 queue=null;
		 nIndex=0;
		 provider=null;
		 initQueue();
	}
}

/**
 * 节点类
 * @author shishengjie
 *
 */
class QueueNode{
	
	private List<CandleData> datas=null;
	private int begin;
	public QueueNode(){}
	public QueueNode(int begin){
		this.begin=begin;
	}
	public QueueNode(List<CandleData> datas){
		this.datas=datas;
	}
	public QueueNode(List<CandleData> datas,int begin){
		this.datas=datas;
		this.begin=begin;
	}
	public void setData(List<CandleData> datas){
		this.datas=datas;
	}
	
	public void addData(List<CandleData> datas){
		this.datas.addAll(datas);
	}
	
	public void addData(CandleData datas){
		this.datas.add(datas);
	}
	public int size(){
		return datas.size();
	}
	
	public int lastIndex(){
		return begin+datas.size()-1;
	}
	
	/**
	 * 获取
	 * @param index
	 * @return
	 */
	public CandleData get(int index){
		return datas.get(index);
	}
	/**
	 * 更新index位置的数据
	 * @param data
	 * @param index
	 */
	public void update(CandleData data,int index){
		datas.set(index, data);
	}
	
	
}
