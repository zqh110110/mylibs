package com.kfd.api;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.kfd.activityfour.R;
import com.kfd.common.Cache;
import com.kfd.common.Define;
import com.kfd.common.Logcat;
import com.kfd.db.SharePersistent;



import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;



/**
 * 数据获取类
 * 
 * @author 朱继洋 QQ7617812 2013-5-20
 */
public class HttpRequest {
	private static final int TIMEOUT =10000;// 10秒
	public static  final String mKey="f6f712249f4b725fac309504d633f839";
	static String TAG  ="HttpRequest";
	/**
	 * 
	 * @description 
	 * @author 
	 * @update 2014年9月26日 上午11:30:42
	 * @param context 上下文 为空字符串!
	 * @param url 网址,后面不用带?及参数
	 * @param params 请求参数
	 * @return
	 */
	public static String sendGetRequestWithMd5(final Context context,String url,LinkedHashMap<String, String> params)
	{
		String result = null;
		
		final Activity activity;
		if (context != null && context instanceof Activity)
		{
			activity = (Activity) context;
		}
		else
		{
			activity = null;
		}
		
		if (activity != null && !Tools.isNetworkAvailable(activity)) {
			return result;
		}
		
		String mtimestamp = System.currentTimeMillis()/1000 + "";
		String versionName;
		if (activity != null) {
			versionName = Tools.getAppVersionName(activity);
		} else {
			versionName = Tools.getVersionName();
		}
		Hashtable<String, String> tmp = new Hashtable<String, String>();
		if (params != null) {
			tmp.putAll(params);
		}		
		String ver = Define.PARAM_V;
		if (!tmp.containsKey("version"))
		{
			tmp.put("version", ver);
		}
		else
		{
			ver = tmp.get("version");
		}
		tmp.put("os", "android");
		try {
			tmp.put("time", URLEncoder.encode(mtimestamp, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Map.Entry[] set = Tools.getSortedHashtableByKey(tmp);
		
		StringBuffer md5Sb = new StringBuffer("");
		for (int i = 0; i < set.length; i++) {
			Map.Entry en = set[i];
			String key = en.getKey().toString();
			String value = en.getValue().toString();
		/*	try {
				value = URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODOAuto-generated catch block
				e1.printStackTrace();
			}*/
			md5Sb.append(key)
			.append('=')
			.append(value)
			.append('&');
			
		}
		String md5 = md5Sb.toString();
		
		StringBuffer sb = new StringBuffer();
		sb.append(url)
		.append("?");

		
		params.put("os", "android");
		params.put("version", ver);
		params.put("time", mtimestamp);
		Map.Entry[] setsecond = Tools.getSortedHashMapByKey(params);
		
		for (int i = 0; i < setsecond.length; i++) {
			Map.Entry en = setsecond[i];
			String key = en.getKey().toString();
			String value = en.getValue().toString();
			try {
				value = URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODOAuto-generated catch block
				e1.printStackTrace();
			}
			sb.append(key)
			.append('=')
			.append(value)
			.append('&');
			
		}
		
		sb.append("sign=")
		.append(MD5.md5(md5 + "key=" + mKey));
		
		String uriAPI = null;
		uriAPI = sb.toString();
		//Logcat.v(TAG, uriAPI);
		Logcat.d(TAG, uriAPI);

		DefaultHttpClient client = (DefaultHttpClient) CustomerHttpClient.getHttpClient();
		client.setCookieStore(Tools.getCookie(activity));
		try {
			HttpGet httpRequest = new HttpGet(uriAPI);
			HttpResponse httpResponse = client.execute(httpRequest);
			Tools.cookieStore = client.getCookieStore();
			Tools.saveCookie(activity, Tools.cookieStore);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity);
			Logcat.d(TAG, result);
		}  catch (final ConnectTimeoutException e) {
			e.printStackTrace();
			Tools.showShortToast(activity, "网络超时");
		} catch (final UnknownHostException e) {
			e.printStackTrace();
			Tools.showShortToast(activity,"网络繁忙");
		} catch (OutOfMemoryError e) {
			System.gc();
		} catch (final Exception e) {
			e.printStackTrace();
			Tools.showShortToast(activity,"请求错误");
		}
		
		return result;
	}
	/**
	 * 传送文本,例如Json,xml等
	 * 
	 * @param urlPath
	 * @param txt
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendText(String urlPath, String txt, String encoding)
			throws Exception {
		byte[] sendData = txt.getBytes();
		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(TIMEOUT);
		// 如果通过post提交数据，必须设置允许对外输出数据
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "text/xml");
		conn.setRequestProperty("Charset", encoding);
		conn.setRequestProperty("Content-Length",
				String.valueOf(sendData.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(sendData);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200) {
			// 获得服务器响应的数据
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), encoding));
			// 数据
			String retData = null;
			String responseData = "";
			while ((retData = in.readLine()) != null) {
				responseData += retData;
			}
			in.close();
			return responseData;
		}
		return "sendText error!";
	}


	/**
	 * 
	 * @description 某些请求需要加密,通过post方式提交参数给服务器,如果当前无网络返回空字符串;context 上下文 为空字符串!
	 * @author
	 * @update 2014年9月27日
	 * @param context 上下文 为空字符串!
	 * @param url 网址,后面不用带?及参数
	 * @param params 请求参数
	 * @return
	 */
	public static String sendPostRequestWithMd5(final Activity activity,String url,LinkedHashMap<String, String> params)
	{
		String result = null;
		if(activity!=null){
			if (!Tools.isNetworkAvailable(activity)) {
				return result;
			}
		}
		
		DefaultHttpClient httpclient = CustomerHttpClient.getHttpClient();
		HttpPost httpRequest = new HttpPost(url);
		httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, Define.DEFAULT_SO_TIMEOUT);
		// 超时设置
		httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Define.DEFAULT_CONNECTION_TIMEOUT);
		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
		
		Hashtable<String, String> tmp = new Hashtable<String, String>();
		if (params != null) {
			tmp.putAll(params);
			Set<String> keys = params.keySet();
//			try {
				if (keys != null && keys.size() > 0) {
					for (String key : keys) {
						String value = params.get(key);
						//value = URLEncoder.encode(value,"utf-8");
						paramsList.add(new BasicNameValuePair(key, value));
					}
				}
//			} catch (UnsupportedEncodingException e) {
//				// TODOAuto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		String mtimestamp = System.currentTimeMillis()/1000 + "";
		String versionName = Tools.getVersionName(activity);
		if(!params.containsKey("version")){
			tmp.put("version", Define.PARAM_V);
			paramsList.add(new BasicNameValuePair("version", Define.PARAM_V));
		}
		tmp.put("os", "android");
	
		tmp.put("time", mtimestamp);
		
		paramsList.add(new BasicNameValuePair("os", "android"));
	
		paramsList.add(new BasicNameValuePair("time", mtimestamp));

		Map.Entry[] set = Tools.getSortedHashtableByKey(tmp);
		String md5 = "";
		for (int i = 0; i < set.length; i++) {
			md5 = md5 + set[i].getKey().toString() + "="
					+ set[i].getValue().toString() + "&";
		}
		paramsList.add(new BasicNameValuePair("sign", MD5.md5(md5 + "key="
				+ mKey)));

		
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(paramsList, HTTP.UTF_8));
			httpclient.setCookieStore(Tools.getCookie(activity));
			HttpResponse httpResponse;
			httpResponse = httpclient.execute(httpRequest);
			Tools.cookieStore = httpclient.getCookieStore();
			Tools.saveCookie(activity, Tools.cookieStore);
			result = EntityUtils.toString(httpResponse.getEntity());
		}  catch (final ConnectTimeoutException e) {
			e.printStackTrace();
			Tools.showShortToast(activity, "网络超时");
		} catch (final UnknownHostException e) {
			e.printStackTrace();
			Tools.showShortToast(activity,"网络繁忙");
		} catch (OutOfMemoryError e) {
			System.gc();
		} catch (final Exception e) {
			e.printStackTrace();
			Tools.showShortToast(activity,"请求错误");
		}
		
