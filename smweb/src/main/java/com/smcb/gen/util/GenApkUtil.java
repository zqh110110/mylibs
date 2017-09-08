package com.smcb.gen.util;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class GenApkUtil {
	
	private static Logger logger = Logger.getLogger(GenApkUtil.class);
	
	public static final String ICON_DIR = "drawable";
	public static final String RELEASE = "Release";
	public static final String DEBUG = "Debug";
	public static final String BUILD_FAILED = "BUILD FAILED";
	public static final String BUILD_SUCCESSFUL = "BUILD SUCCESSFUL";
	
	public static int MAX_RUNBUILD_NUMBER = 3;
	private static int CURRENT_RUNBUILD_NUMBER = 0;
	
	private LinkedBlockingQueue<AppInfo> queues = new LinkedBlockingQueue<>();
	
	private static GenApkUtil instance;
	private Thread mThread;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		AppInfo app = new AppInfo("E:/smstock/","app","13871408469","com.a.b1"
//				,"你好","@mipmap/ic_launcher_round","e:/apk/.apk"
//				,GenApkUtil.DEBUG);
//		
//		runBuild(app,new BuildResultListener() {
//			
//			@Override
//			public void success() {
//				
//			}
//			
//			@Override
//			public void fail() {
//				
//			}
//			@Override
//			public void completed() {
//				
//			}
//		});
		
		AppInfo app = new AppInfo("E:/smstock/","app","13871408469","com.a.b1"
				,"你好","E:\\gwkj\\app\\src\\main\\res\\drawable-hdpi\\icon_search.png","e:/apk/"+1+".apk"
				,GenApkUtil.DEBUG,null,null);
		AppInfo app1 = new AppInfo("E:/smstock/","app","13871408468","com.a.b2"
				,"你好","E:\\gwkj\\app\\src\\main\\res\\drawable-hdpi\\weixin.png","e:/apk/"+2+".apk"
				,GenApkUtil.DEBUG,null,null);
		AppInfo app2 = new AppInfo("E:/smstock/","app","13871408467","com.a.b3"
				,"你好","E:\\gwkj\\app\\src\\main\\res\\drawable-hdpi\\rebounded_orange.png","e:/apk/"+3+".apk"
				,GenApkUtil.DEBUG,null,null);
		GenApkUtil.getInstance().addTask(app);
		GenApkUtil.getInstance().addTask(app1);
		GenApkUtil.getInstance().addTask(app2);
//		GenApkUtil.getInstance().addTask(app);
//		GenApkUtil.getInstance().addTask(app);
//		GenApkUtil.getInstance().addTask(app);
//		GenApkUtil.getInstance().addTask(app);
//		GenApkUtil.getInstance().addTask(app);
//		GenApkUtil.getInstance().addTask(app);
//		GenApkUtil.getInstance().addTask(app);
//		GenApkUtil.getInstance().addTask(app);
	}
	
	private GenApkUtil(){}
	
	public static GenApkUtil getInstance() {
		if(instance == null) {
			instance = new GenApkUtil();
			instance.init();
		}
		return instance;
	}
	
	public boolean addTask(AppInfo info) {
		return queues.offer(info);
	}
	
	private void init() {
		mThread = new Thread() {
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted()) {
					try {
						if(CURRENT_RUNBUILD_NUMBER >= MAX_RUNBUILD_NUMBER) {
							logger.info("=======当前任务忙请等待.....    @当前任务数："+CURRENT_RUNBUILD_NUMBER);
							Thread.sleep(1000);
							continue;
						}
						final AppInfo info = queues.take();
						runBuild(info, new BuildResultListener() {
							@Override
							public void start() {
								
							}
							
							@Override
							public void success() {
								
							}
							
							@Override
							public void fail() {
								
							}
							
							@Override
							public void completed() {
								
							}
							@Override
							public void process(String msg, int process) {
							}
						});
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		mThread.start();
	}
	
	public void destroy() {
		mThread.interrupt();
		try {
			mThread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		queues.clear();
		queues=null;
	}
	
	
	public static int runBuild(final AppInfo appInfo,final BuildResultListener listener) {
		BuildResultListener interceptListener = new BuildResultListener() {
			@Override
			public void start() {
				if(listener!=null) {
					listener.start();
				}
				if(appInfo.getListener()!=null) {
					appInfo.getListener().start();
				}
				
				CURRENT_RUNBUILD_NUMBER++;
				logger.info("=========当前运行build任务数："+CURRENT_RUNBUILD_NUMBER+" =============================");
			}
			
			@Override
			public void success() {
				if(listener!=null) {
					listener.success();
				}
				if(appInfo.getListener()!=null) {
					appInfo.getListener().success();
				}
				logger.info("完成一个build任务,当前任务数："+CURRENT_RUNBUILD_NUMBER+"，apk路径 ："+appInfo.getApkpath());
			}

			@Override
			public void process(String msg, int process) {
				if(listener!=null) {
					listener.process(msg,process);
				}
				if(appInfo.getListener()!=null) {
					appInfo.getListener().process(msg,process);
				}
			}

			@Override
			public void fail() {
				if(listener!=null) {
					listener.fail();
				}
				if(appInfo.getListener()!=null) {
					appInfo.getListener().fail();
				}
			}
			
			@Override
			public void completed() {
				if(listener!=null) {
					listener.completed();
				}
				if(appInfo.getListener()!=null) {
					appInfo.getListener().completed();
				}
				if(CURRENT_RUNBUILD_NUMBER>0) {
					CURRENT_RUNBUILD_NUMBER--;
				}
			}
		};
		return runBuild(appInfo.getProjectDir(),appInfo.getModuleName(),appInfo.getAppKey(),appInfo.getPkg(),appInfo.getAppName(),appInfo.getAppIcon(),appInfo.getApkpath(),appInfo.getBuildtype(),appInfo.getVersionCode(),appInfo.getVersionName(),interceptListener);
	}
	/**
	 * run build
	 * @param projectDir
	 * @param moduleName
	 * @param appKey
	 * @param pkg
	 * @param appName
	 * @param appIcon
	 * @param apkpath
	 * @param buildtype
	 * @param vcode
	 * @param vname
	 * @param listener
	 * @return -1当前任务忙请等待，0正常,-2文件复制失败
	 */
	public static int runBuild(String projectDir,String moduleName,String appKey,String pkg,String appName,String appIcon,String apkpath,String buildtype,String vcode,String vname,BuildResultListener listener) {
		if(CURRENT_RUNBUILD_NUMBER >= MAX_RUNBUILD_NUMBER) {
			return -1;
		}
		String fileName = getIconResName(appKey);
		final String newPath = getIconResDir(projectDir,moduleName)+fileName+".png";
		int result = FileUtil.copyFile(appIcon, newPath);
		if(result !=1 ) {
			return -2;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(projectDir+"gradlew.bat ")
		  .append("-p ")
		  .append(projectDir)
		  .append(" -Pline")
		  .append("=")
		  .append(pkg)
		  .append(":")
		  .append(appName.trim())
		  .append(":")
		  .append("@"+ICON_DIR+"/"+fileName)
		  .append(" ")
		  .append("-Papkpath")
		  .append("=")
		  .append(apkpath.trim());
		  if(vcode!=null) {
		  sb.append(" ")
		  .append("-Pvcode")
		  .append("=")
		  .append(vcode);
		  }
		  if(vname!=null) {
		  sb.append(" ")
		  .append("-Pvname")
		  .append("=")
		  .append(vname.trim());
		  }
		  if(appKey!=null) {
		  sb.append(" ")
		  .append("-PappKey")
		  .append("=")
		  .append(appKey.trim());
		  }
		  sb.append(" ")
		  .append(":")
		  .append(moduleName)
		  .append(":assemble")
		  .append("temp")
		  .append(buildtype);
		CMD.runCMD(sb.toString(),new BuildResultImpl(listener) {
			@Override
			public void completed() {
				super.completed();
				Runtime.getRuntime().gc();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				FileUtil.removeFile(newPath);
			}
		});
		return 0;
	}
	
	private static String getIconResName(String appkey) {
		String fileName = "i_smcb_"+appkey+"_"+System.currentTimeMillis();
		return fileName;
	}
	
	private static String getIconResDir(String projectDir,String moduleName) {
		String newPath = projectDir+moduleName+"/src/main/res/"+ICON_DIR+"/";
		return newPath;
	}
	
}
