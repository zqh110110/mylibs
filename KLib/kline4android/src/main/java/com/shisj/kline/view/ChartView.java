package com.shisj.kline.view;


import java.util.ArrayList;

import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.SubPainter;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.kline.CandleDataProvider;
import com.shisj.kline.chart.kline.CandleQueue;
import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.shape.Base;
import com.shisj.kline.util.IBeforeParse;
import com.shisj.kline.util.IGetBitMap;
import com.shisj.kline.util.IRecord;
import com.shisj.kline.util.ITap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * 绘制图表容器
 * @author shishengjie
 *
 */
public class ChartView extends View implements IGetBitMap{
	private long mFirstTouchEventTime = 0L, mLastTouchEventTime = 0L;
	float x_down,y_down,pan_x,pan_y,tmpScale = 100f;
	final public static int DRAG = 1;//操作模式 拖动
	final public static int ZOOM = 2;//操作模式 缩放
	public int mode = 0;//操作模式
	private float initDis = 1f;//初始两点间的距离
	private ITap tapLinstener=null;
	boolean getMap = false;//用于获取canvas的bitmap
	ChartAnimation anim = null;//用于动画
	RecordManager recordManager;//附加线管理器
	IBeforeParse beforeParse;//
	Base selectedPoint;//选择的可移动点
	public ArrayList<String> ends = new ArrayList<String>();//odDraw结束后需要绘制的附加线的recordConfig
	private ArrayList<IRecord> records = new ArrayList<IRecord>();//记录附加线的状态
	private AbstractChart chart;
	private boolean rotate=false;
	private int layoutBottom,layoutHeight;
	private InitCallback initCallback=null;
	private boolean rotateAllow=false;
	public static float kAxisHeight=300,kAxisWidth=300;
	public static float tAxisHeight=300,tAxisWidth=300;

	public void setLayoutBottom(int layoutBottom){
		this.layoutBottom=layoutBottom;
	}
	
	public void setLayoutHeight(int layoutHeight){
		this.layoutHeight=layoutHeight;
	}
	
	public int getLayoutBottom(){
		return this.layoutBottom;
	}
	
	public int getLayoutHeight(){
		return this.layoutHeight;
	}
	
	public void setInitCallback(InitCallback initCallback){
		this.initCallback=initCallback;
	}
	public interface InitCallback{
		public void afterInit(AbstractChart chart);
	}
	
