package com.kfd.bean;

/**
 * 实体类
 * 
 * @author 朱继洋
 * @QQ 7617812 2013-3-19 version 1.0
 */
public abstract class Entity extends Base {

	protected String id;

	public String getId() {
		return id;
	}

	protected String cacheKey;

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
}
