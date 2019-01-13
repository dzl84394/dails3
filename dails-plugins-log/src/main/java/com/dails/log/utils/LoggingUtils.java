package com.dails.log.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.dails.log.vo.BasicLogContent;


public class LoggingUtils {

	public static void log(Level level,Logger logger,BasicLogContent content,Throwable e){
		logger.log(level,content.toString(),e);
	}
	
	
	public static void log(Level level,Logger logger,String msg,Throwable e){
		BasicLogContent content=new BasicLogContent();
		content.setMsg(msg);
		if (e!=null){
			content.setException(CommonTools.getExceptionSimpleInfo(e));
		}
		log(level,logger,content,e);
	}
	
	public static void debug(Logger logger,String msg){
		log(Level.DEBUG,logger,msg,null);
	}
	public static void info(Logger logger,String msg){
		log(Level.INFO,logger,msg,null);
	}
	public static void warn(Logger logger,String msg){
		log(Level.WARN,logger,msg,null);
	}
	public static void warn(Logger logger,String msg,Throwable e){
		log(Level.WARN,logger,msg,e);
	}
	public static void error(Logger logger,String msg,Throwable e){
		log(Level.ERROR,logger,msg,e);
	}
	
	
	
}
