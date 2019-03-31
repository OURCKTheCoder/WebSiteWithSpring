package top.ourck.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThymeleafTemplateController {

	@RequestMapping("chd")
	public String hello(Model model) {
		model.addAttribute("arg", "this is an arg from controller!");
		return "index";
	}
	
	@RequestMapping("chdWithArg")
	public String helloWithArg(Model model, @RequestParam("arg") String arg) {
		model.addAttribute("arg", arg);
		return "index";
	}
	
}
