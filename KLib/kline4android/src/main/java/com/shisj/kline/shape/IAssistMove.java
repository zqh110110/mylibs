package com.shisj.kline.shape;

/**
 * 
 * @author shishengjie
 *
 */
public interface IAssistMove{
	/**
	 * 移动的偏移量
	 * @param x
	 * @param y
	 */
	public void move(float offsetX,float offsetY);
	
	public void up();
	
	public void down();
}