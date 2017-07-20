package com.shisj.kline.demo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.shisj.kline.R;
import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.chart.kline.CandleQueue;
import com.shisj.kline.chart.kline.KLine;
import com.shisj.kline.chart.kline.KManager;
import com.shisj.kline.chart.kline.ext.CrossLinePainter;
import com.shisj.kline.util.ITap;
import com.shisj.kline.view.ChartView;
import com.shisj.kline.view.ChartView.InitCallback;

public class TransActivity extends Activity {
	private OrientationEventListener mOrientationListener; // 屏幕方向改变监听器
	private ChartView chartView;
	KManager manager;
	WebView webView;
	Configuration rotateCfg = new Configuration();
	int direction = 0;
	private static final String[] strs = new String[] { "1分钟K", "5分钟K",
			"30分钟K", "2小时K" };
	private static final String[] strs1 = new String[] { "十字光标", "ma", "boll",
			"macd" };
	private ListView listView, assistListView;
	boolean showListView = false;
	int array[] = new int[7];
	String rotateRecord = null, savedRecord = null;
	JavascriptObject javascript;
	boolean rotating = false;// 旋转中
	RotateRecord landscape = null, portrait = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_trans);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		chartView = (ChartView) findViewById(R.id.kchart);
		listView = (ListView) findViewById(R.id.list);
		assistListView = (ListView) findViewById(R.id.assistListView);

		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.listview,
				strs));

		webView = (WebView) findViewById(R.id.webView);

		// 启用支持javascript
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		// 获取kchart的view
		// 此时的kview还未resize过
		DisplayMetrics dm2 = getResources().getDisplayMetrics();
		Theme.setDensity(dm2.density);
		chartView = (ChartView) findViewById(R.id.kchart);
		ChartView.kAxisWidth = 120f;
		ChartView.kAxisHeight = 30f;
		ChartView.tAxisWidth = 50f;
		ChartView.tAxisHeight = 30f;
		AbstractChart chart = chartView.getChart();
		if (chart.getType() == KLine.TYPE) {
			KLine kLine = (KLine) chart;
			kLine.setStep(7);// 设置step
			kLine.setAxis(120, 30);
			// 创建附加子绘制器的管理器
			manager = new KManager(chartView);
			manager.register();
		} else {

		}
		chartView.setInitCallback(new InitCallback() {
			@Override
			public void afterInit(AbstractChart chart) {
				if (chart.getType() == KLine.TYPE) {
					manager = new KManager(chartView);
					manager.register();
				}
			}
		});

		// 添加js支持
		javascript = new JavascriptObject(chartView, this);
		webView.addJavascriptInterface(javascript, "kline");
		webView.loadUrl("file:///android_asset/WHBTrs2.html");

		Configuration cfg = getResources().getConfiguration();
		initConfig(cfg);

		assistListView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.listview, strs1));

		chartView.setTapListener(new ITap() {

			@Override
			public boolean tap(MotionEvent event) {
				String type = chartView.getChart().getType();
				if (!type.equals(KLine.TYPE))
					return true;
				int ori = getResources().getConfiguration().orientation;
				if (ori == Configuration.ORIENTATION_PORTRAIT)
					return true;

				int size = manager.lineSize(CrossLinePainter.TYPE);
				if (size > 0) {// 有十字线，可以穿透
					return true;
				}
				showListView = !showListView;
				listView.setVisibility(showListView ? View.VISIBLE
						: View.INVISIBLE);
				assistListView.setVisibility(showListView ? View.VISIBLE
						: View.INVISIBLE);
				return false;// 不执行后续事件
			}
		});

		listView.setCacheColorHint(0);
		assistListView.setCacheColorHint(0);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position > -1) {
					javascript.setDataType(String.valueOf(position + 1));
				}

				listView.setVisibility(View.INVISIBLE);
				assistListView.setVisibility(View.INVISIBLE);
			}
		});

		assistListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.e("prt", "setOnItemClickListener");

				assistClick(position);
				// if(position>-1&&position<3){
				// javascript.setDataType(String.valueOf(position+5));
				// }else if(position==3){
				//
				// }

			}

		});

		//
		savedRecord = "###MACD|END###ASL|16|2976.02002|6|2839.28003|END###MAL|false###";

	}

	private void assistClick(int position) {
		Log.e("prt", "assistClick " + position);
		int flag = array[position];
		if (position == 0) {
			if (flag == 0) {
				manager.addCrossLine();
			} else {
				manager.delCrossLine();
			}

		} else if (position == 1) {
			if (flag == 0) {
				manager.addMALine();
			} else {
				manager.delMALine();
			}
		} else if (position == 2) {
			if (flag == 0) {
				manager.addBollLine();
			} else {
				manager.delBollLine();
			}
		} else if (position == 3) {
			if (flag == 0) {
				manager.addMACDWindow();
			} else {
				manager.delMACDWindow();
			}
		} else if (position == 4) {
			manager.addAssistLine();
		} else if (position == 5) {
			manager.addFibonacciLine();
		} else {
			manager.delAssistLine();
			manager.delFibonacciLine();
		}
		if (array[position] == 0) {
			array[position] = 1;
		} else {
			array[position] = 0;
		}

		listView.setVisibility(View.INVISIBLE);
		assistListView.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onDestroy() {
		chartView.dispose();
		UpdateThread.run = false;
		super.onDestroy();
	}

	public void listView(boolean show) {
		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) listView
				.getLayoutParams();
		lp.height = getWindowManager().getDefaultDisplay().getHeight();
		listView.setLayoutParams(lp);
		listView.setVisibility(View.INVISIBLE);
	}

	public void imageView(boolean show) {
		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) assistListView
				.getLayoutParams();
		// lp.width=getWindowManager().getDefaultDisplay().getWidth();
		lp.height = getWindowManager().getDefaultDisplay().getHeight();
		assistListView.setLayoutParams(lp);
		assistListView.setVisibility(View.INVISIBLE);
	}

	public void initConfig(Configuration newConfig) {
		UpdateThread.run = false;
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {// 水平
			webView.setVisibility(View.GONE);
			listView(true);
			imageView(true);
			if (chartView.getChart().getType() == KLine.TYPE) {
				if (savedRecord != null) {// 如读取的配置不为空，
					Log.e("prt2", "LANDSCAPE: parse savedRecord" + savedRecord);
					chartView.parse(savedRecord);
					savedRecord = null;
				} else {
					Log.e("prt2", "LANDSCAPE: parse rotateRecord"
							+ rotateRecord);
					chartView.parse(rotateRecord);
				}
			}

			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) chartView
					.getLayoutParams();
			lp.bottomMargin = 0;
			lp.topMargin = getStatusHeight();
			lp.height = getWindowManager().getDefaultDisplay().getHeight()
					- getStatusHeight();
			chartView.setLayoutParams(lp);
			Theme.black();
			chartView.changeTheme();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {// 垂直
			webView.setVisibility(View.VISIBLE);
			listView(false);
			imageView(false);
			if (chartView.getChart().getType() == KLine.TYPE) {
				rotateRecord = chartView.getRecord();
				Log.e("prt2", "PORTRAIT: parseDelete rotateRecord"
						+ rotateRecord);
				chartView.parseDelete(rotateRecord);
			}

			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) chartView
					.getLayoutParams();
			lp.topMargin = 0;
			lp.bottomMargin = chartView.getLayoutBottom();
			lp.height = chartView.getLayoutHeight();
			chartView.setLayoutParams(lp);

			Theme.white();
			chartView.changeTheme();
		}

		if (chartView.getChart().getType() == KLine.TYPE) {
			// 下次重新计算text大小
			((KLine) chartView.getChart()).resetAxisXTSize();
		}
		UpdateThread.run = true;
	}

	private int getStatusHeight() {
		Rect rect = new Rect();
		Window win = getWindow();
		win.getDecorView().getWindowVisibleDisplayFrame(rect);
		return rect.top;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.e("prt2", "onConfigurationChanged=");
		rotating = false;
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onStop() {
		UpdateThread.update = false;
		super.onStop();
	}

	@Override
	protected void onRestart() {
		UpdateThread.update = true;
		super.onRestart();
	}

	/**
	 * 开启监听器
	 */
	public void startListener() {

		if (mOrientationListener != null) {
			mOrientationListener.enable();
			return;
		}
		mOrientationListener = new OrientationEventListener(this) {
			@Override
			public void onOrientationChanged(int rotation) {

				if (rotation == OrientationEventListener.ORIENTATION_UNKNOWN)
					return;
				if (rotating)
					return;
				// Log.e("prt2","rotation="+rotation);

				// 设置竖屏
				if (((rotation >= 340) || (rotation < 20))) {
					if (direction != 1) {
						// savePeroid(direction);
						rotating = true;
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
						rotateCfg.orientation = Configuration.ORIENTATION_PORTRAIT;
						initConfig(rotateCfg);
						direction = 1;
						webView.loadUrl("javascript:androidKChartRotate(1)");
						// changePeriod(portrait);
					}
				} else if (((rotation >= 70) && (rotation < 110))) {
					if (direction != 2) {
						// savePeroid(direction);
						rotating = true;
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
						rotateCfg.orientation = Configuration.ORIENTATION_LANDSCAPE;
						initConfig(rotateCfg);
						direction = 2;
						webView.loadUrl("javascript:androidKChartRotate(2)");
						// changePeriod(landscape);
					}
				} else if (((rotation >= 160) && (rotation < 200))) {
					// if(direction!=3){
					// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
					// rotateCfg.orientation=Configuration.ORIENTATION_PORTRAIT;
					// initConfig(rotateCfg);
					// direction=3;
					// }
				} else if (((rotation >= 250) && (rotation <= 290))) {
					if (direction != 4) {
						// savePeroid(direction);
						rotating = true;
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
						rotateCfg.orientation = Configuration.ORIENTATION_LANDSCAPE;
						initConfig(rotateCfg);
						direction = 4;
						webView.loadUrl("javascript:androidKChartRotate(4)");
						// changePeriod(landscape);
					}
				}
				rotating = false;

			}
		};
		mOrientationListener.enable();
	}

	public void stopListener() {
		if (mOrientationListener != null)
			mOrientationListener.disable();
	}

	/**
	 * 0-初始化 1-ORIENTATION_PORTRAIT 2-SCREEN_ORIENTATION_REVERSE_LANDSCAPE
	 * 4-ORIENTATION_LANDSCAPE
	 * 
	 * @param prev
	 * @param direction
	 */
	public void savePeroid(int prev) {
		if (prev == 1) {// 垂直
			portrait = new RotateRecord();
			portrait.setQueue(chartView.getChart().getQueue());
			portrait.setDrift(chartView.getChart().getDrift());
		} else if (prev == 2 || prev == 4) {// 水平
			// 垂直
			landscape = new RotateRecord();
			landscape.setQueue(chartView.getChart().getQueue());
			landscape.setDrift(chartView.getChart().getDrift());
		}
	}

	public void changePeriod(final RotateRecord info) {
		if (chartView.getChart().getType().equals(KLine.TYPE)) {
			KLine kline = (KLine) chartView.getChart();
			kline.update(kline.getQueue().getCandleData(0), 0);
		}
		if (info == null)
			return;
		UpdateThread.update = false;// 此时不需要更新
		Runnable r = new Runnable() {

			@Override
			public void run() {
				CandleQueue queue = info.getQueue();
				chartView.getChart().setQueue(queue);
				chartView.getChart().move(info.getDrift());
				chartView.getChart().reLayout();
				chartView.rePaint();
				UpdateThread.update = true;// 好吧，可以更新了
			}
		};
		new Thread(r).start();

	}

}

class RotateRecord {
	CandleQueue queue;
	float drift = 0.0f;

	public float getDrift() {
		return drift;
	}

	public void setDrift(float drift) {
		this.drift = drift;
	}

	public CandleQueue getQueue() {
		return queue;
	}

	public void setQueue(CandleQueue queue) {
		this.queue = queue;
	}

}
