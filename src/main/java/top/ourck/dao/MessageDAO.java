package top.ourck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import top.ourck.beans.Message;

@Mapper
public interface MessageDAO {

	String TABLE_NAME = " message ";
	String FIELDS = " from_id, to_id, content, created_date, has_read, conversation_id ";
	String SELECT_FIELDS = " id," + FIELDS;
	
	@Insert("INSERT INTO" + TABLE_NAME + "(" + FIELDS + ") "
			+ "VALUES (#{fromId}, #{toId}, #{content}, #{createdDate}, #{hasRead}, #{conversationId})")
	int addMessage(Message message);
	
	/**
	 * Get certain user's conversation list.
	 * 三次排序的SQL。
	 * @param userId
	 * @return
	 */
	@Select("SELECT" + FIELDS + ", count(id) as id " 			// TODO 本来是as cnt的，但是发现Message里边有一个id域用不到，所以干脆把cnt作为id存进去。这合理吗？
			+ "FROM "
			+ "( " 												// 嵌套查询：先查与该用户有关的message，id越大，消息越新。
					+ "SELECT * FROM" + TABLE_NAME
					+ "WHERE from_id = #{userId} OR to_id = #{userId} "
					+ "ORDER BY id DESC "
			+ ") tt " 											// As a "Temp Table".
			+ "GROUP BY conversation_id "						// TODO 执行GROUP BY后，将得到每个会话id下的最新消息。（？）
			+ "ORDER BY id DESC "								// id越大，该次会话越新。
			+ "LIMIT #{offset}, #{limit}")
	List<Message> getConversationListByUserId(@Param("userId") int userId,
											  @Param("offset") int offset,
											  @Param("limit") int limit);
	
	@Select("SELECT" + SELECT_FIELDS + "FROM" + TABLE_NAME
			+ "WHERE conversation_id = #{conversationId} "
			+ "ORDER BY id DESC "
			+ "LIMIT #{offset}, #{limit}")
	List<Message> getConversationDetail(@Param("conversationId") String conversationId,
										@Param("offset") int offset,
										@Param("limit") int limit);
	
	@Select("SELECT count(id) FROM" + TABLE_NAME + "WHERE to_id = #{userId} AND conversation_id = #{conversationId}")
	int getUnreadCountByConversationId(@Param("userId") int userId,
									   @Param("conversationId") String conversationId);
										
}
