package com.shisj.kline.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circle extends Base{
	protected float x, y, radius;
	public Circle(float x, float y, float radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
	}


	@Override
	public void paintInner(Canvas canvas,Paint paint) {
		canvas.drawCircle(x+getPositionX(), y+getPositionY(), radius, paint);
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public float getRadius() {
		return radius;
	}


	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	
	
}
