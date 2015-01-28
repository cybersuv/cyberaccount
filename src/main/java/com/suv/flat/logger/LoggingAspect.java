package com.suv.flat.logger;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.suv.flat.model.User;

@Aspect
public class LoggingAspect {
	static Logger logger=Logger.getLogger(LoggingAspect.class);
	
	
	public void beforeExecution(JoinPoint jp){
		logger.info("Executing " + jp.getTarget().getClass().getName() + " " +  jp.getSignature().getName());
		/*Object[] params=jp.getArgs();
		if(jp.getSignature().toString().contains("com.suv.flat.service.CoreService.authenticateUser")){
		User paramUser=(User)params[0];
		logger.info(jp.getSignature() + " : Before call : " + paramUser.getUser_login());
		}else{
			logger.info("Before calling method : " + jp.getSignature());
		}*/
		
	}
	
	public void afterExecution(JoinPoint jp){
		logger.info("Executed " + jp.getTarget().getClass().getName() + " " +  jp.getSignature().getName());
		Object[] params=jp.getArgs();
		if(jp.getSignature().toString().contains("processLogin")){
			User paramUser=(User)params[0];
			logger.info(" User authenticated : " + paramUser.getUser_login());
		}
		
	}
	
}
