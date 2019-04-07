package top.ourck.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/redirect")
public class RedirectController { // TODO 以隔壁rrtest的控制器做的实验，得改！

	@RequestMapping("/byCmd")
	public String redirect() {
		return "redirect:/rrtest/DefaultUser?request=302TemporaryRedirect";
	}
	
	@RequestMapping("/byResponse")
	public void redirectByresponse(HttpServletRequest request,
								   HttpServletResponse response) {
		try {
			response.sendRedirect("/rrtest/Default?request=302TemporaryRedirect");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/byView/{code}")
	public RedirectView redirectByView(@PathVariable("code") int code) throws Exception {
		RedirectView rv = new RedirectView();
		switch(code) {
			case 301:	
				rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
				rv.setUrl("/rrtest/Default?request=301MOVED_PERMANENTLY");
				break;
			case 302:
				rv.setStatusCode(HttpStatus.FOUND);
				rv.setUrl("/rrtest/Default?request=302FOUND");
				break;
			default:
				throw new Exception("Invalid HTTP redirection code!");
		}
		
		return rv;
	}

	@ExceptionHandler
	@ResponseBody
	public String error(Exception e) {
		return "[!] Exception! " + e;
	}
}
