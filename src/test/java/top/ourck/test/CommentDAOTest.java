package top.ourck.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.ourck.beans.Comment;
import top.ourck.beans.EntityType;
import top.ourck.dao.CommentDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CommentDAOTest {

	private static final int FIXED_ID = 1;
	private static final int TARGET_NEWS_ID = 23;
	private static final int OWNER_ID = 93;
	
	@Autowired
	private CommentDAO commentDAO;

	@Test
	public void addCommentTest() {
		if(commentDAO.selectById(1) == null) {
			Comment c = new Comment(FIXED_ID, "Bravo!", 1, EntityType.News, TARGET_NEWS_ID, OWNER_ID, new Date());
			commentDAO.addComment(c);
			Comment result;
			assertNotNull(result = commentDAO.selectById(FIXED_ID));
			assertEquals(c.getContent(), result.getContent());
		}
	}
	
	@Test
	public void selectListTest() {
		assertNotNull(commentDAO.selectByEntity(TARGET_NEWS_ID, EntityType.News));
	}
	
//	@Test
//	public void deleteTest() {
//		commentDAO.deleteById(FIXED_ID);
//		assertNull(commentDAO.selectById(FIXED_ID));
//	}
}
