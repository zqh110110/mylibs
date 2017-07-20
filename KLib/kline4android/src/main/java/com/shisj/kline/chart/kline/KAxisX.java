package com.shisj.kline.chart.kline;

import java.util.ArrayList;

import com.shisj.kline.chart.core.SubPainter;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.shape.Line;
import com.shisj.kline.shape.Rect;
import com.shisj.kline.shape.Text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;



/**
 * zlevel is 10-19
 * @author shishengjie
 *
 */
public class KAxisX extends SubPainter{
	
	private Rect rect=null;//底部矩形
	private Line xAxisLine=null;//底部直线
	private ArrayList<Line> lines=new ArrayList<Line>();
	private ArrayList<Text> texts=new ArrayList<Text>();
	private float textSize=-1;
	private final int rectLevel=10,textLevel=11;
	KLine kline=null;
	//x轴展现时间数据
	//图形有直线
	public KAxisX(KLine kline){
		super(kline);
		this.kline=kline;
	}

	@Override
	public void onPaint() {
		float begin=kline.getLeftExceed();
		float end=kline.getpWidth();
		//根据展现的蜡烛区间计算应该展现的首个时间，计算的不准确，放弃
		//int timeBeg=(kChart.getShowEnd()-1)/kChart.getxSpan();
		//还是根据偏移计算吧
		int timeBeg=kline.getTimeBegin();
		int index=0;
		//x轴的line需要改变
//		Log.e("prt2", "begin="+begin+",end="+end+",timeBeg="+timeBeg);
		while(begin<end){
			float beginX=begin+kline.getXLineSeg();
			//if(beginX>end)break;
			if(index>=lines.size())break;
			//设置竖线的位置
			lines.get(index).setStartX(beginX);
			lines.get(index).setStopX(beginX);
			
			texts.get(index).setTextSize(textSize);
			//设置文字的位置
			texts.get(index).setX(beginX);
			//设置文字的描述
			String txt=kline.getTimeText((timeBeg--));
			texts.get(index).setText(txt);
			
			index++;
			begin+=kline.getXLineSeg();
		}
	
		
	}
	
	private int  calcTextSize(String text){
		if(text==null||text.trim().length()==0)
			text="20160516";
		text+="AA";
		float width=kline.getpWidth()/5.0f;//获取宽度
		if(width<1.0)return 0;
		int ret=20;
		//5个
		Paint paint=new Paint();
		paint.setTextSize(ret);
		float tmp=paint.measureText(text);
		while(tmp>width){
			paint.setTextSize(--ret);
			tmp=paint.measureText(text);
		}
		return ret;
	}

	public void setTextSize(String text){
		if(text==null){
			this.textSize=-1;
		}else{
			this.textSize=calcTextSize(text);
		}
	}
	
	public float getTextSize(){
		return this.textSize;
	}

	@Override
	public void onDispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCreate(Canvas canvas) {
		if(xAxisLine==null){
			xAxisLine=new Line(0,0,0,0);
			xAxisLine.setWidth(2);
			xAxisLine.setColor(Theme.getColor("AXIS_X_BORDER_BG"));
			xAxisLine.setZlevel(rectLevel);
			addShape(xAxisLine);
		}
		if(rect==null){
			rect=new Rect(0,0,0,0);
			rect.setColor(Theme.getColor("AXIS_X_BG"));
			rect.setZlevel(rectLevel);
			addShape(rect);
		}
		
		
	}

	@Override
	public void onResize(Canvas canvas) {
		float height=kline.getpHeight();
		float width=kline.getpWidth();
//		xAxisLine=new Line(0,height,width,height);
		xAxisLine.setStartX(0);
		xAxisLine.setStartY(height);
		xAxisLine.setStopX(width);
		xAxisLine.setStopY(height);
		
//		rect=new Rect(0,height,width,kline.getAxisHeight());
		rect.setX(0);
		rect.setY(height);
		rect.setWidth(width);
		rect.setHeight(kline.getAxisHeight());
		
		this.textSize=-1;//resize时需要修改x轴文字的大小
		createAxisShow();
	}

	/**
	 * 创建竖线和X轴文字
	 */
	private void createAxisShow(){
		float begin=kline.getLeftExceed();
		float end=kline.getpWidth();
		int timeBeg=kline.getTimeBegin();
		
		//先清除一下之前的竖线和text
		for(int i=0;i<lines.size();i++){
			Line line=lines.get(i);
			Text text=texts.get(i);
			delShape(line);
			delShape(text);
		}
		lines.clear();
		texts.clear();
		
		//重新创建并添加
		while(begin<end){
			//创建直线
			float beginX=begin+kline.getXLineSeg();
			Line line=new Line(beginX,0,beginX,kline.getpHeight());
			line.setDashed(true);
			line.setWidth(2);
			line.setZlevel(0);
			line.setColor(Theme.getColor("AXIS_X_LINE_BG"));
			addShape(line);
			lines.add(line);
						
			//创建文字,
			//计算文字内容，如何计算？
			//根据展现的蜡烛数量计算，如展现0-2
			String txt=kline.getTimeText((timeBeg--));
			Text text=new Text(txt,beginX,kline.getpHeight()+4);
			text.setZlevel(textLevel);
			text.setColor(Theme.getColor("AXIS_X_TEXT_BG"));
			if(textSize<=0)
				textSize=calcTextSize(txt);//计算大小
			text.setTextSize(textSize);//此处的大小需要计算
			text.setHorizonAlign(Paint.Align.CENTER);
			text.setVerticalAlign(Text.Align.TOP);
			addShape(text);
			texts.add(text);
						
			begin+=kline.getXLineSeg();
		}	
	}

	@Override
	public void onTheme() {
		xAxisLine.setColor(Theme.getColor("AXIS_X_BORDER_BG"));
		rect.setColor(Theme.getColor("AXIS_X_BG"));

		for(Line line:lines){
			line.setColor(Theme.getColor("AXIS_X_LINE_BG"));
		}
		for(Text text:texts){
			text.setColor(Theme.getColor("AXIS_X_TEXT_BG"));
		}
	}
}



