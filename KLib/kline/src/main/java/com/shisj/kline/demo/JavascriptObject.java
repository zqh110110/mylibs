package com.shisj.kline.demo;

import android.app.Activity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.kline.CandleDataProvider;
import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.view.ChartView;

public class JavascriptObject {
	ChartView view;
	KLine kLine = null;
	AbstractChart chart;
	UpdateThread updateThread = null;
	TransActivity activity = null;

	public JavascriptObject(ChartView view, Activity activity) {
		this.view = view;
		chart = view.getChart();
		if (chart.getType() == KLine.TYPE)
			this.kLine = (KLine) chart;
		this.activity = (TransActivity) activity;
	}

	@JavascriptInterface
	public void setDataType(final String currType, final String type) {
		UpdateThread.update = false;// 此时不需要更新
		Runnable r = new Runnable() {

			@Override
			public void run() {
				CandleDataProvider provider = view.getChart().getQueue()
						.getCandleDataProvider();
				String fmt = null;
				if (provider != null) {
					fmt = provider.getTimeFormate();
				}
				int typeInt = Integer.valueOf(type);
				if (CandleDataProvider.DEFAULT_FORMAT != null) {
					if (CandleDataProvider.DEFAULT_FORMAT.length > typeInt) {
						fmt = CandleDataProvider.DEFAULT_FORMAT[typeInt];
					}
				}
				String currType_new = currType;
				if (currType == null || currType.trim().length() == 0) {// 若currType为空，取原来的
					currType_new = provider.getCurrType();
				}
				KCandleProvider newProvider = new KCandleProvider(currType_new,
						fmt, type);
				view.setCandleDataProvider(newProvider);// 设置数据提供者
				view.getChart().reLayout();
				view.rePaint();
				UpdateThread.update = true;// 好吧，可以更新了
			}
		};
		new Thread(r).start();
	}

	@JavascriptInterface
	public void setDataType(final String type) {
		UpdateThread.update = false;// 此时不需要更新
		Runnable r = new Runnable() {

			@Override
			public void run() {
				CandleDataProvider provider = view.getChart().getQueue()
						.getCandleDataProvider();
				String fmt = provider.getTimeFormate();
				int typeInt = Integer.valueOf(type);
				if (CandleDataProvider.DEFAULT_FORMAT != null) {
					if (CandleDataProvider.DEFAULT_FORMAT.length > typeInt) {
						fmt = CandleDataProvider.DEFAULT_FORMAT[typeInt];
					}
				}
				KCandleProvider newProvider = new KCandleProvider(
						provider.getCurrType(), fmt, type);
				view.setCandleDataProvider(newProvider);// 设置数据提供者
				view.getChart().reLayout();
				view.rePaint();
				UpdateThread.update = true;// 好吧，可以更新了
			}
		};
		new Thread(r).start();
	}

	@JavascriptInterface
	public void changeChart(String type) {
		String typeCur = view.getChart().getType();
		if (typeCur.equals(type))
			return;
		if (type.equals(KLine.TYPE)) {
			view.setRotateAllow(true);
			view.initKLine();
			chart = view.getChart();
			this.kLine = (KLine) chart;
			kLine.getKMain().setInfo("USD/DKDK 3232.323.2323 32.32.32");
			kLine.getKMain().getPriceText().setVisible(true);
			// 启动线程
			if (updateThread == null) {
				updateThread = new UpdateThread(view);
				Thread thread = new Thread(updateThread);
				thread.start();
			}
			UpdateThread.update = true;// 需要更新
		}

		view.rePaint();
	}

	@JavascriptInterface
	public void beginUpdate() {
		// chart=view.getChart();
		// if(chart.getType()==KLine.TYPE){
		// this.kLine=(KLine) chart;
		// }else{
		// return;
		// }
		// // 启动线程
		 UpdateThread upd = new UpdateThread(view);
		 Thread thread = new Thread(upd);
		 thread.start();
	}

	@JavascriptInterface
	public void endUpdate() {
		UpdateThread.run = false;
	}

	@JavascriptInterface
	public void setDefaultTimeFormat(String fmts) {
		if (fmts == null || fmts.trim().length() == 0)
			return;
		CandleDataProvider.DEFAULT_FORMAT = fmts.split("\\|");
	}

	@JavascriptInterface
	public void setPriceDigit(int num) {
		if (num <= 0)
			return;
		CandleDataProvider.priceDigit = num;
	}

	@JavascriptInterface
	public void setPrecentDigit(int num) {
		if (num <= 0)
			return;
		CandleDataProvider.precentDigit = num;
	}

	@JavascriptInterface
	public void showchart(final float bottom, final float height) {
		// UI主线程中更新view的展现
		view.post(new Runnable() {
			@Override
			public void run() {
				FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view
						.getLayoutParams();
				lp.bottomMargin = (int) Theme.dip2px(bottom);
				if (height > 0) {
					lp.height = (int) Theme.dip2px(height);
					view.setLayoutHeight(lp.height);
				}
				view.setLayoutParams(lp);
				view.setLayoutBottom(lp.bottomMargin);
				view.setVisibility(View.VISIBLE);
				activity.startListener();
			}
		});

	}

	@JavascriptInterface
	public void hidechart() {
		// UI主线程中更新view的展现
		view.post(new Runnable() {
			@Override
			public void run() {
				view.setVisibility(View.INVISIBLE);
				view.dispose();
				UpdateThread.run = false;
				activity.stopListener();
			}
		});
	}
}
