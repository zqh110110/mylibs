package com.shisj.kline.chart.kline.ext;

import java.util.ArrayList;

import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.SubPainter;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.core.Window;
import com.shisj.kline.shape.Line;
import com.shisj.kline.shape.PolyLine;
import com.shisj.kline.shape.Text;
import com.shisj.kline.util.IRecord;
import com.shisj.kline.util.IRefreshY;
import com.shisj.kline.util.KPosition;

import android.graphics.Color;


/**
 * zlevel is 0-9
 * 指标的写在这个里面
 * @author shishengjie
 *
 */
public class MACDWindow extends Window implements IRefreshY,IRecord{

	public final static String TYPE="MACD";
	private ArrayList<Line> lines=null;//保存所有的macd竖线
	Line zeroLine=null;//零线
	private ArrayList<Text> yTexts=null;//保存Y轴数据
	Text leftMACDText;//左上角的文字描述
	PolyLine diffLine=null,deaLine=null;//DIFF折线和DEA折线
	MACDUtil util;//工具类
	int count=0;//保存candleCount
	int zlevel=22;//zlevel
	KPosition diffPos[],deaPos[];//保存DIFF折线和DEA折线的点
	String macdMax="",macdMin="";//保存MACD最大/最小值
	float macdZeroY=0;//零线的Y坐标
	float macdMN[];
	float macdPPYP;
	
	
	public MACDWindow(AbstractChart chart,float height) {
		super(chart,height);
	}
	
	
	@Override
	public float createY(float begin, SubPainter axisY) {
		//创建左上角的文字
		leftMACDText=new Text("MACD(12,26,9)",20,20);
		leftMACDText.setZlevel(30);
		leftMACDText.setColor(Theme.getColor("MACD_TEXT_BG"));
		leftMACDText.setTextSize(Theme.getFloat("MACD_TEXT_SIZE"));
		addShape(leftMACDText);
				
		//创建三个轴文字
		yTexts=new ArrayList<Text>(3);
		float height=getHeight();
		yTexts.add(createText(height/12.0f));
		yTexts.add(createText(0));
		yTexts.add(createText(height*11.0f/12.0f));
				
		zeroLine=new Line(0, 0, kLine.getpWidth(), 0);
		zeroLine.setDashed(true);
		zeroLine.setWidth(1);
		zeroLine.setZlevel(0);
		zeroLine.setColor(Color.DKGRAY);
		addShape(zeroLine);
				
		return 0;
	}

	@Override
	public void refreshY() {
		//根据y轴相关数据YYPY和min max 设置text的内容即可。
		if(yTexts==null)return;
		float size=kLine.getAxisTextSize();
		yTexts.get(0).setText(macdMax);
		yTexts.get(1).setText("0.0000");
		yTexts.get(1).setY(this.macdZeroY);
		yTexts.get(2).setText(macdMin);
		if(this.macdZeroY<yTexts.get(0).getY()+size||this.macdZeroY>yTexts.get(2).getY()){
			yTexts.get(1).setVisible(false);
		}else{
			yTexts.get(1).setVisible(true);
		}
	}


	@Override
	public void disposeY() {
		
	}

	@Override
	public void onDispose() {
		super.onDispose();
		//需要删除图形
		delShape(lines);
		delShape(yTexts);
		delShape(diffLine);
		delShape(deaLine);
		delShape(leftMACDText);
		delShape(zeroLine);
	}

