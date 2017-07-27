package com.kfd.activityfour;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.kfd.api.HttpRequest;
import com.kfd.bean.MessageBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

/**
 * 公告详情
 * @author zhan_hongyi
 *
 */
public class AnnDetailActivity extends BaseActivity {
	TextView titleTextView, timeTextView, contentTextView;
	Intent intent;
	MessageBean messageBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		initTitle("公告详情");
		titleTextView = (TextView) findViewById(R.id.textView1);
		timeTextView = (TextView) findViewById(R.id.textView2);
		contentTextView = (TextView) findViewById(R.id.textView3);
		if (getIntent().getSerializableExtra("messageBean") != null) {
			messageBean = (MessageBean) getIntent().getSerializableExtra("messageBean");
			loadData();
		}
	}

	private void loadData() {
		showDialog("正在加载...");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("id", messageBean.getId());
				String result = HttpRequest.sendGetRequestWithMd5(AnnDetailActivity.this, Define.host + "/api-market/noticeView", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 1;
					message.obj = result;
					handler.sendMessage(message);
				} else {
					handler.sendEmptyMessage(0);
				}
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 1:
				String resulString = (String) msg.obj;

				try {
					JSONObject jsonObject = new JSONObject(resulString);
					String ret = jsonObject.getString("ret");
					if (ret.equals("0")) {
						JSONObject jsonObject2 = jsonObject.optJSONObject("data");
						JSONObject jsonObject3 = jsonObject2.optJSONObject("notice");
						String title = jsonObject3.optString("title");
						String content = jsonObject3.optString("content");

						String dateline = jsonObject3.optString("dateline");
						titleTextView.setText(title);
						contentTextView.setText(content);
						timeTextView.setText(StringUtils.phpdateformat(dateline));

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