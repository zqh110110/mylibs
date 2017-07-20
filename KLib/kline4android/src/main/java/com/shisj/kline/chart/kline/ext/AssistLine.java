package com.shisj.kline.chart.kline.ext;

import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.SubPainter;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.shape.AssistPoint;
import com.shisj.kline.shape.IAssistMove;
import com.shisj.kline.shape.Line;
import com.shisj.kline.util.IRecord;
import com.shisj.kline.util.KPosition;

import android.graphics.Canvas;
import android.util.Log;

/**
 * 辅助线，其他的辅助线如黄金分割线都是基于此类并重写paintLines方法即可
 * @author shishengjie
 *
 */
public class AssistLine extends SubPainter implements IRecord {
	public static final String TYPE="ASL";
	private float bx,by,ex,ey;
	AssistPoint begin=null,mid=null,end=null;
	KPosition beginPos=null,endPos=null;
	private float radius=5;
	Line line=null;
	private int pointZLevel=11;
	KLine kline=null;
	public AssistLine(AbstractChart chart,float bx,float by,float ex,float ey){
		super(chart);
		this.bx=bx;
		this.by=by;
		this.ex=ex;
		this.ey=ey;
		this.kline=(KLine) chart;
	}
	
	@Override
	public void onPaint() {

		// TODO Auto-generated method stub
		if(begin==null){
			this.bx-=kline.getDrift();
			this.ex-=kline.getDrift();
			//初始创建时，需要根据指定点先吸附一次
			beginPos=kline.adsorbExt(bx, by, true, true,true);
			endPos=kline.adsorbExt(ex, ey, true, true,true);
//			bx=beginPos.getX();
//			by=beginPos.getY();
//			ex=endPos.getX();
//			ey=endPos.getY();
			
			begin=new AssistPoint(bx, by, radius);
			begin.setZlevel(pointZLevel);
			begin.setColor(Theme.getColor("ASSIST_POINT_BG"));
			addShape(begin);
			
			float midPos[]=getMidPos(bx, by, ex, ey);
			mid=new AssistPoint(midPos[0], midPos[1], radius);
			mid.setZlevel(pointZLevel);
			mid.setColor(Theme.getColor("ASSIST_POINT_BG"));
			addShape(mid);
			
			end=new AssistPoint(ex, ey, radius);
			end.setZlevel(pointZLevel);
			end.setColor(Theme.getColor("ASSIST_POINT_BG"));
			addShape(end);
			
			
			begin.setListener(new IAssistMove() {
				
				public void down(){}
				@Override
				public void move(float offsetX, float offsetY) {
					beginPos=null;//不设置为null的话，move事件刷新时会根据pos计算，导致不能移动
					
					//清除其他点的bitmap,以防止覆盖
					end.clearBitmap();
					mid.clearBitmap();
				}

				@Override
				public void up() {
					beginPos=kline.adsorb(begin.getX(), begin.getY(), true);
					begin.setX(beginPos.getX());
					begin.setY(beginPos.getY());
//					Log.e("prt2","adsorb index:"+beginPos.getIndex());
					
					begin.nextHide();
				}
			});
			
			end.setListener(new IAssistMove() {
				public void down(){}
				@Override
				public void move(float offsetX, float offsetY) {
					endPos=null;//不设置为null的话，move事件刷新时会根据pos计算，导致不能移动
					
					//清除其他点的bitmap,以防止覆盖
					begin.clearBitmap();
					mid.clearBitmap();
				}

				@Override
				public void up() {
					endPos=kline.adsorb(end.getX(), end.getY(), true);
					end.setX(endPos.getX());
					end.setY(endPos.getY());
//					kline.debug("adsorb index:"+endPos.getIndex());
					
					end.nextHide();
				}
			});
			
			mid.setListener(new IAssistMove() {
				public void down(){}
				@Override
				public void move(float x, float y) {
					
					if((begin.getY()+y)>AssistPoint.endY||(end.getY()+y)>AssistPoint.endY){
						return;
					}
					
					begin.setX(begin.getX()+x);
					begin.setY(begin.getY()+y);
					end.setX(end.getX()+x);
					end.setY(end.getY()+y);
					beginPos=null;//不设置为null则会根据pos设置位置，导致不发生变化
					endPos=null;
					
					begin.clearBitmap();
					end.clearBitmap();
				}

				@Override
				public void up() {
					beginPos=kline.adsorb(begin.getX(), begin.getY(), true);
					begin.setX(beginPos.getX());
					begin.setY(beginPos.getY());
					
					endPos=kline.adsorb(end.getX(), end.getY(), true);
					end.setX(endPos.getX());
					end.setY(endPos.getY());
					
					mid.nextHide();
				}
			});
			
			//create   line
			line=new Line(bx,by,ex,ey);
			line.setColor(Theme.getColor("ASSIST_LINE_BG"));
			line.setWidth(2f);
			line.setZlevel(10);
			addShape(line);
			
		}else{//刷新
			
		}
		
//		Log.e("prt2","beginPos is:"+(beginPos==null?"null":"not null"));
//		Log.e("prt2","endPos is:"+(endPos==null?"null":"not null"));
		
			if(beginPos!=null){
				int index=beginPos.getIndex();
				float price=beginPos.getPriceValue();
				float x=kline.getXPxWin(index);
				float y=kline.getYPx(price);
				begin.setX(x);
				begin.setY(y);
			}
			if(endPos!=null){
				int index=endPos.getIndex();
				float price=endPos.getPriceValue();
				float x=kline.getXPxWin(index);
				float y=kline.getYPx(price);
				end.setX(x);
				end.setY(y);
			}
		
		
		//刷新
			line.setStartX(begin.getX());
			line.setStartY(begin.getY());
			line.setStopX(end.getX());
			line.setStopY(end.getY());
			
			float tmp[]=getMidPos(begin.getX(), begin.getY(), end.getX(), end.getY());
			mid.setX(tmp[0]);
			mid.setY(tmp[1]);
//		kChart.debug(begin.getX()+" "+begin.getPositionX());
		KPosition bpos,epos;
		if(beginPos==null){
			bpos=new KPosition(begin.getX(), begin.getY());
		}else{
			bpos=beginPos;
		}
		if(endPos==null){
			epos=new KPosition(end.getX(), end.getY());
		}else{
			epos=endPos;
		}
//		paintLines(bpos,epos,begin.getX(),end.getY());
		paintLines(begin.getX(),begin.getY(),end.getX(),end.getY());
		AssistPoint.endY=kline.getcHeight();
	}
	
