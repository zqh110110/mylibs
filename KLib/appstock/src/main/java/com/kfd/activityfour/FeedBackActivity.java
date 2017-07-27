package com.kfd.activityfour;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.kfd.api.HttpRequest;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class FeedBackActivity   extends  BaseActivity{
	CharSequence  []  itemsStrings={"服务反馈","错误反馈"};
	int  type;
	TextView  textView;
	EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		initTitle("意见反馈");
		textView  = (TextView) findViewById(R.id.choicebankEdittext);
		editText  = (EditText) findViewById(R.id.editText1);
		findViewById(R.id.choicebanklayout).setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog alertDialog  = new AlertDialog.Builder(FeedBackActivity.this).setItems(itemsStrings, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							type=1;
							textView.setText(itemsStrings[0]);
							break;
						case 1:
							textView.setText(itemsStrings[1]);
							type=2;
							break;
						default:
							break;
						}
					}
					
				}).create();
				alertDialog.show();
			}
		});
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String  string  = editText.getText().toString().trim();
				if (StringUtils.isEmpty(string)) {
					showToast("反馈内容不能为空");
					return;
				}
				dopost(string);
			}
		});
	}
	private void dopost(final String  content){
		showDialog("请稍候...");
executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
					hashMap.put("typeid", type+"");
					hashMap.put("content", content);						
			
			
				String  result  =  HttpRequest.sendPostRequestWithMd5(FeedBackActivity.this, Define.host+"/api-user/feedback", hashMap);
			
				if (!StringUtils.isEmpty(result)) {
					Message message  = new Message();
					message.obj = result;
					message.what=1;
					updateUserInfoHandler.sendMessage(message);
				}else {
					updateUserInfoHandler.sendEmptyMessage(0);
				}
			}
		});
	}
	private Handler  updateUserInfoHandler =  new Handler(){
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 1:
				String  result  = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret  = jsonObject.optString("ret");
					if (ret.equals("0")) {
						showToast("提交成功");
						finish();
					}else {
						String  messgsg  = jsonObject.optString("msg");
						showToast(messgsg);
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
