package com.anymindgroup.bitcoinmanagement.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Before("execution(* com.anymindgroup.bitcoinmanagement..*(..)))")
    public void getlogMethodBeforeExecution(JoinPoint joinPoint) throws Throwable {
        
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		//Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();
        
        //Log method
        log.info("Bitcoin management Logging AOP : Before "+className+"."+methodName);
    }
	
	@After("execution(* com.anymindgroup.bitcoinmanagement..*(..)))")
    public void getlogMethodAfterExecution(JoinPoint joinPoint) throws Throwable {
        
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		//Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();
        
        //Log method
		log.info("Bitcoin management Logging AOP : After "+className+"."+methodName);
    }
}
