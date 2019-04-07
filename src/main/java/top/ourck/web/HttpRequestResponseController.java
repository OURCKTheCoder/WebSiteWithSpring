package top.ourck.web;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ourck.service.IndexService;

@Controller
@RequestMapping("/rrtest")
public class HttpRequestResponseController {

	@Autowired
	private IndexService indexService;
	
	@RequestMapping("/{userName}")
	@ResponseBody
	public String index(@PathVariable("userName") String userName,
						@RequestParam("request") String request) {
		if(userName.equals("ERROR")) throw new RuntimeException("Exception test!");
		return "<h1>Hello, " + userName + "! Your request is " + request + "</h1>"
				+ indexService.getMessage();
	}
	
	@ExceptionHandler
	@ResponseBody
	public String error(Exception e) {
		return "Oops! somthing happened: " + e;
	}
	
	@RequestMapping("/getCookie")
	@ResponseBody
	public String getCookie(HttpServletRequest request,
							HttpServletResponse response) {
		Cookie ck = new Cookie("status", "WHO-LET-THE-DOGS-OUT!!!");
		response.addCookie(ck);
		return "You've got a cookie! Go to /detail to check it out!";
	}
	
	@RequestMapping("/removeCookie")
	@ResponseBody
	public String removeCookie(HttpServletRequest request,
							   HttpServletResponse response) {
		Cookie ck = new Cookie("status", null);
		ck.setMaxAge(0);
		ck.setPath("/");
		response.addCookie(ck);
		return "You've removed a cookie! Go to /detail to check it out!";
	}
	
	@RequestMapping("/detail")
	@ResponseBody
	public String detail(HttpServletRequest request,
						 HttpServletResponse response,
						 @Nullable @CookieValue("status") String cookieVal) {
		Enumeration<String> names = request.getHeaderNames();
		StringBuilder stb = new StringBuilder();
		
		stb.append("<pre>");
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			stb.append(name + "\t");
			stb.append(request.getHeader(name));
			stb.append("\n");
		}

		if(cookieVal != null) {
			stb.append("\n----------------------\n");
			stb.append("It seems you have a \"status\" cookie: ");
			stb.append(cookieVal);
		}

		stb.append("</pre>");
		return stb.toString();
	}
}
