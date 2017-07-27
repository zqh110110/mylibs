package com.kfd.activityfour;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.kfd.api.AppContext;
import com.kfd.api.AppContext.MSGState;
import com.kfd.api.Tools;
import com.kfd.common.Define;
import com.kfd.common.Logcat;
import com.kfd.common.StringUtils;
import com.kfd.db.SharePersistent;

public class PushDemoReceiver extends BroadcastReceiver {
	
	private static int intent_id = 0;

	/**
	 * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView ==
	 * null)
	 */
	public static StringBuilder payloadData = new StringBuilder();
	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Bundle bundle = intent.getExtras();
		this.context = context;
		Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			// String appid = bundle.getString("appid");
			byte[] payload = bundle.getByteArray("payload");

			String taskid = bundle.getString("taskid");
			String messageid = bundle.getString("messageid");

			// smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
			boolean result = PushManager.getInstance().sendFeedbackMessage(
					context, taskid, messageid, 90001);
			System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

			if (payload != null) {
				String data = new String(payload);

				try {
					data = URLDecoder.decode(data, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				Map<String, String> dataMap = new HashMap<String, String>();

				String[] datas = data.split("&");
				for (int i = 0; i < datas.length; i++) {
					String[] keyValue = datas[i].split("=");
					dataMap.put(keyValue[0], keyValue[1]);
				}

				if (!StringUtils.isEmpty(data)) {
					showNotification(context, dataMap);
				}
				Log.d("GetuiSdkDemo", "receiver payload : " + data);

				payloadData.append(data);
				payloadData.append("\n");

				// if (GetuiSdkDemoActivity.tLogView != null) {
				// GetuiSdkDemoActivity.tLogView.append(data + "\n");
				// }
			}
			break;

		case PushConsts.GET_CLIENTID:
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			String cid = bundle.getString("clientid");
			SharePersistent.getInstance().savePerference(context, "clientid",
					cid);
			Logcat.v("test", "cid " + cid);
			// if (GetuiSdkDemoActivity.tView != null) {
			// GetuiSdkDemoActivity.tView.setText(cid);
			// }
			break;

		case PushConsts.THIRDPART_FEEDBACK:
			/*
			 * String appid = bundle.getString("appid"); String taskid =
			 * bundle.getString("taskid"); String actionid =
			 * bundle.getString("actionid"); String result =
			 * bundle.getString("result"); long timestamp =
			 * bundle.getLong("timestamp");
			 * 
			 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo",
			 * "taskid = " + taskid); Log.d("GetuiSdkDemo", "actionid = " +
			 * actionid); Log.d("GetuiSdkDemo", "result = " + result);
			 * Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
			 */
			break;

		default:
			break;
		}
	}

	// 通知管理器
	private static int NOTIFICATION_ID_BASE = 001;

	private void showNotification(Context context, Map<String, String> dataMap) {
		
		MSGState state = AppContext.getInstance().getMsgState();
		
		if(!state.isReceive)
		{
			return;
		}

		String msg = dataMap.get("msg");
		String type = dataMap.get("type");
		String id = dataMap.get("id");
		String sound = dataMap.get("sound");
		String time = dataMap.get("time");
		String shock = dataMap.get("shock");
		
		Intent intent = null;
		if(type != null && type.equals("1"))
		{
			intent = new Intent();	
		}
		else if(type != null && type.equals("3"))
		{
			 intent = new Intent(context, ChartActivity.class);	
			 
			 CookieStore cookieStore = Tools.getCookie(context);
			 
			 String token = "";
			 
			 try
			 {
				 Cookie cookie =cookieStore.getCookies().get(0);
				 token =  cookie.getValue();
				 
				 token = URLEncoder.encode(token, "UTF-8");
			 }
			 catch(Exception e)
			 {
//				 e.printStackTrace();
			 }
			 
			 String url = Define.host + "/m-pm?id=" + id + "&__TOKEN=" + token;
//			 try {
//				url = URLEncoder.encode(url, "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
			 String name = "";
			 try
			 {
				 String[] arr = msg.split(":");
				 name = arr[0];
			 }
			 catch(Exception e)
			 {}
			 intent.putExtra("chat", name);
			 intent.putExtra("url", url);
		}
		else
		{
			intent = new Intent(context, MainActivity.class);
		}
		intent_id ++;
		PendingIntent pi = PendingIntent.getActivity(context, intent_id , intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// 创建一个Notification
		Notification notify = new Notification();
		// 为notification设置图标
		notify.icon = R.drawable.ic_lanucher;
		// 为notification设置文本内容，该文本在标图兰中显示
		notify.tickerText = state.isShowContent ? msg : "";
		// 为notification设置s时间
		notify.when = System.currentTimeMillis();

		// 为notification设置声音
		if (type != null && type.equals("2") && sound != null
				&& sound.equals("2") && state.isSoundInv) {
			notify.defaults |= Notification.DEFAULT_SOUND;
			
//			notify.sound=Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alarm); 
		}

		if (type != null && type.equals("2") && shock != null
				&& shock.equals("1") && state.isVabInv) {
			long timeLong = 0;
			try {
				timeLong = Long.parseLong(time);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			notify.vibrate = new long[] { 0, 0, 0, timeLong * 1000 };
		}
		
		if(state.isFlashLightInv)
		{
			notify.defaults |= Notification.DEFAULT_LIGHTS;
		}

		// 为notification设置默认声音、默认震动、默认呼吸灯等
		notify.when = System.currentTimeMillis();

		notify.flags |= Notification.FLAG_AUTO_CANCEL;
		notify.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

//		notify.setLatestEventInfo(context, "凯福德", msg, pi);
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID_BASE++, notify);
	}

}
