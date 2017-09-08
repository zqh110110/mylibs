package com.smcb.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
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
@TableName("analyst_buildinfo")
public class AnalystBuildinfo extends Model<AnalystBuildinfo> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	@TableField("app_name")
	private String appName;
	@TableField("pic_path")
	private String picPath;
	@TableField("version_name")
	private String versionName;
	@TableField("version_code")
	private String versionCode;
	@TableField("analyst_id")
	private Long analystId;
	@TableField("build_name")
	private String buildName;
	private String buildpath;

	@JsonSerialize(using=ToStringSerializer.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public Long getAnalystId() {
		return analystId;
	}

	public void setAnalystId(Long analystId) {
		this.analystId = analystId;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getBuildpath() {
		return buildpath;
	}

	public void setBuildpath(String buildpath) {
		this.buildpath = buildpath;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
