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

@Component
public class LoginExceptionHandler implements EventHandler {

	@Autowired
	private MessageService msgService;
	
	/**
	 * 业务上，当一个用户登陆时，若该用户登录失败了，向其发消息。
	 */
	@Override
	public void doHandle(EventModel e) {
		Message msg = new Message();
		int fromId = 1; // From "system"
		int toId = e.getCallerId();
		Date now = new Date();
		
		msg.setFromId(fromId);
		msg.setToId(toId);
		msg.setContent("您的帐户存在登陆异常（密码错误)！上次失败登陆尝试发生在：" + now
				+ " 请考虑修改您的密码！");
		msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId)
											: String.format("%d_%d", toId, fromId));
		msg.setCreatedDate(now);
		msg.setHasRead(0);
		msgService.addMessage(msg);
	}

	@Override
	public List<EventType> getSupportTypes() {
		return Arrays.asList(EventType.LOGIN_EXCEPTION);
	}

}
