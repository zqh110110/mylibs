package com.shisj.kline.chart.kline.ext;

import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.kline.CandleData;
import com.shisj.kline.chart.kline.CandleDataProvider;
import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.shape.Base;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.util.Log;

/**
 * 默认的绘制器中的shape使用了30以下
 * @author shishengjie
 *
 */
public class PriceLine extends Base{
	
	float line_Y=10;//直线的Y
	float line_width;//直线宽度
	float rect_X,rect_Y,rect_W,rect_H,rect_Height=26;
	String price="";
	public PriceLine(float pWidth,float xWidth){
		setZlevel(30);
		setColor(Theme.getColor("PRICE_LINE_BG"));// 
		setWidth(1f);
	}
	
	@Override
	protected void paintInner(Canvas canvas, Paint paint) {
		paint.setColor(Theme.getColor("PRICE_LINE_BG"));
		canvas.drawLine(0, line_Y, line_width, line_Y, paint);
		paint.setColor(Theme.getColor("PRICE_LINE_RECT_BG"));
		canvas.drawRect(rect_X,rect_Y,rect_W,rect_H,paint);
		paint.setColor(Theme.getColor("PRICE_LINE_TEXT_BG"));
		paint.setTextSize(Theme.getFloat("AXIS_Y_TEXT_SIZE"));
		
		//垂直居中 水平居左
		FontMetricsInt fontMetrics = paint.getFontMetricsInt(); 
		float baseline=(fontMetrics.bottom - fontMetrics.top) / 4;
		paint.setTextAlign(Paint.Align.LEFT);
		canvas.drawText(price, rect_X+4, line_Y+baseline, paint);
	}

	public void resize(float pWidth,float xWidth){
		this.line_width=pWidth;
		this.rect_X=this.line_width;
		this.rect_Y=this.line_Y-rect_Height/2;
		this.rect_W=rect_X+xWidth;
		this.rect_H=rect_Y+rect_Height;
	}
	
	public void update(CandleData data,AbstractChart chart) {
		if(data==null)return;
		KLine kLine=(KLine)chart;
		
		float price=data.getClosing();
		Log.e("prt","updateLine:"+price);
		this.line_Y=kLine.getYPx(price);//获取展现的Y坐标
		resize(kLine.getpWidth(),kLine.getAxisWidth());//修改其他位置
		this.price=String.format("%."+CandleDataProvider.priceDigit+"f", price);//修改文字描述
		
		if(price>kLine.getYmax()||price<kLine.getYmin())
		{
			setVisible(false);
		}else{
			setVisible(true);
		}
		
	}
	
}
