package com.dails.log.context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.dails.log.vo.StackInfo;

public class ThreadContext {
	/**
	 * 是http的header里面带过来，报文的头文件过来，要做适应性修改
	 */

	private static ThreadContext instance;

	public static ThreadContext get() {
		if (instance == null)
			instance = new ThreadContext();
		return instance;
	}

	
	private static Map<String, AtomicInteger> counter;
	
	private static ThreadLocal<StackInfo> localStackInfo;

	private ThreadContext() {
		counter = new HashMap<String,AtomicInteger>();
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

	public AtomicInteger getCurrency(String key) {
		AtomicInteger atomic = counter.get(key);
		if (atomic == null) {
			atomic = new AtomicInteger(0);
		}
		return atomic;
	}
	
	public void setCurrency(String key,AtomicInteger atomic) {
		counter.put(key, atomic);
	}


}
