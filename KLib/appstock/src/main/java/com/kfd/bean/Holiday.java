package com.kfd.bean;

import java.io.Serializable;

public class Holiday extends Base implements  Serializable{
/*8
 * country-国家
time-时间
content-内容
 */
	private String  country,time,content;

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
	
}
