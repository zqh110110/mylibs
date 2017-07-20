package com.shisj.kline.chart.kline;

import java.util.ArrayList;
import java.util.Date;

import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.core.Window;
import com.shisj.kline.shape.Candle;
import com.shisj.kline.util.IRecord;
import com.shisj.kline.util.IRefreshY;
import com.shisj.kline.util.KPosition;

import android.graphics.Canvas;
import android.util.Log;


/**
 * k线图控制器，内部包含了绘制器，子绘制器，数据队列，更新/缩放监听器等
 * @author shishengjie
 * @date 2016-6-13 上午10:47:55
 */
public class KLine extends AbstractChart implements IRecord{
	
	public static final String TYPE="KC";
	float axisWidth,axisHeight;//坐标系的宽度和高度
	float zWidth,zHeight;//屏幕的宽高
	float pWidth,pHeight;//绘制区域的宽高
	float cWidth;//绘制candle区域的宽高 cHeight
	float xBegin;//x轴起始位置
	int xSpan;//蜡烛个数
	float drift=0.0f;//偏移量
	float driftIndex=0.0f;
	float leftExceed=0.0f;
	float lineSize=6;//x轴直线个数
	float xLineSeg=0;//x轴竖线间隔距离
	float yLineSeg=50;//y轴竖线间隔
	float candleWidth=0;//蜡烛宽度
	float interval=0;//蜡烛直接的间隔
	float xBeginRatio=5.10f/5.5f;//xBegin的比例 即 在 325/550处
	int candleCount,leftCount,rightCount;//蜡烛池：最多可容纳的蜡烛总数。为了重复利用[由于xbegin不在中间，需要计算左侧蜡烛数和右侧蜡烛数]
	float ymax,ymin,PPYP;//Y轴相关数据，当前最大价格 最小价格  单元像素价格
	int showBegin,showEnd;//当前展现的起始 结束蜡烛的index
	int scaleStep[]=new int[]{1,2,3,4,5,6,7,8,16};//设置梯度 内容为竖线内的蜡烛数量
	private int currentStep=4;
	private boolean moveable=true;//是否可滑动
	KAxisX axisX=null;
	KAxisY axisY=null;
	float adsorbY,adsorbYDefault=20;//阀值
	KMain main=null;
	boolean loading=false;
	public KMain getKMain() {
		return main;
	}

	ArrayList<Window> windows=new ArrayList<Window>();
	
	public KLine(CandleQueue queue,float width,float height) {
		super(queue);
		this.axisWidth=Theme.dip2pxExt(width);//width;//
		this.axisHeight=Theme.dip2pxExt(height);//height;//
		Log.e("kline", "height="+height+" axisWidth="+axisWidth+", axisHeight="+axisHeight+" Theme:"+Theme.DENSITY);
		main=new KMain(this);//创建子绘制器
		
		addSubPainter(main);//添加到chart中
		
		axisX=new KAxisX(this);
		addSubPainter(axisX);//添加到chart中
		
		axisY=new KAxisY(this);
		addSubPainter(axisY);//添加到chart中
		
		this.axisY.addRefreshY(this.main);
	}

	
	public void hidePriceLine(){
		if(this.main.pLine!=null)
		this.main.pLine.setVisible(false);
	}
	@Override
	public void resize(Canvas canvas) {
		this.zWidth=canvas.getWidth();
		if(getFixedHeight()>0){
			this.zHeight=getFixedHeight();
		}else{
			this.zHeight=canvas.getHeight();
		}
		//绘制区域的宽度
		this.pWidth=this.zWidth-this.axisWidth;
		//绘制区域的高度
		this.pHeight=this.zHeight-this.axisHeight;
		Log.e("kline", "w:"+pWidth+" h:"+pHeight+" w:"+zWidth+" h:"+zHeight+" this.axisHeight"+this.axisHeight+" "+axisWidth);
		//绘制蜡烛区域的宽度和高度
		this.cWidth=this.pWidth;
//		this.cHeight=this.pHeight-(getWindowHeight());
		
		main.setLayout(0, 0, zWidth, zHeight);
		axisX.setLayout(0, 0, this.zWidth, this.zHeight);
		axisY.setLayout(this.pWidth, 0, this.axisWidth, this.zHeight);
		
		this.xBegin=this.pWidth*xBeginRatio;//懒,先写死吧,根据x轴直线个数计算cBegin位置
		this.xLineSeg=this.pWidth/5.5f;//x轴线之间的距离
//		this.xLineSeg=this.yLineSeg;//x轴线之间的距离
		this.yLineSeg=this.xLineSeg;//y轴线之间的距离
		resizeWindow();//重新设置window的location和高度
//		this.drift=0;//此处需要在resize时将drift设置为合适的值
		if(Float.compare(this.candleWidth, 1f)==-1){
			this.driftIndex=0;
		}else{
			this.driftIndex=this.drift/this.candleWidth;
		}
		if(this.driftIndex<1){
			this.driftIndex=1f;
		}
		calcXLocation();
		setStep(currentStep);
		resetDrift();
		calcShowIndex();
		calcYInfo(this.showBegin,this.showEnd);
	}
	
	
	/**
	 * 根据xbegin和drift计算x轴的相关位置
	 */
	public void calcXLocation(){
		float left=this.drift+this.xBegin;
		this.leftExceed=left%this.xLineSeg-this.xLineSeg;//计算最左边的距离
	}
	
