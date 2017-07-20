package com.shisj.kline.shape;

import com.shisj.kline.chart.core.SubPainter;
import com.shisj.kline.util.IGetBitMap;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;



/**
 * 图形的基础类
 * @author shishengjie
 * @date 2016-6-12 上午11:18:30
 */
public abstract class Base extends Drawable  {

	private boolean moveable;//是否可移动
	private Integer color;//画笔颜色
	private int alpha=255;//透明度
	private float paintWidth=1f;//画布宽度
	private boolean visible=true;
	private SubPainter parent=null;
	public SubPainter getParent() {
		return parent;
	}

	public void setParent(SubPainter parent) {
		this.parent = parent;
	}

	public float getPositionX() {
		return parent.getLocationX();
	}
	
	public float getPositionY() {
		return parent.getLocationY();
	}
		
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public float getPaintWidth() {
		return this.paintWidth;
	}

	public void setWidth(float width) {
		this.paintWidth = width;
	}

	@Override
	public void paint(Canvas canvas, Paint paint) {
		canvas.save();
		if(!visible)return;
		if(color!=null)
			paint.setColor(color);
		paint.setStrokeWidth(paintWidth);
		paintInner(canvas,paint);
		canvas.restore();
	}
	
	protected abstract void paintInner(Canvas canvas, Paint paint) ;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	

	
	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	
	/**
	 * 指定的点是否在区域内
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean inArea(float x,float y){
		return false;
	}
	
	
	public void actionDown(MotionEvent event){
		
	}
	
	/**
	 * 移动时会多次触发，mvX和mvY是与上次触点相比的偏移量
	 * @param event
	 * @param mvX
	 * @param mvY
	 */
	public void actionMove(MotionEvent event,float mvX,float mvY,IGetBitMap provider){
		
	}
	
	public void actionUp(MotionEvent event,IGetBitMap provider){
		
	}
	
}
