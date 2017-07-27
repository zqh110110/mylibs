package com.kfd.activityfour;



import com.flurry.android.FlurryAgent;
import com.kfd.activityfour.R;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.market.FuturesMarketActivity;
import com.kfd.market.MarketCenterActivity;
import com.kfd.ui.MyAnimations;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
/**
 * 代码说明，由于时间有限只有几天时间修改二版代码，所以我尽量简化，等待后来者优化功能，有不全的地方请不要吐槽
 * 我只写出 技术难点，细节问题请自行调节
 * write   朱继洋   QQ   7617812
 * @author Administrator
 *
 */
public class HomeActivity  extends   BaseActivity{
	
	private boolean areButtonsShowing=false;
	private RelativeLayout composerButtonsWrapper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		ActivityManager.push(this);
		initUI();
		//FlurryAgent.onStartSession(this, "Y2VB5XRBP7J68VCFKNQQ");  
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//FlurryAgent.onEndSession(this); 
	}
	
	private void initUI(){
		TextView  titleTextView =(TextView) findViewById(R.id.title_text);
		titleTextView.setText("道富投资");
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//传递数据    1.发广播   2.扔到内存中  Cache类中方法调用
				// TODO Auto-generated method stub
				/*Intent localIntent = new Intent();
				localIntent.setAction("com.broadcast.market");
				Bundle bundle = new Bundle();
				bundle.putString("type", "0");
				Cache.put("Activity_id", "0");
				localIntent.putExtras(bundle);
				HomeActivity.this.sendBroadcast(localIntent);*/
				
				//TabHost host = (TabHost) getParent().findViewById(
				//		android.R.id.tabhost);
				//host.setCurrentTab(1);
				
				HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
				parentActivity1.startChildActivity("MarketCenterActivity", new Intent(
						HomeActivity.this, MarketCenterActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				Cache.put("fromhome", "yes");
				TabHost host = (TabHost) Cache.getCache("host");
				if (host!=null) {
					host.setCurrentTab(1);
				}
				
				//Cache.put("type", "");
				//Cache.put("type", "");
			}
		});
		
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				//HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
				//parentActivity1.startChildActivity("FuturesMarketActivity", new Intent(
				//		HomeActivity.this, FuturesMarketActivity.class)
				//		.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				Cache.put("futures", "yes");
				TabHost host = (TabHost) Cache.getCache("host");
				if (host!=null) {
					host.setCurrentTab(1);
				}
			}
		});
		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	findViewById(R.id.button4).setOnClickListener(new OnClickListener() {
	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		});
	findViewById(R.id.button5).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
			parentActivity1.startChildActivity("AccountCenterActivity", new Intent(
					HomeActivity.this, AccountCenterActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		}
		});
		
	findViewById(R.id.imageView2).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
			parentActivity1.startChildActivity("AccountCenterActivity", new Intent(
					HomeActivity.this, AccountCenterActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)); 
		}
	});
	
	findViewById(R.id.imageView3).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
			parentActivity1.startChildActivity("SettingActivity", new Intent(
					HomeActivity.this, SettingActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		}
	});
	
	findViewById(R.id.eamil).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			HomeActivityGroup parentActivity1 = (HomeActivityGroup) getParent();
			parentActivity1.startChildActivity("EmailMessageActivity", new Intent(
					HomeActivity.this, EmailMessageActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		}
	});
	}
}