	public void paintLines(float begin,float end,float a,float b){
		
	}

	/**
	 * 计算mid点的值
	 * @param bx
	 * @param by
	 * @param ex
	 * @param ey
	 * @return
	 */
	public float[] getMidPos(float bx,float by,float ex,float ey){
		float ret[]=new float[2];
		ret[0]=(bx+ex)/2;
		ret[1]=(by+ey)/2;
		return ret;
	}

	@Override
	public void onResize(Canvas canvas) {
		AssistPoint.endY=kline.getcHeight();
	}

	@Override
	public String getConfig() {
		// TODO Auto-generated method stub
		if(beginPos==null||endPos==null)return TYPE;
		return TYPE+"|"+beginPos.getIndex()+"|"+beginPos.getPrice()+"|"+endPos.getIndex()+"|"
				+endPos.getPrice()+"|END";
	}
	
	@Override
	public void onDispose() {
		delShape(begin);
		delShape(mid);
		delShape(end);
		delShape(line);
	}

	@Override
	public void onTheme() {
		begin.setColor(Theme.getColor("ASSIST_POINT_BG"));
		mid.setColor(Theme.getColor("ASSIST_POINT_BG"));
		end.setColor(Theme.getColor("ASSIST_POINT_BG"));
		line.setColor(Theme.getColor("ASSIST_LINE_BG"));
	}
}
