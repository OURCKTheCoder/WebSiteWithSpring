package top.ourck.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import top.ourck.async.EventModel;
import top.ourck.async.EventType;
import top.ourck.beans.Message;
import top.ourck.service.MessageService;
import top.ourck.service.NewsService;
import top.ourck.service.UserService;
import top.ourck.util.BizConstUtil;

@Component
public class CommentHandler implements EventHandler {

	private static final List<EventType> SUPPORTED_TYPES = Arrays.asList(EventType.COMMENT);
	
	@Autowired
	private MessageService msgService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NewsService newsService;
	
	@Override
	public void doHandle(EventModel e) {
		int resUid = e.getCallerId();
		int fromUid = BizConstUtil.USER_SYSTEM_UID;
		int toUid = e.getEntityOwnerId();
		int targetNewsId = e.getEntityId();
		
		Message msg = new Message();
		msg.setCreatedDate(new Date());
		msg.setContent("有人（@" + userService.getUser(resUid).getName() + "）评论了你的消息：\""
						+ newsService.getNewsById(targetNewsId).getTitle() + "\"，快去看看吧！");
		msg.setConversationId(fromUid < toUid ? fromUid + "_" + toUid
											  : toUid + "_" + fromUid);
		msg.setFromId(fromUid);
		msg.setToId(toUid);
		msgService.addMessage(msg);
	}

	@Override
	public List<EventType> getSupportTypes() {
		return SUPPORTED_TYPES;
	}

	
}
