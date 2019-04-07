package top.ourck.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.ourck.beans.User;

@Mapper
public interface UserDAO {

	String TABLE_NAME = " user ";
	String FIELDS = " name, password, salt, image ";
	String SELECT_FIELDS = " id, name, password, salt, image ";
	
	@Insert("INSERT INTO" + TABLE_NAME + "(" + FIELDS + ") "
			+ "VALUES (#{name}, #{password}, #{salt}, #{image})")
	int addUser(User user);
	
	@Delete("DELETE FROM" + TABLE_NAME + "WHERE id = #{id}")
	void deleteById(int id);

	@Update("UPDATE" + TABLE_NAME + "SET password = #{password} WHERE id = #{id}")
	void updatePassword(User user);
	
	@Select("SELECT" + SELECT_FIELDS + "FROM" + TABLE_NAME + "WHERE id = #{id}")
	User selectById(int id);
}