		return result;
	}



	/**
	 * 上传文件
	 * 
	 * @param urlPath
	 * @param filePath
	 * @param newName
	 * @return
	 * @throws Exception
	 */
	public static String upSendFile(String urlPath, String filePath,
			String newName) throws Exception {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		URL url = new URL(urlPath);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		/* 允许Input、Output，不使用Cache */
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		/* 设置传送的method=POST */
		con.setRequestMethod("POST");
		/* setRequestProperty */
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);
		/* 设置DataOutputStream */
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(twoHyphens + boundary + end);
		ds.writeBytes("Content-Disposition: form-data; "
				+ "name=\"file1\";filename=\"" + newName + "\"" + end);
		ds.writeBytes(end);
		/* 取得文件的FileInputStream */
		FileInputStream fStream = new FileInputStream(filePath);
		/* 设置每次写入1024bytes */
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		/* 从文件读取数据至缓冲区 */
		while ((length = fStream.read(buffer)) != -1) {
			/* 将资料写入DataOutputStream中 */
			ds.write(buffer, 0, length);
		}
		ds.writeBytes(end);
		ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

		/* close streams */
		fStream.close();
		ds.flush();

		/* 取得Response内容 */
		InputStream is = con.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = is.read()) != -1) {
			b.append((char) ch);
		}
		/* 关闭DataOutputStream */
		ds.close();
		return b.toString();
	}

	/**
	 * 通过get方式提交参数给服务器
	 * 
	 * @param urlPath
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendGetRequest(String urlPath,
			Map<String, String> params, String encoding) throws Exception {
		// 使用StringBuilder对象
		StringBuilder sb = new StringBuilder(urlPath);
		sb.append('?');
		// 迭代Map
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey()).append('=')
					.append(URLEncoder.encode(entry.getValue(), encoding))
					.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);

		// 打开链接
		URL url = new URL(sb.toString());
		Log.v("test", sb.toString());
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		HttpGet httpGet = new HttpGet(sb.toString());
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode()==200) {
				HttpEntity entity = response.getEntity();
				String body = EntityUtils.toString(entity);
				return body;
			}
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "sendGetRequest error!";
	}

	/**
	 * 通过Post方式提交参数给服务器,也可以用来传送json或xml文件
	 * 
	 * @param urlPath
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendPostRequest(String urlPath,
			Map<String, String> params, String encoding) throws Exception {
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				TIMEOUT);
		//params.put("mark", Cache.getCache("mark").toString());
		
		
		String urlString=urlPath+params.get("request");
		Log.e("uri", "url=========="+urlString);
		HttpPost httpPost = new HttpPost(urlString);
		ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		if (params != null) {
			Set<String> keys = params.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = i.next();
				pairs.add(new BasicNameValuePair(key, params.get(key)));
			}
		}
		Log.e("uri", "date=========="+pairs.toString());
		
		try {
			UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs,
					encoding);
			httpPost.setEntity(p_entity);
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity);
			return body;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "sendText error!";
	}
	
	
	 public static String sendPostMessage(String urlPath,Map<String, String> params,  
	            String encode) {  
	        StringBuilder stringBuilder = new StringBuilder();  
	        if (params != null && !params.isEmpty()) {  
	            for (Map.Entry<String, String> entry : params.entrySet()) {  
	                try {  
	                    stringBuilder  
	                            .append(entry.getKey())  
	                            .append("=")  
	                            .append(URLEncoder.encode(entry.getValue(), encode))  
	                            .append("&");  
	                } catch (UnsupportedEncodingException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	 

	            stringBuilder.deleteCharAt(stringBuilder.length() - 1);  
	            try {  
	                HttpURLConnection urlConnection = (HttpURLConnection) ((new URL(urlPath))  
	                        .openConnection());  
	                urlConnection.setConnectTimeout(TIMEOUT);  
	                urlConnection.setRequestMethod("POST"); // 以post请求方式提交  
	                urlConnection.setDoInput(true); // 读取数据  
	                urlConnection.setDoOutput(true); // 向服务器写数据  
	                urlConnection.setUseCaches(false);
	                // 获取上传信息的大小和长度  
	                byte[] myData = stringBuilder.toString().getBytes();  
	                // 设置请求体的类型是文本类型,表示当前提交的是文本数据  
	                urlConnection.setRequestProperty("Content-Type",  
	                		"application/json");  
	               // urlConnection.setRequestProperty("Content-Type",  
	               // 		"application/x-www-form-urlencoded");  
	                
	                
	                urlConnection.setRequestProperty("Content-Length",  
	                        String.valueOf(myData.length));  
	                
	                urlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
	                urlConnection.setRequestProperty("Charset", "UTF-8");
	                
	                Log.e("test", urlConnection.toString());
	                // 获得输出流，向服务器输出内容  
	                OutputStream outputStream = urlConnection.getOutputStream();  
	                // 写入数据  
	                outputStream.write(myData);  
	                Log.e("test", "-------------"+myData.length+stringBuilder.toString());
	               // outputStream.flush();
	                //outputStream.close();  
	                // 获得服务器响应结果和状态码  
	                int responseCode = urlConnection.getResponseCode();  
	                if (responseCode == 200) {  
	                    // 取回响应的结果  
	                	Log.e("test", "----------dddddddddd---");
	                	
	                	
	                    return changeInputStream(urlConnection.getInputStream(),  
	                            encode);  
	                }  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	  
	        }  
	        return "";  
	    }  
	
	 
	 private static String changeInputStream(InputStream inputStream,  
	            String encode) {  
	  
	        // 内存流  
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
	        byte[] data = new byte[1024];  
	        int len = 0;  
	        String result = null;  
	        if (inputStream != null) {  
	            try {  
	                while ((len = inputStream.read(data)) != -1) {  
	                    byteArrayOutputStream.write(data, 0, len);  
	                }  
	                result = new String(byteArrayOutputStream.toByteArray(), encode);  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return result;  
	    }  
	 
	 
	 
	 public static void HttpPostData() {  
		 try {  
		     HttpClient httpclient = new DefaultHttpClient();  
		     String uri = "http://api.0898dama.com/post/?url=";  
		     HttpPost httppost = new HttpPost(uri);   
		     //添加http头信息   
		     //httppost.addHeader("Authorization", "your token"); //认证token 
		     httppost.addHeader("Accept", "application/json");
		     httppost.addHeader("Content-type", "application/json");
		     httppost.addHeader("Content-Type", "application/json");  
		     //httppost.addHeader("User-Agent", "imgfornote");  
		     //http post的json数据格式：  {"name": "your name","parentId": "id_of_parent"}   
		     JSONObject obj = new JSONObject();  
		     obj.put("from", "2");  
		     obj.put("account","andortest"); 
		     obj.put("pawd", "123456"); 
		     obj.put("appid", "FLEUHU8mjSFXB80");
		     obj.put("url", "userLogin");
		     httppost.setEntity(new StringEntity(obj.toString()));     
		     HttpResponse response;  
		     response = httpclient.execute(httppost);  
		     //检验状态码，如果成功接收数据   
		     int code = response.getStatusLine().getStatusCode();  
		     if (code == 200) {   
		         String rev = EntityUtils.toString(response.getEntity());//返回json格式： {"id": "27JpL~j4vsL0LX00E00005","version": "abc"}          
		         Log.e("sss", "ssssss"+rev.toString());
		     }  
		     } catch (ClientProtocolException e) {     
		     } catch (IOException e) {     
		     } catch (Exception e) {   
		     }  
		 }
	 

	/**
	 * 在遇上HTTPS安全模式或者操作cookie的时候使用HTTPclient会方便很多 使用HTTPClient（开源项目）向服务器提交参数
	 * 
	 * @param urlPath
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendHttpClientPost(String urlPath,
			Map<String, String> params, String encoding) throws Exception {
		// 需要把参数放到NameValuePair
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				paramPairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
				
			}
		}
		// 对请求参数进行编码，得到实体数据
		UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs,
				encoding);
		// 构造一个请求路径
		HttpPost post = new HttpPost(urlPath);
		// 设置请求实体
		post.setEntity(entitydata);
		// 浏览器对象
		DefaultHttpClient client = new DefaultHttpClient();
		// 执行post请求
		HttpResponse response = client.execute(post);
		// 从状态行中获取状态码，判断响应码是否符合要求
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, encoding);
			BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
			String s;
			String responseData = "";
			while (((s = reader.readLine()) != null)) {
				responseData += s;
			}
			reader.close();// 关闭输入流
			return responseData;
		}
		return "sendHttpClientPost error!";
	}

	/**
	 * post使用session
	 * 
	 * @param urlPath
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendHttpClientPostSession(String urlPath,
			Map<String, String> params, String encoding, String session)
			throws Exception {
		// 需要把参数放到NameValuePair
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				paramPairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		// 对请求参数进行编码，得到实体数据
		UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs,
				encoding);
		// 构造一个请求路径
		HttpPost post = new HttpPost(urlPath);
		// 设置请求实体
		post.setEntity(entitydata);

		if (session != null) {
			post.setHeader("Cookie", "PHPSESSID=" + session);
		}
		// 浏览器对象
		DefaultHttpClient client = new DefaultHttpClient();
		// 执行post请求
		HttpResponse response = client.execute(post);
		// 从状态行中获取状态码，判断响应码是否符合要求
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, encoding);
			BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
			String s;
			String responseData = "";
			while (((s = reader.readLine()) != null)) {
				responseData += s;
			}
			reader.close();// 关闭输入流
			return responseData;
		}
		return "sendHttpClientPost error!";
	}

	/**
	 * 根据URL直接读文件内容，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容
	 * 
	 * @param urlStr
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String readTxtFile(String urlStr, String encoding)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			URL url = new URL(urlStr);
			// 创建一个Http连接
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// 使用IO流读取数据
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream(), encoding));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 该函数返回整形 -1：代表下载文件出错 0：代表下载文件成功 1：代表文件已经存在
	 * 
	 * @param urlStr
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static int downloadFile(String urlStr, String path, String fileName)
			throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = getInputStreamFromUrl(urlStr);
			File resultFile = write2SDFromInput(path, fileName, inputStream);
			if (resultFile == null) {
				return -1;
			}

		} catch (Exception e) {
			return -1;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				throw e;
			}
		}
		return 0;
	}

	/**
	 * 根据URL得到输入流
	 * 
	 * @param urlStr
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static InputStream getInputStreamFromUrl(String urlStr)
			throws MalformedURLException, IOException {
		URL url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param directory
	 * @param fileName
	 * @param input
	 * @return
	 */
	private static File write2SDFromInput(String directory, String fileName,
			InputStream input) {
		File file = null;
		String SDPATH = Environment.getExternalStorageDirectory().toString();
		FileOutputStream output = null;
		File dir = new File(SDPATH + directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		try {
			file = new File(dir + File.separator + fileName);
			file.createNewFile();
			output = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			while ((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}
