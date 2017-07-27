package com.kfd.entity;

import java.io.Serializable;

public class Price implements Serializable
{

	/**
	 * "name": "伦敦银",
	 * 
	 * "typename": "silver",
	 * 
	 * "price": "15.96",
	 * 
	 * "high_price": "16.04",
	 * 
	 * "low_price": "15.92",
	 * 
	 * "change_num": "-0.02",
	 * 
	 * "change_pro": "-0.13",
	 * 
	 * "yest_num": "15.98",
	 * 
	 * "start_price": "15.98"
	 */
	private String name, typename, price, high_price, low_price, change_num, change_pro, yest_num, start_price;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTypename()
	{
		return typename;
	}

	public void setTypename(String typename)
	{
		this.typename = typename;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	public String getHigh_price()
	{
		return high_price;
	}

	public void setHigh_price(String high_price)
	{
		this.high_price = high_price;
	}

	public String getLow_price()
	{
		return low_price;
	}

	public void setLow_price(String low_price)
	{
		this.low_price = low_price;
	}

	public String getChange_num()
	{
		return change_num;
	}

	public void setChange_num(String change_num)
	{
		this.change_num = change_num;
	}

	public String getChange_pro()
	{
		return change_pro;
	}

	public void setChange_pro(String change_pro)
	{
		this.change_pro = change_pro;
	}

	public String getYest_num()
	{
		return yest_num;
	}

	public void setYest_num(String yest_num)
	{
		this.yest_num = yest_num;
	}

	public String getStart_price()
	{
		return start_price;
	}

	public void setStart_price(String start_price)
	{
		this.start_price = start_price;
	}


}
