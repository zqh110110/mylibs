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

package com.wordplat.ikvstockchart.render;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.wordplat.ikvstockchart.drawing.EmptyDataDrawing;
import com.wordplat.ikvstockchart.drawing.HighlightDrawing;
import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.wordplat.ikvstockchart.drawing.IDrawing;
import com.wordplat.ikvstockchart.drawing.TimeLineDrawing;
import com.wordplat.ikvstockchart.drawing.TimeLineGridAxisDrawing;
import com.wordplat.ikvstockchart.entry.StockIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TimeLineRender 分时图</p>
 * <p>Date: 2017/3/9</p>
 *
 * @author afon
 */

public class TimeLineRender extends AbstractRender {

    private final RectF timeChartRect = new RectF(); // 分时图显示区域

    private final float[] extremumY = new float[2];

    private final List<IDrawing> drawingList = new ArrayList<>();
    private final List<StockIndex> stockIndexList = new ArrayList<>(); // 股票指标列表

    public TimeLineRender() {
        drawingList.add(new TimeLineGridAxisDrawing());
        drawingList.add(new TimeLineDrawing());
        drawingList.add(new EmptyDataDrawing());
        drawingList.add(new HighlightDrawing());
    }

    public void addDrawing(IDrawing drawing) {
        drawingList.add(drawing);
    }

    public void clearDrawing() {
        drawingList.clear();
    }

    public RectF getTimeChartRect() {
        return timeChartRect;
    }

    @Override
    public void setEntrySet(EntrySet entrySet) {
        super.setEntrySet(entrySet);

        postMatrixTouch(timeChartRect.width(), sizeColor.getTimeLineMaxCount());

        entrySet.computeTimeLineMinMax(0, entrySet.getEntryList().size());
        computeExtremumValue(extremumY, entrySet.getMinY(), entrySet.getDeltaY());
        postMatrixValue(timeChartRect.width(), timeChartRect.height(), extremumY[0], extremumY[1]);

        postMatrixOffset(timeChartRect.left, timeChartRect.top);
        scroll(0);
    }

    @Override
    public boolean canScroll(float dx) {
        return false;
    }

    @Override
    public boolean canDragging(float dx) {
        return false;
    }

    @Override
    public void onViewRect(RectF viewRect) {
        final float candleBottom = viewRect.bottom - sizeColor.getXLabelViewHeight();
        final int remainHeight = (int) (candleBottom - viewRect.top);

        int calculateHeight = 0;
        for (StockIndex stockIndex : stockIndexList) {
            if (stockIndex.isEnable()) {
                stockIndex.setEnable(stockIndex.getHeight() > 0
                        && calculateHeight + stockIndex.getHeight() < remainHeight);

                calculateHeight += stockIndex.getHeight();
            }
        }

        timeChartRect.set(viewRect.left, viewRect.top, viewRect.right, candleBottom - calculateHeight);

        initDrawingList(timeChartRect, drawingList);

        calculateHeight = 0;
        for (StockIndex stockIndex : stockIndexList) {
            if (stockIndex.isEnable()) {
                calculateHeight += stockIndex.getHeight();

                float top = timeChartRect.bottom + sizeColor.getXLabelViewHeight() + calculateHeight - stockIndex.getHeight();
                float bottom = timeChartRect.bottom + sizeColor.getXLabelViewHeight() + calculateHeight;

                stockIndex.setRect(
                        viewRect.left + stockIndex.getPaddingLeft(),
                        top + stockIndex.getPaddingTop(),
                        viewRect.right - stockIndex.getPaddingRight(),
                        bottom - stockIndex.getPaddingBottom());

                initDrawingList(stockIndex.getRect(), stockIndex.getDrawingList());
            }
        }
    }

    @Override
    public void zoomIn(float x, float y) {

    }

    @Override
    public void zoomOut(float x, float y) {

    }

    @Override
    public void render(Canvas canvas) {
        int count = entrySet.getEntryList().size();
        for (int i = 0; i < count; i++) {
            Entry entry = entrySet.getEntryList().get(i);
            if (stockIndexList != null) {
                for (StockIndex stockIndex : stockIndexList) {
                    if (stockIndex.isEnable()) {
                        stockIndex.computeMinMax(i, entry);
                    }
                }
            }
        }
        renderDrawingList(canvas, drawingList, entrySet.getMinY(), entrySet.getMaxY());
        for (StockIndex stockIndex : stockIndexList) {
            if (stockIndex.isEnable()) {
                float deltaY = stockIndex.getDeltaY();
                if (deltaY > 0) {
                    computeExtremumValue(extremumY,
                            stockIndex.getMinY(),
                            deltaY,
                            stockIndex.getExtremumYScale(),
                            stockIndex.getExtremumYDelta());
                    postMatrixValue(stockIndex.getMatrix(), stockIndex.getRect(), extremumY[0], extremumY[1]);

                    renderDrawingList(canvas, stockIndex.getDrawingList(), stockIndex.getMinY(), stockIndex.getMaxY());

                } else {
                    postMatrixValue(stockIndex.getMatrix(), stockIndex.getRect(), Float.NaN, Float.NaN);

                    renderDrawingList(canvas, stockIndex.getDrawingList(), Float.NaN, Float.NaN);
                }
            }
        }
    }

    private void initDrawingList(RectF rect, List<IDrawing> drawingList) {
        for (IDrawing drawing : drawingList) {
            drawing.onInit(rect, this);
        }
    }

    private void renderDrawingList(Canvas canvas, List<IDrawing> drawingList, float minY, float maxY) {
        final int count = entrySet.getEntryList().size();
        final int lastIndex = count - 1;

        for (int i = 0 ; i < count ; i++) {
            for (IDrawing drawing : drawingList) {
                drawing.computePoint(0, lastIndex, i);
            }
        }

        for (IDrawing drawing : drawingList) {
            drawing.onComputeOver(canvas, 0, lastIndex, minY, maxY);
        }

        for (IDrawing drawing : drawingList) {
            drawing.onDrawOver(canvas);
        }
    }

    public void addStockIndex(StockIndex stockIndex) {
        stockIndexList.add(stockIndex);
    }

    public void removeStockIndex(StockIndex stockIndex) {
        stockIndexList.remove(stockIndex);
    }

    public void clearStockIndex() {
        stockIndexList.clear();
    }

}
