package com.shisj.kline.chart.core;

import com.shisj.kline.chart.kline.CandleDataProvider;
import com.shisj.kline.chart.kline.CandleQueue;
import com.shisj.kline.shape.Base;
import com.shisj.kline.util.IGetBitMap;
import com.shisj.kline.util.ITap;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;



/**
 * 图表绘制控制器
 * @author shishengjie
 * @date 2016-6-12 上午11:36:01
 */
public abstract class AbstractChart {

	
	public Painter painter;//图表的主绘制器
	private float lastCWidth,lastCHeight,lastFixed=-1f;
	private Canvas canvas;
	public CandleQueue queue;
	float fixedHeight;
	public float getFixedHeight() {
		return fixedHeight;
	}

	public void setFixedHeight(float fixedHeight) {
		this.fixedHeight = fixedHeight;
	}
	public AbstractChart(CandleQueue queue){
		Storage storage = new Storage();
		this.painter=new Painter(storage);
		if (queue == null){
			this.queue = new CandleQueue();
		}else{
			this.queue = queue;
		}
		
	}
	
	public void setQueue(CandleQueue queue) {
		this.queue = queue;
	}
	
	public CandleQueue getQueue(){
		return this.queue;
	}
	
	public  void setCandleDataProvider(CandleDataProvider provider){
		queue.clear();
		queue.setCandleDataProvider(provider);
	}
	
	/**
	 * 添加子绘制器
	 * @param subPainter
	 */
	public void addSubPainter(SubPainter subPainter) {
		this.painter.addPainter(subPainter);
	}
	
	
	/**
	 * 删除子绘制器
	 * @param subPainter
	 */
	public void delSubPainter(SubPainter subPainter) {
		subPainter.onDispose();
		this.painter.delPainter(subPainter);
	}
	
	public Painter getPainter() {
		return painter;
	}
	
	
	
	/**
	 * 判断当前选中的图形
	 * @param x
	 * @param y
	 * @return
	 */
	public Base judgeMoveShape(float x,float y){
		Storage storage=painter.getStorage();
		return storage.getMoveableShape(x, y);
	}

	/**
	 * 移动
	 * @param f
	 */
	public abstract void move(float seg);

	/**
	 * 
	 */
	public abstract void swipe(float seg);
	
	public  void swipeEnd(){
		
	}
	
	/**
	 * 缩放
	 * @param index
	 */
	public  void scale(float index){
		painter.doSubScale();
	}
	
	/**
	 * pan时先执行move再执行touchMove
	 * @param event
	 * @param chart
	 * @param x_down
	 * @param y_down
	 */
	public  void touchMove(MotionEvent event, float x_down,
			float y_down){
		painter.doSubTouchMove(event, x_down,y_down);
	}


	public   void clickDown(MotionEvent event){
		painter.doSubClickDown(event);
	}


	public  void clickUp(MotionEvent event){
		painter.doSubClickUp(event);
	}

	/**
	 * tap的监听事件
	 */
	public  void doTap(MotionEvent event){
		painter.doSubTap(event);
	}
	
	/**
	 * 重新布局
	 */
	public abstract void reLayout();
	
	public final void paint(Canvas canvas){
		if(this.canvas==null){//首次执行，先通知子绘制器创建图形
			this.painter.createShape(canvas);
		}
		if(needSetCanvas(canvas)){
			this.canvas=canvas;
			this.lastCWidth=canvas.getWidth();
			this.lastCHeight=canvas.getHeight();
			this.lastFixed=getFixedHeight();
			resize(canvas);//设置
			this.painter.paint(canvas,true);
		}else{
			this.painter.paint(canvas,false);
		}
	}
	
	/**
	 * 每次设置图表的宽高时调用
	 * @param canvas
	 */
	public abstract void resize(Canvas canvas);
	
	/**
	 * 获取偏移量
	 * @return
	 */
	public abstract float getDrift();
	
	/**
	 * 判断是否需要设置canvas，为空或不一致返回true
	 * @param canvas
	 * @return
	 */
	private boolean needSetCanvas(Canvas canvas){
		//设置canvas
		if(this.canvas==null)return true;
		int width=canvas.getWidth();
		int height=canvas.getHeight();
//		Log.e("prt2", "DENSITY="+Theme.DENSITY+" width= "+width+" height= "+height+" lastCWidth= "+lastCWidth+" lastCHeight= "+lastCHeight+" lastFixed= "+lastFixed);
		if(this.lastCWidth!=width||this.lastCHeight!=height||this.lastFixed!=getFixedHeight())
			return true;
		return false;
	}
	
	/**
	 * 解析之前执行
	 * @param config
	 * @return
	 */
	public String beforeParse(String config){
		return config;
	}

	public boolean getBitMap(){
		return false;
	}
	
	/**
	 * 添加图形
	 * @param base
	 */
	public void addShape(Base base){
		this.painter.getStorage().addShape(base);
		this.painter.getStorage().setNeedSort(true);
	}
	/**
	 * 删除图形
	 * @param base
	 */
	public void delShape(Base base){
		if(base==null)return;
		this.painter.getStorage().delShape(base);
	}
	
	public abstract String getType() ;

	public  void changeTheme(){
		this.painter.doChangeTheme();
	}
	
}
