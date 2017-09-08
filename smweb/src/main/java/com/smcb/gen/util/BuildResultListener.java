package com.smcb.gen.util;

public interface BuildResultListener {
	public void start();
	public void success();
	public void fail();
	public void completed();

	public void process(String msg,int process);
}
