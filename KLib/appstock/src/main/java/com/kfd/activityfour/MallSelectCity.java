/*
 * Copyright (c) 2014 The MaMaHelp_small_withReceiver_6.0.0 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */

package com.kfd.activityfour;

import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Text;

import com.kfd.activityfour.MallSelectProv.JSONParser;
import com.kfd.adapter.MallSelectProvAdapter;
import com.kfd.bean.Cityinfo;

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
 * @Date: 2014年8月7日 下午12:07:14
 */
public class MallSelectCity extends BaseActivity implements OnClickListener {
	private ListView lv;
	private ImageView backBtn;
	private HashMap<String, List<Cityinfo>> city_map = new HashMap<String, List<Cityinfo>>();
	String city_key = "";
	String city = "";
	private String tag = "MallSelectCity";
	public final static int SELECT_COUNY_FOR_RESULT = 0x100 + 912;
	private List<Cityinfo> city_list;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmall_mall_select_prov);
		initViews();
		getaddressinfo();
		TextView textView  = (TextView) findViewById(R.id.title_tv);
		textView.setText("选择城市");
		try {
			city_key = getIntent().getStringExtra("city_key");
			city_list = city_map.get(city_key); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (city_list != null && city_list.size() > 0) {
			MallSelectProvAdapter adapter = new MallSelectProvAdapter(
					city_map.get(city_key), MallSelectCity.this);
			lv.setAdapter(adapter);
		} else {
			Intent intent = new Intent();
			intent.putExtra("city", "");
			intent.putExtra("couny", "");
			setResult(RESULT_OK, intent);
			finish();
		}

	}


	protected void initViews() {
		// TODO Auto-generated method stub
		backBtn = (ImageView) findViewById(R.id.back);
		lv = (ListView) findViewById(R.id.lv);
		backBtn.setOnClickListener(this);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				city = city_map.get(city_key).get(position).getCity_name();
				Intent intent = new Intent();
				intent.setClass(MallSelectCity.this, MallSelectRegional.class);
				intent.putExtra("regional_key",
						city_map.get(city_key).get(position).getId());
				startActivityForResult(intent, SELECT_COUNY_FOR_RESULT);

			}
		});
	}

	

	// 获取城市信息
	private void getaddressinfo() {
		// TODO Auto-generated method stub
		// 读取城市信息string
		JSONParser parser = new JSONParser();
		//String area_str = MyFileUtils.readAssets(getApplicationContext(),"area.json");
		// province_list = parser.getJSONParserResult(area_str, "area0");
		// citycodeUtil.setProvince_list_code(parser.province_list_code);
		//city_map = parser.getJSONParserResultArray(area_str, "area1"); 
		String area_str = sp.getString("area_string", "");  
		city_map = parser.getJSONParserResultArray(area_str, "area1"); 
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case SELECT_COUNY_FOR_RESULT:
				String couny = data.getStringExtra("couny");
				Intent intent = new Intent();
				intent.putExtra("city", city);
				intent.putExtra("couny", couny);
				setResult(RESULT_OK, intent);
				finish();
				break;
			default:
				break;
			}
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == backBtn) {
			finish();
		}
	}

	
}
