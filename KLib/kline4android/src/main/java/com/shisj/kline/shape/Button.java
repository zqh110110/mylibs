package com.shisj.kline.shape;

import com.shisj.kline.util.IGetBitMap;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.view.MotionEvent;

public class Button extends Circle {

	private String text;
	private int textColor;
	IAssistMove listener=null; 
	

	public void setListener(IAssistMove listener) {
		this.listener = listener;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Button(float x, float y, float radius) {
		super(x, y, radius);
		setMoveable(true);
	}
	
	
	@Override
	public void actionDown(MotionEvent event) {
		if(listener!=null){
			listener.down();
		}
	}
	@Override
	public boolean inArea(float x, float y) {
		//centerX和centerY应该取相对于窗口的坐标
		float centerX=this.getPositionX()+this.getX();
		float centerY=this.getPositionY()+this.getY();
		float radius=this.radius+20;
		if(x>=(centerX-radius)&&x<=(centerX+radius)){
			if(y>=(centerY-radius)&&y<=(centerY+radius)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void paintInner(Canvas canvas, Paint paint) {
		canvas.save();
		super.paintInner(canvas, paint);
		if(text!=null){
			paint.setColor(textColor);
			paint.setTextSize(radius*2);
			paint.setTextAlign(Align.CENTER);
			FontMetricsInt fontMetrics = paint.getFontMetricsInt();  
			int baseline = (fontMetrics.bottom - fontMetrics.top) / 4; 
			canvas.drawText(text, x+getPositionX(), y+getPositionY()+baseline, paint);
		}
		canvas.restore();
	}
}