	public void resetDrift(){
		//
		this.drift=this.driftIndex*this.candleWidth;
	}
	
	/**
	 * 设置step
	 * 计算：蜡烛间隔、蜡烛半径、蜡烛宽度，可容纳蜡烛数量
	 * @param index
	 */
	public void setStep(int index){
		this.currentStep=index;
		//x轴线之间的蜡烛个数
		int xSpan=this.scaleStep[index];
        float perCandle=this.xLineSeg/xSpan/2.6f;
        this.xSpan=xSpan;
        this.interval=perCandle*0.6f;//蜡烛之间的间隔
        Candle.radius=perCandle;//蜡烛的半径
		this.candleWidth=perCandle*2.6f;//蜡烛宽度+间隔
		this.candleCount=(int)(this.pWidth/this.candleWidth)+6;//可容纳的蜡烛数量
		//之前采用平分的方法不正确，需要计算左右两边的蜡烛数
		this.leftCount=(int) (this.candleCount*xBeginRatio)+1;
		this.rightCount=this.candleCount-leftCount;
		this.showEnd=leftCount;
	}
	
	/**
	 * 计算当前要展现的范围
	 * 有时范围出错，需要设置这里
	 */
	public void calcShowIndex(){
		//偏移的数量
		int size=(int)(this.drift/this.candleWidth);
		//偏移量为超过右侧蜡烛数
        if(size<=rightCount){
            this.showBegin=0;
            this.showEnd=leftCount+size;
        }else{
            this.showBegin=size-rightCount-1;
            this.showEnd=leftCount+size+1;
        }
        if(this.showBegin<0)
        	this.showBegin=0;
        //数量大于
        if((this.showEnd-this.showBegin+1)>=this.candleCount){
        	this.showEnd=this.showBegin+this.candleCount-1;
        }
	}
	
	/**
	 * 计算Y轴相关数据，主要包括三个：
	 * ymin:y=0时的价格
	 * ymax:y=pHeight的价格
	 * PPYP:Price of Per Y-Px
	 */
	public void calcYInfo(int begin,int end){
		float mxn[]=queue.getMaxmin(begin,end);
		float tmp=(mxn[0]-mxn[1])/10f;
		if(tmp==0)tmp=this.ymax/10;//若数据相同
		this.ymax=mxn[0]+tmp;
		this.ymin=mxn[1]-tmp;
        this.PPYP=(this.ymax-this.ymin)/(this.getcHeight());
	}
	
	
	private boolean vertify(){
		CandleQueue queue=getQueue();
		if(queue==null)return false;
	    CandleDataProvider	provider=queue.getCandleDataProvider();
	    if(provider==null)return false;
	    return true;
	}
	
