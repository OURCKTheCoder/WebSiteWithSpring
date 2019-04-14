package top.ourck.web;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ourck.service.UserService;
import top.ourck.util.JSONUtil;

@Controller
@RequestMapping("/login")
public class LoginController {

	// TODO Wire it using conf?
	private static Logger logger = LoggerFactory.getLogger("LoginControllerLogger");
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/reg")
	@ResponseBody
	public String register(@RequestParam("username") String userName,
						   @RequestParam("password") String passwd, 
						   @RequestParam(value="rember", defaultValue="0") int rememberMe) throws UnsupportedEncodingException {
		Map<String, String> info = userService.register(userName, passwd);
		return JSONUtil.getJSONString(0, info);
	}
	
	@RequestMapping("/auth")
	@ResponseBody
	public String auth(@RequestParam("username") String userName,
					   @RequestParam("password") String passwd, 
					   @RequestParam(value="rember", defaultValue="0") int rememberMe,
					   HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, String> info = userService.getAuth(userName, passwd);
		if(info.get("success").equals("true")) { // If login success, give him a cookie.
			Cookie ck = new Cookie("ticket", info.get("ticket"));
			ck.setPath("/");
			if(rememberMe > 0)
				ck.setMaxAge(1000 * 60 * 60 * 24); // 1-day man
			response.addCookie(ck);
		}
		return JSONUtil.getJSONString(0, info);
	}
	
	@RequestMapping("/logout")
	public String logout(@CookieValue("ticket") String ticket) {
		userService.logout(ticket);
		// TODO Clear user's local cookie?
		return "redirect:/";
	}
	
	@ExceptionHandler
	@ResponseBody
	public String error(HttpServletRequest req, HttpServletResponse rep, Exception e) {
		logger.error("[!] Error: " + e.getMessage());
		return JSONUtil.getJSONString(1, "Server side error!");
	}
}
