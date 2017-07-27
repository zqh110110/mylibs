package com.kfd.activityfour;

import java.util.LinkedHashMap;







import org.json.JSONObject;

import u.aly.s;

import com.flurry.org.codehaus.jackson.node.IntNode;
import com.kfd.api.HttpRequest;
import com.kfd.api.MD5;
import com.kfd.common.AES;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.db.SharePersistent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Element;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 设置用户名和密码
 * @author zhu
 *2015年6月5日
 */
public class SetNameAndPasswordActivity  extends  BaseActivity {
	String  type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setusernameandpw);
		initUI();
		if (getIntent().getStringExtra("type")!=null) {
			type= getIntent().getStringExtra("type");
			commitButton.setText("保存");
			if (type.equals("setnickname")) {
				initTitle("设置昵称");
				if (getIntent().getStringExtra("nickname")!=null) {
					namEditText.setText(getIntent().getStringExtra("nickname"));
				//	passwordEditText.setVisibility(View.GONE);
				}
				passwordEditText.setText(SharePersistent.getInstance().getPerference(getApplicationContext(), "pwd"));
				findViewById(R.id.pwdrelativeLayout2).setVisibility(View.GONE);
			}else if (type.equals("setpwd")) {
				initTitle("设置密码");
				if (getIntent().getStringExtra("nickname")!=null) {
					namEditText.setText(getIntent().getStringExtra("nickname"));
					
				}
				findViewById(R.id.nickrelativeLayout1).setVisibility(View.GONE);
				//passwordEditText.setText(SharePersistent.getInstance().getPerference(getApplicationContext(), "pwd"));
				//namEditText.setVisibility(View.GONE);
			}
  		}else {
			
			initTitle("设置昵称和密码");
		}
	}
	
	EditText  namEditText,passwordEditText;
	Button   commitButton;
	private void initUI(){
		namEditText  = (EditText) findViewById(R.id.editText1);
		passwordEditText = (EditText) findViewById(R.id.editText2);
		commitButton = (Button) findViewById(R.id.button1);
		commitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = namEditText.getText().toString().trim();
				String passwordString  = passwordEditText.getText().toString().trim();
				showDialog("请稍候...");
				commit(name, passwordString);
			}
		});
	}
	private void commit(final String name,final   String pwd){
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				Message message = new Message();
				LinkedHashMap<String, String>  hashMap = new LinkedHashMap<String, String>();
				hashMap.put("nickname", name);
				String pwdencrypt;
				try {
					
					String url  = Define.host+"/api-user-main/set";
					if (type!=null &&type.equals("setnickname")) {
						url  = Define.host+"/api-user-main/update";
					}else {
						pwdencrypt = AES.Encrypt(pwd, "a6ce962f31d4a3d9");
						hashMap.put("password", pwdencrypt);
					}
					String resutString =  HttpRequest.sendPostRequestWithMd5(SetNameAndPasswordActivity.this, url, hashMap);
					if (!StringUtils.isEmpty(resutString)) {					
						message.what=1;
						message.obj= resutString;
						updateHandler.sendMessage(message);
					}else {
						updateHandler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
		});
	}
	private Handler updateHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 0:
				showToast("设置失败，请重试");
				break;
			case  1:
				String rest=  (String) msg.obj;
				try {
					JSONObject  jsonObject  = new JSONObject(rest);
					String ret  = jsonObject.getString("ret");
					String mss=  jsonObject.getString("msg");
					
					if (ret.equals("0")) {
						if (type!=null) {
							finish();
						}else {
							Intent intent = new Intent(getApplicationContext(), MainActivity.class);
							startActivity(intent);
							finish();
						}
					
					}else {
						showToast(mss);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			default:
				break;
			}
		};
	};
}
