package com.shisj.kline.shape;

import com.shisj.kline.util.KPosition;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

/**
 * 折线
 * @author shishengjie
 *
 */
public class PolyLine extends Base{
	//折线点, [x0 y0 x1 y1 x2 y2 ...]
	protected KPosition points[];
	protected float bottom=Float.MIN_VALUE;
	protected int lastIndex;
	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottomLimit(float bottom) {
		this.bottom = bottom;
	}

	public KPosition[] getPoints() {
		return points;
	}

	public void setPoints(KPosition[] points) {
		this.points = points;
	}

	public PolyLine() {
		super();
	}

	@Override
	public void paintInner(Canvas canvas,Paint paint) {
		float startX=points[0].getX()+getPositionX(),startY=points[0].getY()+getPositionY();
		int last=points.length;
		if(lastIndex!=0)last=lastIndex;
		for(int i=1;i<last;i++){
			if(points[i]==null)break;
			float stopX=points[i].getX()+getPositionX(),stopY=points[i].getY()+getPositionY();
			//如果超出范围则不绘制
			if(bottom!=Float.MIN_VALUE){
				if(startY>bottom||stopY>bottom){
					startX=stopX;
					startY=stopY;
					continue;
				}
			}
			canvas.drawLine(startX, startY, stopX, stopY, paint);
			startX=stopX;
			startY=stopY;
		}
	}
	
}

