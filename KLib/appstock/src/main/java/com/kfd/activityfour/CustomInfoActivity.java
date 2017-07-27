package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.AccountAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.CustomInfoBean;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

/**
 * 客户信息
 * 
 * 
 */
public class CustomInfoActivity extends BaseActivity {
	HomeActivityGroup parentActivity1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custominfo);
		parentActivity1 = (HomeActivityGroup) getParent();
		initTitleButton();
		initTitle();
		initUI();
		getData();
		FlurryAgent.onPageView();
		ActivityManager.push(this);
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
		backButton.setVisibility(View.VISIBLE);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("个人资料");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				parentActivity1.goBack();
			}
		});
	}

	private TextView userIdTextView, userNameTextView, nameTextView,
			sexTextView, countryTextView, telTextView, emailTextView,checkemail,
			msnTextView, qqTextView,phoneTextView,checkphone,banktext,checkbanktext;

	private void initUI() {
		userIdTextView = (TextView) findViewById(R.id.textView2);
		userNameTextView = (TextView) findViewById(R.id.textView4);
		nameTextView = (TextView) findViewById(R.id.textView6);
		sexTextView = (TextView) findViewById(R.id.textView8);
		countryTextView = (TextView) findViewById(R.id.textView10);
		telTextView = (TextView) findViewById(R.id.textView12);
		emailTextView = (TextView) findViewById(R.id.textView14);
		qqTextView = (TextView) findViewById(R.id.textView18);
		phoneTextView = (TextView) findViewById(R.id.textView12);
		checkphone = (TextView) findViewById(R.id.checkphone);
		banktext = (TextView) findViewById(R.id.banktext);
		checkbanktext = (TextView) findViewById(R.id.checkbanktext);
		checkemail = (TextView) findViewById(R.id.checkemail);
	}

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void getData() {
		try {
			progressDialog = new ProgressDialog(getParent());
			progressDialog.setMessage("加载数据中，请稍候.....");
			
			progressDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "userBasic");
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					String result="";
					try {
						result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
						CustomInfoBean bean = CustomInfoBean.parseData1(result);
						if (bean != null) {
							Message message = new Message();
							message.what = 1;
							message.obj = bean;
							updateUIHandler.sendMessage(message);
						} else {
							Message message = new Message();
							message.what = 0;
							message.obj = result;
							updateUIHandler.sendMessage(message);
						}
						LogUtils.log("test", "返回数据" + result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Message message = new Message();
						message.what = 0;
						message.obj = result;
						updateUIHandler.sendMessage(message);
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
			progressDialog.dismiss();
			switch (msg.what) {
			case 0:
				try {
					
    				String string1 = (String) msg.obj;
    				JSONObject jsonObject1 = JSON.parseObject(string1);
    				String mesString = jsonObject1.getString("message");
    				showToast(mesString);
    				} catch (Exception e) {
    					// TODO: handle exception
    					e.printStackTrace();
    				}
				break;
			case 1:
				final CustomInfoBean bean = (CustomInfoBean) msg.obj;
				bean.toString();	
				//userIdTextView.setText(bean.getUserId());
				userNameTextView.setText(bean.getUserName());
				nameTextView.setText(bean.getName());
				//sexTextView.setText(bean.getSex());
				//countryTextView.setText(bean.getCountry());
				telTextView.setText(bean.getMobile());
				
				checkemail.setText(bean.getCheck_email());
				if (bean.getCheck_email()!=null) {
					if (bean.getCheck_email().toString().equals("未验证")) {
						checkemail.setText("点击绑定");
						
						checkemail.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								parentActivity1.startChildActivity("EmailCheckActivity", new Intent(
										CustomInfoActivity.this, EmailCheckActivity.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
							}
						});
					}
				}
				
				
				emailTextView.setText(bean.getEmail());
				qqTextView.setText(bean.getQq());
				phoneTextView.setText(bean.getTel());
				checkphone.setText(bean.getCheckphone());
				if(bean.getCheckphone()!=null){
					if (bean.getCheckphone().toString().equals("未验证")) {
						checkphone.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								parentActivity1.startChildActivity("CheckPhoneNumberActivity", new Intent(
										CustomInfoActivity.this, CheckPhoneNumberActivity.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
							}
						});
					}
				}
				String Maccount = bean.getMaccount();
				String hideMaccount = null;
				if(Maccount!=null){
					hideMaccount = bean.getMaccount();
					if(Maccount.length()>4&&Maccount.length()<8){
						StringBuilder resultBuilder = new StringBuilder();
					    String part1 = Maccount.substring(0,2);
					    String part2 = Maccount.substring(Maccount.length() - 2,Maccount.length());
					    resultBuilder.append(part1);
					    for(int i=0;i<Maccount.length()-3;i++){
					    	resultBuilder.append("*");  	
					    }
					    resultBuilder.append(part2);
						hideMaccount = resultBuilder.toString();
					}else if(Maccount.length()>8){
						StringBuilder resultBuilder = new StringBuilder();
					    String part1 = Maccount.substring(0,3);
					    String part2 = Maccount.substring(Maccount.length() - 4,Maccount.length());
					    resultBuilder.append(part1);
					    for(int i=0;i<Maccount.length()-7;i++){
					    	resultBuilder.append("*");  	
					    }
					    resultBuilder.append(part2);
						hideMaccount = resultBuilder.toString();
					}
					
				}
						
				if(bean.getBank_has()!=null){
					if (bean.getBank_has().toString().equals("0")||bean.getBank_has().toString().equals("-1")) {
						checkbanktext.setText("点击绑定");
						checkbanktext.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								parentActivity1.startChildActivity("CheckBankNumActivity", new Intent(
										CustomInfoActivity.this, CheckBankNumActivity.class)
										.putExtra("bean", bean)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
							}
						});
						if(hideMaccount!=null){
							banktext.setText(hideMaccount);						
						}else{
							banktext.setVisibility(View.GONE);
						}	
					} else {
						banktext.setText(hideMaccount);
						checkbanktext.setText("已绑定");
					}
					
				}
				break;
			default:
				break;
			}
		};
	};
}