	@Override
	public void onPaint() {
		super.onPaint();
		
		int begin=kLine.getShowBegin();
		int end=kLine.getShowEnd();
		
		if(lines==null){
			lines=new ArrayList<Line>(count);
		}
		if(util==null){
			util=new MACDUtil(kLine);
		}
		if(diffLine==null){
			diffLine=new PolyLine();
			diffLine.setColor(Theme.getColor("MACD_DIFF_LINE_BG"));
			diffLine.setWidth(2f);
			diffLine.setZlevel(16);
			addShape(diffLine);
		}
		if(deaLine==null){
			deaLine=new PolyLine();
			deaLine.setColor(Theme.getColor("MACD_DEA_LINE_BG"));
			deaLine.setWidth(2f);
			deaLine.setZlevel(16);
			addShape(deaLine);
		}
		
		//这步需要吗？
		for(int index=begin;index<=end;index++){
			util.getCandleMACD(index);
		}
		
		int cnt=kLine.getCandleCount();
		if(count<cnt){//若当前count小于目前的cnt，重新构建
			diffPos=new KPosition[cnt+2];
			deaPos=new KPosition[cnt+2];
			count=cnt;
		}
		
		//获取diff数据
		float diffMN[]=util.getDIFFMaxmin(begin, end);
//		float diffPPYP=getPPYP(diffMN);
		//获取dea数据
		float deaMN[]=util.getDEAMaxmin(begin, end);
//		float deaPPYP=getPPYP(deaMN);
		
		//结合两者的数据
		float allMN[]=util.getMaxmin(diffMN,deaMN);
		float allPPYP=getPPYP(allMN);
		
		int j=0;
		for(int index=begin;index<=end;index++,j++){
			float x=kLine.getXPxWin(index);
			float price=util.getCandleDIFF(index);
			float y=getPointY(allMN,price,allPPYP);
			diffPos[j]=new KPosition(x, y);
		}
		diffPos[j]=null;
		diffLine.setPoints(diffPos);
		
		j=0;
		for(int index=begin;index<=end;index++,j++){
			float x=kLine.getXPxWin(index);
			float price=util.getCandleDEA(index);
			float y=getPointY(allMN,price,allPPYP);
			deaPos[j]=new KPosition(x, y);
		}
		deaPos[j]=null;
		deaLine.setPoints(deaPos);
		
		//获取macd数据
		//
		macdMN=util.getMACDMaxmin(begin, end);
		macdPPYP=getPPYP(macdMN);
		this.macdZeroY=getPointY(macdMN,0,macdPPYP);
		macdMax=String.format("%.5f", macdMN[0]);
		macdMin=String.format("%.5f", macdMN[1]);
		j=0;
		for(int index=begin;index<=end;index++,j++){
			Line line=getLine(j);
			float x=kLine.getXPxWin(index);
			float price=util.getCandleMACD(index);
			float y=getPointY(macdMN,price,macdPPYP);
			line.setStartX(x);
			line.setStopX(x);
			line.setStartY(y);
			line.setStopY(macdZeroY);
			
			line.setColor((y<macdZeroY)?Theme.getColor("MACD_SIGN_UP_BG"):Theme.getColor("MACD_SIGN_DOWN_BG"));
			line.setVisible(true);
		}
		//隐藏其余的line
		for(;j<lines.size();j++){
			lines.get(j).setVisible(false);
		}
		if(zeroLine!=null){
			zeroLine.setStartY(this.macdZeroY);
			zeroLine.setStopY(this.macdZeroY);
		}
		
	}
	
	private Line getLine(int index){
		int size=lines.size();
		Line line=null;
		if(index>=size){//创建并添加进去
			line=new Line(0, 0, 0, 0);
			line.setColor(Color.BLUE);
			line.setZlevel(9);
			addShape(line);
			lines.add(line);
			return line;
		}else{
			line=lines.get(index);
			return line;
		}
		
	}
	
	private Text createText(float y){
		Text text=new Text("", kLine.getpWidth(), y);
		text.setTextSize(Theme.getFloat("AXIS_Y_TEXT_SIZE"));
		text.setColor(Theme.getColor("AXIS_Y_TEXT_BG"));
		text.setZlevel(zlevel);
		text.setVerticalAlign(Text.Align.MIDDLE);
		addShape(text);
		return text;
	}
	public float getPointY(float mn[],float price,float ppyp){
		float max=mn[0];
		return (max-price)/ppyp;
	}
	public float getPPYP(float mn[]){
		float max=mn[0];float min=mn[1];
		return (max-min)/getHeight();
	}
	
	
	@Override
	public String getConfig() {
		// TODO Auto-generated method stub
		return TYPE+"|END";
	}

	@Override
	public String getAxisYText(float ypx) {
		//ypx是在window内的偏移
//		float macdMN[] float macdPPYP
		float price=macdMN[0]-(ypx*macdPPYP);// ymax-(price*ppyp)
		return String.format("%.5f",price);
	}


	@Override
	public float resizeY(float begin, SubPainter axisY) {
		for(Text text:yTexts){
			text.setX(kLine.getpWidth());
		}
		yTexts.get(0).setY(getHeight()/12.0f);
		yTexts.get(2).setY(getHeight()*11.0f/12.0f);
		return begin;
	}


	@Override
	public void onTheme() {
		// TODO Auto-generated method stub
		
	}

	
		
}
