package top.ourck.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import top.ourck.beans.User;
import top.ourck.beans.UserHolder;
import top.ourck.service.NewsService;
import top.ourck.util.ImageUtil;
import top.ourck.util.JSONUtil;

@Controller
@RequestMapping("/news")
public class NewsController {

	// TODO Wire it using conf?
	private static Logger logger = LoggerFactory.getLogger("NewsControllerLogger");
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private UserHolder userHolder;
	
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
	
	@ExceptionHandler
	@ResponseBody
	public String error(Exception e) {
		logger.error("[!] Error: " + e.getMessage());
		return JSONUtil.getJSONString(1, "Upload image failed!");
	}
}