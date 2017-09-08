package com.smcb.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zqh
 * @since 2017-04-12
 */
public class Analyst extends Model<Analyst> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	@TableField("analyst_name")
	private String analystName;
	private String password;
	private String appkey;
	@TableField("buildinfo_id")
	private Long buildinfoId;
	@TableField("user_id")
	private String userId;
	private String temp;

	@JsonSerialize(using=ToStringSerializer.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnalystName() {
		return analystName;
	}

	public void setAnalystName(String analystName) {
		this.analystName = analystName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public Long getBuildinfoId() {
		return buildinfoId;
	}

	public void setBuildinfoId(Long buildinfoId) {
		this.buildinfoId = buildinfoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
