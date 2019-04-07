package top.ourck.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public ModelAndView hello(Model model) {
		model.addAttribute("arg", "this is an arg from controller!");
		return new ModelAndView("index");
	}
	
	@RequestMapping("/homeWithArg")
	public ModelAndView helloWithArg(Model model, @RequestParam("arg") String arg) {
		model.addAttribute("arg", arg);
		return new ModelAndView("index");
	}
	
}
