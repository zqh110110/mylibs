package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kfd.adapter.DynamicImageAdapter;
import com.kfd.adapter.FaceAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.ui.MyGridView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectFaceActivity  extends  BaseActivity {
	MyGridView gridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectface);
		initTitle("选择头像");
		gridView = (MyGridView) findViewById(R.id.fresh_news_photo_gv);
		loadData();
	}
	 private void loadData(){
		   executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				String  result  = HttpRequest.sendGetRequestWithMd5(SelectFaceActivity.this, Define.host+"/api-user-main/getface", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what=1;
					message.obj=  result;
					updatehandler.sendMessage(message);
				}else {
					updatehandler.sendEmptyMessage(0);
				}
			}
		});
	   }
	 private ArrayList<String>  arrayList   = new ArrayList<String>();
	 private Handler updatehandler =  new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				
				break;
			case 1:
				  String  resulString = (String) msg.obj;
				  try {
					  JSONObject jsonObject = new JSONObject(resulString);
						String ret  = jsonObject.getString("ret");
						if (ret.equals("0")) {
							JSONObject jsonObject2  =  jsonObject.getJSONObject("data");
							JSONArray jsonArray = jsonObject2.optJSONArray("face");
							arrayList.clear();
							for (int i = 0; i < jsonArray.length(); i++) {
								arrayList.add(jsonArray.getString(i));
							}
							}
						
						
						FaceAdapter  dynamicImageAdapter   = new FaceAdapter(context,arrayList,imageLoader);
	
						gridView.setAdapter(dynamicImageAdapter);
						gridView.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
								/*ArrayList<String>  urlArrayList =  new ArrayList<String>();
								for (int i = 0; i < investBean.getPicListsArrayList().size(); i++) {
									urlArrayList.add(investBean.getPicListsArrayList().get(i).getPicture());
								}
								DressUpBean  dressupbean =  new DressUpBean();
								dressupbean.setPic(urlArrayList);
								if (position < arrayList.size()) {
									Intent intent = new Intent(context,
											DressUpDetailBigImgActivity.class);
									intent.putExtra("dressUpBean",dressupbean);
									intent.putExtra("currSelectIndex", position);
									context.startActivity(intent);
								} else {
									Toast.makeText(context, "数据异常", 0).show();
								}*/
								Intent intent = new Intent();
								intent.putExtra("faceurl", arrayList.get(position));
								setResult(202, intent);
								finish();
							}
						});
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
