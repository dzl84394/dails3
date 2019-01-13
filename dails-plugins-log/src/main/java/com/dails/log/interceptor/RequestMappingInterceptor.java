package com.dails.log.interceptor;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.dails.log.context.ThreadContext;
import com.dails.log.utils.CommonTools;
import com.dails.log.utils.LoggingUtils;
import com.dails.log.vo.MethodLogContent;

/**
 * 日志打印拦截
 * 
 * @author dzl
 *
 */
@Aspect
@Service
@Order(100)
public class RequestMappingInterceptor {

	@Autowired
	GaugeService gaugeService;
	// counterService有加1，减1功能
	@Autowired
	CounterService counterService;

	@Autowired
	private MetricReader metricReader;

	@Around(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object intercept(ProceedingJoinPoint point) throws Throwable {
		Logger logger = LogManager.getLogger(point.getTarget().getClass());
		Method method = CommonTools.getMethod(point);
		long startTimeMillis = System.currentTimeMillis();
		long costMillis = 0;
		counterService.increment("total_concurrency");
		counterService.increment(method.getDeclaringClass().getSimpleName() + "#" + method.getName() + "_concurrency");
		Metric<Long> concurrency = (Metric<Long>) metricReader.findOne(
				"counter." + method.getDeclaringClass().getSimpleName() + "#" + method.getName() + "_concurrency");
		Metric<Long> totalConcurrency = (Metric<Long>) metricReader.findOne("counter.total_concurrency");

		Object result = null;
		Throwable exception = null;

		try {
			result = point.proceed();
		} catch (Throwable e) {
			exception = e;
		} finally {
			costMillis = System.currentTimeMillis() - startTimeMillis;
		}
		MethodLogContent logContent = new MethodLogContent(method, point.getArgs(), result, costMillis,
				concurrency.getValue(), totalConcurrency.getValue(), exception);

		LoggingUtils.log(getLevel(method), logger, logContent, null);

		counterService.decrement(method.getDeclaringClass().getSimpleName() + "#" + method.getName() + "_concurrency");
		counterService.decrement("total_concurrency");

		if (exception != null && ThreadContext.get().getStackInfo().isContain(exception, true)) {
			LoggingUtils.log(Level.ERROR, logger, logContent, exception);
		}

		if (exception != null) {
			throw exception;
		}

		return result;
	}

	private Level getLevel(Method method) {
		return Level.getLevel("INFO");
	}

}
