package top.ourck.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import top.ourck.beans.LoginTicket;
import top.ourck.beans.UserHolder;
import top.ourck.beans.User;
import top.ourck.service.LoginTicketService;
import top.ourck.service.UserService;

@Component
public class PassportInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private LoginTicketService loginTicketService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * <b>为什么要使用ThreadLocal存储待使用的User？</b>
	 * <br>
	 * <ul>
	 * <li>
	 * 因为不止一个线程工作在服务器上，
	 * 而又有“每个线程处理自己的登陆会话”这一需求，
	 * 所以使用线程自己的ThreadLocal来存储User对象。
	 * </li>
	 * 
	 * <li>
	 * 而与此同时，作为@Component的Interceptor是单例的。
	 * 这样直接搞一个共享的User变量的话很不线程安全。
	 * </li>
	 * </ul>
	 */
	@Autowired
	private UserHolder userHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) throws Exception {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) return true;
		
		for(Cookie ck : cookies) {
			if(ck.getName().equals("ticket")) {
				// 1. Get ticket string.
				String tStr = ck.getValue();
				LoginTicket t = loginTicketService.getTicket(tStr);
				if(t == null) break;
				
				// 2. Validate this ticket.
				Date deadline = t.getExpired();
				Date now = new Date();
				if(deadline.compareTo(now) < 0 || t.getStatus() != 0) {
					// If this ticket has expired.
					// TODO Delete this ticket.
					break;
				}
				
				// 3. Get this ticket's owner.
				int uid = t.getUserId();
				User user = userService.getUser(uid);
				userHolder.setUser(user);
				
				break;
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response,
						   Object handler,
						   ModelAndView modelAndView) throws Exception {
		User user = userHolder.getUser();
		if(modelAndView != null && user != null) {
			modelAndView.addObject("verifiedUser", user);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		userHolder.clear();
	}

}
