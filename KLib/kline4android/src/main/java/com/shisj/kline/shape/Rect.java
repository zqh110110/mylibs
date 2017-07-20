package com.shisj.kline.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Rect extends Base{
	protected float x, y, width, height;

	
	public Rect(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setX(float x) {
		this.x = x;
	}


	public void setY(float y) {
		this.y = y;
	}


	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getW(){
		return this.width;
	}
	public float getH(){
		return this.height;
	}


	public void setHeight(float height) {
		this.height = height;
	}


	@Override
	public void paintInner(Canvas canvas,Paint paint) {
		float x=this.x+getPositionX(),y=this.y+getPositionY();
		canvas.drawRect(x, y, x+width, y+height, paint);
	}
	
}
