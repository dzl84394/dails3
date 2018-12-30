package org.dails.plugins.log.interceptor;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.dails.plugins.log.annotation.MyLoggable;
import org.dails.plugins.log.utils.CommonTools;
import org.dails.plugins.log.utils.LoggingUtils;
import org.dails.plugins.log.vo.MethodLogContent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;


/**
 * 日志打印拦截
 * 
 * @author dzl
 *
 */
@Aspect
@Service
@Order(100)
public class LoggingInterceptor {

	@Around(value = "@annotation(org.dails.plugins.log.annotation.MyLoggable)")
	public Object intercept(ProceedingJoinPoint point) throws Throwable {
		Logger logger = LogManager.getLogger(point.getTarget().getClass());
		Method method = CommonTools.getMethod(point);
		long startTimeMillis = System.currentTimeMillis();
		long costMillis = 0;
		Object result = null;
		Throwable exception = null;

		try {
			result = point.proceed();
		} catch (Throwable e) {
			exception = e;
		} finally {
			costMillis = System.currentTimeMillis() - startTimeMillis;
		}
		MethodLogContent logContent=new MethodLogContent(method, point.getArgs(), result, costMillis,exception);
		LoggingUtils.log(getLevel(method), logger,logContent,null);
		
		if (exception != null && ThreadContext.get().getStackInfo().isContain(exception, true)){
			LoggingUtils.log(Level.ERROR, logger,logContent, exception);
		}
		
		if (exception != null) {
			throw exception;
		}

		return result;
	}

	private Level getLevel(Method method) {
		MyLoggable loggableAnnotaion = method.getAnnotation(MyLoggable.class);

		// LoggingSupport
		// s=loggableAnnotaion.annotationType().getAnnotation(LoggingSupport.class);
		String logLevel = loggableAnnotaion.level();
		return Level.getLevel(logLevel);
	}

}
