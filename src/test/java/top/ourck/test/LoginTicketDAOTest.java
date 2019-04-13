package top.ourck.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.ourck.beans.LoginTicket;
import top.ourck.dao.LoginTicketDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoginTicketDAOTest {

	@Autowired
	private LoginTicketDAO dao;
	
	@Test
	public void daoTest() {
		LoginTicket t = new LoginTicket();
		t.setExpired(new Date(0));
		t.setStatus(0);
		t.setTicket(new Random().nextInt(100) + "");
		t.setUserId(1);
		t.setId(dao.addTicket(t));

		int id = t.getId();
		
		LoginTicket ticket = dao.selectById(id);
		assertNotNull(ticket);
		
		ticket = dao.selectByTicket(ticket.getTicket());
		assertNotNull(ticket);
		
		dao.delTicket(id);
		ticket = dao.selectById(id);
		assertNull(ticket);
		
//		int status = ticket.getStatus();
//		dao.updateStatus(1, id);
//		ticket = dao.selectById(id);
//		assertEquals(ticket.getStatus(), status);
//		dao.updateStatus(status, id);
	}
	
}
