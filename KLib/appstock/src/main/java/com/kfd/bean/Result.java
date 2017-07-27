package com.kfd.bean;

import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 数据操作结果实体类
 * 
 * @author 朱继洋
 * @QQ 7617812 2013-3-19 version 1.0
 */
public class Result extends Base {

	private int errorCode;
	private String errorMessage;

	public boolean OK() {
		return errorCode == 1;
	}

	/**
	 * 解析调用结果
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static Result parse(InputStream stream) throws IOException {
		return null;

	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return String.format("RESULT: CODE:%d,MSG:%s", errorCode, errorMessage);
	}

}
