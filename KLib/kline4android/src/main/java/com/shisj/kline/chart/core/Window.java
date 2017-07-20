package com.shisj.kline.chart.core;

import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.shape.Line;



import android.graphics.Canvas;
import android.graphics.Color;

/**
 * 窗口
 * @author shishengjie
 *
 */
public abstract class Window extends SubPainter{

	private Line _topLine=null;
	public KLine kLine=null;
	public Window(AbstractChart chart,float height){
		super(chart);
		setHeight(height);
		this.kLine=(KLine)chart;
	}
	
	
	@Override
	public void onResize(Canvas canvas) {
		
	}
	
	@Override
	public void onPaint() {
		if(_topLine==null){
			_topLine=new Line(0, 0, kLine.getpWidth(), 0);
			_topLine.setColor(Theme.getColor("WINDOW_TOPLINE_BG"));
			_topLine.setZlevel(20);
			addShape(_topLine);
		}else{
			_topLine.setStopX(kLine.getpWidth());
		}
	
	}
	
	/**
	 * 销毁窗口
	 */
	@Override
	public void onDispose() {
		delShape(_topLine);
	}
	/**
	 * 根据y轴数值获取窗口对应的内容
	 * @param ypx
	 * @return
	 */
	public abstract String getAxisYText(float ypx);
	
}