	@Override
	public void swipe(float seg) {
		move(seg);
	}
	
	/**
	 * 平移
	 * @param x
	 * @param force 是否强制，即使为0 用于刷新
	 */
	public void move(float x,boolean force){
		if(!vertify())return;
		if(!force){
			if(!moveable)return;
			//偏移量为0
			if(x==0)return;
		}
		
		//左移不能超过
		float tmp=this.drift+x;
        if(tmp<0){
            x=-this.drift;
        }
        //开始偏移
		this.drift+=x;
		
		//新增逻辑，若偏移量超过了最大数据的偏移，则不能继续向右拉动
		int size=getQueue().size();
		float last=getXPx(size+2);//last元素首屏展现时可能是正的，超过首屏一般是负数
		if(last>0){
			this.drift=0;
		}else{
			if(this.drift>-last)
				this.drift=-last;
		}
		
		
		calcShowIndex();
		calcYInfo(this.showBegin,this.showEnd);
		//x轴
		calcXLocation();
		//若showEnd已经到了当前数据的一半，需要加载数据，给定起始日期，获取数据
		if(getQueue().size()==0||queue.isEnd())return;
		if(this.showEnd>getQueue().size()/2){
			if(!loading){
				this.loading=true;
				Date date=getQueue().getNextLoadDate();
				if(date==null){
					this.loading=false;
					return;
				}
				getQueue().getCandleDataProvider().getData(this,date, 0);
			}
		}
	}
	
	
	public void loadOver(){
		this.loading=false;
	}
	@Override
	public void move(float xseg) {
		move(xseg,false);
	}

	@Override
	public void scale(float index) {
		if(index>0){
			more();
		}else{
			less();
		}
	}
	
	private void more(){
		if(this.currentStep==0)
			return;
		this.doScale(this.currentStep-1);
	}
	
	private void less(){
		if(this.currentStep==this.scaleStep.length-1)
            return;
        this.doScale(this.currentStep+1);
	}
	
	private void doScale(int index){
		//清除多余candle的显示
		main.clear();
		int count=(int)(this.drift/this.candleWidth);
		this.setStep(index);
		//计算偏移
		this.drift=count*this.candleWidth;
		this.move(1,true);
		
		//触发缩放的监听事件
		painter.doSubScale();
	}

	public void resetAxisXTSize(){
//		if(queue!=null&&queue.getCandleDataProvider()!=null)
		this.resetAxisXTextSize(null);
	}

	
	@Override
	public void setCandleDataProvider(CandleDataProvider provider) {
		super.setCandleDataProvider(provider);
		this.resetAxisXTextSize(provider.getTimeFormate());
		this.main.setInfo("");
	}
	
	
	@Override
	public String beforeParse(String config) {
		String macd = "###MACD";
		if (config.indexOf(macd) != -1) {
			config = config.replace(macd, "");
			config = macd + config;
		}
		return config;
	}
	
	@Override
	public String getConfig() {
		// TODO Auto-generated method stub
		return TYPE+"|"+((int)(this.drift/this.candleWidth))+"|"+currentStep+"|END";
	}
	
	
	/**
	 * 吸附功能，返回的KPosition相对于canvas
	 * @param x
	 * @param y
	 * @param calc	是否计算time和price
	 * @param chartsX	为true时表示x是相对于canvas的坐标；false为相对于窗口的坐标
	 * @return
	 */
	public KPosition adsorbExt(float x,float y,boolean calc,boolean chartsX,boolean inKArea){
		if(chartsX){
			x=x+this.drift;//转换为相对于canvas的坐标
		}
		KPosition ret=this.adsorb(x,y,calc,inKArea);
        ret.setX(ret.getX()-this.drift);//ret.x-=this.drift;
        return ret;
	}
	public KPosition adsorb(float x,float y,boolean calc){
		return adsorb(x,y,calc,adsorbYDefault,false);
	}
	public KPosition adsorb(float x,float y,boolean calc,boolean inKArea){
		return adsorb(x,y,calc,adsorbYDefault,inKArea);
	}
	
