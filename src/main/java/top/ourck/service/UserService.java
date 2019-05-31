package top.ourck.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import top.ourck.beans.LoginTicket;
import top.ourck.beans.User;
import top.ourck.dao.LoginTicketDAO;
import top.ourck.dao.UserDAO;
import top.ourck.util.BizConstUtil;
import top.ourck.util.StringUtil;

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
	 * 用户注册接口。
	 * @param userName 用户名
	 * @param passwd 密码
	 * @return 信息
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String, Object> register(String userName, String passwd) throws UnsupportedEncodingException {
		Map<String, Object> info = new HashMap<>();
		
		// 1. 输入不为空 & 特殊字符校验
		if(StringUtil.isBlank(userName)) {
			info.put("success", "false");
			info.put("msgname", "用户名不能为空");
			return info;
		}
		if(StringUtil.isBlank(passwd)) {
			info.put("success", "false");
			info.put("msgpwd", "密码不能为空");
			return info;
		}
		if(StringUtil.containsSpchar(userName)) {
			info.put("success", "false");
			info.put("msgname", "用户名包含特殊字符");
			return info;
		}
		if(StringUtil.containsSpchar(passwd)) {
			info.put("success", "false");
			info.put("msgpwd", "密码包含特殊字符");
			return info;
		}
		
		// 2. 查重
		User u;
		u = userDAO.selectByName(userName);
		if(u != null) {
			info.put("success", "false");
			info.put("msgname", "用户名已经被注册");
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

	public Map<String, Object> getAuth(String userName, String passwd, boolean rememberMe) throws UnsupportedEncodingException {
		Map<String, Object> info = new HashMap<>();
		
		if(StringUtil.isBlank(userName)) {
			info.put("success", "false");
			info.put("msgname", "用户名不能为空");
			return info;
		}
		if(StringUtil.isBlank(passwd)) {
			info.put("success", "false");
			info.put("msgpwd", "密码不能为空");
			return info;
		}
		
		User u = userDAO.selectByName(userName);
		if(u == null) {
			info.put("success", "false");
			info.put("msgname", "用户名不存在");
			return info;
		}
		else {
			String pwd = u.getPassword();
			String salt = u.getSalt();
			String input = DigestUtils.md5DigestAsHex((passwd + salt).getBytes("iso-8859-1"));
			if(input.equals(pwd)) {
				info.put("success", "true");
				// Add ticket on both side.
				int expireSec = rememberMe ? BizConstUtil.LOGIN_REMEMBERME_EXPIRE_SEC
						: BizConstUtil.LOGIN_NOT_REMEMBERME_EXPIRE_SEC;
				LoginTicket ticket = genTicket(u.getId(), expireSec);
				info.put("ticket", ticket);
				info.put("uid", u.getId());
			}
			else {
				info.put("success", "false");
				info.put("msgpwd", "密码不正确");
				info.put("uid", u.getId());
			}
			return info;
		}
	}

	public void logout(String ticket) {
		loginTicketDAO.updateStatus(1, ticket);
	}
	
	private LoginTicket genTicket(int userId, int expireSec) {
		LoginTicket t = new LoginTicket();
		t.setUserId(userId);
		t.setStatus(0);
		Date d = new Date();
		d.setTime(d.getTime() + expireSec);
		t.setExpired(d);
		t.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDAO.addTicket(t);
		
		return t;
	}
	
}
