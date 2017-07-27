package com.kfd.bean;

import java.io.Serializable;

public class  KFDPrice  implements Serializable {
	/**
	 *    "typename": "silver", "price": "16.71", "change_pro": "-0.04"
	 */
	private String typename,price,change_pro;

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
	
}