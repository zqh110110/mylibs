package com.smcb.config;

import com.smcb.gen.util.BuildResultListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@PropertySource("classpath:app.properties")
@Component
public class AppInfoConfig {

	public String projectDir;//工程目录
	public String moduleName;//module名称
	public String apkpath;//生成apk的全路径
	public String buildtype;//编译类型GenApkUtil.DEBUG，GenApkUtil.RELEASE
	public String versionName;//版本名称
	public String versionCode;//版本号
	public String pkg;//包名前缀
	public String tempPicPath;
	public String appPath;


	public String getProjectDir() {
		return projectDir;
	}
	public void setProjectDir(String projectDir) {
		this.projectDir = projectDir;
	}
	public String getModuleName() {
		return moduleName;
	}
	public String getApkpath() {
		return apkpath;
	}
	public void setApkpath(String apkpath) {
		this.apkpath = apkpath;
	}
	public String getBuildtype() {
		return buildtype;
	}
	public void setBuildtype(String buildtype) {
		this.buildtype = buildtype;
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

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getTempPicPath() {
		return tempPicPath;
	}

	public void setTempPicPath(String tempPicPath) {
		this.tempPicPath = tempPicPath;
	}

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}
}
