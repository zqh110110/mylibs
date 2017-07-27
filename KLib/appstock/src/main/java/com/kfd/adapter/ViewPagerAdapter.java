package com.kfd.adapter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kfd.activityfour.R;
import com.kfd.bean.StockBean;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.sinastock.SinaStockClient;
import com.kfd.sinastock.SinaStockInfo;
import com.kfd.sinastock.SinaStockInfo.ParseStockInfoException;

public class ViewPagerAdapter extends PagerAdapter {

	// 界面列表
	private List<View> views;
	private Context context;
	private StockBean stockBean;
	private ProgressBar progressBar;

	public ViewPagerAdapter(Context context, List<View> views,
			StockBean stockBean, ProgressBar progressBar) {
		this.views = views;
		this.context = context;
		this.progressBar = progressBar;
		this.stockBean = stockBean;
	}

	// 销毁arg1位置的界面
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	// 获得当前界面数

	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}

		return 0;
	}

	// 初始化arg1位置的界面
	@Override
	public Object instantiateItem(View arg0, int arg1) {

		((ViewPager) arg0).addView(views.get(arg1), 0);

		return views.get(arg1);
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}

	/**
	 * 跳转到每个页面都要执行的方法
	 */

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {

		// 把这个position赋值到一个全局变量，通过这个就会知道滑动到哪个页面了
		setPosition(position);
		// 保证每滑动一次都只请求一次联网，执行过后就不在执行getNewsData方法了
		// 把这个position赋值到一个全局变量，通过这个就会知道滑动到哪个页面了
		setPosition(position);
		// 保证每滑动一次都只请求一次联网，执行过后就不在执行getNewsData方法了
		if (positionValue[position] == false) {
			// 这个方法就是联网获取数据
			// showDialog("请稍候....");
			progressBar.setVisibility(View.VISIBLE);
			getData(position);
			positionValue[position] = true;
		}

	}

	private int position;

	private void setPosition(int position) {
		this.position = position;
	}

	private SinaStockClient sinaStockClient;

	private void getData(int position) {
		sinaStockClient = SinaStockClient.getInstance();

		getInfo(position);
		switch (position) {
		case 0://分时

			getImage("", stockBean.getStockcode(), position);
			// getImage(SinaStockClient.IMAGE_TYPE_MINITE,stockBean.getStockcode(),0);

			break;
		case 1:

			getImage(ConstantInfo.DayKeyWord, stockBean.getStockcode(),
					position);

			// getImage(SinaStockClient.IMAGE_TYPE_DAILY,stockBean.getStockcode(),1);

			break;
		case 2:

			getImage(ConstantInfo.WeekKeyWord, stockBean.getStockcode(),
					position);
			// getImage(SinaStockClient.IMAGE_TYPE_MONTHLY,stockBean.getStockcode(),2);

			break;
		case 3:
			getImage(ConstantInfo.MonthKeyWord, stockBean.getStockcode(),
					position);
			// getImage(SinaStockClient.IMAGE_TYPE_MONTHLY,stockBean.getStockcode(),2);
			break;
		default:
			break;
		}
	}

	/**
	 * 获取股票图片
	 */

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void getImage(final String type, final String stockecode,
			final int position) {

		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Bitmap bitmap = null;
				try {

					/*
					 * Bitmap bitmap = sinaStockClient.getStockImage(stockecode,
					 * type);
					 */
					/*URLConnection conn = null;
					// 判断一下子，时分线和其他所使用的接口不同
					if (position == 0) {
						conn = new URL(ConstantInfo.StockHourKLine + stockecode)
								.openConnection();
					} else {
						conn = new URL(ConstantInfo.StockImageUrl + stockecode
								+ type).openConnection();
					}

					conn.connect();
					InputStream is = conn.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is);
					bitmap = BitmapFactory.decodeStream(bis);
					bis.close();
					is.close();*/
					Message message = new Message();
					
					//sina获取股票k线图方法
					if (position==0) {
						bitmap=sinaStockClient.getStockImage(stockecode, SinaStockClient.IMAGE_TYPE_MINITE);
					}else if (position==1) {
						bitmap=sinaStockClient.getStockImage(stockecode, SinaStockClient.IMAGE_TYPE_DAILY);
					}else if (position==2) {
						bitmap=sinaStockClient.getStockImage(stockecode, SinaStockClient.IMAGE_TYPE_WEEKLY);
					}else if (position==3) {
						bitmap=sinaStockClient.getStockImage(stockecode, SinaStockClient.IMAGE_TYPE_MONTHLY);
					}
					
					if (bitmap != null) {
						message.what = 1;
						message.obj = bitmap;
						message.arg1 = position;
					} else {
						message.what = 0;
						message.arg1 = position;
					}

					updateUIHander.sendMessage(message);
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 获取股票信息
	 */
	private void getInfo(final int position) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					List<SinaStockInfo> list = sinaStockClient
							.getStockInfo(new String[] { stockBean
									.getStockcode() });
					if (list.size()>0) {					
						SinaStockInfo sinaStockInfo = list.get(0);
						Message message = new Message();
						message.what = 2;
						message.obj = sinaStockInfo;
						message.arg1 = position;
						updateUIHander.sendMessage(message);
					}
					
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseStockInfoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private ArrayList<Bitmap> arrayList = new ArrayList<Bitmap>();

	public void refreshViewPager() {
		arrayList.clear();

	}

	private Handler updateUIHander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// dismissDialog();
			progressBar.setVisibility(View.GONE);
			switch (msg.what) {
			case 0:
				// 对应的position位置的图片显示为没有找到的

				break;
			case 1:
				Bitmap bitmap = (Bitmap) msg.obj;
				if (bitmap != null) {
					try {
						ImageView imageView = (ImageView) views.get(msg.arg1)
								.findViewById(R.id.imageView1);
						imageView.setImageBitmap(bitmap);
						arrayList.add(bitmap);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				break;
			case 2:
				int position = msg.arg1;
				SinaStockInfo sinaStockInfo = (SinaStockInfo) msg.obj;
				// 更具position不同分别取更新UI
				
				break;
			default:
				break;
			}
		};
	};

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	boolean[] positionValue = null;

	/**
	 * 设置标示
	 * 
	 * @param list
	 */
	public void setFlage() {

		positionValue = new boolean[views.size()];
		for (int i = 0; i < positionValue.length; i++) {
			positionValue[i] = false;

		}

		notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
			return POSITION_NONE;    
	}

}
