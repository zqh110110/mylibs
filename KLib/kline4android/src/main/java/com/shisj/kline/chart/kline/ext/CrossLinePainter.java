package com.shisj.kline.chart.kline.ext;

import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.SubPainter;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.chart.kline.ext.CrossLine.ICallback;
import com.shisj.kline.shape.ClosePoint;
import com.shisj.kline.shape.IAssistMove;
import com.shisj.kline.shape.Rect;
import com.shisj.kline.shape.Text;
import com.shisj.kline.util.IRecord;
import com.shisj.kline.util.KPosition;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 辅助线，其他的辅助线如黄金分割线都是基于此类并重写paintLines方法即可
 * 暂时不用这个
 * @author shishengjie
 *
 */
public class CrossLinePainter extends SubPainter implements IRecord{
	public static final String TYPE="CLP";
	KPosition initPosition;
	CrossLine crossLine=null;
	ClosePoint point=null;
	Rect rect=null;
	Text text=null;
	private int pointZLevel=11;
	private float radius=Theme.dip2px(20);
	IAssistMove listener=null;
	KLine kLine=null;
	CrossLine.ICallback cb=null;
	float rectWidth=80,rectHeight=50;
	public CrossLinePainter(AbstractChart chart,KPosition selectedPos,CrossLine.ICallback cb){
		super(chart);
		this.initPosition=selectedPos;
		this.kLine=(KLine) chart;
		this.cb=cb;
	}
	
	public void setTipRect(float width,float height){
		this.rectWidth=width;
		this.rectHeight=height;
	}
	
	@Override
	public void onResize(Canvas canvas) {
		//设置宽高等等
		crossLine.resize(kLine.getpHeight(), kLine.getpWidth(), kLine.getAxisWidth());
		point.setLayout(kLine.getpWidth()-radius, 0, radius, radius);
		
		KPosition saved=crossLine.getSavePos();
		if(saved==null)return;
		//以下是旋转的
		int index=saved.getIndex();
		if(index>kLine.getShowBegin()+1&&index<kLine.getShowEnd()-1)
		{
			float x=kLine.getXPxWin(index);
			float y=kLine.getYPx(saved.getPriceValue());
			KPosition tmp=kLine.adsorb(x,y,true);
			crossLine.clickDown(tmp);
			
		}else{
			//居中
			float width=kLine.getpWidth();
			float height=kLine.getpHeight();
			KPosition tmp=kLine.adsorb(width/2,height/2,true);
			crossLine.clickDown(tmp);
		}
		
	}
	
	@Override
	public void onDispose() {
		delShape(crossLine);
		delShape(point);
		delShape(rect);
		delShape(text);
	}
	
	@Override
	public void onPaint() {
		if(crossLine==null){
			crossLine=new CrossLine(initPosition);
			addShape(crossLine);
			crossLine.setVisible(true);
			
				crossLine.setCallback(new ICallback() {
					
					@Override
					public void update(KPosition pos) {
						resizeTip(pos);
						if(cb!=null){
//							cb.update(pos);
						}
					}
				});
		}
		if(rect==null){
			setTipRect(Theme.getFloat("CROSS_TIP_RECT_WIDTH"),
					Theme.getFloat("CROSS_TIP_RECT_HEIGHT"));
			rect=new Rect(0, 0, this.rectWidth, this.rectHeight);
			rect.setZlevel(30);
			rect.setColor(Theme.getColor("CROSS_TIP_RECT_BG"));
			rect.setVisible(true);
			addShape(rect);
		}
		if(text==null){
			text=new Text("", 120, 120);
			text.setZlevel(31);
			text.setWrap(true);
			text.setColor(Theme.getColor("CROSS_TIP_TEXT_BG"));
			text.setTextSize((Theme.getFloat("CROSS_TIP_TEXT_SIZE")));
			text.setVisible(true);
			addShape(text);
		}
		
		if(point==null){
			point=new ClosePoint(0f,0f,0f,0f);
			point.setZlevel(pointZLevel);
			point.setColor(Color.CYAN);
			if(listener!=null)
				point.setListener(listener);
			addShape(point);
			onResize(null);
		}
	
	}
	
	/**
	 * 根据当前点设置tip的位置
	 * @param pos
	 */
	private void resizeTip(KPosition pos){
		float x=pos.getX();
		float y=pos.getY();
        if(x+this.rectWidth+10>kLine.getpWidth())
            x=x-this.rectWidth-20;
        if(y<this.rectHeight+20)
            y=y+this.rectHeight+20;
        x=x+10;
        y=y-this.rectHeight-10;
		rect.setX(x);
		rect.setY(y);
		
		String price=pos.getPriceExt();
		if(price==null)price="";
		text.setText(price);
		text.setX(x+10);
		text.setY(y+10);
	}
	/**
	 * 设置关闭函数
	 * @param listener
	 */
	public void setClose(IAssistMove listener){
		if(point!=null)
			point.setListener(listener);
		this.listener=listener;
	}

	
	

	@Override
	public String getConfig() {
		KPosition savePos=crossLine.getSavePos();
		return TYPE+"|"+savePos.getIndex()+"|"+savePos.getPrice()+"|END";
	}

	
	@Override
	public void onScale() {
		float x=crossLine.getX();
		float y=crossLine.getY();
		KPosition pos=kLine.adsorb(x,y,true,0,false);
		crossLine.scale(pos);
	}
	
	
	@Override
	public void onTouceMove(MotionEvent event, float x_down,float y_down) {
		if(crossLine==null)return;
		KPosition selectedPos=crossLine.getSelectedPos();
		if(selectedPos==null)return;//若无选中点则返回
		float x_new=selectedPos.getX()+event.getX()-x_down;
		float y_new=selectedPos.getY()+event.getY()-y_down;
//		float x_new=selectedPos.getX()+getLocation(event.getX()-x_down);
//		float y_new=selectedPos.getY()+event.getY()-y_down;
		Log.e("event","x_down="+x_down+" selectedPos.getX()"+selectedPos.getX()+" event.getX()"+event.getX());
		KPosition posRet=kLine.adsorb(x_new,y_new,true,0,false);
		crossLine.touchMove(posRet);
	}
	
	@Override
	public void onTap(MotionEvent event) {
		if(crossLine==null)return;
		KPosition tmp=kLine.adsorb(event.getX(), event.getY(),true,0,false);
		crossLine.clickDown(tmp);
	}

	@Override
	public void onClickDown(MotionEvent event) {
		if(crossLine==null)return;
		KPosition tmp=kLine.adsorb(event.getX(), event.getY(),true,0,false);
		crossLine.clickDown(tmp);
	}

	@Override
	public void onClickUp(MotionEvent event) {
		if(crossLine==null)return;
		crossLine.clickUp(event);
	}


	@Override
	public void onTheme() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
