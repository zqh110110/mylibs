package com.kfd.entity;

import java.io.Serializable;


/**
 * @author Administrator
 *
 */
public class MarkPrice implements Serializable{
    private String typename;// hs300,
    private String price;// 3666.20,
    private String     change_pro;// 4.98,
    private String name;// \u6cf8\u6df1300
    
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getChange_pro() {
		return change_pro;
	}
	public void setChange_pro(String change_pro) {
		this.change_pro = change_pro;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}
