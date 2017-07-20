package com.shisj.kline.shape;


import com.shisj.kline.chart.core.Theme;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 蜡烛
 * @author shishengjie
 *
 */
public class Candle extends Base{
	private float x, y, lineBegin,lineEnd,rectBegin, rectEnd;
	private boolean arise;//上升
	public static float radius=10;//矩形半径
	
	private String debug;
	
	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

	public Candle(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public void setStyle(float array[]){
		this.lineBegin=array[0];
		this.lineEnd=array[1];
		this.rectBegin=array[2];
		this.rectEnd=array[3];
	}
	
	public void setStyle(float a,float b,float c,float d){
		this.lineBegin=a;
		this.lineEnd=b;
		this.rectBegin=c;
		this.rectEnd=d;
	}

	public void setX(float x) {
		this.x = x;
	}
	public float getX(){
		return this.x;
	}


	public void setY(float y) {
		this.y = y;
	}

	

	public float getLineBegin() {
		return lineBegin;
	}

	public void setLineBegin(float lineBegin) {
		this.lineBegin = lineBegin;
	}

	public float getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(float lineEnd) {
		this.lineEnd = lineEnd;
	}

	public float getRectBegin() {
		return rectBegin;
	}

	public void setRectBegin(float rectBegin) {
		this.rectBegin = rectBegin;
	}

	public float getRectEnd() {
		return rectEnd;
	}

	public void setRectEnd(float rectEnd) {
		this.rectEnd = rectEnd;
	}

	public boolean isArise() {
		return arise;
	}

	public void setArise(boolean arise) {
		this.arise = arise;
	}
	
//	@Override
//	public void setVisible(boolean visible) {
//		if("0".equals(debug)&&!visible)
//			System.out.println("111111");
//		super.setVisible(visible);
//	}

	@Override
	public void paintInner(Canvas canvas,Paint paint) {
		//y没有用到 ，因为k线图暂时不会偏移
		float x=this.x+getPositionX(),y=this.y+getPositionY();
		//再绘制矩形
		if(arise){
			paint.setColor(Theme.getColor("CANDLE_RISE_BG"));
		}else{
			paint.setColor(Theme.getColor("CANDLE_DOWN_BG"));
		}
		//先绘制直线
		canvas.drawLine(x, lineBegin, x, lineEnd, paint);
		if(Math.abs(rectBegin-rectEnd)<0.1)
			rectEnd+=1;
		canvas.drawRect(x-radius, rectBegin, x+radius, rectEnd, paint);
//		paint.setColor(Theme.CANDLE_DOWN_BG);
//		canvas.drawLine(x, lineBegin, x, lineEnd, paint);
		
	}
	
}
