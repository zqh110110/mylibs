package com.kfd.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kfd.common.StringUtils;

public class InvestBean   implements  Serializable{
	/**
	 *    "islike": "1",

                "nickname": "aiden",

                "face": "http://file.iduouo.com/20150522121229__90955103default.jpg"
	 */
	private String  id,uid,content,likes,comments,islike,nickname,face,dateline;
	
	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public class  InvestNewLikes  implements  Serializable{
		private String  uid,nickname;

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		
		
	}
	
public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLikes() {
		
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getIslike() {
		return islike;
	}

	public void setIslike(String islike) {
		this.islike = islike;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}
	private ArrayList<InvestNewLikes>  arrayList  =  new ArrayList<InvestNewLikes>();
	

public ArrayList<InvestNewLikes> getArrayList() {
		return arrayList;
	}

	public void setArrayList(ArrayList<InvestNewLikes> arrayList) {
		this.arrayList = arrayList;
	}


/**
 *   "id": "6",

                "uid": "1",

                "content": "汪老师讲课真不错",

                "likes": "6",

                "comments": "2",

                "newlikes": [

                    {

                        "uid": "6",

                        "nickname": "公民"

                    },

                    {

                        "uid": "5",

                        "nickname": "恭候您"

                    },

                    {

                        "uid": "4",

                        "nickname": "1111"

                    },

                    {

                        "uid": "3",

                        "nickname": "分"

                    },

                    {

                        "uid": "2",

                        "nickname": "老师"

                    }

                ],

                "newcomments": [

                    {

                        "uid": "1",

                        "nickname": "aiden",

                        "content": "哈哈",

                        "cuid": "0",

                        "cnickname": ""

                    },

                    {

                        "uid": "1",

                        "nickname": "aiden",

                        "content": "哈哈",

                        "cuid": "2",

                        "cnickname": "老师"

                    }

                ],

                "piclist": [],

                "islike": "1",

                "nickname": "aiden",

                "face": "http://file.iduouo.com/20150522121229__90955103default.jpg"

 */
	
	private ArrayList<InvestNewcomments>  commArrayList  = new ArrayList<InvestBean.InvestNewcomments>();
	
	public ArrayList<InvestNewcomments> getCommArrayList() {
	return commArrayList;
}

public void setCommArrayList(ArrayList<InvestNewcomments> commArrayList) {
	this.commArrayList = commArrayList;
}

	public class  InvestNewcomments  implements Serializable{
		private String  uid,nickname,content,cuid,cnickname;

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getCuid() {
			return cuid;
		}

		public void setCuid(String cuid) {
			this.cuid = cuid;
		}

		public String getCnickname() {
			return cnickname;
		}

		public void setCnickname(String cnickname) {
			this.cnickname = cnickname;
		}
		
	}

	public class PicList  implements Serializable{
		private String  thumb,picture;

		public String getThumb() {
			return thumb;
		}

		public void setThumb(String thumb) {
			this.thumb = thumb;
		}

		public String getPicture() {
			return picture;
		}

		public void setPicture(String picture) {
			this.picture = picture;
		}
		
	}
	private ArrayList<PicList>  picListsArrayList   = new ArrayList<InvestBean.PicList>();
	
	public ArrayList<PicList> getPicListsArrayList() {
		return picListsArrayList;
	}

	public void setPicListsArrayList(ArrayList<PicList> picListsArrayList) {
		this.picListsArrayList = picListsArrayList;
	}

	public static ArrayList<InvestBean> parseData(String result) {
		ArrayList<InvestBean>  arrayList  =  new ArrayList<InvestBean>();
		if (!StringUtils.isEmpty(result)) {
	
			try {
				JSONObject jsonObject = new JSONObject(result);
				JSONObject jsonObject2 = jsonObject.optJSONObject("data");
				JSONArray  jsonArray =  jsonObject2.optJSONArray("list");
				for (int i = 0; i < jsonArray.length(); i++) {
					InvestBean  investBean  = new InvestBean();
					JSONObject jsonObject3 =  jsonArray.optJSONObject(i);
					investBean.setId(jsonObject3.optString("id"));
					investBean.setUid(jsonObject3.optString("uid"));
					investBean.setContent(jsonObject3.optString("content"));
					investBean.setFace(jsonObject3.optString("face"));
					investBean.setLikes(jsonObject3.optString("likes"));
					investBean.setComments(jsonObject3.optString("comments"));
					investBean.setIslike(jsonObject3.optString("islike"));
					investBean.setNickname(jsonObject3.optString("nickname"));
					investBean.setDateline(jsonObject3.optString("dateline"));
					
					
					JSONArray  jsonArray2 =  jsonObject3.optJSONArray("newlikes");
					for (int j = 0; j < jsonArray2.length(); j++) {
						InvestNewLikes  investNewLikes = investBean.new InvestNewLikes();
						JSONObject jsonObject4  = jsonArray2.optJSONObject(j);
						investNewLikes.setNickname(jsonObject4.optString("nickname"));
						investNewLikes.setUid(jsonObject4.optString("uid"));
						investBean.getArrayList().add(investNewLikes);
					}
					
					
					JSONArray  jsonArray3 =  jsonObject3.optJSONArray("newcomments");
					if (jsonArray3!=null ) {
						for (int j = 0; j < jsonArray3.length(); j++) {
							InvestNewcomments  investNewcomments = investBean.new InvestNewcomments();
							JSONObject jsonObject4  = jsonArray3.optJSONObject(j);
							investNewcomments.setNickname(jsonObject4.optString("nickname"));
							investNewcomments.setUid(jsonObject4.optString("uid"));
							investNewcomments.setCnickname(jsonObject4.optString("cnickname"));
							investNewcomments.setContent(jsonObject4.optString("content"));
							investNewcomments.setCuid(jsonObject4.optString("cuid"));
							
							investBean.getCommArrayList().add(investNewcomments);
						}
					}
					
					JSONArray  jsonArray4 =  jsonObject3.optJSONArray("piclist");
					if (jsonArray4!=null ) {
						for (int j = 0; j < jsonArray4.length(); j++) {
							PicList  piclist = investBean.new PicList();
							JSONObject jsonObject4  = jsonArray4.optJSONObject(j);
							piclist.setPicture(jsonObject4.optString("picture"));
							piclist.setThumb(jsonObject4.optString("thumb"));
							
							investBean.getPicListsArrayList().add(piclist);
						}
					}
						
					arrayList.add(investBean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}
}
