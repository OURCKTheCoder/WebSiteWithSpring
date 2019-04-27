package top.ourck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
	
	@Select("SELECT * FROM" + TABLE_NAME + "WHERE id = #{id} LIMITS #{offset}, #{limit}")
	List<News> selectByUserIdAndOffset(@Param("id") int id,
										@Param("offset") int offset,
										@Param("limit") int limit);
}