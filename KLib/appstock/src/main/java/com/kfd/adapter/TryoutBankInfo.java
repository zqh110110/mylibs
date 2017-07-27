package com.kfd.adapter;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kfd.common.StringUtils;

import android.R.string;

public class TryoutBankInfo  implements Serializable {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public static  ArrayList<TryoutBankInfo>  parseInfo(String  result){
		 ArrayList<TryoutBankInfo>    arrayList  = new ArrayList<TryoutBankInfo>();
		 if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jsonObject  = new JSONObject(result);
				JSONObject  dataJsonObject  = jsonObject.optJSONObject("data");
				JSONArray  jsonArray  = dataJsonObject.optJSONArray("bank");
				for (int i = 0; i < jsonArray.length(); i++) {
					String  string  = jsonArray.optString(i);
					TryoutBankInfo tryoutBankInfo = new TryoutBankInfo();
					tryoutBankInfo.setName(string);
					arrayList.add(tryoutBankInfo);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		 return  arrayList;
	}
	
}
