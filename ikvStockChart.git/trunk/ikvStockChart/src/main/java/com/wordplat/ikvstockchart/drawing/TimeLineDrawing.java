/*
 * Copyright (C) 2017 WordPlat Open Source Project
 *
 *      https://wordplat.com/InteractiveKLineView/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wordplat.ikvstockchart.drawing;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.wordplat.ikvstockchart.entry.SizeColor;
import com.wordplat.ikvstockchart.render.AbstractRender;

/**
 * <p>TimeLineDrawing</p>
 * <p>Date: 2017/3/9</p>
 *
 * @author afon
 */

public class TimeLineDrawing implements IDrawing {

    private Paint linePaint;

    private final RectF timeChartRect = new RectF(); // 分时图显示区域
    private AbstractRender render;

    private float[] lineBuffer = new float[4];
    private float[] pointBuffer = new float[2];
    private Paint rectPaint;

    @Override
    public void onInit(RectF contentRect, AbstractRender render) {
        this.render = render;
        final SizeColor sizeColor = render.getSizeColor();

        if (linePaint == null) {
            linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            linePaint.setStyle(Paint.Style.FILL);
        }
        linePaint.setStrokeWidth(sizeColor.getTimeLineSize());
        linePaint.setColor(sizeColor.getTimeLineColor());

        if (rectPaint == null) {
            rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            rectPaint.setStyle(Paint.Style.FILL);
            Shader mShader = new LinearGradient(contentRect.left,contentRect.top,contentRect.left,contentRect.bottom,new int[] {sizeColor.getTimeLineColor(), 0x00ffffff},null, Shader.TileMode.REPEAT);
            rectPaint.setStrokeWidth(1);
            rectPaint.setShader(mShader);
        }

        timeChartRect.set(contentRect);
    }

    @Override
    public void computePoint(int minIndex, int maxIndex, int currentIndex) {
        final int count = (maxIndex - minIndex) * 4;

        if (lineBuffer.length < count) {
            lineBuffer = new float[count];
        }

        final EntrySet entrySet = render.getEntrySet();
        final Entry entry = entrySet.getEntryList().get(currentIndex);
        final int i = currentIndex - minIndex;

        if (currentIndex < maxIndex) {
            lineBuffer[i * 4 + 0] = currentIndex;
            lineBuffer[i * 4 + 1] = entry.getClose();
            lineBuffer[i * 4 + 2] = currentIndex + 1;
            lineBuffer[i * 4 + 3] = entrySet.getEntryList().get(currentIndex + 1).getClose();
        }
    }

    @Override
    public void onComputeOver(Canvas canvas, int minIndex, int maxIndex, float minY, float maxY) {
        canvas.save();
        canvas.clipRect(timeChartRect);

        render.mapPoints(lineBuffer);

        final int count = (maxIndex - minIndex) * 4;

        if (count > 0) {
            canvas.drawLines(lineBuffer, 0, count, linePaint);
            Path path = new Path();
            for (int i = 0; i < count/4; i++) {
                if (i==0) {
                    path.moveTo(lineBuffer[i*4], timeChartRect.bottom);
                    path.lineTo(lineBuffer[i*4],lineBuffer[i*4+1]);
                } else {
                    path.lineTo(lineBuffer[i*4],lineBuffer[i*4+1]);
                    if (i==count/4-1) {
                        path.lineTo(lineBuffer[i*4+2],lineBuffer[i*4+3]);
                        path.lineTo(lineBuffer[i*4+2], timeChartRect.bottom);
                    }
                }
            }
            canvas.drawPath(path,rectPaint);
        }

        // 计算高亮坐标
        if (render.isHighlight()) {
            final EntrySet entrySet = render.getEntrySet();
            final int lastEntryIndex = entrySet.getEntryList().size() - 2;
            final float[] highlightPoint = render.getHighlightPoint();
            pointBuffer[0] = highlightPoint[0];
            render.invertMapPoints(pointBuffer);
            final int highlightIndex = pointBuffer[0] < 0 ? 0 : (int) pointBuffer[0];
            final int i = highlightIndex - minIndex;
            highlightPoint[0] = highlightIndex < lastEntryIndex ?
                    lineBuffer[i * 4 + 0] : lineBuffer[lastEntryIndex * 4 + 2];
            highlightPoint[1] = highlightIndex < lastEntryIndex ?
                    lineBuffer[i * 4 + 1] : lineBuffer[lastEntryIndex * 4 + 3];
        }

        canvas.restore();
    }

    @Override
    public void onDrawOver(Canvas canvas) {

    }
}
