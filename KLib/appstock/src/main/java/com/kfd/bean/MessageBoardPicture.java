/*
 * Copyright (c) 2014 The MaMaHelp_7_27 Project,
 *
 * 深圳市新网智创科技有限公司. All Rights Reserved.
 */

package com.kfd.bean;

import java.io.Serializable;

/**
 * @Function: 图片详情
 * @author xiaobo.lin
 * @version
 * @Date: 2014-4-3 下午8:18:37
 */
public class MessageBoardPicture implements Serializable{
	private String picture;
	private String width;
	private String height;
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	
}

	
