package com.shisj.kline.util;

import java.util.ArrayList;

import com.shisj.kline.chart.core.SubPainter;



public interface IRefreshY {
	public int textLevel=22;
	public float yBegin=0;
	/**
	 * 首次调用
	 * @param begin
	 * @param axisY
	 * @return
	 */
	public float createY(float begin,SubPainter axisY);
	/**
	 * 刷新Y轴时调用
	 */
	public void refreshY();
	/**
	 * 旋转时调用
	 * @param begin
	 * @param axisY
	 */
	public float resizeY(float begin,SubPainter axisY);
	/**
	 * 新增/取消窗口后调用
	 * @param yBegin
	 */
//	public void onWindowChangeY(float yBegin);
	
	/**
	 * 销毁时若实现了IRefreshY接口，需要调用disposeY
	 */
	public void disposeY();
}
