package com.kfd.activityfour;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.interfaces.RSAKey;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.alibaba.fastjson.JSONObject;
import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.api.ApiClient;
import com.kfd.api.HttpRequest;
import com.kfd.api.MD5;
import com.kfd.api.Tools;
import com.kfd.common.Define;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;


import com.kfd.ui.TimeButton;






import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户注册界面
 * 
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {
	TimeButton  phone_verify_btn;
	ImageView phone_pwd_show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rigister);
		phone_verify_btn  = (TimeButton) findViewById(R.id.timeButton1);
		phone_verify_btn.onCreate(savedInstanceState);
		phone_verify_btn.setText("获取验证码");
		phone_verify_btn.setOnClickListener(this);
		
		phone_pwd_show  = (ImageView) findViewById(R.id.phone_pwd_show);
		phone_pwd_show.setOnClickListener(this);
		initTitle("注册帐户");
		initUI();
		//imageView = (ImageView) findViewById(R.id.imgview);
		//getImageAndSession();
		initTitleButton();
		freshButton.setVisibility(View.GONE);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		//getImageAndSession();
	}


	
  
	private ImageView imageView;

	


	private Button submitButton, commitButton,getnumberbutton;
	private TextView titleTextView;
	private EditText accouEdit, phoneedit, codeedit;
		

	public LinearLayout moreinfo,ll;



	public void initUI() {
		accouEdit = (EditText) findViewById(R.id.accoutEdit);
		phoneedit = (EditText) findViewById(R.id.loginEdit);
		codeedit = (EditText) findViewById(R.id.submitEdit);

	//	checkBox = (CheckBox) findViewById(R.id.checkBox);
		submitButton = (Button) findViewById(R.id.btn1);

		submitButton.setOnClickListener(this);

	
		//imagetextEdittxt = (EditText) findViewById(R.id.edit8);
	
		ll = (LinearLayout) findViewById(R.id.ll1);
		
		getnumberbutton = (Button) findViewById(R.id.getnumberbutton);
		getnumberbutton.setOnClickListener(this);
	
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		if (v==submitButton) {
			String email = accouEdit.getText().toString().trim();
			String phone = phoneedit.getText().toString().trim();
			String code = codeedit.getText().toString().trim();
			Tools.hideInputBoard(RegisterActivity.this);
			showDialog("请稍候...");
				register(email,phone,code );
			}else if (v==phone_verify_btn) {
				String phone = phoneedit.getText().toString().trim();
				sendTelPhoneCode(phone);
			}
	}
	private void register(final String email,final String phone,final String code){
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				hashMap.put("realname", email);
				hashMap.put("mobile", phone);
				hashMap.put("code", code);
				String strResult = HttpRequest.sendPostRequestWithMd5(RegisterActivity.this, Define.host+"/api-user/register",hashMap );
			
				try {
				
					if (!StringUtils.isEmpty(strResult)) {
						Message msg = new Message();
						msg.what=1;
						msg.obj= strResult;
						registHandler.sendMessage(msg);
					}else {
						showToast("当前网络较差，请重试");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			
			}
		});
	
	}
	   
	private Handler  registHandler =  new Handler(){
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 0:
				
				break;
			case 1:
				String resultString  = (String) msg.obj;
				try {
					org.json.JSONObject jsonObject  = new org.json.JSONObject(resultString);
					String ret  = jsonObject.getString("ret");
					String message  =  jsonObject.getString("msg");
					if (ret.equals("0")) {
						showToast(message);
						//跳转到设置用户名和密码页面
						Intent intent =  new Intent(RegisterActivity.this, SetNameAndPasswordActivity.class);
						startActivity(intent);
						finish();
					}else {
						Intent intent =  new Intent(RegisterActivity.this, SetNameAndPasswordActivity.class);
						startActivity(intent);
						finish();
						showToast(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};
	   /**
		 * 
		 * @description  发送短信验证码 
		 * @author zhu
		 * @update 2015年5月22日 下午3:46:58
		 */
		private void sendTelPhoneCode(final String mobile){
			
			executorService.execute(new Runnable() {
				
				@Override
				public void run() {
					LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
					hashMap.put("mobile", mobile);
					hashMap.put("type", "register");
		
					try {
						String result = HttpRequest.sendGetRequestWithMd5(RegisterActivity.this, Define.host+"/api-user/code", hashMap);
						if (!StringUtils.isEmpty(result)) {
							Message msg = new Message();
							msg.what=1;
							msg.obj= result;
							codeHandler.sendMessage(msg);
						}else {
							showToast("当前网络较差，请重试");
						}
					} catch (Exception e) {
						showToast("当前网络较差，请重试");
						e.printStackTrace();
					}
				}
			});
		}
private Handler  codeHandler =  new Handler(){
	public void handleMessage(Message msg) {
		dismissDialog();
		switch (msg.what) {
		case 0:
			
			break;
		case 1:
			String resultString  = (String) msg.obj;
			try {
				JSONObject jsonObject  = JSONObject.parseObject(resultString);
				String ret  = jsonObject.getString("ret");
				String message  =  jsonObject.getString("msg");
				if (ret.equals("0")) {
					showToast(message);
					//跳转到设置用户名和密码页面
					
				}else {
					showToast(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	};
};

	
	
	
}