package com.kfd.activityfour;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.market.IndustryBean;
import com.kfd.market.MarketCenterActivity;

/**
 * 行业分类显示
 * 
 * @author 
 */
public class IndustryListActivity extends Activity {
	private String main;
	private ArrayList<IndustryBean> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.industry);
		if (getIntent().getStringExtra("main") != null) {
			main = getIntent().getStringExtra("main");
		}
		if (getIntent().getSerializableExtra("list") != null) {
			list = (ArrayList<IndustryBean>) getIntent().getSerializableExtra(
					"list");
		}
		initTitle();
		initUI();
		uiAdapte();
		FlurryAgent.onPageView();
	}

	private ListView listView;
	private ArrayList<String> arrayList = new ArrayList<String>();
	private ArrayAdapter adapter;

	private void initUI() {
		listView = (ListView) findViewById(R.id.listView1);

		adapter = new ArrayAdapter(getApplicationContext(), R.layout.listitem1,
				R.id.textView1, arrayList);

		listView.setAdapter(adapter);
	}

	private void uiAdapte() {
		if (list != null) {

			for (IndustryBean industryBean : list) {
				arrayList.add(industryBean.getName().trim());
			}
			adapter.notifyDataSetChanged();
			listView.invalidate();
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent intent = new Intent(getApplicationContext(),
							MarketCenterActivity.class);
					intent.putExtra("main", main);
					// intent.putExtra("typeindex", );
				}
			});
		}
	}

	private ImageView backButton;
	private TextView titleTextView;

	private void initTitle() {

		backButton = (ImageView) findViewById(R.id.back);

		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("行业分类");
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
