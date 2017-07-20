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
public class MALine extends SubPainter implements IRecord {

	public final static String TYPE = "MAL";
	int count = 0;
	PolyLine maLine = null;// 移动平均线
	KPosition maPoints[];
	MVBollUtil util = null;
	KLine kLine = null;

	public MALine(AbstractChart chart) {
		super(chart);
		this.kLine = (KLine) chart;
	}


	@Override
	public void onPaint() {
		// 待优化，某些情况下不需要属性
		MVResult last = null;
		if (util == null) {
			util = new MVBollUtil(kLine);
			util.setBoll(false);// 先计算移动平均线的点
		}
		if (maLine == null) {// 创建折线
			maLine = new PolyLine();
			maLine.setColor(Theme.getColor("MV_LINE_BG"));
			maLine.setWidth(2f);
			maLine.setZlevel(9);
			addShape(maLine);
		}
		// 当前count
		int cCount = kLine.getCandleCount() + 2;
		if (count != cCount) {
			maPoints = new KPosition[cCount];
			count = cCount;
		}
		int begin = kLine.getShowBegin();
		int end = kLine.getShowEnd();
		int j = 0;
		for (int candleIndex = begin; candleIndex <= end; candleIndex++, j++) {
			float x = 0, y = 0;
			last = util.getMAverage(last, candleIndex);
			x = kLine.getXPxWin(candleIndex);
			y = kLine.getYPx(last.getAverage());
			maPoints[j] = new KPosition(x, y);

		}
		maPoints[j] = null;
		maLine.setPoints(maPoints);
	}

	@Override
	public void onDispose() {
		delShape(maLine);
	}

	@Override
	public void onResize(Canvas canvas) {}

	@Override
	public String getConfig() {
		// TODO Auto-generated method stub
		return TYPE;
	}


	@Override
	public void onTheme() {
		maLine.setColor(Theme.getColor("MV_LINE_BG"));
	}

}
