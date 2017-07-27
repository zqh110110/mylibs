package com.kfd.bean;

import java.io.Serializable;

public class ETFPostionBean implements  Serializable{
	/**
	 *  "nums": "709.89",

                "dateline": "1433279948",

                "change_nums": "-4.18"

	 */
	private String nums,dateline,change_nums;

	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getChange_nums() {
		return change_nums;
	}

	public void setChange_nums(String change_nums) {
		this.change_nums = change_nums;
	}
	
}
