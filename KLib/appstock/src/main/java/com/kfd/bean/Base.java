package com.kfd.bean;

import java.io.Serializable;

/**
 * 实体基类：实现序列化
 * 
 * @author 朱继洋
 * @QQ 7617812 2013-3-19 version 1.0
 */
public abstract class Base implements Serializable {
  
   public int  type;

public int getType() {
	return type;
}

public void setType(int type) {
	this.type = type;
}
   

}