	public ChartView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ChartView(Context context) {
		this(context, null,0);
	}
	public AbstractChart getChart(){
		return this.chart;
	}
	public ChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		DisplayMetrics dm=getResources().getDisplayMetrics();
		Theme.setDensity(dm.density);
		final ChartView chartView = this;
		this.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				int action = event.getAction();
				// 多点触摸的时候 必须加上MotionEvent.ACTION_MASK
				switch (action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					//按下的时间
					if (mFirstTouchEventTime == 0) {
						mFirstTouchEventTime = event.getDownTime();
					}
					//按下的坐标
					x_down = event.getX();
					y_down = event.getY();
					// 初始为drag模式
					mode = DRAG;
					pan_x = pan_y = 0f;
					anim.setStop(true);//停止动画
					
					// 检查是否选择了可移动的点
					selectedPoint = chart.judgeMoveShape(x_down, y_down);
					if (selectedPoint != null) {
						selectedPoint.actionDown(event);
					}
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					// 初始的两个触摸点间的距离
					initDis = spacing(event);
					// 设置为缩放模式
					mode = ZOOM;
					tmpScale = 100f;
					break;
				case MotionEvent.ACTION_MOVE:
					// drag模式
					if (mode == DRAG) {
						float move_x = event.getX() - x_down;
						float move_y = event.getY() - y_down;
						float pan_x_seg = move_x - pan_x;
						float pan_y_seg = move_y - pan_y;
						pan_x = move_x;
						pan_y = move_y;
						if (selectedPoint != null) {
							// 执行移动点的事件
							selectedPoint.actionMove(event, pan_x_seg,
									pan_y_seg, chartView);
							break;
						}

						if (Math.abs(pan_x_seg) > 10.0f) {// swipe

							if (pan_x_seg > 0)
								pan_x_seg = pan_x_seg * 8;
							if (pan_x_seg < 0)
								pan_x_seg = pan_x_seg * 16;
							long time = (long) Math
									.abs((pan_x_seg / ChartAnimation.SPEED) + 33.0f);
							anim.setDuration(time);
							anim.setStop(false);
							anim.direction(pan_x_seg);
							chartView.startAnimation(anim);
							
							//
						} else {// pan
							anim.setStop(true);
							chart.move(pan_x_seg * 2);
							chart.touchMove(event, x_down, y_down);
						}

					} else if (mode == ZOOM) {

						float newDis = spacing(event);
						// 计算出缩放比例
						float scale = newDis / initDis;

						float tmp = Math.round(scale * 100);
						// 放手的时候tmp是0导致abs大于10
						if (tmp == 0.0f)
							break;
						// kchart.debug(tmp+" "+(tmpScale));

						if (Math.abs(tmp - tmpScale) > 10) {
							chart.scale(tmp - tmpScale);
							tmpScale = tmp;
						}

					}
					break;
				case MotionEvent.ACTION_UP:
					float move_x = event.getX() - x_down;
					float move_y = event.getY() - y_down;
					float mUpX = (int) event.getX();
					if (selectedPoint != null) {
						// 执行移动点的事件
						selectedPoint.actionUp(event, chartView);
						break;
					}
					mLastTouchEventTime = event.getDownTime();
					if (Math.abs(mUpX - x_down) < 15) {
						if (Math.abs(mFirstTouchEventTime - mLastTouchEventTime) < 150 * 1000) {
							mFirstTouchEventTime = 0L;
							mLastTouchEventTime = 0L;
							//执行拦截器
							if(chartView.tapLinstener!=null){
								if(!chartView.tapLinstener.tap(event)){
									break;//不执行后续事件了
								}
							}
							//执行tap事件
							chart.doTap(event);
							break;//不执行后续事件了
						}
					} else {
						mFirstTouchEventTime = 0L;
						mLastTouchEventTime = 0L;
					}
					
					boolean clickEvent = isClick(move_x, move_y);
					if (mode == DRAG) {// 由于touche时候也会触发drag所以在up时执行
						if (clickEvent) {
							chart.clickDown(event);
						} else {
							chart.clickUp(event);
						}
					}
					break;

				}
				postInvalidate();
				return true;
			
			}
		});
		
