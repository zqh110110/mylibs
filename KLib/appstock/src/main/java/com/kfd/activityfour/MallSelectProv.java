/*
 * Copyright (c) 2014 The MaMaHelp_small_withReceiver_6.0.0 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */

package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kfd.adapter.MallSelectProvAdapter;
import com.kfd.bean.Cityinfo;



/**
 * @Function 选择省份
 * @author xiaobo.lin
 * @version
 * @Date: 2014年8月7日 上午10:37:58
 */
public class MallSelectProv extends BaseActivity implements  OnClickListener {
	private List<Cityinfo> province_list = new ArrayList<Cityinfo>();
	public final static int SELECT_CITY_FOR_RESULT = 0x100 + 911;
	private ImageView backbtn;
	private ListView lv;
	String prov = "";
	private String tag = "MallSelectProv";
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmall_mall_select_prov);
		initViews();
		getaddressinfo();
		MallSelectProvAdapter adapter = new MallSelectProvAdapter(
				province_list, MallSelectProv.this);
		lv.setAdapter(adapter);
	}


	protected void initViews() {
		// TODO Auto-generated method stub
		backbtn = (ImageView) findViewById(R.id.back);
		backbtn.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.lv);
		sp = PreferenceManager.getDefaultSharedPreferences(MallSelectProv.this);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				prov = province_list.get(position).getCity_name();
				Intent intent = new Intent();
				intent.setClass(MallSelectProv.this, MallSelectCity.class);
				intent.putExtra("city_key", province_list.get(position).getId());
				startActivityForResult(intent, SELECT_CITY_FOR_RESULT);
			}
		});
	}

	@Override
	public void onClick(View v) {
		
		if (v == backbtn) {
			finish();
		}
	}

	// 获取城市信息
	private void getaddressinfo() {
		// TODO Auto-generated method stub
		// 读取城市信息string
		JSONParser parser = new JSONParser();
		String area_str = sp.getString("area_string", ""); 
		province_list = parser.getJSONParserResult(area_str, "area0");
		// citycodeUtil.setProvince_list_code(parser.province_list_code);
		// city_map = parser.getJSONParserResultArray(area_str, "area1");
		// System.out.println("city_mapsize" +
		// parser.city_list_code.toString());
		// citycodeUtil.setCity_list_code(parser.city_list_code);
		// couny_map = parser.getJSONParserResultArray(area_str, "area2");
		// citycodeUtil.setCouny_list_code(parser.city_list_code);
		// System.out.println("couny_mapsize" +
		// parser.city_list_code.toString());
		// Logcat.v(province_list.toString());
		// Logcat.v(city_map.toString());
		// Logcat.v(couny_map.toString());
	}

	public static class JSONParser {
		public ArrayList<String> province_list_code = new ArrayList<String>();
		public ArrayList<String> city_list_code = new ArrayList<String>();

		public List<Cityinfo> getJSONParserResult(String JSONString, String key) {
			List<Cityinfo> list = new ArrayList<Cityinfo>();
			JsonObject result = new JsonParser().parse(JSONString)
					.getAsJsonObject().getAsJsonObject(key);

			Iterator iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator
						.next();
				Cityinfo cityinfo = new Cityinfo();

				cityinfo.setCity_name(entry.getValue().getAsString());
				cityinfo.setId(entry.getKey());
				province_list_code.add(entry.getKey());
				list.add(cityinfo);
			}
			System.out.println(province_list_code.size());
			return list;
		}

		public HashMap<String, List<Cityinfo>> getJSONParserResultArray(
				String JSONString, String key) {
			HashMap<String, List<Cityinfo>> hashMap = new HashMap<String, List<Cityinfo>>();
			JsonObject result = new JsonParser().parse(JSONString)
					.getAsJsonObject().getAsJsonObject(key);

			Iterator iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator
						.next();
				List<Cityinfo> list = new ArrayList<Cityinfo>();
				JsonArray array = entry.getValue().getAsJsonArray();
				for (int i = 0; i < array.size(); i++) {
					Cityinfo cityinfo = new Cityinfo();
					cityinfo.setCity_name(array.get(i).getAsJsonArray().get(0)
							.getAsString());
					cityinfo.setId(array.get(i).getAsJsonArray().get(1)
							.getAsString());
					city_list_code.add(array.get(i).getAsJsonArray().get(1)
							.getAsString());
					list.add(cityinfo);
				}
				hashMap.put(entry.getKey(), list);
			}
			return hashMap;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case SELECT_CITY_FOR_RESULT:
				String city = data.getStringExtra("city");
				String couny = data.getStringExtra("couny");
				Intent intent = new Intent();
				intent.putExtra("city", city);
				intent.putExtra("couny", couny);
				intent.putExtra("prov", prov);
				setResult(RESULT_OK, intent);
				finish();
				break;
			default:
				break;
			}
		}
	}

}
