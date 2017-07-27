/*
 * Copyright (c) 2014 The MaMaHelp_small_withReceiver_6.0.0 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */

package com.kfd.activityfour;

import java.util.HashMap;
import java.util.List;

import com.kfd.activityfour.MallSelectProv.JSONParser;
import com.kfd.adapter.MallSelectProvAdapter;
import com.kfd.bean.Cityinfo;
import com.kfd.common.Logcat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * @Function: TODO ADD FUNCTION.
 * @author xiaobo.lin
 * @version
 * @Date: 2014年8月7日 下午2:03:34
 */
public class MallSelectRegional extends BaseActivity  implements  OnClickListener {
	private HashMap<String, List<Cityinfo>> couny_map = new HashMap<String, List<Cityinfo>>();
	String regional_key = "";
	private ImageView backBtn;
	private ListView lv;
	String couny = "";
	private String tag = "MallSelectRegional";
	private List<Cityinfo> city_list;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmall_mall_select_prov);
		initViews();
		TextView textView  = (TextView) findViewById(R.id.title_tv);
		textView.setText("选择区域");
		getaddressinfo();
		try {
			regional_key = getIntent().getStringExtra("regional_key");
			city_list = couny_map.get(regional_key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (city_list != null && city_list.size() > 0) {
			Logcat.v(city_list.size() + "size");
			MallSelectProvAdapter adapter = new MallSelectProvAdapter(
					city_list, MallSelectRegional.this);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					couny = city_list.get(position).getCity_name();
					Intent intent = new Intent();
					intent.putExtra("couny", couny);
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		} else {
			Intent intent = new Intent();
			intent.putExtra("couny", "");
			setResult(RESULT_OK, intent);
			finish();
		}
	}


	protected void initViews() {
		// TODO Auto-generated method stub
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		backBtn = (ImageView) findViewById(R.id.back);
		backBtn.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.lv);
	}

	@Override
	public void onClick(View v) {
	
		if (v == backBtn) {
			finish();
		}
	}

	// 获取城市信息
	private void getaddressinfo() {
		// TODO Auto-generated method stub
		// 读取城市信息string
		JSONParser parser = new JSONParser();
		/*String area_str = MyFileUtils.readAssets(getApplicationContext(),"area.json");
		couny_map = parser.getJSONParserResultArray(area_str, "area2");*/
		
		String area_str = sp.getString("area_string", "");  
		couny_map = parser.getJSONParserResultArray(area_str, "area2");
	}


}
