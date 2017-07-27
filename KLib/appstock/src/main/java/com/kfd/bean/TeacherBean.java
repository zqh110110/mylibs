package com.kfd.bean;

import java.io.Serializable;

public class TeacherBean   implements Serializable{
	/**
	 *    "id": "3",

                "realname": "张生",

                "face": "http://kfd.demo.golds-cloud.com/upload/201505//2015053010355016D1.jpg",

                "level": "5",

                "content": "张生毕业于北京大学光华管理学院，主修金融学。从事黄金投资行业已经有十１多年，",

                "ischoose": "1",

                "choose_time": "1434374890",

                "url": "http://kfd.demo.golds-cloud.com/pm?uid=19"

	 */
	private String   id,realname,face,level,content,ischoose,choose_time,url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIschoose() {
		return ischoose;
	}

	public void setIschoose(String ischoose) {
		this.ischoose = ischoose;
	}

	public String getChoose_time() {
		return choose_time;
	}

	public void setChoose_time(String choose_time) {
		this.choose_time = choose_time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
