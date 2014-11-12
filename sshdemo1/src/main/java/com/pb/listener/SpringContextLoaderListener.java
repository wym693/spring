package com.pb.listener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.orm.hibernate3.BaseDaoImpl;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 自定义监听器，用于容器初始化时保存ApplicationContext
 * @author zhi.li
 *
 */
public class SpringContextLoaderListener implements ServletContextListener {
	private static WebApplicationContext springContext;
	public void contextDestroyed(ServletContextEvent event) {
	}
	public void contextInitialized(ServletContextEvent event) {
		if(springContext == null){
			springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());		
		}
		autoWire();
		
	}
	public static WebApplicationContext getaApplicationContext(){
		return springContext;
	}
	public static void autoWire(){
		if(springContext == null)
			return;
		Map<String, Object> beans = springContext.getBeansOfType(Object.class);
		Map<String, Object> servieceBeans = new HashMap<String, Object>();
		System.out.println("打印容器中所有bean：");
		for (Entry<String, Object> entry : beans.entrySet()) {
			System.out.println(entry.getValue().getClass().getSimpleName());
			if(entry.getKey().lastIndexOf("Service") != -1&&entry.getValue().getClass().getSimpleName().lastIndexOf("Service") != -1){
				servieceBeans.put(entry.getKey(), entry.getValue());
			}
		}
		System.out.println("所有bean打印完毕");
		Map<String, BaseDaoImpl> daoBeans = springContext.getBeansOfType(BaseDaoImpl.class);
		
		for (Entry<String, BaseDaoImpl> entry : daoBeans.entrySet()) {
			System.out.println(entry.getValue().getClass().getSimpleName());
		}
		for (Entry<String, Object> entry : servieceBeans.entrySet()) {	
			System.out.println(entry.getValue().getClass().getSimpleName());
			Method[] methods = entry.getValue().getClass().getDeclaredMethods();				

			for(Method method : methods){				
				if(Pattern.matches("set[\\w]*Dao[\\w]*", method.getName())){
					String daoName = method.getName().substring(3);
					System.out.println("daoName:"+daoName);
					for (Entry<String, BaseDaoImpl> daoEntry : daoBeans.entrySet()) {
						if(daoEntry.getValue().getClass().getSimpleName().contains(daoName)){
							System.out.println(daoName+"找到了匹配的对象："+daoEntry.getValue());	
							try {
								method.invoke(entry.getValue(), daoEntry.getValue());
							} catch (Exception e) {
								System.out.println("自动装配出现异常");
							}	
						}						
					}
				}
			}			
		}	
	}
}
