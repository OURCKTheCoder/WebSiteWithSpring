package top.ourck.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nowcoder.beans.ViewObject;

import top.ourck.beans.News;
import top.ourck.service.NewsService;
import top.ourck.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private UserService userService;

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

	
	
	
	
	
	// ===========================================================
	// ===================== From NowCoder =======================
	// ===========================================================
	
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(@RequestParam(value = "userId", defaultValue = "0") int userId,
    					@RequestParam(value="pop", defaultValue="0") int pop,
                        Model model) {
        model.addAttribute("vos", getNews(0, 0, 10));
        model.addAttribute("pop", pop);
        return "home";
    }

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);

        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
