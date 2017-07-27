package com.kfd.bean;

import java.io.Serializable;

import com.kfd.common.LogUtils;

/**
 * 主页显示
 * 
 * @author 朱继洋 QQ7617812 2013-7-2
 */
public class MainMesageBean implements Serializable {
	private String article_id, c_id, title, abstractmessage, title_pic,
			add_time, content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getC_id() {
		return c_id;
	}

	public void setC_id(String c_id) {
		this.c_id = c_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbstractmessage() {
		return abstractmessage;
	}

	public void setAbstractmessage(String abstractmessage) {
		this.abstractmessage = abstractmessage;
	}

	public String getTitle_pic() {
		return title_pic;
	}

	public void setTitle_pic(String title_pic) {
		this.title_pic = title_pic;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	@Override
	public String toString() {
		LogUtils.log("test", "MainMesageBean [article_id=" + article_id
				+ ", c_id=" + c_id + ", title=" + title + ", abstractmessage="
				+ abstractmessage + ", title_pic=" + title_pic + ", add_time="
				+ add_time + ", content=" + content + "]");
		return "MainMesageBean [article_id=" + article_id + ", c_id=" + c_id
				+ ", title=" + title + ", abstractmessage=" + abstractmessage
				+ ", title_pic=" + title_pic + ", add_time=" + add_time
				+ ", content=" + content + "]";
	}

}
