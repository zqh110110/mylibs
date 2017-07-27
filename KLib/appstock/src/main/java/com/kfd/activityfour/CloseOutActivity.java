package com.kfd.activityfour;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
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
import com.kfd.bean.PositionBean;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

/**
 * 平仓窗口
 * 
 * 
 */
public class CloseOutActivity extends BaseActivity implements OnClickListener {

	private PositionBean positionBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.closeout);
		if (getIntent().getSerializableExtra("bean") != null) {
			positionBean = (PositionBean) getIntent().getSerializableExtra(
					"bean");
		}
		initTitle();
		initUI();
		initData();
		FlurryAgent.onPageView();
	}

	private LinearLayout prompt;
	private TextView number, stockname, price, type, count,close_style,qzpcmsg;
	private EditText countEditText;
	private Button submitButton;
	private CheckBox checkBox;
	private boolean judgetime = false;

	private void initUI() {
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		qzpcmsg = (TextView) findViewById(R.id.textView13);
		number = (TextView) findViewById(R.id.textView14);
		stockname = (TextView) findViewById(R.id.textView2);
		price = (TextView) findViewById(R.id.textView4);
		type = (TextView) findViewById(R.id.textView6);
		close_style = (TextView) findViewById(R.id.textView8);
		count = (TextView) findViewById(R.id.textView10);
		countEditText = (EditText) findViewById(R.id.editText);
		submitButton = (Button) findViewById(R.id.commitbutton);
		submitButton.setOnClickListener(this);
		prompt = (LinearLayout) findViewById(R.id.linearLayout7);

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

				try {
					/*String userid = SharePersistent.getInstance()
							.getUserInfo(getApplicationContext()).getUserid();*/
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "stocksSingle");
					hashMap.put("pid", positionBean.getPid());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					//hashMap.put("uid", userid);
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.log("test", "@@   " + result);
					CloseOutListBean listBean = CloseOutListBean
							.parseData1(result);
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

	private CloseOutListBean listBean;
	private Handler updateUIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dismissDialog();
			switch (msg.what) {

			case 0:
				Toast.makeText(getApplicationContext(), "数据获取错误,请重试", 1000)
						.show();
				break;
			case 1:
				listBean = (CloseOutListBean) msg.obj;
				if (listBean != null) {
					number.setText(listBean.getNumber());
					stockname.setText(listBean.getStock_name());
					price.setText(listBean.getNowprice());
					type.setText(listBean.getCloseout_type());
					count.setText(listBean.getNum());
					countEditText.setText(listBean.getNum());
					close_style.setText(listBean.getClose_style());
					//time = Long.parseLong(listBean.getTime());
					if ((listBean.getIsqzpc().toString()).equals("1")) {
						prompt.setVisibility(View.VISIBLE);
						judgetime=true;
						qzpcmsg.setText(listBean.getQzpcmsg());
					} else {
						prompt.setVisibility(View.GONE);
					}
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
				submitData();
			} else {
				showToast("未得到服务器数据，请返回重试");
			}

			break;

		default:
			break;
		}
	}

	private void submitData() {
		int amount = Integer
				.parseInt(countEditText.getText().toString().trim());
		if (amount < 100) {
			showToast("委托数量不能小于100");
			if (listBean != null) {
				countEditText.setText(listBean.getNum());
			}
			return;
		}

		if (amount > Integer.parseInt(listBean.getNum())) {
			showToast("委托数量不能大于可委托总量");
			countEditText.setText(listBean.getNum());
			return;
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
		

	}

	private void commitData() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "stocksClose");
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("appid", getDeviceId());	
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					
					try {
						hashMap.put("num", countEditText.getText().toString().trim());
						hashMap.put("pid", listBean.getPid());
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl,
								hashMap, "UTF-8").trim();
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
			//}
		});
	}
}