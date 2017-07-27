package com.kfd.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * 通知信息实体类
 * 
 * @author 朱继洋
 * @QQ 7617812 2013-3-16
 */
public class Notice implements Serializable {

	public final static String UTF8 = "UTF-8";
	public final static String NODE_ROOT = "ebs";

	public final static int TYPE_ATME = 1;
	public final static int TYPE_MESSAGE = 2;
	public final static int TYPE_COMMENT = 3;
	public final static int TYPE_NEWFAN = 4;

	private int atmeCount;
	private int msgCount;
	private int reviewCount;

	public int getAtmeCount() {
		return atmeCount;
	}

	public void setAtmeCount(int atmeCount) {
		this.atmeCount = atmeCount;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

}
