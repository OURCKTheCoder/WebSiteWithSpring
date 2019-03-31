package top.ourck.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/session")
public class SessionController {

	@RequestMapping("/detail")
	@ResponseBody
	public String getDetail(HttpSession session) {
		StringBuilder stb = new StringBuilder();
		Enumeration<String> names = session.getAttributeNames();
		
		stb.append("<pre>");
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			stb.append("key = ").append(name);
			stb.append(" value = ").append(session.getAttribute(name));
			stb.append("\n");
		}
		stb.append("</pre>");
		
		return stb.toString();
	}
	
	@RequestMapping("/set")
	@ResponseBody
	public String setSessionValue(@RequestParam("value") String value,
								  HttpSession session) {
		session.setAttribute("info", value);
		return "You've set a session value = " + value + " with key = info";
	}
	
	@RequestMapping("/remove")
	@ResponseBody
	public String removeSessionValue(HttpSession session) {
		session.removeAttribute("info");
		return "You've removed a session attribute with key = info";
	}	
	
	@ExceptionHandler
	@ResponseBody
	public String error(Exception e) {
		return "[!] Exception! " + e;
	}
}
