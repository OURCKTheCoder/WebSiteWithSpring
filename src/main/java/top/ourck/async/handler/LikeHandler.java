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
public class LikeHandler implements EventHandler {

	private static final List<EventType> SUPPORTED_EVENT_TYPES;
	static {
		// Arrays.asList() returns a FIXED list, which means you CANNOT add() anything into it.
		SUPPORTED_EVENT_TYPES = Arrays.asList(EventType.LIKE);
	}

	@Autowired
	private MessageService msgService;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private UserService userService;


	/**
	 * 业务上，要给用户发送过去一个信息，告知他的消息被人赞了。
	 */
	@Override
	public void doHandle(EventModel e) {
		System.out.println("LIKE EventHandler Get an event! " + e);
		Message msg = new Message();
		int fromId = BizConstUtil.USER_SYSTEM_UID; // System notification from "system".
		int toId = e.getEntityOwnerId();
		
		msg.setFromId(fromId);
		msg.setToId(toId);
		msg.setContent("你的消息：" + newsService.getNewsById(e.getEntityId()).getTitle()
				+ " 被人(" + userService.getUser(e.getCallerId()).getName() + ")赞了！+1s！");
		msg.setCreatedDate(new Date());
		msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId)
											: String.format("%d_%d", toId, fromId));
		msg.setHasRead(0);
		msgService.addMessage(msg);
	}

	@Override
	public List<EventType> getSupportTypes() {
		return SUPPORTED_EVENT_TYPES;
	}

}
