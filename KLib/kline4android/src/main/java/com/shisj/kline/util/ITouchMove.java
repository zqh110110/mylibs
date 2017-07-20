package com.shisj.kline.util;


import com.shisj.kline.chart.core.AbstractChart;

import android.view.MotionEvent;

public interface ITouchMove {

	public void touchMove(MotionEvent event,AbstractChart chart,float panX,float panY);
}
