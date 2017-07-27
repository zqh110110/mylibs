package com.kfd.common;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * 数据缓存类
 * 
 * @author 朱继洋 2013-3-22 QQ 7617812
 */
public class Cache {
	private static HashMap<String, SoftReference<Object>> cache = new HashMap();

	public static void clear() {
		cache.clear();
	}

	public static Object getCache(String paramString) {
		if (cache.get(paramString) != null) {
			return cache.get(paramString).get();
		} else {
			return null;
		}
	}

	public static void put(String paramString, Object paramObject) {
		SoftReference<Object> object = new SoftReference<Object>(paramObject);
		cache.put(paramString, object);
	}

	public static Object remove(String paramString) {
		SoftReference<Object> res = cache.get(paramString);
		return res == null ? null : res.get();
	}
}
