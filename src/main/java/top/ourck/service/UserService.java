package top.ourck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.ourck.beans.User;
import top.ourck.dao.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	public User getUser(int id) {
		return userDAO.selectById(id);
	}
}
