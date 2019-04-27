package top.ourck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import top.ourck.beans.Comment;

@Mapper
public interface CommentDAO {
	
	String TABLE_NAME = " comment ";
	String INSERT_FIELDS = " (id, user_id, entity_id, entity_type, status, content, create_date) ";
	
	@Select("SELECT * FROM" + TABLE_NAME + "WHERE id = #{id}")
	Comment selectById(int id);
	
	@Select("SELECT * FROM" + TABLE_NAME + "WHERE entity_id = #{entityId} AND entity_type = #{entityType}"
			+ " ORDER BY id DESC")
	List<Comment> selectByEntity(@Param("entityId") int entityId,
										@Param("entityType") int entityType);
	
	@Select("SELECT COUNT(id) FROM" + TABLE_NAME + "WHERE entity_id = #{entityId} AND entity_type = #{entitytype}")
	int getCommentCountById(@Param("entityId") int entityId,
								   @Param("entityType") int entityType);
	
	@Insert("INSERT INTO" + TABLE_NAME + INSERT_FIELDS + "VALUES"
			+ " (#{id}, #{userId}, #{entityId}, #{entityType}, #{status}, #{content}, #{createDate}) ")
	int addComment(Comment comment);
	
	@Delete("DELETE FROM" + TABLE_NAME + "WHERE id = #{id}")
	int deleteById(int id);
}
