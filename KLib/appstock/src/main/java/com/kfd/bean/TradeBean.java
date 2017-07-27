package com.kfd.bean;

import java.io.Serializable;

public class TradeBean implements  Serializable{
/**
 *     "id": "6",

                "title": "04月10日欧美盘操作建议",

                "stdesc": "伦敦金：上方压力1193、1197、1202 下方支撑1180、1175、1169。伦敦银：上方压力16.55、16.70、16.90",

                "dateline": "1433473313"
                url

 */
	public String id,title,stdesc,dateline,url,content,isshowall;

public String getIsshowall() {
	return isshowall;
}

public void setIsshowall(String isshowall) {
	this.isshowall = isshowall;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
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

public String getStdesc() {
	return stdesc;
}

public void setStdesc(String stdesc) {
	this.stdesc = stdesc;
}

public String getDateline() {
	return dateline;
}

public void setDateline(String dateline) {
	this.dateline = dateline;
}
	
}
