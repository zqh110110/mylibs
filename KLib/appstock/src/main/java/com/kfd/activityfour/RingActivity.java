package com.kfd.activityfour;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.kfd.api.HttpRequest;
import com.kfd.common.Define;
import com.kfd.common.LogUtils;
import com.kfd.common.StringUtils;

import u.aly.s;
import u.aly.t;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class RingActivity  extends BaseActivity{
	
	private void setdata(){
		//：/api-user-main/getconfig
	final	String up_sound   =uptextview.getText().toString().trim()=="静音"?"1":"0";
	final  String  shock;
	final	String  down_sound   =downTextView.getText().toString().trim()=="静音"?"1":"0";
	if (toggleButton.isChecked()) {
		shock="1";
	}else {
		shock="0";
	}
	final  String  istime;
	if (toggleButton2.isChecked()) {
		istime="1";
	}else {
		istime="0";
	}
	final   String  stime=   starttimetextView.getText().toString().trim();
	final   String  etime=   endtimetextView.getText().toString().trim();
	final   String  notice_time=   secondtextView.getText().toString().trim();
	showDialog("请稍候...");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				try {
					
					
					hashMap.put("up_sound", up_sound);
					hashMap.put("down_sound", down_sound);
					hashMap.put("shock", shock);
					hashMap.put("istime", istime);
					hashMap.put("notice_time", notice_time);
					hashMap.put("etime", etime);
					hashMap.put("stime",stime);
					String result  = HttpRequest.sendPostRequestWithMd5(RingActivity.this,Define.host+"/api-user-main/dosound", hashMap);
					LogUtils.v("test", "result--- "+result);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						sethandler.sendMessage(message);
					}else {
						Message message = new Message();
						message.what = 0;
						message.obj = "连接超时，请重试";
						sethandler.sendMessage(message);
						//handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		
	}
	
	private Handler  sethandler  =  new  Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			switch (msg.what) {
			case 0:
				//showToast("账号或者密码错误，请重试");
				String message=(String) msg.obj;
				showToast(message);
				break;
			case 1:
				String result= (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret  = jsonObject.getString("ret");
					String msgdata  = jsonObject.getString("msg");
					
					if (ret.equals("0")) {
						showToast("设置成功 ！");
						
						
					
					}else {
						showToast(msgdata);
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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ring);
		initTitle("铃声设置");
		initUI();
		findViewById(R.id.refresh).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setdata();
			}
		});
	findViewById(R.id.resetlayout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDefault();
			}
		});
	getging();
	}
	private void  getDefault(){
//api-user-main/auto
		showDialog("请稍候...");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				try {
			
					String result  = HttpRequest.sendGetRequestWithMd5(RingActivity.this,Define.host+"/api-user-main/auto", hashMap);
					LogUtils.v("test", "result--- "+result);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						handler.sendMessage(message);
					}else {
						Message message = new Message();
						message.what = 0;
						message.obj = "连接超时，请重试";
						handler.sendMessage(message);
						//handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	private Handler handler =new Handler(){
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 0:
				
				break;
			case 1:
				uptextview.setText(alarmset[1]);
				downTextView.setText(alarmset[1]);
				secondtextView.setText(alarmtime[3]);
				toggleButton.setChecked(true);
				toggleButton2.setChecked(true);
				starttimetextView.setText(timeset[0]);
				endtimetextView.setText(timeset[0]);
				break;
			default:
				break;
			}
		};
	};
	private void getging(){
		showDialog("请稍候...");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				try {
			
					String result  = HttpRequest.sendGetRequestWithMd5(RingActivity.this,Define.host+"/api-user-main/sound", hashMap);
					LogUtils.v("test", "result--- "+result);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						updateHandler.sendMessage(message);
					}else {
						Message message = new Message();
						message.what = 0;
						message.obj = "连接超时，请重试";
						updateHandler.sendMessage(message);
						//handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		
	}
	private Handler  updateHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			try {
				dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			switch (msg.what) {
			case 0:
				//showToast("账号或者密码错误，请重试");
				String message=(String) msg.obj;
				showToast(message);
				break;
			case 1:
				String result= (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret  = jsonObject.getString("ret");
					String msgdata  = jsonObject.getString("msg");
				
					if (ret.equals("0")) {
						JSONObject   data  = jsonObject.optJSONObject("data");
						JSONObject  config  =  data.optJSONObject("config");
						/**
						 * up_sound
 上涨铃声 1-静音 2-非静音
 
down_sound
 下跌铃声 1-静音 2-非静音
 
notice_time
 提醒时间
 
shock
 提醒时震动
 
istime
 提醒时间段 1-开启 0-关闭
 
stime
 开始时间
 
etime 

						 */
						String  up_sound   = config.getString("up_sound");
						String  down_sound   = config.getString("down_sound");
						String  notice_time   = config.getString("notice_time");
						String  shock   = config.getString("shock");
						String  istime   = config.getString("istime");
						String  stime   = config.getString("stime");
						String  etime   = config.getString("etime");
						if (up_sound.equals("1")) {
							uptextview.setText("静音");
						}else if (up_sound.equals("2")) {
							uptextview.setText("非静音");
						}
					
						if (down_sound.equals("1")) {
							downTextView.setText("静音");
						}else if (down_sound.equals("2")) {
							downTextView.setText("非静音");
						}
					
				
						secondtextView.setText(notice_time);
						if (shock.equals("1")) {
							toggleButton.setChecked(true);
						}else {
							toggleButton.setChecked(false);
						}
						
						
						if (istime.equals("1")) {
							toggleButton2.setChecked(true);
						}else {
							toggleButton2.setChecked(false);
						}
						
						starttimetextView.setText(stime);
						endtimetextView.setText(etime);
						
					}else {
						showToast(msgdata);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				break;
			
			default:
				break;
			}

		}
	};
	CharSequence[]   alarmset =  {"静音","非静音"};
	RelativeLayout upLayout,lowlayout,continuetimelayout,alarmshakelayout,alarmtimelayout,starttimelayout;
	ToggleButton toggleButton,toggleButton2;
	TextView  uptextview,downTextView,secondtextView,starttimetextView,endtimetextView;
	CharSequence[]   alarmtime =  {"5","15","25","60"};
	CharSequence[]   timeset =  {"00:00","08:00","10:00","12:00","14:00","15:00","20:00","23:00"};
	private void initUI(){
		toggleButton  = (ToggleButton) findViewById(R.id.switch1);
		toggleButton2 = (ToggleButton) findViewById(R.id.switch2);
	
		upLayout = (RelativeLayout) findViewById(R.id.upmsglayout);
		upLayout.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog alertDialog  = new AlertDialog.Builder(RingActivity.this).setTitle("请选择模式").setItems(alarmset, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						uptextview.setText(alarmset[which]);
					}
				}).create();
				alertDialog.show();
			}
		});
		lowlayout = (RelativeLayout) findViewById(R.id.lowlayout);
		lowlayout.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog alertDialog  = new AlertDialog.Builder(RingActivity.this).setTitle("请选择模式").setItems(alarmset, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						downTextView.setText(alarmset[which]);
					}
				}).create();
				alertDialog.show();
			}
		});
		continuetimelayout  = (RelativeLayout) findViewById(R.id.continuetimelayout);
		continuetimelayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog alertDialog  = new AlertDialog.Builder(RingActivity.this).setTitle("请选择提醒持续时间").setItems(alarmtime, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						secondtextView.setText(alarmtime[which]);
					}
				}).create();
				alertDialog.show();
			}
		});
		alarmshakelayout  = (RelativeLayout) findViewById(R.id.alarmshakelayout);
		alarmtimelayout =  (RelativeLayout) findViewById(R.id.alarmtimelayout);
		starttimelayout = (RelativeLayout) findViewById(R.id.starttimelayout);
		
		starttimelayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog alertDialog  = new AlertDialog.Builder(RingActivity.this).setTitle("请选择提醒开始时间 ").setItems(timeset, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						starttimetextView.setText(timeset[which]);
					}
				}).create();
				alertDialog.show();
			}
		});
		 uptextview  =  (TextView) findViewById(R.id.uptextView);
		 downTextView = (TextView) findViewById(R.id.downtextView);
		 secondtextView = (TextView) findViewById(R.id.secondtextView);
		 starttimetextView= (TextView) findViewById(R.id.starttimetextView);
		 endtimetextView  = (TextView) findViewById(R.id.endtimetextView);
		 endtimetextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog alertDialog  = new AlertDialog.Builder(RingActivity.this).setTitle("请选择提醒结束时间 ").setItems(timeset, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							endtimetextView.setText(timeset[which]);
						}
					}).create();
					alertDialog.show();
				}
			});
	}
}
