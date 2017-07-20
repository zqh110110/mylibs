package com.shisj.kline.shape;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class Line extends Base{
	private float startX, startY, stopX, stopY;
	private String text;//文字描述
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getStartX() {
		return startX;
	}

	public float getStartY() {
		return startY;
	}

	private boolean dashed=false;
	private float dLineWidth=15,dSegWidth=5;
	private float textSize=10.0f;
	
	public boolean isDashed() {
		return dashed;
	}

	public void setDashed(boolean dashed) {
		this.dashed = dashed;
	}


	

	public Line(float startX, float startY, float stopX, float stopY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.stopX = stopX;
		this.stopY = stopY;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}


	public void setStartY(float startY) {
		this.startY = startY;
	}


	public void setStopX(float stopX) {
		this.stopX = stopX;
	}


	public void setStopY(float stopY) {
		this.stopY = stopY;
	}


	@Override
	public void paintInner(Canvas canvas,Paint paint) {
//		if(getPositionY()>0)
//			System.out.println(1111111);
		float startX=this.startX+getPositionX();//+translateX;
		float stopX=this.stopX+getPositionX();//+translateX;
		float startY=this.startY+getPositionY();//+translateY;
		float stopY=this.stopY+getPositionY();//+translateY;
	
		if(dashed){
			float deltaX=Math.abs(startX-stopX),
					deltaY=Math.abs(startY-stopY);
			float lineLen=(float) Math.sqrt(deltaX*deltaX+deltaY*deltaY);//线段的长度
			int xFlag=stopX-startX>0?1:-1;
			int yFlag=stopY-startY>0?1:-1;
			float beginX=startX,beginY=startY,endX=0,endY=0;
			float begin,end;
			if(0==deltaX){
				begin=startY;
				end=stopY;
			}else if(0==deltaY){
				begin=startX;
				end=stopX;
			}else{
				begin=0;
				end=lineLen;
			}
			//begin初始化为起始点的X
			while(begin<end){
				if(0==deltaX){
					endX=startX;
					endY=begin+yFlag*dLineWidth;
				}else if(0==deltaY){
					endX=begin+xFlag*dLineWidth;
					endY=startY;
				}else{
					begin+=dLineWidth*xFlag;
					float x=getLocation(deltaX,deltaY,begin*begin);
					float y=x*deltaY/deltaX;
					endX=startX+x;
					endY=startY+y;
				}
				if(endX*xFlag>stopX||endY*yFlag>stopY){//如果绘制结束点超过了设置的结束坐标
					endX=stopX;
					endY=stopY;
				}
				canvas.drawLine(beginX, beginY, endX, endY, paint);
				
				if(0==deltaX){
					begin+=(dLineWidth+dSegWidth)*yFlag;
					beginX=startX;
					beginY=begin;
				}else if(0==deltaY){
					begin+=(dLineWidth+dSegWidth)*xFlag;
					beginX=begin;
					beginY=startY;
				}else{
					begin+=dSegWidth*xFlag;
					float x=getLocation(deltaX,deltaY,begin*begin);
					float y=x*deltaY/deltaX;
					beginX=startX+x;
					beginY=startY+y;
				}
				
			}
			
			//最后绘制一下
			canvas.drawLine(endX, endY, stopX, stopY, paint);
		}else{
			canvas.drawLine(startX, startY, stopX, stopY, paint);
			if(text!=null){
				paint.setTextSize(textSize=10.0f);
				canvas.drawText(text, stopX, stopY, paint);
			}
		}
	}
	
	/**
	 * x^2(1+(QY^2)/(QX^2))=100
	 * @param delatX
	 * @param deltaY
	 * @param rs	
	 * @return
	 */
	public float getLocation(float delatX,float deltaY,float rs){
		return (float) Math.sqrt(rs/(1+(deltaY*deltaY)/(delatX*delatX)));
	}

}
