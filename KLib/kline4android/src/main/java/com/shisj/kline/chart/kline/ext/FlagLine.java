package com.shisj.kline.chart.kline.ext;

import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.SubPainter;
import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.shape.Line;
import com.shisj.kline.shape.Text;
import com.shisj.kline.util.IRecord;

import android.graphics.Canvas;
import android.graphics.Color;


/**
 * 默认的绘制器中的shape使用了30以下
 * @author shishengjie
 *
 */
public class FlagLine extends SubPainter implements IRecord{

	public FlagLine(AbstractChart chart) {
		super(chart);
	}

	public static final String TYPE="FL";
	Line line=null;
	Text text=null;
	String showText="";
	float price;
	int zlevel=31;
	float textX=20f,textSize=20f;
	private int color=Color.BLUE;
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getShowText() {
		return showText;
	}

	public void setShowText(String showText) {
		this.showText = showText;
	}

	
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	@Override
	public void onPaint() {
		KLine kline=(KLine) chart;
		if(line==null){
			line=new Line(0,0,kline.getpWidth(),0);
			line.setColor(color);
			line.setZlevel(zlevel);
			addShape(line);
		}
		if(text==null){
			text=new Text(showText, 0, 0);
			text.setTextSize(textSize);
			text.setColor(Color.BLUE);
			text.setZlevel(zlevel);
			addShape(text);
		}
	
		if(price>=kline.getYmin()&&price<=kline.getYmax()){
			line.setVisible(true);
			text.setVisible(true);
			float ret=kline.getYPx(price);
			line.setStartY(ret);
			line.setStopY(ret);
			text.setX(textX);
			text.setY(ret-5);
		}else{
			line.setVisible(false);
			text.setVisible(false);
		}
		
	}

	@Override
	public void onResize(Canvas canvas) {
		KLine kline=(KLine) chart;
		line.setStopX(kline.getpWidth());
	}
	
	

	@Override
	public void onDispose() {
		delShape(line);
		delShape(text);
	}

	@Override
	public String getConfig() {
		//
		return TYPE+"|"+getPrice()+"|"+getShowText();
	}

	
	
	
}
