package top.ourck.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import top.ourck.interceptor.AuthCheckInterceptor;
import top.ourck.interceptor.PassportInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

	@Autowired
	private PassportInterceptor passportInterceptor;
	
	@Autowired
	private AuthCheckInterceptor authCheckInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(passportInterceptor);
		registry.addInterceptor(authCheckInterceptor).addPathPatterns(new String[] {"/user/*", "/user/*/"}); // TODO 为什么第一个路径不包括第二个？
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	
}
