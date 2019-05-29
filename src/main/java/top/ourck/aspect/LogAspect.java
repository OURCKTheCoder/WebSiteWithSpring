package top.ourck.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Before("execution(* top.ourck.web.*.*(..))")
	public void beforeMethod(JoinPoint p) {
		StringBuilder stb = new StringBuilder();
		for(Object obj : p.getArgs()) stb.append(obj);
		logger.info(p.toShortString() + " entered.");
	}

	@After("execution(* top.ourck.web.*.*(..))")
	public void afterMethod(JoinPoint p) {
		logger.info(p.toShortString() + " exited.");
	}
}