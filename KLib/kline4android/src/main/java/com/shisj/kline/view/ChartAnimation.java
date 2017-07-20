package com.shisj.kline.view;


import com.shisj.kline.chart.core.AbstractChart;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 用于动画
 * @author shishengjie
 * @date 2016-6-14 下午7:01:06
 */
public class ChartAnimation extends Animation {
	public final static float SPEED = 2f;
	private float movement = SPEED * 33;
	private boolean stop = false;
	int i = 0;
	ChartView view;
	AbstractChart chart;
	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public float getMovement() {
		return movement;
	}

	public void setMovement(float movement) {
		this.movement = movement;
	}

	public void direction(float x) {
		float tmp = Math.abs(movement);
		if (x > 0) {
			movement = tmp;
		} else {
			movement = -tmp;
		}
	}

	public ChartAnimation(ChartView view) {
		this.view=view;
		this.chart=view.getChart();
		
		this.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				chart.swipeEnd();
			}
		});
	}

	public void reset() {
		i = 0;
	}

	@Override
	protected void applyTransformation(float interpolatedTime,
			Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		if (stop) {
			return;
		}
		chart.swipe(movement);
		view.postInvalidate();
		i++;
	}
}