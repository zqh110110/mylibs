package com.kfd.bean;

import java.io.Serializable;

public class KuaiXun implements  Serializable {
/*
 *   "id": "351",

                "content": "印尼能源及矿业部长苏迪曼：欧佩主要成员国“完全支持”印尼重回欧佩克。",

                "dateline": "1433471460"

 */
	private String  id,content,dateline;
	
	private String picture;
	
	private String  isbold;
	private String color;
	
	private String datapic;//-利多空背景图 ;
	
	private String  datainfo;//-利多空文字;
	private String datacolor;//-利多空文字颜色;
	
	

	public String getDatapic() {
		return datapic;
	}

	public void setDatapic(String datapic) {
		this.datapic = datapic;
	}

	public String getDatainfo() {
		return datainfo;
	}

	public void setDatainfo(String datainfo) {
		this.datainfo = datainfo;
	}

	public String getDatacolor() {
		return datacolor;
	}

	public void setDatacolor(String datacolor) {
		this.datacolor = datacolor;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getIsbold() {
		return isbold;
	}

	public void setIsbold(String isbold) {
		this.isbold = isbold;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	
}
