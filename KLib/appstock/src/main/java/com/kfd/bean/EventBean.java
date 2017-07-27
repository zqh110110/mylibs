package com.kfd.bean;

public class EventBean extends  Base{
 /**
  * country-国家
time-时间
content-内容
important-重要性
  */
	private String  country,time,content,important;
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getImportant() {
		return important;
	}
	
	public void setImportant(String important) {
		this.important = important;
	}
	
	
}
