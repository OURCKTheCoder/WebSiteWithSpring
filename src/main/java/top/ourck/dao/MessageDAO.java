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
	 * @param userId
	 * @return
	 */
	@Select("SELECT" + FIELDS + ", count(id) as id "
			+ "FROM "
			+ "( "
					+ "SELECT * FROM" + TABLE_NAME + "WHERE from_id = #{userId} OR to_id = #{userId} "
					+ "ORDER BY id ASC "
			+ ")tt " // As a "Temp Table".
			+ "GROUP BY conversation_id "
			+ "ORDER BY id ASC "
			+ "LIMIT #{offset}, #{limit}")
	List<Message> getConversationListByUserId(@Param("userId") int userId,
											  @Param("offset") int offset,
											  @Param("limit") int limit);
	
	@Select("SELECT" + SELECT_FIELDS + "FROM" + TABLE_NAME
			+ "WHERE conversation_id = #{conversationId} "
			+ "ORDER BY id ASC "
			+ "LIMIT #{offset}, #{limit}")
	List<Message> getConversationDetail(@Param("conversationId") String conversationId,
										@Param("offset") int offset,
										@Param("limit") int limit);
	
	@Select("SELECT count(id) FROM" + TABLE_NAME + "WHERE to_id = #{userId} AND conversation_id = #{conversationId}")
	int getUnreadCountByConversationId(@Param("userId") int userId,
									   @Param("conversationId") String conversationId);
										
}
