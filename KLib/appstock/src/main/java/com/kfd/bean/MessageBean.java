package com.kfd.bean;

import java.io.Serializable;

public class MessageBean  implements Serializable {
 /**
  *    "id": "3",

                "title": "活动上线啦",

                "isnew": "1",

                "dateline": "1435161852"

  */
	private String  id,title,isnew,dateline;

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

public String getIsnew() {
	return isnew;
}

public void setIsnew(String isnew) {
	this.isnew = isnew;
}

public String getDateline() {
	return dateline;
}

public void setDateline(String dateline) {
	this.dateline = dateline;
}
	
}
