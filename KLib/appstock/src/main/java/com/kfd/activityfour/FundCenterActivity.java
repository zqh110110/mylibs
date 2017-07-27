package com.kfd.activityfour;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.api.HttpRequest;
import com.kfd.bean.FundCenterBean;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

/**
 * 资金中心
 * 
 * @author 
 */
public class FundCenterActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fundcent);
		initTitle();
		initUI();
		getData();
		FlurryAgent.onPageView();
		initTitleButton();
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		getData();
	}
	

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("资金中心");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private TextView accountfundTextView, netfundTextView, occupyFundTextView,
			availableFundTextView, totalBareProfitlossTextView,
			blasteWarehousePointTextView, blasteScaleTextView;

	private void initUI() {
		accountfundTextView = (TextView) findViewById(R.id.textView2);
		netfundTextView = (TextView) findViewById(R.id.textView4);
		occupyFundTextView = (TextView) findViewById(R.id.textView6);
		availableFundTextView = (TextView) findViewById(R.id.textView8);
		totalBareProfitlossTextView = (TextView) findViewById(R.id.textView10);
		blasteWarehousePointTextView = (TextView) findViewById(R.id.textView12);
		blasteScaleTextView = (TextView) findViewById(R.id.textView14);
	}

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void getData() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("c", "Account");
					hashMap.put("a", "AccountMoney");
					//hashMap.put("uid", userid);
					hashMap.put("mobile", "android");
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
							updateUIHandler.sendEmptyMessage(0);
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

	private Handler updateUIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:

				break;
			case 1:
				FundCenterBean bean = (FundCenterBean) msg.obj;
				accountfundTextView.setText(bean.getAccountfund());
				netfundTextView.setText(bean.getNetfund());
				occupyFundTextView.setText(bean.getOccupyFund());
				availableFundTextView.setText(bean.getAvailableFund());
				totalBareProfitlossTextView.setText(bean
						.getTotalBareProfitloss());
				blasteWarehousePointTextView.setText(bean
						.getBlasteWarehousePoint()+"%");
				blasteScaleTextView.setText(bean.getBlasteScale()+"%");
				break;
			default:
				break;
			}
		};
	};
}
