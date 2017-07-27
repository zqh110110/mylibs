package com.kfd.activityfour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.ViewPagerAdapter;
import com.kfd.bean.StockBean;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.sinastock.SinaStockClient;
import com.kfd.sinastock.SinaStockInfo;
import com.kfd.sinastock.SinaStockInfo.ParseStockInfoException;

/**
 * 股票行情详情类
 * 
 * @author 朱继洋 QQ7617812 2013-5-22
 */


public class StockDetailActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener {
	HomeActivityGroup parentActivity1;
	private ViewPager viewPager;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;// 页面list
	// 底部小店图片
	private Button[] dots;
	// 记录当前选中位置
	private int currentIndex;
	// 显示页面个数
	private int pagesize = 4;
	// 传递过来的
	private ArrayList<StockBean> arrayList;
	private int postion;
	private StockBean stockBean;

	private View view1, view2, view3, view4, view5;
	private ProgressBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stockdetail);
		views = new ArrayList<View>();
		LogUtils.v("test", "进入");
		FlurryAgent.onPageView();
		parentActivity1 = (HomeActivityGroup) getParent();
		if (getIntent().getStringExtra("code") != null) {
			stockBean = new StockBean();
			stockBean.setStockcode(getIntent().getStringExtra("code"));
			// 调用新浪股票的数据判断股票是否正确的
		}

		if (getIntent().getSerializableExtra("stockbean") != null) {
			stockBean = (StockBean) getIntent().getSerializableExtra(
					"stockbean");
		}
		if (getIntent().getSerializableExtra("list") != null) {
			arrayList = (ArrayList<StockBean>) getIntent()
					.getSerializableExtra("list");
			LogUtils.v("test", "接收sizse  " + arrayList.size());
		}
		if (getIntent().getIntExtra("postion", 0) > -1) {
			postion = getIntent().getIntExtra("postion", 0);
		}

		progressBar = (ProgressBar) findViewById(R.id.titleProgress);

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// 初始化每个分页面
		LayoutInflater inflater = getLayoutInflater();
		//view1=inflater.inflate(R.layout.stockdetail, null);
		view2 = inflater.inflate(R.layout.stockdetaillayout, null);
		view3 = inflater.inflate(R.layout.stockdetaillayout6, null);
		view4 = inflater.inflate(R.layout.stockdetaillayout7, null);
		view5 = inflater.inflate(R.layout.stockdetaillayout8, null);
		// views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		views.add(view5);
		viewPager.setCurrentItem(0);
		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(StockDetailActivity.this, views,
				stockBean, progressBar);
		vpAdapter.setFlage();
		viewPager.setAdapter(vpAdapter);

		// 绑定回调
		viewPager.setOnPageChangeListener(this);
		
		
		initData();
		
		// 初始化底部小点
		initDots();
		initTitle();
		initBottom();
	}

	private void initBottom() {
		Button button1, button2, button3, button4, button5;
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);

		button1.setText("买入");
		button2.setText("关注");

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

	private TextView nameTextView, codeTextView;
	private ImageView leftbtn, rightbtn;
	private ImageView backButton;

	private void initTitle() {
		nameTextView = (TextView) findViewById(R.id.titleText01);
		codeTextView = (TextView) findViewById(R.id.titleText02);
		nameTextView.setText(stockBean.getStockname());
		codeTextView.setText(stockBean.getStockcode());
		backButton = (ImageView) findViewById(R.id.back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
		});
		
		//leftbtn = (ImageView) findViewById(R.id.leftBtn);
		//rightbtn = (ImageView) findViewById(R.id.rightBtn);
		// 点击事件是点击左右的跳转到上一个,点击右边的跳转到下一个
		//leftbtn.setOnClickListener(this);
		//rightbtn.setOnClickListener(this);
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new Button[pagesize];

		// 循环取得小点图片
		for (int i = 0; i < pagesize; i++) {
			dots[i] = (Button) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
		}
		currentIndex = 0;
		dots[currentIndex].setBackgroundResource(R.drawable.leftselect);// 设置为白色，即选中状态
	}

	private ProgressDialog pd;

	public void showDialog(String message) {
		pd = ProgressDialog.show(StockDetailActivity.this, "", message, false);
		pd.show();
	}

	public void dismissDialog() {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
	}

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {

		viewPager.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pagesize - 1 || currentIndex == positon) {
			return;
		}

		dots[positon].setBackgroundResource(R.drawable.menu_select_right);
		dots[currentIndex].setBackgroundResource(R.drawable.menu1);
		if (positon==1||positon==2) {
			dots[positon].setBackgroundResource(R.drawable.menu_middle);
		}	
		if (positon==3) {
			dots[positon].setBackgroundResource(R.drawable.menu_select_left);
		}
		if (currentIndex==1) {
			dots[currentIndex].setBackgroundResource(R.drawable.menu2);
		}
		if (currentIndex==2) {
			dots[currentIndex].setBackgroundResource(R.drawable.menu3);
		}
		
		if (currentIndex==3) {
			dots[currentIndex].setBackgroundResource(R.drawable.menu4);
		}
		currentIndex = positon;
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	// 当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurDot(arg0);
		initData();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.button1:
			parentActivity1.startChildActivity("PlaceAnOrderActivity", new Intent(
					StockDetailActivity.this, PlaceAnOrderActivity.class)
					.putExtra("bean", stockBean)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			
			// 买入
			break;
		case R.id.button2:
			addStockToSelfBase(stockBean);// 添加关注
			break;
		case R.id.button3:// 刷新
			// 初始化Adapter

			/*vpAdapter = new ViewPagerAdapter(StockDetailActivity.this, views,view,
					stockBean, progressBar);
			vpAdapter.refreshViewPager();
			// vpAdapter.restoreState(arg0, arg1)
			vpAdapter.setFlage();
			viewPager.setAdapter(vpAdapter);

			// 绑定回调
			viewPager.setOnPageChangeListener(this);*/
			break;
		case R.id.radio0:
			setCurDot(0);
			initData();
			LogUtils.v("ssssss", "radio0");
			viewPager.setCurrentItem(0);
			break;
		case R.id.radio1:
			setCurDot(1);
			initData();
			viewPager.setCurrentItem(1);
			break;
		case R.id.radio2:
			setCurDot(2);
			initData();
			viewPager.setCurrentItem(2);
			break;
		case R.id.radio3:
			setCurDot(3);
			initData();
			viewPager.setCurrentItem(3);
			break;
		case R.id.button5:
			finish();
			break;
		default:
			break;
		}
	}
	
	private SinaStockClient sinaStockClient;
	
	private void initData() {
		// 初始化Handler
		sinaStockClient = SinaStockClient.getInstance();
		getInfo(currentIndex);
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
						message.what = 1;
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
	
	private Handler updateUIHander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// dismissDialog();
			progressBar.setVisibility(View.GONE);
			switch (msg.what) {
			case 0:
				// 对应的position位置的图片显示为没有找到的

				break;
			case 1:
				int position = msg.arg1;
				SinaStockInfo sinaStockInfo = (SinaStockInfo) msg.obj;
				// 更具position不同分别取更新UI
				LogUtils.v("test", sinaStockInfo.toString());
				if (sinaStockInfo != null) {
					TextView recentprice =(TextView) findViewById(R.id.recentprice);
					TextView updownamount =(TextView) findViewById(R.id.updownamount);
					TextView textView16 =(TextView) findViewById(R.id.textView16);
					TextView textView14 =(TextView) findViewById(R.id.textView14);
					TextView TotalMoney =(TextView) findViewById(R.id.TotalMoney);
					TextView textView18 =(TextView) findViewById(R.id.textView18);
					TextView textView19 =(TextView) findViewById(R.id.textView19);
					
					
					recentprice.setText(String.valueOf(sinaStockInfo
							.getmNowPrice()));
					updownamount.setText(String.valueOf(stockBean
							.getUpdownrange())+"  "+String.valueOf(stockBean.getUpdownamount()));
					textView14.setText(String.valueOf(sinaStockInfo
							.getmTradeCount()));
					TotalMoney.setText(String.valueOf(sinaStockInfo
							.getmTradeMoney()));
					textView16.setText(String.valueOf(sinaStockInfo
							.getTodayPrice()));
					textView18.setText(String.valueOf(sinaStockInfo
							.getmHighestPrice()));
					textView19.setText(String.valueOf(sinaStockInfo
							.getLowestPrice()));

				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		//arrayList.clear();
		views.clear();
	}
}
