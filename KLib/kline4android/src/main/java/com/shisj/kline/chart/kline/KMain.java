package com.shisj.kline.chart.kline;

import java.util.ArrayList;
import java.util.List;

import com.shisj.kline.chart.core.SubPainter;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.kline.ext.PriceLine;
import com.shisj.kline.shape.Candle;
import com.shisj.kline.shape.Line;
import com.shisj.kline.shape.Rect;
import com.shisj.kline.shape.Text;
import com.shisj.kline.util.IRefreshY;

import android.graphics.Canvas;


/**
 * zlevel is 0-9
 * @author shishengjie
 *
 */
public class KMain extends SubPainter implements IRefreshY{

	private ArrayList<Line> lines=new ArrayList<Line>();
	private ArrayList<Text> texts=new ArrayList<Text>();
	Rect background=null;
	List<Candle> candles=new ArrayList<Candle>();
	KLine kLine=null;
	Text priceText=null;
	public Text getPriceText() {
		return priceText;
	}

	PriceLine pLine=null;
	public KMain(KLine chart) {
		super(chart);
		this.kLine=(KLine)chart;
	}

	@Override
	public void onDispose() {}
	
	@Override
	public void onCreate(Canvas canvas) {
		if(background==null){
			background=new Rect(0, 0, 0, 0);
			background.setColor(Theme.getColor("MAIN_BG"));
			background.setZlevel(0);
			addShape(background);
		}
		if(priceText==null){
			priceText=new Text("",0,0);
			priceText.setZlevel(textLevel);
			priceText.setColor(Theme.getColor("PRICE_TEXT_TEXT_BG"));
			priceText.setTextSize(Theme.getFloat("PRICE_TEXT_TEXT_SIZE"));
			priceText.setVisible(false);//不可见
			priceText.setText("loading...");
			addShape(priceText);
		}
		if(pLine==null){
			pLine=new PriceLine(0,0);
			pLine.setVisible(false);
			addShape(pLine);
		}
	}
	
	@Override
	public void onResize(Canvas canvas) {
		//设置子绘制器的宽度和高度
		setWidth(canvas.getWidth()-kLine.getAxisWidth());
		setHeight(canvas.getHeight()-kLine.getAxisHeight());
		background.setWidth(getWidth());
		background.setHeight(getHeight());
		
		priceText.setX(10);
		priceText.setY(30);
		
		pLine.resize(kLine.getpWidth(),kLine.getAxisWidth());//修改其他位置
		//这是为了更新价格线
		CandleQueue queue=kLine.getQueue();
		if(queue!=null){
			CandleData data=queue.getCandleData(0);
			pLine.update(data,kLine);
		}
		
	}
	
	@Override
	public void onPaint() {
		int beg=kLine.getShowBegin(),end=kLine.getShowEnd();
		for(;beg<=end;beg++){
			//设置相关数据
			setCandleStyle(beg);
		}
		
		//取CandleCount到candles.size()的最小值，将剩余的candle设置为不展现
		//目的是为了左移时将隐藏可展现之外的candle
		if(kLine.getShowBegin()==0){
			int candleEnd=Math.min(kLine.getCandleCount(),candles.size());
			for(;beg<candleEnd;beg++){
				candles.get(beg).setVisible(false);
			}
		}
		
		
		if(pLine!=null){
			//这是为了更新价格线
			CandleQueue queue=kLine.getQueue();
			CandleData data=queue.getCandleData(0);
			pLine.update(data,kLine);
		}
		
	}
	
	@Override
	public void onUpdate(CandleData data) {
		String price=getCandleText(data);
		if(priceText != null)
		priceText.setText(price);
		//更新
		pLine.update(data, kLine);
	}
	
	public void setInfo(String info){
		if(info==null)return;
		if(priceText!=null)
			priceText.setText(info);
	}
	
	public String getCandleText(CandleData data){
		String cur=kLine.getQueue().getCandleDataProvider().getCurrType();//币种对
		String type=kLine.getQueue().getCandleDataProvider().getDateTypeShow();
		String price=cur+" "+type+" "+kLine.formatPrice(data.getOpened())+" "
				+kLine.formatPrice(data.getClosing())+" "
				+kLine.formatPrice(data.getHighest())+" "
				+kLine.formatPrice(data.getLowest());
		
		return price;
	}

	@Override
	public float createY(float begin, SubPainter axisY) {
		int index=0;
		float width=kLine.getpWidth();
		while(begin<kLine.getcHeight()){
			//创建直线
			addUpdateLine(index, begin, width);
			//创建文字
			addUpdateText(index, begin, width);
			begin+=kLine.getYLineSeg();
			index++;
		}
		return begin;
	}

