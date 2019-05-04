package top.ourck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.ourck.beans.Comment;

@Mapper
public interface CommentDAO {
	
	String TABLE_NAME = " comment ";
	String INSERT_FIELDS = " (id, user_id, entity_id, entity_type, status, content, create_date) ";
	
	@Select("SELECT * FROM" + TABLE_NAME + "WHERE id = #{id}")
	Comment selectById(int id);
	
	@Select("SELECT * FROM" + TABLE_NAME
			+ "WHERE entity_id = #{entityId} "
			+ "AND entity_type = #{entityType} "
			+ "AND status = 1" // Filter those comment which are hidden with status = 0.
			+ " ORDER BY id DESC")
	List<Comment> selectByEntity(@Param("entityId") int entityId,
								 @Param("entityType") int entityType);
	
	@Select("SELECT COUNT(id) FROM" + TABLE_NAME + "WHERE entity_id = #{entityId} AND entity_type = #{entityType}")
	int getCommentCountById(@Param("entityId") int entityId,
							@Param("entityType") int entityType);
	
	@Insert("INSERT INTO" + TABLE_NAME + INSERT_FIELDS + "VALUES"
			+ " (#{id}, #{userId}, #{entityId}, #{entityType}, #{status}, #{content}, #{createDate}) ")
	int addComment(Comment comment);
	
	@Delete("DELETE FROM" + TABLE_NAME + "WHERE id = #{id}")
	int dropById(int id);
	
	@Update("UPDATE" + TABLE_NAME + "SET status = #{status} WHERE id = #{id}")
	void updateStatus(@Param("id") int id, @Param("status") int status);

	default void deleteById(int id) {
		updateStatus(id, id);
	}
}
