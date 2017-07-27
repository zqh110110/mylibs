package com.kfd.activityfour;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.bean.CloseOutListBean;
import com.kfd.bean.FuturesCloseOutBean;
import com.kfd.bean.FuturesCloseOutListBean;
import com.kfd.bean.FuturesPositionBean;
import com.kfd.bean.PositionBean;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

public class FuturesCloseOutActivity extends BaseActivity implements OnClickListener {

	private FuturesPositionBean positionBean;
	private String showtype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.closeout);
		if (getIntent().getSerializableExtra("bean") != null) {
			positionBean = (FuturesPositionBean) getIntent().getSerializableExtra(
					"bean");
		}
		if (getIntent().getStringExtra("type")!=null) {
			showtype  = getIntent().getStringExtra("type");
		}
		
		initTitle();
		initUI();
		initData();
		FlurryAgent.onPageView();
	}

	private LinearLayout prompt;
	private TextView number, stockname, price, type, count,qzpcmsg;
	private EditText countEditText;
	private Button submitButton;
	private CheckBox checkBox;
	private boolean judgetime = false;
    private TextView textView1;
    private LinearLayout  layout,linearLayout9;
    private EditText  entrustpriceEditText;
	private void initUI() {
		textView1=(TextView) findViewById(R.id.textView1);
		textView1.setText("期货代码");
		findViewById(R.id.linearLayout7).setVisibility(View.GONE);
		qzpcmsg = (TextView) findViewById(R.id.textView13);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		number = (TextView) findViewById(R.id.textView14);
		stockname = (TextView) findViewById(R.id.textView2);
		price = (TextView) findViewById(R.id.textView4);
		type = (TextView) findViewById(R.id.textView6);
		count = (TextView) findViewById(R.id.textView10);
		countEditText = (EditText) findViewById(R.id.editText);
		submitButton = (Button) findViewById(R.id.commitbutton);
		submitButton.setOnClickListener(this);
		prompt = (LinearLayout) findViewById(R.id.linearLayout7);
		layout  = (LinearLayout) findViewById(R.id.linearLayout8);
		entrustpriceEditText = (EditText) findViewById(R.id.editText22);
		
		linearLayout9 = (LinearLayout) findViewById(R.id.linearLayout9);
		
		if (showtype!=null && showtype.length()>0) {
			layout.setVisibility(View.VISIBLE);
			
		}
	}

	/**
	 * 获取数据
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void initData() {
		try {
			showDialog("请稍候....");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		executorService.execute(new Runnable() {

			@Override
			public void run() {
//c=FuturesOpen&a=index&pid=366&mobile=android&uid=87
				try {
					Map<String, String> hashMap = new HashMap<String, String>();				
					hashMap.put("request", "futuresFtones");
					hashMap.put("pid", positionBean.getPid());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
			
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.log("test", "FuturesCloseOutActivity @@   " + result);
					FuturesCloseOutListBean listBean = FuturesCloseOutListBean
							.parseData(result);
					if (result != null && result.length() > 1) {
						Message message = new Message();
						message.what = 1;
						message.obj = listBean;
						updateUIHandler.sendMessage(message);
					} else {
						updateUIHandler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
	}
	
	
	

	private FuturesCloseOutListBean listBean;
	
	FuturesCloseOutBean  outBean  ;
	private Handler updateUIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dismissDialog();
			switch (msg.what) {

			case 0:
				Toast.makeText(getApplicationContext(), "数据获取错误,请重试", 1000)
						.show();
				break;
			case 1:
				listBean = (FuturesCloseOutListBean) msg.obj;
				if (listBean != null) {
					outBean	=  listBean.getArrayList().get(0);
					number.setText(outBean.getNumber());
					stockname.setText(outBean.getFuturescode());
					price.setText(outBean.getNow_price());
					type.setText(outBean.getCloseout_type());
					count.setText(outBean.getNum());
					countEditText.setText(outBean.getNum());
					if ((outBean.getIsqzpc().toString()).equals("1")) {
						prompt.setVisibility(View.VISIBLE);
						judgetime=true;
						qzpcmsg.setText(outBean.getQzpcmsg());
					} else {
						prompt.setVisibility(View.GONE);
					}
					
					/*time = Long.parseLong(outBean.getTime());
					if (time < 1800) {
						prompt.setVisibility(View.VISIBLE);
					} else {
						prompt.setVisibility(View.GONE);
					}*/
				}
				break;
			case 2:
				String string = (String) msg.obj;
				try {
					JSONObject jsonObject = JSONObject.parseObject(string);
					String string3 = jsonObject.getString("message");
					setResult(10);
					finish();
					showToast(string3);
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			default:
				break;
			}
		};
	};

	private long time;
	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("平仓");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.commitbutton:
			if (listBean != null) {
				//judgetime = false;
				submitData();
			} else {
				showToast("未得到服务器数据，请返回重试");
			}

			break;

		default:
			break;
		}
	}
	private String  entrustprice;
	private void submitData() {
		if (countEditText.getText().toString().trim().equals("")) {
			showToast("请输入委托数量");
			return;
		}
		
		double amount = Double
				.parseDouble(countEditText.getText().toString().trim());
		if (amount < 0.05) {
			showToast("委托数量不能小于0.05");
			if (listBean != null) {
				countEditText.setText(outBean.getNum());
			}
			return;
		}

		if (amount > Double.parseDouble(outBean.getNum())) {
			showToast("委托数量不能大于可委托总量");
			countEditText.setText(outBean.getNum());
			return;
		}
			
			if (showtype!=null && showtype.length()>0) {
				entrustprice  = entrustpriceEditText.getText().toString().trim();
				if (entrustprice.equals("")) {
					showToast("委托价格不能为空");
					return;
				}
			}
		
		if (judgetime) {
			if (checkBox.isChecked()) {
				commitData();
			}else {
				showToast("请勾选平仓");
			}
		}else {
			commitData();		
		}
			
		/*if (checkBox.isChecked()) {
			judgetime = true;
		}

		if (time < 1800) {
			if (judgetime) {
				// 提交数据

				commitData();
			} else {
				showToast("请勾选平仓");
			}
		} else {
			commitData();
		}*/

	}

	private void commitData() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
					Map<String, String> hashMap1 = new HashMap<String, String>();
					if (showtype!=null && showtype.length()>0) {
						LogUtils.log("test", "---委托平仓---");
						hashMap1.put("request", "futuresTrustclose");
						hashMap1.put("closeprice", entrustprice);
					}else {
						hashMap1.put("request", "futuresClose");
						
					}
					
					
					hashMap1.put("pid", outBean.getPid());
					hashMap1.put("from", "2");
					hashMap1.put("mark", getMark());
					hashMap1.put("appid", getDeviceId());
					hashMap1.put("num", countEditText.getText().toString().trim());
					hashMap1.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));				
					
					/*if (time < 1800) {
						if (judgetime) {
							hashMap1.put("judgetime", "1");
						} else {
							hashMap1.put("judgetime", "0");
						}
					}*/

					try {
						String result = HttpRequest.sendPostRequest(ConstantInfo.parenturl,
								hashMap1, "UTF-8").trim();
						LogUtils.log("test", result);

						if (result != null && result.length() > 0) {
							Message message = new Message();
							message.what = 2;
							message.obj = result;
							updateUIHandler.sendMessage(message);
						} else {
							updateUIHandler.sendEmptyMessage(0);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}
}
