package com.shisj.kline.shape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;

public class Text extends Base{
	private float x, y;//width, height;
	private String text;
	private Align verticalAlign=Text.Align.BOTTOM;//垂直
	private Paint.Align horizonAlign=Paint.Align.LEFT;//居左
	private boolean isWrap=false;//是否换行
	private int wrapWidth=300;
	public float getWrapWidth() {
		return wrapWidth;
	}

	public void setWrapWidth(int wrapWidth) {
		this.wrapWidth = wrapWidth;
	}

	public boolean isWrap() {
		return isWrap;
	}

	public void setWrap(boolean isWrap) {
		this.isWrap = isWrap;
	}

	public void setHorizonAlign(Paint.Align horizonAlign) {
		this.horizonAlign = horizonAlign;
	}

	public enum Align {
	        /**
	         * The text is drawn to the right of the x,y origin
	         */
	        MIDDLE,
	        /**
	         * The text is drawn centered horizontally on the x,y origin
	         */
	        TOP,
	        /**
	         * The text is drawn to the left of the x,y origin
	         */
	        BOTTOM;
	    }
	public void setVerticalAlign(Align verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private float textSize=20f;
	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public Text(String text,float x, float y) {
		super();
		this.x = x;
		this.y = y;
//		this.width = width;
//		this.height = height;
		this.text=text;
	}

	public void setX(float x) {
		this.x = x;
	}


	public void setY(float y) {
		this.y = y;
	}
	
	public float getY(){
		return this.y;
	}

//	public void setWidth(float width) {
//		this.width = width;
//	}
//
//
//	public void setHeight(float height) {
//		this.height = height;
//	}

	@Override
	public void paintInner(Canvas canvas,Paint paint) {
		if(isWrap()){
			TextPaint txtPaint=new TextPaint(paint);
			txtPaint.setColor(getColor());
			txtPaint.setTextSize(getTextSize());
			txtPaint.setAntiAlias(true);
			StaticLayout layout=new StaticLayout(text,txtPaint,wrapWidth,Alignment.ALIGN_NORMAL,1f,1f,true);
			canvas.save();
			canvas.translate(x, y);
			layout.draw(canvas);
			canvas.restore();
			return;
		}
		float x=this.x+getPositionX(),y=this.y+getPositionY();
		paint.setTextSize(this.textSize);
		
		FontMetricsInt fontMetrics = paint.getFontMetricsInt();  
	    int baseline = 0;  
	    // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()  
//	    
	    if(verticalAlign==Text.Align.TOP){
	    	baseline = (fontMetrics.bottom - fontMetrics.top) / 2;  
	    }else if(verticalAlign==Text.Align.MIDDLE){
	    	baseline = (fontMetrics.bottom - fontMetrics.top) / 4; 
	    }else if(verticalAlign==Text.Align.BOTTOM){//默认是bottom
	    	baseline=0;
	    }
	    
	    paint.setTextAlign(horizonAlign);
	    
		canvas.drawText(text,x,y+baseline,paint);
		
		// baseline
//		paint.setColor(Color.BLUE);
//		canvas.drawLine(x, y, x+50, y, paint);
////		// top
//		paint.setColor(Color.DKGRAY);
//		canvas.drawLine(x, y+fontMetrics.top, 1024, y+fontMetrics.top, paint);
////		 bottom
//		paint.setColor(Color.GREEN);
//		canvas.drawLine(x, y+fontMetrics.bottom, 1024, y+fontMetrics.bottom, paint);
	}
	
	
}
