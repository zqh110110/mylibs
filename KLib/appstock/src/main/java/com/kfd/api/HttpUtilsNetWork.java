package com.kfd.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpUtilsNetWork {
	public static final int GET = 0;
	public static final int POST = 1;
	public static final int PUT = 2;
	public static final int DELETE = 3;
	public static final int BITMAP = 4;

	public static final int TIMEOUT = 5000;// 默认超时时间

	private static DefaultHttpClient getClient(int timeout) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				timeout);
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), timeout);
		return httpClient;
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream get(String url) throws Exception {
		return get(url, false, TIMEOUT);
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream get(String url, boolean gzip) throws Exception {
		return get(url, gzip, TIMEOUT);
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream get(String url, boolean gzip, int timeout)
			throws Exception {
		return doAction(GET, url, null, gzip, timeout);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static InputStream post(String url, List<NameValuePair> params)
			throws Exception {
		if (params == null) {
			return null;
		}
		return post(url, params, false, TIMEOUT);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param params
	 * @param gzip
	 * @return
	 * @throws Exception
	 */
	public static InputStream post(String url, List<NameValuePair> params,
			boolean gzip) throws Exception {
		if (params == null) {
			return null;
		}
		return post(url, params, gzip, TIMEOUT);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param params
	 * @param gzip
	 * @return
	 * @throws Exception
	 */
	public static InputStream post(String url, List<NameValuePair> params,
			boolean gzip, int timeout) throws Exception {
		if (params == null) {
			return null;
		}
		return doAction(POST, url, params, gzip, timeout);
	}

	/**
	 * PUT请求
	 * 
	 * @param url
	 * @param pairs
	 * @return
	 * @throws Exception
	 */
	public static InputStream put(String url, List<NameValuePair> pairs)
			throws Exception {
		if (pairs == null) {
			return null;
		}
		return put(url, pairs, false, TIMEOUT);
	}

	/**
	 * PUT请求
	 * 
	 * @param url
	 * @param pairs
	 * @return
	 * @throws Exception
	 */
	public static InputStream put(String url, List<NameValuePair> pairs,
			boolean gzip) throws Exception {
		if (pairs == null) {
			return null;
		}
		return put(url, pairs, gzip, TIMEOUT);
	}

	/**
	 * PUT请求
	 * 
	 * @param url
	 * @param pairs
	 * @return
	 * @throws Exception
	 */
	public static InputStream put(String url, List<NameValuePair> pairs,
			boolean gzip, int timeout) throws Exception {
		if (pairs == null) {
			return null;
		}
		return doAction(PUT, url, pairs, gzip, timeout);
	}

	/**
	 * DELETE请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream delete(String url) throws Exception {
		return delete(url, false, TIMEOUT);
	}

	/**
	 * DELETE请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream delete(String url, boolean gzip) throws Exception {
		return delete(url, gzip, TIMEOUT);
	}

	/**
	 * DELETE请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream delete(String url, boolean gzip, int timeout)
			throws Exception {
		return doAction(DELETE, url, null, gzip, timeout);
	}

	/**
	 * 下载图片
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream bitmap(String url) throws Exception {
		return bitmap(url, false, TIMEOUT);
	}

	/**
	 * 下载图片
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream bitmap(String url, boolean gzip) throws Exception {
		return bitmap(url, gzip, TIMEOUT);
	}

	/**
	 * 下载图片
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream bitmap(String url, boolean gzip, int timeout)
			throws Exception {
		return doAction(BITMAP, url, null, gzip, timeout);
	}

	/**
	 * 将流转为字符串
	 * 
	 * @param ins
	 * @return
	 */
	public static StringBuffer streamToString(InputStream ins) {
		if (ins == null) {
			return null;
		}
		InputStreamReader br = new InputStreamReader(ins);
		StringBuffer result = new StringBuffer();
		char[] buf = new char[1024 * 4];
		int r = 0;
		try {
			while ((r = br.read(buf)) > 0)
				result.append(buf, 0, r);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将流转为图片
	 * 
	 * @param ins
	 * @return
	 */
	public static Bitmap streamToBitmap(InputStream ins) {
		if (ins == null) {
			return null;
		}
		Bitmap bm = BitmapFactory.decodeStream(ins);
		return bm;
	}

	public static InputStream doAction(int method, String url,
			List<NameValuePair> data, boolean gzip, int timeout)
			throws Exception {
		HttpResponse response = null;
		BufferedHttpEntity bufferedHttpEntity = null;
		HttpUriRequest uriRequest = null;
		try {
			switch (method) {
			case GET:
				uriRequest = new HttpGet(url);
				break;
			case POST:
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
				uriRequest = httpPost;
				break;
			case PUT:
				HttpPut httpPut = new HttpPut(url);
				httpPut.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
				uriRequest = httpPut;
				break;
			case DELETE:
				uriRequest = new HttpDelete(url);
				break;
			case BITMAP:
				uriRequest = new HttpGet(url);
				break;
			}
			uriRequest.addHeader("Accept-Encoding", "gzip");
			response = getClient(timeout).execute(uriRequest);

			if (response.getStatusLine().getStatusCode() == 200) {
				if (method < BITMAP) {
					org.apache.http.Header contentEncoding = response
							.getFirstHeader("Content-Encoding");
					InputStream ins = response.getEntity().getContent();
					if (contentEncoding != null
							&& gzip
							&& contentEncoding.getValue().equalsIgnoreCase(
									"gzip")) {
						return new GZIPInputStream(ins);
					} else {
						return ins;
					}
				}
			} else {
				bufferedHttpEntity = new BufferedHttpEntity(
						response.getEntity());
				return bufferedHttpEntity.getContent();
			}
		} catch (Exception ex) {
		}
		return null;
	}
}
