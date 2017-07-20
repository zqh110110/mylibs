package com.shisj.kline.chart.kline;

import java.util.ArrayList;

import com.shisj.kline.chart.core.SubPainter;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.shape.Line;
import com.shisj.kline.shape.Rect;
import com.shisj.kline.util.IRefreshY;

import android.graphics.Canvas;



/**
 * zlevel is 20-29
 * @author shishengjie
 *
 */
public class KAxisY extends SubPainter {
	
	private Rect rect=null;
	private Line xAxisLine=null;
	private final float yBegin=20;
	public float getyBegin() {
		return yBegin;
	}

	private ArrayList<IRefreshY> refreshYList=new ArrayList<IRefreshY>();
	private final int rectLevel=21;//textLevel=22; 目前该在IRefreshY中设置
	KLine kLine;
	
	
	//x轴展现时间数据
	//图形有直线
	public KAxisY(KLine kchart) {
		super(kchart);
		this.kLine=kchart;
		setOrder(0);
	}

	/**
	 * 添加属性Y轴的对象
	 * @param refreshY
	 */
	public void addRefreshY(IRefreshY refreshY){
		refreshYList.add(refreshY);
	}
	
	/**
	 * 删除刷新Y轴的对象
	 * @param refreshY
	 */
	public void delRefreshY(IRefreshY refreshY){
		refreshYList.remove(refreshY);
	}
	
	@Override
	public void onPaint() {
		//每次只需要依次调用即可，遍历Y轴刷新器，刷新Y轴
		for(IRefreshY ref:refreshYList){
			ref.refreshY();
		}
	}

	@Override
	public void onDispose() {
		
	}

	@Override
	public void onCreate(Canvas canvas) {
		if(rect==null){
			rect=new Rect(0,0,0,0);
			rect.setColor(Theme.getColor("AXIS_Y_BG"));
			rect.setZlevel(rectLevel);
			addShape(rect);
		}
		
		if(xAxisLine==null){
			xAxisLine=new Line(0,0,0,0);
			xAxisLine.setWidth(1);
			xAxisLine.setColor(Theme.getColor("AXIS_Y_BORDER_BG"));
			xAxisLine.setZlevel(rectLevel);
			addShape(xAxisLine);
		}
		
	}

	@Override
	public void onResize(Canvas canvas) {
		
		rect.setX(0);
		rect.setY(0);
		rect.setWidth(getWidth());
		rect.setHeight(getHeight());
		
		xAxisLine.setStartX(-1);
		xAxisLine.setStartY(0);
		xAxisLine.setStopX(-1);
		xAxisLine.setStopY(kLine.getpHeight());
		
		float begin=this.yBegin;
		for(IRefreshY ref:refreshYList){
			begin=ref.resizeY(begin,this);
		}
		
	}

	@Override
	public void onTheme() {
		rect.setColor(Theme.getColor("AXIS_Y_BG"));
		xAxisLine.setColor(Theme.getColor("AXIS_Y_BORDER_BG"));
	}
	
	

}
