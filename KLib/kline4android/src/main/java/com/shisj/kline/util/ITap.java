package com.shisj.kline.util;

import android.view.MotionEvent;

public interface ITap {
	/**
	 * 返回false 则不执行后续事件了
	 * @param event
	 * @return
	 */
	public boolean tap(MotionEvent event);
}
