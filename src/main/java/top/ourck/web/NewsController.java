package top.ourck.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nowcoder.beans.ViewObject;

import top.ourck.async.EventModel;
import top.ourck.async.EventProducer;
import top.ourck.async.EventType;
import top.ourck.beans.Comment;
import top.ourck.beans.EntityType;
import top.ourck.beans.News;
import top.ourck.beans.User;
import top.ourck.beans.UserHolder;
import top.ourck.service.CommentService;
import top.ourck.service.NewsService;
import top.ourck.service.UserService;
import top.ourck.util.ImageUtil;
import top.ourck.util.JSONUtil;

@Controller
@RequestMapping("/news")
public class NewsController {

	private static Logger logger = LoggerFactory.getLogger("NewsControllerLogger");
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserHolder userHolder;
	
	@Autowired
	private EventProducer eventProduer;
	
	@RequestMapping("/image")
	public void getImage(@RequestParam("name") String fileName,
						 HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		FileInputStream fos = new FileInputStream(new File(ImageUtil.IMAGE_SAVE_PATH + fileName));
		StreamUtils.copy(fos, response.getOutputStream());
	}
	
	@RequestMapping("/upload")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
		String url = newsService.saveImageToLocal(file);
		if(url == null)
			return JSONUtil.getJSONString(1, "Upload failed: url = null!");
		return JSONUtil.getJSONString(0, url);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String addNews(@RequestParam("title") String title,
						  @RequestParam("image") String image,
						  @RequestParam("link") String link) {
		User user = userHolder.getUser();
		int userId;
		if(user == null) {
			// Regard as a anonymous user.
			userId = 1;
		}
		else {
			userId = user.getId();
		}
		newsService.addNews(title, link, image, userId);
		return JSONUtil.getJSONString(0);
	}
	
	@GetMapping("/show/{newsId}")
	public String showNews(@PathVariable("newsId") int newsId, Model model) {
		News news = newsService.getNewsById(newsId);
		User user = userService.getUser(news.getUserId());
		List<Comment> cmtList = commentService.getChildComment(newsId, EntityType.NEWS);
		List<ViewObject> viewObjects = new LinkedList<ViewObject>();
		for(Comment c : cmtList) {
			ViewObject vo = new ViewObject();
			vo.set("user", userService.getUser(c.getUserId()));
			vo.set("comment", c);
			viewObjects.add(vo);
		}
		model.addAttribute("comments", viewObjects);
		model.addAttribute("news", news);
		model.addAttribute("owner", user);
		model.addAttribute("user", userHolder.getUser());
		return "detail";
	}
	
	@PostMapping("/addComment")
	public String addComment(@RequestParam("newsId") int newsId, @RequestParam("content") String content) {
		Comment comment = new Comment();
		comment.setCreateDate(new Date());
		comment.setEntityType(EntityType.NEWS);
		comment.setContent(content);
		comment.setEntityId(newsId);
		comment.setStatus(1);
		comment.setUserId(userHolder.getUser().getId());
		commentService.addComment(comment);
		
		 // 更新评论数量，并异步通知新闻所有者
		int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
		newsService.updateCommentCount(comment.getEntityId(), count);
		
		EventModel e = new EventModel();
		e.setCallerId(userHolder.getUser().getId())
		 .setEntityId(newsId)
		 .setEntityOwnerId(newsService.getNewsById(newsId).getUserId())
		 .setEntityType(EntityType.NEWS)
		 .setType(EventType.COMMENT);
		eventProduer.fireEvent(e);
		
		return "redirect:/news/show/" + newsId;
		// TODO 评论分页？
		// TODO +敏感词过滤模块：使用DFA。
	}
	
	@ExceptionHandler
	@ResponseBody
	public String error(Exception e) {
		logger.error("[!] Error: " + e.getMessage());
		return JSONUtil.getJSONString(1, e.toString());
	}
}
