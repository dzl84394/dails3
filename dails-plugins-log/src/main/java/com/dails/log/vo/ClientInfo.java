package com.dails.log.vo;


import com.alibaba.fastjson.annotation.JSONField;

public class ClientInfo {

	private String clientId;

	@JSONField(name = "clientIP")
	private String clientIp;

	@JSONField(name = "clientUA")
	private String ua;

	private String appName;
	private String channelId;
	
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	
}
