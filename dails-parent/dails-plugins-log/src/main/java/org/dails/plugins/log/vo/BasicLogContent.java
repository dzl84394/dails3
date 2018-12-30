package org.dails.plugins.log.vo;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletResponse;

import org.dails.plugins.log.utils.CommonTools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;



/**
 * 最基础的日志信息
 * @author dzl
 *
 */
public class BasicLogContent {
	
	private static MySerializeFilter mySerializeFilter = new MySerializeFilter();
	
	//用户自定义信息
	protected String msg;
	protected String traceId;
	protected String hostName;
	protected String exception;
	
	public BasicLogContent (){
		
	}
	
	public void setMsg(String msg){
		this.msg=msg;
	}
	public String getMsg() {
		return msg;
	}

	public String getTraceId() {
		if (traceId != null) return traceId;
		//如果traceId为空,则从stackInfo中获取traceId(stackInfo会自动创建traceId,如果没有traceId)
		traceId = "trc_" + UUID.randomUUID().toString();
		return traceId;
	}
	
	public String getHostName() {
		return CommonTools.getHostName();
	}
	
	public void setException(String desc){
		this.exception=desc;
	}
	public String getException(){
		return this.exception;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this, mySerializeFilter);
	}
	
	static class MySerializeFilter implements ValueFilter {
		private static final String _json_serialize_error = "can't_serialize_to_json_string";

		@Override
		public Object process(Object obj, String name, Object value) {
			if (value == null)
				return value;

			if ("inputs".equals(name)) {
				Object[] inputs = (Object[]) value;
				List<Object> inputsStringList = new ArrayList<>(inputs.length);
				for (Object input : inputs) {
					if (input instanceof ServletResponse){
						//防止serverResponse的getOutputStream被read
						continue;
					}
					try {
						JSON.toJSONString(input);
						inputsStringList.add(input);
					} catch (Exception e) {
						inputsStringList.add(_json_serialize_error);
					}
				}
				return inputsStringList;
			}
			if ("output".equals(name)) {
				try {
					JSON.toJSONString(value);
					return value;
				} catch (Exception e) {
					e.printStackTrace();
					return _json_serialize_error;
				}
			}
			return value;
		}

	}
	
}