	public KPosition adsorb4Move(float x,float y){
//		KPosition pos=adsorb(x, y,calc, _adsorbY);
//		KPosition pos=oldPos;
//		pos.setX(x);
//		pos.setY(y);
//		if(!calc){//若不计算，将
//			pos.setIndex(oldPos.getIndex());
//			pos.setPrice(oldPos.getPrice());
//			pos.setTime(oldPos.getTime());
//		}
//		return pos;
		KPosition pos=new KPosition(x, y);
		float maxLen=this.drift+this.xBegin-x;
		int size;
		float sizeF=(maxLen/this.candleWidth);//计算x点右边蜡烛个数,17.999-->17
		size=(int) sizeF;
        if(sizeF-size>0.99){
        	size++;
        }
        pos.setIndex(size);
        pos.setTime(queue.getTime(size));
        pos.setPrice(getAxisYText(y));
        return pos;
	}
	
//	public KPosition adsorb(float x,float y,boolean calc,float _adsorbY){
//		return this.adsorb(x,y,calc,_adsorbY,false);
//	}
	public float interval(){
		return this.interval;
	}
	
	/**
	 * 根据指定的x和y坐标获取吸附后的坐标值
	 * @param x
	 * @param y
	 * @param calc
	 * @param _adsorbY
	 * @return
	 */
	public KPosition adsorb(float x,float y,boolean calc,float _adsorbY,boolean inKArea){
		if(y>getcHeight()&&inKArea){
			y=getcHeight();
		}
		this.adsorbY=_adsorbY;
		KPosition ret=new KPosition(x,y,0);
		boolean noneFlag=false;
        if(x>this.drift+this.xBegin){//偏移的右边
            noneFlag=true;
            //return ret;
        }
        float maxLen=noneFlag?(x-this.drift-this.xBegin):(this.drift+this.xBegin-x);//获取x点到起始点+偏移的距离
        float mod=maxLen%this.candleWidth;//计算x点距离右边蜡烛的距离
        int size=(int) (maxLen/this.candleWidth);//计算x点右边蜡烛个数,17.999-->17
        if(this.candleWidth-mod<0.01){
        	size++;
        	mod=0f;
        }
        size=noneFlag?size:-size;//
        float last_x=this.drift+this.xBegin+size*this.candleWidth;//计算右边蜡烛的x
        if(mod>this.candleWidth/2){//下一个
            ret.setX(noneFlag?last_x+this.candleWidth:last_x-this.candleWidth);
            ret.setIndex(noneFlag?-size-1:-size+1);
        }else if(mod<this.candleWidth/2){
            ret.setX(last_x);
            ret.setIndex(-size);
        }else{
            ret.setX(x);
            ret.setIndex(-size);
        }

        //计算y轴的吸附，已经知道index，获取candle的几个值即可
        Candle candle;
        if(y!=Float.MIN_VALUE){
            if(ret.index>=this.candleCount){
                candle=main.getCandle(ret.index%this.candleCount);
            }else{
                candle=main.getCandle(ret.index);
            }
            if(candle!=null){
                if(Math.abs(candle.getLineBegin()-y)<this.adsorbY){
                    ret.setY(candle.getLineBegin());
                }else if(Math.abs(candle.getLineEnd()-y)<this.adsorbY){
                    ret.setY(candle.getLineEnd());
                }else if(Math.abs(candle.getRectBegin()-y)<this.adsorbY){
                    ret.setY(candle.getRectBegin());
                }else if(Math.abs(candle.getRectEnd()-y)<this.adsorbY){
                    ret.setY(candle.getRectEnd());
                }else{
                    ret.setY(y);
                }
            }else{
            	ret.setY(y);
            }
        }

        //计算x轴和y轴显示内容
        if(calc){
            ret.setTime(queue.getTime(ret.index));
            ret.setPrice(getAxisYText(ret.getY()));
            CandleData data=this.queue.getCandleData(ret.index); 
            if(data!=null){
            	String fmt=null;
            	CandleDataProvider	provider=queue.getCandleDataProvider();
         	    if(provider!=null){
         	    	String type=provider.getDateType();
//         	    	Log.e("kchart","type="+type);
         	    	if("01234".indexOf(type)==-1){//分时图,1M,5M,30M,2H
         	    		fmt="yyyy-MM-dd";
         	    	}
         	    }
            	ret.setPriceExt(data.getDate(fmt)+"\r\n"
            			+"开盘:"+formatPrice(data.getOpened())+"\r\n"
            			+"收盘:"+formatPrice(data.getClosing())+"\r\n"
            			+"最高:"+formatPrice(data.getHighest())+"\r\n"
            			+"最低:"+formatPrice(data.getLowest()));
            }
        }
//        if(ret.getX()>this.pWidth||ret.getY()>this.pHeight){
//        	return null;
//        }
        return ret;   
    }
	
	
	/**
	 * 获取显示内容
	 * @return
	 */
	private String getAxisYText(float ypx){
		Window window=getWindowByYPX(ypx);
		if(window==null){
			return this.getPriceTxt(ypx);
		}else{
			return window.getAxisYText(ypx-window.getLocationY());
		}
//		return "";
	}
	
