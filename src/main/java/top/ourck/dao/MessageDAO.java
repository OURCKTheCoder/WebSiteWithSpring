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
	 * Get certain user's conversation list & digest.
	 * @deprecated 该查询试图从一次聚集查询中<b>选择出非聚集列</b>。<br>
	 * 尽最大努力减少查询次数，这很好。<br>
	 * 但这无论是在数据库优化上（MySQL新版本默认要求ONLY_FULL_GROUP_BY）<br>
	 * 还是在聚集查询的语义上<br>
	 * 都是不正确的。<br>
	 * 要完成同样的功能，请：
	 * <ol>
	 * <li>使用getConversationsByUser()取得对话列表；</li>
	 * <li>再从列表中逐个用ID使用getConversationDigest()和getConversationMsgCount()取得对话摘要。</li>
	 * </ol>
	 * <h6> - 关于性能 - </h6>
	 * 虽然看上去执行了更多的SQL，
	 * 但实际上上面的替代方案
	 * 与本方法在实际执行的查询中的50%情况下耗时差不多<br>
	 * （都是0.0012~0.0017sec）。<br>
	 * 因为本方法是一次三次排序的SQL，
	 * 并使用了嵌套查询和非ONLY_FULL_GROUP_BY，
	 * 因此一次SQL的方案仍然耗时较大。<br>
	 * 不过若是多次执行本方案中的SQL倒是能将时间开销降至一半(0.00050~0.00080），是因为缓冲吗？
	 * @param userId 用户ID
	 * @param offset 查询起始偏移
	 * @param limit 查询条目数
	 * @return 对话概览列表
	 */
	@Deprecated
	@Select("SELECT" + FIELDS + ", count(id) as id " 			// 本来是as cnt的，但是发现Message里边有一个id域用不到，所以干脆把cnt作为id存进去。
																// 这在设计上不合理，但却给业务逻辑上提供了很大的便利。
																// 看看不用这个方法的话，要调用3次才能实现目标。
																// 见@deprecated说明。
			+ "FROM "
			+ "( " 												// 嵌套查询：先查与该用户有关的message，id越大，消息越新。
					+ "SELECT * FROM" + TABLE_NAME
					+ "WHERE from_id = #{userId} OR to_id = #{userId} "
					+ "ORDER BY id DESC "
			+ ") tt " 											// As a "Temp Table".
			+ "GROUP BY conversation_id "						// 执行GROUP BY后，将得到每个会话id下的最新消息。
																// 因为默认GROUP BY合并多条记录时取返回结果集“最上边的”那条。
			+ "ORDER BY id DESC "								// id越大，该次会话越新。
			+ "LIMIT #{offset}, #{limit}")
	List<Message> getConversationListByUserId(@Param("userId") int userId,
											  @Param("offset") int offset,
											  @Param("limit") int limit);
	/**
	 * 取得与该用户相关的所有对话ID（conversation_id)。
	 * @param userId 用户ID
	 * @param offset 查询起始偏移
	 * @param limit 查询条目数
	 * @return 对话ID列表
	 */
	@Select("SELECT DISTINCT conversation_id " + 
			"FROM message " + 
			"WHERE from_id = #{userId} OR to_id = #{userId}")
	List<String> getConversationsByUser(@Param("userId") int userId,
										  @Param("offset") int offset,
										  @Param("limit") int limit);
	
	/**
	 * 获取对话的摘要。<br>
	 * 摘要为该对话中的最新消息。<br>
	 * 注意：消息总数将作为Message的id域返回。
	 * @param conversationId 对话ID
	 * @return 该对话摘要
	 */
	@Select("SELECT " + FIELDS +
			"FROM message " + 
			"WHERE conversation_id = #{conversationId} " + 
			"ORDER BY id DESC " + 	// 只取最新
			"LIMIT 0, 1 ")			// 的一条
	Message getConversationDigest(@Param("conversationId") String conversationId);
	
	/**
	 * 得到指定对话中的消息总数。
	 * @param conversationId 目标对话ID
	 * @return 该对话的消息总数
	 */
	@Select("SELECT COUNT(id) " + 
			"FROM message " + 
			"WHERE conversation_id = #{conversationId}")
	int getConversationMsgCount(@Param("conversationId") String conversationId);
	
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
