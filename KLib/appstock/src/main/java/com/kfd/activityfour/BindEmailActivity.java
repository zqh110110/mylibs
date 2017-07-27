package com.kfd.activityfour;

import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.kfd.api.HttpRequest;
import com.kfd.api.Tools;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.umeng.analytics.c;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
    绑定邮箱
 * 2015-6-14
 */
public class BindEmailActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bindemail);
		initUI();
		initTitle("绑定邮箱");
	}
	EditText  inputemailEditText;
	Button  commitButton;
	private void initUI(){
		inputemailEditText  =  (EditText) findViewById(R.id.editText1);
		commitButton = (Button) findViewById(R.id.button1);
		commitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String emailString  = inputemailEditText.getText().toString().trim();
				if (!StringUtils.isEmpty(emailString)) {
					Tools.hideInputBoard(BindEmailActivity.this);
					commitData(emailString);
				}
			}
		});
	}
	private void commitData(final String mail){
		showDialog("请稍候...");
		executorService.execute(new Runnable() {
			public void run() {
				LinkedHashMap<String, String>  hashMap = new LinkedHashMap<String, String>();
				hashMap.put("email", mail);
				String result  =  HttpRequest.sendGetRequestWithMd5(BindEmailActivity.this, Define.host+"/api-user-main/email", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what=1;
					message.obj = result;
					upHandler.sendMessage(message);
					
				}else {
					upHandler.sendEmptyMessage(0);
				}
			}
		});
	}
	private Handler  upHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 0:
				
				break;
			case 1:
				try {
					String  result   = (String) msg.obj;
					JSONObject jsonObject = new JSONObject(result);
					String ret  = jsonObject.optString("ret");
					String mesString=   jsonObject.optString("msg");
					showToast(mesString);
					if (ret.equals("0")) {
						finish();
					}else {
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};
	
}