	/**
	 * 根据Y轴获取处于哪个窗口
	 * @return
	 */
	public Window getWindowByYPX(float ypx){
		float tmp=this.getcHeight();
		if(ypx<tmp){
			return null;//不在window中
		}else{//大于等于cHeight
			for(Window win:windows){
				tmp+=win.getHeight();
				if(ypx<=tmp){
					return win;
				}
			}
			
		}
		return null;
	}
	
	/**
	 * 清除多余的candle，scale时需要使用
	 */
	public void clear(){
		main.clear();
	}
	
	/**
	 * 计算某个蜡烛应该的drift
	 * @param count
	 * @return
	 */
	public float calcDrift(int count){
		return count*this.candleWidth;
	}
	
	
	
	
	/**
	 * 设置X轴文字大小，如重新设置格式内容时需要调用
	 * @param txt
	 */
	public void resetAxisXTextSize(String txt){
		this.axisX.setTextSize(txt);
	}
	
	public float getAxisWidth() {
		return axisWidth;
	}
	public float getAxisHeight() {
		return axisHeight;
	}

	public float getpWidth() {
		return pWidth;
	}

	public float getpHeight() {
		return pHeight;
	}
	public float getxBegin() {
		return xBegin;
	}
	public float getLeftExceed() {
		return leftExceed;
	}


	public float getXLineSeg() {
		return xLineSeg;
	}
	
	public float getYLineSeg() {
		return yLineSeg;
	}
	
	/**
	 * 计算出的蜡烛最大值
	 * @return
	 */
	public int getCandleCount() {
		return candleCount;
	}


	public int getShowBegin() {
		return showBegin;
	}

	public int getShowEnd() {
		return showEnd;
	}


	public float getCandleWidth() {
		return candleWidth;
	}


	public CandleQueue getQueue() {
		return queue;
	}


	public float getcWidth() {
		return cWidth;
	}


	public void setcWidth(float cWidth) {
		this.cWidth = cWidth;
	}


	public float getcHeight() {
		return this.pHeight-getWindowHeight();
	}
	
	/**
	 * 获取所有窗口的高度
	 */
	private float getWindowHeight(){
		float ret=0.0f;
		for(Window win:windows){
			ret+=win.getHeight();
		}
		return ret;
	}
	
	/**
	 * x轴线之间的蜡烛个数
	 * @return
	 */
	public int getxSpan() {
		return xSpan;
	}
	
	/**
	 * 获取偏移量
	 * @return
	 */
	public float getDrift() {
		return drift;
	}
	/**
	 * 根据偏移量，计算出x轴坐标的起始时间
	 * @return
	 */
	public int getTimeBegin(){
		
		 int ret=(int) ((this.drift+this.xBegin)/this.xLineSeg);
		 float mod=(this.drift+this.xBegin)%this.xLineSeg;
//		 Log.e("prt2", "ret="+ret+"  mod="+mod+"  xls:"+this.xLineSeg);
		 return ret;
	}
	/**
	 * 获取时间文字内容
	 * @param xSeg	第x根竖线
	 * @return
	 */
	public String getTimeText(int xSeg){
		return getQueue().getTime(xSeg*this.xSpan);
	}
	
