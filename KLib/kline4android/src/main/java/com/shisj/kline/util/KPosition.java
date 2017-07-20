package com.shisj.kline.util;

public class KPosition {

	private float x;
	private float y;
	public int index;
	
	private String time;
	private String price;
	private String priceExt;
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getPriceValue(){
		if(price==null)return 0.0f;
		return Float.parseFloat(price);
	}
	public float getPriceExtValue(){
		if(priceExt==null)return 0.0f;
		return Float.parseFloat(priceExt);
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPriceExt() {
		return priceExt;
	}
	public void setPriceExt(String priceExt) {
		this.priceExt = priceExt;
	}
	public KPosition(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	public KPosition(float x, float y, int index) {
		super();
		this.x = x;
		this.y = y;
		this.index = index;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
}
