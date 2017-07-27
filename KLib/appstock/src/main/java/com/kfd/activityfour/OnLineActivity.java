package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.adapter.OnlineAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

/**
 * 在线充值页面
 * 
 * @author
 * 
 */
public class OnLineActivity extends BaseActivity {
	private TextView selfaccount;
	private TextView  textView15,textView16,textView17,textView25,textView26,textView27,textView35,textView36,textView37;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.online);
		initTitle();
		FlurryAgent.onPageView();
		selfaccount = (TextView) findViewById(R.id.selfaccount);
		// 设置账号，规则，随机数+时间戳+用户名
		int num = (int) (Math.random() * 100000);
		String tString = String.valueOf(System.currentTimeMillis());
		String phpString = tString.substring(0, tString.length() - 3);
		String username = null;
		/*if (SharePersistent.getInstance().getUserInfo(getApplicationContext()) != null) {
			username = SharePersistent.getInstance()
					.getUserInfo(getApplicationContext()).getUsername();
		} else {

		}*/
		textView15  = (TextView) findViewById(R.id.textView15);
		textView16  = (TextView) findViewById(R.id.textView16);
		textView17  = (TextView) findViewById(R.id.textView17);
		textView25  = (TextView) findViewById(R.id.textView25);
		textView26  = (TextView) findViewById(R.id.textView26);
		textView27  = (TextView) findViewById(R.id.textView27);
		textView35  = (TextView) findViewById(R.id.textView35);
		textView36  = (TextView) findViewById(R.id.textView36);
		textView37  = (TextView) findViewById(R.id.textView37);
		String lastString = String.valueOf(num) + phpString + username;
		selfaccount.setText(lastString);
        initTitleButton();
        freshButton.setVisibility(View.GONE);
        initData();
	}
	private ExecutorService  executorService  = Executors.newFixedThreadPool(5);
	private void initData(){
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					/*String userid = SharePersistent.getInstance()
							.getUserInfo(getApplicationContext()).getUserid();*/
					Map<String, String> hashMap = new HashMap<String, String>();
					//c=advisory&a=messagebank&mobile=android&uid=87
					hashMap.put("c", "advisory");
					hashMap.put("a", "messagebank");
					hashMap.put("mobile", "android");

					//hashMap.put("uid", userid);
					String result = HttpRequest.sendPostRequest(
							ConstantInfo.parenturl, hashMap, "UTF-8");
					LogUtils.log("test", "返回 22      " + result);
					if (result.length() > 0) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						updateHandler.sendMessage(message);
					} else {
						updateHandler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	private Handler   updateHandler  = new  Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				
				break;
			case 1:
				String result  = (String) msg.obj;
				try {
					JSONObject jsonObject  = JSONObject.parseObject(result);
					JSONObject  jsonObject2  =  jsonObject.getJSONObject("nonghang");
					textView25.setText(jsonObject2.getString("num"));
					textView26.setText(jsonObject2.getString("username"));
					textView27.setText(jsonObject2.getString("address"));
					JSONObject  jsonObject3  =  jsonObject.getJSONObject("jianhang");
					textView35.setText(jsonObject3.getString("num"));
					textView36.setText(jsonObject3.getString("username"));
					textView37.setText(jsonObject3.getString("address"));
					JSONObject jsonObject4  =  jsonObject.getJSONObject("gongshang");
					textView15.setText(jsonObject4.getString("num"));
					textView16.setText(jsonObject4.getString("username"));
					textView17.setText(jsonObject4.getString("address"));
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
	
	private ImageView bacButton;
	private TextView textView;

	private void initTitle() {

		bacButton = (ImageView) findViewById(R.id.back);
		textView = (TextView) findViewById(R.id.title_text);
		textView.setText("在线充值");

		bacButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	

}