	@Override
	public void refreshY() {
		//根据y轴相关数据YYPY和min max 设置text的内容即可。
		for(int i=0;i<texts.size();i++){
			Text text=texts.get(i);
			Line line=lines.get(i);
			text.setText(kLine.getPriceTxt(line.getStartY()));
		}
	}


	@Override
	public void disposeY() {
		delShape(lines);
		delShape(texts);
	}
	
	@Override
	public float resizeY(float begin, SubPainter axisY) {
		int index=0;
		float width=kLine.getpWidth();
		while(begin<kLine.getcHeight()){
			addUpdateLine(index, begin, width);
			addUpdateText(index,begin,width);
			begin+=kLine.getYLineSeg();
			index++;
		}
		for(;index<texts.size();index++){
			texts.get(index).setVisible(false);
			lines.get(index).setVisible(false);
		}
		return begin;
	}
	
	
	private Line addUpdateLine(int index,float begin,float width){
		int size=lines.size();
		if(index>=size){//>=size 需要创建新的
			Line line=new Line(0,begin,width,begin);
			line.setDashed(true);
			line.setWidth(2);
			line.setZlevel(0);
			line.setColor(Theme.getColor("AXIS_Y_LINE_BG"));
			addShape(line);
			lines.add(line);
			return line;
		}else{
			Line line=lines.get(index);
			line.setStartX(0);
			line.setStartY(begin);
			line.setStopX(width);
			line.setStopY(begin);
			line.setVisible(true);
			return line;
		}
	}
	
	private Text addUpdateText(int index,float begin,float width){
		int size=texts.size();
		if(index>=size){//>=size 需要创建新的
			Text text=new Text(kLine.getPriceTxt(begin),width+4,begin);
			text.setZlevel(textLevel);
			text.setColor(Theme.getColor("AXIS_Y_TEXT_BG"));
			text.setTextSize(Theme.getFloat("AXIS_Y_TEXT_SIZE"));
			text.setVerticalAlign(Text.Align.MIDDLE);
			addShape(text);
			texts.add(text);
			return text;
		}else{
			Text text=texts.get(index);
			text.setText(kLine.getPriceTxt(begin));
			text.setX(width+4);
			text.setY(begin);
			text.setVisible(true);
			return text;
		}
	}
	
	/**
	 * 将所有蜡烛设置为不展现
	 */
	public void clear(){
		for(Candle candle:candles){
			candle.setVisible(false);
		}
	}
	
	/**
	 * 设置某个蜡烛的样式
	 * @param index
	 */
	private void setCandleStyle(int index){
		
		Candle candle=getCandle(index);
//		candle.setDebug(""+index);
		
        CandleData data=kLine.getQueue().getCandleData(index);
        if(data==null){
        	candle.setVisible(false);
        	return;
        }
        candle.setVisible(true);
        candle.setX(kLine.drift+kLine.getxBegin()-(index*kLine.getCandleWidth()));
        candle.setY(0);
        
        
        if(data.getClosing()<data.getOpened()){//开盘大于收盘
            candle.setArise(false);
            candle.setRectBegin(kLine.getYPx(data.getOpened()));//开盘
            candle.setRectEnd(kLine.getYPx(data.getClosing()));//收盘
        }else{//开盘小于收盘
        	candle.setArise(true);
        	candle.setRectBegin(kLine.getYPx(data.getClosing()));//收盘
            candle.setRectEnd(kLine.getYPx(data.getOpened()));//开盘
        }
        
        candle.setLineBegin(kLine.getYPx(data.getHighest()));//最高
        candle.setLineEnd(kLine.getYPx(data.getLowest()));//最低
    }
	
	/**
	 * 获取第index个candle
	 * @param index
	 * @return
	 */
	public Candle getCandle(int index){
		if(index<0)return null;
		index=index%kLine.getCandleCount();
		Candle candle=null;
		if(index>=candles.size()){
			candle=new Candle(0,100);
			candle.setVisible(false);
			candle.setStyle(30f,300f,80f,240f);
			candle.setZlevel(1);
			candle.setWidth(2);
			candles.add(candle);
			addShape(candle);
		}else{
			candle=candles.get(index);//从数组中获取
		}
		
		return  candle;
	}

	@Override
	public void onTheme() {
		background.setColor(Theme.getColor("MAIN_BG"));
		priceText.setColor(Theme.getColor("PRICE_TEXT_TEXT_BG"));
		priceText.setTextSize(Theme.getFloat("PRICE_TEXT_TEXT_SIZE"));

		for(Text text:texts){
			text.setColor(Theme.getColor("AXIS_Y_TEXT_BG"));
			text.setTextSize(Theme.getFloat("AXIS_Y_TEXT_SIZE"));
		}
		for(Line line:lines){
			line.setColor(Theme.getColor("AXIS_Y_LINE_BG"));
		}
					
	}

	
}
