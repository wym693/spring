package aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class ServiceLogging {
	private Log log = LogFactory.getLog(this.getClass());
	
	public void beforeService(JoinPoint joinPoint){		
		log.info("前置增强处理被执行");
		log.info("连接点对象："+joinPoint.getTarget().getClass().getSimpleName());
		log.info("连接点方法："+joinPoint.getSignature());
		log.info("连接点方法参数："+joinPoint.getArgs()[0]);
	}
	public void after(){
		log.info("最终增强处理被执行");
	}
	public void afterReturning(Object returnVal){
		log.info("后置增强处理被执行");
		log.info("后置增强处理：方法返回值为："+returnVal);
	}
	public void afterThrowing(Exception ex){
		log.info("业务方法抛出了异常，异常增强处理被执行");
		log.info("抛出的异常为："+ex.getMessage());
	}
	public Boolean around(ProceedingJoinPoint pjp) throws Throwable{
		log.info("环绕增强处理：目标方法执行前织入");
		log.info("环绕增强处理：目标方法的参数："+pjp.getArgs()[0]);
		Boolean b = null;
		if(true){
			b = (Boolean)pjp.proceed(pjp.getArgs());
		}
		log.info("环绕增强处理：目标方法的返回值为："+b);
		log.info("环绕增强处理：目标方法执行后织入");
		return b;
	}
}
