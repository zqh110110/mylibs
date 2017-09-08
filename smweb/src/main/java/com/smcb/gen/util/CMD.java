package com.smcb.gen.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
 
public class CMD {
	
	private static Logger logger = Logger.getLogger(CMD.class);
	
	public static void main(String[] args) {
		CMD.runCMD("e:/stocktrade/Tradegradlew.bat -p E:\\stocktrade\\Trade -Pline=com.a.b:a1:@mipmap/ic_launcher_round -Papkpath=e:/ab.apk :app:assembleA1Debug",new BuildResultListener() {
			@Override
			public void start() {
				logger.info("apk开始生成。。。。");
			}
			@Override
			public void success() {
				logger.info("apk生成成功");
			}

            @Override
            public void process(String msg, int process) {

            }

            @Override
			public void fail() {
				logger.info("apk生成失败");
			}
			@Override
			public void completed() {
				logger.info("执行结束");
			}
		});
	}
     
    public static Process runCMD(String cmd,BuildResultListener listener){
        Process p = null;
        try {
            cmd = "cmd.exe /c start /b "+cmd;
            logger.info(cmd);
            if(listener!=null) {
            	listener.start();
            }
            p = Runtime.getRuntime().exec(cmd);
            new Thread(new cmdResult(p.getInputStream(),listener)).start();
            new Thread(new cmdResult(p.getErrorStream(),listener)).start();
            p.getOutputStream().close();
        } catch (Exception e) {
            logger.info("命令行出错！");
            e.printStackTrace();
            if(listener!=null) {
            	listener.fail();
            	listener.completed();
            }
        }
        return p;
    }
     
    static class cmdResult implements Runnable{
        private InputStream ins;
        private BuildResultListener listener;
         
        public cmdResult(InputStream ins,BuildResultListener listener){
            this.ins = ins;
            this.listener = listener;
        }
 
        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(ins,Charset.forName("GBK")));
                String line = null;
                boolean processPrint = true;
                while ((line=reader.readLine())!=null) {
                    if(listener!=null) {
                        if (processPrint) {
                            listener.process(line, 0);
                        }
                        if (line.contains(": Total time:")) {
                            processPrint = false;
                        }
                    }
                    if(line.equals(GenApkUtil.BUILD_SUCCESSFUL)) {
                    	if(listener!=null) {
                    		listener.success();
                    	}
                    } else if(line.equals(GenApkUtil.BUILD_FAILED)||line.startsWith("系统找不到文件")) {
                    	if(listener!=null) {
                    		listener.fail();
                    	}
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(listener!=null) {
            	listener.completed();
            }
        }
         
    }
 
}