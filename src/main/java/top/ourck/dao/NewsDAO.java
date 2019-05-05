package top.ourck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.ourck.beans.News;

@Mapper
public interface NewsDAO {

	String TABLE_NAME = " news ";
	String FIELDS = " user_id, created_date, comment_count, like_count, image, link, title ";
	String SELECT_FIELDS = " id," + FIELDS; 
	
	@Insert("INSERT INTO" + TABLE_NAME + "(" + FIELDS + ")"
			+ "VALUES (#{userId}, #{createdDate}, #{commentCount}, #{likeCount}, #{image}, #{link}, #{title})" )
	int addNews(News news);
	
	@Select("SELECT" + SELECT_FIELDS + "FROM" + TABLE_NAME
			+ "WHERE id = #{id}")
	News selectById(int id);
	
//	@Select("SELECT * FROM" + TABLE_NAME + "WHERE id = #{id} LIMITS #{offset}, #{limit}")
	// .XML style mapping here
	List<News> selectByUserIdAndOffset(@Param("id") int id,
										@Param("offset") int offset,
										@Param("limit") int limit);
	
	@Select("SELECT comment_count FROM" + TABLE_NAME + "WHERE id = #{newsId}")
	int getCommentCount(int newsId);
	
	@Update("UPDATE" + TABLE_NAME + "SET comment_count = #{newCount} WHERE id = #{newsId}")
	void updateCommentCount(@Param("newsId") int newsId,
							@Param("newCount") int newCount);
	
	@Select("SELECT like_count FROM" + TABLE_NAME + "WHERE id = #{newsId}")
	int getLikeCount(int newsId);
	
	@Update("UPDATE" + TABLE_NAME + "SET like_count = #{newCount} WHERE id = #{newsId}")
	void updateLikeCount(@Param("newsId") int newsId,
						 @Param("newCount") int newCount);
	
}