package com.shisj.kline.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.util.KPosition;



/**
 * 用于管理旋转时的状态，以及辅助线的创建删除
 * @author shishengjie
 *
 */
public class RecordManager {

	
	public interface ICreator{
		public Object getInstance(String params[]);
		public void dispose(Object object);
	}
	/**
	 * 保存类型与接口的对应关系
	 */
	Map<String, ICreator> map=new HashMap<String, ICreator>();
	/**
	 * 保存类型与实例的对应关系
	 */
	Map<String,ArrayList<Object>> instances=new HashMap<String,ArrayList<Object>>();
	
	/**
	 * 将辅助线添加到缓存
	 * @param type
	 * @param obj
	 */
	protected void addInstance(String type,Object obj){
		ArrayList<Object> ret=null;
		if(type==null||obj==null){
			return;
		}
		ret=instances.get(type);
		if(ret==null){
			ret=new ArrayList<Object>();
		}
		ret.add(obj);
		instances.put(type, ret);
	}
	
	/**
	 * 从缓存中删除实例
	 * @param type
	 * @param index
	 */
	protected void delInstance(String type,int index){
		Object obj=getInstance(type,index);
		if(obj!=null){
			ArrayList<Object> ret=instances.get(type);
			ret.remove(index);
		}
	}
	
	
	public RecordManager(final ChartView kview){
		
		//注册MALine的创建方式
		
		//注册MACD的创建方式
				
		//注册KChart的创建方式
		this.register(KLine.TYPE, new RecordManager.ICreator() {
			@Override
			public Object getInstance(String params[]) {
				KLine kline=(KLine) kview.getChart();
				//currentStep
				kline.clear();
				int step=Integer.valueOf(params[2]);
				kline.setStep(step);
				
				int count=Integer.valueOf(params[1]);//类似scale
				float drift=kline.calcDrift(count);
				kline.move(drift,true);
				return null;
			}

			@Override
			public void dispose(Object object) {
				// TODO Auto-generated method stub
			}
		});
				
		//注册AssistLine的创建方式
		
		//注册AssistLine的创建方式
		
		//注册CrossLine的创建方式
	}
	
	private KPosition getCrossLocation(String params[],ChartView kview){
		KLine kline=(KLine) kview.getChart();
		int index;
		float price,x = 0,y=0;
		if(params.length==3){//手工添加,x&y
			x=Float.valueOf(params[1]);
			y=Float.valueOf(params[2]);
		}else{//旋转 index&price
			index=Integer.valueOf(params[1]);
			price=Float.valueOf(params[2]);
			//根据四个变量计算出bx by ex ey
			x=kline.getXPxWin(index);
			y=kline.getYPx(price);
		}
		return kline.adsorb(x,y,true);
	}
	
	private float[] getAssistLocation(String params[],ChartView kview){
		KLine kline=(KLine) kview.getChart();
		int bIndex,eIndex;
		float bPrice,ePrice,bx = 0,by=0,ex=0,ey=0;
		if(params.length==5){//手工添加
			bx=Float.valueOf(params[1]);
			by=Float.valueOf(params[2]);
			ex=Float.valueOf(params[3]);
			ey=Float.valueOf(params[4]);
		}else{//旋转
			bIndex=Integer.valueOf(params[1]);
			eIndex=Integer.valueOf(params[3]);
			bPrice=Float.valueOf(params[2]);
			ePrice=Float.valueOf(params[4]);
			//根据四个变量计算出bx by ex ey
			bx=kline.getXPxWin(bIndex);
			by=kline.getYPx(bPrice);
			
			ex=kline.getXPxWin(eIndex);
			ey=kline.getYPx(ePrice);
		}
		return new float[]{bx,by,ex,ey};
	}
	/**
	 * 注册
	 * @param type
	 * @param record
	 */
	public void register(String type,ICreator record){
		if(map.get(type)==null){
			map.put(type, record);
		}
	}
	
	/**
	 * 创建某种类型的实例
	 * @param type
	 * @return
	 */
	private Object create(String type[]){
		String _type=type[0];
		ICreator record= map.get(_type);
		if(record!=null){
			 Object ret=record.getInstance(type);
			 addInstance(_type, ret);//将实例加入
			 return ret;
		}
		return null;
	}
	
	/**
	 * 创建某种类型的实例
	 * @param config
	 * @return
	 */
	public Object create(String config){
		if(config==null)return null;
		String cfg[]=config.split("\\|");
		return create(cfg);
	}
	
	/**
	 * 删除FL
	 * @param type
	 * @param index
	 */
	public void dispose(String type,int index){
		Object obj=getInstance(type, index);
		if(obj==null)return;
		//获取
		ICreator creator=map.get(type);
		if(creator!=null){
			creator.dispose(obj);//调用此种类型的dispose方法
		}
		//删除实例
		delInstance(type, index);
	}
	
	public void dispose(String type){
		int size=instanceSize(type);
		for(int index=0;index<size;index++){
			dispose(type,index);
		}
	}
	
	/**
	 * 获取实例,若type为空或index不在范围内，返回null
	 * @param type
	 * @param index
	 * @return
	 */
	public Object getInstance(String type,int index){
		ArrayList<Object> ret=null;
		if(type==null||index<0){
			return null;
		}
		ret=instances.get(type);
		if(ret!=null&&index<ret.size()){
			return ret.get(index);
		}
		return null;
	}
	
	/**
	 * 获取实例数量
	 * @param type
	 * @return
	 */
	public int instanceSize(String type){
		ArrayList<Object> ret=null;
		if(type==null){
			return 0;
		}
		ret=instances.get(type);
		if(ret!=null){
			return ret.size();
		}
		return 0;
	}
}
