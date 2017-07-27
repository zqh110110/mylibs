package com.kfd.bean;

import java.io.Serializable;

public class ActiveBean  implements Serializable {
	/**
	 *    "id": "5",

                "title": "听课做交易 降点差增盈利",

                "thumb": "http://kfd.demo.golds-cloud.com/st/img/act5.jpg",

                "starttime": "1430409600",

                "isjoin": "0"
                

	 */
	private String  id,title,thumb,starttime,isjoin,url;

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

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getIsjoin() {
		return isjoin;
	}

	public void setIsjoin(String isjoin) {
		this.isjoin = isjoin;
	}
	
}
