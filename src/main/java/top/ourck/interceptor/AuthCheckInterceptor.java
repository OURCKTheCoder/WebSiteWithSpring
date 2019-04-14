package top.ourck.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import top.ourck.beans.UserHolder;

@Component
public class AuthCheckInterceptor implements HandlerInterceptor {

	@Autowired
	private UserHolder userHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(userHolder.getUser() == null) {
			response.sendRedirect("/");
			return false;
		}
		return true;
	}

}
