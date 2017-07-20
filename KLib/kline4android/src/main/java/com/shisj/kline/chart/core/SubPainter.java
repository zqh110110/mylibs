package com.shisj.kline.chart.core;

import java.util.ArrayList;
import java.util.List;

import com.shisj.kline.chart.kline.CandleData;
import com.shisj.kline.shape.Base;

import android.graphics.Canvas;
import android.view.MotionEvent;




/**
 * 子绘制系统
 * @author shishengjie
 *
 */
public abstract class SubPainter{
	
	private float locationX;//绘制容器的位置
	private float locationY;//绘制容器的位置
	private float width;
	private float height;
	private int zlevel;//绘制容器的层级
	public Painter painter=null;
	public AbstractChart chart;
	boolean created=false;
	boolean visible=true;//是否可见
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isCreated() {
		return created;
	}
	public void setCreated(boolean created) {
		this.created = created;
	}

	private int order=10;//子绘制器绘制顺序 如Y轴需要最后刷新
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public Painter getPainter() {
		return painter;
	}
	
	
	public SubPainter(AbstractChart chart){
		this.chart=chart;
		this.painter=chart.getPainter();
	}
	
	/**
	 * 删除时调用
	 */
	public abstract void onDispose();
	
	/**
	 * 绘制时调用
	 */
	public abstract void onPaint();
	
	/**
	 * 创建时调用，此时 没有pwidth pheight等
	 * @param canvas
	 */
	public  void onCreate(Canvas canvas){};
	
	/**
	 * 由于先create再resize，此时有pwidth和pheight
	 * @param canvas
	 */
	public  void onResize(Canvas canvas){};
	
	
	
	/**
	 * 调用update更新时触发
	 * @param data
	 */
	public void onUpdate(CandleData data){};
	
	public  void onTheme(){}
	
	/**
	 * 缩放时触发
	 */
	public void onScale(){};
	
	
	
	public void onTouceMove(MotionEvent event, float x_down,
			float y_down) {

	}
	
	public void onClickDown(MotionEvent event) {

	}
	
	
	public void onClickUp(MotionEvent event) {

	}
	
	public void onTap(MotionEvent event) {

	}
	
	/**
	 * 为容器添加图形
	 * @param base
	 */
	public void addShape(Base base){
		base.setParent(this);
		this.painter.getStorage().addShape(base);
		this.painter.getStorage().setNeedSort(true);//新增后需要排序
		
		
	}
	
	public void delShape(Base base){
		if(base==null)return;
		base.setParent(null);
		this.painter.getStorage().delShape(base);
		base=null;
	}
	
	public void delShape(List bases){
		if(bases==null)return;
		for(int i=0;i<bases.size();i++){
			Base base=(Base) bases.get(i);
			delShape(base);
		}
	}
	
	/**
	 * 绘制容器的位置
	 * @return
	 */
	public float getLocationX() {
		return this.locationX;
	}
	public void setLocationX(float x) {
		this.locationX = x;
	}
	/**
	 * 绘制容器的位置
	 * @return
	 */
	public float getLocationY() {
		return this.locationY;
	}
	public void setLocationY(float y) {
		this.locationY = y;
	}
	
	/**
	 * 获取子绘制器的层级
	 * @return
	 */
	public int getZlevel() {
		return zlevel;
	}
	public void setZlevel(int zlevel) {
		this.zlevel = zlevel;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setLayout(float x,float y,float width,float height){
		this.locationX=x;
		this.locationY=y;
		this.width=width;
		this.height=height;
	}
	
	
}
