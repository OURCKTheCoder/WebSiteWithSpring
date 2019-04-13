package top.ourck.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.ourck.beans.LoginTicket;

@Mapper
public interface LoginTicketDAO {

	String TABLE_NAME = " login_ticket ";
	String FIELDS = " user_id, ticket, expired, status ";
	String SELECT_FIELDS = " id," + FIELDS; 

	@Insert("INSERT INTO" + TABLE_NAME + "(" + FIELDS + ")" + " VALUES ( #{userId}, #{ticket}, #{expired}, #{status} )")
	int addTicket(LoginTicket ticket);
	
	@Delete("DELETE FROM" + TABLE_NAME + "WHERE id = #{id}")
	void delTicket(int id);

	@Select("SELECT" + SELECT_FIELDS + "FROM" + TABLE_NAME + "WHERE id = #{id}")
	LoginTicket selectById(int id);
	
	@Select("SELECT" + SELECT_FIELDS + "FROM" + TABLE_NAME + "WHERE ticket = #{ticket}")
	LoginTicket selectByTicket(String ticket);
	
	@Update("UPDATE" + TABLE_NAME + "SET status = #{status} WHERE ticket = #{ticket}")
	void updateStatus(@Param("status") int status, @Param("ticket") String ticket);
}
