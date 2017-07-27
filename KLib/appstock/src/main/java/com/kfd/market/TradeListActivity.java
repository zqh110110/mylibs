package com.kfd.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kfd.activityfour.BaseActivity;
import com.kfd.activityfour.IndustryListActivity;
import com.kfd.activityfour.R;
import com.kfd.activityfour.R.id;
import com.kfd.activityfour.R.layout;
import com.kfd.api.HttpRequest;
import com.kfd.common.LogUtils;
import com.kfd.db.ConstantInfo;
import com.kfd.db.SharePersistent;

/**
 * 板块分类
 * 
 * @author 朱继洋 QQ7617812 2013-5-23
 */

public class TradeListActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tradelist);
		initTitle();
		initUI();
		getTypeData();

	}

	private ListView listView;
	private ArrayList<String> arrayList = new ArrayList<String>();
	private ArrayAdapter adapter;

	private void initUI() {
		listView = (ListView) findViewById(R.id.listView1);
		adapter = new ArrayAdapter(getApplicationContext(), R.layout.listitem,
				R.id.textView1, arrayList);
		listView.setAdapter(adapter);
	}

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("板块分类");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/**
	 * 获取数据解析类
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	private void getTypeData() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
		/*		String userid = SharePersistent.getInstance()
						.getUserInfo(getApplicationContext()).getUserid();*/
				//if (userid != null && userid.length() > 0) {
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("c", "Market");
					hashMap.put("a", "getCommonData");
					hashMap.put("mobile", "android");
					//hashMap.put("uid", userid);
					try {
						String result = HttpRequest.sendPostRequest(
								ConstantInfo.parenturl, hashMap, "UTF-8");
						LogUtils.log("test", "返回数据###" + result);
						parseData(result);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			//}
		});

	}

	private void parseData(String string) {
		Gson gson = new Gson();
		DataBean dataBean = gson.fromJson(string, DataBean.class);
		Industry industry = dataBean.data;

		// 市场分类
		String[] string2 = industry.stockType;
		for (int i = 0; i < string2.length; i++) {
			System.out.println(string2[i]);
		}

		// 行业分类
		LinkedHashMap<String, String> hashMap = industry.industry;
		for (Entry<String, String> entry : hashMap.entrySet()) {
			String string3 = entry.getValue();
		}

	}

	private DataBean dataBean;
	private Handler updateUIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				showToast("服务器获取分类数据失败");
				break;
			case 1:
				dataBean = (DataBean) msg.obj;

				Industry industry = dataBean.data;

				// 市场分类
				String[] string2 = industry.stockType;
				for (int i = 0; i < string2.length; i++) {
					arrayList.add(string2[i]);
				}

				final ArrayList<IndustryBean> list = new ArrayList<IndustryBean>();
				// 行业分类
				final LinkedHashMap<String, String> hashMap = industry.industry;
				for (Entry<String, String> entry : hashMap.entrySet()) {
					IndustryBean industryBean = new IndustryBean();
					industryBean.setId(entry.getKey());
					industryBean.setName(entry.getValue().trim());
					list.add(industryBean);
				}

				adapter.notifyDataSetChanged();
				listView.invalidate();
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent = new Intent(getApplicationContext(),
								IndustryListActivity.class);
						intent.putExtra("main", String.valueOf(arg2));
						intent.putExtra("list", list);
						startActivity(intent);
						finish();
					}
				});

				break;
			}
		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			// 跳转到板块列表页
			// startActivity(new Intent(getApplicationContext(),
			// TradeListActivity.class));
			break;
		case R.id.button2:// 刷新
			// data.clear();
			// loadData(1, listviewHandler,
			// ConstantInfo.LISTVIEW_ACTION_REFRESH);
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
}
