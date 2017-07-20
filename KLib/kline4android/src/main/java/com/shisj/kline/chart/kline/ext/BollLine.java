package com.shisj.kline.chart.kline.ext;

import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.SubPainter;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.shape.PolyLine;
import com.shisj.kline.util.IRecord;
import com.shisj.kline.util.KPosition;

import android.graphics.Canvas;

/**
 * 移动平均线和布林带 默认的绘制器中的shape使用了30以下
 * 
 * @author shishengjie
 * 
 */
public class BollLine extends SubPainter implements IRecord {

	public final static String TYPE = "BOLL";
	int count = 0;
	PolyLine bollLine, bollLineUp, bollLineDown;// 布林带
	KPosition bollPoints[], upPoints[], downPoints[];
	MVBollUtil util = null;
	float drift = -1f;
	KLine kLine = null;

	public BollLine(AbstractChart chart) {
		super(chart);
		this.kLine = (KLine) chart;
	}

	@Override
	public void onPaint() {
		// 待优化，某些情况下不需要属性
		MVResult bollLast = null;
		if (util == null) {
			util = new MVBollUtil(kLine);
			util.setBoll(true);
		}

		if (bollLine == null) {
			bollLine = new PolyLine();
			bollLine.setColor(Theme.getColor("MV_BOLL_LINE_BG"));
			bollLine.setWidth(2f);
			bollLine.setZlevel(9);
			addShape(bollLine);
		} else {
			bollLine.setVisible(true);
		}
		
		if (bollLineUp == null) {
			bollLineUp = new PolyLine();
			bollLineUp.setColor(Theme.getColor("MV_BOLL_TOP_BG"));
			bollLineUp.setWidth(2f);
			bollLineUp.setZlevel(9);
			addShape(bollLineUp);
		} else {
			bollLineUp.setVisible(true);
		}
		
		if (bollLineDown == null) {
			bollLineDown = new PolyLine();
			bollLineDown.setColor(Theme.getColor("MV_BOLL_DOWN_BG"));
			bollLineDown.setWidth(2f);
			bollLineDown.setZlevel(9);
			bollLineDown.setBottomLimit(kLine.getcHeight());
			addShape(bollLineDown);
		} else {
			bollLineDown.setVisible(true);
			bollLineDown.setBottomLimit(kLine.getcHeight());
		}

		// 当前count
		int cCount = kLine.getCandleCount() + 2;
		if (count != cCount) {
			bollPoints = new KPosition[cCount];
			upPoints = new KPosition[cCount];
			downPoints = new KPosition[cCount];
			count = cCount;
		}
		int begin = kLine.getShowBegin();
		int end = kLine.getShowEnd();
		int j = 0;
		for (int candleIndex = begin; candleIndex <= end; candleIndex++, j++) {
			float x = 0, y = 0;

			bollLast = util.getMAverage(bollLast, candleIndex);

			if (upPoints == null) {// 可能是空的
				bollPoints = new KPosition[cCount];
				upPoints = new KPosition[cCount];
				downPoints = new KPosition[cCount];
				count = cCount;
			}
			x = kLine.getXPxWin(candleIndex);
			y = kLine.getYPx(bollLast.getAverage());
			bollPoints[j] = new KPosition(x, y);

			y = kLine.getYPx(bollLast.getTop());
			upPoints[j] = new KPosition(x, y);

			y = kLine.getYPx(bollLast.getDown());
			downPoints[j] = new KPosition(x, y);
		}

		bollPoints[j] = null;
		upPoints[j] = null;
		downPoints[j] = null;

		bollLine.setPoints(bollPoints);
		bollLineUp.setPoints(upPoints);
		bollLineDown.setPoints(downPoints);

	}

	@Override
	public void onDispose() {
		delShape(bollLine);
		delShape(bollLineUp);
		delShape(bollLineDown);
	}

	@Override
	public void onResize(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getConfig() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	public void onTheme() {
		bollLine.setColor(Theme.getColor("MV_BOLL_LINE_BG"));
		bollLineUp.setColor(Theme.getColor("MV_BOLL_TOP_BG"));
		bollLineDown.setColor(Theme.getColor("MV_BOLL_DOWN_BG"));
	}

}
