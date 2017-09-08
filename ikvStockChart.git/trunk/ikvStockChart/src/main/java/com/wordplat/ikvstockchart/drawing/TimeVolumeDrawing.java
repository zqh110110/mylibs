package com.wordplat.ikvstockchart.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wordplat.ikvstockchart.compat.ViewUtils;
import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.wordplat.ikvstockchart.entry.SizeColor;
import com.wordplat.ikvstockchart.render.AbstractRender;

/**
 * <p>KLineVolumeDrawing K线成交量的绘制</p>
 * <p>Date: 2017/6/28</p>
 *
 * @author afon
 */

public class TimeVolumeDrawing implements IDrawing {
    private static final String TAG = "TimeVolumeDrawing";

    private Paint axisPaint; // X 轴和 Y 轴的画笔
    private Paint candlePaint; // 成交量画笔

    private final RectF candleRect = new RectF(); // 绘图区域
    private AbstractRender render;

    private float candleSpace = 0.99f; // entry 与 entry 之间的间隙，默认 0.1f (10%)
    private float[] xRectBuffer = new float[4];
    private float[] candleBuffer = new float[4];

    @Override
    public void onInit(RectF contentRect, AbstractRender render) {
        this.render = render;
        final SizeColor sizeColor = render.getSizeColor();

        if (axisPaint == null) {
            axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            axisPaint.setStyle(Paint.Style.STROKE);
        }
        axisPaint.setStrokeWidth(sizeColor.getAxisSize());
        axisPaint.setColor(sizeColor.getAxisColor());

        if (candlePaint == null) {
            candlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            candlePaint.setStyle(Paint.Style.FILL);
            candlePaint.setStrokeWidth(sizeColor.getCandleBorderSize());
        }

        candleRect.set(contentRect);
    }

    @Override
    public void computePoint(int minIndex, int maxIndex, int currentIndex) {
    }

    @Override
    public void onComputeOver(Canvas canvas, int minIndex, int maxIndex, float minY, float maxY) {
        final EntrySet entrySet = render.getEntrySet();
        final SizeColor sizeColor = render.getSizeColor();

        canvas.save();
        canvas.clipRect(candleRect);

        canvas.drawRect(candleRect, axisPaint);

        for (int i = minIndex; i < maxIndex; i++) {
            // 设置画笔颜色
            Entry entry = ViewUtils.setUpTimePaint(candlePaint, entrySet, i, sizeColor);

            // 计算 成交量的矩形坐标
            xRectBuffer[0] = i + candleSpace;
            xRectBuffer[1] = 0;
            xRectBuffer[2] = i + 1 - candleSpace;
            xRectBuffer[3] = 0;
            render.mapPoints(xRectBuffer);

            candleBuffer[0] = 0;
            candleBuffer[1] = entry.getVolume();
            candleBuffer[2] = 0;
            candleBuffer[3] = minY;

            render.mapPoints(null, candleBuffer);

            if (Math.abs(candleBuffer[1] - candleBuffer[3]) < 1.f) { // 成交量非常小画一条直线
                canvas.drawRect(xRectBuffer[0], candleBuffer[1] - 2, xRectBuffer[2], candleBuffer[1], candlePaint);
            } else {
                canvas.drawRect(xRectBuffer[0], candleBuffer[1], xRectBuffer[2], candleBuffer[3] - axisPaint.getStrokeWidth(), candlePaint);
            }
        }
        canvas.restore();
    }

    @Override
    public void onDrawOver(Canvas canvas) {

    }
}
