package com.kfd.activityfour;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.kfd.adapter.SelectTeacherAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.TeacherBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

public class SelectTeacherActivity extends BaseActivity {
	
	private PtrFrameLayout mPtrFrameLayout;

	private ListView mListView;
	
	SelectTeacherAdapter adapter;
	private ArrayList<TeacherBean> arrayList = new ArrayList<TeacherBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectactivity);
		initTitle("选择老师");
		
		
		// pull to refresh
		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_list_view_ptr_frame);

		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

				// here check list view, not content.
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				arrayList.clear();
				loadData();
			}
		});

		mListView = (ListView) findViewById(R.id.load_more_small_image_list_view);
		
		adapter = new SelectTeacherAdapter(arrayList, SelectTeacherActivity.this, getLayoutInflater(), SelectTeacherActivity.this);
		mListView.setAdapter(adapter);
		showDialog("请稍候...");
		loadData();
	}

	private int postion;

	public void selectTeacher(final String tid, int postion) {
		this.postion = postion;
		showDialog("请稍候...");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("id", tid);
				String result = HttpRequest.sendGetRequestWithMd5(SelectTeacherActivity.this, Define.host + "/api-find-vip/choose", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 1;
					message.obj = result;
					selecthandler.sendMessage(message);
				} else {
					selecthandler.sendEmptyMessage(0);
				}
			}
		});
	}

	private Handler selecthandler = new Handler() {
		public void handleMessage(Message msg) {
			dismissDialog();

			switch (msg.what) {
			case 1:
				String resulString = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(resulString);
					String ret = jsonObject.optString("ret");
					String message = jsonObject.optString("msg");
					if (ret.equals("0")) {
						TeacherBean teacherBean = (TeacherBean) adapter.getItem(postion);
						teacherBean.setIschoose("1");
						adapter.notifyDataSetChanged();
						loadData();
					} else {
						showToast(message);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				break;

			default:
				break;
			}
		};
	};

	private void loadData() {

		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(SelectTeacherActivity.this, Define.host + "/api-find-vip/teacher", hashMap);
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
			mPtrFrameLayout.refreshComplete();
			switch (msg.what) {
			case 1:
				String resulString = (String) msg.obj;

				try {
					JSONObject jsonObject = new JSONObject(resulString);
					String ret = jsonObject.getString("ret");
					if (ret.equals("0")) {
						JSONObject jsonObject2 = jsonObject.optJSONObject("data");
						JSONArray listArray = jsonObject2.optJSONArray("list");
						arrayList.clear();
						for (int i = 0; i < listArray.length(); i++) {
							TeacherBean teacherBean = new TeacherBean();
							JSONObject jsonObject3 = listArray.getJSONObject(i);
							teacherBean.setChoose_time(jsonObject3.optString("choose_time"));
							teacherBean.setContent(jsonObject3.optString("content"));
							teacherBean.setFace(jsonObject3.optString("face"));
							teacherBean.setId(jsonObject3.optString("id"));
							teacherBean.setIschoose(jsonObject3.optString("ischoose"));
							teacherBean.setLevel(jsonObject3.optString("level"));
							teacherBean.setRealname(jsonObject3.optString("realname"));

							teacherBean.setUrl(jsonObject3.optString("url"));

							arrayList.add(teacherBean);
						}
						adapter.notifyDataSetChanged();
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
