package top.ourck.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import top.ourck.beans.LoginTicket;
import top.ourck.beans.User;
import top.ourck.dao.LoginTicketDAO;
import top.ourck.dao.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private LoginTicketDAO loginTicketDAO;
	
	public User getUser(int id) {
		return userDAO.selectById(id);
	}
	
	/**
	 * TODO 特殊字符验证？
	 * @param userName 用户名
	 * @param passwd 密码
	 * @return 信息
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String, String> register(String userName, String passwd) throws UnsupportedEncodingException {
		Map<String, String> info = new HashMap<>();
		
		// 1. 输入不为空校验
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(passwd)) {
			info.put("success", "false");
			info.put("error", "用户名 / 密码不能为空！");
			return info;
		}
		
		// 2. 查重
		User u;
		u = userDAO.selectByName(userName);
		if(u != null) {
			info.put("success", "false");
			info.put("error", "用户名已存在！");
			return info;
		}
		else {
				// 3. 写库
				u = new User();
				u.setName(userName);
				u.setImage(String.format("http://images.nowcoder.com/head/%dt.png",
						new Random(System.currentTimeMillis()).nextInt(1000)));
				String salt = UUID.randomUUID().toString().substring(0, 5);
				u.setSalt(salt);
				u.setPassword(DigestUtils.md5DigestAsHex((passwd + salt).getBytes("iso-8859-1")));

				userDAO.addUser(u);
				
				info.put("success", "true");
				return info;
		}
	}
	
	public Map<String, String> getAuth(String userName, String passwd) throws UnsupportedEncodingException {
		Map<String, String> info = new HashMap<>();
		User u = userDAO.selectByName(userName);
		
		if(u == null) {
			info.put("success", "false");
			info.put("error", "用户名不存在！");
			return info;
		}
		else {
			String pwd = u.getPassword();
			String salt = u.getSalt();
			String input = DigestUtils.md5DigestAsHex((passwd + salt).getBytes("iso-8859-1"));
			if(input.equals(pwd)) {
				info.put("success", "true");
				// [!] Add ticket on both side.
				String ticket = genTicket(u.getId());
				info.put("ticket", ticket);
			}
			else {
				info.put("success", "false");
				info.put("error", "密码错误！");
			}
			return info;
		}
	}
	
	private String genTicket(int userId) {
		LoginTicket t = new LoginTicket();
		t.setUserId(userId);
		t.setStatus(0);
		Date d = new Date();
		d.setTime(d.getTime() + 60 * 1000); // 1-min man.
		t.setExpired(d);
		t.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDAO.addTicket(t);
		
		return t.getTicket();
	}
	
}
