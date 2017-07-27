package com.kfd.activityfour;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.bean.CustomInfoBean;
import com.kfd.bean.DrawBean;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

/**
 * 提款类
 * 
 * @autho
 */
public class DrawMoneyActivity extends BaseActivity implements OnClickListener {
	private String maxDrawnumber;
	private ProgressBar  progressBar;
	private String type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawmoney);
			progressBar = (ProgressBar) findViewById(R.id.titleProgress);
		initTitleButton();
		initUI();
		TextView titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("取款申请");
		if (getIntent().getStringExtra("maxDraw")!=null) {
			maxDrawnumber = getIntent().getStringExtra("maxDraw");
		}
		if (getIntent().getStringExtra("type")!=null) {
			type = getIntent().getStringExtra("type");
		}
		
		
		getData("");
	
		FlurryAgent.onPageView();

	}

@Override
public void onRefresh() {
	// TODO Auto-generated method stub
	super.onRefresh();
	getData("");
}



	private TextView nameTextView,bankaddressEditText,bankcardEditText,costEditText,realMoneyEditText;
	private EditText banknameEditText, passwordEditText, drawAmountEditText;
	private Button commitButton;
	private LinearLayout  accountLayout,idcardLayout;
	private void initUI() {
		accountLayout =  (LinearLayout) findViewById(R.id.linearLayout3); //银行卡号
		idcardLayout=  (LinearLayout) findViewById(R.id.linearLayout5);//身份证
		nameTextView = (TextView) findViewById(R.id.textView2);
		bankaddressEditText = (TextView) findViewById(R.id.editText1);
		bankcardEditText = (TextView) findViewById(R.id.editText2);
		passwordEditText = (EditText) findViewById(R.id.editText5);
		drawAmountEditText = (EditText) findViewById(R.id.editText6);
		costEditText = (TextView) findViewById(R.id.editText7);
		realMoneyEditText = (TextView) findViewById(R.id.editText8);
		commitButton = (Button) findViewById(R.id.button1);
		commitButton.setOnClickListener(this);
		costEditText.setFocusable(false);
		realMoneyEditText.setFocusable(false);

		drawAmountEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (drawAmountEditText.getText().toString()
								.trim()!=null) {
						getrealData(drawAmountEditText.getText().toString().trim());
				}
				

			}
		});
	}
	private double number;
	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void getData(String string) {
		try {
			progressDialog = new ProgressDialog(getParent());
			progressDialog.setMessage("数据获取中，请稍候....");
			
			progressDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		progressBar.setVisibility(View.VISIBLE);
		freshButton.setVisibility(View.GONE);
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
				/*	String userid = SharePersistent.getInstance()
							.getUserInfo(getApplicationContext()).getUserid();*/
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "userBasic");
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");

					CustomInfoBean bean = CustomInfoBean.parseData1(result);
					if (bean != null) {
						Message message = new Message();
						message.what = 1;
						message.obj = bean;
						updateHandler.sendMessage(message);
					}else{
						updateHandler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}
	
	
	private void getrealData(final String money) {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
				/*	String userid = SharePersistent.getInstance()
							.getUserInfo(getApplicationContext()).getUserid();*/
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "accountApplyfee");
					hashMap.put("appid", getDeviceId());
					hashMap.put("money", money);
					hashMap.put("type", type);
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.log("test", "返回数据" + result);
					if (result.length() > 0 ) {
						Message message = new Message();
						message.what = 3;
						message.obj = result;
						updateHandler.sendMessage(message);
					}else{
						updateHandler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	private String costmoney;
	private CustomInfoBean customInfoBean;
	private Handler updateHandler = new Handler() {
		public void handleMessage(Message msg) {
			dismissDialog();
			
			progressBar.setVisibility(View.GONE);
			freshButton.setVisibility(View.VISIBLE);
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "没有接收到返回数据", 1000)
				.show();
				break;
			case 1:
				CustomInfoBean customInfoBean= (CustomInfoBean) msg.obj;
				customInfoBean.toString();
				
				if (customInfoBean.getMaccount()==null) {
					showToast("请先绑定银行卡");
				}
				
				String Maccount = customInfoBean.getMaccount();
				String hideMaccount = null;
				if(Maccount!=null){
					hideMaccount = customInfoBean.getMaccount();
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
				
				nameTextView.setText(customInfoBean.getName());
				bankcardEditText.setText(hideMaccount);
				bankaddressEditText.setText(customInfoBean.getAddress());

				break;


			case 2:
				try {
					String string = (String) msg.obj;
					JSONObject jsonObject = JSONObject.parseObject(string);
					Toast.makeText(getApplicationContext(),
							jsonObject.getString("message"), 2000).show();
					if (jsonObject.getString("status").equals("1")) {
						finish();
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				break;
			case 3:
				try {
					String string2 = (String) msg.obj;
					JSONObject jsonObj1 = JSON.parseObject(string2);
					JSONObject jsonObj = jsonObj1.getJSONObject("data");
					if (jsonObj!=null) {
						costEditText.setText(jsonObj.getString("fee"));
						realMoneyEditText.setText(jsonObj.getString("equal")+"元");					
					}
					
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
	String bankaddress, bankcardnum, bankname, idcard, password, drawAmount,drawFormalities;
	double  percent;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			bankaddress = bankaddressEditText.getText().toString().trim();
			bankcardnum = bankcardEditText.getText().toString().trim();
			password = passwordEditText.getText().toString().trim();
			drawAmount = drawAmountEditText.getText().toString().trim();
			
			if (drawAmount.toString().equals("")) {
				Toast.makeText(getApplicationContext(),
						"取款额度必须大于100", 1000).show();
				break ;
			}
			final double drawnum = Double.parseDouble(drawAmount);
			drawFormalities  = costEditText.getText().toString().trim();
			
			if (bankaddress.length() > 0) {
				if (bankcardnum.length() > 0) {
							if (password.length() > 0) {
								if (drawnum >= 100) {
									AlertDialog alertDialog  =  new AlertDialog.Builder(getParent())
									.setMessage("取款金额"+drawAmount).setPositiveButton("確定", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub				
												postData();
												} 
									}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											
										}
									}).create();
									alertDialog.show();
								} else {
									Toast.makeText(getApplicationContext(),
											"取款额度必须大于100", 1000).show();
								}

							} else {
								Toast.makeText(getApplicationContext(),
										"密码不能为空", 1000).show();
							}

				} else {
					Toast.makeText(getApplicationContext(), "请先绑定银行卡", 1000)
							.show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "请先绑定银行卡", 1000)
						.show();
			}
			
			break;
		case R.id.button2:
			finish();
			break;
		default:
			break;
		}
	}
	
	

	/**
	 * 提交数据到服务器
	 */
	private void postData() {
		try {
			showDialog("取款申请中....");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "accountCashapply");
					hashMap.put("appid", getDeviceId());
					hashMap.put("money", drawAmount);
					hashMap.put("money_pawd", password);
					hashMap.put("type", type);
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					

					try {
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
						LogUtils.log("test", "result bank    " + result);
						if (result.length() > 0) {
							Message message = new Message();
							message.obj = result;
							message.what = 2;
							updateHandler.sendMessage(message);
						} else {
							updateHandler.sendEmptyMessage(0);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			//}
		});
	}

}
