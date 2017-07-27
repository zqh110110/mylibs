package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.RefreshListViewAdapter;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.bean.StockBean;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
import com.kfd.ui.PullToRefreshListView;

/**
 * 汇总信息
 * 
 *
 */
public class CollectInformationActivity extends BaseActivity implements
		OnClickListener {
private ProgressBar progressBar;
private String type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collectinformation);
		initTitleButton();
		progressBar = (ProgressBar) findViewById(R.id.titleProgress);
		initTitle();
		initUI();
		initBottom();
		if (getIntent().getStringExtra("type")!=null) {
			type = getIntent().getStringExtra("type");
		}
		getData();
		FlurryAgent.onPageView();

	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		getData();
	}
	
	private TextView textView, textView2, textView3, textView4;

	private void initUI() {
		textView = (TextView) findViewById(R.id.textView2);
		textView2 = (TextView) findViewById(R.id.textView4);
		textView3 = (TextView) findViewById(R.id.textView6);
		textView4 = (TextView) findViewById(R.id.textView8);
	}

	Button button1, button2, button3, button4, button5;

	private void initBottom() {

		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button5.setVisibility(View.GONE);
		findViewById(R.id.r5).setVisibility(View.GONE);
		button1.setText("日期");
		button2.setText("刷新");
		button3.setText("个股");
		button3.setVisibility(View.GONE);
		findViewById(R.id.r3).setVisibility(View.GONE);
		button4.setText("返回");
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
	}

	private PullToRefreshListView pullToRefreshListView;// 自定义的listview
	private RefreshListViewAdapter adapter;
	private ArrayList<StockBean> data = new ArrayList<StockBean>();
	private View listView_footer;
	private TextView listView_foot_more;
	private ProgressBar listView_foot_progress;
	private int sumdata;

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void getData() {
		progressBar.setVisibility(View.VISIBLE);
		freshButton.setVisibility(View.GONE);
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					Map<String, String> hashMap = new HashMap<String, String>();
					if (type!=null && type.length()>0) {
						hashMap.put("request", "reportFlccount");
					}else{
						hashMap.put("request", "reportLccount");
					}
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));

					boolean issearch = false;

					if (searchtime != null && searchtime.length() > 3) {
						hashMap.put("search_time", searchtime);
						issearch = true;
					}

					String result = null;
					// if (issearch) {
					// LogUtils.log("test", "search_time   "+searchtime);
					// result = JsonParseTool.sendHttpClientPost(
					// url, hashMap1, "UTF-8");
					// }else {
					result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					// }
					LogUtils.log("test", "返回   " + result);
					parseData(result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	JSONObject jsonObject;

	private void parseData(String string) {
		try {
			jsonObject = JSONObject.parseObject(string);
			int status = jsonObject.getIntValue("status");
			if (status == 1) {

				ResponseBean responseBean = new ResponseBean();
				JSONObject jsonObject2 = jsonObject.getJSONObject("data");
				JSONObject jsonObject3 = jsonObject2.getJSONObject("collectList");
				
				responseBean.setYk(jsonObject3.getString("zyk"));
				responseBean.setLive_cost(jsonObject3.getString("zLiveCost"));
				if (type!=null && type.length()>0) {
					responseBean.setRealmoney(jsonObject3.getString("faccount"));
				}else {
					responseBean.setRealmoney(jsonObject3.getString("saccount"));
				}
				responseBean.setZyMoney(jsonObject3.getString("zZyMoney"));
				responseBean.toString();
				Message message = new Message();
				message.what = 1;
				message.obj = responseBean;
				updateUIHandler.sendMessage(message);
			} else {
				Message message = new Message();
				message.obj = jsonObject.getString("message");
				message.what = 0;
				updateUIHandler.sendMessage(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message message = new Message();
			message.obj = jsonObject.getString("message");
			message.what = 0;
			updateUIHandler.sendMessage(message);
		}

	}

	class ResponseBean {
		private String yk, live_cost, realmoney, zyMoney;

		public String getYk() {
			return yk;
		}

		public void setYk(String yk) {
			this.yk = yk;
		}

		public String getLive_cost() {
			return live_cost;
		}

		public void setLive_cost(String live_cost) {
			this.live_cost = live_cost;
		}

		public String getRealmoney() {
			return realmoney;
		}

		public void setRealmoney(String realmoney) {
			this.realmoney = realmoney;
		}

		public String getZyMoney() {
			return zyMoney;
		}

		public void setZyMoney(String zyMoney) {
			this.zyMoney = zyMoney;
		}

		@Override
		public String toString() {
			LogUtils.log("test", "ResponseBean [yk=" + yk + ", live_cost="
					+ live_cost + ", realmoney=" + realmoney + ", zyMoney="
					+ zyMoney + "]");
			return "ResponseBean [yk=" + yk + ", live_cost=" + live_cost
					+ ", realmoney=" + realmoney + ", zyMoney=" + zyMoney + "]";
		}

	}

	private Handler updateUIHandler = new Handler() {
		public void handleMessage(Message msg) {
			progressBar.setVisibility(View.GONE);
			freshButton.setVisibility(View.VISIBLE);
			switch (msg.what) {
			case 0:
				String message = (String) msg.obj;
				Toast.makeText(getApplicationContext(), message, 1000).show();
				textView.setText("0");
				textView2.setText("0");
				textView3.setText("0");
				textView4.setText("0");
				break;
			case 1:
				ResponseBean responseBean = (ResponseBean) msg.obj;
					textView.setText(responseBean.getRealmoney());
					textView2.setText(responseBean.getYk());
					textView3.setText(responseBean.getLive_cost());
					if (responseBean.getZyMoney()!=null) {
						textView4.setText(responseBean.getZyMoney());
					}else {
						textView4.setText("0");
					}		

					break;
			default:
				break;
				}
				
			}
		};

	RelativeLayout mHead;

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("汇总信息 ");
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
		case R.id.button1:
			showDate();

			break;
		case R.id.button2:// 刷新
			data.clear();
			getData();
			break;
		case R.id.button3:
			// 显示键盘对话框
			break;
		case R.id.button4:
			finish();
			break;

		default:
			break;
		}
	}

	private String searchtime;

	private void showDate() {
		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);

		mMonth = c.get(Calendar.MONTH);

		mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog datePickerDialog = new DatePickerDialog(this,
				mDateSetListener, mYear, mMonth, mDay);
		datePickerDialog.show();

	}

	private int mYear;
	private int mMonth;
	private int mDay;
	/**
	 * 
	 * 日期控件的事件
	 */

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,

		int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			String date = new StringBuilder().append(mYear).append("-").append(

			(mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
					.append(

					(mDay < 10) ? "0" + mDay : mDay).toString();

			searchtime = date;
			button1.setText(searchtime);
			// 点击完结束时间后重新加载数据
			data.clear();
			getData();
		}

	};

}
