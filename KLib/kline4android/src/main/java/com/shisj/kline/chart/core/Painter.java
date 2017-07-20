package com.shisj.kline.chart.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.shisj.kline.chart.kline.CandleData;
import com.shisj.kline.shape.Base;



import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 绘制器
 * @author shishengjie
 * @date 2016-6-12 下午1:49:21
 */
public class Painter {

	private Storage storage;//保存所有的图形
	
	private float lastCWidth,lastCHeight;
	Paint paint;
	public Paint getPaint() {
		return paint;
	}

	//这两个是全局的偏移，先不用，保留在这里，
	private float positionX=0;
	private float positionY=0;
//	private float xBegin;//起始绘制位置
	private float zWidth;//画布宽度
	private float zHeight;//画布高度
	
	//子绘制器
	private List<SubPainter> painters=new ArrayList<SubPainter>();
	
	public Painter(Storage storage){
		this.storage=storage;
	}
	public void addPainter(SubPainter painter){
		painters.add(painter);
		sort();
	}
	public void delPainter(SubPainter painter){
		painters.remove(painter);
	}
	
	private void sort(){
		Collections.sort(painters,new Comparator<SubPainter>() {
			@Override
			public int compare(SubPainter o1, SubPainter o2) {
				return o2.getOrder()-o1.getOrder();
			}
		});
	}
	/**
	 * 执行子绘制器的创建
	 * @param canvas
	 */
	public void createShape(Canvas canvas){
		for(SubPainter painter:painters){
			painter.onCreate(canvas);
			painter.setCreated(true);
		}
	}
	private void resizeShape(Canvas canvas){
		for(SubPainter painter:painters){
			painter.onResize(canvas);
		}
	}
	public void setCanvas(Canvas canvas){
		this.zWidth=canvas.getWidth();
		this.zHeight=canvas.getHeight();
	}
	
	
	
	
	public void paint(Canvas canvas,boolean resize){
		if(paint==null){//画笔
			paint=new Paint();
			paint.setAntiAlias(true);
		}
		
		if(resize){//若需要刷新 设置画布的宽高，并调用子画布的resize方法
			this.setCanvas(canvas);
			this.lastCWidth=canvas.getWidth();
			this.lastCHeight=canvas.getHeight();
			resizeShape(canvas);
		}
		//子绘制一次执行，修改图像大小
		for(SubPainter painter:painters){
			painter.onPaint();
		}
		//依次绘制图形
		if(storage==null||storage.getSize()==0)
			return;
		List<Base> shapes=storage.getSortedList();
		for(Base shape:shapes){
			if(shape==null)continue;
			shape.paint(canvas, paint);
		}
		
	}
	
	/**
	 * 遍历执行子绘制器的scale
	 */
	public void doSubScale(){
		for(SubPainter painter:painters){
			painter.onScale();
		}
	}
	
	public void doSubTouchMove(MotionEvent event, float x_down,
			float y_down){
		for(SubPainter painter:painters){
			painter.onTouceMove(event,x_down,y_down);
		}
	}
	
	public void doSubClickDown(MotionEvent event){
		for(SubPainter painter:painters){
			painter.onClickDown(event);
		}
	}
	
	public void doSubClickUp(MotionEvent event){
		for(SubPainter painter:painters){
			painter.onClickUp(event);
		}
	}
	
	public void doSubTap(MotionEvent event){
		for(SubPainter painter:painters){
			painter.onTap(event);
		}
	}

	/**
	 * 遍历执行子绘制器的update
	 */
	public void doSubUpdate(CandleData data){
		for(SubPainter painter:painters){
			painter.onUpdate(data);
		}
	}
	
	public void doChangeTheme(){
		for(SubPainter painter:painters){
			if(painter.isCreated()){
				painter.onTheme();
			}
		}
	}
	
	
	public Storage getStorage() {
		return storage;
	}
	public float getZWidth() {
		return zWidth;
	}
	public float getZHeight() {
		return zHeight;
	}
	
}
