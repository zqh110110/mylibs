package com.shisj.kline.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Drawable {
	private int zlevel=0;//z层
	private String id;//图形的ID
//	public float positionX=0;//由于窗口容器的概念，目前认为是窗口的位置
//	public float positionY=0;//由于窗口容器的概念，目前认为是窗口的位置
	
//	public float translateX,translateY;//位移
//	
//	
//	public float getTranslateX1() {
//		return translateX;
//	}
//
//	public void setTranslateX2(float translateX) {
//		this.translateX = translateX;
//	}

//	public float getTranslateY() {
//		return translateY;
//	}
//
//	public void setTranslateY(float translateY) {
//		this.translateY = translateY;
//	}

	public int getZlevel() {
		return zlevel;
	}

	public void setZlevel(int zlevel) {
		this.zlevel = zlevel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
//	public float getPositionX() {
//		return positionX;
//	}
//
//	public void setPositionX(float positionX) {
//		this.positionX = positionX;
//	}
//
//	public float getPositionY() {
//		return positionY;
//	}
//
//	public void setPositionY(float positionY) {
//		this.positionY = positionY;
//	}

	public abstract void paint(Canvas canvas,Paint paint);
}
