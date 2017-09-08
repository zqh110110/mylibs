package com.smcb.gen.util;

import java.io.Serializable;

public class AppInfo implements Serializable {
	
	public String projectDir;//工程目录
	public String moduleName;//module名称
	public String appKey;//唯一标识
	public String pkg;//项目包名
	public String appName;//应用名称
	public String appIcon;//应用对应的图标路径
	public String apkpath;//生成apk的全路径
	public String buildtype;//编译类型GenApkUtil.DEBUG，GenApkUtil.RELEASE
	public String versionName;//版本名称
	public String versionCode;//版本号
	public String pic;
	public String iconpic;
	public String buildinfoId;
	public BuildResultListener listener;

	public boolean forcebuild = false;

	public AppInfo(){}
	
	public AppInfo(String projectDir, String moduleName, String appKey,
			String pkg, String appName, String appIcon, String apkpath,
			String buildtype, String versionName, String versionCode) {
		super();
		this.projectDir = projectDir;
		this.moduleName = moduleName;
		this.appKey = appKey;
		this.pkg = pkg;
		this.appName = appName;
		this.appIcon = appIcon;
		this.apkpath = apkpath;
		this.buildtype = buildtype;
		this.versionName = versionName;
		this.versionCode = versionCode;
	}
	
	public String getProjectDir() {
		return projectDir;
	}
	public void setProjectDir(String projectDir) {
		this.projectDir = projectDir;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
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

	public BuildResultListener getListener() {
		return listener;
	}

	public void setListener(BuildResultListener listener) {
		this.listener = listener;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getIconpic() {
		return iconpic;
	}

	public void setIconpic(String iconpic) {
		this.iconpic = iconpic;
	}

	public boolean isForcebuild() {
		return forcebuild;
	}

	public void setForcebuild(boolean forcebuild) {
		this.forcebuild = forcebuild;
	}

	public String getBuildinfoId() {
		return buildinfoId;
	}

	public void setBuildinfoId(String buildinfoId) {
		this.buildinfoId = buildinfoId;
	}
}
