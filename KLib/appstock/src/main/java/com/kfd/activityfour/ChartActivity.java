package com.kfd.activityfour;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class ChartActivity extends BaseActivity implements OnClickListener {

	TextView titleTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuzhi);
		intent = getIntent();
		getViewId();

		String title = getIntent().getStringExtra("chat");
		titleTextView = (TextView) findViewById(R.id.title_text);

		if (title != null) {
			titleTextView.setText(title);
		} else {
			titleTextView.setText("");
		}
		if (intent.getStringExtra("url") != null) {
			url = intent.getStringExtra("url");
		}
	}

	protected void onResume() {
		super.onResume();
		
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
//			 e.printStackTrace();
		 }
		
		if (url.endsWith("__TOKEN==") && token.equals(""))// 未登录
		{
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return;
		}
		else if(url.endsWith("__TOKEN=="))
		{
			url = url + token;
		}
		
		loadData();
	};

	ImageView back_iv;
	private String url;
	private String showtype;
	private WebView webView;
	private ProgressBar mProgressBar;
	private String title;
	private Intent intent;
	private ImageView backIV, webpage_back_iv, webpage_forward_iv, webpage_refresh_iv, opermore_iv;
	private TextView titleNameTV, close_tv, cancel_web_tv;
	private RelativeLayout bottom_cancel, bottom_rl;

	private void loadData() {
		showDialog("请稍候...");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();

				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-user-main/tologin", hashMap);
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
					/**
					 * "name": "PHPSESSID",
					 * 
					 * "value": "i2ct2fvmi55dlt6n7pi836d8e1"
					 */
					/*
					 * String url =data.optString("url"); if
					 * (!StringUtils.isEmpty(url)) { //url =
					 * ZhiBoActivity.addCookie2Url(ZhiBoActivity.this, url);
					 * webView.loadUrl(url);
					 * 
					 * }
					 */
					String name = data.optString("name");
					String value = data.optString("value");

					StringBuffer sb = new StringBuffer();

					sb.append(name).append('=').append(value).append(';').append(' ');

					CookieSyncManager.createInstance(context);
					CookieManager cookieManager = CookieManager.getInstance();
					cookieManager.setAcceptCookie(true);
					cookieManager.removeSessionCookie();// 移除

					cookieManager.setCookie(url, sb.toString());// cookies是在HttpClient中获得的cookie
					CookieSyncManager.getInstance().sync();
					webView.loadUrl(url);

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

	public void getViewId() {

		webView = (WebView) findViewById(R.id.details_webview);
		webView.getSettings().setJavaScriptEnabled(true);

		// webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);

		webView.setWebChromeClient(new ChromeClient());
		webView.setWebViewClient(new WebViewClient());
		webView.addJavascriptInterface(null, "game");
		webView.clearCache(true);
		webView.setWebViewClient(new WebClient());
		mProgressBar = (ProgressBar) findViewById(R.id.details_progressbar);
		// url = intent.getStringExtra("url");
		if (intent.hasExtra("chat")) {
			title = intent.getStringExtra("chat");
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
			if (!TextUtils.isEmpty(url) && url.endsWith("apk")) {
				Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(viewIntent);
			} else {

				url = ChartActivity.addCookie2Url(ChartActivity.this, url);
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

			synCookies(activity, url);
		}

		return url;
	}

	/**
	 * 同步一下cookie
	 */
	private static void synCookies(Context context, String url) {
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

			webView.goBack();
			return true;
		} else {
			this.finish();
		}
		return false;
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
				Toast.makeText(ChartActivity.this, "最后一页了！", Toast.LENGTH_SHORT).show();
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

}
