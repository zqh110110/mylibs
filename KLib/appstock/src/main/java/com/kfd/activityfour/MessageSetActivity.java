package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.kfd.adapter.MsgSettingAdapter;
import com.kfd.api.AppContext;
import com.kfd.api.AppContext.MSGState;

/**
 * 消息通知设置
 * 
 * @author Administrator
 * 
 */
public class MessageSetActivity extends BaseActivity {

	private ListView mListView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messagelist);
		initTitle("消息通知");

		mListView = (ListView) findViewById(R.id.listView);
		initData();
	}

	private void initData() {
		
		MSGState state = AppContext.getInstance().getMsgState();
		List<ItemData> datas = new ArrayList<ItemData>();
		
		datas.add(new ItemData("开启通知", state.isReceive));
		datas.add(new ItemData("声音", state.isSoundInv));
		datas.add(new ItemData("震动", state.isVabInv));
		datas.add(new ItemData("通知显示消息内容", state.isShowContent));
		datas.add(new ItemData("通知时指示灯闪", state.isFlashLightInv));
		
		mListView.setAdapter(new MsgSettingAdapter(this, datas));
	}

	public class ItemData {
		public String title;
		public boolean isChecked;

		public ItemData(String title, boolean isChecked) {
			this.title = title;

			this.isChecked = isChecked;
		}
	}
}
