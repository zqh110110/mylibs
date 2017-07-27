package com.kfd.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import android.content.Context;

public class HttpDownloader {
	
	private URL url = null; 
	private Context mContext ;
	
	/**
	 * ���URL�����ļ�,ǰ��������ļ����е��������ı�,����ķ���ֵ�����ı����е�����
	 * 1.����һ��URL����
	 * 2.ͨ��URL����,����һ��HttpURLConnection����
	 * 3.�õ�InputStream
	 * 4.��InputStream���ж�ȡ���
	 * @param urlStr
	 * @return
	 */
	
	public String download(String urlStr){
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			while( (line = buffer.readLine()) != null){
				sb.append(line);
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param urlStr
	 * @param path
	 * @param fileName
	 * @return 
	 * 		-1:�ļ����س���
	 * 		 0:�ļ����سɹ�
	 * 		 1:�ļ��Ѿ�����
	 */
	public int downFile(String urlStr, String path, String fileName, String tag){
		InputStream inputStream = null;
		System.out.println("开始下载图片");
		try {
			FileUtils fileUtils = new FileUtils();
			
			if(fileUtils.isFileExist(path + fileName)){
				System.out.println("图片存在");
				return 1;
			} else {
				inputStream = getInputStreamFromURL(urlStr);
				if(inputStream==null){
					return -1;
				}
				//System.out.println("inputStream: "+inputStream);
				File resultFile = FileUtils.write2SDFromInput(path, fileName, inputStream, tag);
				if(resultFile == null){
					return -1;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		finally{
			try {
				if(inputStream!=null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/**
	 * ���URL�õ�������
	 * @param urlStr
	 * @return
	 */
	public InputStream getInputStreamFromURL(String urlStr) {
		HttpURLConnection urlConn = null;
		InputStream inputStream = null;
		try {
			url = new URL(urlStr);
			urlConn = (HttpURLConnection)url.openConnection();
			urlConn.connect();
			inputStream = urlConn.getInputStream();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return inputStream;
	}
}
