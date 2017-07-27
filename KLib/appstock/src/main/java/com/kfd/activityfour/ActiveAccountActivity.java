package com.kfd.activityfour;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kfd.activityfour.R;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.bean.StockBean;
import com.kfd.bean.StockInfo;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;
public class ActiveAccountActivity  extends BaseActivity{

	private EditText idcardedit,moneypwedit,moneycardnumedit,nameEdit;
	Button  submit,back;
	StockBean stockBean;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activieacount);
		
		initTitle("激活账户");
		if (getIntent().getSerializableExtra("bean")!=null) {
			stockBean = (StockBean) getIntent().getSerializableExtra("bean");
		}
		idcardedit  =  (EditText) findViewById(R.id.idcardedit);
		moneycardnumedit  =  (EditText) findViewById(R.id.moneycardnumedit);
		moneypwedit =  (EditText) findViewById(R.id.moneypwedit);
		nameEdit = (EditText) findViewById(R.id.nameEdit);
		submit = (Button) findViewById(R.id.btn1);
		back  = (Button) findViewById(R.id.btn2);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			activeAccout();	
			}
		});
		
	}
	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void activeAccout() {
		showDialog("请稍候....");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// http://mm.ber9999.com/?c=&a=&code=sz000006
				String idcardString   = idcardedit.getText().toString().trim();
				String moneycardnum   = moneycardnumedit.getText().toString().trim();
				String moneypw   = moneypwedit.getText().toString().trim();
				String truename = nameEdit.getText().toString().trim();
			//	String uidString  =  SharePersistent.getInstance().getUserInfo(getApplicationContext()).getUserid();
			
				
				
				//if (uidString!=null && uidString.length()>0) {
					
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("c", "user");
					hashMap.put("a", "updateUserAndroid");
					//hashMap.put("uid", uidString);
					hashMap.put("mobile", "android");
					
					String url = ApiClient.makeUrl(ConstantInfo.parenturl1,
							hashMap);
				LogUtils.v("test", "url   " + url);
					Map<String, String> hashMap1 = new HashMap<String, String>();
				//	hashMap1.put("act", "register_do");
					hashMap1.put("user[name]", truename);
					hashMap1.put("user[credentials_no]", idcardString);
					hashMap1.put("user[money_password]", moneypw);
					hashMap1.put("user[bank_account]", moneycardnum);
				//hashMap1.put("uid", uidString);
					
					try {
						String result = HttpRequest.sendHttpClientPost(
								url, hashMap1, "UTF-8").trim();
						LogUtils.v("test", "reslt"+ result);
					
						if (result!=null && result.length()>0) {
							Message msg = new Message();
							msg.obj = result;
							msg.what = 1;
							updateUIHandler.sendMessage(msg);
						}else {
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
	private Handler updateUIHandler   = new  Handler(){
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 0:
				//showToast("");
				break;
			case 1:
				try {
					String result  = (String) msg.obj;
					LogUtils.v("test", result);
					JSONObject  jsonObject =  JSONObject.parseObject(result);
					
					showToast(jsonObject.getString("message"));
					//如果成功的话再跳回去
					
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
