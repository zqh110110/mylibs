package com.shisj.kline.shape;

import com.shisj.kline.util.IGetBitMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * 关闭点
 * @author shishengjie
 *
 */
public class ClosePoint extends Rect{

	IAssistMove listener=null;
	
	public ClosePoint(float x, float y, float width, float height) {
		super(x, y, width, height);
		setMoveable(true);
	}
	
	public void setLayout(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	
	@Override
	public void paintInner(Canvas canvas, Paint paint) {
		
		super.paintInner(canvas, paint);
		paint.setStrokeWidth(1f);
		paint.setColor(Color.RED);
		canvas.drawLine(x, y, x+width, y+height, paint);
		canvas.drawLine(x, y+height, x+width, y, paint);
		
	}
	
	
	@Override
	public boolean inArea(float x, float y) {
		//centerX和centerY应该取相对于窗口的坐标
		float begX=this.getPositionX()+this.x;
		float begY=this.getPositionY()+this.y;
		if(x>=(begX)&&x<=(begX+width*2)){
			if(y>=(begY)&&y<=(begY+height*2)){
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public void actionUp(MotionEvent event,IGetBitMap provider) {
		if(listener!=null)
			listener.up();
	}
	@Override
	public void actionDown(MotionEvent event) {
		if(listener!=null)
			listener.down();
	}
	
	public void setListener(IAssistMove listener){
		this.listener=listener;
	}
	
}

