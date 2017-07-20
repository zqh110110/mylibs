package com.shisj.kline.chart.kline.ext;

import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.shape.Base;
import com.shisj.kline.util.KPosition;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.view.MotionEvent;

/**
 * 默认的绘制器中的shape使用了30以下
 * 需要注意:若visible 则不需要click/touchemove/scale
 * @author shishengjie
 * 之前使用的这个作为十字线，但后来需要全屏幕展现，需要展现closePoint用于关闭，因此只能将其作为
 * 形状加入到crossLinePainter中
 */
public class CrossLine extends Base{
	public final static String TYPE="CL";
	private float x=20,y=20;//十字坐标的x和y数据
	private float line_H,line_W;//直线的宽度和高度
	KPosition selectedPos=null,movePos=null,savePos;//点击的位置和中间位置
	private float crossLen=20f,blackLen=10f,fullLen=crossLen+blackLen;
	private float rect_W,rect_H=26f,timeRect_W=-1f;
	private String price,priceExt,time;
	boolean showLeft=false;
//	private float xSpan=0.0f;//设置x的左侧偏移量
	public boolean isShowLeft() {
		return showLeft;
	}

	public void setShowLeft(boolean showLeft) {
		this.showLeft = showLeft;
	}

	public interface ICallback{
		public void update(KPosition pos);
	}
	public ICallback callback;
	KLine kLine;
	
//	public void setXSpan(float xSpan){
//		
//		this.xSpan=xSpan;
//	}
	public CrossLine(KPosition selectedPos){
		setZlevel(30);
		setColor(Theme.getColor("CROSS_LINE_BG"));
		setWidth(1f);
		this.selectedPos=selectedPos;
		//获取kchart设置的相关数据
	}
	
	public void resize(float pHeight,float pWidth,float axisWidth){
		this.line_H=pHeight;
		this.line_W=pWidth;
		this.rect_W=axisWidth;
		//
		
	}
	
	private void paintRect(Canvas canvas,Paint paint){
		
		paint.setTextSize(Theme.getFloat("CROSS_X_TEXT_SIZE"));
		//绘制X轴矩形
		if(timeRect_W<0f)
			timeRect_W=paint.measureText("20160526150500");
//		timeRect_W=100;
		float x=this.x-timeRect_W/2;
		float y=line_H;
		canvas.save();
		paint.setColor(Theme.getColor("CROSS_X_RECT_BG"));
		canvas.drawRect(x,y,x+timeRect_W,y+rect_H,paint);
		
		
		//绘制文字,TOP和center
		paint.setColor(Theme.getColor("CROSS_X_TEXT_BG"));
		
		FontMetricsInt fontMetrics = paint.getFontMetricsInt(); 
		int baseline = (fontMetrics.bottom - fontMetrics.top) / 2+4;
		paint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(this.time, this.x, y+baseline, paint);
		
		
		//绘制Y轴上面的矩形
		x=line_W;
		y=this.y-rect_H/2;
		paint.setColor(Theme.getColor("CROSS_Y_RECT_BG"));
		canvas.drawRect(x,y,x+rect_W,y+rect_H,paint);
		
		//绘制文字
		paint.setColor(Theme.getColor("CROSS_Y_TEXT_BG"));
		paint.setTextSize(Theme.getFloat("AXIS_Y_TEXT_SIZE"));
		
		//垂直居中 水平居左
		fontMetrics = paint.getFontMetricsInt(); 
		baseline=(fontMetrics.bottom - fontMetrics.top) / 4;
		paint.setTextAlign(Paint.Align.LEFT);
		canvas.drawText(this.price, line_W+2, this.y+baseline, paint);
		
		
		if(showLeft){//绘制X轴左边的矩形
			paint.setColor(Theme.getColor("CROSS_Y_RECT_BG"));
			canvas.drawRect(0,y,rect_W,y+rect_H,paint);
			//绘制文字
			paint.setColor(Theme.getColor("CROSS_Y_TEXT_BG"));
			paint.setTextSize(Theme.getFloat("AXIS_Y_TEXT_SIZE"));
			paint.setTextAlign(Paint.Align.RIGHT);//...
			canvas.drawText(this.priceExt, rect_W-2, this.y+baseline, paint);
		}
		
		
		canvas.restore();
	}
	
	/**
	 * 绘制十字坐标线
	 * @param canvas
	 * @param paint
	 */
	private void paintLine(Canvas canvas,Paint paint){
		//分为三步
		paint.setColor(Theme.getColor("CROSS_LINE_BG"));
		canvas.drawLine(this.x, 0, this.x, this.y-fullLen, paint);
		canvas.drawLine(this.x, this.y-crossLen, this.x, this.y+crossLen, paint);
		canvas.drawLine(this.x, this.y+fullLen, this.x, line_H, paint);
		
		//分为三步
		canvas.drawLine(0, this.y, this.x-fullLen, this.y, paint);
		canvas.drawLine(this.x-crossLen, this.y, this.x+crossLen, this.y, paint);
		canvas.drawLine(this.x+fullLen, this.y, line_W, this.y, paint);
		
	}
	@Override
	protected void paintInner(Canvas canvas, Paint paint) {
		KPosition pos=null;
		if(selectedPos==null){
			return;//还未选中点，不需要绘制
		}
		if(movePos==null){
			pos=selectedPos;
		}else{
			pos=movePos;
		}
		savePos=pos;
		this.x=pos.getX();//+this.xSpan;
		this.y=pos.getY();
		this.time=pos.getTime();
		//若在main中，取
		
		this.price=String.valueOf(pos.getPrice());
		this.priceExt=String.valueOf(pos.getPriceExt());
		//绘制直线
		paintLine(canvas, paint);
		//绘制矩形
		paintRect(canvas, paint);
		
		int index=pos.getIndex();
		//更新文字显示
		if(callback!=null)
			callback.update(savePos);
		
	}

	public void setCallback(CrossLine.ICallback callback){
		this.callback=callback;
	}
	
	
	public void touchMove(KPosition posRet) {
		if(inArea(posRet)){
			movePos=posRet;
		}
	}
	
	/**
	 * 判断是否在绘制区域内
	 * @param pos
	 * @return
	 */
	private boolean inArea(KPosition pos){
		float x=pos.getX(),y=pos.getY();
		if(x>=0&&x<line_W&&y>=0&&y<line_H){
			return true;
		}
		return false;
	}

	public void scale(KPosition pos) {
		if(selectedPos==null)return;//若无选中点则返回
		//获取之前的index转换为新的index,修改选中点的信息
		selectedPos=pos;
	}

	public void clickDown(KPosition tmp) {
		//设置选中点
//		KPosition tmp=kchart.adsorb(event.getX(), event.getY(),true,0);
//		KPosition tmp=new KPosition(event.getX(), event.getY());
		if(inArea(tmp)){
			selectedPos=tmp;
		}
		movePos=null;
	}

	
	public void clickUp(MotionEvent event) {
		if(movePos!=null){
			selectedPos=movePos;//将点击
			movePos=null;
		}
	}

	public String getConfig() {
		// TODO Auto-generated method stub
		return TYPE+"|"+savePos.getIndex()+"|"+savePos.getPrice()+"|END";
	}	
	
	public KPosition getSavePos(){
		return this.savePos;
	}
	
	public float getX(){
		return this.x;
	}
	public float getY(){
		return this.y;
	}
	
	public KPosition getSelectedPos(){
		return this.selectedPos;
	}
	
}
