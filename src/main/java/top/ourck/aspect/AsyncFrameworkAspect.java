package top.ourck.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 异步框架的日志切面
 * @author ourck
 */
@Aspect
@Component
public class AsyncFrameworkAspect {

	private static final Logger logger = LoggerFactory.getLogger(AsyncFrameworkAspect.class);
	
	@Before("execution(* top.ourck.async.handler.*.*(..))")
	public void beforeEventHandling(JoinPoint p) {
		StringBuilder stb = new StringBuilder();
		for(Object obj : p.getArgs())
			stb.append(obj);
		logger.info(" [+] " + p.toShortString() + " entered, with args = " + p.getArgs());
	}
	
	@After("execution(* top.ourck.async.handler.*.*(..))")
	public void afterMethod(JoinPoint p) {
		logger.info(" [-] " + p.toShortString() + " exited.");
	}
}
