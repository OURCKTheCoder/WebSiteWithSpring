package top.ourck.web;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ourck.async.EventModel;
import top.ourck.async.EventProducer;
import top.ourck.async.EventType;
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
	
	@Autowired
	private EventProducer eventProducer;
	
	@ResponseBody
	public String like(@Param("newsId") int newsId) {
		User user = userHolder.getUser();
		if(user == null)
			return JSONUtil.getJSONString(1, "请先登录！");
		
		int userId = user.getId();
		long likeCount = likeService.incLikeOn(userId, EntityType.NEWS, newsId); // 放入Redis
		newsService.updateLikeCount(newsId, (int) likeCount); // 更新到磁盘数据库
		return JSONUtil.getJSONString(0, String.valueOf(likeCount));
	}
	
	@RequestMapping("/like")
	@ResponseBody
	public String likeAsync(@Param("newsId") int newsId) {
		User user = userHolder.getUser();
		if(user == null)
			return JSONUtil.getJSONString(1, "请先登录！");
		
		/* --- Using async event framework instead. --- */
		
		int userId = user.getId();
		long likeCount = likeService.incLikeOn(userId, EntityType.NEWS, newsId);
		newsService.updateLikeCount(newsId, (int) likeCount); // 更新到磁盘数据库
		EventModel event = new EventModel();
		event.setCallerId(user.getId()) 
			 .setEntityType(EntityType.NEWS)
			 .setEntityId(newsId)
			 .setType(EventType.LIKE)
			 .setEntityOwnerId(newsService.getNewsById(newsId).getUserId());
		eventProducer.fireEvent(event);
		return JSONUtil.getJSONString(0, String.valueOf(likeCount));
	}
	
	@RequestMapping("/dislike")
	@ResponseBody
	public String dislike(@Param("newsId") int newsId) {
		User user = userHolder.getUser();
		if(user == null)
			return JSONUtil.getJSONString(1, "请先登录！");
		
		int userId = user.getId();
		long likeCount = likeService.incDislikeOn(userId, EntityType.NEWS, newsId);
		newsService.updateLikeCount(newsId, (int) likeCount);
		return JSONUtil.getJSONString(0, String.valueOf(likeCount));
	}
}
