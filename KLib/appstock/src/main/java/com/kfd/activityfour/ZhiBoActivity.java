package com.kfd.activityfour;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kfd.api.HttpRequest;
import com.kfd.api.Tools;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

public class ZhiBoActivity extends BaseActivity implements OnClickListener {

	private WebView webView;
	private ProgressBar mProgressBar;
	private String type, title;
	private Intent intent;
	private ImageView backIV, webpage_back_iv, webpage_forward_iv, webpage_refresh_iv, opermore_iv;
	private TextView titleNameTV, close_tv, cancel_web_tv;
	private RelativeLayout bottom_cancel, bottom_rl;

	// 是否需要重新加载
	public boolean isNeedReload = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhibo);

		intent = getIntent();
		getViewId();
		// loadData();

		findViewById(R.id.moreimageView1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (arrayList != null && arrayList.size() > 0) {
					CharSequence[] charSequences = new CharSequence[arrayList.size()];
					for (int i = 0; i < arrayList.size(); i++) {
						charSequences[i] = arrayList.get(i).getName();
					}
					AlertDialog alertDialog = new AlertDialog.Builder(ZhiBoActivity.this).setTitle("请选择直播间").setItems(charSequences, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String url = arrayList.get(which).getUrl();
							if (!StringUtils.isEmpty(url)) {

								webView.loadUrl(url);

							}

						}
					}).create();
					alertDialog.show();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isNeedReload) {
			loadRoomData();
			isNeedReload = false;
		}
	}

	private void loadRoomData() {

		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();

				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-main/room", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 2;
					message.obj = result;
					handler.sendMessage(message);
				} else {
					handler.sendEmptyMessage(3);
				}
			}
		});
	}

	class RoomInfo {
		private String name, isauto, url;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIsauto() {
			return isauto;
		}

		public void setIsauto(String isauto) {
			this.isauto = isauto;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

	ArrayList<RoomInfo> arrayList = new ArrayList<ZhiBoActivity.RoomInfo>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case 2:

				String resultsecond = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(resultsecond);
					JSONObject data = jsonObject.optJSONObject("data");
					JSONArray array = data.optJSONArray("list");
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonObject2 = array.optJSONObject(i);
						RoomInfo roomInfo = new RoomInfo();
						roomInfo.setIsauto(jsonObject2.optString("isauto"));
						roomInfo.setName(jsonObject2.optString("name"));
						roomInfo.setUrl(jsonObject2.optString("url"));
						arrayList.add(roomInfo);
					}
					for (int j = 0; j < arrayList.size(); j++) {
						RoomInfo roomInfo = arrayList.get(j);
						if (roomInfo.getIsauto().equals("1")) {
							String url = roomInfo.getUrl();
							if (!StringUtils.isEmpty(url)) {

								webView.loadUrl(url);

							}
						}
					}
					if (arrayList.size() == 0) {
						loadData();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 3:

				break;
			default:
				break;
			}
		};
	};

	private void loadData() {
		showDialog("请稍候...");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();

				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-main/chat", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 2;
					message.obj = result;
					updateUIHandler.sendMessage(message);
				} else {
					updateUIHandler.sendEmptyMessage(3);
				}
			}
		});
	}

	private Handler updateUIHandler = new Handler() {
		public void handleMessage(Message msg) {

			dismissDialog();
			switch (msg.what) {

			case 2:

				String resultsecond = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(resultsecond);
					JSONObject data = jsonObject.optJSONObject("data");
					String url = data.optString("url");
					if (!StringUtils.isEmpty(url)) {
						// url = ZhiBoActivity.addCookie2Url(ZhiBoActivity.this,
						// url);
						webView.loadUrl(url);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 3:

				break;
			default:
				break;
			}
		};
	};

	@SuppressLint("JavascriptInterface")
	public void getViewId() {

		webView = (WebView) findViewById(R.id.details_webview);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);

		webView.setWebChromeClient(new ChromeClient());
		webView.setWebViewClient(new WebViewClient());
		webView.addJavascriptInterface(new JavaScriptInterface(), "Methods");
		webView.clearCache(true);
		webView.setWebViewClient(new WebClient());
		mProgressBar = (ProgressBar) findViewById(R.id.details_progressbar);
		// url = intent.getStringExtra("url");
		type = intent.getStringExtra("type");
		if (intent.hasExtra("title")) {
			title = intent.getStringExtra("title");
			if (!TextUtils.isEmpty(title) && title.length() > 8) {
				title = title.substring(0, 8) + "...";
			}
		}
		//
		// url = ZhiBoActivity.addCookie2Url(ZhiBoActivity.this, url);
		// webView.loadUrl(url);

		// webView.getSettings().setBuiltInZoomControls(true);// 会出现放大缩小的按钮
		// webView.getSettings().setSupportZoom(true);
		backIV = (ImageView) findViewById(R.id.back_iv);
		backIV.setOnClickListener(this);
		titleNameTV = (TextView) findViewById(R.id.title_text);
		titleNameTV.setText(title);
		webpage_back_iv = (ImageView) findViewById(R.id.webpage_back_iv);
		webpage_back_iv.setOnClickListener(this);
		webpage_forward_iv = (ImageView) findViewById(R.id.webpage_forward_iv);
		webpage_forward_iv.setOnClickListener(this);
		webpage_refresh_iv = (ImageView) findViewById(R.id.webpage_refresh_iv);
		webpage_refresh_iv.setOnClickListener(this);
		close_tv = (TextView) findViewById(R.id.close_tv);
		close_tv.setOnClickListener(this);
		opermore_iv = (ImageView) findViewById(R.id.opermore_iv);
		opermore_iv.setOnClickListener(this);
		cancel_web_tv = (TextView) findViewById(R.id.cancel_web_tv);
		cancel_web_tv.setOnClickListener(this);
		bottom_cancel = (RelativeLayout) findViewById(R.id.bottom_cancel);
		bottom_rl = (RelativeLayout) findViewById(R.id.bottom_rl);
	}

	class ChromeClient extends WebChromeClient {

		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			super.onShowCustomView(view, callback);
			Log.e("tag", "on Show Custom View >>>>>webView");

		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			mProgressBar.setProgress(newProgress);
			if (newProgress == mProgressBar.getMax()) {
				mProgressBar.setVisibility(View.GONE);
			} else {
				mProgressBar.setVisibility(View.VISIBLE);
			}
		}

	}

	class WebClient extends WebViewClient {

		/*
		 * @Override public void onLoadResource(WebView view, String url) {
		 * super.onLoadResource(view, url); Log.v("tag", "url = " + url); }
		 * 
		 * @Override public void onReceivedError(WebView view, int errorCode,
		 * String description, String failingUrl) { super.onReceivedError(view,
		 * errorCode, description, failingUrl); }
		 */

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			if (!processCustomUrl(ZhiBoActivity.this, url, null, false)) {
				// url = ZhiBoActivity.addCookie2Url(ZhiBoActivity.this, url);
				// view.loadUrl(url);
				return true;
			} else {

				url = ZhiBoActivity.addCookie2Url(ZhiBoActivity.this, url);
				view.loadUrl(url);

			}
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			if (TextUtils.isEmpty(title)) {
				title = view.getTitle();
				if (!TextUtils.isEmpty(title) && title.length() > 8) {
					title = title.substring(0, 8) + "...";
				}
				titleNameTV.setText(title);
			}
			super.onPageFinished(view, url);
		}

	}

	public static String addCookie2Url(Activity activity, String url) {
		if (url != null) {
			String t_skey = "";
			CookieStore cs = com.kfd.api.Tools.getCookie(activity);
			if (cs != null) {
				List<Cookie> list = cs.getCookies();
				if (list != null) {
					for (Cookie cookie : list) {
						String name = cookie.getName();
						if ("t_skey".equals(name)) {
							t_skey = "t_skey=" + cookie.getValue();
						}
					}
				}

				if (!url.contains("?")) {
					url = url + "?" + t_skey;
				}
				if (url.contains("?") && url.contains("=")) {
					url = url + "&" + t_skey;
				}

				String market = "market=" + "android";
				if (!url.contains(market)) {
					if (!url.contains("?")) {
						url = url + "?" + market;
					}
					if (url.contains("?") && url.contains("=")) {
						url = url + "&" + market;
					}
				}

				String imei = "device_id=" + Tools.getIMEI(activity);
				if (!url.contains(imei)) {
					if (!url.contains("?")) {
						url = url + "?" + imei;
					}
					if (url.contains("?") && url.contains("=")) {
						url = url + "&" + imei;
					}
				}
			}

			try {
				synCookies(activity, url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return url;
	}

	/**
	 * 同步一下cookie
	 */
	private static void synCookies(Context context, String url) throws NullPointerException {
		List<Cookie> cookies = Tools.getCookie(context).getCookies();
		if (cookies != null) {
			StringBuffer sb = new StringBuffer();
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				String cookieValue = cookie.getValue();
				String cookieDomain = cookie.getDomain();

				sb.append(cookieName).append('=').append(cookieValue).append(';').append(' ');

				// sb.append(sessionCookie.getName() + "=" +
				// sessionCookie.getValue() + "; domain=" +
				// sessionCookie.getDomain());
			}

			CookieSyncManager.createInstance(context);
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.setAcceptCookie(true);
			cookieManager.removeSessionCookie();// 移除

			/*
			 * Cookie sessionCookie = null; if (sessionCookie != null) {
			 * cookieManager.removeSessionCookie(); String cookieString =
			 * sessionCookie.getName() + "=" + sessionCookie.getValue() +
			 * "; domain=" + sessionCookie.getDomain();
			 * cookieManager.setCookie(url, cookieString);
			 * CookieSyncManager.getInstance().sync(); }
			 */

			cookieManager.setCookie(url, sb.toString());// cookies是在HttpClient中获得的cookie
			CookieSyncManager.getInstance().sync();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			/*
			 * if ("微信".equals(type)) { this.finish(); }
			 */
			webView.goBack();
			return true;
		} else {
			if ((keyCode == KeyEvent.KEYCODE_BACK)) {
				AlertDialog alertDialog = new AlertDialog.Builder(ZhiBoActivity.this).setMessage("是否退出应用?").setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
						ActivityManager.popall();
						System.exit(0);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).create();
				alertDialog.show();
				return true;
			}

		}
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == backIV) {// 返回--改为--后退
			// this.finish();
			if (webView.canGoBack()) {
				webView.goBack();
			} else {
				// Toast.makeText(WebActivity.this, "最前一页了！",
				// Toast.LENGTH_SHORT)
				// .show();
				finish();
			}
		} else if (v == webpage_back_iv) {// 后退
			if (webView.canGoBack()) {
				webView.goBack();
			} else {
				// Toast.makeText(WebActivity.this, "最前一页了！",
				// Toast.LENGTH_SHORT).show();
				finish();
			}

		} else if (v == webpage_forward_iv) {// 向前
			if (webView.canGoForward()) {
				webView.goForward();
			} else {
				Toast.makeText(ZhiBoActivity.this, "最后一页了！", Toast.LENGTH_SHORT).show();
			}
		} else if (v == webpage_refresh_iv) {// 刷新
			webView.reload();
		} else if (v == close_tv) {// 关闭
			this.finish();
		} else if (v == opermore_iv) {// 更多--改为--刷新
			webView.reload();
			loadData();
			// if (bottom_rl.getVisibility() == View.VISIBLE) {
			// bottom_rl.setVisibility(View.GONE);
			// } else {
			// bottom_rl.setVisibility(View.VISIBLE);
			// }
			// if (bottom_cancel.getVisibility() == View.VISIBLE) {
			// bottom_cancel.setVisibility(View.GONE);
			// } else {
			// bottom_cancel.setVisibility(View.VISIBLE);
			// }
		} else if (v == cancel_web_tv) {// 取消(隐藏)
			bottom_rl.setVisibility(View.GONE);
			bottom_cancel.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * @description 可以通过
	 *              document.location="objc://goToOCPage?typeid=1&typevalue=8125145"
	 *              ; 类似于上面这样的 JS 语句调用 Java 的函数 说明 备注 objc:// 全屏网页控件识别的域
	 *              goToOCPage OC函数名称 typeid=1&typevalue=8125145 键值参数 注
	 *              1：全屏控件才会识别这些函数，其他网页控件不会识别 注 2：注意大小写，包括参数键值 注
	 *              3：打开这些链接后全屏控件自动关闭
	 * @author 张清田
	 * @update 2014年9月2日 下午8:14:25
	 */
	public static boolean processCustomUrl(Activity activity, String url, String refer, boolean isFromSign) {

		if (TextUtils.isEmpty(url) || !url.startsWith("objc://") || !url.contains("goToOCPage")) {
			return false;
		}
		int typeid = getParameterAsInt(url, "typeid");
		String typevalue = getParameter(url, "typevalue");

		Intent intent = null;

		if (typeid == 1 && typevalue.equals("001"))// 登录
		{
			// 登录
			intent = new Intent(activity, LoginActivity.class);
			activity.startActivity(intent);
			((ZhiBoActivity) activity).isNeedReload = true;
		}
		if (typeid == 1 && typevalue.equals("002"))// 注册
		{
			// 注册
			intent = new Intent(activity, RegisterActivity.class);
			activity.startActivity(intent);
			((ZhiBoActivity) activity).isNeedReload = true;
		}
		return true;
	}

	private static int getParameterAsInt(String url, String name) {
		int value = -1;
		String v = getParameter(url, name);
		if (!TextUtils.isEmpty(v)) {
			try {
				value = Integer.parseInt(v);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * @description 获取url中name对应的value参数
	 * @author 张清田
	 * @update 2014年9月2日 下午5:32:46
	 */
	private static String getParameter(String url, String name) {
		String value = "";
		try {
			List<NameValuePair> parameters = URLEncodedUtils.parse(new URI(url), "utf-8");
			if (name != null && parameters != null) {
				for (NameValuePair p : parameters) {
					String v = p.getValue();
					String n = p.getName();

					if (name.equals(n)) {
						if (!TextUtils.isEmpty(v)) {
							value = v;
							break;
						}
					}
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public class JavaScriptInterface {
		public void getHTML(String html) {
			System.out.println("**********");
		}
	}
}
