package com.kfd.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;



public class MyProgressBar extends ProgressBar{
	
	private Paint mPaint;
	
	private String value;
	
	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		setTextSize(10);
		mPaint.setColor(Color.BLACK);
		value = "0%";
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.value, 0, this.value.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        System.out.println("value"+value);
       // System.out.println(getHeight() / 2 +","+rect.centerY());
        canvas.drawText(value, x, y, this.mPaint);
	}
	
	public void setProgress(String value){
		this.value = value;
		invalidate();
	}
	
    /**
     * 设置文字大小
     * 
     * @param size
     */
    public void setTextSize(int size)
    {
        if (size > 0)
        {
            final float scale = this.getContext().getResources().getDisplayMetrics().density;
            this.mPaint.setTextSize((int) (size * scale + 0.5f));
        }
    }
}
