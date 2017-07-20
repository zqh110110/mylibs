package com.shisj.kline.shape;

import com.shisj.kline.util.IGetBitMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;


/**
 * 辅助线的点，会被选择和触发
 * @author shishengjie
 * @date 2016-6-12 上午11:18:56
 */
public class AssistPoint extends Circle{
	public static float endY=1000f;
	IAssistMove listener=null;
	private float moveRadius=50;
	Bitmap bitmap=null;
	
	int len=50;
	boolean nextHide=false;
	@Override
	public void paintInner(Canvas canvas, Paint paint) {
		
		super.paintInner(canvas, paint);
		if(nextHide)return;
		if(bitmap!=null){
			int x=(int) (getX()+getPositionX()-len);
			int y=(int)(getY()+getPositionY()-len);
			Rect rect=new Rect(x,y,x+len*2,y+len*2);
			Rect rectx=new Rect(0, 0, len*2, len*2);
			
			try{
			canvas.drawBitmap(bitmap, rect,rectx, paint);
			}catch(RuntimeException e){
				
			}
			if (bitmap != null && !bitmap.isRecycled()) 
			{ 
				bitmap=null;
			} 
			
			if(nextHide){
				bitmap=null;
//				nextHide=false;
			}
		}
	}
	
	public void nextHide(){
		this.nextHide=true;
	}
	
	public void clearBitmap(){
		this.bitmap=null;
	}
	
	
	
	
	@Override
	public boolean inArea(float x, float y) {
		//centerX和centerY应该取相对于窗口的坐标
		float centerX=this.getPositionX()+this.getX();
		float centerY=this.getPositionY()+this.getY();
		float radius=moveRadius;
		if(x>=(centerX-radius)&&x<=(centerX+radius)){
			if(y>=(centerY-radius)&&y<=(centerY+radius)){
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public void actionUp(MotionEvent event,IGetBitMap provider) {
		if(listener!=null)
			listener.up();
//		this.bitmap=null;
		if(provider!=null)
			this.bitmap=provider.getBitmap();
	}
	
	@Override
	public void actionMove(MotionEvent event,float offsetX,float offsetY,IGetBitMap provider) {
		this.nextHide=false;
		float x=getX();
		float y=getY();
		if(y+offsetY>AssistPoint.endY){
			return;
		}
		setX(x+offsetX);
		setY(y+offsetY);
		if(listener!=null)
			listener.move(offsetX,offsetY);
		if(provider!=null)
			this.bitmap=provider.getBitmap();
	}
	
	
	public void setListener(IAssistMove listener){
		this.listener=listener;
	}
	public AssistPoint(float x, float y, float radius) {
		super(x, y, radius);
		setMoveable(true);
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
}

