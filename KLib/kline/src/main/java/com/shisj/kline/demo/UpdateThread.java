package com.shisj.kline.demo;

import com.shisj.kline.chart.kline.CandleData;
import com.shisj.kline.chart.kline.CandleDataProvider;
import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.view.ChartView;

public class UpdateThread implements Runnable {
	ChartView view;
	CandleDataProvider provider;
	public static boolean run = true;
	public static boolean update = true;
	KLine kLine = null;

	public UpdateThread(ChartView view) {
		this.view = view;
//		this.provider = provider;
		this.kLine = (KLine) view.getChart();
	}

	@Override
	public void run() {
		UpdateThread.run = true;
		while (true) {
			CandleData data = new CandleData();
			data.setOpened(2976.69f);
			// data.setClosing(arr.getFloatValue(1)+new
			// Random().nextFloat()*10);
			data.setClosing(2976.69f);
			data.setHighest(2978.69f);
			data.setLowest(2971.29f);
			data.setDate("2016-12-06", "yyyy-MM-dd");

			if (!UpdateThread.run) {
				break;
			} else {
			}
			if (true) {
				String type = view.getChart().getType();
				if (type == KLine.TYPE) {
					kLine.update(data, 0);
					this.kLine = (KLine) view.getChart();
					provider = view.getChart().getQueue()
							.getCandleDataProvider();
					if (provider != null) {
						int success = provider.getLatest(kLine);
						if (success == 1) {
							view.getChart().reLayout();
							view.rePaint();
						} else if (success == -1) {// 重新加载
							CandleDataProvider provider = view.getChart()
									.getQueue().getCandleDataProvider();
							KCandleProvider newProvider = new KCandleProvider(
									provider.getCurrType(),
									provider.getTimeFormate(),
									provider.getDateType());
							view.setCandleDataProvider(newProvider);// 设置数据提供者
							view.getChart().reLayout();
							view.rePaint();
						}
					}
				}

			}

			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}