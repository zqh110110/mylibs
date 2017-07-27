package com.kfd.activityfour;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.w3c.dom.Text;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.bean.CustomInfoBean;
import com.kfd.bean.TotalfundsBean;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

public class FuturesTradeRuleActivity extends BaseActivity {
	HomeActivityGroup parentActivity1;
	private TextView tv1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tradrule);
		parentActivity1 = (HomeActivityGroup) getParent();
		tv1 = (TextView) findViewById(R.id.textView1);
		initTitle("期货交易规则");
		initTitleButton();
		freshButton.setVisibility(View.GONE);
		FlurryAgent.onPageView();
		
		String markString = getMark();
		Cache.put("mark", markString);
		
		if (!SharePersistent.getInstance().getPerference(getApplicationContext(), "FuturesTradeRule").toString().equals("")) {
			tv1.setText(SharePersistent.getInstance().getPerference(getApplicationContext(), "FuturesTradeRule"));
		}else {
			getData();
		}
		

	}
	public   void onRefresh(){
		getData();
	};

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void getData() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				/*String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("request", "spreadRule");
					hashMap.put("appid", getDeviceId());
					hashMap.put("from", "2"); hashMap.put("mark", getMark());
					hashMap.put("type", "1");
					hashMap.put("token", SharePersistent.getInstance().getPerference(getApplicationContext(), "token"));
					try {
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
						if (result != null) {
							Message message = new Message();
							message.what = 1;
							message.obj = result;
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

	/**
	 * ui数据匹配
	 */
	private Handler updateUIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:

				break;
			case 1:
				String string =(String) msg.obj;
				try {
				JSONObject jsonObject= JSONObject.parseObject(string);
				JSONObject jsonObject1 = jsonObject.getJSONObject("data");
				String string1 = jsonObject1.getString("content");

				String string2=string1.replaceAll("&lt;p", "");
				String string3=string2.replaceAll("class=&quot;p0&quot;", "");
				String string4=string3.replaceAll("style=&quot;", "");
				String string5=string4.replaceAll("text-indent:21.0000pt;", "");	
				String string6=string5.replaceAll("&quot;&gt;", "");
				String string7=string6.replaceAll("&lt;/p&gt;", "");	

				
				String string8=string7.replaceAll("&lt;p", "");
				String string9=string8.replaceAll("class=&quot;p0&quot;", "");
				String string10=string9.replaceAll("style=&quot;text-align:center;&quot;&gt;", "");
				
				String string11=string10.replaceAll("text-align:center;", "");
				String string12=string11.replaceAll("br /&gt;", "");
				
				String string13=string12.replaceAll("&gt", "");
				String string14=string13.replaceAll("span", "");
				String string15=string14.replaceAll(";", "");
				String string16=string15.replaceAll("&lt", "");
				String ruleString=string16.replaceAll("text-indent:21pt", "");
				
				
				SharePersistent.getInstance().savePerference(getApplicationContext(), "FuturesTradeRule", ruleString);
				tv1.setText(ruleString);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				break;
			default:
				break;
			}
		};
	};
	
	
	private ProgressDialog pd;

	private void waitDialog(String message) {
		if (pd==null) {
			pd = new ProgressDialog(parentActivity1);
			pd.setMessage(message);
			pd.show();
		}
		
		
		pd.setOnKeyListener(new DialogInterface.OnKeyListener() {
            
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                // Cancel task.
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                	pd.dismiss();
                }
                return false;
            }
        });
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
	
	}
}