	public int getCurrentStep() {
		return currentStep;
	}
	/**
	 * 设置默认的step
	 * @param currentStep
	 */
	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}
	
	public float getYmax() {
		return ymax;
	}

	public float getYmin() {
		return ymin;
	}
	
	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	
	private void resize(){
		
	}
	
	public void setAxis(float axisWidth,float axisHeight) {
		this.axisWidth = Theme.dip2pxExt(axisWidth);
		this.axisHeight= Theme.dip2pxExt(axisHeight);
		
		
	}


	/**
	 * 获取相对canvas的值 
	 * @param index
	 * @return
	 */
	public float getXPx(int index){
        return this.xBegin-(index*this.candleWidth);
     }
    
	/**
	 * 获取相对于窗口的值0~pwidth
	 * @param index
	 * @return
	 */
	public float getXPxWin(int index){
		return this.drift+getXPx(index);
	}
	/**
	 * 根据价格获取Y轴坐标
	 * @param price
	 * @return
	 */
	public float getYPx(float price) {
		return (this.ymax-price)/(this.PPYP);
	}
	
	public float getPrice(float height){
		return this.ymax-(this.PPYP*height);
	}
	public String getPriceTxt(float height){
		float f=getPrice(height);
		return String.format("%."+CandleDataProvider.priceDigit+"f", f);
	}
	
	public String formatPrice(float price){
		return String.format("%."+CandleDataProvider.priceDigit+"f", price);
	}

	
	/**
	 * 对外接口：更新首个元素数据
	 * @param candle
	 */
	public void update(CandleData candle,int index){
		Date candleTime=candle.getDate();//获取传入的时间
		CandleData dat=queue.getCandleData(index);//index数据的时间
		if(dat==null)return;
		if(dat.getDate().equals(candleTime)){//如果时间相等才更新
			queue.update(candle,index);
			move(0,true);
			//图形只能实现update，子绘制器也可以实现update
			if(index==0)
			painter.doSubUpdate(candle);
		}
	}
	
	public void prepend(CandleData candle){
		queue.prepend(candle);
		move(0,true);
		//图形只能实现update，子绘制器也可以实现update
		painter.doSubUpdate(candle);
	}
	/**
	 * 获取X轴的文字大小
	 * @return
	 */
	public float getAxisTextSize(){
		return this.axisX.getTextSize();
	}


	@Override
	public void reLayout() {
		this.move(0, true);
	}

	
	/***===================================================*****/
	/**
	 * 添加窗口
	 * @param window
	 */
	public void addWindow(Window window){
		windows.add(window);//加入到数组中
		addSubPainter(window);//加入绘制器
		//重置窗口的位置
		float newY=this.getcHeight();
		window.setLocationY(newY);
		
		//判断是否实现刷新Y轴
		if (window instanceof IRefreshY) {
			IRefreshY ref = (IRefreshY) window;
			this.axisY.addRefreshY(ref);
			ref.createY(0, axisY);
			
			//刷新一下main的Y轴
			this.main.resizeY(this.axisY.getyBegin(),null);
		}
	}
	
	/**
	 * 删除窗口
	 * @param win
	 */
	public void delWindow(Window win){
		win.onDispose();
		windows.remove(win);//从数组中移除
		painter.delPainter(win);
		if (win instanceof IRefreshY) {
			IRefreshY ref = (IRefreshY) win;
			ref.disposeY();//销毁Y轴内容
			this.axisY.delRefreshY(ref);//从列表中删除
		}
		//刷新一下main的Y轴
		this.main.resizeY(this.axisY.getyBegin(),null);
		
	}
	
	public void resizeWindow(){
		float location=getpHeight();
		float height=getpHeight()/4;
		for(Window window:windows){
			window.setHeight(height);
			location-=height;
			window.setLocationY(location);
		}
	}
	
	@Override
	public boolean getBitMap() {
		return true;
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return KLine.TYPE;
	}
}
