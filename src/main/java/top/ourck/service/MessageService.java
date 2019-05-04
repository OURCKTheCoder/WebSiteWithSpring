package top.ourck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.ourck.beans.Message;
import top.ourck.dao.MessageDAO;

@Service
public class MessageService {
	
	@Autowired
	private MessageDAO messageDAO;
	
	public void addMessage(Message msg) {
		messageDAO.addMessage(msg);
	}
	
	public List<Message> getConversationList(int userId, int offset, int limit) {
		return messageDAO.getConversationListByUserId(userId, offset, limit);
	}
	
	public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
		return messageDAO.getConversationDetail(conversationId, offset, limit);
	}
	
	/**
	 * Get certain user's unread message count at certain conversation
	 * @param uid User's id
	 * @param conversationId Conversation's id
	 * @return unread message's count
	 */
	public int getUnreadCountAt(int uid, String conversationId) {
		return messageDAO.getUnreadCountByConversationId(uid, conversationId);
	}
}
