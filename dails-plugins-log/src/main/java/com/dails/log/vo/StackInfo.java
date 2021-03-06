package com.dails.log.vo;

import java.util.HashMap;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.dails.log.context.ControllerMethodContext;
import com.google.common.base.Strings;


public class StackInfo {

	private static final String _AUTO_GEN_TRC_PREFIX = "trc_";

	
	protected String traceId;
	//private BasicRestResponse restResponse; 
	protected ClientInfo fromClientInfo;
	protected ClientInfo localClientInfo;

	private ControllerMethodContext ctlMethodCtx;
	
	
	private HashMap<Integer, Throwable> exceptionsOccour;
	
	
	public StackInfo() {
		
	}
	
	
	
	public void clear(){
		if(exceptionsOccour!=null) exceptionsOccour.clear();
	}
	
	/**
	 * 在本次调用stack中是否已经记录了该异常,没有没有则记录
	 * @param e
	 * @return
	 */
	public boolean isContain(Throwable e,boolean autoSave){
		if(e==null) return false;
		if (exceptionsOccour==null) exceptionsOccour=new HashMap<>();
		Integer id=System.identityHashCode(e);
		if (exceptionsOccour.containsKey(id)){
			return true;
		}
		if(autoSave){
			exceptionsOccour.put(id, e);
		}
		return false;
	}
	
	public String getTraceId() {
		if (traceId!=null) return traceId;
		this.traceId = _AUTO_GEN_TRC_PREFIX + UUID.randomUUID().toString();
		return traceId;
	}
	

	public void setTraceId(String traceId) {
		if (Strings.isNullOrEmpty(traceId))
			traceId = _AUTO_GEN_TRC_PREFIX + UUID.randomUUID().toString();
		this.traceId = traceId;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	
	public void setCtlMethodCtx(ControllerMethodContext ctlMethodCtx){
		this.ctlMethodCtx=ctlMethodCtx;
	}
	public ControllerMethodContext getCtlMethodCtx(){
		return this.ctlMethodCtx;
	}

	

	
	
	/**
	 * 获得根部调用者的ip地址
	 * @return
	 */
	public String getFromClientIp(){
		if(fromClientInfo==null) return null;
		return fromClientInfo.getClientIp();
	}
	/**
	 * 获得调用者的ip地址
	 * @return
	 */
	public String getLocalClientIp(){
		if(localClientInfo==null) return null;
		return localClientInfo.getClientIp();
	}



	public ClientInfo getFromClientInfo() {
		return fromClientInfo;
	}



	public void setFromClientInfo(ClientInfo fromClientInfo) {
		this.fromClientInfo = fromClientInfo;
	}



	public ClientInfo getLocalClientInfo() {
		return localClientInfo;
	}



	public void setLocalClientInfo(ClientInfo localClientInfo) {
		this.localClientInfo = localClientInfo;
	}

	
	
	
}
