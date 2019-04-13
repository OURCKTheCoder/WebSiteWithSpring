package top.ourck.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import top.ourck.interceptor.PassportInterceptor;

@Component // DO NOT FORGET!!
public class InterceptorConfiguration implements WebMvcConfigurer {

	@Autowired
	private PassportInterceptor passportInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(passportInterceptor);
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	
}
