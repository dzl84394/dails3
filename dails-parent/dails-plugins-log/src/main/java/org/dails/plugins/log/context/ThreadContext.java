package org.dails.plugins.log.context;

import org.dails.plugins.log.vo.StackInfo;

public class ThreadContext {

	private static ThreadContext instance;

	public static ThreadContext get() {
		if (instance == null)
			instance = new ThreadContext();
		return instance;
	}

	
	
	
	private static ThreadLocal<StackInfo> localStackInfo;

	private ThreadContext() {
		localStackInfo = new ThreadLocal<>();
	}

	public void clear() {
		if (localStackInfo.get() != null) {
			localStackInfo.get().clear();
		}
		localStackInfo.set(null);
	}

	public void putTraceId(String trcId) {
		StackInfo info = getStackInfo();
		if (info == null) {
			info = new StackInfo();
		}
		info.setTraceId(trcId);
		saveInfo(info);
	}
	/*
	 * public void putRestResponseContent(BasicRestResponse response){ StackInfo
	 * info= getStackInfo(); if (info==null){ info=new StackInfo(); }
	 * info.setRestResponse(response); saveInfo(info); }
	 */

	public StackInfo getStackInfo() {
		StackInfo stackInfo = localStackInfo.get();
		if (stackInfo == null) {
			stackInfo = new StackInfo();
			saveInfo(stackInfo);
		}

		return stackInfo;
	}

	public void saveInfo(StackInfo v) {
		localStackInfo.set(v);
	}

}
