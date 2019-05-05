package top.ourck.web;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ourck.beans.EntityType;
import top.ourck.beans.User;
import top.ourck.beans.UserHolder;
import top.ourck.service.LikeService;
import top.ourck.service.NewsService;
import top.ourck.util.JSONUtil;

@Controller
@RequestMapping("/newsLike")
public class LikeController {

	@Autowired
	private NewsService newsService;
	
	@Autowired
	private UserHolder userHolder;
	
	@Autowired
	private LikeService likeService;
	
	@RequestMapping("/like")
	@ResponseBody
	public String like(@Param("newsId") int newsId) {
		User user = userHolder.getUser();
		if(user == null)
			return JSONUtil.getJSONString(1, "请先登录！");
		
		int userId = user.getId();
		long likeCount = likeService.incLikeOn(userId, EntityType.NEWS, newsId);
		newsService.updateLikeCount(newsId, (int) likeCount);
		return JSONUtil.getJSONString(0, String.valueOf(likeCount));
	}
	
	@RequestMapping("/dislike")
	@ResponseBody
	public String dislike(@Param("newsId") int newsId) {
		User user = userHolder.getUser();
		if(user == null)
			return JSONUtil.getJSONString(1, "请先登录！");
		
		int userId = user.getId();
		long likeCount = likeService.incLikeOn(userId, EntityType.NEWS, newsId);
		newsService.updateLikeCount(newsId, (int) likeCount);
		return JSONUtil.getJSONString(0, String.valueOf(likeCount));
	}
}
