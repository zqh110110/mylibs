package com.kfd.bean;

import java.io.Serializable;

public class HelpBean  implements Serializable {
	/**
	 *    "title": "我要修改姓名",

                "content": "姓名一经确认，系统将无法修改。"

	 */
	private String  title,content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
