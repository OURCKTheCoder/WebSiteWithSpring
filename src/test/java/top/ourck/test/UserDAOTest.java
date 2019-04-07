package top.ourck.test;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.ourck.beans.User;
import top.ourck.dao.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDAOTest {

	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void insertTest() {
		Random r = new Random();
	    for (int i = 0; i < 11; ++i) {
	        User user = new User();
	        user.setName(String.format("USER%d", i));
			user.setImage(String.format("http://images.nowcoder.com/head/%dt.png", r.nextInt(1000)));
			user.setPassword("");
			
			user.setSalt("");
			userDAO.addUser(user);
	    }
	
	}
	
	@Test
	public void updateDeleteTest() {
		User user = new User();
		user.setId(1);
		user.setPassword("newpassword");
		userDAO.updatePassword(user);
		
		Assert.assertEquals("newpassword", userDAO.selectById(1).getPassword());
		userDAO.deleteById(2);
		Assert.assertNull(userDAO.selectById(2));	
	}

}
