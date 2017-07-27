package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.AccountAdapter;
import com.kfd.adapter.AccountFundAdapter;
import com.kfd.adapter.TabPageIndicator;
import com.kfd.api.HttpRequest;
import com.kfd.bean.CustomInfoBean;
import com.kfd.bean.FundCenterBean;
import com.kfd.bean.TotalfundsBean;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author 账户资金
 *
 */
public class AccountFundActivity extends BaseActivity {
	private ViewPager pager ;
	private TabPageIndicator indicator ;
	TextView textView ;
	private String [] text = new String[]{"A股资金","期货资金"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountfund);
		initTitleButton();
		initTitle();
		initlistview();
		freshButton.setVisibility(View.GONE);
		getData();
		initUI();
		FlurryAgent.onPageView();
	}
	
	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		 backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("账户资金");
		
		 backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		 
	}
	
	private void initUI(){
		pager = (ViewPager) findViewById(R.id.vpager);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		pager.setAdapter(new PagerAdapter() {
		
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1 ;
			}
			
			@Override
			public int getCount() {
				return text.length;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView((View) object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				container.addView(array3.get(position),params );
				return array3.get(position);
			}
			
			@Override
			public CharSequence getPageTitle(int position) {
				return  text[position];
			}
		});
		
		indicator.setViewPager(pager);
		
	}
	
	private ArrayList<String> array2;
	private ArrayList<View> array3;
	private LinearLayout layout,layout2;
	private TextView tx1,tx2,tx3,tx4,tx5;
	private TextView tx6,tx7,tx8,tx9,tx10;
	
	
	private void initlistview(){
		layout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.accountfundlayout, null);
		tx1 = (TextView) layout.findViewById(R.id.Afund);
		tx2 = (TextView) layout.findViewById(R.id.balance);
		tx3 = (TextView) layout.findViewById(R.id.occupation);
		tx4 = (TextView) layout.findViewById(R.id.canuse);
		tx5 = (TextView) layout.findViewById(R.id.max);
		
		layout2 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.accountfundlayout2, null);
		
		tx6 = (TextView) layout2.findViewById(R.id.furfund);
		tx7 = (TextView) layout2.findViewById(R.id.balance);
		tx8 = (TextView) layout2.findViewById(R.id.occupation);
		tx9 = (TextView) layout2.findViewById(R.id.canuse);
		tx10 = (TextView) layout2.findViewById(R.id.max);
		
		array3=new ArrayList<View>();
		array3.add(layout);
		array3.add(layout2);
		
	}
	
	
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	/**
	 * A股资金数据
	 */
	
	private void getData() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "accountStocks");
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					try {
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");

						FundCenterBean bean = FundCenterBean.parseData(result);
						if (bean != null) {
							Message message = new Message();
							message.what = 1;
							message.obj = bean;
							updateUIHandler.sendMessage(message);
						} else {
							Message message = new Message();
							JSONObject jsonObject = JSONObject.parseObject(result);
							message.what = 0;
							message.obj = jsonObject.getString("message");
							updateUIHandler.sendMessage(message);
						}
						LogUtils.log("test", "返回数据" + result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			//}
		});
	}
	/**
	 * 期货资金数据
	 */
	private void getData2() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "accountFutures");
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					try {
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");

						FundCenterBean bean = FundCenterBean.parseData2(result);
						if (bean != null) {
							Message message = new Message();
							message.what = 2;
							message.obj = bean;
							updateUIHandler.sendMessage(message);
						} else {
							Message message = new Message();
							JSONObject jsonObject = JSONObject.parseObject(result);
							message.what = 0;
							message.obj = jsonObject.getString("message");
							updateUIHandler.sendMessage(message);
						}
						LogUtils.log("test", "返回数据" + result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			//}
		});
	}
	
	/**
	 * ui数据匹配
	 */
	private Handler updateUIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String string = (String) msg.obj;
				Toast.makeText(getApplicationContext(), string,
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				FundCenterBean bean = (FundCenterBean) msg.obj;
				bean.toString();
				tx1.setText(bean.getAccountfund());
				tx2.setText(bean.getNetfund());
				tx3.setText(bean.getOccupyFund());
				tx4.setText(bean.getAvailableFund());
				tx5.setText(bean.getTotalBareProfitloss());
				getData2();
				break;
				
			case 2:
				FundCenterBean furbean = (FundCenterBean) msg.obj;
				furbean.toString();
				tx6.setText(furbean.getAccountfund());
				tx7.setText(furbean.getNetfund());
				tx8.setText(furbean.getOccupyFund());
				tx9.setText(furbean.getAvailableFund());
				tx10.setText(furbean.getTotalBareProfitloss());
				break;
				
			case 3:
				try {
					String string1 = (String) msg.obj;
					JSONObject jsonObject = JSONObject.parseObject(string1);
					String string3 = jsonObject.getString("message");
					String string4 = jsonObject.getString("status");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};

	

}
