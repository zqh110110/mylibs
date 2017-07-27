package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 存取中心
 * 
 *
 */
public class AccessCenterActivity extends BaseActivity implements OnClickListener {
	private ViewPager pager ;
	private TabPageIndicator indicator ;
	HomeActivityGroup parentActivity1;
	TextView textView ;
	private String faccount_take,saccount_take;
	private String [] text = new String[]{"A股资金","期货资金"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountfund);
		parentActivity1 = (HomeActivityGroup) getParent();
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
		titleTextView.setText("存取中心");
		
		 backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity1.goBack();
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
				container.removeView((View)object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				container.addView(array3.get(position));
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
	private TextView tx1,tx2,tx3,tx4;
	private Button getmoney1,getmoney2;
	
	private void initlistview(){
		layout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.accesscenterlayout, null);
		tx1 = (TextView) layout.findViewById(R.id.balance);
		tx2 = (TextView) layout.findViewById(R.id.max);
		getmoney1 = (Button) layout.findViewById(R.id.button1);
		
		getmoney1.setOnClickListener(this);
		
		layout2 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.accesscenterlayout_s, null);
		
		tx3 = (TextView) layout2.findViewById(R.id.balance);
		tx4 = (TextView) layout2.findViewById(R.id.max);
		getmoney2 = (Button) layout2.findViewById(R.id.button2);
		getmoney2.setOnClickListener(this);
		
		array3=new ArrayList<View>();
		array3.add(layout);
		array3.add(layout2);
		
	}
	
	
	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	/**
	 * 资金数据
	 */
	
	private void getData() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "accountTotalfunds");
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					try {
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");

						TotalfundsBean bean = TotalfundsBean.parseData(result);
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
	 * ui数据匹配
	 */
	private Handler updateUIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String string1 = (String) msg.obj;
				Toast.makeText(getApplicationContext(), string1,
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				TotalfundsBean totalfundsbean = (TotalfundsBean) msg.obj;
				totalfundsbean.toString();
				tx1.setText(totalfundsbean.getSaccount_money());
				tx2.setText(totalfundsbean.getSaccount_reflect());
				
				tx3.setText(totalfundsbean.getFaccount_money());
				tx4.setText(totalfundsbean.getFaccount_reflect());
				
				faccount_take = totalfundsbean.getFaccount_take();
				saccount_take = totalfundsbean.getSaccount_take();
				break;
				
			case 2:
				String string = (String) msg.obj;
				try {
					JSONObject jsonObject = JSONObject.parseObject(string);
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			if (saccount_take!=null) {
				if (saccount_take.toString().equals("0")) {
					showToast("账户不可提现！");
					return;
				}
			}
			
			// 点击跳转到出款页面   首先判断当天的取款次数 
			parentActivity1.startChildActivity("DrawMoneyActivity", new Intent(
					AccessCenterActivity.this, DrawMoneyActivity.class)
					.putExtra("type", "1")
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			break;
		case R.id.button2:
			if (faccount_take!=null) {
				if (faccount_take.toString().equals("0")) {
					showToast("账户不可提现！");
					return;
				}
			}
			
			// 点击跳转到出款页面   首先判断当天的取款次数 
			parentActivity1.startChildActivity("DrawMoneyActivity", new Intent(
					AccessCenterActivity.this, DrawMoneyActivity.class)
					.putExtra("type", "2")
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			
			break;
		default:
			break;
		}
	}

}
