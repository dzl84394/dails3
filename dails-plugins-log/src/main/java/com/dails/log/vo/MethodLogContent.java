package com.dails.log.vo;

import java.lang.reflect.Method;

import com.alibaba.fastjson.annotation.JSONField;
import com.dails.log.utils.CommonTools;


public class MethodLogContent extends BasicLogContent {
	
	public static final String _NULL_OUTPUT="_NULL_RETURN";
	
	@JSONField(ordinal = 13)
	protected Long costTimeMillis;
	@JSONField(ordinal = 17)
	protected String method;
	@JSONField(ordinal = 18)
	protected Object[] inputs;
	@JSONField(ordinal = 19)
	protected Object output;
	
	@JSONField(ordinal = 10)
	protected Object concurrency;
	@JSONField(ordinal = 10)
	protected Object totalConcurrency;
	
	
	protected Throwable throwable;
	
	
	
	public MethodLogContent(Method method,Object[] inputs,Object output,long costTimeMillis,Long concurrency,Long totalConcurrency,Throwable throwable){
		super();
		this.method= method.getDeclaringClass().getName()+"."+method.getName();
//		this.method=CommonTools.getMethodDeclareInfo(method);
		this.costTimeMillis=costTimeMillis;
		this.inputs=inputs;
		this.output=output;
		this.throwable=throwable;
		this.concurrency=concurrency;
		this.totalConcurrency=totalConcurrency;
		
		
	}
	public MethodLogContent(Method method,Object[] inputs,Object output,long costTimeMillis,Throwable throwable){
		super();
		this.method= method.getDeclaringClass().getName()+"."+method.getName();
//		this.method=CommonTools.getMethodDeclareInfo(method);
		this.costTimeMillis=costTimeMillis;
		this.inputs=inputs;
		this.output=output;
		this.throwable=throwable;
	}
	

	public Long getCostTimeMillis() {
		return costTimeMillis;
	}

	public String getMethod() {
		return method;
	}

	public Object[] getInputs() {
		return inputs;
	}

	public Object getOutput() {
		if (output==null) return _NULL_OUTPUT;
		return output;
	}
	public String getExceptionInfo(){
		if (throwable==null){
			return null;
		}
		return CommonTools.getExceptionSimpleInfo(throwable);
	}

	public Object getConcurrency() {
		return concurrency;
	}

	public Object getTotalConcurrency() {
		return totalConcurrency;
	}
}
