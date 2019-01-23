package com.dails.example;

public abstract class Example {
	private String rooturl;
	private String url;
	private String workUrl;

	private Class<?> entityClass;
	
	void initFiles(){
		
	}
	void initJava(){
		
	}
	void initJsp(){
		
	}
	
	
	
	
	
	
	
	/**---------------------------------------***/
	public String getRooturl() {
		return rooturl;
	}
	public void setRooturl(String rooturl) {
		this.rooturl = rooturl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Class<?> getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	public String getWorkUrl() {
		return getRooturl() + "/src/main/java/"+ getUrl();
	}
	public void setWorkUrl(String workUrl) {
		this.workUrl = workUrl;
	}
	
	
	
	
}
