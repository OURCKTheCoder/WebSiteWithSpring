package top.ourck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import top.ourck.beans.Message;

@Mapper
public interface MessageDAO {

	String TABLE_NAME = " message ";
	String INSERT_FIELDS = " from_id, to_id, content, created_date, has_read, conversation_id ";
	String SELECT_FIELDS = " id," + INSERT_FIELDS;
	
	@Insert("INSERT INTO" + TABLE_NAME + "(" + INSERT_FIELDS + ") "
			+ "VALUES (#{fromId}, #{toId}, #{content}, #{createdDate}, #{hasRead}, #{conversationId})")
	int addMessage(Message message);
	
	/**
	 * Get certain user's conversation list.
	 * @param userId
	 * @return
	 */
	List<Message> getConversationListByUserId(int userId,
											  int offset,
											  int limit);
	
}