//		initTimeLine();
		initKLine();
	}
	
	
	
	/**
	 * 初始化图表
	 */
	public void initKLine() {
		CandleQueue queue=null;
		float fixedHeight=0.0f;
		if(chart!=null){
			queue=chart.getQueue();
			fixedHeight=chart.getFixedHeight();
		}
		chart=new KLine(queue, ChartView.kAxisWidth,  ChartView.kAxisHeight);
		chart.setFixedHeight(fixedHeight);
		//1.呵呵答
		this.getMap=chart.getBitMap();
		if(this.getMap){
			this.setDrawingCacheEnabled(true);
			this.buildDrawingCache(true);
		}
		//2.哒哒呵
		anim = new ChartAnimation(this);
		anim.setDuration(1000);
		
		//3.管理辅助线
		recordManager = new RecordManager(this);
		
		//4.初始化k线图后做的事情
		afterInitChart();
		
	}
	
	public void afterInitChart(){
		if(initCallback!=null){
			initCallback.afterInit(chart);
		}
	}
	
	/**
	 * 只是重绘
	 */
	public void rePaint() {
		postInvalidate();
	}
	
	
	/**
	 * 解析字符串
	 * @param config
	 */
	public void parse(String config){
		parse(config,null);
	}
	/**
	 * 设置解析前处理接口
	 * @param parse
	 */
	public void setBeforeParse(IBeforeParse parse){
		this.beforeParse=parse;
	}
	/**
	 * 解析字符串
	 * 
	 * @param config
	 */
	public void parse(String config,String type) {
		if (rotate)
			return;
		if (config == null || config.trim().length() == 0)
			return;
		config = chart.beforeParse(config);
		// 帮助解析
		String configs[] = config.split("###");
		for (String cfg : configs) {
			if (cfg.trim().length() == 0)
				continue;
			String cfg_type=cfg.split("\\|")[0];
			if(type!=null&&!type.equals(cfg_type)){//若设置了type且当前不符合 不解析
				continue;
			}
			
			// 需要在onDraw后刷新
			ends.add(cfg);
		}
	}
	
	public void parseDelete(String config){
		if (config == null || config.trim().length() == 0)
			return;
		// 帮助解析
		String configs[] = config.split("###");
		for (String cfg : configs) {
			if (cfg.trim().length() == 0)
				continue;
			String cfg_type=cfg.split("\\|")[0];
			recordManager.dispose(cfg_type);
		}
		ends.clear();//fix bug有时，parse()后 未立刻执行onDraw 此时parseDelete 在onDraw导致删除后再次加入了 
	}
	
	/**
	 * 设置数据提供者
	 * 
	 * @param provider
	 */
	public void setCandleDataProvider(CandleDataProvider provider) {
		if (rotate)
			return;
		chart.setCandleDataProvider(provider);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制
		chart.paint(canvas);
		for (String cfg : ends) {
			recordManager.create(cfg);
		}
		if (ends.size() > 0) {
			this.rePaint();
			ends.clear();
		}
	}
	
	/**
	 * 添加子绘制器
	 * @param subPainter
	 */
	public void addPainter(SubPainter subPainter) {
		chart.getPainter().addPainter(subPainter);
		addToRecord(subPainter);
	}

	/**
	 * 删除子绘制器 会自动调用dispose
	 * 
	 * @param subPainter
	 */
	public void delPainter(SubPainter subPainter) {
		subPainter.onDispose();
		chart.getPainter().delPainter(subPainter);
		delFromRecord(subPainter);
	}
	
	
	public void setTapListener(ITap tapLinstener){
		this.tapLinstener=tapLinstener;
	}
	
	/**
	 * 取两点的距离
	 * @param event
	 * @return
	 */
	private float spacing(MotionEvent event) {
		try {
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return (float) Math.sqrt(x * x + y * y);
		} catch (IllegalArgumentException ex) {
			// Log.v("TAG", ex.getLocalizedMessage());
			return 0;
		}
	}
		
	/**
	 * 判断是否是点击而非平移
	 * @param move_x
	 * @param move_y
	 * @return
	 */
	private boolean isClick(float move_x, float move_y) {
		float max = 5.0f;
		if (move_x > -max && move_x < max && move_y > -max&& move_y < max)
			return true;
		return false;
	}
	
	/**
	 * 获取附加线的对象
	 * @param type
	 * @param index
	 * @return
	 */
	public RecordManager getRecordManager() {
		return this.recordManager;
	}

	@Override
	public Bitmap getBitmap() {
		this.setDrawingCacheEnabled(true);
		this.buildDrawingCache(true);
		return getDrawingCache(true);
	}
	
	
	/**
	 * 获取当前配置
	 * 
	 * @return
	 */
	public String getRecord() {
		StringBuffer sb = new StringBuffer("###");
		for (IRecord record : records) {
			sb.append(record.getConfig());
			sb.append("###");
		}
		return sb.toString();
	}
	
	
	/**
	 * 添加需要保存状态的附件线
	 * @param shape
	 */
	public void addToRecord(Object shape){
		if (shape instanceof IRecord) {
			IRecord rcd = (IRecord) shape;
			records.add(rcd);
		}

//		Log.e("prt2", "add to records "+shape);
	}
	
	
	public void delFromRecord(Object shape){
		if (shape instanceof IRecord) {
			IRecord rcd = (IRecord) shape;
			records.remove(rcd);
		}
//		Log.e("prt2", "remove from records "+shape);
	}
	public void changeTheme() {
		if(this.chart!=null)
			this.chart.changeTheme();
	}
	
	public void dispose() {

	}
	
	public void setRotateAllow(boolean rotate){
		this.rotateAllow=rotate;
	}
	public boolean rotateAllowed(){
		return this.rotateAllow;
	}
}