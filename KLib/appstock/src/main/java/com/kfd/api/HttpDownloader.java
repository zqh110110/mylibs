package com.kfd.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * 
 * 版本信息 V1.0
 * 
 */
public class HttpDownloader {
	/**
	 * POST方式 直接解析urlStr地址的XML数据,返回String类型的字符串
	 * 
	 * @param urlStr
	 * @return
	 */
	public static String download(String urlStr) {
		String strResult = "没有搜索到内容！";
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urlStr);
		System.out.println("获得httpGet对象");
		try {
			HttpResponse response = httpClient.execute(httpGet);
			System.out.println("发送request请求");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("得到response回应");
				strResult = EntityUtils.toString(response.getEntity());
				System.out.println("查询结果:" + strResult + "---->");
			} else {
				System.out.println("没有得到网站回应");
				Log.i("GET", "bad request");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}

	/**
	 * POST方式(JSON类型数据)
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String downloadFromJson(String url) throws Exception {
		StringBuilder sb = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();

		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpResponse response = client.execute(new HttpGet(url));

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), "UTF-8"), 8192);
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
		}
		System.out.println("get json data:" + sb.toString());
		return sb.toString();
	}

	/**
	 * GET方式 直接解析urlStr地址的XML数据,返回InputStream类型数据
	 * 
	 * @param urlStr
	 * @return
	 */
	public static InputStream downloadtoInputStream(String urlStr) {

		InputStream is = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			is = conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return is;
	}

}
