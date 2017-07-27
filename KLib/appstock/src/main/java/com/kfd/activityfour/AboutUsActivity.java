package com.kfd.activityfour;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.kfd.api.HttpRequest;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

public class AboutUsActivity  extends BaseActivity {
	TextView  telTextView,qqTextView,emailTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		initTitle("关于我们");
		telTextView  =  (TextView) findViewById(R.id.teltextView2);
		qqTextView  =  (TextView) findViewById(R.id.qqtextView2);
		emailTextView  =  (TextView) findViewById(R.id.emailtextView2);
		loadData();
	}

	 private void loadData(){
		 showDialog("请稍候...");
		   executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				String  result  = HttpRequest.sendGetRequestWithMd5(AboutUsActivity.this, Define.host+"/api-main/about", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what=1;
					message.obj=  result;
					handler.sendMessage(message);
				}else {
					handler.sendEmptyMessage(0);
				}
			}
		});
	   }
	 private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 1:
				  String  resulString = (String) msg.obj;

					try {
						JSONObject jsonObject = new JSONObject(resulString);
						String ret  = jsonObject.getString("ret");
						if (ret.equals("0")) {
							JSONObject  jsonObject2 = jsonObject.optJSONObject("data");
							/*8
							 * "data": {

        "tel": "400-689-1818",

        "qq": "800028539",

        "email": "kefe@kfd9999.com",

        "about": "http://kfd.demo.golds-cloud.com/info/about"

							 */
							String tel  =  jsonObject2.optString("tel");
							String  qq =  jsonObject2.optString("qq");
							String email  = jsonObject2.optString("email");
							String  about  =  jsonObject2.optString("about");
							telTextView.setText(tel);
							qqTextView .setText(qq);
							emailTextView.setText(email);
							
							}
						}catch (Exception e) {
							e.printStackTrace();
						}
				break;

			default:
				break;
			}
		}; 
	 };
}
