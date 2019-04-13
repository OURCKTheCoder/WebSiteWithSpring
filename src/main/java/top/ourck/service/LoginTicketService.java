package top.ourck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.ourck.beans.LoginTicket;
import top.ourck.dao.LoginTicketDAO;

@Service
public class LoginTicketService {

	@Autowired
	private LoginTicketDAO loginTicketDao;
	
	public boolean validateTicket(String ticket) {
		return getTicket(ticket) == null;
	}
	
	public LoginTicket getTicket(String ticket) {
		LoginTicket t = loginTicketDao.selectByTicket(ticket);
		return t;
	}
}
