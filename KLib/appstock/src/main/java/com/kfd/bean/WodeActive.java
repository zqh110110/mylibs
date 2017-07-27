package com.kfd.bean;

import java.io.Serializable;
/**
 * 我的活动

 * 2015-6-28
 */
public class WodeActive  implements   Serializable {
	/**
	 * "dateline": "1435161411",

                "url": "m-main/active?id=3",

                "id": "3",

                "title": "史上首家以被推荐人入金量计算奖金 奖金高达累计净入金量3%",

                "endtime": "1438358400"

	 */
	private String  dateline,url,id,title,endtime;

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}
