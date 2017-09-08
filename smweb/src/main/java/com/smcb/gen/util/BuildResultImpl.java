package com.smcb.gen.util;

public class BuildResultImpl implements BuildResultListener {


	private BuildResultListener listener;
	
	public BuildResultImpl(){}
	public BuildResultImpl(BuildResultListener listener) {
		this.listener= listener;
	}
	
	@Override
	public void start() {
		if(listener!=null) {
			listener.start();
		}
	}

	@Override
	public void success() {
		if(listener!=null) {
			listener.success();
		}
	}

	@Override
	public void fail() {
		if(listener!=null) {
			listener.fail();
		}
	}

	@Override
	public void completed() {
		if(listener!=null) {
			listener.completed();
		}
	}

	@Override
	public void process(String msg, int process) {
		if(listener!=null) {
			listener.process(msg,process);
		}
	}

}
